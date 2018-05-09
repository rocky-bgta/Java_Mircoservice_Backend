/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/28/2018
 * Time: 3:38 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
public class InventoryTransaction extends BaseEntity {


    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "inventoryTransactionID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    @NotNull
    @Column
    private Integer inventoryTransactionID;
    private Integer businessID;
    private Date date;
    private Integer referenceType;
    private Integer referenceID;
    private Integer productID;
    private Integer productCategoryID;
    private Integer productTypeID;
    private Double inQuantity;
    private Double outQuantity;

    public Integer getInventoryTransactionID() {
        return inventoryTransactionID;
    }

    public void setInventoryTransactionID(Integer inventoryTransactionID) {
        this.inventoryTransactionID = inventoryTransactionID;
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

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Integer getProductCategoryID() {
        return productCategoryID;
    }

    public void setProductCategoryID(Integer productCategoryID) {
        this.productCategoryID = productCategoryID;
    }

    public Integer getProductTypeID() {
        return productTypeID;
    }

    public void setProductTypeID(Integer productTypeID) {
        this.productTypeID = productTypeID;
    }

    public Double getInQuantity() {
        return inQuantity;
    }

    public void setInQuantity(Double inQuantity) {
        this.inQuantity = inQuantity;
    }

    public Double getOutQuantity() {
        return outQuantity;
    }

    public void setOutQuantity(Double outQuantity) {
        this.outQuantity = outQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InventoryTransaction)) return false;
        if (!super.equals(o)) return false;
        InventoryTransaction that = (InventoryTransaction) o;
        return Objects.equals(getInventoryTransactionID(), that.getInventoryTransactionID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getReferenceType(), that.getReferenceType()) &&
                Objects.equals(getReferenceID(), that.getReferenceID()) &&
                Objects.equals(getProductID(), that.getProductID()) &&
                Objects.equals(getProductCategoryID(), that.getProductCategoryID()) &&
                Objects.equals(getProductTypeID(), that.getProductTypeID()) &&
                Objects.equals(getInQuantity(), that.getInQuantity()) &&
                Objects.equals(getOutQuantity(), that.getOutQuantity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getInventoryTransactionID(), getBusinessID(), getDate(), getReferenceType(), getReferenceID(), getProductID(), getProductCategoryID(), getProductTypeID(), getInQuantity(), getOutQuantity());
    }

    @Override
    public String toString() {
        return "InventoryTransaction{" +
                "inventoryTransactionID=" + inventoryTransactionID +
                ", businessID=" + businessID +
                ", date=" + date +
                ", referenceType=" + referenceType +
                ", referenceID=" + referenceID +
                ", productID=" + productID +
                ", productCategoryID=" + productCategoryID +
                ", productTypeID=" + productTypeID +
                ", inQuantity=" + inQuantity +
                ", outQuantity=" + outQuantity +
                '}';
    }
}
