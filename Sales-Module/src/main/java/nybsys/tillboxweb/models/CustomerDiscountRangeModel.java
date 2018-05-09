/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 04:18
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class CustomerDiscountRangeModel extends BaseModel {
    private Integer customerDiscountRangeID;
    private Integer businessID;
    private Integer customerID;
    private Double rangeStart;
    private Double rangeEnd;
    private Boolean isPercent;
    private Double amount;

    public Integer getCustomerDiscountRangeID() {
        return customerDiscountRangeID;
    }

    public void setCustomerDiscountRangeID(Integer customerDiscountRangeID) {
        this.customerDiscountRangeID = customerDiscountRangeID;
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

    public Double getRangeStart() {
        return rangeStart;
    }

    public void setRangeStart(Double rangeStart) {
        this.rangeStart = rangeStart;
    }

    public Double getRangeEnd() {
        return rangeEnd;
    }

    public void setRangeEnd(Double rangeEnd) {
        this.rangeEnd = rangeEnd;
    }

    public Boolean getPercent() {
        return isPercent;
    }

    public void setPercent(Boolean percent) {
        isPercent = percent;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerDiscountRangeModel)) return false;
        if (!super.equals(o)) return false;
        CustomerDiscountRangeModel that = (CustomerDiscountRangeModel) o;
        return Objects.equals(getCustomerDiscountRangeID(), that.getCustomerDiscountRangeID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getCustomerID(), that.getCustomerID()) &&
                Objects.equals(getRangeStart(), that.getRangeStart()) &&
                Objects.equals(getRangeEnd(), that.getRangeEnd()) &&
                Objects.equals(isPercent, that.isPercent) &&
                Objects.equals(getAmount(), that.getAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCustomerDiscountRangeID(), getBusinessID(), getCustomerID(), getRangeStart(), getRangeEnd(), isPercent, getAmount());
    }

    @Override
    public String toString() {
        return "CustomerDiscountRangeModel{" +
                "customerDiscountRangeID=" + customerDiscountRangeID +
                ", businessID=" + businessID +
                ", customerID=" + customerID +
                ", rangeStart=" + rangeStart +
                ", rangeEnd=" + rangeEnd +
                ", isPercent=" + isPercent +
                ", amount=" + amount +
                '}';
    }
}
