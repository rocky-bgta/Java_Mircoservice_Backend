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

public class CustomerTypeModel extends BaseModel{
    private Integer customerTypeID;
    private Integer businessID;
    private String name;
    private String description;
    private Double discountPercent;

    public Integer getCustomerTypeID() {
        return customerTypeID;
    }

    public void setCustomerTypeID(Integer customerTypeID) {
        this.customerTypeID = customerTypeID;
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
        if (!(o instanceof CustomerTypeModel)) return false;
        if (!super.equals(o)) return false;
        CustomerTypeModel that = (CustomerTypeModel) o;
        return Objects.equals(getCustomerTypeID(), that.getCustomerTypeID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getDiscountPercent(), that.getDiscountPercent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCustomerTypeID(), getBusinessID(), getName(), getDescription(), getDiscountPercent());
    }

    @Override
    public String toString() {
        return "CustomerTypeModel{" +
                "customerTypeID=" + customerTypeID +
                ", businessID=" + businessID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", discountPercent=" + discountPercent +
                '}';
    }
}
