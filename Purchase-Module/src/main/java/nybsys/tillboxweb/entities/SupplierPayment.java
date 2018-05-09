/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 27-Feb-18
 * Time: 4:45 PM
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
public class SupplierPayment extends BaseEntityWithCurrency {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "supplierPaymentID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

    private Integer supplierPaymentID;
    private Integer businessID;
    private String supplierInvoiceNo;
    private Integer purchaseOrderID;
    private Date date;
    private Integer supplierID;
    private String docNumber;
    private Integer paymentMethod;
    private Integer accountID;
    private Integer branchID;
    private String trackingNo;
    private Double unAllocatedAmount;
    private Double paidAmount;
    private Double discount;
    private String description;

    public Integer getSupplierPaymentID() {
        return supplierPaymentID;
    }

    public void setSupplierPaymentID(Integer supplierPaymentID) {
        this.supplierPaymentID = supplierPaymentID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public String getSupplierInvoiceNo() {
        return supplierInvoiceNo;
    }

    public void setSupplierInvoiceNo(String supplierInvoiceNo) {
        this.supplierInvoiceNo = supplierInvoiceNo;
    }

    public Integer getPurchaseOrderID() {
        return purchaseOrderID;
    }

    public void setPurchaseOrderID(Integer purchaseOrderID) {
        this.purchaseOrderID = purchaseOrderID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(Integer supplierID) {
        this.supplierID = supplierID;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    public Integer getBranchID() {
        return branchID;
    }

    public void setBranchID(Integer branchID) {
        this.branchID = branchID;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public Double getUnAllocatedAmount() {
        return unAllocatedAmount;
    }

    public void setUnAllocatedAmount(Double unAllocatedAmount) {
        this.unAllocatedAmount = unAllocatedAmount;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SupplierPayment)) return false;
        if (!super.equals(o)) return false;
        SupplierPayment that = (SupplierPayment) o;
        return Objects.equals(getSupplierPaymentID(), that.getSupplierPaymentID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getSupplierInvoiceNo(), that.getSupplierInvoiceNo()) &&
                Objects.equals(getPurchaseOrderID(), that.getPurchaseOrderID()) &&
                Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getSupplierID(), that.getSupplierID()) &&
                Objects.equals(getDocNumber(), that.getDocNumber()) &&
                Objects.equals(getPaymentMethod(), that.getPaymentMethod()) &&
                Objects.equals(getAccountID(), that.getAccountID()) &&
                Objects.equals(getBranchID(), that.getBranchID()) &&
                Objects.equals(getTrackingNo(), that.getTrackingNo()) &&
                Objects.equals(getUnAllocatedAmount(), that.getUnAllocatedAmount()) &&
                Objects.equals(getPaidAmount(), that.getPaidAmount()) &&
                Objects.equals(getDiscount(), that.getDiscount()) &&
                Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSupplierPaymentID(), getBusinessID(), getSupplierInvoiceNo(), getPurchaseOrderID(), getDate(), getSupplierID(), getDocNumber(), getPaymentMethod(), getAccountID(), getBranchID(), getTrackingNo(), getUnAllocatedAmount(), getPaidAmount(), getDiscount(), getDescription());
    }

    @Override
    public String toString() {
        return "SupplierPayment{" +
                "supplierPaymentID=" + supplierPaymentID +
                ", businessID=" + businessID +
                ", supplierInvoiceNo='" + supplierInvoiceNo + '\'' +
                ", purchaseOrderID=" + purchaseOrderID +
                ", date=" + date +
                ", supplierID=" + supplierID +
                ", docNumber='" + docNumber + '\'' +
                ", paymentMethod=" + paymentMethod +
                ", accountID=" + accountID +
                ", branchID=" + branchID +
                ", trackingNo='" + trackingNo + '\'' +
                ", unAllocatedAmount=" + unAllocatedAmount +
                ", paidAmount=" + paidAmount +
                ", discount=" + discount +
                ", description='" + description + '\'' +
                '}';
    }
}