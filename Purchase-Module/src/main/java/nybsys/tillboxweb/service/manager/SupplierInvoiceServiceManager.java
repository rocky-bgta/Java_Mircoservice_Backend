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
import nybsys.tillboxweb.bll.manager.SupplierInvoiceBllManager;
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
public class SupplierInvoiceServiceManager extends BaseService {

    private static final Logger log = LoggerFactory.getLogger(SupplierInvoiceServiceManager.class);

    private SupplierInvoiceBllManager supplierInvoiceBllManager = new SupplierInvoiceBllManager();

    private SupplierAdjustmentServiceManager supplierAdjustmentServiceManager = new SupplierAdjustmentServiceManager();

    public ResponseMessage save(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        boolean isUpdateRequest = false;
        VMSupplierInvoice vmSupplierInvoice ;
        CurrencyModel currencyModel;

        try {
            //get base currency and exchange rate
            currencyModel = this.supplierAdjustmentServiceManager.getBaseCurrency();
            if(currencyModel == null){
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = CurrencyConstant.BASE_CURRENT_NOT_FOUND;
                return responseMessage;
            }

            //check entry currency is present if not base currency will be entry currency
            if(requestMessage.entryCurrencyID == null || requestMessage.entryCurrencyID == 0)
            {
                requestMessage.entryCurrencyID = currencyModel.getCurrencyID();
            }

            vmSupplierInvoice = Core.getRequestObject(requestMessage, VMSupplierInvoice.class);
            /*Set<ConstraintViolation<AddressTypeModel>> violations = this.validator.validate(supplierAddressTypeModel);
            if (violations.size() > 0) {
                responseMessage = this.buildResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            //(1)
            if (vmSupplierInvoice.supplierInvoiceModel.getSupplierInvoiceID() == null || vmSupplierInvoice.supplierInvoiceModel.getSupplierInvoiceID() > 0) {
                isUpdateRequest = true;
            }

            //add currency
            if (vmSupplierInvoice.supplierInvoiceModel.getSupplierInvoiceID() == null || vmSupplierInvoice.supplierInvoiceModel.getSupplierInvoiceID() == 0) {
                vmSupplierInvoice.supplierInvoiceModel.setBaseCurrencyID(currencyModel.getCurrencyID());
                vmSupplierInvoice.supplierInvoiceModel.setEntryCurrencyID(requestMessage.entryCurrencyID);
                vmSupplierInvoice.supplierInvoiceModel.setBaseCurrencyAmount(vmSupplierInvoice.supplierInvoiceModel.getTotalAmount()* vmSupplierInvoice.supplierInvoiceModel.getExchangeRate());
            }
            this.supplierInvoiceBllManager.saveSupplierInvoice(vmSupplierInvoice);

            List<JournalModel> lstJournalModel = new ArrayList<>();
            lstJournalModel = getJournalModelFromInvoice(vmSupplierInvoice,currencyModel,requestMessage.entryCurrencyID);
            VMJournalListModel vmJournalListModel = new VMJournalListModel();
            vmJournalListModel.lstJournalModel = lstJournalModel;
            requestMessage.requestObj = vmJournalListModel;


            if (Core.clientMessage.get().messageCode != null) {
                this.rollBack();
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.SUPPLIER_INVOICE_SAVE_FAILED;
                } else {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
            } else {
                if (isUpdateRequest) {
                    responseMessage = this.checkInterComUpdate(requestMessage, vmSupplierInvoice);
                } else {
                    responseMessage = this.checkInterCom(requestMessage, vmSupplierInvoice);
                }

                if (responseMessage.responseCode != TillBoxAppConstant.SUCCESS_CODE) {
                    this.rollBack();
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    responseMessage.message = MessageConstant.SUPPLIER_INVOICE_SAVE_FAILED;
                } else {
                    this.commit();
                    responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                    responseMessage.message = MessageConstant.SUPPLIER_INVOICE_SAVE_SUCCESSFULLY;
                }
            }

            responseMessage.responseObj = vmSupplierInvoice;
        } catch (Exception ex) {
            this.rollBack();
            log.error("SupplierInvoiceServiceManager -> Supplier InvoiceService Manager got exception");
            responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            responseMessage.message = MessageConstant.SUPPLIER_INVOICE_SAVE_FAILED;
//            if (responseMessage.message == null) {
//                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
//            }
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }


    private List<JournalModel> getJournalModelFromInvoice(VMSupplierInvoice vmSupplierInvoice,CurrencyModel currencyModel, Integer entryCurrencyID) {
        List<JournalModel> lstJournalModel = new ArrayList<>();

        //Debit journal
        JournalModel journalModelDebit = new JournalModel();
        journalModelDebit.setBusinessID(vmSupplierInvoice.supplierInvoiceModel.getBusinessID());
        journalModelDebit.setAmount(vmSupplierInvoice.supplierInvoiceModel.getTotalAmount());
        journalModelDebit.setAccountID(DefaultCOA.Inventory.get());
        journalModelDebit.setReferenceID(vmSupplierInvoice.supplierInvoiceModel.getSupplierInvoiceID());
        journalModelDebit.setReferenceType(ReferenceType.SupplierInvoice.get());
        journalModelDebit.setDrCrIndicator(DebitCreditIndicator.Debit.get());
        journalModelDebit.setDate(new Date());

        journalModelDebit.setBaseCurrencyID(currencyModel.getCurrencyID());
        journalModelDebit.setEntryCurrencyID(entryCurrencyID);
        journalModelDebit.setExchangeRate(vmSupplierInvoice.supplierInvoiceModel.getExchangeRate());
        journalModelDebit.setBaseCurrencyAmount(vmSupplierInvoice.supplierInvoiceModel.getTotalAmount()*vmSupplierInvoice.supplierInvoiceModel.getExchangeRate());

        lstJournalModel.add(journalModelDebit);
        //Credit journal
        JournalModel journalModelCredit = new JournalModel();
        journalModelCredit.setBusinessID(vmSupplierInvoice.supplierInvoiceModel.getBusinessID());
        journalModelCredit.setAmount(vmSupplierInvoice.supplierInvoiceModel.getTotalAmount());
        journalModelCredit.setPartyID(vmSupplierInvoice.supplierInvoiceModel.getSupplierID());
        journalModelCredit.setPartyType(PartyType.Supplier.get());
        journalModelCredit.setAccountID(DefaultCOA.AccountPayable.get());
        journalModelCredit.setReferenceID(vmSupplierInvoice.supplierInvoiceModel.getSupplierInvoiceID());
        journalModelCredit.setReferenceType(ReferenceType.SupplierInvoice.get());
        journalModelCredit.setDrCrIndicator(DebitCreditIndicator.Credit.get());
        journalModelCredit.setDate(new Date());

        journalModelCredit.setBaseCurrencyID(currencyModel.getCurrencyID());
        journalModelCredit.setEntryCurrencyID(entryCurrencyID);
        journalModelCredit.setExchangeRate(vmSupplierInvoice.supplierInvoiceModel.getExchangeRate());
        journalModelCredit.setBaseCurrencyAmount(vmSupplierInvoice.supplierInvoiceModel.getTotalAmount()*vmSupplierInvoice.supplierInvoiceModel.getExchangeRate());

        lstJournalModel.add(journalModelCredit);

        if (vmSupplierInvoice.supplierInvoiceModel.getTotalDiscount() > 0) {
            //Debit journal
            journalModelDebit = new JournalModel();
            journalModelDebit.setBusinessID(vmSupplierInvoice.supplierInvoiceModel.getBusinessID());
            journalModelDebit.setAmount(vmSupplierInvoice.supplierInvoiceModel.getTotalDiscount());
            journalModelDebit.setAccountID(DefaultCOA.AccountPayable.get());
            journalModelDebit.setPartyID(vmSupplierInvoice.supplierInvoiceModel.getSupplierID());
            journalModelDebit.setPartyType(PartyType.Supplier.get());
            journalModelDebit.setReferenceID(vmSupplierInvoice.supplierInvoiceModel.getSupplierInvoiceID());
            journalModelDebit.setReferenceType(ReferenceType.SupplierInvoice.get());
            journalModelDebit.setDrCrIndicator(DebitCreditIndicator.Debit.get());
            journalModelDebit.setDate(new Date());

            journalModelDebit.setBaseCurrencyID(currencyModel.getCurrencyID());
            journalModelDebit.setEntryCurrencyID(entryCurrencyID);
            journalModelDebit.setExchangeRate(vmSupplierInvoice.supplierInvoiceModel.getExchangeRate());
            journalModelDebit.setBaseCurrencyAmount(vmSupplierInvoice.supplierInvoiceModel.getTotalDiscount()*vmSupplierInvoice.supplierInvoiceModel.getExchangeRate());

            lstJournalModel.add(journalModelDebit);
            //Credit journal
            journalModelCredit = new JournalModel();
            journalModelCredit.setBusinessID(vmSupplierInvoice.supplierInvoiceModel.getBusinessID());
            journalModelCredit.setAmount(vmSupplierInvoice.supplierInvoiceModel.getTotalDiscount());
            journalModelCredit.setAccountID(DefaultCOA.DiscountEarn.get());
            journalModelCredit.setReferenceID(vmSupplierInvoice.supplierInvoiceModel.getSupplierInvoiceID());
            journalModelCredit.setReferenceType(ReferenceType.SupplierInvoice.get());
            journalModelCredit.setDrCrIndicator(DebitCreditIndicator.Credit.get());
            journalModelCredit.setDate(new Date());

            journalModelCredit.setBaseCurrencyID(currencyModel.getCurrencyID());
            journalModelCredit.setEntryCurrencyID(entryCurrencyID);
            journalModelCredit.setExchangeRate(vmSupplierInvoice.supplierInvoiceModel.getExchangeRate());
            journalModelCredit.setBaseCurrencyAmount(vmSupplierInvoice.supplierInvoiceModel.getTotalDiscount()*vmSupplierInvoice.supplierInvoiceModel.getExchangeRate());

            lstJournalModel.add(journalModelCredit);
        }

        if (vmSupplierInvoice.supplierInvoiceModel.getTotalVAT() > 0) {
            //Debit journal
            journalModelDebit = new JournalModel();
            journalModelDebit.setBusinessID(vmSupplierInvoice.supplierInvoiceModel.getBusinessID());
            journalModelDebit.setAmount(vmSupplierInvoice.supplierInvoiceModel.getTotalVAT());
            journalModelDebit.setAccountID(DefaultCOA.PurchaseVAT.get());
            journalModelDebit.setReferenceID(vmSupplierInvoice.supplierInvoiceModel.getSupplierInvoiceID());
            journalModelDebit.setReferenceType(ReferenceType.SupplierInvoice.get());
            journalModelDebit.setDrCrIndicator(DebitCreditIndicator.Debit.get());
            journalModelDebit.setDate(new Date());

            journalModelDebit.setBaseCurrencyID(currencyModel.getCurrencyID());
            journalModelDebit.setEntryCurrencyID(entryCurrencyID);
            journalModelDebit.setExchangeRate(vmSupplierInvoice.supplierInvoiceModel.getExchangeRate());
            journalModelDebit.setBaseCurrencyAmount(vmSupplierInvoice.supplierInvoiceModel.getTotalVAT()*vmSupplierInvoice.supplierInvoiceModel.getExchangeRate());

            lstJournalModel.add(journalModelDebit);
            //Credit journal
            journalModelCredit = new JournalModel();
            journalModelCredit.setBusinessID(vmSupplierInvoice.supplierInvoiceModel.getBusinessID());
            journalModelCredit.setAmount(vmSupplierInvoice.supplierInvoiceModel.getTotalVAT());
            journalModelCredit.setPartyID(vmSupplierInvoice.supplierInvoiceModel.getSupplierID());
            journalModelCredit.setPartyType(PartyType.Supplier.get());
            journalModelCredit.setAccountID(DefaultCOA.AccountPayable.get());
            journalModelCredit.setReferenceID(vmSupplierInvoice.supplierInvoiceModel.getSupplierInvoiceID());
            journalModelCredit.setReferenceType(ReferenceType.SupplierInvoice.get());
            journalModelCredit.setDrCrIndicator(DebitCreditIndicator.Credit.get());
            journalModelCredit.setDate(new Date());

            journalModelCredit.setBaseCurrencyID(currencyModel.getCurrencyID());
            journalModelCredit.setEntryCurrencyID(entryCurrencyID);
            journalModelCredit.setExchangeRate(vmSupplierInvoice.supplierInvoiceModel.getExchangeRate());
            journalModelCredit.setBaseCurrencyAmount(vmSupplierInvoice.supplierInvoiceModel.getTotalVAT()*vmSupplierInvoice.supplierInvoiceModel.getExchangeRate());

            lstJournalModel.add(journalModelCredit);
        }


        return lstJournalModel;
    }


    public ResponseMessage search(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        SupplierInvoiceModel supplierInvoiceModel = new SupplierInvoiceModel();


        try {
            supplierInvoiceModel = Core.getRequestObject(requestMessage, SupplierInvoiceModel.class);
            List<VMSupplierInvoice> lstVMSupplierInvoice = new ArrayList<>();
            lstVMSupplierInvoice = this.supplierInvoiceBllManager.searchVMSupplierInvoice(supplierInvoiceModel);
            responseMessage.responseObj = lstVMSupplierInvoice;
            if (lstVMSupplierInvoice.size() > 0) {
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
        SupplierInvoiceModel supplierInvoiceModel = new SupplierInvoiceModel();


        try {
            supplierInvoiceModel = Core.getRequestObject(requestMessage, SupplierInvoiceModel.class);
            List<VMSupplierInvoice> lstVMSupplierInvoice = new ArrayList<>();
            lstVMSupplierInvoice = this.supplierInvoiceBllManager.searchVMSupplierInvoice(supplierInvoiceModel);
            responseMessage.responseObj = lstVMSupplierInvoice;
            if (lstVMSupplierInvoice.size() > 0) {
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
            this.WriteExceptionLog(ex);
        }


        return responseMessage;
    }


    public ResponseMessage checkInterComUpdate(RequestMessage requestMessage, VMSupplierInvoice vmSupplierInvoice) {

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
            searchJournalModel.setReferenceType(ReferenceType.SupplierInvoice.get());
            searchJournalModel.setReferenceID(vmSupplierInvoice.supplierInvoiceModel.getSupplierInvoiceID());

            journalDeletedRequestMessage.requestObj = searchJournalModel;
            journalDeletedRequestMessage.brokerMessage.serviceName = "api/journal/delete";
            SubscriberForWorker subForWorkerJournalDelete = new SubscriberForWorker(journalDeletedRequestMessage.brokerMessage.messageId, this.barrier);
            mqttClientDeleteJournal = subForWorkerJournalDelete.subscribe();
            callBackDeleteJournal = subForWorkerJournalDelete.getCallBack();
            PublisherForWorker pubForWorkerForJournalDelete = new PublisherForWorker(accountingTopic, mqttClientDeleteJournal);
            pubForWorkerForJournalDelete.publishedMessageToWorker(journalDeletedRequestMessage);
            //======================= End of one ===========================

            //======================= Start of two ===========================
            InventoryTransactionModel inventoryTransactionModel = new InventoryTransactionModel();
            inventoryTransactionModel.setReferenceType(ReferenceType.SupplierInvoice.get());
            inventoryTransactionModel.setReferenceID(vmSupplierInvoice.supplierInvoiceModel.getSupplierInvoiceID());

            inventoryTransactionDeletedRequestMessage.requestObj = inventoryTransactionModel;
            inventoryTransactionDeletedRequestMessage.brokerMessage.serviceName = "api/inventory/inventoryTransaction/delete";
            SubscriberForWorker subForWorkerInventoryTransaction = new SubscriberForWorker(inventoryTransactionDeletedRequestMessage.brokerMessage.messageId, this.barrier);
            mqttClientDeleteInventoryTransaction = subForWorkerInventoryTransaction.subscribe();
            callBackDeleteInventoryTransaction = subForWorkerInventoryTransaction.getCallBack();
            PublisherForWorker pubForWorkerDeleteInventoryTransaction = new PublisherForWorker(inventoryTopic, mqttClientDeleteInventoryTransaction);
            pubForWorkerDeleteInventoryTransaction.publishedMessageToWorker(inventoryTransactionDeletedRequestMessage);
            //======================= Start of two ===========================

            //======================= Start of three ===========================
            reqJournalSaveMessage.requestObj = requestMessage.requestObj;
            reqJournalSaveMessage.brokerMessage.serviceName = "api/journal/save";
            SubscriberForWorker subForWorker = new SubscriberForWorker(reqJournalSaveMessage.brokerMessage.messageId, this.barrier);
            mqttClientSaveJournal = subForWorker.subscribe();
            callBackSaveJournal = subForWorker.getCallBack();
            PublisherForWorker pubForWorkerGetJournalSave = new PublisherForWorker(accountingTopic, mqttClientSaveJournal);
            pubForWorkerGetJournalSave.publishedMessageToWorker(reqJournalSaveMessage);
            //======================= Start of three ===========================

            VMInventoryTransaction vmInventoryTransaction = new VMInventoryTransaction();
            vmInventoryTransaction.lstInventoryTransactionModel = this.getInventoryTransactionModels(vmSupplierInvoice);
            //======================= Start of two ===========================
            // Save inventory transaction

            //======================= Start of four ===========================
            reqObjInventoryTransactionMessage.requestObj = vmInventoryTransaction;
            reqObjInventoryTransactionMessage.brokerMessage.serviceName = "api/inventory/inventoryTransaction/Save";
            SubscriberForWorker subForWorkerForInventory = new SubscriberForWorker(reqObjInventoryTransactionMessage.brokerMessage.messageId, this.barrier);
            mqttClientSaveInventoryTransaction = subForWorkerForInventory.subscribe();
            callBackInventoryTransaction = subForWorkerForInventory.getCallBack();
            PublisherForWorker pubForWorkerInvenotryTransaction = new PublisherForWorker(inventoryTopic, mqttClientSaveInventoryTransaction);
            pubForWorkerInvenotryTransaction.publishedMessageToWorker(reqObjInventoryTransactionMessage);
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

    private List<InventoryTransactionModel> getInventoryTransactionModels(VMSupplierInvoice vmSupplierInvoice) {
        List<InventoryTransactionModel> lstInventoryTransactionModel = new ArrayList<>();
        for (SupplierInvoiceDetailModel supplierInvoiceDetailModel : vmSupplierInvoice.lstSupplierInvoiceDetailModel) {

            InventoryTransactionModel inventoryTransactionModel = new InventoryTransactionModel();
            inventoryTransactionModel.setBusinessID(vmSupplierInvoice.supplierInvoiceModel.getBusinessID());
            inventoryTransactionModel.setProductID(supplierInvoiceDetailModel.getProductID());
            inventoryTransactionModel.setInQuantity(supplierInvoiceDetailModel.getQuantity());
            inventoryTransactionModel.setReferenceID(vmSupplierInvoice.supplierInvoiceModel.getSupplierInvoiceID());
            inventoryTransactionModel.setReferenceType(ReferenceType.SupplierInvoice.get());
            lstInventoryTransactionModel.add(inventoryTransactionModel);
        }
        return lstInventoryTransactionModel;
    }

    public ResponseMessage checkInterCom(RequestMessage requestMessage, VMSupplierInvoice vmSupplierInvoice) {

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
            vmInventoryTransaction.lstInventoryTransactionModel = this.getInventoryTransactionModels(vmSupplierInvoice);


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


    public ResponseMessage supplierStatement(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        SupplierModel supplierModel = new SupplierModel();
        List<VMSupplierStatementModel> lstVMSupplierStatementModel = new ArrayList<>();

        StatementSearchModel statementSearchModel = new StatementSearchModel();

        try {
            statementSearchModel = Core.getRequestObject(requestMessage, StatementSearchModel.class);

            lstVMSupplierStatementModel = this.supplierInvoiceBllManager.getSupplierStatement(statementSearchModel);
            responseMessage.responseObj = lstVMSupplierStatementModel;
            if (lstVMSupplierStatementModel.size() > 0) {
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


    public ResponseMessage getSupplierOutstanding(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        SupplierModel supplierModel = new SupplierModel();
        Double outstandingAmount;

        try {
            supplierModel = Core.getRequestObject(requestMessage, SupplierModel.class);

            outstandingAmount = this.supplierInvoiceBllManager.getPartyBalance(PartyType.Supplier.get(), supplierModel.getSupplierID(), supplierModel.getBusinessID());
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
        SupplierInvoiceModel supplierInvoiceModel = new SupplierInvoiceModel();
        try {
            supplierInvoiceModel = Core.getRequestObject(requestMessage, SupplierInvoiceModel.class);
            supplierInvoiceModel = this.supplierInvoiceBllManager.deleteSupplierInvoice(supplierInvoiceModel);

            responseMessage.responseObj = supplierInvoiceModel;
            if (Core.clientMessage.get().messageCode != null) {
                this.rollBack();
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.FAILED_TO_DELETE;
                } else {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
            } else {

                responseMessage = this.deletedRelatedInterModuleObject(supplierInvoiceModel);
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
            log.error("SupplierInvoiceServiceManager -> supplier invoice delete got exception");
            if (responseMessage.message == null) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            }
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }

    public ResponseMessage deletedRelatedInterModuleObject(SupplierInvoiceModel supplierInvoiceModel) {

        MqttClient mqttClientDeleteJournal;
        MqttClient mqttClientDeleteInventoryTransaction;

        CallBack callBackDeleteJournal = null;
        CallBack callBackDeleteInventoryTransaction = null;

        ResponseMessage responseMessage = new ResponseMessage();

        ResponseMessage responseMessageDeleteJournal;
        ResponseMessage responseMessageDeleteInventoryTransaction;


        boolean workCompleteWithInAllowTime;
        RequestMessage reqJournalSaveMessage, reqObjInventoryTransactionMessage, journalDeletedRequestMessage, inventoryTransactionDeletedRequestMessage;
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
            searchJournalModel.setReferenceType(ReferenceType.SupplierInvoice.get());
            searchJournalModel.setReferenceID(supplierInvoiceModel.getSupplierInvoiceID());

            journalDeletedRequestMessage.requestObj = searchJournalModel;
            journalDeletedRequestMessage.brokerMessage.serviceName = "api/journal/delete";
            journalDeletedRequestMessage.token = Core.requestToken.get();

            SubscriberForWorker subForWorkerJournalDelete = new SubscriberForWorker(journalDeletedRequestMessage.brokerMessage.messageId, this.barrier);
            mqttClientDeleteJournal = subForWorkerJournalDelete.subscribe();
            callBackDeleteJournal = subForWorkerJournalDelete.getCallBack();
            PublisherForWorker pubForWorkerForJournalDelete = new PublisherForWorker(accountingTopic, mqttClientDeleteJournal);
            pubForWorkerForJournalDelete.publishedMessageToWorker(journalDeletedRequestMessage);
            //======================= End of one ===========================

            //======================= Start of two ===========================
            InventoryTransactionModel inventoryTransactionModel = new InventoryTransactionModel();
            inventoryTransactionModel.setReferenceType(ReferenceType.SupplierInvoice.get());
            inventoryTransactionModel.setReferenceID(supplierInvoiceModel.getSupplierInvoiceID());

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
            log.error("Exception from checkInterCom Module communication SupplierInvoiceServiceManager");
        }
        return responseMessage;
    }

}
