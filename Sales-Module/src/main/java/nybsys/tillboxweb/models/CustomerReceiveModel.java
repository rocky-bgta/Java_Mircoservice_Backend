/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 12-Mar-18
 * Time: 5:14 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModelWithCurrency;

import java.util.Date;
import java.util.Objects;

public class CustomerReceiveModel extends BaseModelWithCurrency {

    private Integer customerReceiveID;
    private Integer businessID;
    private Date dateTime;
    private Integer customerID;
    private String docNumber;
    private String customerReceiveNo;
    private Double totalAmount;
    private Double totalDiscount;
    private Double unAllocatedAmount;
    private String note;
    private String description;
    private String reference;
    private Integer paymentMethod;
    private Boolean reconciled;
    private String comment;
    private Integer accountID;
    private String trackingNumber;

    public Integer getCustomerReceiveID() {
        return customerReceiveID;
    }

    public void setCustomerReceiveID(Integer customerReceiveID) {
        this.customerReceiveID = customerReceiveID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
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

    public String getCustomerReceiveNo() {
        return customerReceiveNo;
    }

    public void setCustomerReceiveNo(String customerReceiveNo) {
        this.customerReceiveNo = customerReceiveNo;
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

    public Double getUnAllocatedAmount() {
        return unAllocatedAmount;
    }

    public void setUnAllocatedAmount(Double unAllocatedAmount) {
        this.unAllocatedAmount = unAllocatedAmount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Boolean getReconciled() {
        return reconciled;
    }

    public void setReconciled(Boolean reconciled) {
        this.reconciled = reconciled;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerReceiveModel)) return false;
        if (!super.equals(o)) return false;
        CustomerReceiveModel that = (CustomerReceiveModel) o;
        return Objects.equals(getCustomerReceiveID(), that.getCustomerReceiveID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getDateTime(), that.getDateTime()) &&
                Objects.equals(getCustomerID(), that.getCustomerID()) &&
                Objects.equals(getDocNumber(), that.getDocNumber()) &&
                Objects.equals(getCustomerReceiveNo(), that.getCustomerReceiveNo()) &&
                Objects.equals(getTotalAmount(), that.getTotalAmount()) &&
                Objects.equals(getTotalDiscount(), that.getTotalDiscount()) &&
                Objects.equals(getUnAllocatedAmount(), that.getUnAllocatedAmount()) &&
                Objects.equals(getNote(), that.getNote()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getReference(), that.getReference()) &&
                Objects.equals(getPaymentMethod(), that.getPaymentMethod()) &&
                Objects.equals(getReconciled(), that.getReconciled()) &&
                Objects.equals(getComment(), that.getComment()) &&
                Objects.equals(getAccountID(), that.getAccountID()) &&
                Objects.equals(getTrackingNumber(), that.getTrackingNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCustomerReceiveID(), getBusinessID(), getDateTime(), getCustomerID(), getDocNumber(), getCustomerReceiveNo(), getTotalAmount(), getTotalDiscount(), getUnAllocatedAmount(), getNote(), getDescription(), getReference(), getPaymentMethod(), getReconciled(), getComment(), getAccountID(), getTrackingNumber());
    }

    @Override
    public String toString() {
        return "CustomerReceiveModel{" +
                "customerReceiveID=" + customerReceiveID +
                ", businessID=" + businessID +
                ", dateTime=" + dateTime +
                ", customerID=" + customerID +
                ", docNumber='" + docNumber + '\'' +
                ", customerReceiveNo='" + customerReceiveNo + '\'' +
                ", totalAmount=" + totalAmount +
                ", totalDiscount=" + totalDiscount +
                ", unAllocatedAmount=" + unAllocatedAmount +
                ", note='" + note + '\'' +
                ", description='" + description + '\'' +
                ", reference='" + reference + '\'' +
                ", paymentMethod=" + paymentMethod +
                ", reconciled=" + reconciled +
                ", comment='" + comment + '\'' +
                ", accountID=" + accountID +
                ", trackingNumber='" + trackingNumber + '\'' +
                '}';
    }
}