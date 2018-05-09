/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/26/2018
 * Time: 12:11 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.Utils.TillBoxUtils;
import nybsys.tillboxweb.bll.manager.CustomerReturnBllManager;
import nybsys.tillboxweb.broker.client.CallBack;
import nybsys.tillboxweb.broker.client.PublisherForWorker;
import nybsys.tillboxweb.broker.client.SubscriberForWorker;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.constant.WorkerSubscriptionConstants;
import nybsys.tillboxweb.coreConstant.CurrencyConstant;
import nybsys.tillboxweb.coreEnum.DebitCreditIndicator;
import nybsys.tillboxweb.coreEnum.DefaultCOA;
import nybsys.tillboxweb.coreEnum.PartyType;
import nybsys.tillboxweb.coreEnum.ReferenceType;
import nybsys.tillboxweb.coreModels.*;
import nybsys.tillboxweb.models.CustomerModel;
import nybsys.tillboxweb.models.CustomerReturnDetailModel;
import nybsys.tillboxweb.models.CustomerReturnModel;
import nybsys.tillboxweb.models.VMCustomerReturn;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CustomerReturnServiceManager extends BaseService {

    private static final Logger log = LoggerFactory.getLogger(CustomerReturnServiceManager.class);
    @Autowired
    private CustomerReturnBllManager customerReturnBllManager;

    private CustomerAdjustmentServiceManager customerAdjustmentServiceManager = new CustomerAdjustmentServiceManager();

    public ResponseMessage save(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        VMCustomerReturn vmCustomerReturn = new VMCustomerReturn();
        CustomerModel customerModel = new CustomerModel();
        Boolean isUpdateRequest = false;
        CurrencyModel currencyModel;

        try {
            //get base currency and exchange rate
            currencyModel = this.customerAdjustmentServiceManager.getBaseCurrency();
            if (currencyModel == null) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = CurrencyConstant.BASE_CURRENT_NOT_FOUND;
                return responseMessage;
            }

            //check entry currency is present if not base currency will be entry currency
            if (requestMessage.entryCurrencyID == null || requestMessage.entryCurrencyID == 0) {
                requestMessage.entryCurrencyID = currencyModel.getCurrencyID();
            }

            vmCustomerReturn = Core.getRequestObject(requestMessage, VMCustomerReturn.class);
            /*Set<ConstraintViolation<AddressTypeModel>> violations = this.validator.validate(customerAddressTypeModel);
            if (violations.size() > 0) {
                responseMessage = this.buildResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            //(1)

            if (vmCustomerReturn.customerReturnModel.getCustomerReturnID() == null || vmCustomerReturn.customerReturnModel.getCustomerReturnID() > 0) {
                isUpdateRequest = true;
            }

            if (vmCustomerReturn.customerReturnModel.getCustomerReturnID() == null && vmCustomerReturn.customerReturnModel.getCustomerReturnID() == 0) {
                vmCustomerReturn.customerReturnModel.setBaseCurrencyID(currencyModel.getCurrencyID());
                vmCustomerReturn.customerReturnModel.setEntryCurrencyID(requestMessage.entryCurrencyID);
                vmCustomerReturn.customerReturnModel.setBaseCurrencyAmount(vmCustomerReturn.customerReturnModel.getTotalAmount() * vmCustomerReturn.customerReturnModel.getExchangeRate());
            }
            this.customerReturnBllManager.saveCustomerReturn(vmCustomerReturn);

            List<JournalModel> lstJournalModel = new ArrayList<>();
            lstJournalModel = getJournalModelFromInvoice(vmCustomerReturn, currencyModel, requestMessage.entryCurrencyID);
            VMJournalListModel vmJournalListModel = new VMJournalListModel();
            vmJournalListModel.lstJournalModel = lstJournalModel;
            requestMessage.requestObj = vmJournalListModel;


            if (Core.clientMessage.get().messageCode != null) {
                this.rollBack();
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.FAILED_TO_SAVE_CUSTOMER_RETURN;
                } else {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
            } else {
                // responseMessage = this.callInterModuleFunction("api/journal/save", WorkerSubscriptionConstants.WORKER_ACCOUNTING_MODULE_TOPIC, requestMessage);

                if (isUpdateRequest) {
                    responseMessage = this.checkInterComUpdate(requestMessage, vmCustomerReturn);
                } else {
                    responseMessage = this.checkInterCom(requestMessage, vmCustomerReturn);
                }

                if (responseMessage.responseCode == TillBoxAppConstant.FAILED_ERROR_CODE) {
                    this.rollBack();
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    responseMessage.message = MessageConstant.FAILED_TO_SAVE_CUSTOMER_RETURN;
                } else {
                    responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                    responseMessage.message = MessageConstant.CUSTOMER_RETURN_SAVE_SUCCESSFULLY;
                }
            }
            responseMessage.responseObj = vmCustomerReturn;
        } catch (Exception ex) {
            log.error("PurchaseOrderServiceManager -> savePurchaseOrderVM got exception");
            responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            responseMessage.message = MessageConstant.SUPPLIER_RETURN_SAVE_FAILED;
//            if (responseMessage.message == null) {
//                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
//            }
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }


    private List<JournalModel> getJournalModelFromInvoice(VMCustomerReturn vmCustomerReturn, CurrencyModel currencyModel, Integer entryCurrencyID) {
        List<JournalModel> lstJournalModel = new ArrayList<>();

        //Debit journal
        JournalModel journalModelDebit = new JournalModel();
        journalModelDebit.setBusinessID(vmCustomerReturn.customerReturnModel.getBusinessID());
        journalModelDebit.setAmount(vmCustomerReturn.customerReturnModel.getTotalAmount());
        journalModelDebit.setAccountID(DefaultCOA.Inventory.get());
        journalModelDebit.setReferenceID(vmCustomerReturn.customerReturnModel.getCustomerReturnID());
        journalModelDebit.setReferenceType(ReferenceType.CustomerReturn.get());
        journalModelDebit.setDrCrIndicator(DebitCreditIndicator.Debit.get());
        journalModelDebit.setDate(new Date());

        journalModelDebit.setBaseCurrencyID(currencyModel.getCurrencyID());
        journalModelDebit.setEntryCurrencyID(entryCurrencyID);
        journalModelDebit.setExchangeRate(vmCustomerReturn.customerReturnModel.getExchangeRate());
        journalModelDebit.setBaseCurrencyAmount(vmCustomerReturn.customerReturnModel.getTotalAmount() * vmCustomerReturn.customerReturnModel.getExchangeRate());

        lstJournalModel.add(journalModelDebit);
        //Credit journal
        JournalModel journalModelCredit = new JournalModel();
        journalModelCredit.setBusinessID(vmCustomerReturn.customerReturnModel.getBusinessID());
        journalModelCredit.setAmount(vmCustomerReturn.customerReturnModel.getTotalAmount());
        journalModelCredit.setAccountID(DefaultCOA.AccountReceivable.get());
        journalModelCredit.setPartyID(vmCustomerReturn.customerReturnModel.getCustomerID());
        journalModelCredit.setPartyType(PartyType.Customer.get());
        journalModelCredit.setReferenceID(vmCustomerReturn.customerReturnModel.getCustomerReturnID());
        journalModelCredit.setReferenceType(ReferenceType.CustomerReturn.get());
        journalModelCredit.setDrCrIndicator(DebitCreditIndicator.Credit.get());
        journalModelCredit.setDate(new Date());

        journalModelCredit.setBaseCurrencyID(currencyModel.getCurrencyID());
        journalModelCredit.setEntryCurrencyID(entryCurrencyID);
        journalModelCredit.setExchangeRate(vmCustomerReturn.customerReturnModel.getExchangeRate());
        journalModelCredit.setBaseCurrencyAmount(vmCustomerReturn.customerReturnModel.getTotalAmount() * vmCustomerReturn.customerReturnModel.getExchangeRate());

        lstJournalModel.add(journalModelCredit);


        if (vmCustomerReturn.customerReturnModel.getTotalVAT() > 0) {
            //Debit journal
            journalModelDebit = new JournalModel();
            journalModelDebit.setBusinessID(vmCustomerReturn.customerReturnModel.getBusinessID());
            journalModelDebit.setAmount(vmCustomerReturn.customerReturnModel.getTotalVAT());
            journalModelDebit.setAccountID(DefaultCOA.SalesVAT.get());
            journalModelDebit.setReferenceID(vmCustomerReturn.customerReturnModel.getCustomerReturnID());
            journalModelDebit.setReferenceType(ReferenceType.CustomerReturn.get());
            journalModelDebit.setDrCrIndicator(DebitCreditIndicator.Debit.get());
            journalModelDebit.setDate(new Date());


            journalModelDebit.setBaseCurrencyID(currencyModel.getCurrencyID());
            journalModelDebit.setEntryCurrencyID(entryCurrencyID);
            journalModelDebit.setExchangeRate(vmCustomerReturn.customerReturnModel.getExchangeRate());
            journalModelDebit.setBaseCurrencyAmount(vmCustomerReturn.customerReturnModel.getTotalVAT() * vmCustomerReturn.customerReturnModel.getExchangeRate());

            lstJournalModel.add(journalModelDebit);


            //Credit journal
            journalModelCredit = new JournalModel();
            journalModelCredit.setBusinessID(vmCustomerReturn.customerReturnModel.getBusinessID());
            journalModelCredit.setAmount(vmCustomerReturn.customerReturnModel.getTotalVAT());
            journalModelCredit.setAccountID(DefaultCOA.AccountReceivable.get());
            journalModelCredit.setPartyID(vmCustomerReturn.customerReturnModel.getCustomerID());
            journalModelCredit.setPartyType(PartyType.Customer.get());
            journalModelCredit.setReferenceID(vmCustomerReturn.customerReturnModel.getCustomerReturnID());
            journalModelCredit.setReferenceType(ReferenceType.CustomerReturn.get());
            journalModelCredit.setDrCrIndicator(DebitCreditIndicator.Credit.get());
            journalModelCredit.setDate(new Date());

            journalModelCredit.setBaseCurrencyID(currencyModel.getCurrencyID());
            journalModelCredit.setEntryCurrencyID(entryCurrencyID);
            journalModelCredit.setExchangeRate(vmCustomerReturn.customerReturnModel.getExchangeRate());
            journalModelCredit.setBaseCurrencyAmount(vmCustomerReturn.customerReturnModel.getTotalVAT() * vmCustomerReturn.customerReturnModel.getExchangeRate());

            lstJournalModel.add(journalModelCredit);
        }

        if (vmCustomerReturn.customerReturnModel.getTotalDiscount() > 0) {
            //Debit journal
            journalModelDebit = new JournalModel();
            journalModelDebit.setBusinessID(vmCustomerReturn.customerReturnModel.getBusinessID());
            journalModelDebit.setAmount(vmCustomerReturn.customerReturnModel.getTotalDiscount());
            journalModelDebit.setAccountID(DefaultCOA.AccountReceivable.get());
            journalModelDebit.setPartyID(vmCustomerReturn.customerReturnModel.getCustomerID());
            journalModelDebit.setPartyType(PartyType.Customer.get());
            journalModelDebit.setReferenceID(vmCustomerReturn.customerReturnModel.getCustomerReturnID());
            journalModelDebit.setReferenceType(ReferenceType.CustomerReturn.get());
            journalModelDebit.setDrCrIndicator(DebitCreditIndicator.Debit.get());
            journalModelDebit.setDate(new Date());

            journalModelDebit.setBaseCurrencyID(currencyModel.getCurrencyID());
            journalModelDebit.setEntryCurrencyID(entryCurrencyID);
            journalModelDebit.setExchangeRate(vmCustomerReturn.customerReturnModel.getExchangeRate());
            journalModelDebit.setBaseCurrencyAmount(vmCustomerReturn.customerReturnModel.getTotalDiscount() * vmCustomerReturn.customerReturnModel.getExchangeRate());

            lstJournalModel.add(journalModelDebit);
            //Credit journal
            journalModelCredit = new JournalModel();
            journalModelCredit.setBusinessID(vmCustomerReturn.customerReturnModel.getBusinessID());
            journalModelCredit.setAmount(vmCustomerReturn.customerReturnModel.getTotalDiscount());
            journalModelCredit.setAccountID(DefaultCOA.DiscountGiven.get());
            journalModelCredit.setReferenceID(vmCustomerReturn.customerReturnModel.getCustomerReturnID());
            journalModelCredit.setReferenceType(ReferenceType.CustomerReturn.get());
            journalModelCredit.setDrCrIndicator(DebitCreditIndicator.Credit.get());
            journalModelCredit.setDate(new Date());

            journalModelCredit.setBaseCurrencyID(currencyModel.getCurrencyID());
            journalModelCredit.setEntryCurrencyID(entryCurrencyID);
            journalModelCredit.setExchangeRate(vmCustomerReturn.customerReturnModel.getExchangeRate());
            journalModelCredit.setBaseCurrencyAmount(vmCustomerReturn.customerReturnModel.getTotalDiscount() * vmCustomerReturn.customerReturnModel.getExchangeRate());

            lstJournalModel.add(journalModelCredit);
        }

        return lstJournalModel;
    }


    public ResponseMessage callInterModuleFunction(String apiurl, String topicName, RequestMessage reqMessForWorker) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {

            MqttClient mqttClient;
            responseMessage = Core.buildDefaultResponseMessage();

            boolean workCompleteWithInAllowTime;
            Object lockObject = new Object();
            this.barrier = TillBoxUtils.getBarrier(1, lockObject);

            CallBack callBack;

            String pubTopic = topicName;
            reqMessForWorker.brokerMessage.serviceName = apiurl;
            reqMessForWorker.token = Core.requestToken.get();
            SubscriberForWorker subForWorker = new SubscriberForWorker(reqMessForWorker.brokerMessage.messageId, this.barrier);
            mqttClient = subForWorker.subscribe();
            callBack = subForWorker.getCallBack();
            PublisherForWorker pubForWorker = new PublisherForWorker(pubTopic, mqttClient);
            pubForWorker.publishedMessageToWorker(reqMessForWorker);

            synchronized (lockObject) {
                long startTime = System.nanoTime();
                lockObject.wait(allowedTime);
                workCompleteWithInAllowTime = this.isResponseWithInAllowedTime(startTime);

                if (workCompleteWithInAllowTime) {
                    responseMessage = callBack.getResponseMessage();


                } else {
                    //timeout
                    //TODO Implement role back logic
                }
            }
            this.closeBrokerClient(mqttClient, reqMessForWorker.brokerMessage.messageId);

        } catch (Exception ex) {

        }


        return responseMessage;
    }

    private List<InventoryTransactionModel> getInventoryTransactionList(VMCustomerReturn vmCustomerReturn) {
        List<InventoryTransactionModel> lstInventoryTransactionModel = new ArrayList<>();
        for (CustomerReturnDetailModel customerReturnDetailModel : vmCustomerReturn.lstCustomerReturnDetailModel) {

            InventoryTransactionModel inventoryTransactionModel = new InventoryTransactionModel();
            inventoryTransactionModel.setBusinessID(vmCustomerReturn.customerReturnModel.getBusinessID());
            inventoryTransactionModel.setProductID(customerReturnDetailModel.getProductID());
            inventoryTransactionModel.setInQuantity(customerReturnDetailModel.getQuantity());
            inventoryTransactionModel.setReferenceID(vmCustomerReturn.customerReturnModel.getCustomerReturnID());
            inventoryTransactionModel.setReferenceType(ReferenceType.CustomerReturn.get());
            lstInventoryTransactionModel.add(inventoryTransactionModel);
        }
        return lstInventoryTransactionModel;
    }


    public ResponseMessage checkInterComUpdate(RequestMessage requestMessage, VMCustomerReturn vmCustomerReturn) {
        MqttClient mqttClientSaveJournal;
        MqttClient mqttClientSaveInventoryTransaction;
        MqttClient mqttClientDeleteJournal = null;
        MqttClient mqttClientDeleteInventoryTransaction = null;

        CallBack callBackSaveJournal;
        CallBack callBackInventoryTransaction;
        CallBack callBackDeleteJournal = null;
        CallBack callBackDeleteInventoryTransaction = null;

        ResponseMessage responseMessage = new ResponseMessage();
        ResponseMessage responseMessageSaveJournal;
        ResponseMessage responseMessageSaveInventoryTransaction;
        ResponseMessage responseMessageDeleteJournal;
        ResponseMessage responseMessageDeleteInventoryTransaction;


        RequestMessage reqJournalSaveMessage, reqObjInventoryTransactionMessage, journalDeletedRequestMessage, inventoryTransactionDeletedRequestMessage;
        boolean workCompleteWithInAllowTime;
        try {

            Object lockObject = new Object();
            reqJournalSaveMessage = Core.getDefaultWorkerRequestMessage();
            reqObjInventoryTransactionMessage = Core.getDefaultWorkerRequestMessage();
            journalDeletedRequestMessage = Core.getDefaultWorkerRequestMessage();
            inventoryTransactionDeletedRequestMessage = Core.getDefaultWorkerRequestMessage();

            reqJournalSaveMessage.token = requestMessage.token;
            reqObjInventoryTransactionMessage.token = requestMessage.token;
            journalDeletedRequestMessage.token = requestMessage.token;
            inventoryTransactionDeletedRequestMessage.token = requestMessage.token;

            String pubTopic = WorkerSubscriptionConstants.WORKER_ACCOUNTING_MODULE_TOPIC;
            String inventoryTopic = WorkerSubscriptionConstants.WORKER_INVENTORY_TOPIC;
            this.barrier = TillBoxUtils.getBarrier(4, lockObject); //new CyclicBarrier(2, new WorkersStatus(lockObject));

            VMInventoryTransaction vmInventoryTransaction = new VMInventoryTransaction();
            vmInventoryTransaction.lstInventoryTransactionModel = this.getInventoryTransactionList(vmCustomerReturn);

            //======================= Start of one ===========================
            JournalModel searchJournalModel = new JournalModel();
            searchJournalModel.setReferenceType(ReferenceType.CustomerReturn.get());
            searchJournalModel.setReferenceID(vmCustomerReturn.customerReturnModel.getCustomerReturnID());

            journalDeletedRequestMessage.requestObj = searchJournalModel;
            journalDeletedRequestMessage.brokerMessage.serviceName = "api/journal/delete";
            SubscriberForWorker journalDeleteSubForWorker = new SubscriberForWorker(journalDeletedRequestMessage.brokerMessage.messageId, this.barrier);
            mqttClientDeleteJournal = journalDeleteSubForWorker.subscribe();
            callBackDeleteJournal = journalDeleteSubForWorker.getCallBack();
            PublisherForWorker pubForWorkerGetUserList = new PublisherForWorker(pubTopic, mqttClientDeleteJournal);
            pubForWorkerGetUserList.publishedMessageToWorker(journalDeletedRequestMessage);
            //======================= End of one ===========================

            //======================= Start of two ===========================
            InventoryTransactionModel inventoryTransactionModel = new InventoryTransactionModel();
            inventoryTransactionModel.setReferenceType(ReferenceType.CustomerReturn.get());
            inventoryTransactionModel.setReferenceID(vmCustomerReturn.customerReturnModel.getCustomerReturnID());

            inventoryTransactionDeletedRequestMessage.requestObj = inventoryTransactionModel;
            inventoryTransactionDeletedRequestMessage.brokerMessage.serviceName = "api/inventory/inventoryTransaction/delete";
            SubscriberForWorker subForWorkerInventoryTransaction = new SubscriberForWorker(inventoryTransactionDeletedRequestMessage.brokerMessage.messageId, this.barrier);
            mqttClientDeleteInventoryTransaction = subForWorkerInventoryTransaction.subscribe();
            callBackDeleteInventoryTransaction = subForWorkerInventoryTransaction.getCallBack();
            PublisherForWorker pubForWorkerDeleteInventoryTransaction = new PublisherForWorker(inventoryTopic, mqttClientDeleteInventoryTransaction);
            pubForWorkerDeleteInventoryTransaction.publishedMessageToWorker(inventoryTransactionDeletedRequestMessage);
            //======================= End of two ===========================

            //======================= Start of three ===========================
            reqJournalSaveMessage.requestObj = requestMessage.requestObj;
            reqJournalSaveMessage.brokerMessage.serviceName = "api/journal/save";
            SubscriberForWorker subForWorker = new SubscriberForWorker(reqJournalSaveMessage.brokerMessage.messageId, this.barrier);
            mqttClientSaveJournal = subForWorker.subscribe();
            callBackSaveJournal = subForWorker.getCallBack();
            PublisherForWorker pubForWorkerGetJournalSave = new PublisherForWorker(pubTopic, mqttClientSaveJournal);
            pubForWorkerGetJournalSave.publishedMessageToWorker(reqJournalSaveMessage);

            //======================= End of three ===========================

            //======================= Start of four ===========================


            reqObjInventoryTransactionMessage.requestObj = vmInventoryTransaction;
            reqObjInventoryTransactionMessage.brokerMessage.serviceName = "api/inventory/inventoryTransaction/Save";
            SubscriberForWorker subForWorkerForInventory = new SubscriberForWorker(reqObjInventoryTransactionMessage.brokerMessage.messageId, this.barrier);
            mqttClientSaveInventoryTransaction = subForWorkerForInventory.subscribe();
            callBackInventoryTransaction = subForWorkerForInventory.getCallBack();
            PublisherForWorker pubForWorkerSaveUser = new PublisherForWorker(inventoryTopic, mqttClientSaveInventoryTransaction);
            pubForWorkerSaveUser.publishedMessageToWorker(reqObjInventoryTransactionMessage);
            //======================= End of four ===========================


            synchronized (lockObject) {
                responseMessage = Core.buildDefaultResponseMessage();
                long startTime = System.nanoTime();
                lockObject.wait(this.allowedTime);
                workCompleteWithInAllowTime = this.isResponseWithInAllowedTime(startTime);

                if (workCompleteWithInAllowTime) {

                    responseMessageDeleteJournal = callBackDeleteJournal.getResponseMessage();
                    callBackDeleteInventoryTransaction.getResponseMessage();

                    responseMessageSaveJournal = callBackSaveJournal.getResponseMessage();
                    responseMessageSaveInventoryTransaction = callBackInventoryTransaction.getResponseMessage();
                    responseMessage.message = "Inter module communication successful";
                    responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

                } else {
                    //timeout
                    log.info("Response time out");
                    log.info("RollBack checkInterCom Operation");

                    responseMessage.message = "Inter module communication Failed";
                    responseMessage.responseCode = TillBoxAppConstant.UN_PROCESSABLE_REQUEST;
                }
            }


            this.closeBrokerClient(mqttClientSaveJournal, requestMessage.brokerMessage.messageId);
            this.closeBrokerClient(mqttClientSaveInventoryTransaction, requestMessage.brokerMessage.messageId);
            this.closeBrokerClient(mqttClientDeleteJournal, requestMessage.brokerMessage.messageId);
            this.closeBrokerClient(mqttClientDeleteInventoryTransaction, requestMessage.brokerMessage.messageId);

        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Exception from checkInterCom Module communication UserServiceManager");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
        }
        return responseMessage;
    }


    public ResponseMessage checkInterCom(RequestMessage requestMessage, VMCustomerReturn vmCustomerReturn) {
        MqttClient mqttClientSaveJournal;
        MqttClient mqttClientSaveInventoryTransaction;

        CallBack callBackSaveJournal;
        CallBack callBackInventoryTransaction;

        ResponseMessage responseMessage;//= new ResponseMessage();
        ResponseMessage responseMessageSaveJournal;
        ResponseMessage responseMessageSaveInventoryTransaction;


        RequestMessage reqMessGetUserList;
        boolean workCompleteWithInAllowTime;
        try {

            Object lockObject = new Object();
            RequestMessage reqObjInventoryTransactionMessage = Core.getDefaultWorkerRequestMessage();
            RequestMessage reqJournalSaveMessage = Core.getDefaultWorkerRequestMessage();
            reqJournalSaveMessage.token = requestMessage.token;
            reqObjInventoryTransactionMessage.token = requestMessage.token;


            String pubTopic = WorkerSubscriptionConstants.WORKER_ACCOUNTING_MODULE_TOPIC;
            String inventoryTopic = WorkerSubscriptionConstants.WORKER_INVENTORY_TOPIC;
            this.barrier = TillBoxUtils.getBarrier(2, lockObject); //new CyclicBarrier(2, new WorkersStatus(lockObject));

            VMInventoryTransaction vmInventoryTransaction = new VMInventoryTransaction();
            vmInventoryTransaction.lstInventoryTransactionModel = this.getInventoryTransactionList(vmCustomerReturn);


            //======================= Start of one ===========================
            reqJournalSaveMessage.requestObj = requestMessage.requestObj;
            reqJournalSaveMessage.brokerMessage.serviceName = "api/journal/save";
            SubscriberForWorker subForWorker = new SubscriberForWorker(reqJournalSaveMessage.brokerMessage.messageId, this.barrier);
            mqttClientSaveJournal = subForWorker.subscribe();
            callBackSaveJournal = subForWorker.getCallBack();
            PublisherForWorker pubForWorkerGetUserList = new PublisherForWorker(pubTopic, mqttClientSaveJournal);
            pubForWorkerGetUserList.publishedMessageToWorker(reqJournalSaveMessage);
            //======================= End of one ===========================


            //======================= Start of two ===========================


            reqObjInventoryTransactionMessage.requestObj = vmInventoryTransaction;
            reqObjInventoryTransactionMessage.brokerMessage.serviceName = "api/inventory/inventoryTransaction/Save";
            SubscriberForWorker subForWorkerForInventory = new SubscriberForWorker(reqObjInventoryTransactionMessage.brokerMessage.messageId, this.barrier);
            mqttClientSaveInventoryTransaction = subForWorkerForInventory.subscribe();
            callBackInventoryTransaction = subForWorkerForInventory.getCallBack();
            PublisherForWorker pubForWorkerSaveUser = new PublisherForWorker(inventoryTopic, mqttClientSaveInventoryTransaction);
            pubForWorkerSaveUser.publishedMessageToWorker(reqObjInventoryTransactionMessage);
            //======================= End of two ===========================


            synchronized (lockObject) {
                responseMessage = Core.buildDefaultResponseMessage();
                long startTime = System.nanoTime();
                lockObject.wait(this.allowedTime);
                workCompleteWithInAllowTime = this.isResponseWithInAllowedTime(startTime);

                if (workCompleteWithInAllowTime) {
                    responseMessageSaveJournal = callBackSaveJournal.getResponseMessage();
                    responseMessageSaveInventoryTransaction = callBackInventoryTransaction.getResponseMessage();
                    responseMessage.message = "Inter module communication successful";
                    responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

                } else {
                    //timeout
                    log.info("Response time out");
                    log.info("RollBack checkInterCom Operation");

                    responseMessage.message = "Inter module communication Failed";
                    responseMessage.responseCode = TillBoxAppConstant.UN_PROCESSABLE_REQUEST;
                }
            }


            this.closeBrokerClient(mqttClientSaveJournal, requestMessage.brokerMessage.messageId);
            this.closeBrokerClient(mqttClientSaveInventoryTransaction, requestMessage.brokerMessage.messageId);

        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Exception from checkInterCom Module communication UserServiceManager");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
        }
        return responseMessage;
    }


    public ResponseMessage search(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        CustomerReturnModel customerInvoiceModel = new CustomerReturnModel();


        try {
            customerInvoiceModel = Core.getRequestObject(requestMessage, CustomerReturnModel.class);

            responseMessage.responseObj = this.customerReturnBllManager.searchVMCustomerReturn(customerInvoiceModel);

            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
        } catch (Exception ex) {
            log.error("PurchaseOrderServiceManager -> search purchase order vm got exception");
            if (responseMessage.message == null) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            }
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }

    public ResponseMessage getByID(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        CustomerReturnModel customerInvoiceModel = new CustomerReturnModel();


        try {
            customerInvoiceModel = Core.getRequestObject(requestMessage, CustomerReturnModel.class);

            responseMessage.responseObj = this.customerReturnBllManager.searchVMCustomerReturn(customerInvoiceModel);

            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
        } catch (Exception ex) {
            log.error("PurchaseOrderServiceManager -> search purchase order vm got exception");
            if (responseMessage.message == null) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            }
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }

    public ResponseMessage delete(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        CustomerReturnModel customerReturnModel = new CustomerReturnModel();

        try {
            customerReturnModel = Core.getRequestObject(requestMessage, CustomerReturnModel.class);

            responseMessage.responseObj = this.customerReturnBllManager.deleteCustomerReturn(customerReturnModel);

            if (Core.clientMessage.get().messageCode != null) {
                this.rollBack();
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.FAILED_TO_DELETE;
                } else {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
            } else {

                responseMessage = this.deletedRelatedInterModuleObject(customerReturnModel);
                if (responseMessage.responseCode == TillBoxAppConstant.FAILED_ERROR_CODE) {
                    this.rollBack();
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    responseMessage.message = MessageConstant.FAILED_TO_DELETE;
                } else {
                    responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                    responseMessage.message = MessageConstant.DELETED_SUCCESSFULLY;
                }
            }

        } catch (Exception ex) {
            log.error("PurchaseOrderServiceManager -> search purchase order vm got exception");
            if (responseMessage.message == null) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            }
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }

    public ResponseMessage deletedRelatedInterModuleObject(CustomerReturnModel customerReturnModel) {

        MqttClient mqttClientDeleteJournal;
        MqttClient mqttClientDeleteInventoryTransaction;

        CallBack callBackDeleteJournal = null;
        CallBack callBackDeleteInventoryTransaction = null;

        ResponseMessage responseMessage = new ResponseMessage();

        ResponseMessage responseMessageDeleteJournal;
        ResponseMessage responseMessageDeleteInventoryTransaction;

        boolean workCompleteWithInAllowTime;
        RequestMessage journalDeletedRequestMessage, inventoryTransactionDeletedRequestMessage;
        try {

            journalDeletedRequestMessage = Core.getDefaultWorkerRequestMessage();
            inventoryTransactionDeletedRequestMessage = Core.getDefaultWorkerRequestMessage();
            journalDeletedRequestMessage.token = Core.requestToken.get();
            inventoryTransactionDeletedRequestMessage.token = Core.requestToken.get();

            Object lockObject = new Object();

            String pubTopic = WorkerSubscriptionConstants.WORKER_ACCOUNTING_MODULE_TOPIC;
            String inventoryTopic = WorkerSubscriptionConstants.WORKER_INVENTORY_TOPIC;

            this.barrier = TillBoxUtils.getBarrier(2, lockObject); //new CyclicBarrier(2, new WorkersStatus(lockObject));


            JournalModel searchJournalModel = new JournalModel();
            searchJournalModel.setReferenceType(ReferenceType.CustomerReturn.get());
            searchJournalModel.setReferenceID(customerReturnModel.getCustomerReturnID());

            journalDeletedRequestMessage.requestObj = searchJournalModel;
            journalDeletedRequestMessage.brokerMessage.serviceName = "api/journal/delete";
            journalDeletedRequestMessage.token = Core.requestToken.get();

            SubscriberForWorker subForWorker = new SubscriberForWorker(journalDeletedRequestMessage.brokerMessage.messageId, this.barrier);

            mqttClientDeleteJournal = subForWorker.subscribe();
            callBackDeleteJournal = subForWorker.getCallBack();
            PublisherForWorker pubForWorkerGetUserList = new PublisherForWorker(pubTopic, mqttClientDeleteJournal);
            pubForWorkerGetUserList.publishedMessageToWorker(journalDeletedRequestMessage);


            InventoryTransactionModel inventoryTransactionModel = new InventoryTransactionModel();
            inventoryTransactionModel.setReferenceType(ReferenceType.CustomerReturn.get());
            inventoryTransactionModel.setReferenceID(customerReturnModel.getCustomerReturnID());

            inventoryTransactionDeletedRequestMessage.requestObj = inventoryTransactionModel;
            inventoryTransactionDeletedRequestMessage.brokerMessage.serviceName = "api/inventory/inventoryTransaction/delete";
            inventoryTransactionDeletedRequestMessage.token = Core.requestToken.get();

            SubscriberForWorker subForWorkerInventoryTransaction = new SubscriberForWorker(inventoryTransactionDeletedRequestMessage.brokerMessage.messageId, this.barrier);
            mqttClientDeleteInventoryTransaction = subForWorkerInventoryTransaction.subscribe();
            callBackDeleteInventoryTransaction = subForWorkerInventoryTransaction.getCallBack();
            PublisherForWorker pubForWorkerDeleteInventoryTransaction = new PublisherForWorker(inventoryTopic, mqttClientDeleteInventoryTransaction);
            pubForWorkerDeleteInventoryTransaction.publishedMessageToWorker(inventoryTransactionDeletedRequestMessage);


            synchronized (lockObject) {
                responseMessage = Core.buildDefaultResponseMessage();
                long startTime = System.nanoTime();
                lockObject.wait(this.allowedTime);
                workCompleteWithInAllowTime = this.isResponseWithInAllowedTime(startTime);

                if (workCompleteWithInAllowTime) {
                    responseMessageDeleteJournal = callBackDeleteJournal.getResponseMessage();
                    responseMessageDeleteInventoryTransaction = callBackDeleteInventoryTransaction.getResponseMessage();

                    responseMessage.message = "Inter module communication successful";
                    responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

                } else {
                    //timeout
                    log.info("Response time out");
                    log.info("RollBack checkInterCom Operation");

                    responseMessage.message = "Inter module communication Failed";
                    responseMessage.responseCode = TillBoxAppConstant.UN_PROCESSABLE_REQUEST;
                }
            }


            this.closeBrokerClient(mqttClientDeleteJournal, journalDeletedRequestMessage.brokerMessage.messageId);
            this.closeBrokerClient(mqttClientDeleteInventoryTransaction, inventoryTransactionDeletedRequestMessage.brokerMessage.messageId);

        } catch (Exception ex) {

            ex.printStackTrace();
            log.error("Exception from checkInterCom Module communication CustomerReturnServiceManager");
        }
        return responseMessage;
    }

}
