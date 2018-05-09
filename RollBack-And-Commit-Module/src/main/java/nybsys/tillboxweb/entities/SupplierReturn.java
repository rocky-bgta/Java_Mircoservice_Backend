/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/23/2018
 * Time: 12:56 PM
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
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
public class SupplierReturn extends BaseEntityWithCurrency {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "supplierReturnID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    private Integer supplierReturnID;

    @Column
    @NotNull
    private Integer businessID;
    private Integer purchaseOrderID;
    private String supplierReturnNumber;
    private String docNumber;
    private Integer supplierID;
    private Double totalAmount;
    private Double totalVAT;
    private Double totalDiscount;
    private Date returnDate;

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(Double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public Integer getSupplierReturnID() {
        return supplierReturnID;
    }

    public void setSupplierReturnID(Integer supplierReturnID) {
        this.supplierReturnID = supplierReturnID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getPurchaseOrderID() {
        return purchaseOrderID;
    }

    public void setPurchaseOrderID(Integer purchaseOrderID) {
        this.purchaseOrderID = purchaseOrderID;
    }

    public String getSupplierReturnNumber() {
        return supplierReturnNumber;
    }

    public void setSupplierReturnNumber(String supplierReturnNumber) {
        this.supplierReturnNumber = supplierReturnNumber;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public Integer getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(Integer supplierID) {
        this.supplierID = supplierID;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SupplierReturn)) return false;
        if (!super.equals(o)) return false;
        SupplierReturn that = (SupplierReturn) o;
        return Objects.equals(getSupplierReturnID(), that.getSupplierReturnID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getPurchaseOrderID(), that.getPurchaseOrderID()) &&
                Objects.equals(getSupplierReturnNumber(), that.getSupplierReturnNumber()) &&
                Objects.equals(getDocNumber(), that.getDocNumber()) &&
                Objects.equals(getSupplierID(), that.getSupplierID()) &&
                Objects.equals(getTotalAmount(), that.getTotalAmount()) &&
                Objects.equals(getTotalVAT(), that.getTotalVAT());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSupplierReturnID(), getBusinessID(), getPurchaseOrderID(), getSupplierReturnNumber(), getDocNumber(), getSupplierID(), getTotalAmount(), getTotalVAT());
    }

    @Override
    public String toString() {
        return "SupplierReturn{" +
                "supplierReturnID=" + supplierReturnID +
                ", businessID=" + businessID +
                ", purchaseOrderID=" + purchaseOrderID +
                ", supplierReturnNumber='" + supplierReturnNumber + '\'' +
                ", docNumber='" + docNumber + '\'' +
                ", supplierID=" + supplierID +
                ", totalAmount=" + totalAmount +
                ", totalVAT=" + totalVAT +
                '}';
    }
}
