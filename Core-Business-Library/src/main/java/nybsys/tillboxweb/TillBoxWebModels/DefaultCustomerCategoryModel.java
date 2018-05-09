/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 12/03/2018
 * Time: 11:59
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.TillBoxWebModels;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class DefaultCustomerCategoryModel extends BaseModel {
    private Integer customerCategoryID;
    private Integer businessID;
    private String categoryName;

    public Integer getCustomerCategoryID() {
        return customerCategoryID;
    }

    public void setCustomerCategoryID(Integer customerCategoryID) {
        this.customerCategoryID = customerCategoryID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultCustomerCategoryModel)) return false;
        if (!super.equals(o)) return false;
        DefaultCustomerCategoryModel that = (DefaultCustomerCategoryModel) o;
        return Objects.equals(getCustomerCategoryID(), that.getCustomerCategoryID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getCategoryName(), that.getCategoryName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCustomerCategoryID(), getBusinessID(), getCategoryName());
    }

    @Override
    public String toString() {
        return "CustomerCategoryModel{" +
                "customerCategoryID=" + customerCategoryID +
                ", businessID=" + businessID +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
