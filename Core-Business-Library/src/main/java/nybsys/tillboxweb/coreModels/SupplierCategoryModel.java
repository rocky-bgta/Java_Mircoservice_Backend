/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 22/02/2018
 * Time: 11:00
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.coreModels;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class SupplierCategoryModel extends BaseModel{
    private Integer supplierCategoryID;
    private Integer businessID;
    private String categoryName;

    public Integer getSupplierCategoryID() {
        return supplierCategoryID;
    }

    public void setSupplierCategoryID(Integer supplierCategoryID) {
        this.supplierCategoryID = supplierCategoryID;
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
        if (!(o instanceof SupplierCategoryModel)) return false;
        if (!super.equals(o)) return false;
        SupplierCategoryModel that = (SupplierCategoryModel) o;
        return Objects.equals(getSupplierCategoryID(), that.getSupplierCategoryID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getCategoryName(), that.getCategoryName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSupplierCategoryID(), getBusinessID(), getCategoryName());
    }

    @Override
    public String toString() {
        return "SupplierCategoryModel{" +
                "supplierCategoryID=" + supplierCategoryID +
                ", businessID=" + businessID +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
