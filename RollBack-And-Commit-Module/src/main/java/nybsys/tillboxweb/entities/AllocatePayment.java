/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 05-Mar-18
 * Time: 3:57 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntityWithCurrency;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
public class AllocatePayment extends BaseEntityWithCurrency {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "allocatePaymentID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

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
        if (!(o instanceof AllocatePayment)) return false;
        if (!super.equals(o)) return false;
        AllocatePayment that = (AllocatePayment) o;
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
        return "AllocatePayment{" +
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