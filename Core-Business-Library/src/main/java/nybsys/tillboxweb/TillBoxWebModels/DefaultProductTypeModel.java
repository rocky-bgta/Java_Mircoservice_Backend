/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 13/02/2018
 * Time: 10:48
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.TillBoxWebModels;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class DefaultProductTypeModel extends BaseModel {

    private Integer productTypeID;
    private Integer businessID;
    private String name;

    public Integer getProductTypeID() {
        return productTypeID;
    }

    public void setProductTypeID(Integer productTypeID) {
        this.productTypeID = productTypeID;
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
        if (!(o instanceof DefaultProductTypeModel)) return false;
        if (!super.equals(o)) return false;
        DefaultProductTypeModel that = (DefaultProductTypeModel) o;
        return Objects.equals(getProductTypeID(), that.getProductTypeID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getProductTypeID(), getBusinessID(), getName());
    }

    @Override
    public String toString() {
        return "ProductTypeModel{" +
                "productTypeID=" + productTypeID +
                ", businessID=" + businessID +
                ", name='" + name + '\'' +
                '}';
    }
}
