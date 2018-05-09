/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/23/2018
 * Time: 12:54 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import nybsys.tillboxweb.BaseEntityWithCurrency;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
public class PurchaseOrder extends BaseEntityWithCurrency {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "purchaseOrderID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    private Integer purchaseOrderID;
    @Column
    @NotNull
    private Integer businessID;
    private String docNumber;
    private String orderNumber;
    private Date date;
    private Date deliveryDate;
    private Integer supplierID;
    private Double total;
    private Double totalVAT;
    private Double totalDiscount;
    private Integer purchaseOrderStatus;
    private String note;

    public Integer getPurchaseOrderID() {
        return purchaseOrderID;
    }

    public void setPurchaseOrderID(Integer purchaseOrderID) {
        this.purchaseOrderID = purchaseOrderID;
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

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Integer getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(Integer supplierID) {
        this.supplierID = supplierID;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
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

    public Integer getPurchaseOrderStatus() {
        return purchaseOrderStatus;
    }

    public void setPurchaseOrderStatus(Integer purchaseOrderStatus) {
        this.purchaseOrderStatus = purchaseOrderStatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PurchaseOrder)) return false;
        if (!super.equals(o)) return false;
        PurchaseOrder that = (PurchaseOrder) o;
        return Objects.equals(getPurchaseOrderID(), that.getPurchaseOrderID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getDocNumber(), that.getDocNumber()) &&
                Objects.equals(getOrderNumber(), that.getOrderNumber()) &&
                Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getDeliveryDate(), that.getDeliveryDate()) &&
                Objects.equals(getSupplierID(), that.getSupplierID()) &&
                Objects.equals(getTotal(), that.getTotal()) &&
                Objects.equals(getTotalVAT(), that.getTotalVAT()) &&
                Objects.equals(getTotalDiscount(), that.getTotalDiscount()) &&
                Objects.equals(getPurchaseOrderStatus(), that.getPurchaseOrderStatus()) &&
                Objects.equals(getNote(), that.getNote());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPurchaseOrderID(), getBusinessID(), getDocNumber(), getOrderNumber(), getDate(), getDeliveryDate(), getSupplierID(), getTotal(), getTotalVAT(), getTotalDiscount(), getPurchaseOrderStatus(), getNote());
    }

    @Override
    public String toString() {
        return "PurchaseOrder{" +
                "purchaseOrderID=" + purchaseOrderID +
                ", businessID=" + businessID +
                ", docNumber='" + docNumber + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", date=" + date +
                ", deliveryDate=" + deliveryDate +
                ", supplierID=" + supplierID +
                ", total=" + total +
                ", totalVAT=" + totalVAT +
                ", totalDiscount=" + totalDiscount +
                ", purchaseOrderStatus=" + purchaseOrderStatus +
                ", note='" + note + '\'' +
                '}';
    }
}
