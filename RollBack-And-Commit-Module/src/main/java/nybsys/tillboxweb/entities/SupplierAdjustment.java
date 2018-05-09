/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 22/02/2018
 * Time: 11:43
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntityWithCurrency;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
public class SupplierAdjustment extends BaseEntityWithCurrency {
    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "supplierAdjustmentID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    private Integer supplierAdjustmentID;
    @Column
    private Integer businessID;
    @Column
    private Date date;
    @Column
    private Integer supplierID;
    @Column
    private String docNumber;
    @Column
    private String adjustmentNumber;
    @Column
    private Integer effectType;
    @Column
    private Integer vatType;
    @Column
    private Double totalVAT;
    @Column
    private Double totalAmount;
    @Column
    private Double unAllocatedAmount;
    @Column
    private Integer accountID;
    @Column
    private String note;
    @Column
    private String description;

    public Integer getSupplierAdjustmentID() {
        return supplierAdjustmentID;
    }

    public void setSupplierAdjustmentID(Integer supplierAdjustmentID) {
        this.supplierAdjustmentID = supplierAdjustmentID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
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

    public String getAdjustmentNumber() {
        return adjustmentNumber;
    }

    public void setAdjustmentNumber(String adjustmentNumber) {
        this.adjustmentNumber = adjustmentNumber;
    }

    public Integer getEffectType() {
        return effectType;
    }

    public void setEffectType(Integer effectType) {
        this.effectType = effectType;
    }

    public Integer getVatType() {
        return vatType;
    }

    public void setVatType(Integer vatType) {
        this.vatType = vatType;
    }

    public Double getTotalVAT() {
        return totalVAT;
    }

    public void setTotalVAT(Double totalVAT) {
        this.totalVAT = totalVAT;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getUnAllocatedAmount() {
        return unAllocatedAmount;
    }

    public void setUnAllocatedAmount(Double unAllocatedAmount) {
        this.unAllocatedAmount = unAllocatedAmount;
    }

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SupplierAdjustment)) return false;
        if (!super.equals(o)) return false;
        SupplierAdjustment that = (SupplierAdjustment) o;
        return Objects.equals(getSupplierAdjustmentID(), that.getSupplierAdjustmentID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getSupplierID(), that.getSupplierID()) &&
                Objects.equals(getDocNumber(), that.getDocNumber()) &&
                Objects.equals(getAdjustmentNumber(), that.getAdjustmentNumber()) &&
                Objects.equals(getEffectType(), that.getEffectType()) &&
                Objects.equals(getVatType(), that.getVatType()) &&
                Objects.equals(getTotalVAT(), that.getTotalVAT()) &&
                Objects.equals(getTotalAmount(), that.getTotalAmount()) &&
                Objects.equals(getUnAllocatedAmount(), that.getUnAllocatedAmount()) &&
                Objects.equals(getAccountID(), that.getAccountID()) &&
                Objects.equals(getNote(), that.getNote()) &&
                Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSupplierAdjustmentID(), getBusinessID(), getDate(), getSupplierID(), getDocNumber(), getAdjustmentNumber(), getEffectType(), getVatType(), getTotalVAT(), getTotalAmount(), getUnAllocatedAmount(), getAccountID(), getNote(), getDescription());
    }

    @Override
    public String toString() {
        return "SupplierAdjustment{" +
                "supplierAdjustmentID=" + supplierAdjustmentID +
                ", businessID=" + businessID +
                ", date=" + date +
                ", supplierID=" + supplierID +
                ", docNumber='" + docNumber + '\'' +
                ", adjustmentNumber='" + adjustmentNumber + '\'' +
                ", effectType=" + effectType +
                ", vatType=" + vatType +
                ", totalVAT=" + totalVAT +
                ", totalAmount=" + totalAmount +
                ", unAllocatedAmount=" + unAllocatedAmount +
                ", accountID=" + accountID +
                ", note='" + note + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
