/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 13/03/2018
 * Time: 02:19
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Date;
import java.util.Objects;

public class CustomerModel extends BaseModel {
    private Integer customerID;
    private Integer businessID;
    private Integer customerCategoryID;
    private Integer customerTypeID;
    private String customerName;
    private String address;
    private String phone;
    private String email;
    private Double openingBalanceAmount;
    private Date openingBalanceDate;
    private Integer preferredCurrencyID;
    private Boolean autoAllocateReceipt;
    private Boolean acceptElectronicInvoice;
    private Boolean viewInvoiceOnline;

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getCustomerCategoryID() {
        return customerCategoryID;
    }

    public void setCustomerCategoryID(Integer customerCategoryID) {
        this.customerCategoryID = customerCategoryID;
    }

    public Integer getCustomerTypeID() {
        return customerTypeID;
    }

    public void setCustomerTypeID(Integer customerTypeID) {
        this.customerTypeID = customerTypeID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getOpeningBalanceAmount() {
        return openingBalanceAmount;
    }

    public void setOpeningBalanceAmount(Double openingBalanceAmount) {
        this.openingBalanceAmount = openingBalanceAmount;
    }

    public Date getOpeningBalanceDate() {
        return openingBalanceDate;
    }

    public void setOpeningBalanceDate(Date openingBalanceDate) {
        this.openingBalanceDate = openingBalanceDate;
    }

    public Integer getPreferredCurrencyID() {
        return preferredCurrencyID;
    }

    public void setPreferredCurrencyID(Integer preferredCurrencyID) {
        this.preferredCurrencyID = preferredCurrencyID;
    }

    public Boolean getAutoAllocateReceipt() {
        return autoAllocateReceipt;
    }

    public void setAutoAllocateReceipt(Boolean autoAllocateReceipt) {
        this.autoAllocateReceipt = autoAllocateReceipt;
    }

    public Boolean getAcceptElectronicInvoice() {
        return acceptElectronicInvoice;
    }

    public void setAcceptElectronicInvoice(Boolean acceptElectronicInvoice) {
        this.acceptElectronicInvoice = acceptElectronicInvoice;
    }

    public Boolean getViewInvoiceOnline() {
        return viewInvoiceOnline;
    }

    public void setViewInvoiceOnline(Boolean viewInvoiceOnline) {
        this.viewInvoiceOnline = viewInvoiceOnline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerModel)) return false;
        if (!super.equals(o)) return false;
        CustomerModel that = (CustomerModel) o;
        return Objects.equals(getCustomerID(), that.getCustomerID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getCustomerCategoryID(), that.getCustomerCategoryID()) &&
                Objects.equals(getCustomerTypeID(), that.getCustomerTypeID()) &&
                Objects.equals(getCustomerName(), that.getCustomerName()) &&
                Objects.equals(getAddress(), that.getAddress()) &&
                Objects.equals(getPhone(), that.getPhone()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getOpeningBalanceAmount(), that.getOpeningBalanceAmount()) &&
                Objects.equals(getOpeningBalanceDate(), that.getOpeningBalanceDate()) &&
                Objects.equals(getPreferredCurrencyID(), that.getPreferredCurrencyID()) &&
                Objects.equals(getAutoAllocateReceipt(), that.getAutoAllocateReceipt()) &&
                Objects.equals(getAcceptElectronicInvoice(), that.getAcceptElectronicInvoice()) &&
                Objects.equals(getViewInvoiceOnline(), that.getViewInvoiceOnline());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCustomerID(), getBusinessID(), getCustomerCategoryID(), getCustomerTypeID(), getCustomerName(), getAddress(), getPhone(), getEmail(), getOpeningBalanceAmount(), getOpeningBalanceDate(), getPreferredCurrencyID(), getAutoAllocateReceipt(), getAcceptElectronicInvoice(), getViewInvoiceOnline());
    }

    @Override
    public String toString() {
        return "CustomerModel{" +
                "customerID=" + customerID +
                ", businessID=" + businessID +
                ", customerCategoryID=" + customerCategoryID +
                ", customerTypeID=" + customerTypeID +
                ", customerName='" + customerName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", openingBalanceAmount=" + openingBalanceAmount +
                ", openingBalanceDate=" + openingBalanceDate +
                ", preferredCurrencyID=" + preferredCurrencyID +
                ", autoAllocateReceipt=" + autoAllocateReceipt +
                ", acceptElectronicInvoice=" + acceptElectronicInvoice +
                ", viewInvoiceOnline=" + viewInvoiceOnline +
                '}';
    }
}
