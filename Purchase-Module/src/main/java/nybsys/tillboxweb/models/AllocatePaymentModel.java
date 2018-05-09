/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 05-Mar-18
 * Time: 4:13 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModelWithCurrency;

import java.util.Date;
import java.util.Objects;

public class AllocatePaymentModel extends BaseModelWithCurrency {
    private Integer allocatePaymentID;
    private Integer supplierInvoiceID;
    private Integer businessID;
    private Integer referenceType;
    private Integer referenceID;
    private Integer paymentID;
    private String docNumber;
    private String allocatePaymentNo;
    private Date date;
    private Double amount;
    private String allocateBy;

    public Integer getAllocatePaymentID() {
        return allocatePaymentID;
    }

    public void setAllocatePaymentID(Integer allocatePaymentID) {
        this.allocatePaymentID = allocatePaymentID;
    }

    public Integer getSupplierInvoiceID() {
        return supplierInvoiceID;
    }

    public void setSupplierInvoiceID(Integer supplierInvoiceID) {
        this.supplierInvoiceID = supplierInvoiceID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(Integer referenceType) {
        this.referenceType = referenceType;
    }

    public Integer getReferenceID() {
        return referenceID;
    }

    public void setReferenceID(Integer referenceID) {
        this.referenceID = referenceID;
    }

    public Integer getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(Integer paymentID) {
        this.paymentID = paymentID;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getAllocatePaymentNo() {
        return allocatePaymentNo;
    }

    public void setAllocatePaymentNo(String allocatePaymentNo) {
        this.allocatePaymentNo = allocatePaymentNo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getAllocateBy() {
        return allocateBy;
    }

    public void setAllocateBy(String allocateBy) {
        this.allocateBy = allocateBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AllocatePaymentModel)) return false;
        if (!super.equals(o)) return false;
        AllocatePaymentModel that = (AllocatePaymentModel) o;
        return Objects.equals(getAllocatePaymentID(), that.getAllocatePaymentID()) &&
                Objects.equals(getSupplierInvoiceID(), that.getSupplierInvoiceID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getReferenceType(), that.getReferenceType()) &&
                Objects.equals(getReferenceID(), that.getReferenceID()) &&
                Objects.equals(getPaymentID(), that.getPaymentID()) &&
                Objects.equals(getDocNumber(), that.getDocNumber()) &&
                Objects.equals(getAllocatePaymentNo(), that.getAllocatePaymentNo()) &&
                Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getAmount(), that.getAmount()) &&
                Objects.equals(getAllocateBy(), that.getAllocateBy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAllocatePaymentID(), getSupplierInvoiceID(), getBusinessID(), getReferenceType(), getReferenceID(), getPaymentID(), getDocNumber(), getAllocatePaymentNo(), getDate(), getAmount(), getAllocateBy());
    }

    @Override
    public String toString() {
        return "AllocatePaymentModel{" +
                "allocatePaymentID=" + allocatePaymentID +
                ", supplierInvoiceID=" + supplierInvoiceID +
                ", businessID=" + businessID +
                ", referenceType=" + referenceType +
                ", referenceID=" + referenceID +
                ", paymentID=" + paymentID +
                ", docNumber='" + docNumber + '\'' +
                ", allocatePaymentNo='" + allocatePaymentNo + '\'' +
                ", date=" + date +
                ", amount=" + amount +
                ", allocateBy='" + allocateBy + '\'' +
                '}';
    }
}
