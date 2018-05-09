/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 03:20
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class CustomerQuotationAddressModel extends BaseModel {
    private Integer customerQuotationAddressID;
    private Integer businessID;
    private Integer customerAddressTypeID;
    private Integer customerQuotationID;
    private Integer customerAddressID;

    public Integer getCustomerQuotationAddressID() {
        return customerQuotationAddressID;
    }

    public void setCustomerQuotationAddressID(Integer customerQuotationAddressID) {
        this.customerQuotationAddressID = customerQuotationAddressID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getCustomerAddressTypeID() {
        return customerAddressTypeID;
    }

    public void setCustomerAddressTypeID(Integer customerAddressTypeID) {
        this.customerAddressTypeID = customerAddressTypeID;
    }

    public Integer getCustomerQuotationID() {
        return customerQuotationID;
    }

    public void setCustomerQuotationID(Integer customerQuotationID) {
        this.customerQuotationID = customerQuotationID;
    }

    public Integer getCustomerAddressID() {
        return customerAddressID;
    }

    public void setCustomerAddressID(Integer customerAddressID) {
        this.customerAddressID = customerAddressID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerQuotationAddressModel)) return false;
        if (!super.equals(o)) return false;
        CustomerQuotationAddressModel that = (CustomerQuotationAddressModel) o;
        return Objects.equals(getCustomerQuotationAddressID(), that.getCustomerQuotationAddressID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getCustomerAddressTypeID(), that.getCustomerAddressTypeID()) &&
                Objects.equals(getCustomerQuotationID(), that.getCustomerQuotationID()) &&
                Objects.equals(getCustomerAddressID(), that.getCustomerAddressID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCustomerQuotationAddressID(), getBusinessID(), getCustomerAddressTypeID(), getCustomerQuotationID(), getCustomerAddressID());
    }

    @Override
    public String toString() {
        return "CustomerQuotationAddressModel{" +
                "customerQuotationAddressID=" + customerQuotationAddressID +
                ", businessID=" + businessID +
                ", customerAddressTypeID=" + customerAddressTypeID +
                ", customerQuotationID=" + customerQuotationID +
                ", customerAddressID=" + customerAddressID +
                '}';
    }
}
