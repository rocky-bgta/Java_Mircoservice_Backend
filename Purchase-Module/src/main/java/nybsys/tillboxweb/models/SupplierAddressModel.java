/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 22/02/2018
 * Time: 02:31
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class SupplierAddressModel extends BaseModel {
    private  Integer supplierAddressID;
    private  Integer supplierID;
    private  Integer addressType;
    private String phone;
    private String email;
    private String state;
    private String province;
    private String zipCode;

    public Integer getSupplierAddressID() {
        return supplierAddressID;
    }

    public void setSupplierAddressID(Integer supplierAddressID) {
        this.supplierAddressID = supplierAddressID;
    }

    public Integer getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(Integer supplierID) {
        this.supplierID = supplierID;
    }

    public Integer getAddressType() {
        return addressType;
    }

    public void setAddressType(Integer addressType) {
        this.addressType = addressType;
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
        if (!(o instanceof SupplierAddressModel)) return false;
        if (!super.equals(o)) return false;
        SupplierAddressModel that = (SupplierAddressModel) o;
        return Objects.equals(getSupplierAddressID(), that.getSupplierAddressID()) &&
                Objects.equals(getSupplierID(), that.getSupplierID()) &&
                Objects.equals(getAddressType(), that.getAddressType()) &&
                Objects.equals(getPhone(), that.getPhone()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getState(), that.getState()) &&
                Objects.equals(getProvince(), that.getProvince()) &&
                Objects.equals(getZipCode(), that.getZipCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSupplierAddressID(), getSupplierID(), getAddressType(), getPhone(), getEmail(), getState(), getProvince(), getZipCode());
    }

    @Override
    public String toString() {
        return "SupplierAddressModel{" +
                "supplierAddressID=" + supplierAddressID +
                ", supplierID=" + supplierID +
                ", addressType=" + addressType +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", state='" + state + '\'' +
                ", province='" + province + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
