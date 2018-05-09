/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/28/2018
 * Time: 3:34 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Date;

public class InventoryTransactionModel extends BaseModel {

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
}
