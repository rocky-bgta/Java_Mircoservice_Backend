/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 13/02/2018
 * Time: 10:52
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class ProductAttributeModel extends BaseModel {

    private Integer productAttributeID;
    private Integer businessID;
    private String name;

    public Integer getProductAttributeID() {
        return productAttributeID;
    }

    public void setProductAttributeID(Integer productAttributeID) {
        this.productAttributeID = productAttributeID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductAttributeModel)) return false;
        if (!super.equals(o)) return false;
        ProductAttributeModel that = (ProductAttributeModel) o;
        return Objects.equals(getProductAttributeID(), that.getProductAttributeID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getProductAttributeID(), getBusinessID(), getName());
    }

    @Override
    public String toString() {
        return "ProductAttributeModel{" +
                "productAttributeID=" + productAttributeID +
                ", businessID=" + businessID +
                ", name='" + name + '\'' +
                '}';
    }
}
