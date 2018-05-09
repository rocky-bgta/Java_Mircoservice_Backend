/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 13-Mar-18
 * Time: 11:06 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;

import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.coreModels.JournalModel;
import nybsys.tillboxweb.entities.CustomerReceiveDetail;
import nybsys.tillboxweb.models.CustomerReceiveDetailModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CustomerReceiveDetailBllManager extends BaseBll<CustomerReceiveDetail> {

    private static final Logger log = LoggerFactory.getLogger(CustomerReceiveDetailBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(CustomerReceiveDetail.class);
        Core.runTimeModelType.set(CustomerReceiveDetailModel.class);
    }

    public CustomerReceiveDetailModel saveOrUpdate(CustomerReceiveDetailModel reqCustomerReceiveDetailModel) throws Exception {

        BigInteger primaryKeyValue =
                new BigInteger(String.valueOf(reqCustomerReceiveDetailModel.getCustomerReceiveDetailID()));
        BigInteger bigZero = new BigInteger(String.valueOf(0));


        CustomerReceiveDetailModel savedCustomerReceiveDetailModel = null, updatedCustomerReceiveDetailModel;
        JournalModel drJournalModel, crJournalModel;
        List<JournalModel> journalModelList = new ArrayList<>();
        Boolean isJournalEntrySuccess;

        try {

            if (primaryKeyValue == null || primaryKeyValue.equals(bigZero)) {
                // Save Code
                savedCustomerReceiveDetailModel = this.save(reqCustomerReceiveDetailModel);
                if (savedCustomerReceiveDetailModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    //Core.ClientMessage.get().userMessage = "CustomerReceiveDetail Save Successfully";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to Save CustomerReceiveDetail";
                }
            } else {
                // Update Code
                updatedCustomerReceiveDetailModel = this.update(reqCustomerReceiveDetailModel);
                if (updatedCustomerReceiveDetailModel != null) {
                    //Core.clientMessage.get().userMessage = "CustomerReceiveDetail Update Successfully";
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to Update CustomerReceiveDetail";
                }

                savedCustomerReceiveDetailModel = updatedCustomerReceiveDetailModel;
            }

        } catch (Exception ex) {
            log.error("CustomerReceiveDetailBllManager -> saveOrUpdate got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return savedCustomerReceiveDetailModel;
    }

    public List<CustomerReceiveDetailModel> search(CustomerReceiveDetailModel reqCustomerReceiveDetailModel) throws Exception {
        List<CustomerReceiveDetailModel> findCustomerReceiveDetailList;
        try {
            findCustomerReceiveDetailList = this.getAllByConditions(reqCustomerReceiveDetailModel);
            if (findCustomerReceiveDetailList.size() > 0) {
                //Core.clientMessage.get().userMessage = "Find the request CustomerReceiveDetail";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                Core.clientMessage.get().message = "Failed to find the requested CustomerReceiveDetail";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("CustomerReceiveDetailBllManager -> search got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return findCustomerReceiveDetailList;
    }

    public Integer deleteByConditions(RequestMessage requestMessage) throws Exception {
        CustomerReceiveDetailModel req_CustomerReceiveDetailModel =
                Core.getRequestObject(requestMessage, CustomerReceiveDetailModel.class);
        Integer numberOfDeleteRow = 0;
        try {
            numberOfDeleteRow = this.deleteByConditions(req_CustomerReceiveDetailModel);
            if (numberOfDeleteRow > 0) {
                //Core.clientMessage.get().userMessage = "Successfully deleted the requested CustomerReceiveDetail";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                Core.clientMessage.get().message = "Failed to deleted the requested CustomerReceiveDetail";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("CustomerReceiveDetailBllManager -> deleteByConditions got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return numberOfDeleteRow;
    }


    public CustomerReceiveDetailModel inActive(RequestMessage requestMessage) throws Exception {
        CustomerReceiveDetailModel reqCustomerReceiveDetailModel =
                Core.getRequestObject(requestMessage, CustomerReceiveDetailModel.class);
        CustomerReceiveDetailModel _CustomerReceiveDetailModel = null;
        try {
            if (reqCustomerReceiveDetailModel != null) {
                _CustomerReceiveDetailModel = this.inActive(reqCustomerReceiveDetailModel);
                if (_CustomerReceiveDetailModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    //Core.clientMessage.get().userMessage = "Successfully inactive the requested CustomerReceiveDetail";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to inactive the requested CustomerReceiveDetail";
                }
            }

        } catch (Exception ex) {
            log.error("CustomerReceiveDetailBllManager -> inActive got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return _CustomerReceiveDetailModel;
    }


    public CustomerReceiveDetailModel delete(CustomerReceiveDetailModel reqCustomerReceiveDetailModel) throws Exception {
        CustomerReceiveDetailModel deletedCustomerReceiveDetailModel = null;
        try {
            if (reqCustomerReceiveDetailModel != null) {
                deletedCustomerReceiveDetailModel = this.softDelete(reqCustomerReceiveDetailModel);
                if (deletedCustomerReceiveDetailModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    //Core.clientMessage.get().userMessage = "Successfully deleted the requested CustomerReceiveDetail";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to deleted the requested CustomerReceiveDetail";
                }
            }

        } catch (Exception ex) {
            log.error("CustomerReceiveDetailBllManager -> delete got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return deletedCustomerReceiveDetailModel;
    }

    public CustomerReceiveDetailModel getByReqId(CustomerReceiveDetailModel reqCustomerReceiveDetailModel) throws Exception {
        CustomerReceiveDetailModel foundCustomerReceiveDetailModel = null;
        try {
            BigInteger primaryKeyValue =
                    new BigInteger(String.valueOf(reqCustomerReceiveDetailModel.getCustomerReceiveDetailID()));

            if (primaryKeyValue != null) {
                foundCustomerReceiveDetailModel = this.getById(primaryKeyValue);
                if (foundCustomerReceiveDetailModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    //Core.clientMessage.get().userMessage = "Get the requested CustomerReceiveDetail successfully";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to the requested CustomerReceiveDetail";
                }
            }

        } catch (Exception ex) {
            log.error("CustomerReceiveDetailBllManager -> getByID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return foundCustomerReceiveDetailModel;
    }

    public Double getPriceSumByInvoiceID(Integer invoiceID) throws Exception {
        List<CustomerReceiveDetailModel> lstCustomerReceiveDetailModel = new ArrayList<>();
        CustomerReceiveDetailModel whereCondition = new CustomerReceiveDetailModel();
        Double priceSum = 0.0;
        Double tempRowSum = 0.0;
        try {
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());
            whereCondition.setCustomerInvoiceID(invoiceID);
            lstCustomerReceiveDetailModel = this.getAllByConditions(whereCondition);
            for (CustomerReceiveDetailModel customerReceiveDetailModel : lstCustomerReceiveDetailModel)
            {
                tempRowSum = customerReceiveDetailModel.getAmount()+customerReceiveDetailModel.getDiscount();
                priceSum += tempRowSum;
            }
        } catch (Exception ex) {
            log.error("CustomerReceiveDetailBllManager -> getPriceSumByInvoiceID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return priceSum;
    }
    
    /*
    
    private ResponseMessage journalEntry(CustomerReceiveDetailModel modelForJournalEntry) throws Exception {
        List<JournalModel> lstJournalModel = new ArrayList<>();
        JournalModel drJournalModel = new JournalModel();
        JournalModel crJournalModel = new JournalModel();
       

        if(modelForJournalEntry.getAdjustmentType() == Adjustment.Decrease.get()){
            drJournalModel.setAccountID(DefaultCOA.AdjustmentLoss.get());
            drJournalModel.setAmount(modelForJournalEntry.getTotalPrice());
            drJournalModel.setDrCrIndicator(DebitCreditIndicator.Debit.get());
            drJournalModel.setNote(modelForJournalEntry.getReason());
            drJournalModel.setReferenceID(modelForJournalEntry.getProductAdjustmentID());
            drJournalModel.setReferenceType(ReferenceType.ProductAdjustment.get());

            crJournalModel.setAccountID(DefaultCOA.Inventory.get());
            crJournalModel.setAmount(modelForJournalEntry.getTotalPrice());
            crJournalModel.setDrCrIndicator(DebitCreditIndicator.Credit.get());
            crJournalModel.setNote(modelForJournalEntry.getReason());
            crJournalModel.setReferenceID(modelForJournalEntry.getProductAdjustmentID());
            crJournalModel.setReferenceType(ReferenceType.ProductAdjustment.get());
        }

        if(modelForJournalEntry.getAdjustmentType() == Adjustment.Increase.get()){
            drJournalModel.setAccountID(DefaultCOA.Inventory.get());
            drJournalModel.setAmount(modelForJournalEntry.getTotalPrice());
            drJournalModel.setDrCrIndicator(DebitCreditIndicator.Debit.get());
            drJournalModel.setNote(modelForJournalEntry.getReason());
            drJournalModel.setReferenceID(modelForJournalEntry.getProductAdjustmentID());
            drJournalModel.setReferenceType(ReferenceType.ProductAdjustment.get());

            crJournalModel.setAccountID(DefaultCOA.AdjustmentIncome.get());
            crJournalModel.setAmount(modelForJournalEntry.getTotalPrice());
            crJournalModel.setDrCrIndicator(DebitCreditIndicator.Credit.get());
            crJournalModel.setNote(modelForJournalEntry.getReason());
            crJournalModel.setReferenceID(modelForJournalEntry.getProductAdjustmentID());
            crJournalModel.setReferenceType(ReferenceType.ProductAdjustment.get());
        }

        // add double entry
        lstJournalModel.add(drJournalModel);
        lstJournalModel.add(crJournalModel);
        VMJournalListModel vmJournalListModel = new VMJournalListModel();

        //================== inter-module communication for journal save ===========================
        boolean workCompleteWithInAllowTime;
        ResponseMessage responseMessage;
        RequestMessage requestMessage;
        CallBack callBackForJournal;
        MqttClient mqttClientForJournal;
        Object lockObject = new Object();
        String pubTopic = WorkerSubscriptionConstants.WORKER_ACCOUNTING_MODULE_TOPIC;
        this.barrier = TillBoxUtils.getBarrier(1, lockObject);

        //======================= Start of one ===========================
        requestMessage = Core.getDefaultWorkerRequestMessage();
        requestMessage.brokerMessage.serviceName = "api/journal/save";
        vmJournalListModel.lstJournalModel = lstJournalModel;
        requestMessage.requestObject =  vmJournalListModel;
        SubscriberForWorker subForWorker = new SubscriberForWorker(requestMessage.brokerMessage.messageId, this.barrier);
        mqttClientForJournal = subForWorker.subscribe();
        callBackForJournal = subForWorker.getCallBack();
        PublisherForWorker pubForWorkerToSaveJournal = new PublisherForWorker(pubTopic, mqttClientForJournal);
        pubForWorkerToSaveJournal.publishedMessageToWorker(requestMessage);
        //======================= End of one ===========================

        synchronized (lockObject) {
            long startTime = System.nanoTime();
            lockObject.wait(this.allowedTime);
            workCompleteWithInAllowTime = this.isResponseWithInAllowedTime(startTime);
            if (workCompleteWithInAllowTime) {
                responseMessage = callBackForJournal.getResponseMessage();
            } else {
                //timeout
                throw new Exception("Response not get within allowed time");
            }
        }
        this.closeBrokerClient(mqttClientForJournal, requestMessage.brokerMessage.messageId);
        return responseMessage;
    }
    
    */
}