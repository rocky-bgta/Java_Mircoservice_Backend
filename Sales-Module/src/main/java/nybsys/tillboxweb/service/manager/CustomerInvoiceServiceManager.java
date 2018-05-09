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
import nybsys.tillboxweb.bll.manager.CustomerInvoiceBllManager;
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
import nybsys.tillboxweb.models.*;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CustomerInvoiceServiceManager extends BaseService {

    private static final Logger log = LoggerFactory.getLogger(CustomerInvoiceServiceManager.class);

    private CustomerInvoiceBllManager customerInvoiceBllManager = new CustomerInvoiceBllManager();

    private CustomerAdjustmentServiceManager customerAdjustmentServiceManager = new CustomerAdjustmentServiceManager();

    public ResponseMessage save(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        boolean isUpdateRequest = false;
        VMCustomerInvoice vmCustomerInvoice;
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

            vmCustomerInvoice = Core.getRequestObject(requestMessage, VMCustomerInvoice.class);
            /*Set<ConstraintViolation<AddressTypeModel>> violations = this.validator.validate(customerAddressTypeModel);
            if (violations.size() > 0) {
                responseMessage = this.buildResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            //(1)
            if (vmCustomerInvoice.customerInvoiceModel.getCustomerInvoiceID() == null || vmCustomerInvoice.customerInvoiceModel.getCustomerInvoiceID() > 0) {
                isUpdateRequest = true;
            }

            //add currency
            if (vmCustomerInvoice.customerInvoiceModel.getCustomerInvoiceID() == null || vmCustomerInvoice.customerInvoiceModel.getCustomerInvoiceID() == 0) {
                vmCustomerInvoice.customerInvoiceModel.setBaseCurrencyID(currencyModel.getCurrencyID());
                vmCustomerInvoice.customerInvoiceModel.setEntryCurrencyID(requestMessage.entryCurrencyID);
                vmCustomerInvoice.customerInvoiceModel.setBaseCurrencyAmount(vmCustomerInvoice.customerInvoiceModel.getTotalAmount() * vmCustomerInvoice.customerInvoiceModel.getExchangeRate());
            }
            this.customerInvoiceBllManager.saveCustomerInvoice(vmCustomerInvoice);

            List<JournalModel> lstJournalModel = new ArrayList<>();
            lstJournalModel = getJournalModelFromInvoice(vmCustomerInvoice, currencyModel,vmCustomerInvoice.customerInvoiceModel.getExchangeRate(), requestMessage.entryCurrencyID);
            VMJournalListModel vmJournalListModel = new VMJournalListModel();
            vmJournalListModel.lstJournalModel = lstJournalModel;
            requestMessage.requestObj = vmJournalListModel;


            if (Core.clientMessage.get().messageCode != null) {
                this.rollBack();
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.FAILED_TO_SAVE_CUSTOMER_INVOICE;
                } else {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
            } else {
                if (isUpdateRequest) {
                    responseMessage = this.checkInterComUpdate(requestMessage, vmCustomerInvoice);
                } else {
                    responseMessage = this.checkInterCom(requestMessage, vmCustomerInvoice);
                }


                if (responseMessage.responseCode != TillBoxAppConstant.SUCCESS_CODE) {
                    this.rollBack();
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    responseMessage.message = MessageConstant.FAILED_TO_SAVE_CUSTOMER_INVOICE;
                } else {
                    this.commit();
                    responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                    responseMessage.message = MessageConstant.CUSTOMER_INVOICE_SAVE_SUCCESSFULLY;
                }
            }

            responseMessage.responseObj = vmCustomerInvoice;
        } catch (Exception ex) {
            this.rollBack();
            log.error("CustomerInvoiceServiceManager -> Customer InvoiceService Manager got exception");
            responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            responseMessage.message = MessageConstant.FAILED_TO_SAVE_CUSTOMER_INVOICE;
//            if (responseMessage.message == null) {
//                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
//            }
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }

    private Double calculateCostOfGoodsSold(VMCustomerInvoice vmCustomerInvoice) {
        return vmCustomerInvoice.customerInvoiceModel.getTotalAmount();
    }

    private List<JournalModel> getJournalModelFromInvoice(VMCustomerInvoice vmCustomerInvoice, CurrencyModel currencyModel, Double exchangeRate, Integer entryCurrencyID) {
        List<JournalModel> lstJournalModel = new ArrayList<>();

        double costOfGoodsSold = calculateCostOfGoodsSold(vmCustomerInvoice);
        //Debit journal
        JournalModel journalModelDebit = new JournalModel();
        journalModelDebit.setBusinessID(vmCustomerInvoice.customerInvoiceModel.getBusinessID());
        journalModelDebit.setAmount(costOfGoodsSold);
        journalModelDebit.setAccountID(DefaultCOA.CostOfGoodsSold.get());
        journalModelDebit.setReferenceID(vmCustomerInvoice.customerInvoiceModel.getCustomerInvoiceID());
        journalModelDebit.setReferenceType(ReferenceType.CustomerInvoice.get());
        journalModelDebit.setDrCrIndicator(DebitCreditIndicator.Debit.get());
        journalModelDebit.setDate(new Date());

        journalModelDebit.setBaseCurrencyID(currencyModel.getCurrencyID());
        journalModelDebit.setEntryCurrencyID(entryCurrencyID);
        journalModelDebit.setExchangeRate(exchangeRate);
        journalModelDebit.setBaseCurrencyAmount(costOfGoodsSold * exchangeRate);

        lstJournalModel.add(journalModelDebit);

        //Credit journal
        JournalModel journalModelCredit = new JournalModel();
        journalModelCredit.setBusinessID(vmCustomerInvoice.customerInvoiceModel.getBusinessID());
        journalModelCredit.setAmount(costOfGoodsSold);
        journalModelCredit.setAccountID(DefaultCOA.Inventory.get());
        journalModelCredit.setReferenceID(vmCustomerInvoice.customerInvoiceModel.getCustomerInvoiceID());
        journalModelCredit.setReferenceType(ReferenceType.CustomerInvoice.get());
        journalModelCredit.setDrCrIndicator(DebitCreditIndicator.Credit.get());
        journalModelCredit.setDate(new Date());

        journalModelCredit.setBaseCurrencyID(currencyModel.getCurrencyID());
        journalModelCredit.setEntryCurrencyID(entryCurrencyID);
        journalModelCredit.setExchangeRate(exchangeRate);
        journalModelCredit.setBaseCurrencyAmount(costOfGoodsSold * exchangeRate);

        lstJournalModel.add(journalModelCredit);


        //Debit journal
        journalModelDebit = new JournalModel();
        journalModelDebit.setBusinessID(vmCustomerInvoice.customerInvoiceModel.getBusinessID());
        journalModelDebit.setAmount(vmCustomerInvoice.customerInvoiceModel.getTotalAmount());
        journalModelDebit.setPartyID(vmCustomerInvoice.customerInvoiceModel.getCustomerID());
        journalModelDebit.setPartyType(PartyType.Customer.get());
        journalModelDebit.setAccountID(DefaultCOA.AccountReceivable.get());
        journalModelDebit.setReferenceID(vmCustomerInvoice.customerInvoiceModel.getCustomerInvoiceID());
        journalModelDebit.setReferenceType(ReferenceType.CustomerInvoice.get());
        journalModelDebit.setDrCrIndicator(DebitCreditIndicator.Debit.get());
        journalModelDebit.setDate(new Date());

        journalModelDebit.setBaseCurrencyID(currencyModel.getCurrencyID());
        journalModelDebit.setEntryCurrencyID(entryCurrencyID);
        journalModelDebit.setExchangeRate(exchangeRate);
        journalModelDebit.setBaseCurrencyAmount(vmCustomerInvoice.customerInvoiceModel.getTotalAmount() * exchangeRate);

        lstJournalModel.add(journalModelDebit);

        //Credit journal
        journalModelCredit = new JournalModel();
        journalModelCredit.setBusinessID(vmCustomerInvoice.customerInvoiceModel.getBusinessID());
        journalModelCredit.setAmount(vmCustomerInvoice.customerInvoiceModel.getTotalAmount());
        journalModelCredit.setAccountID(DefaultCOA.Sales.get());
        journalModelCredit.setReferenceID(vmCustomerInvoice.customerInvoiceModel.getCustomerInvoiceID());
        journalModelCredit.setReferenceType(ReferenceType.CustomerInvoice.get());
        journalModelCredit.setDrCrIndicator(DebitCreditIndicator.Credit.get());
        journalModelCredit.setDate(new Date());

        journalModelCredit.setBaseCurrencyID(currencyModel.getCurrencyID());
        journalModelCredit.setEntryCurrencyID(entryCurrencyID);
        journalModelCredit.setExchangeRate(exchangeRate);
        journalModelCredit.setBaseCurrencyAmount(vmCustomerInvoice.customerInvoiceModel.getTotalAmount() * exchangeRate);

        lstJournalModel.add(journalModelCredit);


        if (vmCustomerInvoice.customerInvoiceModel.getTotalDiscount() > 0) {
            //Debit journal
            journalModelDebit = new JournalModel();
            journalModelDebit.setBusinessID(vmCustomerInvoice.customerInvoiceModel.getBusinessID());
            journalModelDebit.setAmount(vmCustomerInvoice.customerInvoiceModel.getTotalDiscount());
            journalModelDebit.setAccountID(DefaultCOA.DiscountGiven.get());
            journalModelDebit.setReferenceID(vmCustomerInvoice.customerInvoiceModel.getCustomerInvoiceID());
            journalModelDebit.setReferenceType(ReferenceType.CustomerInvoice.get());
            journalModelDebit.setDrCrIndicator(DebitCreditIndicator.Debit.get());
            journalModelDebit.setDate(new Date());

            journalModelDebit.setBaseCurrencyID(currencyModel.getCurrencyID());
            journalModelDebit.setEntryCurrencyID(entryCurrencyID);
            journalModelDebit.setExchangeRate(exchangeRate);
            journalModelDebit.setBaseCurrencyAmount(vmCustomerInvoice.customerInvoiceModel.getTotalDiscount() * exchangeRate);

            lstJournalModel.add(journalModelDebit);

            //Credit journal
            journalModelCredit = new JournalModel();
            journalModelCredit.setBusinessID(vmCustomerInvoice.customerInvoiceModel.getBusinessID());
            journalModelCredit.setAmount(vmCustomerInvoice.customerInvoiceModel.getTotalDiscount());
            journalModelCredit.setAccountID(DefaultCOA.AccountReceivable.get());
            journalModelCredit.setPartyID(vmCustomerInvoice.customerInvoiceModel.getCustomerID());
            journalModelCredit.setPartyType(PartyType.Customer.get());
            journalModelCredit.setReferenceID(vmCustomerInvoice.customerInvoiceModel.getCustomerInvoiceID());
            journalModelCredit.setReferenceType(ReferenceType.CustomerInvoice.get());
            journalModelCredit.setDrCrIndicator(DebitCreditIndicator.Credit.get());
            journalModelCredit.setDate(new Date());

            journalModelCredit.setBaseCurrencyID(currencyModel.getCurrencyID());
            journalModelCredit.setEntryCurrencyID(entryCurrencyID);
            journalModelCredit.setExchangeRate(exchangeRate);
            journalModelCredit.setBaseCurrencyAmount(vmCustomerInvoice.customerInvoiceModel.getTotalDiscount() * exchangeRate);

            lstJournalModel.add(journalModelCredit);
        }

        if (vmCustomerInvoice.customerInvoiceModel.getTotalVAT() > 0) {
            //Debit journal
            journalModelDebit = new JournalModel();
            journalModelDebit.setBusinessID(vmCustomerInvoice.customerInvoiceModel.getBusinessID());
            journalModelDebit.setAmount(vmCustomerInvoice.customerInvoiceModel.getTotalVAT());
            journalModelDebit.setPartyID(vmCustomerInvoice.customerInvoiceModel.getCustomerID());
            journalModelDebit.setPartyType(PartyType.Customer.get());
            journalModelDebit.setAccountID(DefaultCOA.AccountReceivable.get());
            journalModelDebit.setReferenceID(vmCustomerInvoice.customerInvoiceModel.getCustomerInvoiceID());
            journalModelDebit.setReferenceType(ReferenceType.CustomerInvoice.get());
            journalModelDebit.setDrCrIndicator(DebitCreditIndicator.Debit.get());
            journalModelDebit.setDate(new Date());

            journalModelDebit.setBaseCurrencyID(currencyModel.getCurrencyID());
            journalModelDebit.setEntryCurrencyID(entryCurrencyID);
            journalModelDebit.setExchangeRate(exchangeRate);
            journalModelDebit.setBaseCurrencyAmount(vmCustomerInvoice.customerInvoiceModel.getTotalVAT() * exchangeRate);

            lstJournalModel.add(journalModelDebit);

            //Credit journal
            journalModelCredit = new JournalModel();
            journalModelCredit.setBusinessID(vmCustomerInvoice.customerInvoiceModel.getBusinessID());
            journalModelCredit.setAmount(vmCustomerInvoice.customerInvoiceModel.getTotalVAT());
            journalModelCredit.setAccountID(DefaultCOA.SalesVAT.get());
            journalModelCredit.setReferenceID(vmCustomerInvoice.customerInvoiceModel.getCustomerInvoiceID());
            journalModelCredit.setReferenceType(ReferenceType.CustomerInvoice.get());
            journalModelCredit.setDrCrIndicator(DebitCreditIndicator.Credit.get());
            journalModelCredit.setDate(new Date());

            journalModelCredit.setBaseCurrencyID(currencyModel.getCurrencyID());
            journalModelCredit.setEntryCurrencyID(entryCurrencyID);
            journalModelCredit.setExchangeRate(exchangeRate);
            journalModelCredit.setBaseCurrencyAmount(vmCustomerInvoice.customerInvoiceModel.getTotalVAT() * exchangeRate);

            lstJournalModel.add(journalModelCredit);
        }


        return lstJournalModel;
    }


    public ResponseMessage search(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        CustomerInvoiceModel customerInvoiceModel = new CustomerInvoiceModel();


        try {
            customerInvoiceModel = Core.getRequestObject(requestMessage, CustomerInvoiceModel.class);
            List<VMCustomerInvoice> lstVMCustomerInvoice = new ArrayList<>();
            lstVMCustomerInvoice = this.customerInvoiceBllManager.searchVMCustomerInvoice(customerInvoiceModel);
            responseMessage.responseObj = lstVMCustomerInvoice;
            if (lstVMCustomerInvoice.size() > 0) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.NO_DATA_FOUND;
            }

        } catch (Exception ex) {
            log.error("PurchaseOrderServiceManager -> search purchase order vm got exception");
            if (responseMessage.message == null) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            }
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }

    public ResponseMessage getByID(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        CustomerInvoiceModel customerInvoiceModel = new CustomerInvoiceModel();


        try {
            customerInvoiceModel = Core.getRequestObject(requestMessage, CustomerInvoiceModel.class);
            List<VMCustomerInvoice> lstVMCustomerInvoice = new ArrayList<>();
            lstVMCustomerInvoice = this.customerInvoiceBllManager.searchVMCustomerInvoice(customerInvoiceModel);
            responseMessage.responseObj = lstVMCustomerInvoice;
            if (lstVMCustomerInvoice.size() > 0) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.NO_DATA_FOUND;
            }

        } catch (Exception ex) {
            log.error("PurchaseOrderServiceManager -> search purchase order vm got exception");
            if (responseMessage.message == null) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            }
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }


    public ResponseMessage callInterModuelfunction(String apiurl, String topicName, RequestMessage reqMessForWorker) {
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
            this.WriteExceptionLog(ex);
        }


        return responseMessage;
    }


    public ResponseMessage checkInterComUpdate(RequestMessage requestMessage, VMCustomerInvoice vmCustomerInvoice) {

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


            String accountingTopic = WorkerSubscriptionConstants.WORKER_ACCOUNTING_MODULE_TOPIC;
            String inventoryTopic = WorkerSubscriptionConstants.WORKER_INVENTORY_TOPIC;

            this.barrier = TillBoxUtils.getBarrier(4, lockObject);

            //======================= Start of one ===========================
            JournalModel searchJournalModel = new JournalModel();
            searchJournalModel.setReferenceType(ReferenceType.CustomerInvoice.get());
            searchJournalModel.setReferenceID(vmCustomerInvoice.customerInvoiceModel.getCustomerInvoiceID());

            journalDeletedRequestMessage.requestObj = searchJournalModel;
            journalDeletedRequestMessage.brokerMessage.serviceName = "api/journal/delete";
            SubscriberForWorker subForWorker = new SubscriberForWorker(journalDeletedRequestMessage.brokerMessage.messageId, this.barrier);
            mqttClientDeleteJournal = subForWorker.subscribe();
            callBackDeleteJournal = subForWorker.getCallBack();
            PublisherForWorker pubForWorkerGetUserList = new PublisherForWorker(accountingTopic, mqttClientDeleteJournal);
            pubForWorkerGetUserList.publishedMessageToWorker(journalDeletedRequestMessage);
            //======================= End of one ===========================

            //======================= Start of two ===========================
            InventoryTransactionModel inventoryTransactionModel = new InventoryTransactionModel();
            inventoryTransactionModel.setReferenceType(ReferenceType.CustomerInvoice.get());
            inventoryTransactionModel.setReferenceID(vmCustomerInvoice.customerInvoiceModel.getCustomerInvoiceID());

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
            SubscriberForWorker journalSubForWorker = new SubscriberForWorker(reqJournalSaveMessage.brokerMessage.messageId, this.barrier);
            mqttClientSaveJournal = journalSubForWorker.subscribe();
            callBackSaveJournal = journalSubForWorker.getCallBack();
            PublisherForWorker journalPubForWorkerGetUserList = new PublisherForWorker(accountingTopic, mqttClientSaveJournal);
            journalPubForWorkerGetUserList.publishedMessageToWorker(reqJournalSaveMessage);
            //======================= End of three ===========================

            VMInventoryTransaction vmInventoryTransaction = new VMInventoryTransaction();
            vmInventoryTransaction.lstInventoryTransactionModel = this.getInventoryTransactionModels(vmCustomerInvoice);


            //======================= Start of four ===========================

            reqObjInventoryTransactionMessage.requestObj = vmInventoryTransaction;
            reqObjInventoryTransactionMessage.brokerMessage.serviceName = "api/inventory/inventoryTransaction/Save";
            SubscriberForWorker subForWorker1 = new SubscriberForWorker(reqObjInventoryTransactionMessage.brokerMessage.messageId, this.barrier);
            mqttClientSaveInventoryTransaction = subForWorker1.subscribe();
            callBackInventoryTransaction = subForWorker1.getCallBack();
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
                    responseMessageDeleteInventoryTransaction = callBackDeleteInventoryTransaction.getResponseMessage();

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

    private List<InventoryTransactionModel> getInventoryTransactionModels(VMCustomerInvoice vmCustomerInvoice) {
        List<InventoryTransactionModel> lstInventoryTransactionModel = new ArrayList<>();
        for (CustomerInvoiceDetailModel customerInvoiceDetailModel : vmCustomerInvoice.lstCustomerInvoiceDetailModel) {

            InventoryTransactionModel inventoryTransactionModel = new InventoryTransactionModel();
            inventoryTransactionModel.setBusinessID(vmCustomerInvoice.customerInvoiceModel.getBusinessID());
            inventoryTransactionModel.setProductID(customerInvoiceDetailModel.getProductID());
            inventoryTransactionModel.setOutQuantity(customerInvoiceDetailModel.getQuantity());
            inventoryTransactionModel.setReferenceID(vmCustomerInvoice.customerInvoiceModel.getCustomerInvoiceID());
            inventoryTransactionModel.setReferenceType(ReferenceType.CustomerInvoice.get());
            lstInventoryTransactionModel.add(inventoryTransactionModel);
        }
        return lstInventoryTransactionModel;
    }

    public ResponseMessage checkInterCom(RequestMessage requestMessage, VMCustomerInvoice vmCustomerInvoice) {

        MqttClient mqttClientSaveJournal;
        MqttClient mqttClientSaveInventoryTransaction;

        CallBack callBackSaveJournal;
        CallBack callBackInventoryTransaction;
        //CallBack callBackDeleteJournal = null;


        ResponseMessage responseMessage;// = new ResponseMessage();
        ResponseMessage responseMessageSaveJournal;
        ResponseMessage responseMessageSaveInventoryTransaction;


        RequestMessage reqJournalSaveMessage, reqObjInventoryTransactionMessage;
        boolean workCompleteWithInAllowTime;
        try {

            Object lockObject = new Object();

            reqJournalSaveMessage = Core.getDefaultWorkerRequestMessage();
            reqObjInventoryTransactionMessage = Core.getDefaultWorkerRequestMessage();
            reqJournalSaveMessage.token = requestMessage.token;
            reqObjInventoryTransactionMessage.token = requestMessage.token;


            String accountingTopic = WorkerSubscriptionConstants.WORKER_ACCOUNTING_MODULE_TOPIC;
            String inventoryTopic = WorkerSubscriptionConstants.WORKER_INVENTORY_TOPIC;

            this.barrier = TillBoxUtils.getBarrier(2, lockObject);


            VMInventoryTransaction vmInventoryTransaction = new VMInventoryTransaction();
            vmInventoryTransaction.lstInventoryTransactionModel = this.getInventoryTransactionModels(vmCustomerInvoice);


            reqJournalSaveMessage.requestObj = requestMessage.requestObj;
            reqJournalSaveMessage.brokerMessage.serviceName = "api/journal/save";
            SubscriberForWorker subForWorker = new SubscriberForWorker(reqJournalSaveMessage.brokerMessage.messageId, this.barrier);
            mqttClientSaveJournal = subForWorker.subscribe();
            callBackSaveJournal = subForWorker.getCallBack();
            PublisherForWorker pubForWorkerGetJournalList = new PublisherForWorker(accountingTopic, mqttClientSaveJournal);
            pubForWorkerGetJournalList.publishedMessageToWorker(reqJournalSaveMessage);


            reqObjInventoryTransactionMessage.requestObj = vmInventoryTransaction;
            reqObjInventoryTransactionMessage.brokerMessage.serviceName = "api/inventory/inventoryTransaction/Save";
            SubscriberForWorker subForWorkerForInventory = new SubscriberForWorker(reqObjInventoryTransactionMessage.brokerMessage.messageId, this.barrier);
            mqttClientSaveInventoryTransaction = subForWorkerForInventory.subscribe();
            callBackInventoryTransaction = subForWorkerForInventory.getCallBack();
            PublisherForWorker pubForWorkerSaveUser = new PublisherForWorker(inventoryTopic, mqttClientSaveInventoryTransaction);
            pubForWorkerSaveUser.publishedMessageToWorker(reqObjInventoryTransactionMessage);


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


            this.closeBrokerClient(mqttClientSaveJournal, reqJournalSaveMessage.brokerMessage.messageId);
            this.closeBrokerClient(mqttClientSaveInventoryTransaction, reqObjInventoryTransactionMessage.brokerMessage.messageId);


        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Exception from checkInterCom Module communication UserServiceManager");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
        }
        return responseMessage;
    }


    public ResponseMessage customerStatement(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        CustomerModel customerModel = new CustomerModel();
        List<VMCustomerStatementModel> lstVMCustomerStatementModel = new ArrayList<>();

        StatementSearchModel statementSearchModel = new StatementSearchModel();

        try {
            statementSearchModel = Core.getRequestObject(requestMessage, StatementSearchModel.class);

            lstVMCustomerStatementModel = this.customerInvoiceBllManager.getCustomerStatement(statementSearchModel);
            responseMessage.responseObj = lstVMCustomerStatementModel;
            if (lstVMCustomerStatementModel.size() > 0) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.NO_DATA_FOUND;
            }

        } catch (Exception ex) {
            log.error("PurchaseOrderServiceManager -> search purchase order vm got exception");
            if (responseMessage.message == null) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            }
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }


    public ResponseMessage getCustomerOutstanding(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        CustomerModel customerModel = new CustomerModel();
        Double outstandingAmount;

        try {
            customerModel = Core.getRequestObject(requestMessage, CustomerModel.class);

            outstandingAmount = this.customerInvoiceBllManager.getPartyBalance(PartyType.Customer.get(), customerModel.getCustomerID(), customerModel.getBusinessID());
            responseMessage.responseObj = outstandingAmount;
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

        } catch (Exception ex) {
            log.error("PurchaseOrderServiceManager -> search purchase order vm got exception");
            if (responseMessage.message == null) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            }
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }


    public ResponseMessage delete(RequestMessage requestMessage) {

        ResponseMessage responseMessage = new ResponseMessage();
        CustomerInvoiceModel customerInvoiceModel = new CustomerInvoiceModel();
        try {
            customerInvoiceModel = Core.getRequestObject(requestMessage, CustomerInvoiceModel.class);
            customerInvoiceModel = this.customerInvoiceBllManager.deleteCustomerInvoice(customerInvoiceModel);

            responseMessage.responseObj = customerInvoiceModel;
            if (Core.clientMessage.get().messageCode != null) {
                this.rollBack();
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.FAILED_TO_DELETE;
                } else {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
            } else {

                responseMessage = this.deletedRelatedInterModuleObject(customerInvoiceModel);

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
            log.error("CustomerInvoiceServiceManager -> customer invoice delete got exception");
            if (responseMessage.message == null) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            }
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }

    public ResponseMessage deletedRelatedInterModuleObject(CustomerInvoiceModel customerInvoiceModel) {

        MqttClient mqttClientDeleteJournal;
        MqttClient mqttClientDeleteInventoryTransaction;

        CallBack callBackDeleteJournal = null;
        CallBack callBackDeleteInventoryTransaction = null;

        ResponseMessage responseMessage = new ResponseMessage();

        ResponseMessage responseMessageDeleteJournal;
        ResponseMessage responseMessageDeleteInventoryTransaction;

        RequestMessage journalDeletedRequestMessage, inventoryTransactionDeletedRequestMessage;
        boolean workCompleteWithInAllowTime;
        try {

            Object lockObject = new Object();

            journalDeletedRequestMessage = Core.getDefaultWorkerRequestMessage();
            inventoryTransactionDeletedRequestMessage = Core.getDefaultWorkerRequestMessage();
            journalDeletedRequestMessage.token = Core.requestToken.get();
            inventoryTransactionDeletedRequestMessage.token = Core.requestToken.get();

            String accountingTopic = WorkerSubscriptionConstants.WORKER_ACCOUNTING_MODULE_TOPIC;
            String inventoryTopic = WorkerSubscriptionConstants.WORKER_INVENTORY_TOPIC;

            this.barrier = TillBoxUtils.getBarrier(2, lockObject); //new CyclicBarrier(2, new WorkersStatus(lockObject));


            JournalModel searchJournalModel = new JournalModel();
            searchJournalModel.setReferenceType(ReferenceType.CustomerInvoice.get());
            searchJournalModel.setReferenceID(customerInvoiceModel.getCustomerInvoiceID());
            journalDeletedRequestMessage.token = Core.requestToken.get();
            journalDeletedRequestMessage.requestObj = searchJournalModel;
            journalDeletedRequestMessage.brokerMessage.serviceName = "api/journal/delete";
            SubscriberForWorker subForWorker = new SubscriberForWorker(journalDeletedRequestMessage.brokerMessage.messageId, this.barrier);

            mqttClientDeleteJournal = subForWorker.subscribe();
            callBackDeleteJournal = subForWorker.getCallBack();
            PublisherForWorker pubForWorkerGetUserList = new PublisherForWorker(accountingTopic, mqttClientDeleteJournal);
            pubForWorkerGetUserList.publishedMessageToWorker(journalDeletedRequestMessage);

            InventoryTransactionModel inventoryTransactionModel = new InventoryTransactionModel();
            inventoryTransactionModel.setReferenceType(ReferenceType.CustomerInvoice.get());
            inventoryTransactionModel.setReferenceID(customerInvoiceModel.getCustomerInvoiceID());
            inventoryTransactionDeletedRequestMessage.requestObj = inventoryTransactionModel;
            inventoryTransactionDeletedRequestMessage.token = Core.requestToken.get();
            inventoryTransactionDeletedRequestMessage.brokerMessage.serviceName = "api/inventory/inventoryTransaction/delete";
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
            log.error("Exception from checkInterCom Module communication CustomerInvoiceServiceManager");
        }
        return responseMessage;
    }

}
