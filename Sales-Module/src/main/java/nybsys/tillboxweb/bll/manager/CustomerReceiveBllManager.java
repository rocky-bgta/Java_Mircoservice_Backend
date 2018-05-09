/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 12-Mar-18
 * Time: 5:45 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.MessageModel.BllResponseMessage;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.Utils.TillBoxUtils;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.coreBllManager.CoreJournalBllManager;
import nybsys.tillboxweb.coreConstant.CurrencyConstant;
import nybsys.tillboxweb.coreEnum.DefaultCOA;
import nybsys.tillboxweb.coreEnum.PartyType;
import nybsys.tillboxweb.coreEnum.ReferenceType;
import nybsys.tillboxweb.coreModels.CurrencyModel;
import nybsys.tillboxweb.coreModels.JournalModel;
import nybsys.tillboxweb.coreUtil.CoreUtils;
import nybsys.tillboxweb.entities.CustomerReceive;
import nybsys.tillboxweb.models.CustomerInvoiceModel;
import nybsys.tillboxweb.models.CustomerReceiveDetailModel;
import nybsys.tillboxweb.models.CustomerReceiveModel;
import nybsys.tillboxweb.models.VMCustomerReceive;
import nybsys.tillboxweb.sales_enum.PaymentStatus;
import nybsys.tillboxweb.service.manager.CustomerAdjustmentServiceManager;
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
public class CustomerReceiveBllManager extends BaseBll<CustomerReceive> {

    private static final Logger log = LoggerFactory.getLogger(CustomerReceiveBllManager.class);

