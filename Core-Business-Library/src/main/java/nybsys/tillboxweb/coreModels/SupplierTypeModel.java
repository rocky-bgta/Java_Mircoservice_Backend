/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 21/03/2018
 * Time: 02:01
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.coreModels;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class SupplierTypeModel extends BaseModel{
    private Integer supplierTypeID;
    private Integer businessID;
    private String name;
    private String description;
    private Double discountPercent;

    public Integer getSupplierTypeID() {
        return supplierTypeID;
    }

    public void setSupplierTypeID(Integer supplierTypeID) {
        this.supplierTypeID = supplierTypeID;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Double discountPercent) {
        this.discountPercent = discountPercent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SupplierTypeModel)) return false;
        if (!super.equals(o)) return false;
        SupplierTypeModel that = (SupplierTypeModel) o;
        return Objects.equals(getSupplierTypeID(), that.getSupplierTypeID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getDiscountPercent(), that.getDiscountPercent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSupplierTypeID(), getBusinessID(), getName(), getDescription(), getDiscountPercent());
    }

    @Override
    public String toString() {
        return "SupplierTypeModel{" +
                "supplierTypeID=" + supplierTypeID +
                ", businessID=" + businessID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", discountPercent=" + discountPercent +
                '}';
    }
}
