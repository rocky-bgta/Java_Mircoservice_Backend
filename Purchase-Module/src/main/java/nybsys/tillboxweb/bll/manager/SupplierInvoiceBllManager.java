/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/23/2018
 * Time: 4:20 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.coreEnum.DebitCreditIndicator;
import nybsys.tillboxweb.coreEnum.PartyType;
import nybsys.tillboxweb.coreModels.DebitCreditBalanceModel;
import nybsys.tillboxweb.coreModels.VMSupplierStatementModel;
import nybsys.tillboxweb.entities.SupplierInvoice;
import nybsys.tillboxweb.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SupplierInvoiceBllManager extends BaseBll<SupplierInvoice> {

    private static final Logger log = LoggerFactory.getLogger(SupplierInvoiceBllManager.class);


    private SupplierInvoiceDetailBllManager supplierInvoiceDetailBllManager = new SupplierInvoiceDetailBllManager();


    private SupplierReturnBllManager supplierReturnBllManager = new SupplierReturnBllManager();

    private SupplierPaymentBllManager supplierPaymentBllManager = new SupplierPaymentBllManager();

    private SupplierPaymentDetailBllManager supplierPaymentDetailBllManager = new SupplierPaymentDetailBllManager();

    private SupplierReturnDetailBllManager supplierReturnDetailBllManager = new SupplierReturnDetailBllManager();

    private SupplierAdjustmentDetailBllManager supplierAdjustmentDetailBllManager = new SupplierAdjustmentDetailBllManager();

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(SupplierInvoice.class);
        Core.runTimeModelType.set(SupplierInvoiceModel.class);
    }


    public VMSupplierInvoice saveSupplierInvoice(VMSupplierInvoice vmSupplierInvoice) throws Exception {

        try {
            if (isValidSupplierInvoice(vmSupplierInvoice)) {
                if (vmSupplierInvoice.supplierInvoiceModel.getSupplierInvoiceID() != null && vmSupplierInvoice.supplierInvoiceModel.getSupplierInvoiceID() > 0) {

                    vmSupplierInvoice.supplierInvoiceModel.setUpdatedDate(new Date());
                    vmSupplierInvoice.supplierInvoiceModel = this.update(vmSupplierInvoice.supplierInvoiceModel);

              /* detail save*/

                    SupplierInvoiceDetailModel searchInvoiceDetail = new SupplierInvoiceDetailModel();
                    searchInvoiceDetail.setSupplierInvoiceID(vmSupplierInvoice.supplierInvoiceModel.getSupplierInvoiceID());
                    List<SupplierInvoiceDetailModel> lstExistingSupplierInvoiceDetailModel = new ArrayList<>();
                    lstExistingSupplierInvoiceDetailModel = this.supplierInvoiceDetailBllManager.getAllByConditions(searchInvoiceDetail);
                    for (SupplierInvoiceDetailModel supplierInvoiceDetailModel : lstExistingSupplierInvoiceDetailModel) {
                        supplierInvoiceDetailModel.setUpdatedDate(new Date());
                        supplierInvoiceDetailModel.setStatus(TillBoxAppEnum.Status.Deleted.get());
                        supplierInvoiceDetailModel = this.supplierInvoiceDetailBllManager.update(supplierInvoiceDetailModel);
                    }
                    this.supplierInvoiceDetailBllManager.saveSupplierInvoiceDetail(vmSupplierInvoice);
                } else {
                    vmSupplierInvoice.supplierInvoiceModel.setStatus(TillBoxAppEnum.Status.Active.get());
                    vmSupplierInvoice.supplierInvoiceModel.setCreatedBy("");
                    vmSupplierInvoice.supplierInvoiceModel.setCreatedDate(new Date());
                    vmSupplierInvoice.supplierInvoiceModel = this.save(vmSupplierInvoice.supplierInvoiceModel);

               /* detail save*/
                    this.supplierInvoiceDetailBllManager.saveSupplierInvoiceDetail(vmSupplierInvoice);
                }

            }


        } catch (Exception ex) {
            log.error("SupplierInvoiceBllManager -> SupplierInvoiceBllManager got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return vmSupplierInvoice;
    }


    public List<VMSupplierInvoice> searchVMSupplierInvoice(SupplierInvoiceModel supplierInvoiceModel) throws Exception {

        List<VMSupplierInvoice> lstVMSupplierInvoice = new ArrayList<>();
        try {
            List<SupplierInvoiceModel> lstSupplierInvoiceModel = new ArrayList<>();
            if (supplierInvoiceModel.getStatus() == null || supplierInvoiceModel.getStatus() == 0) {
                supplierInvoiceModel.setStatus(TillBoxAppEnum.Status.Active.get());
            }

            lstSupplierInvoiceModel = this.getAllByConditions(supplierInvoiceModel);

            if (lstSupplierInvoiceModel.size() > 0) {
                for (SupplierInvoiceModel supplierInvoiceModel1 : lstSupplierInvoiceModel) {
                    VMSupplierInvoice vmSupplierInvoice = new VMSupplierInvoice();
                    vmSupplierInvoice.supplierInvoiceModel = supplierInvoiceModel1;
                    SupplierInvoiceDetailModel searchSupplierInvoiceModel = new SupplierInvoiceDetailModel();
                    searchSupplierInvoiceModel.setSupplierInvoiceID(supplierInvoiceModel1.getSupplierInvoiceID());
                    searchSupplierInvoiceModel.setStatus(TillBoxAppEnum.Status.Active.get());
                    vmSupplierInvoice.lstSupplierInvoiceDetailModel = this.supplierInvoiceDetailBllManager.getAllByConditions(searchSupplierInvoiceModel);
                    lstVMSupplierInvoice.add(vmSupplierInvoice);
                }
            }

        } catch (Exception ex) {
            log.error("SupplierBllManager -> searchSupplier got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstVMSupplierInvoice;
    }


    private Boolean isValidSupplierInvoice(VMSupplierInvoice vmSupplierInvoice) throws Exception {
        SupplierInvoiceModel existingSupplierInvoiceModel = new SupplierInvoiceModel();
        existingSupplierInvoiceModel.setSupplierInvoiceNo(vmSupplierInvoice.supplierInvoiceModel.getSupplierInvoiceNo());
        List<SupplierInvoiceModel> lstSupplierInvoiceModel = new ArrayList<>();


        lstSupplierInvoiceModel = this.getAllByConditions(existingSupplierInvoiceModel);


        if (lstSupplierInvoiceModel.size() > 0) {
            existingSupplierInvoiceModel = lstSupplierInvoiceModel.get(0);
            if ((existingSupplierInvoiceModel.getSupplierInvoiceID() != null && existingSupplierInvoiceModel.getSupplierInvoiceID() > 0) && existingSupplierInvoiceModel.getSupplierInvoiceID().intValue() != vmSupplierInvoice.supplierInvoiceModel.getSupplierInvoiceID().intValue()) {

                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().userMessage = MessageConstant.DUPLICATE_SUPPLIER_INVOICE_NUMBER;
                return false;
            }
        }

        return true;
    }


    public Double getPartyBalance(Integer partyType, Integer partyID, Integer businessID) throws Exception {
        List<DebitCreditBalanceModel> lstDebitCreditBalanceModel = new ArrayList<>();

        Double debitAmountSum = 0.0, creditAmountSum = 0.0, balance = 0.0;
        try {

            String hql = "SELECT sum(J.amount) as  amountSum, J.drCrIndicator as drCrIndicator FROM Journal J WHERE J.status = " + TillBoxAppEnum.Status.Active.get() + " AND J.businessID = " + businessID + " AND J.partyID = " + partyID + " AND J.partyType = " + partyType + " GROUP BY J.drCrIndicator";
            lstDebitCreditBalanceModel = this.executeHqlQuery(hql, DebitCreditBalanceModel.class, TillBoxAppEnum.QueryType.Join.get());
            if (lstDebitCreditBalanceModel.size() > 0) {
                for (DebitCreditBalanceModel journalBalanceResult : lstDebitCreditBalanceModel) {
                    if (journalBalanceResult.drCrIndicator == DebitCreditIndicator.Debit.get()) {
                        debitAmountSum = journalBalanceResult.amountSum;
                    } else if (journalBalanceResult.drCrIndicator == DebitCreditIndicator.Credit.get()) {
                        creditAmountSum = journalBalanceResult.amountSum;
                    }
                }
            }
            balance = debitAmountSum - creditAmountSum;

        } catch (Exception ex) {
            log.error("SupplierInvoiceBllManager -> get party balance got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return balance;
    }


    public Double getSupplierOpeningBalance(Integer partyType, Integer partyID, Integer businessID, Date toDate) throws Exception {
        List<DebitCreditBalanceModel> lstDebitCreditBalanceModel = new ArrayList<>();

        Double debitAmountSum = 0.0, creditAmountSum = 0.0, balance = 0.0;
        try {

            String newToDate = new SimpleDateFormat("yyyy-MM-dd").format(toDate) + " 23:59:59";

//            String hql = "SELECT\n" +
//                    "\"public\".\"Journal\".\"businessID\",\n" +
//                    "\"public\".\"Journal\".\"accountID\",\n" +
//                    "\"public\".\"Journal\".\"drCrIndicator\",\n" +
//                    "Sum( \"public\".\"Journal\".amount) amount\n" +
//                    "FROM\n" +
//                    "\t\"public\".\"Journal\"\n" +
//                    "WHERE\n" +
//                    "\"public\".\"Journal\".\"date\" BETWEEN '2016-03-02 00:00:00' AND '" + newToDate + "' AND\n" +
//                    "\"public\".\"Journal\".\"partyID\" = " + partyID + " AND\n" +
//                    "\"public\".\"Journal\".\"partyType\" = " + partyType + " AND\n" +
//                    "\"public\".\"Journal\".status = 1 AND\n" +
//                    "\"public\".\"Journal\".\"businessID\" = " + businessID + "\n" +
//                    "GROUP BY\n" +
//                    "\"public\".\"Journal\".\"accountID\",\n" +
//                    "\"public\".\"Journal\".\"businessID\",\n" +
//                    "\"public\".\"Journal\".\"drCrIndicator\"";

            String hql = "SELECT J.businessID, J.accountID,J.drCrIndicator, Sum(J.amount) as amountSum FROM Journal J WHERE J.date BETWEEN '2016-03-02 00:00:00' AND '2018-03-05 23:59:59' AND J.partyID = 1 AND  J.partyType = 2 AND J.status = 1 AND J.businessID = 46 GROUP BY J.accountID,J.businessID,J.drCrIndicator";

            lstDebitCreditBalanceModel = this.executeHqlQuery(hql, DebitCreditBalanceModel.class, TillBoxAppEnum.QueryType.Join.get());
            //lstDebitCreditBalanceModel = this.supplierInvoiceDetailBllManager.e(hql, DebitCreditBalanceModel.class, TillBoxAppEnum.QueryType.Join.get());
            if (lstDebitCreditBalanceModel.size() > 0) {
                for (DebitCreditBalanceModel journalBalanceResult : lstDebitCreditBalanceModel) {
                    if (journalBalanceResult.drCrIndicator == DebitCreditIndicator.Debit.get()) {
                        debitAmountSum = journalBalanceResult.amountSum;
                    } else if (journalBalanceResult.drCrIndicator == DebitCreditIndicator.Credit.get()) {
                        creditAmountSum = journalBalanceResult.amountSum;
                    }
                }
            }
            // balance = debitAmountSum - creditAmountSum;
            balance = creditAmountSum - debitAmountSum;
        } catch (Exception ex) {
            log.error("SupplierInvoiceBllManager -> get party balance got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return balance;
    }


    public List<VMSupplierStatementModel> getSupplierStatement(StatementSearchModel statementSearchModel) throws Exception {
        List<VMSupplierStatementModel> lstVMSupplierStatementModel = new ArrayList<>();
        List<SupplierInvoiceModel> lstSupplierInvoiceModel = new ArrayList<>();
        List<SupplierReturnModel> lstSupplierReturnModel = new ArrayList<>();
        List<SupplierPaymentModel> lstSupplierPaymentModel = new ArrayList<>();

        try {

            String newFromDate = new SimpleDateFormat("yyyy-MM-dd").format(statementSearchModel.getFromDate()) + " 00:00:00";

            String newToDate = new SimpleDateFormat("yyyy-MM-dd").format(statementSearchModel.getToDate()) + " 23:59:59";

            Double opeingBalance = getSupplierOpeningBalance(PartyType.Supplier.get(), statementSearchModel.getSupplierID(), statementSearchModel.getBusinessID(), statementSearchModel.getToDate());
            SupplierInvoiceModel supplierInvoiceModel = new SupplierInvoiceModel();
            supplierInvoiceModel.setBusinessID(statementSearchModel.getBusinessID());
            supplierInvoiceModel.setSupplierID(statementSearchModel.getSupplierID());
            //  lstSupplierInvoiceModel = this.getAllByConditions(supplierInvoiceModel);

            String hql = "SELECT\n" +
                    "\"public\".\"SupplierInvoice\".\"supplierInvoiceID\",\n" +
                    "\"public\".\"SupplierInvoice\".\"date\",\n" +
                    "\"public\".\"SupplierInvoice\".status,\n" +
                    "\"public\".\"SupplierInvoice\".\"businessID\",\n" +
                    "\"public\".\"SupplierInvoice\".\"supplierInvoiceNo\",\n" +
                    "\"public\".\"SupplierInvoice\".\"totalAmount\",\n" +
                    "\"public\".\"SupplierInvoice\".\"totalDiscount\",\n" +
                    "\"public\".\"SupplierInvoice\".\"totalVAT\",\n" +
                    "\"public\".\"SupplierInvoice\".\"SupplierID\"\n" +
                    "FROM\n" +
                    "\"public\".\"SupplierInvoice\"\n" +
                    "WHERE\n" +
                    "\"public\".\"SupplierInvoice\".\"SupplierID\" = " + statementSearchModel.getSupplierID() + " AND\n" +
                    "\"public\".\"SupplierInvoice\".\"businessID\" = " + statementSearchModel.getBusinessID() + " AND\n" +
                    "\"public\".\"SupplierInvoice\".\"date\" BETWEEN '" + newFromDate + "' AND '" + newToDate + "'";
            lstSupplierInvoiceModel = this.executeHqlQuery(hql, SupplierInvoiceModel.class, TillBoxAppEnum.QueryType.Join.get());


            SupplierReturnModel supplierReturnModel = new SupplierReturnModel();
            supplierReturnModel.setBusinessID(statementSearchModel.getBusinessID());
            supplierReturnModel.setSupplierID(statementSearchModel.getSupplierID());
            //  lstSupplierReturnModel = this.supplierReturnBllManager.getAllByConditions(supplierReturnModel);

            hql = "SELECT\n" +
                    "\"public\".\"SupplierReturn\".\"supplierReturnID\",\n" +
                    "\"public\".\"SupplierReturn\".\"businessID\",\n" +
                    "\"public\".\"SupplierReturn\".\"returnDate\",\n" +
                    "\"public\".\"SupplierReturn\".\"supplierID\",\n" +
                    "\"public\".\"SupplierReturn\".\"supplierReturnNumber\",\n" +
                    "\"public\".\"SupplierReturn\".\"totalAmount\",\n" +
                    "\"public\".\"SupplierReturn\".\"totalDiscount\",\n" +
                    "\"public\".\"SupplierReturn\".\"totalVAT\",\n" +
                    "\"public\".\"SupplierReturn\".status\n" +
                    "FROM\n" +
                    "\"public\".\"SupplierReturn\"\n" +
                    "WHERE\n" +
                    "\"public\".\"SupplierReturn\".status = 1 AND\n" +
                    "\"public\".\"SupplierReturn\".\"businessID\" = " + statementSearchModel.getBusinessID() + " AND\n" +
                    "\"public\".\"SupplierReturn\".\"returnDate\" BETWEEN '" + newFromDate + "' AND '" + newToDate + "' AND\n" +
                    "\"public\".\"SupplierReturn\".\"supplierID\" = " + statementSearchModel.getSupplierID() + "\n";
            lstSupplierReturnModel = this.executeHqlQuery(hql, SupplierReturnModel.class, TillBoxAppEnum.QueryType.Join.get());


            SupplierPaymentModel paymentModel = new SupplierPaymentModel();
            paymentModel.setBusinessID(statementSearchModel.getBusinessID());
            paymentModel.setSupplierID(statementSearchModel.getSupplierID());
            // lstSupplierPaymentModel = this.supplierPaymentBllManager.getAllByConditions(paymentModel);

            hql = "SELECT\n" +
                    "\"public\".\"SupplierPayment\".\"supplierPaymentID\",\n" +
                    "\"public\".\"SupplierPayment\".status,\n" +
                    "\"public\".\"SupplierPayment\".\"businessID\",\n" +
                    "\"public\".\"SupplierPayment\".\"date\",\n" +
                    "\"public\".\"SupplierPayment\".\"paidAmount\",\n" +
                    "\"public\".\"SupplierPayment\".\"supplierID\",\n" +
                    "\"public\".\"SupplierPayment\".description\n" +
                    "FROM\n" +
                    "\"public\".\"SupplierPayment\"\n" +
                    "WHERE\n" +
                    "\"public\".\"SupplierPayment\".\"supplierID\" = " + statementSearchModel.getSupplierID() + " AND\n" +
                    "\"public\".\"SupplierPayment\".\"businessID\" = " + statementSearchModel.getBusinessID() + " AND\n" +
                    "\"public\".\"SupplierPayment\".status = 1 AND\n" +
                    "\"public\".\"SupplierPayment\".\"date\" BETWEEN '" + newFromDate + "' AND '" + newToDate + "'\n";
            lstSupplierPaymentModel = this.executeHqlQuery(hql, SupplierPaymentModel.class, TillBoxAppEnum.QueryType.Join.get());


            if (opeingBalance > 0) {
                VMSupplierStatementModel vmSupplierStatementModel = new VMSupplierStatementModel();
                vmSupplierStatementModel.setSupplierInvoiceID(0);
                vmSupplierStatementModel.setCreditAmount(opeingBalance);
                vmSupplierStatementModel.setTransactionDate(statementSearchModel.getToDate());
                vmSupplierStatementModel.setDescription("Opening Balaance");
                lstVMSupplierStatementModel.add(vmSupplierStatementModel);
            }


            for (SupplierInvoiceModel supplierInvoiceModel1 : lstSupplierInvoiceModel) {
                VMSupplierStatementModel vmSupplierStatementModel = new VMSupplierStatementModel();
                vmSupplierStatementModel.setSupplierInvoiceID(supplierInvoiceModel1.getSupplierInvoiceID());
                vmSupplierStatementModel.setCreditAmount(supplierInvoiceModel.getTotalAmount());
                vmSupplierStatementModel.setTransactionDate(supplierInvoiceModel.getDate());
                vmSupplierStatementModel.setReference(supplierInvoiceModel.getSupplierInvoiceNo());
                vmSupplierStatementModel.setDescription("Supplier Invoice");
                lstVMSupplierStatementModel.add(vmSupplierStatementModel);
            }

            for (SupplierReturnModel supplierReturnModel1 : lstSupplierReturnModel) {
                VMSupplierStatementModel vmSupplierStatementModel = new VMSupplierStatementModel();
                vmSupplierStatementModel.setSupplierReturnID(supplierReturnModel1.getSupplierReturnID());
                vmSupplierStatementModel.setDebitAmount(supplierReturnModel1.getTotalAmount());
                vmSupplierStatementModel.setTransactionDate(supplierReturnModel1.getReturnDate());
                vmSupplierStatementModel.setReference(supplierReturnModel1.getSupplierReturnNumber());
                vmSupplierStatementModel.setDescription("Supplier Return");
                lstVMSupplierStatementModel.add(vmSupplierStatementModel);
            }

            for (SupplierPaymentModel supplierPaymentModel : lstSupplierPaymentModel) {
                VMSupplierStatementModel vmSupplierStatementModel = new VMSupplierStatementModel();
                vmSupplierStatementModel.setSupplierPaymentID(supplierPaymentModel.getSupplierPaymentID());
                vmSupplierStatementModel.setDebitAmount(supplierPaymentModel.getPaidAmount());
                vmSupplierStatementModel.setTransactionDate(supplierPaymentModel.getDate());
                vmSupplierStatementModel.setReference(supplierPaymentModel.getSupplierInvoiceNo());
                vmSupplierStatementModel.setDescription("Supplier Payment");
                lstVMSupplierStatementModel.add(vmSupplierStatementModel);
            }

        } catch (Exception ex) {
            log.error("SupplierInvoiceBllManager -> get party balance got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstVMSupplierStatementModel;
    }

    public SupplierInvoiceModel getByInvoiceID(Integer invoiceID) throws Exception {
        SupplierInvoiceModel supplierInvoiceModel = new SupplierInvoiceModel();
        List<SupplierInvoiceModel> lstSupplierInvoiceModel;
        try {
            supplierInvoiceModel.setSupplierInvoiceID(invoiceID);
            supplierInvoiceModel.setStatus(TillBoxAppEnum.Status.Active.get());
            lstSupplierInvoiceModel = this.getAllByConditions(supplierInvoiceModel);
            if (lstSupplierInvoiceModel.size() > 0) {
                supplierInvoiceModel = lstSupplierInvoiceModel.get(0);
            } else {
                supplierInvoiceModel = null;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.SUPPLIER_INVOICE_GET_FAILED;
            }
        } catch (Exception ex) {
            log.error("SupplierInvoiceBllManager -> getByInvoiceID got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return supplierInvoiceModel;
    }


    public SupplierInvoiceModel deleteSupplierInvoice(SupplierInvoiceModel supplierInvoiceModel) throws Exception {
        try {
            if (isValidDeleteObject(supplierInvoiceModel)) {

                SupplierInvoiceDetailModel supplierInvoiceDetailModel = new SupplierInvoiceDetailModel();
                supplierInvoiceDetailModel.setSupplierInvoiceID(supplierInvoiceModel.getSupplierInvoiceID());
                List<SupplierInvoiceDetailModel> supplierInvoiceDetailModels = new ArrayList<>();
                supplierInvoiceDetailModels = this.supplierInvoiceDetailBllManager.getAllByConditions(supplierInvoiceDetailModel);

                supplierInvoiceModel.setStatus(TillBoxAppEnum.Status.Deleted.get());
                this.update(supplierInvoiceModel);
                for (SupplierInvoiceDetailModel supplierInvoiceDetailModel1 : supplierInvoiceDetailModels) {
                    supplierInvoiceDetailModel1.setStatus(TillBoxAppEnum.Status.Deleted.get());
                    this.supplierInvoiceDetailBllManager.update(supplierInvoiceDetailModel1);
                }
            }
        } catch (Exception ex) {
            supplierInvoiceModel = null;
            log.error("SupplierInvoiceBllManager -> get party balance got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return supplierInvoiceModel;
    }


    private boolean isValidDeleteObject(SupplierInvoiceModel supplierInvoiceModel) throws Exception {

        if (supplierInvoiceModel.getSupplierInvoiceID() == null || supplierInvoiceModel.getSupplierInvoiceID() == 0) {
            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            Core.clientMessage.get().userMessage = MessageConstant.SUPPLIER_INVOICE_ID_CANNOT_BE_BLANK;
            return false;
        }

        SupplierPaymentDetailModel searchSupplierPaymentDetailModel = new SupplierPaymentDetailModel();
        searchSupplierPaymentDetailModel.setSupplierInvoiceID(supplierInvoiceModel.getSupplierInvoiceID());

        List<SupplierPaymentDetailModel> lstSupplierPaymentDetailModel = new ArrayList<>();
        lstSupplierPaymentDetailModel = this.supplierPaymentDetailBllManager.getAllByConditions(searchSupplierPaymentDetailModel);
        if (lstSupplierPaymentDetailModel.size() > 0) {
            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            Core.clientMessage.get().userMessage = MessageConstant.SUPPLIER_INVOICE_CANNOT_BE_DELETE_IT_IS_ALREADY_USED;
            return false;
        }


        SupplierReturnDetailModel searchSupplierReturnDetailModel = new SupplierReturnDetailModel();
        searchSupplierReturnDetailModel.setSupplierInvoiceID(supplierInvoiceModel.getSupplierInvoiceID());

        List<SupplierReturnDetailModel> supplierReturnDetailModels = new ArrayList<>();
        supplierReturnDetailModels = this.supplierReturnDetailBllManager.getAllByConditions(searchSupplierReturnDetailModel);

        if (supplierReturnDetailModels.size() > 0) {
            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            Core.clientMessage.get().userMessage = MessageConstant.SUPPLIER_INVOICE_CANNOT_BE_DELETE_IT_IS_ALREADY_USED;
            return false;
        }


        SupplierAdjustmentDetailModel supplierAdjustmentDetailModel = new SupplierAdjustmentDetailModel();
        supplierAdjustmentDetailModel.setSupplierInvoiceID(supplierInvoiceModel.getSupplierInvoiceID());


        List<SupplierAdjustmentDetailModel> supplierAdjustmentDetailModels = new ArrayList<>();
        supplierAdjustmentDetailModels = this.supplierAdjustmentDetailBllManager.getAllByConditions(supplierAdjustmentDetailModel);
        if (supplierAdjustmentDetailModels.size() > 0) {
            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            Core.clientMessage.get().userMessage = MessageConstant.SUPPLIER_INVOICE_CANNOT_BE_DELETE_IT_IS_ALREADY_USED;
            return false;
        }

        return true;
    }


}
