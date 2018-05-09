/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 02:03
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModelWithCurrency;

import java.util.Date;
import java.util.Objects;

public class CustomerInvoiceModel extends BaseModelWithCurrency {
    private Integer customerInvoiceID;
    private Integer businessID;
    private Integer salesQuotationID;
    private Integer customerID;
    private String docNumber;
    private Integer customerInvoiceNo;
    private Date invoiceDate;
    private Date dueDate;
    private Integer allowOnlinePayment;
    private Double totalAmount;
    private Double totalVAT;
    private Double totalDiscount;
    private String message;
    private Integer paymentStatus;

    public Integer getCustomerInvoiceID() {
        return customerInvoiceID;
    }

    public void setCustomerInvoiceID(Integer customerInvoiceID) {
        this.customerInvoiceID = customerInvoiceID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getSalesQuotationID() {
        return salesQuotationID;
    }

    public void setSalesQuotationID(Integer salesQuotationID) {
        this.salesQuotationID = salesQuotationID;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public Integer getCustomerInvoiceNo() {
        return customerInvoiceNo;
    }

    public void setCustomerInvoiceNo(Integer customerInvoiceNo) {
        this.customerInvoiceNo = customerInvoiceNo;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getAllowOnlinePayment() {
        return allowOnlinePayment;
    }

    public void setAllowOnlinePayment(Integer allowOnlinePayment) {
        this.allowOnlinePayment = allowOnlinePayment;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getTotalVAT() {
        return totalVAT;
    }

    public void setTotalVAT(Double totalVAT) {
        this.totalVAT = totalVAT;
    }

    public Double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(Double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerInvoiceModel)) return false;
        if (!super.equals(o)) return false;
        CustomerInvoiceModel that = (CustomerInvoiceModel) o;
        return Objects.equals(getCustomerInvoiceID(), that.getCustomerInvoiceID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getSalesQuotationID(), that.getSalesQuotationID()) &&
                Objects.equals(getCustomerID(), that.getCustomerID()) &&
                Objects.equals(getDocNumber(), that.getDocNumber()) &&
                Objects.equals(getCustomerInvoiceNo(), that.getCustomerInvoiceNo()) &&
                Objects.equals(getInvoiceDate(), that.getInvoiceDate()) &&
                Objects.equals(getDueDate(), that.getDueDate()) &&
                Objects.equals(getAllowOnlinePayment(), that.getAllowOnlinePayment()) &&
                Objects.equals(getTotalAmount(), that.getTotalAmount()) &&
                Objects.equals(getTotalVAT(), that.getTotalVAT()) &&
                Objects.equals(getTotalDiscount(), that.getTotalDiscount()) &&
                Objects.equals(getMessage(), that.getMessage()) &&
                Objects.equals(getPaymentStatus(), that.getPaymentStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCustomerInvoiceID(), getBusinessID(), getSalesQuotationID(), getCustomerID(), getDocNumber(), getCustomerInvoiceNo(), getInvoiceDate(), getDueDate(), getAllowOnlinePayment(), getTotalAmount(), getTotalVAT(), getTotalDiscount(), getMessage(), getPaymentStatus());
    }

    @Override
    public String toString() {
        return "CustomerInvoiceModel{" +
                "customerInvoiceID=" + customerInvoiceID +
                ", businessID=" + businessID +
                ", salesQuotationID=" + salesQuotationID +
                ", customerID=" + customerID +
                ", docNumber='" + docNumber + '\'' +
                ", customerInvoiceNo=" + customerInvoiceNo +
                ", invoiceDate=" + invoiceDate +
                ", dueDate=" + dueDate +
                ", allowOnlinePayment=" + allowOnlinePayment +
                ", totalAmount=" + totalAmount +
                ", totalVAT=" + totalVAT +
                ", totalDiscount=" + totalDiscount +
                ", message='" + message + '\'' +
                ", paymentStatus=" + paymentStatus +
                '}';
    }
}
