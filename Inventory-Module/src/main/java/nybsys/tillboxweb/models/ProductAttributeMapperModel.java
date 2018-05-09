/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 13/02/2018
 * Time: 12:27
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class ProductAttributeMapperModel extends BaseModel{

    private Integer productAttributeMapperID;
    private Integer businessID;
    private Integer productID;
    private Integer productAttributeID;
    private Integer productAttributeValueID;

    public Integer getProductAttributeMapperID() {
        return productAttributeMapperID;
    }

    public void setProductAttributeMapperID(Integer productAttributeMapperID) {
        this.productAttributeMapperID = productAttributeMapperID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Integer getProductAttributeID() {
        return productAttributeID;
    }

    public void setProductAttributeID(Integer productAttributeID) {
        this.productAttributeID = productAttributeID;
    }

    public Integer getProductAttributeValueID() {
        return productAttributeValueID;
    }

    public void setProductAttributeValueID(Integer productAttributeValueID) {
        this.productAttributeValueID = productAttributeValueID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductAttributeMapperModel)) return false;
        if (!super.equals(o)) return false;
        ProductAttributeMapperModel that = (ProductAttributeMapperModel) o;
        return Objects.equals(getProductAttributeMapperID(), that.getProductAttributeMapperID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getProductID(), that.getProductID()) &&
                Objects.equals(getProductAttributeID(), that.getProductAttributeID()) &&
                Objects.equals(getProductAttributeValueID(), that.getProductAttributeValueID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getProductAttributeMapperID(), getBusinessID(), getProductID(), getProductAttributeID(), getProductAttributeValueID());
    }

    @Override
    public String toString() {
        return "ProductAttributeMapperModel{" +
                "productAttributeMapperID=" + productAttributeMapperID +
                ", businessID=" + businessID +
                ", productID=" + productID +
                ", productAttributeID=" + productAttributeID +
                ", productAttributeValueID=" + productAttributeValueID +
                '}';
    }
}
