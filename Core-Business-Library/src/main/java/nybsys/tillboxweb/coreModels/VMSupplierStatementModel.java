/**
 * Created By: Md. Abdul Hannan
 * Created Date: 3/2/2018
 * Time: 12:56 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.coreModels;

import java.util.Date;

public class VMSupplierStatementModel {

    public Date transactionDate;
    public Integer  supplierInvoiceID;
    public Integer  supplierReturnID;
    public Integer  supplierPaymentID;
    public Double   debitAmount;
    public Double   creditAmount;
    public String   reference;
    public String   description;


    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Integer getSupplierInvoiceID() {
        return supplierInvoiceID;
    }

    public void setSupplierInvoiceID(Integer supplierInvoiceID) {
        this.supplierInvoiceID = supplierInvoiceID;
    }

    public Integer getSupplierReturnID() {
        return supplierReturnID;
    }

    public void setSupplierReturnID(Integer supplierReturnID) {
        this.supplierReturnID = supplierReturnID;
    }

    public Integer getSupplierPaymentID() {
        return supplierPaymentID;
    }

    public void setSupplierPaymentID(Integer supplierPaymentID) {
        this.supplierPaymentID = supplierPaymentID;
    }

    public Double getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(Double debitAmount) {
        this.debitAmount = debitAmount;
    }

    public Double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
