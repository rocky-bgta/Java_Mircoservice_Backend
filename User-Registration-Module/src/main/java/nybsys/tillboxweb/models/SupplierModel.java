/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 22/02/2018
 * Time: 11:31
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Date;
import java.util.Objects;

public class SupplierModel extends BaseModel {
    private Integer supplierID;
    private Integer businessID;
    private Integer supplierCategoryID;
    private Integer supplierTypeID;
    private String supplierName;
    private String address;
    private String phone;
    private String email;
    private Double openingBalanceAmount;
    private Date openingBalanceDate;
    private Boolean autoAllocatePayment;
    private Integer preferredCurrencyID;

    public Integer getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(Integer supplierID) {
        this.supplierID = supplierID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getSupplierCategoryID() {
        return supplierCategoryID;
    }

    public void setSupplierCategoryID(Integer supplierCategoryID) {
        this.supplierCategoryID = supplierCategoryID;
    }

    public Integer getSupplierTypeID() {
        return supplierTypeID;
    }

    public void setSupplierTypeID(Integer supplierTypeID) {
        this.supplierTypeID = supplierTypeID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    public Boolean getAutoAllocatePayment() {
        return autoAllocatePayment;
    }

    public void setAutoAllocatePayment(Boolean autoAllocatePayment) {
        this.autoAllocatePayment = autoAllocatePayment;
    }

    public Integer getPreferredCurrencyID() {
        return preferredCurrencyID;
    }

    public void setPreferredCurrencyID(Integer preferredCurrencyID) {
        this.preferredCurrencyID = preferredCurrencyID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SupplierModel)) return false;
        if (!super.equals(o)) return false;
        SupplierModel that = (SupplierModel) o;
        return Objects.equals(getSupplierID(), that.getSupplierID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getSupplierCategoryID(), that.getSupplierCategoryID()) &&
                Objects.equals(getSupplierTypeID(), that.getSupplierTypeID()) &&
                Objects.equals(getSupplierName(), that.getSupplierName()) &&
                Objects.equals(getAddress(), that.getAddress()) &&
                Objects.equals(getPhone(), that.getPhone()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getOpeningBalanceAmount(), that.getOpeningBalanceAmount()) &&
                Objects.equals(getOpeningBalanceDate(), that.getOpeningBalanceDate()) &&
                Objects.equals(getAutoAllocatePayment(), that.getAutoAllocatePayment()) &&
                Objects.equals(getPreferredCurrencyID(), that.getPreferredCurrencyID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSupplierID(), getBusinessID(), getSupplierCategoryID(), getSupplierTypeID(), getSupplierName(), getAddress(), getPhone(), getEmail(), getOpeningBalanceAmount(), getOpeningBalanceDate(), getAutoAllocatePayment(), getPreferredCurrencyID());
    }

    @Override
    public String toString() {
        return "SupplierModel{" +
                "supplierID=" + supplierID +
                ", businessID=" + businessID +
                ", supplierCategoryID=" + supplierCategoryID +
                ", supplierTypeID=" + supplierTypeID +
                ", supplierName='" + supplierName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", openingBalanceAmount=" + openingBalanceAmount +
                ", openingBalanceDate=" + openingBalanceDate +
                ", autoAllocatePayment=" + autoAllocatePayment +
                ", preferredCurrencyID=" + preferredCurrencyID +
                '}';
    }
}
