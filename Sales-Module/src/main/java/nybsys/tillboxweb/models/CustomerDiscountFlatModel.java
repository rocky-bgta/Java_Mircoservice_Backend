/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 04:14
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class CustomerDiscountFlatModel extends BaseModel {
    private Integer customerDiscountFlatID;
    private Integer businessID;
    private Integer customerID;
    private Double percentAmount;

    public Integer getCustomerDiscountFlatID() {
        return customerDiscountFlatID;
    }

    public void setCustomerDiscountFlatID(Integer customerDiscountFlatID) {
        this.customerDiscountFlatID = customerDiscountFlatID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public Double getPercentAmount() {
        return percentAmount;
    }

    public void setPercentAmount(Double percentAmount) {
        this.percentAmount = percentAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerDiscountFlatModel)) return false;
        if (!super.equals(o)) return false;
        CustomerDiscountFlatModel that = (CustomerDiscountFlatModel) o;
        return Objects.equals(getCustomerDiscountFlatID(), that.getCustomerDiscountFlatID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getCustomerID(), that.getCustomerID()) &&
                Objects.equals(getPercentAmount(), that.getPercentAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCustomerDiscountFlatID(), getBusinessID(), getCustomerID(), getPercentAmount());
    }

    @Override
    public String toString() {
        return "CustomerDiscountFlatModel{" +
                "customerDiscountFlatID=" + customerDiscountFlatID +
                ", businessID=" + businessID +
                ", customerID=" + customerID +
                ", percentAmount=" + percentAmount +
                '}';
    }
}
