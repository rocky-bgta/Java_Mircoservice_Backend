/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 27-Feb-18
 * Time: 5:24 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.MessageModel.BllResponseMessage;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
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
import nybsys.tillboxweb.entities.SupplierPayment;
import nybsys.tillboxweb.enumpurches.PaymentStatus;
import nybsys.tillboxweb.models.SupplierInvoiceModel;
import nybsys.tillboxweb.models.SupplierPaymentDetailModel;
import nybsys.tillboxweb.models.SupplierPaymentModel;
import nybsys.tillboxweb.models.VMSupplierPaymentModel;
import nybsys.tillboxweb.service.manager.SupplierAdjustmentServiceManager;
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
public class SupplierPaymentBllManager extends BaseBll<SupplierPayment> {

    private static final Logger log = LoggerFactory.getLogger(SupplierPaymentBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(SupplierPayment.class);
        Core.runTimeModelType.set(SupplierPaymentModel.class);
    }

    @Autowired
    private SupplierPaymentDetailBllManager supplierPaymentDetailBllManager;

    @Autowired
    private CoreJournalBllManager coreJournalBllManager;

    @Autowired
    private SupplierInvoiceBllManager supplierInvoiceBllManager;

    private SupplierAdjustmentServiceManager supplierAdjustmentServiceManager = new SupplierAdjustmentServiceManager();


