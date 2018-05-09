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
import nybsys.tillboxweb.coreModels.VMCustomerStatementModel;
import nybsys.tillboxweb.entities.CustomerInvoice;
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
public class CustomerInvoiceBllManager extends BaseBll<CustomerInvoice> {

    private static final Logger log = LoggerFactory.getLogger(CustomerInvoiceBllManager.class);


    private CustomerInvoiceDetailBllManager customerInvoiceDetailBllManager = new CustomerInvoiceDetailBllManager();


    private CustomerReturnBllManager customerReturnBllManager = new CustomerReturnBllManager();

    private CustomerReceiveBllManager customerReceiveBllManager = new CustomerReceiveBllManager();

    private CustomerReceiveDetailBllManager customerReceiveDetailBllManager = new CustomerReceiveDetailBllManager();

    private CustomerReturnDetailBllManager customerReturnDetailBllManager = new CustomerReturnDetailBllManager();

    private CustomerAdjustmentDetailBllManager customerAdjustmentDetailBllManager = new CustomerAdjustmentDetailBllManager();

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(CustomerInvoice.class);
        Core.runTimeModelType.set(CustomerInvoiceModel.class);
    }


    public VMCustomerInvoice saveCustomerInvoice(VMCustomerInvoice vmCustomerInvoice) throws Exception {

        try {
            if (isValidCustomerInvoice(vmCustomerInvoice)) {
                if (vmCustomerInvoice.customerInvoiceModel.getCustomerInvoiceID() != null && vmCustomerInvoice.customerInvoiceModel.getCustomerInvoiceID() > 0) {

                    vmCustomerInvoice.customerInvoiceModel = this.update(vmCustomerInvoice.customerInvoiceModel);

              /* detail save*/

                    CustomerInvoiceDetailModel searchInvoiceDetail = new CustomerInvoiceDetailModel();
                    searchInvoiceDetail.setCustomerInvoiceID(vmCustomerInvoice.customerInvoiceModel.getCustomerInvoiceID());
                    List<CustomerInvoiceDetailModel> lstExistingCustomerInvoiceDetailModel;
                    lstExistingCustomerInvoiceDetailModel = this.customerInvoiceDetailBllManager.getAllByConditions(searchInvoiceDetail);

                    for (CustomerInvoiceDetailModel customerInvoiceDetailModel : lstExistingCustomerInvoiceDetailModel) {
                        customerInvoiceDetailModel.setStatus(TillBoxAppEnum.Status.Deleted.get());
                        customerInvoiceDetailModel = this.customerInvoiceDetailBllManager.update(customerInvoiceDetailModel);
                    }
                    this.customerInvoiceDetailBllManager.saveCustomerInvoiceDetail(vmCustomerInvoice);
                } else {

                    vmCustomerInvoice.customerInvoiceModel = this.save(vmCustomerInvoice.customerInvoiceModel);

               /* detail save*/
                    this.customerInvoiceDetailBllManager.saveCustomerInvoiceDetail(vmCustomerInvoice);
                }

            }


        } catch (Exception ex) {
            log.error("CustomerInvoiceBllManager -> CustomerInvoiceBllManager got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return vmCustomerInvoice;
    }


    public List<VMCustomerInvoice> searchVMCustomerInvoice(CustomerInvoiceModel customerInvoiceModel) throws Exception {

        List<VMCustomerInvoice> lstVMCustomerInvoice = new ArrayList<>();
        try {
            List<CustomerInvoiceModel> lstCustomerInvoiceModel = new ArrayList<>();
            if (customerInvoiceModel.getStatus() == null || customerInvoiceModel.getStatus() == 0) {
                customerInvoiceModel.setStatus(TillBoxAppEnum.Status.Active.get());
            }

            lstCustomerInvoiceModel = this.getAllByConditions(customerInvoiceModel);

            if (lstCustomerInvoiceModel.size() > 0) {
                for (CustomerInvoiceModel customerInvoiceModel1 : lstCustomerInvoiceModel) {
                    VMCustomerInvoice vmCustomerInvoice = new VMCustomerInvoice();
                    vmCustomerInvoice.customerInvoiceModel = customerInvoiceModel1;
                    CustomerInvoiceDetailModel searchCustomerInvoiceModel = new CustomerInvoiceDetailModel();
                    searchCustomerInvoiceModel.setCustomerInvoiceID(customerInvoiceModel1.getCustomerInvoiceID());
                    searchCustomerInvoiceModel.setStatus(TillBoxAppEnum.Status.Active.get());
                    vmCustomerInvoice.lstCustomerInvoiceDetailModel = this.customerInvoiceDetailBllManager.getAllByConditions(searchCustomerInvoiceModel);
                    lstVMCustomerInvoice.add(vmCustomerInvoice);
                }
            }

        } catch (Exception ex) {
            log.error("CustomerBllManager -> searchCustomer got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstVMCustomerInvoice;
    }


    private Boolean isValidCustomerInvoice(VMCustomerInvoice vmCustomerInvoice) throws Exception {
        CustomerInvoiceModel existingCustomerInvoiceModel = new CustomerInvoiceModel();
        existingCustomerInvoiceModel.setCustomerInvoiceNo(vmCustomerInvoice.customerInvoiceModel.getCustomerInvoiceNo());
        List<CustomerInvoiceModel> lstCustomerInvoiceModel = new ArrayList<>();


        lstCustomerInvoiceModel = this.getAllByConditions(existingCustomerInvoiceModel);


        if (lstCustomerInvoiceModel.size() > 0) {
            existingCustomerInvoiceModel = lstCustomerInvoiceModel.get(0);
            if ((existingCustomerInvoiceModel.getCustomerInvoiceID() != null && existingCustomerInvoiceModel.getCustomerInvoiceID() > 0) && existingCustomerInvoiceModel.getCustomerInvoiceID().intValue() != vmCustomerInvoice.customerInvoiceModel.getCustomerInvoiceID().intValue()) {

                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().userMessage = MessageConstant.DUPLICATE_CUSTOMER_INVOICE_NUMBER;
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
            log.error("CustomerInvoiceBllManager -> get party balance got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return balance;
    }


    public Double getCustomerOpeningBalance(Integer partyType, Integer partyID, Integer businessID, Date toDate) throws Exception {
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
            //lstDebitCreditBalanceModel = this.customerInvoiceDetailBllManager.e(hql, DebitCreditBalanceModel.class, TillBoxAppEnum.QueryType.Join.get());
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
            log.error("CustomerInvoiceBllManager -> get party balance got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return balance;
    }


    public List<VMCustomerStatementModel> getCustomerStatement(StatementSearchModel statementSearchModel) throws Exception {
        List<VMCustomerStatementModel> lstVMCustomerStatementModel = new ArrayList<>();
        List<CustomerInvoiceModel> lstCustomerInvoiceModel = new ArrayList<>();
        List<CustomerReturnModel> lstCustomerReturnModel = new ArrayList<>();
        List<CustomerReceiveModel> lstCustomerReceiveModel = new ArrayList<>();

        try {

            String newFromDate = new SimpleDateFormat("yyyy-MM-dd").format(statementSearchModel.getFromDate()) + " 00:00:00";

            String newToDate = new SimpleDateFormat("yyyy-MM-dd").format(statementSearchModel.getToDate()) + " 23:59:59";

            Double opeingBalance = getCustomerOpeningBalance(PartyType.Customer.get(), statementSearchModel.getCustomerID(), statementSearchModel.getBusinessID(), statementSearchModel.getToDate());
            CustomerInvoiceModel customerInvoiceModel = new CustomerInvoiceModel();
            customerInvoiceModel.setBusinessID(statementSearchModel.getBusinessID());
            customerInvoiceModel.setCustomerID(statementSearchModel.getCustomerID());
            //  lstCustomerInvoiceModel = this.getAllByConditions(customerInvoiceModel);

            String hql = "SELECT\n" +
                    "\"public\".\"CustomerInvoice\".\"customerInvoiceID\",\n" +
                    "\"public\".\"CustomerInvoice\".\"date\",\n" +
                    "\"public\".\"CustomerInvoice\".status,\n" +
                    "\"public\".\"CustomerInvoice\".\"businessID\",\n" +
                    "\"public\".\"CustomerInvoice\".\"customerInvoiceNo\",\n" +
                    "\"public\".\"CustomerInvoice\".\"totalAmount\",\n" +
                    "\"public\".\"CustomerInvoice\".\"totalDiscount\",\n" +
                    "\"public\".\"CustomerInvoice\".\"totalVAT\",\n" +
                    "\"public\".\"CustomerInvoice\".\"CustomerID\"\n" +
                    "FROM\n" +
                    "\"public\".\"CustomerInvoice\"\n" +
                    "WHERE\n" +
                    "\"public\".\"CustomerInvoice\".\"CustomerID\" = " + statementSearchModel.getCustomerID() + " AND\n" +
                    "\"public\".\"CustomerInvoice\".\"businessID\" = " + statementSearchModel.getBusinessID() + " AND\n" +
                    "\"public\".\"CustomerInvoice\".\"date\" BETWEEN '" + newFromDate + "' AND '" + newToDate + "'";
            lstCustomerInvoiceModel = this.executeHqlQuery(hql, CustomerInvoiceModel.class, TillBoxAppEnum.QueryType.Join.get());


            CustomerReturnModel customerReturnModel = new CustomerReturnModel();
            customerReturnModel.setBusinessID(statementSearchModel.getBusinessID());
            customerReturnModel.setCustomerID(statementSearchModel.getCustomerID());
            //  lstCustomerReturnModel = this.customerReturnBllManager.getAllByConditions(customerReturnModel);

            hql = "SELECT\n" +
                    "\"public\".\"CustomerReturn\".\"customerReturnID\",\n" +
                    "\"public\".\"CustomerReturn\".\"businessID\",\n" +
                    "\"public\".\"CustomerReturn\".\"returnDate\",\n" +
                    "\"public\".\"CustomerReturn\".\"customerID\",\n" +
                    "\"public\".\"CustomerReturn\".\"customerReturnNumber\",\n" +
                    "\"public\".\"CustomerReturn\".\"totalAmount\",\n" +
                    "\"public\".\"CustomerReturn\".\"totalDiscount\",\n" +
                    "\"public\".\"CustomerReturn\".\"totalVAT\",\n" +
                    "\"public\".\"CustomerReturn\".status\n" +
                    "FROM\n" +
                    "\"public\".\"CustomerReturn\"\n" +
                    "WHERE\n" +
                    "\"public\".\"CustomerReturn\".status = 1 AND\n" +
                    "\"public\".\"CustomerReturn\".\"businessID\" = " + statementSearchModel.getBusinessID() + " AND\n" +
                    "\"public\".\"CustomerReturn\".\"returnDate\" BETWEEN '" + newFromDate + "' AND '" + newToDate + "' AND\n" +
                    "\"public\".\"CustomerReturn\".\"customerID\" = " + statementSearchModel.getCustomerID() + "\n";
            lstCustomerReturnModel = this.executeHqlQuery(hql, CustomerReturnModel.class, TillBoxAppEnum.QueryType.Join.get());


            CustomerReceiveModel paymentModel = new CustomerReceiveModel();
            paymentModel.setBusinessID(statementSearchModel.getBusinessID());
            paymentModel.setCustomerID(statementSearchModel.getCustomerID());
            // lstCustomerReceiveModel = this.customerReceiveBllManager.getAllByConditions(paymentModel);

            hql = "SELECT\n" +
                    "\"public\".\"CustomerReceive\".\"customerReceiveID\",\n" +
                    "\"public\".\"CustomerReceive\".status,\n" +
                    "\"public\".\"CustomerReceive\".\"businessID\",\n" +
                    "\"public\".\"CustomerReceive\".\"date\",\n" +
                    "\"public\".\"CustomerReceive\".\"paidAmount\",\n" +
                    "\"public\".\"CustomerReceive\".\"customerID\",\n" +
                    "\"public\".\"CustomerReceive\".description\n" +
                    "FROM\n" +
                    "\"public\".\"CustomerReceive\"\n" +
                    "WHERE\n" +
                    "\"public\".\"CustomerReceive\".\"customerID\" = " + statementSearchModel.getCustomerID() + " AND\n" +
                    "\"public\".\"CustomerReceive\".\"businessID\" = " + statementSearchModel.getBusinessID() + " AND\n" +
                    "\"public\".\"CustomerReceive\".status = 1 AND\n" +
                    "\"public\".\"CustomerReceive\".\"date\" BETWEEN '" + newFromDate + "' AND '" + newToDate + "'\n";
            lstCustomerReceiveModel = this.executeHqlQuery(hql, CustomerReceiveModel.class, TillBoxAppEnum.QueryType.Join.get());


            if (opeingBalance > 0) {
                VMCustomerStatementModel vmCustomerStatementModel = new VMCustomerStatementModel();
                vmCustomerStatementModel.setCustomerInvoiceID(0);
                vmCustomerStatementModel.setCreditAmount(opeingBalance);
                vmCustomerStatementModel.setTransactionDate(statementSearchModel.getToDate());
                vmCustomerStatementModel.setDescription("Opening Balaance");
                lstVMCustomerStatementModel.add(vmCustomerStatementModel);
            }


            for (CustomerInvoiceModel customerInvoiceModel1 : lstCustomerInvoiceModel) {
                VMCustomerStatementModel vmCustomerStatementModel = new VMCustomerStatementModel();
                vmCustomerStatementModel.setCustomerInvoiceID(customerInvoiceModel1.getCustomerInvoiceID());
                vmCustomerStatementModel.setCreditAmount(customerInvoiceModel.getTotalAmount());
                vmCustomerStatementModel.setTransactionDate(customerInvoiceModel.getInvoiceDate());
                vmCustomerStatementModel.setReference(customerInvoiceModel.getCustomerInvoiceNo().toString());
                vmCustomerStatementModel.setDescription("Customer Invoice");
                lstVMCustomerStatementModel.add(vmCustomerStatementModel);
            }

            for (CustomerReturnModel customerReturnModel1 : lstCustomerReturnModel) {
                VMCustomerStatementModel vmCustomerStatementModel = new VMCustomerStatementModel();
                vmCustomerStatementModel.setCustomerReturnID(customerReturnModel1.getCustomerReturnID());
                vmCustomerStatementModel.setDebitAmount(customerReturnModel1.getTotalAmount());
                vmCustomerStatementModel.setTransactionDate(customerReturnModel1.getReturnDate());
                vmCustomerStatementModel.setReference(customerReturnModel1.getCustomerReturnNo());
                vmCustomerStatementModel.setDescription("Customer Return");
                lstVMCustomerStatementModel.add(vmCustomerStatementModel);
            }

            for (CustomerReceiveModel customerReceiveModel : lstCustomerReceiveModel) {
                VMCustomerStatementModel vmCustomerStatementModel = new VMCustomerStatementModel();
                vmCustomerStatementModel.setCustomerReceiveID(customerReceiveModel.getCustomerReceiveID());
                vmCustomerStatementModel.setDebitAmount(customerReceiveModel.getTotalAmount());
                vmCustomerStatementModel.setTransactionDate(customerReceiveModel.getDateTime());
                vmCustomerStatementModel.setReference(customerReceiveModel.getCustomerReceiveNo());
                vmCustomerStatementModel.setDescription("Customer Receive");
                lstVMCustomerStatementModel.add(vmCustomerStatementModel);
            }

        } catch (Exception ex) {
            log.error("CustomerInvoiceBllManager -> get party balance got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstVMCustomerStatementModel;
    }

    public CustomerInvoiceModel getByInvoiceID(Integer invoiceID) throws Exception {
        CustomerInvoiceModel customerInvoiceModel = new CustomerInvoiceModel();
        List<CustomerInvoiceModel> lstCustomerInvoiceModel;
        try {
            customerInvoiceModel.setCustomerInvoiceID(invoiceID);
            customerInvoiceModel.setStatus(TillBoxAppEnum.Status.Active.get());
            lstCustomerInvoiceModel = this.getAllByConditions(customerInvoiceModel);
            if (lstCustomerInvoiceModel.size() > 0) {
                customerInvoiceModel = lstCustomerInvoiceModel.get(0);
            } else {
                customerInvoiceModel = null;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.CUSTOMER_INVOICE_GET_FAILED;
            }
        } catch (Exception ex) {
            log.error("CustomerInvoiceBllManager -> getByInvoiceID got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return customerInvoiceModel;
    }


    public CustomerInvoiceModel deleteCustomerInvoice(CustomerInvoiceModel customerInvoiceModel) throws Exception {
        try {
            if (isValidDeleteObject(customerInvoiceModel)) {

                CustomerInvoiceDetailModel customerInvoiceDetailModel = new CustomerInvoiceDetailModel();
                customerInvoiceDetailModel.setCustomerInvoiceID(customerInvoiceModel.getCustomerInvoiceID());
                List<CustomerInvoiceDetailModel> customerInvoiceDetailModels = new ArrayList<>();
                customerInvoiceDetailModels = this.customerInvoiceDetailBllManager.getAllByConditions(customerInvoiceDetailModel);

                customerInvoiceModel.setStatus(TillBoxAppEnum.Status.Deleted.get());
                this.update(customerInvoiceModel);
                for (CustomerInvoiceDetailModel customerInvoiceDetailModel1 : customerInvoiceDetailModels) {
                    customerInvoiceDetailModel1.setStatus(TillBoxAppEnum.Status.Deleted.get());
                    this.customerInvoiceDetailBllManager.update(customerInvoiceDetailModel1);
                }
            }
        } catch (Exception ex) {
            customerInvoiceModel = null;
            log.error("CustomerInvoiceBllManager -> get party balance got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return customerInvoiceModel;
    }


    private boolean isValidDeleteObject(CustomerInvoiceModel customerInvoiceModel) throws Exception {

        if (customerInvoiceModel.getCustomerInvoiceID() == null || customerInvoiceModel.getCustomerInvoiceID() == 0) {
            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            Core.clientMessage.get().userMessage = MessageConstant.CUSTOMER_INVOICE_ID_CANNOT_BE_BLANK;
            return false;
        }

        CustomerReceiveDetailModel searchCustomerReceiveDetailModel = new CustomerReceiveDetailModel();
        searchCustomerReceiveDetailModel.setCustomerInvoiceID(customerInvoiceModel.getCustomerInvoiceID());

        List<CustomerReceiveDetailModel> lstCustomerReceiveDetailModel = new ArrayList<>();
        lstCustomerReceiveDetailModel = this.customerReceiveDetailBllManager.getAllByConditions(searchCustomerReceiveDetailModel);
        if (lstCustomerReceiveDetailModel.size() > 0) {
            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            Core.clientMessage.get().userMessage = MessageConstant.CUSTOMER_INVOICE_CANNOT_BE_DELETE_IT_IS_ALREADY_USED;
            return false;
        }


        CustomerReturnDetailModel searchCustomerReturnDetailModel = new CustomerReturnDetailModel();
        searchCustomerReturnDetailModel.setCustomerReturnID(customerInvoiceModel.getCustomerInvoiceID());

        List<CustomerReturnDetailModel> customerReturnDetailModels = new ArrayList<>();
        customerReturnDetailModels = this.customerReturnDetailBllManager.getAllByConditions(searchCustomerReturnDetailModel);

        if (customerReturnDetailModels.size() > 0) {
            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            Core.clientMessage.get().userMessage = MessageConstant.CUSTOMER_INVOICE_CANNOT_BE_DELETE_IT_IS_ALREADY_USED;
            return false;
        }


        CustomerAdjustmentDetailModel customerAdjustmentDetailModel = new CustomerAdjustmentDetailModel();
        customerAdjustmentDetailModel.setCustomerInvoiceID(customerInvoiceModel.getCustomerInvoiceID());


        List<CustomerAdjustmentDetailModel> customerAdjustmentDetailModels = new ArrayList<>();
        customerAdjustmentDetailModels = this.customerAdjustmentDetailBllManager.getAllByConditions(customerAdjustmentDetailModel);
        if (customerAdjustmentDetailModels.size() > 0) {
            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            Core.clientMessage.get().userMessage = MessageConstant.CUSTOMER_INVOICE_CANNOT_BE_DELETE_IT_IS_ALREADY_USED;
            return false;
        }

        return true;
    }


}
