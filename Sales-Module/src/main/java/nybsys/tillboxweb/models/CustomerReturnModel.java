/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 02:29
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModelWithCurrency;

import java.util.Date;
import java.util.Objects;

public class CustomerReturnModel extends BaseModelWithCurrency {
    private Integer customerReturnID;
    private Integer businessID;
    private String docNumber;
    private String customerReturnNo;
    private Integer customerInvoiceID;
    private Date returnDate;
    private Integer customerID;
    private Double totalAmount;
    private Double totalDiscount;
    private Double totalVAT;
    private String message;
    private String comment;
    private Integer returnStatus;
    private Double paidAmount;

    public Integer getCustomerReturnID() {
        return customerReturnID;
    }

    public void setCustomerReturnID(Integer customerReturnID) {
        this.customerReturnID = customerReturnID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getCustomerReturnNo() {
        return customerReturnNo;
    }

    public void setCustomerReturnNo(String customerReturnNo) {
        this.customerReturnNo = customerReturnNo;
    }

    public Integer getCustomerInvoiceID() {
        return customerInvoiceID;
    }

    public void setCustomerInvoiceID(Integer customerInvoiceID) {
        this.customerInvoiceID = customerInvoiceID;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(Double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public Double getTotalVAT() {
        return totalVAT;
    }

    public void setTotalVAT(Double totalVAT) {
        this.totalVAT = totalVAT;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(Integer returnStatus) {
        this.returnStatus = returnStatus;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    @Override
    public String toString() {
        return "CustomerReturnModel{" +
                "customerReturnID=" + customerReturnID +
                ", businessID=" + businessID +
                ", docNumber='" + docNumber + '\'' +
                ", customerReturnNo='" + customerReturnNo + '\'' +
                ", customerInvoiceID=" + customerInvoiceID +
                ", returnDate=" + returnDate +
                ", customerID=" + customerID +
                ", totalAmount=" + totalAmount +
                ", totalDiscount=" + totalDiscount +
                ", totalVAT=" + totalVAT +
                ", message='" + message + '\'' +
                ", comment='" + comment + '\'' +
                ", returnStatus=" + returnStatus +
                ", paidAmount=" + paidAmount +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerReturnModel)) return false;
        if (!super.equals(o)) return false;
        CustomerReturnModel that = (CustomerReturnModel) o;
        return Objects.equals(getCustomerReturnID(), that.getCustomerReturnID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getDocNumber(), that.getDocNumber()) &&
                Objects.equals(getCustomerReturnNo(), that.getCustomerReturnNo()) &&
                Objects.equals(getCustomerInvoiceID(), that.getCustomerInvoiceID()) &&
                Objects.equals(getReturnDate(), that.getReturnDate()) &&
                Objects.equals(getCustomerID(), that.getCustomerID()) &&
                Objects.equals(getTotalAmount(), that.getTotalAmount()) &&
                Objects.equals(getTotalDiscount(), that.getTotalDiscount()) &&
                Objects.equals(getTotalVAT(), that.getTotalVAT()) &&
                Objects.equals(getMessage(), that.getMessage()) &&
                Objects.equals(getComment(), that.getComment()) &&
                Objects.equals(getReturnStatus(), that.getReturnStatus()) &&
                Objects.equals(getPaidAmount(), that.getPaidAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCustomerReturnID(), getBusinessID(), getDocNumber(), getCustomerReturnNo(), getCustomerInvoiceID(), getReturnDate(), getCustomerID(), getTotalAmount(), getTotalDiscount(), getTotalVAT(), getMessage(), getComment(), getReturnStatus(), getPaidAmount());
    }
}