    public BllResponseMessage saveOrUpdate(RequestMessage requestMessage) throws Exception {
        RequestMessage reqMessageForBllManager;
        ResponseMessage responseMessage;
        BllResponseMessage bllResponseMessage = new BllResponseMessage();
        CurrencyModel currencyModel;

        VMSupplierPaymentModel vmSupplierPaymentModel;
        JournalModel drJournalModel, crJournalModel;

        vmSupplierPaymentModel = Core.getRequestObject(requestMessage, VMSupplierPaymentModel.class);
        SupplierPaymentDetailModel saveOrUpdateSupplierPaymentDetailModel;
        List<SupplierPaymentDetailModel> saveOrUpdateSupplierPaymentDetailModelList = new ArrayList<>();
        List<SupplierPaymentDetailModel> previousSupPayDetailModelList;

        SupplierPaymentDetailModel whereConditionSupPayDetailModel;
        SupplierInvoiceModel supplierInvoiceModel;

        //get base currency and exchange rate
        currencyModel = this.supplierAdjustmentServiceManager.getBaseCurrency();
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
        SupplierPaymentModel supplierPaymentModel
                = vmSupplierPaymentModel.supplierPaymentModel;
        List<SupplierPaymentDetailModel> supplierPaymentDetailModelList
                = vmSupplierPaymentModel.supplierPaymentDetailModelList;
        // Extract data from VM =======================================

        Integer supplierPaymentID = supplierPaymentModel.getSupplierPaymentID();
        SupplierPaymentModel savedSupplierPaymentModel, updateSupplierPaymentModel = null;

        List<SupplierPaymentModel> supplierPaymentModelList = null;
        String supplierInvoiceNo = "0", buildSupplierInvoiceNo;
        Integer invoiceNo = 0, invoiceNoLength = 0, zeroFillLength = 0;
        String hsql, buildDbSequence, currentDBSequence = null;
        String journalReferenceNo;
        Double amount = 0.0;
        Double discount = 0.0, previousDiscount = 0.0;
        Double unAllocateAmount = 0.0;
        Double totalAmount = 0.0;
        Double totalDiscount = 0.0;
        Double previousPaymentTotal = 0.0, currentPayment = 0.0;

        try {
            journalReferenceNo = TillBoxUtils.getUUID();
            List<JournalModel> journalModelList = new ArrayList<>();

            // ============================= Create PMT0000001 =============================
            hsql = hsql = "SELECT e FROM SupplierPayment e ORDER BY e.supplierPaymentID DESC";
            supplierPaymentModelList = this.executeHqlQuery(hsql, SupplierPaymentModel.class, TillBoxAppEnum.QueryType.GetOne.get());
            if (supplierPaymentModelList.size() > 0) {
                currentDBSequence = supplierPaymentModelList.get(0).getSupplierInvoiceNo();
            }
            buildDbSequence = CoreUtils.getSequence(currentDBSequence, "PMT");
            // ==========================End Create PMT0000001 =============================


            if (supplierPaymentID == null || supplierPaymentID == 0) {
                // Save Code
                supplierPaymentModel.setSupplierInvoiceNo(buildDbSequence);
                supplierPaymentModel.setDocNumber(buildDbSequence);

                //Save master table data
                savedSupplierPaymentModel = this.save(supplierPaymentModel);

                // save supplier payment details ============================================
                for (SupplierPaymentDetailModel item : supplierPaymentDetailModelList) {

                    //======================= due calculation logic ==========================================================================
                    supplierInvoiceModel = this.supplierInvoiceBllManager.getByIdActiveStatus(item.getSupplierInvoiceID());
                    // if payment status is due then
                    if (supplierInvoiceModel.getPaymentStatus() == PaymentStatus.Due.get()
                            || supplierInvoiceModel.getPaymentStatus() == PaymentStatus.Partial.get()) {

                        whereConditionSupPayDetailModel = new SupplierPaymentDetailModel();
                        whereConditionSupPayDetailModel.setSupplierInvoiceID(item.getSupplierInvoiceID());
                        previousSupPayDetailModelList =
                                this.supplierPaymentDetailBllManager.getAllByConditionWithActive(whereConditionSupPayDetailModel);

                        for (SupplierPaymentDetailModel previousSupPaymentDetailModel : previousSupPayDetailModelList) {
                            previousPaymentTotal += previousSupPaymentDetailModel.getDiscount() + previousSupPaymentDetailModel.getPaidAmount();
                            previousDiscount += previousSupPaymentDetailModel.getDiscount();
                        }

                        currentPayment = item.getPaidAmount() + item.getDiscount();
                        currentPayment = currentPayment + previousPaymentTotal;


                        if (supplierInvoiceModel.getTotalAmount().doubleValue() == currentPayment.doubleValue()) {
                            //set paid status (paid) in SupplierInvoice Table of Column (paymentStatus)
                            supplierInvoiceModel.setPaymentStatus(PaymentStatus.Paid.get());
                            this.supplierInvoiceBllManager.update(supplierInvoiceModel);
                        } else if (supplierInvoiceModel.getTotalAmount().doubleValue() > currentPayment.doubleValue()) {
                            //set paid status (Partial) in SupplierInvoice Table of Column (paymentStatus)
                            supplierInvoiceModel.setPaymentStatus(PaymentStatus.Partial.get());
                            this.supplierInvoiceBllManager.update(supplierInvoiceModel);
                        }
                        if (supplierInvoiceModel.getTotalAmount().doubleValue() < currentPayment.doubleValue()) {
                            // set paid status (paid) in SupplierInvoice Table of Column (paymentStatus)
                            // and calculate the excess amount and set is as unallocate amount in supplierPayment Table
                            supplierInvoiceModel.setPaymentStatus(PaymentStatus.Paid.get());
                            this.supplierInvoiceBllManager.update(supplierInvoiceModel);
                            unAllocateAmount = currentPayment - supplierInvoiceModel.getTotalAmount();
                        }

                    }
                    //======================= End Due calculation logic ======================================================================


                    item.setSupplierPaymentID(savedSupplierPaymentModel.getSupplierPaymentID());
                    reqMessageForBllManager = this.getDefaultRequestMessage();
                    reqMessageForBllManager.requestObj = item;
                    //Save detail table data
                    saveOrUpdateSupplierPaymentDetailModel = this.supplierPaymentDetailBllManager.saveOrUpdate(reqMessageForBllManager);

                    // Hold datail table data for return
                    saveOrUpdateSupplierPaymentDetailModelList.add(saveOrUpdateSupplierPaymentDetailModel);


                    amount = item.getPaidAmount();
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
                // save supplier payment details End ====== for loop end ==================================


                //if(amount>0.0) {
                if (totalAmount > 0.0) {
                    //=============== Journal Entry for amount  ============================
                    drJournalModel = CoreUtils.buildDrJournalEntry(
                            journalReferenceNo,
                            DefaultCOA.TradeCreditors.get(),
                            savedSupplierPaymentModel.getSupplierPaymentID(),
                            ReferenceType.SupplierPayment.get(),
                            savedSupplierPaymentModel.getSupplierID(),
                            PartyType.Supplier.get(),
                            totalAmount,
                            savedSupplierPaymentModel.getDescription(),
                            currencyModel,
                            savedSupplierPaymentModel.getExchangeRate(),
                            requestMessage.entryCurrencyID
                    );

                    crJournalModel = CoreUtils.buildCrJournalEntry(
                            journalReferenceNo,
                            DefaultCOA.BankAccount.get(),
                            savedSupplierPaymentModel.getSupplierPaymentID(),
                            ReferenceType.SupplierPayment.get(),
                            null,
                            null,
                            totalAmount,
                            savedSupplierPaymentModel.getDescription(),
                            currencyModel,
                            savedSupplierPaymentModel.getExchangeRate(),
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
                            DefaultCOA.TradeCreditors.get(),
                            savedSupplierPaymentModel.getSupplierPaymentID(),
                            ReferenceType.SupplierPayment.get(),
                            savedSupplierPaymentModel.getSupplierID(),
                            PartyType.Supplier.get(),
                            totalDiscount,
                            savedSupplierPaymentModel.getDescription(),
                            currencyModel,
                            savedSupplierPaymentModel.getExchangeRate(),
                            requestMessage.entryCurrencyID
                    );

                    crJournalModel = CoreUtils.buildCrJournalEntry(
                            journalReferenceNo,
                            DefaultCOA.DiscountEarn.get(),
                            savedSupplierPaymentModel.getSupplierPaymentID(),
                            ReferenceType.SupplierPayment.get(),
                            null,
                            null,
                            totalDiscount,
                            savedSupplierPaymentModel.getDescription(),
                            currencyModel,
                            savedSupplierPaymentModel.getExchangeRate(),
                            requestMessage.entryCurrencyID
                    );
                    this.journalEntry(drJournalModel, crJournalModel);
                }
                //=============== Journal for discount Entry End =============================


                //totalAmount = totalAmount + unAllocateAmount;

                savedSupplierPaymentModel.setPaidAmount(totalAmount);
                savedSupplierPaymentModel.setBaseCurrencyAmount(totalAmount);
                savedSupplierPaymentModel.setDiscount(totalDiscount);
                savedSupplierPaymentModel.setUnAllocatedAmount(unAllocateAmount);
                savedSupplierPaymentModel = this.update(savedSupplierPaymentModel);

                //update VM for return
                vmSupplierPaymentModel.supplierPaymentModel = savedSupplierPaymentModel;
                vmSupplierPaymentModel.supplierPaymentDetailModelList = saveOrUpdateSupplierPaymentDetailModelList;

                if (savedSupplierPaymentModel != null && saveOrUpdateSupplierPaymentDetailModelList.size() > 0) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "SupplierPayment Save Successfully";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to Save SupplierPayment";
                }
            } else {
                // Update Code
                updateSupplierPaymentModel = this.update(supplierPaymentModel);
                totalAmount = 0.0;

                //================ Delete Journal before update =====================================
                Integer referenceID, referenceType;
                JournalModel journalModelForDelete = new JournalModel();

                journalModelList = new ArrayList<>();

                referenceID = updateSupplierPaymentModel.getSupplierPaymentID();
                referenceType = ReferenceType.SupplierPayment.get();

                journalModelForDelete.setReferenceID(referenceID);
                journalModelForDelete.setStatus(TillBoxAppEnum.Status.Active.get());
                journalModelForDelete.setReferenceType(referenceType);
                journalModelList = this.coreJournalBllManager.getAllByConditions(journalModelForDelete);

                for (JournalModel journalModel : journalModelList) {
                    this.coreJournalBllManager.softDelete(journalModel);
                }
                //===================================================================================

                // Update supplier payment details ==================================================
                for (SupplierPaymentDetailModel item : supplierPaymentDetailModelList) {


                    //======================= due calculation logic =========================================================================
                    supplierInvoiceModel = this.supplierInvoiceBllManager.getByIdActiveStatus(item.getSupplierInvoiceID());
                    // if payment status is due then
                    if (supplierInvoiceModel.getPaymentStatus() == PaymentStatus.Due.get()
                            || supplierInvoiceModel.getPaymentStatus() == PaymentStatus.Partial.get()) {

                        whereConditionSupPayDetailModel = new SupplierPaymentDetailModel();
                        whereConditionSupPayDetailModel.setSupplierInvoiceID(item.getSupplierInvoiceID());
                        previousSupPayDetailModelList =
                                this.supplierPaymentDetailBllManager.getAllByConditionWithActive(whereConditionSupPayDetailModel);

                        for (SupplierPaymentDetailModel previousSupPaymentDetailModel : previousSupPayDetailModelList) {
                            previousPaymentTotal += previousSupPaymentDetailModel.getDiscount() + previousSupPaymentDetailModel.getPaidAmount();
                            previousDiscount += previousSupPaymentDetailModel.getDiscount();
                        }

                        currentPayment = item.getPaidAmount() + item.getDiscount();
                        currentPayment = currentPayment + previousPaymentTotal;


                        if (supplierInvoiceModel.getTotalAmount().doubleValue() == currentPayment.doubleValue()) {
                            //set paid status (paid) in SupplierInvoice Table of Column (paymentStatus)
                            supplierInvoiceModel.setPaymentStatus(PaymentStatus.Paid.get());
                            this.supplierInvoiceBllManager.update(supplierInvoiceModel);
                        } else if (supplierInvoiceModel.getTotalAmount().doubleValue() > currentPayment.doubleValue()) {
                            //set paid status (Partial) in SupplierInvoice Table of Column (paymentStatus)
                            supplierInvoiceModel.setPaymentStatus(PaymentStatus.Partial.get());
                            this.supplierInvoiceBllManager.update(supplierInvoiceModel);
                        }
                        if (supplierInvoiceModel.getTotalAmount().doubleValue() < currentPayment.doubleValue()) {
                            // set paid status (paid) in SupplierInvoice Table of Column (paymentStatus)
                            // and calculate the excess amount and set is as unallocate amount in supplierPayment Table
                            supplierInvoiceModel.setPaymentStatus(PaymentStatus.Paid.get());
                            this.supplierInvoiceBllManager.update(supplierInvoiceModel);
                            unAllocateAmount = currentPayment - supplierInvoiceModel.getTotalAmount();
                        }

                    }
                    //======================= End Due calculation logic ======================================================================

                    item.setSupplierPaymentID(updateSupplierPaymentModel.getSupplierPaymentID());
                    reqMessageForBllManager = this.getDefaultRequestMessage();
                    reqMessageForBllManager.requestObj = item;
                    //Save detail table data
                    saveOrUpdateSupplierPaymentDetailModel = this.supplierPaymentDetailBllManager.saveOrUpdate(reqMessageForBllManager);

                    // Hold datail table data for return
                    saveOrUpdateSupplierPaymentDetailModelList.add(saveOrUpdateSupplierPaymentDetailModel);


                    amount = item.getPaidAmount();
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
                // update supplier payment details End ====== for loop end ==================================


                if (totalAmount > 0.0) {
                    //=============== Journal Entry for amount  ============================
                    drJournalModel = CoreUtils.buildDrJournalEntry(
                            journalReferenceNo,
                            DefaultCOA.TradeCreditors.get(),
                            updateSupplierPaymentModel.getSupplierPaymentID(),
                            ReferenceType.SupplierPayment.get(),
                            updateSupplierPaymentModel.getSupplierID(),
                            PartyType.Supplier.get(),
                            totalAmount,
                            updateSupplierPaymentModel.getDescription(),
                            currencyModel,
                            updateSupplierPaymentModel.getExchangeRate(),
                            requestMessage.entryCurrencyID
                    );

                    crJournalModel = CoreUtils.buildCrJournalEntry(
                            journalReferenceNo,
                            DefaultCOA.BankAccount.get(),
                            updateSupplierPaymentModel.getSupplierPaymentID(),
                            ReferenceType.SupplierPayment.get(),
                            null,
                            null,
                            totalAmount,
                            updateSupplierPaymentModel.getDescription(),
                            currencyModel,
                            updateSupplierPaymentModel.getExchangeRate(),
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
                            DefaultCOA.TradeCreditors.get(),
                            updateSupplierPaymentModel.getSupplierPaymentID(),
                            ReferenceType.SupplierPayment.get(),
                            updateSupplierPaymentModel.getSupplierID(),
                            PartyType.Supplier.get(),
                            totalDiscount,
                            updateSupplierPaymentModel.getDescription(),
                            currencyModel,
                            updateSupplierPaymentModel.getExchangeRate(),
                            requestMessage.entryCurrencyID
                    );

                    crJournalModel = CoreUtils.buildCrJournalEntry(
                            journalReferenceNo,
                            DefaultCOA.DiscountEarn.get(),
                            updateSupplierPaymentModel.getSupplierPaymentID(),
                            ReferenceType.SupplierPayment.get(),
                            null,
                            null,
                            totalDiscount,
                            updateSupplierPaymentModel.getDescription(),
                            currencyModel,
                            updateSupplierPaymentModel.getExchangeRate(),
                            requestMessage.entryCurrencyID
                    );
                    this.journalEntry(drJournalModel, crJournalModel);
                }
                //=============== Journal for discount Entry End =============================


                updateSupplierPaymentModel.setPaidAmount(totalAmount);
                updateSupplierPaymentModel.setBaseCurrencyAmount(totalAmount);
                updateSupplierPaymentModel.setDiscount(totalDiscount);
                updateSupplierPaymentModel.setUnAllocatedAmount(unAllocateAmount);
                updateSupplierPaymentModel = this.update(updateSupplierPaymentModel);


                //update VM for return
                vmSupplierPaymentModel.supplierPaymentModel = updateSupplierPaymentModel;
                vmSupplierPaymentModel.supplierPaymentDetailModelList = saveOrUpdateSupplierPaymentDetailModelList;

                // Update supplier payment details ==============================================
                if (updateSupplierPaymentModel != null && saveOrUpdateSupplierPaymentDetailModelList.size() > 0) {
                    Core.clientMessage.get().userMessage = "Supplier Payment Update Successfully";
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to Update Supplier Payment";
                }
            }


            bllResponseMessage.responseObject = vmSupplierPaymentModel;
            bllResponseMessage.responseCode = Core.clientMessage.get().messageCode;
            bllResponseMessage.message = Core.clientMessage.get().userMessage;


        } catch (Exception ex) {
            log.error("SupplierPaymentBllManager -> saveOrUpdate got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        //return savedSupplierPaymentModel;
        return bllResponseMessage;
    }


    public Integer deleteByConditions(RequestMessage requestMessage) throws Exception {
        SupplierPaymentModel castRequestModel =
                Core.getRequestObject(requestMessage, SupplierPaymentModel.class);
        Integer numberOfDeleteRow = 0;
        try {
            numberOfDeleteRow = this.deleteByConditions(castRequestModel);
            if (numberOfDeleteRow > 0) {
                Core.clientMessage.get().userMessage = "Successfully deleted the requested SupplierPayment";
            } else {
                Core.clientMessage.get().message = "Failed to deleted the requested SupplierPayment";
            }
        } catch (Exception ex) {
            log.error("SupplierPaymentBllManager -> deleteByConditions got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return numberOfDeleteRow;
    }

    public SupplierPaymentModel inActive(RequestMessage requestMessage) throws Exception {
        SupplierPaymentModel castRequestModel =
                Core.getRequestObject(requestMessage, SupplierPaymentModel.class);
        SupplierPaymentModel processedModel = null;
        try {
            if (castRequestModel != null) {
                processedModel = this.inActive(castRequestModel);
                if (processedModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "Successfully inactive the requested SupplierPayment";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to inactive the requested SupplierPayment";
                }
            }

        } catch (Exception ex) {
            log.error("SupplierPaymentBllManager -> inActive got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return processedModel;
    }


    public SupplierPaymentModel delete(RequestMessage requestMessage) throws Exception {
        SupplierPaymentModel castRequestModel =
                Core.getRequestObject(requestMessage, SupplierPaymentModel.class);
        SupplierPaymentModel processedModel = null;
        try {
            if (castRequestModel != null) {
                processedModel = this.softDelete(castRequestModel);
                if (processedModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "Successfully deleted the requested SupplierPayment";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to deleted the requested SupplierPayment";
                }
            }

        } catch (Exception ex) {
            log.error("SupplierPaymentBllManager -> delete got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return processedModel;
    }

    /*private void journalEntry(Double amount, String journalReferenceNo,Integer referenceID) throws Exception {
        List<JournalModel> journalModelList = new ArrayList<>();
        //=============== Journal Entry for detail table for amount ============
        if(amount>0.0){

            JournalModel drJournalModel = CoreUtils.buildDrJournalEntry(
                    journalReferenceNo,
                    DefaultCOA.TradeCreditors.get(),
                    referenceID,
                    ReferenceType.SupplierPayment.get(),
                    referenceID,
                    PartyType.Supplier.get(),
                    amount,
                    null


            );

            JournalModel crJournalModel = CoreUtils.buildDrJournalEntry(
                    journalReferenceNo,
                    DefaultCOA.DiscountEarn.get(),
                    referenceID,
                    ReferenceType.SupplierPayment.get(),
                    null, //supPaymentModel.getSupplierPaymentID(),
                    null,//PartyType.Supplier.get(),
                    amount,
                    null
            );

            journalModelList.add(drJournalModel);
            journalModelList.add(crJournalModel);

            this.coreJournalBllManager.saveOrUpdate(journalModelList);
        }
        //=============== Journal Entry End ============
    }*/

   /*

    private ResponseMessage journalEntry(SupplierPaymentModel modelForJournalEntry,String journalReferenceNo) throws Exception {
        //build dr model
        //user-input (Bank-accountID)
        // DefaultCOA.TradeCreditors.get()  dr
        // user input accountID             cr

        List<JournalModel> lstJournalModel = new ArrayList<>();
        JournalModel drJournalModel = new JournalModel();
        JournalModel crJournalModel = new JournalModel();


        drJournalModel.setJournalReferenceNo(journalReferenceNo);
        drJournalModel.setAccountID(DefaultCOA.TradeCreditors.get());
        drJournalModel.setBusinessID(Core.businessId.get());
        drJournalModel.setPartyID(modelForJournalEntry.getSupplierID());
        drJournalModel.setPartyType(PartyType.Supplier.get());
        drJournalModel.setAmount(modelForJournalEntry.getPaidAmount());
        drJournalModel.setDrCrIndicator(DebitCreditIndicator.Debit.get());
        drJournalModel.setReferenceID(modelForJournalEntry.getSupplierPaymentID());
        drJournalModel.setReferenceType(ReferenceType.SupplierPayment.get());
        drJournalModel.setDate(new Date());


        crJournalModel.setJournalReferenceNo(journalReferenceNo);
        crJournalModel.setAccountID(modelForJournalEntry.getAccountID());
        crJournalModel.setAmount(modelForJournalEntry.getPaidAmount());
        crJournalModel.setBusinessID(Core.businessId.get());
        crJournalModel.setDrCrIndicator(DebitCreditIndicator.Credit.get());
        //crJournalModel.setPartyID(modelForJournalEntry.getSupplierID());
        //crJournalModel.setPartyType(PartyType.Supplier.get());
        crJournalModel.setReferenceID(modelForJournalEntry.getSupplierPaymentID());
        crJournalModel.setReferenceType(ReferenceType.SupplierPayment.get());
        crJournalModel.setDate(new Date());

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
        requestMessage.businessID = Core.businessId.get();
        vmJournalListModel.lstJournalModel = lstJournalModel;
        requestMessage.requestObj = vmJournalListModel;
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