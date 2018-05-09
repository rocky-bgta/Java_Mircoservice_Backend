/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 13/02/2018
 * Time: 10:50
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.coreModels;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class ProductCategoryModel extends BaseModel {

    private Integer productCategoryID;
    private Integer businessID;
    private String name;

    public Integer getProductCategoryID() {
        return productCategoryID;
    }

    public void setProductCategoryID(Integer productCategoryID) {
        this.productCategoryID = productCategoryID;
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
        if (!(o instanceof ProductCategoryModel)) return false;
        if (!super.equals(o)) return false;
        ProductCategoryModel that = (ProductCategoryModel) o;
        return Objects.equals(getProductCategoryID(), that.getProductCategoryID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getProductCategoryID(), getBusinessID(), getName());
    }

    @Override
    public String toString() {
        return "ProductCategoryModel{" +
                "productCategoryID=" + productCategoryID +
                ", businessID=" + businessID +
                ", name='" + name + '\'' +
                '}';
    }
}
