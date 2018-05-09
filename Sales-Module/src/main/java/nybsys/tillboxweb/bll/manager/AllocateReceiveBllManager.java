/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 21-Mar-18
 * Time: 4:56 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.MessageModel.BllResponseMessage;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.coreUtil.CoreUtils;
import nybsys.tillboxweb.entities.AllocateReceive;
import nybsys.tillboxweb.models.AllocateReceiveModel;
import nybsys.tillboxweb.models.CustomerInvoiceModel;
import nybsys.tillboxweb.models.VMAllocateReceive;
import nybsys.tillboxweb.sales_enum.PaymentStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AllocateReceiveBllManager extends BaseBll<AllocateReceive> {

    private static final Logger log = LoggerFactory.getLogger(AllocateReceiveBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(AllocateReceive.class);
        Core.runTimeModelType.set(AllocateReceiveModel.class);
    }


    @Autowired
    private CustomerInvoiceBllManager customerInvoiceBllManager;


    public BllResponseMessage saveOrUpdate(RequestMessage requestMessage) throws Exception {
        BllResponseMessage bllResponseMessage = new BllResponseMessage();

        VMAllocateReceive vmAllocateReceive = Core.getRequestObject(requestMessage, VMAllocateReceive.class);

        // Extract data from VM
        List<AllocateReceiveModel> allocateReceiveModelList = vmAllocateReceive.allocateReceiveModelList;
        List<AllocateReceiveModel> saveOrUpdatedAllocateReceiveModelList;
        List<AllocateReceiveModel> allocateReceiveModelListForSequence;

        String hsql, buildDbSequence, currentDBSequence = null;

        Integer primaryKeyValue;
        AllocateReceiveModel savedAllocateReceiveModel = null, updatedAllocateReceiveModel, whereConditionAllocateReceiveModel;
        List<CustomerInvoiceModel> customerInvoiceModelList;
        CustomerInvoiceModel whereConditionCustomerInvoiceModel, temCustomerInvoiceModel;
        List<AllocateReceiveModel> previousAllocateReceiveModelList;

        Double previousPaymentTotal = 0.0, currentPayment = 0.0;

        try {

            saveOrUpdatedAllocateReceiveModelList = new ArrayList<>();

            for (AllocateReceiveModel reqAllocateReceiveModel : allocateReceiveModelList) {
                primaryKeyValue = reqAllocateReceiveModel.getAllocateReceiveID();

                if (primaryKeyValue == null || primaryKeyValue == 0) {
                    // Save Code

                    //======================= due calculation logic ==========================================================================
                    whereConditionCustomerInvoiceModel = new CustomerInvoiceModel();
                    whereConditionCustomerInvoiceModel.setCustomerInvoiceID(reqAllocateReceiveModel.getCustomerInvoiceID());

                    customerInvoiceModelList = this.customerInvoiceBllManager.getAllByConditionWithActive(whereConditionCustomerInvoiceModel);

                    for (CustomerInvoiceModel item : customerInvoiceModelList) {
                        whereConditionAllocateReceiveModel = new AllocateReceiveModel();
                        whereConditionAllocateReceiveModel.setCustomerInvoiceID(item.getCustomerInvoiceID());
                        previousAllocateReceiveModelList = this.getAllByConditionWithActive(whereConditionAllocateReceiveModel);

                        for (AllocateReceiveModel preAllocateReceiveModel : previousAllocateReceiveModelList) {
                            previousPaymentTotal += preAllocateReceiveModel.getAmount();
                        }
                        currentPayment = currentPayment + previousPaymentTotal;
                        currentPayment = currentPayment + reqAllocateReceiveModel.getAmount();

                        if (item.getTotalAmount().doubleValue() == currentPayment.doubleValue()) {
                            temCustomerInvoiceModel = item;
                            temCustomerInvoiceModel.setPaymentStatus(PaymentStatus.Paid.get());
                            this.customerInvoiceBllManager.update(temCustomerInvoiceModel);
                        } else if (item.getTotalAmount().doubleValue() > currentPayment.doubleValue()) {
                            temCustomerInvoiceModel = item;
                            temCustomerInvoiceModel.setPaymentStatus(PaymentStatus.Partial.get());
                            this.customerInvoiceBllManager.update(temCustomerInvoiceModel);
                        } else if (item.getTotalAmount().doubleValue() < currentPayment.doubleValue()) {
                            bllResponseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                            Core.clientMessage.get().userMessage = "Invoice amount less than Allocate amount!";
                            Core.clientMessage.get().message = "Invoice amount less than Allocate amount!";
                            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                            return bllResponseMessage;
                        }
                        currentPayment = 0.0;
                        previousPaymentTotal = 0.0;
                    }
                    //======================= End Due calculation logic ======================================================================


                    // ============================= Create PMT0000001 =============================
                    hsql = hsql = "SELECT e FROM AllocateReceive e ORDER BY e.allocateReceiveID DESC";
                    allocateReceiveModelListForSequence = this.executeHqlQuery(hsql, AllocateReceiveModel.class, TillBoxAppEnum.QueryType.GetOne.get());
                    if (allocateReceiveModelListForSequence.size() > 0) {
                        currentDBSequence = allocateReceiveModelListForSequence.get(0).getAllocateReceiveNo();
                    }
                    buildDbSequence = CoreUtils.getSequence(currentDBSequence, "ALR");
                    // ==========================End Create PMT0000001 =============================



                    reqAllocateReceiveModel.setAllocateReceiveNo(buildDbSequence);

                    savedAllocateReceiveModel = this.save(reqAllocateReceiveModel);
                    //Hold data for return
                    saveOrUpdatedAllocateReceiveModelList.add(savedAllocateReceiveModel);

                    if (saveOrUpdatedAllocateReceiveModelList.size() > 0) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                        Core.clientMessage.get().userMessage = "AllocateReceive Save Successfully";
                    } else {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = "Failed to Save AllocateReceive";
                    }
                } else {
                    // Update Code
                    //======================= due calculation logic ==========================================================================
                    whereConditionCustomerInvoiceModel = new CustomerInvoiceModel();
                    whereConditionCustomerInvoiceModel.setCustomerInvoiceID(reqAllocateReceiveModel.getCustomerInvoiceID());

                    customerInvoiceModelList = this.customerInvoiceBllManager.getAllByConditionWithActive(whereConditionCustomerInvoiceModel);

                    for (CustomerInvoiceModel item : customerInvoiceModelList) {
                        whereConditionAllocateReceiveModel = new AllocateReceiveModel();
                        whereConditionAllocateReceiveModel.setCustomerInvoiceID(item.getCustomerInvoiceID());
                        previousAllocateReceiveModelList = this.getAllByConditionWithActive(whereConditionAllocateReceiveModel);

                        for (AllocateReceiveModel preAllocateReceiveModel : previousAllocateReceiveModelList) {
                            if (primaryKeyValue == preAllocateReceiveModel.getAllocateReceiveID())
                                continue;
                            else
                                previousPaymentTotal += preAllocateReceiveModel.getAmount();
                        }
                        currentPayment = currentPayment + previousPaymentTotal;
                        currentPayment = currentPayment + reqAllocateReceiveModel.getAmount();

                        if (item.getTotalAmount().doubleValue() == currentPayment.doubleValue()) {
                            temCustomerInvoiceModel = item;
                            temCustomerInvoiceModel.setPaymentStatus(PaymentStatus.Paid.get());
                            this.customerInvoiceBllManager.update(temCustomerInvoiceModel);
                        } else if (item.getTotalAmount().doubleValue() > currentPayment.doubleValue()) {
                            temCustomerInvoiceModel = item;
                            temCustomerInvoiceModel.setPaymentStatus(PaymentStatus.Partial.get());
                            this.customerInvoiceBllManager.update(temCustomerInvoiceModel);
                        } else if (item.getTotalAmount().doubleValue() < currentPayment.doubleValue()) {
                            bllResponseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                            Core.clientMessage.get().userMessage = "Invoice amount less than Allocate amount!";
                            Core.clientMessage.get().message = "Invoice amount less than Allocate amount!";
                            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                            return bllResponseMessage;
                        }
                        currentPayment = 0.0;
                        previousPaymentTotal = 0.0;
                    }
                    //======================= End Due calculation logic ======================================================================


                    updatedAllocateReceiveModel = this.update(reqAllocateReceiveModel);
                    // Hold data for return
                    saveOrUpdatedAllocateReceiveModelList.add(updatedAllocateReceiveModel);

                    if (saveOrUpdatedAllocateReceiveModelList.size() > 0) {
                        Core.clientMessage.get().userMessage = "AllocateReceive Update Successfully";
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    } else {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = "Failed to Update AllocateReceive";
                    }
                }

            }

        } catch (Exception ex) {
            log.error("AllocateReceiveBllManager -> saveOrUpdate got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        //prepere VM for return
        vmAllocateReceive.allocateReceiveModelList = saveOrUpdatedAllocateReceiveModelList;

        bllResponseMessage.responseObject = vmAllocateReceive;
        bllResponseMessage.responseCode = Core.clientMessage.get().messageCode;
        bllResponseMessage.message = Core.clientMessage.get().message;

        return bllResponseMessage;
    }

    public List<AllocateReceiveModel> search(AllocateReceiveModel reqAllocateReceiveModel) throws Exception {
        List<AllocateReceiveModel> findAllocateReceiveList;
        try {
            findAllocateReceiveList = this.getAllByConditions(reqAllocateReceiveModel);
            if (findAllocateReceiveList.size() > 0) {
                Core.clientMessage.get().userMessage = "Find the request AllocateReceive";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                Core.clientMessage.get().message = "Failed to find the requested AllocateReceive";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("AllocateReceiveBllManager -> search got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return findAllocateReceiveList;
    }

    public Integer deleteByConditions(RequestMessage requestMessage) throws Exception {
        AllocateReceiveModel req_AllocateReceiveModel =
                Core.getRequestObject(requestMessage, AllocateReceiveModel.class);
        Integer numberOfDeleteRow = 0;
        try {
            numberOfDeleteRow = this.deleteByConditions(req_AllocateReceiveModel);
            if (numberOfDeleteRow > 0) {
                //Core.clientMessage.get().userMessage = "Successfully deleted the requested AllocateReceive";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                Core.clientMessage.get().message = "Failed to deleted the requested AllocateReceive";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("AllocateReceiveBllManager -> deleteByConditions got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return numberOfDeleteRow;
    }


    public AllocateReceiveModel inActive(RequestMessage requestMessage) throws Exception {
        AllocateReceiveModel reqAllocateReceiveModel =
                Core.getRequestObject(requestMessage, AllocateReceiveModel.class);
        AllocateReceiveModel _AllocateReceiveModel = null;
        try {
            if (reqAllocateReceiveModel != null) {
                _AllocateReceiveModel = this.inActive(reqAllocateReceiveModel);
                if (_AllocateReceiveModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "Successfully inactive the requested AllocateReceive";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to inactive the requested AllocateReceive";
                }
            }

        } catch (Exception ex) {
            log.error("AllocateReceiveBllManager -> inActive got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return _AllocateReceiveModel;
    }


    public AllocateReceiveModel delete(AllocateReceiveModel reqAllocateReceiveModel) throws Exception {
        AllocateReceiveModel deletedAllocateReceiveModel = null;
        try {
            if (reqAllocateReceiveModel != null) {
                deletedAllocateReceiveModel = this.softDelete(reqAllocateReceiveModel);
                if (deletedAllocateReceiveModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "Successfully deleted the requested AllocateReceive";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to deleted the requested AllocateReceive";
                }
            }

        } catch (Exception ex) {
            log.error("AllocateReceiveBllManager -> delete got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return deletedAllocateReceiveModel;
    }

    public AllocateReceiveModel getByReqId(AllocateReceiveModel reqAllocateReceiveModel) throws Exception {
        Integer primaryKeyValue = reqAllocateReceiveModel.getAllocateReceiveID();
        AllocateReceiveModel foundAllocateReceiveModel = null;
        try {
            if (primaryKeyValue != null) {
                foundAllocateReceiveModel = this.getById(primaryKeyValue);
                if (foundAllocateReceiveModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    //Core.clientMessage.get().userMessage = "Get the requested AllocateReceive successfully";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to the requested AllocateReceive";
                }
            }

        } catch (Exception ex) {
            log.error("AllocateReceiveBllManager -> getByID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return foundAllocateReceiveModel;
    }
    
    /*
    
    private ResponseMessage journalEntry(AllocateReceiveModel modelForJournalEntry) throws Exception {
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