    private CustomerAdjustmentServiceManager customerAdjustmentServiceManager = new CustomerAdjustmentServiceManager();

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(CustomerReceive.class);
        Core.runTimeModelType.set(CustomerReceiveModel.class);
    }

    @Autowired
    private CoreJournalBllManager coreJournalBllManager;

    @Autowired
    private CustomerReceiveDetailBllManager customerReceiveDetailBllManager;

    @Autowired
    private CustomerInvoiceBllManager customerInvoiceBllManager;

    public BllResponseMessage saveOrUpdate(RequestMessage requestMessage) throws Exception {

        //Boolean isOperationSuccess = false;
        BllResponseMessage bllResponseMessage = this.getDefaultBllResponse();

        CustomerReceiveModel reqCustomerReceiveModel, savedCustomerReceiveModel, updatedCustomerReceiveModel;
        CustomerReceiveDetailModel whereConditionCusRecModel;
        List<CustomerReceiveDetailModel> reqCustomerReceiveDetailModelList;
        VMCustomerReceive reqVMCustomerReceive, vmCustomerReceive, updatedVMCustomerReceive;
        CurrencyModel currencyModel;


        //get base currency and exchange rate
        currencyModel = this.customerAdjustmentServiceManager.getBaseCurrency();
        if (currencyModel == null) {
            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            Core.clientMessage.get().userMessage = CurrencyConstant.BASE_CURRENT_NOT_FOUND;
            Core.clientMessage.get().message = CurrencyConstant.BASE_CURRENT_NOT_FOUND;
            return bllResponseMessage;
        }

        //check entry currency is present if not base currency will be entry currency
        if (requestMessage.entryCurrencyID == null || requestMessage.entryCurrencyID == 0) {
            requestMessage.entryCurrencyID = currencyModel.getCurrencyID();
        }

        // Extract data from VM ======================================
        reqVMCustomerReceive = Core.getRequestObject(requestMessage, VMCustomerReceive.class);
        reqCustomerReceiveModel = reqVMCustomerReceive.customerReceiveModel;
        reqCustomerReceiveDetailModelList = reqVMCustomerReceive.customerReceiveDetailModelList;
        // Extract data from VM ======================================

        Integer primaryKeyValue = reqCustomerReceiveModel.getCustomerReceiveID();

        JournalModel drJournalModel, crJournalModel;
        List<JournalModel> journalModelList = new ArrayList<>();
        Boolean isJournalEntrySuccess;
        String buildDbSequence, currentDBSequence = null;
        String hsql;
        String journalReferenceNo;
        List<CustomerReceiveModel> customerReceiveModelList;
        List<CustomerReceiveDetailModel> saveOrUpdatedCustomerRecDetailList,
                updatedCustomerReceiveDetailModelList, previousCustomerRecDetailModelList;

        CustomerReceiveDetailModel temCustomerReceiveDetailModel;
        CustomerInvoiceModel customerInvoiceModel;

        Double discount = 0.0, amount = 0.0, previousDiscount = 0.0;
        Double unAllocateAmount = 0.0;
        Double totalAmount = 0.0;
        Double totalDiscount = 0.0;
        Double previousPaymentTotal = 0.0, currentPayment = 0.0;

        vmCustomerReceive = new VMCustomerReceive();
        saveOrUpdatedCustomerRecDetailList = new ArrayList<>();

        try {
            journalReferenceNo = TillBoxUtils.getUUID();

            if (primaryKeyValue == null || primaryKeyValue == 0) { //primaryKeyValue.equals(bigZero)) {
                // Save Code

                // ============================= Create RCP0000001 =============================
                hsql = "SELECT e FROM CustomerReceive e ORDER BY e.customerReceiveID DESC";
                customerReceiveModelList = this.executeHqlQuery(hsql, CustomerReceiveModel.class, TillBoxAppEnum.QueryType.GetOne.get());
                if (customerReceiveModelList.size() > 0) {
                    currentDBSequence = customerReceiveModelList.get(0).getCustomerReceiveNo();
                }
                buildDbSequence = CoreUtils.getSequence(currentDBSequence, "RCP");
                // ==========================End Create RCP0000001 =============================

                reqCustomerReceiveModel.setCustomerReceiveNo(buildDbSequence);
                reqCustomerReceiveModel.setDocNumber(buildDbSequence);

                //add currency
                reqCustomerReceiveModel.setBaseCurrencyID(currencyModel.getCurrencyID());
                reqCustomerReceiveModel.setEntryCurrencyID(requestMessage.entryCurrencyID);
                reqCustomerReceiveModel.setBaseCurrencyAmount(reqCustomerReceiveModel.getTotalAmount() * reqCustomerReceiveModel.getExchangeRate());

                //Save master table
                savedCustomerReceiveModel = this.save(reqCustomerReceiveModel);


                //Save detail table
                for (CustomerReceiveDetailModel item : reqCustomerReceiveDetailModelList) {
                    item.setCustomerReceiveID(savedCustomerReceiveModel.getCustomerReceiveID());


                    //======================= due calculation logic ==========================================================================
                    customerInvoiceModel = this.customerInvoiceBllManager.getByIdActiveStatus(item.getCustomerInvoiceID());
                    // if payment status is due then
                    if (customerInvoiceModel.getPaymentStatus() == PaymentStatus.Due.get()
                            || customerInvoiceModel.getPaymentStatus() == PaymentStatus.Partial.get()) {

                        whereConditionCusRecModel = new CustomerReceiveDetailModel();
                        whereConditionCusRecModel.setCustomerInvoiceID(item.getCustomerInvoiceID());
                        previousCustomerRecDetailModelList =
                                this.customerReceiveDetailBllManager.getAllByConditionWithActive(whereConditionCusRecModel);

                        for (CustomerReceiveDetailModel previousCustomerRecDetailModel : previousCustomerRecDetailModelList) {
                            previousPaymentTotal += previousCustomerRecDetailModel.getDiscount() + previousCustomerRecDetailModel.getAmount();
                            previousDiscount += previousCustomerRecDetailModel.getDiscount();
                        }

                        currentPayment = item.getAmount() + item.getDiscount();
                        currentPayment = currentPayment + previousPaymentTotal;


                        if (customerInvoiceModel.getTotalAmount().doubleValue() == currentPayment.doubleValue()) {
                            //set paid status (paid) in CustomerInvoic Table of Column (paymentStatus)
                            customerInvoiceModel.setPaymentStatus(PaymentStatus.Paid.get());
                            this.customerInvoiceBllManager.update(customerInvoiceModel);
                        } else if (customerInvoiceModel.getTotalAmount().doubleValue() > currentPayment.doubleValue()) {
                            //set paid status (Partial) in CustomerInvoic Table of Column (paymentStatus)
                            customerInvoiceModel.setPaymentStatus(PaymentStatus.Partial.get());
                            this.customerInvoiceBllManager.update(customerInvoiceModel);
                        }
                        if (customerInvoiceModel.getTotalAmount().doubleValue() < currentPayment.doubleValue()) {
                            // set paid status (paid) in CustomerInvoic Table of Column (paymentStatus)
                            // and calculate the excess amount and set is as unallocate amount in CustomerInvoic Table
                            customerInvoiceModel.setPaymentStatus(PaymentStatus.Paid.get());
                            this.customerInvoiceBllManager.update(customerInvoiceModel);
                            unAllocateAmount = currentPayment - customerInvoiceModel.getTotalAmount();
                        }

                    }
                    //======================= End Due calculation logic ======================================================================

                    //Save detail table data
                    temCustomerReceiveDetailModel = this.customerReceiveDetailBllManager.saveOrUpdate(item);

                    // Hold datail table data for return
                    saveOrUpdatedCustomerRecDetailList.add(temCustomerReceiveDetailModel);


                    amount = item.getAmount();
                    if (amount == 0.0 || amount == null)
                        amount = 0.0;
                    else
                        totalAmount += amount;

                    discount = item.getDiscount();
                    if (discount == 0 || discount == null)
                        discount = 0.0;
                    else
                        totalDiscount += discount;

                }

                if (totalAmount > 0.0) {
                    //=============== Journal Entry for amount  ============================
                    drJournalModel = CoreUtils.buildDrJournalEntry(
                            journalReferenceNo,
                            DefaultCOA.BankAccount.get(),
                            savedCustomerReceiveModel.getCustomerReceiveID(),
                            ReferenceType.CustomerReceipt.get(),
                            null,
                            null,
                            totalAmount,
                            savedCustomerReceiveModel.getNote(),
                            currencyModel,
                            reqCustomerReceiveModel.getExchangeRate(),
                            requestMessage.entryCurrencyID
                    );

                    crJournalModel = CoreUtils.buildCrJournalEntry(
                            journalReferenceNo,
                            DefaultCOA.TradeDebtors.get(),
                            savedCustomerReceiveModel.getCustomerReceiveID(),
                            ReferenceType.CustomerReceipt.get(),
                            savedCustomerReceiveModel.getCustomerID(),
                            PartyType.Customer.get(),
                            totalAmount,
                            savedCustomerReceiveModel.getNote(),
                            currencyModel,
                            reqCustomerReceiveModel.getExchangeRate(),
                            requestMessage.entryCurrencyID
                    );
                    this.journalEntry(drJournalModel, crJournalModel);
                }
                //=============== Journal entry for amount end =============================


                //=============== Journal Entry for discount ===============================
                //if(discount>0.0){
                if (totalDiscount > 0.0) {
                    drJournalModel = CoreUtils.buildDrJournalEntry(
                            journalReferenceNo,
                            DefaultCOA.DiscountGiven.get(),
                            savedCustomerReceiveModel.getCustomerReceiveID(),
                            ReferenceType.CustomerReceipt.get(),
                            null,
                            null,
                            totalDiscount,
                            savedCustomerReceiveModel.getNote(),
                            currencyModel,
                            reqCustomerReceiveModel.getExchangeRate(),
                            requestMessage.entryCurrencyID
                    );

                    crJournalModel = CoreUtils.buildCrJournalEntry(
                            journalReferenceNo,
                            DefaultCOA.TradeDebtors.get(),
                            savedCustomerReceiveModel.getCustomerReceiveID(),
                            ReferenceType.CustomerReceipt.get(),
                            savedCustomerReceiveModel.getCustomerID(),
                            PartyType.Customer.get(),
                            totalDiscount,
                            savedCustomerReceiveModel.getNote(),
                            currencyModel,
                            reqCustomerReceiveModel.getExchangeRate(),
                            requestMessage.entryCurrencyID
                    );
                    this.journalEntry(drJournalModel, crJournalModel);
                }
                //=============== Journal for discount Entry End =============================


                savedCustomerReceiveModel.setTotalAmount(totalAmount);
                savedCustomerReceiveModel.setBaseCurrencyAmount(totalAmount);
                savedCustomerReceiveModel.setTotalDiscount(totalDiscount);
                savedCustomerReceiveModel.setUnAllocatedAmount(unAllocateAmount);
                savedCustomerReceiveModel = this.update(savedCustomerReceiveModel);


                //update VM for return
                vmCustomerReceive.customerReceiveModel = savedCustomerReceiveModel;
                vmCustomerReceive.customerReceiveDetailModelList = saveOrUpdatedCustomerRecDetailList;

                if (savedCustomerReceiveModel != null && saveOrUpdatedCustomerRecDetailList.size() > 0) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "Customer Receive Save Successfully";

                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to Save CustomerReceive";
                }
            } else {
                //Update Code

                // Update master table
                updatedCustomerReceiveModel = this.update(reqCustomerReceiveModel);
                totalAmount = 0.0;

                //================ Delete Journal before update detail table ========================
                Integer referenceID, referenceType;
                JournalModel journalModelForDelete = new JournalModel();

                journalModelList = new ArrayList<>();

                referenceID = updatedCustomerReceiveModel.getCustomerReceiveID();
                referenceType = ReferenceType.CustomerReceipt.get();

                journalModelForDelete.setReferenceID(referenceID);
                journalModelForDelete.setStatus(TillBoxAppEnum.Status.Active.get());
                journalModelForDelete.setReferenceType(referenceType);
                journalModelList = this.coreJournalBllManager.getAllByConditions(journalModelForDelete);

                for (JournalModel journalModel : journalModelList) {
                    this.coreJournalBllManager.softDelete(journalModel);
                }
                //=====================================================================================

                // Update detail table============================================================
                for (CustomerReceiveDetailModel item : reqCustomerReceiveDetailModelList) {


                    //======================= due calculation logic ==========================================================================
                    customerInvoiceModel = this.customerInvoiceBllManager.getByIdActiveStatus(item.getCustomerInvoiceID());
                    // if payment status is due then
                    if (customerInvoiceModel.getPaymentStatus() == PaymentStatus.Due.get()
                            || customerInvoiceModel.getPaymentStatus() == PaymentStatus.Partial.get()) {

                        whereConditionCusRecModel = new CustomerReceiveDetailModel();
                        whereConditionCusRecModel.setCustomerInvoiceID(item.getCustomerInvoiceID());
                        previousCustomerRecDetailModelList =
                                this.customerReceiveDetailBllManager.getAllByConditionWithActive(whereConditionCusRecModel);

                        for (CustomerReceiveDetailModel previousCustomerRecDetailModel : previousCustomerRecDetailModelList) {
                            previousPaymentTotal += previousCustomerRecDetailModel.getDiscount() + previousCustomerRecDetailModel.getAmount();
                            previousDiscount += previousCustomerRecDetailModel.getDiscount();
                        }

                        currentPayment = item.getAmount() + item.getDiscount();
                        currentPayment = currentPayment + previousPaymentTotal;


                        if (customerInvoiceModel.getTotalAmount().doubleValue() == currentPayment.doubleValue()) {
                            //set paid status (paid) in CustomerInvoic Table of Column (paymentStatus)
                            customerInvoiceModel.setPaymentStatus(PaymentStatus.Paid.get());
                            this.customerInvoiceBllManager.update(customerInvoiceModel);
                        } else if (customerInvoiceModel.getTotalAmount().doubleValue() > currentPayment.doubleValue()) {
                            //set paid status (Partial) in CustomerInvoic Table of Column (paymentStatus)
                            customerInvoiceModel.setPaymentStatus(PaymentStatus.Partial.get());
                            this.customerInvoiceBllManager.update(customerInvoiceModel);
                        }
                        if (customerInvoiceModel.getTotalAmount().doubleValue() < currentPayment.doubleValue()) {
                            // set paid status (paid) in CustomerInvoic Table of Column (paymentStatus)
                            // and calculate the excess amount and set is as unallocate amount in CustomerInvoic Table
                            customerInvoiceModel.setPaymentStatus(PaymentStatus.Paid.get());
                            this.customerInvoiceBllManager.update(customerInvoiceModel);
                            unAllocateAmount = currentPayment - customerInvoiceModel.getTotalAmount();
                        }

                    }
                    //======================= End Due calculation logic ======================================================================


                    //update detail table
                    temCustomerReceiveDetailModel = this.customerReceiveDetailBllManager.saveOrUpdate(item);
                    // Hold datail table data for return
                    saveOrUpdatedCustomerRecDetailList.add(temCustomerReceiveDetailModel);

                    amount = item.getAmount();
                    if (amount == 0.0 || amount == null)
                        amount = 0.0;
                    else
                        totalAmount += amount;

                    discount = item.getDiscount();
                    if (discount == 0 || discount == null)
                        discount = 0.0;
                    else
                        totalDiscount += discount;

                }

                if (totalAmount > 0.0) {
                    //=============== Journal Entry for amount  ============================
                    drJournalModel = CoreUtils.buildDrJournalEntry(
                            journalReferenceNo,
                            DefaultCOA.BankAccount.get(),
                            updatedCustomerReceiveModel.getCustomerReceiveID(),
                            ReferenceType.CustomerReceipt.get(),
                            null,
                            null,
                            totalAmount,
                            updatedCustomerReceiveModel.getNote(),
                            currencyModel,
                            reqCustomerReceiveModel.getExchangeRate(),
                            requestMessage.entryCurrencyID
                    );

                    crJournalModel = CoreUtils.buildCrJournalEntry(
                            journalReferenceNo,
                            DefaultCOA.TradeDebtors.get(),
                            updatedCustomerReceiveModel.getCustomerReceiveID(),
                            ReferenceType.CustomerReceipt.get(),
                            updatedCustomerReceiveModel.getCustomerID(),
                            PartyType.Customer.get(),
                            totalAmount,
                            updatedCustomerReceiveModel.getNote(),
                            currencyModel,
                            reqCustomerReceiveModel.getExchangeRate(),
                            requestMessage.entryCurrencyID
                    );
                    this.journalEntry(drJournalModel, crJournalModel);
                }
                //=============== Journal entry for amount end =============================


                //=============== Journal Entry for discount ===============================
                if (totalDiscount > 0.0) {
                    drJournalModel = CoreUtils.buildDrJournalEntry(
                            journalReferenceNo,
                            DefaultCOA.BankAccount.get(),
                            updatedCustomerReceiveModel.getCustomerReceiveID(),
                            ReferenceType.CustomerReceipt.get(),
                            null,
                            null,
                            totalDiscount,
                            updatedCustomerReceiveModel.getNote(),
                            currencyModel,
                            reqCustomerReceiveModel.getExchangeRate(),
                            requestMessage.entryCurrencyID
                    );

                    crJournalModel = CoreUtils.buildCrJournalEntry(
                            journalReferenceNo,
                            DefaultCOA.TradeDebtors.get(),
                            updatedCustomerReceiveModel.getCustomerReceiveID(),
                            ReferenceType.CustomerReceipt.get(),
                            updatedCustomerReceiveModel.getCustomerID(),
                            PartyType.Customer.get(),
                            totalDiscount,
                            updatedCustomerReceiveModel.getNote(),
                            currencyModel,
                            reqCustomerReceiveModel.getExchangeRate(),
                            requestMessage.entryCurrencyID
                    );
                    this.journalEntry(drJournalModel, crJournalModel);
                }
                //=============== Journal for discount Entry End =============================


                updatedCustomerReceiveModel.setTotalAmount(totalAmount);
                updatedCustomerReceiveModel.setBaseCurrencyAmount(totalAmount);
                updatedCustomerReceiveModel.setTotalDiscount(totalDiscount);
                updatedCustomerReceiveModel.setUnAllocatedAmount(unAllocateAmount);
                updatedCustomerReceiveModel = this.update(updatedCustomerReceiveModel);


                vmCustomerReceive.customerReceiveModel = updatedCustomerReceiveModel;
                vmCustomerReceive.customerReceiveDetailModelList = saveOrUpdatedCustomerRecDetailList;


                //updatedVMCustomerReceive = new VMCustomerReceive();
                if (updatedCustomerReceiveModel != null && saveOrUpdatedCustomerRecDetailList.size() > 0) {
                    Core.clientMessage.get().userMessage = "CustomerReceive Update Successfully";
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to Update CustomerReceive";
                }
            }


            bllResponseMessage.responseObject = vmCustomerReceive;
            bllResponseMessage.responseCode = Core.clientMessage.get().messageCode;
            bllResponseMessage.message = Core.clientMessage.get().userMessage;

        } catch (Exception ex) {
            log.error("CustomerReceiveBllManager -> saveOrUpdate got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return bllResponseMessage;
    }

    public List<CustomerReceiveModel> search(CustomerReceiveModel reqCustomerReceiveModel) throws Exception {
        List<CustomerReceiveModel> findCustomerReceiveList;
        try {
            findCustomerReceiveList = this.getAllByConditions(reqCustomerReceiveModel);
            if (findCustomerReceiveList.size() > 0) {
                //Core.clientMessage.get().userMessage = "Find the request CustomerReceive";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                Core.clientMessage.get().message = "Failed to find the requested CustomerReceive";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("CustomerReceiveBllManager -> search got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return findCustomerReceiveList;
    }

    public Integer deleteByConditions(RequestMessage requestMessage) throws Exception {
        CustomerReceiveModel req_CustomerReceiveModel =
                Core.getRequestObject(requestMessage, CustomerReceiveModel.class);
        Integer numberOfDeleteRow = 0;
        try {
            numberOfDeleteRow = this.deleteByConditions(req_CustomerReceiveModel);
            if (numberOfDeleteRow > 0) {
                //Core.clientMessage.get().userMessage = "Successfully deleted the requested CustomerReceive";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                Core.clientMessage.get().message = "Failed to deleted the requested CustomerReceive";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("CustomerReceiveBllManager -> deleteByConditions got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return numberOfDeleteRow;
    }


    public CustomerReceiveModel inActive(RequestMessage requestMessage) throws Exception {
        CustomerReceiveModel reqCustomerReceiveModel =
                Core.getRequestObject(requestMessage, CustomerReceiveModel.class);
        CustomerReceiveModel _CustomerReceiveModel = null;
        try {
            if (reqCustomerReceiveModel != null) {
                _CustomerReceiveModel = this.inActive(reqCustomerReceiveModel);
                if (_CustomerReceiveModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    //Core.clientMessage.get().userMessage = "Successfully inactive the requested CustomerReceive";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to inactive the requested CustomerReceive";
                }
            }

        } catch (Exception ex) {
            log.error("CustomerReceiveBllManager -> inActive got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return _CustomerReceiveModel;
    }


    public CustomerReceiveModel delete(CustomerReceiveModel reqCustomerReceiveModel) throws Exception {
        CustomerReceiveModel deletedCustomerReceiveModel = null;
        try {
            if (reqCustomerReceiveModel != null) {
                deletedCustomerReceiveModel = this.softDelete(reqCustomerReceiveModel);
                if (deletedCustomerReceiveModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    //Core.clientMessage.get().userMessage = "Successfully deleted the requested CustomerReceive";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to deleted the requested CustomerReceive";
                }
            }

        } catch (Exception ex) {
            log.error("CustomerReceiveBllManager -> delete got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return deletedCustomerReceiveModel;
    }

    public CustomerReceiveModel getByReqId(CustomerReceiveModel reqCustomerReceiveModel) throws Exception {
        Integer primaryKeyValue = reqCustomerReceiveModel.getCustomerReceiveID();
        CustomerReceiveModel foundCustomerReceiveModel = null;
        try {
            if (primaryKeyValue != null) {
                foundCustomerReceiveModel = this.getById(primaryKeyValue);
                if (foundCustomerReceiveModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "Get the requested CustomerReceive successfully";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to the requested CustomerReceive";
                }
            }

        } catch (Exception ex) {
            log.error("CustomerReceiveBllManager -> getByID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return foundCustomerReceiveModel;
    }


    private void journalEntry(JournalModel drJournalModel, JournalModel crJournalModel) throws Exception {
        Boolean isCompleted = false;
        List<JournalModel> journalModelList = new ArrayList<>();
        journalModelList.add(drJournalModel);
        journalModelList.add(crJournalModel);

        isCompleted = this.coreJournalBllManager.saveOrUpdate(journalModelList);
        if (!isCompleted) {
            throw new Exception(Core.clientMessage.get().userMessage);
        }
    }
}