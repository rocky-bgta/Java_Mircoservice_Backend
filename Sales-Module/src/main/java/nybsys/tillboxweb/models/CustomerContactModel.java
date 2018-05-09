/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 23/02/2018
 * Time: 10:49
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class CustomerContactModel extends BaseModel {
    private Integer customerContactID;
    private Integer customerID;
    private Integer contactTypeID;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String state;
    private String province;
    private String zipCode;

    public Integer getCustomerContactID() {
        return customerContactID;
    }

    public void setCustomerContactID(Integer customerContactID) {
        this.customerContactID = customerContactID;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public Integer getContactTypeID() {
        return contactTypeID;
    }

    public void setContactTypeID(Integer contactTypeID) {
        this.contactTypeID = contactTypeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerContactModel)) return false;
        if (!super.equals(o)) return false;
        CustomerContactModel that = (CustomerContactModel) o;
        return Objects.equals(getCustomerContactID(), that.getCustomerContactID()) &&
                Objects.equals(getCustomerID(), that.getCustomerID()) &&
                Objects.equals(getContactTypeID(), that.getContactTypeID()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getPhone(), that.getPhone()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getAddress(), that.getAddress()) &&
                Objects.equals(getState(), that.getState()) &&
                Objects.equals(getProvince(), that.getProvince()) &&
                Objects.equals(getZipCode(), that.getZipCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCustomerContactID(), getCustomerID(), getContactTypeID(), getName(), getPhone(), getEmail(), getAddress(), getState(), getProvince(), getZipCode());
    }

    @Override
    public String toString() {
        return "CustomerContactModel{" +
                "customerContactID=" + customerContactID +
                ", customerID=" + customerID +
                ", contactTypeID=" + contactTypeID +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", state='" + state + '\'' +
                ", province='" + province + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
