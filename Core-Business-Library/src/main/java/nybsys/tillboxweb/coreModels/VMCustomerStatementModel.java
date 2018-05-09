/**
 * Created By: Md. Abdul Hannan
 * Created Date: 3/20/2018
 * Time: 11:07 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.coreModels;

import java.util.Date;

public class VMCustomerStatementModel {

    public Date transactionDate;
    public Integer customerInvoiceID;
    public Integer customerReturnID;
    public Integer customerReceiveID;
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

    public Integer getCustomerInvoiceID() {
        return customerInvoiceID;
    }

    public void setCustomerInvoiceID(Integer customerInvoiceID) {
        this.customerInvoiceID = customerInvoiceID;
    }

    public Integer getCustomerReturnID() {
        return customerReturnID;
    }

    public void setCustomerReturnID(Integer customerReturnID) {
        this.customerReturnID = customerReturnID;
    }

    public Integer getCustomerReceiveID() {
        return customerReceiveID;
    }

    public void setCustomerReceiveID(Integer customerReceiveID) {
        this.customerReceiveID = customerReceiveID;
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
