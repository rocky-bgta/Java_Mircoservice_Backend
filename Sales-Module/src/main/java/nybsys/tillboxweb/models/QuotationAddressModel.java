/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 04:02
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class QuotationAddressModel extends BaseModel {
    private Integer quotationAddressID;
    private Integer businessID;
    private Integer customerAddressTypeID;
    private Integer customerQuotationID;
    private Integer customerAddressID;

    public Integer getQuotationAddressID() {
        return quotationAddressID;
    }

    public void setQuotationAddressID(Integer quotationAddressID) {
        this.quotationAddressID = quotationAddressID;
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
        if (!(o instanceof QuotationAddressModel)) return false;
        if (!super.equals(o)) return false;
        QuotationAddressModel that = (QuotationAddressModel) o;
        return Objects.equals(getQuotationAddressID(), that.getQuotationAddressID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getCustomerAddressTypeID(), that.getCustomerAddressTypeID()) &&
                Objects.equals(getCustomerQuotationID(), that.getCustomerQuotationID()) &&
                Objects.equals(getCustomerAddressID(), that.getCustomerAddressID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getQuotationAddressID(), getBusinessID(), getCustomerAddressTypeID(), getCustomerQuotationID(), getCustomerAddressID());
    }

    @Override
    public String toString() {
        return "QuotationAddressModel{" +
                "quotationAddressID=" + quotationAddressID +
                ", businessID=" + businessID +
                ", customerAddressTypeID=" + customerAddressTypeID +
                ", customerQuotationID=" + customerQuotationID +
                ", customerAddressID=" + customerAddressID +
                '}';
    }
}
