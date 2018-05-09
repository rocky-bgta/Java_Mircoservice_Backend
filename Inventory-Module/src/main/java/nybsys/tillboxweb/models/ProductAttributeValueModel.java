/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 13/02/2018
 * Time: 10:54
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class ProductAttributeValueModel extends BaseModel {

    private Integer productAttributeValueID;
    private Integer businessID;
    private Integer productAttributeID;
    private String value;

    public Integer getProductAttributeValueID() {
        return productAttributeValueID;
    }

    public void setProductAttributeValueID(Integer productAttributeValueID) {
        this.productAttributeValueID = productAttributeValueID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getProductAttributeID() {
        return productAttributeID;
    }

    public void setProductAttributeID(Integer productAttributeID) {
        this.productAttributeID = productAttributeID;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductAttributeValueModel)) return false;
        if (!super.equals(o)) return false;
        ProductAttributeValueModel that = (ProductAttributeValueModel) o;
        return Objects.equals(getProductAttributeValueID(), that.getProductAttributeValueID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getProductAttributeID(), that.getProductAttributeID()) &&
                Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getProductAttributeValueID(), getBusinessID(), getProductAttributeID(), getValue());
    }

    @Override
    public String toString() {
        return "ProductAttributeValueModel{" +
                "productAttributeValueID=" + productAttributeValueID +
                ", businessID=" + businessID +
                ", productAttributeID=" + productAttributeID +
                ", value='" + value + '\'' +
                '}';
    }
}
