/**
 * Created By: Md. Abdul Hannan
 * Created Date: 4/17/2018
 * Time: 7:10 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class CompanyAddressModel extends BaseModel {
    Integer companyAddressID;
    Integer businessID;
    Integer addressType;
    String addressLine1;
    String addressLine2;
    String addressLine3;
    String state;
    String city;
    Integer country;
    String postalCode;

    public Integer getCompanyAddressID() {
        return companyAddressID;
    }

    public void setCompanyAddressID(Integer companyAddressID) {
        this.companyAddressID = companyAddressID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getAddressType() {
        return addressType;
    }

    public void setAddressType(Integer addressType) {
        this.addressType = addressType;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompanyAddressModel)) return false;
        if (!super.equals(o)) return false;
        CompanyAddressModel that = (CompanyAddressModel) o;
        return Objects.equals(getCompanyAddressID(), that.getCompanyAddressID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getAddressType(), that.getAddressType()) &&
                Objects.equals(getAddressLine1(), that.getAddressLine1()) &&
                Objects.equals(getAddressLine2(), that.getAddressLine2()) &&
                Objects.equals(getAddressLine3(), that.getAddressLine3()) &&
                Objects.equals(getState(), that.getState()) &&
                Objects.equals(getCity(), that.getCity()) &&
                Objects.equals(getCountry(), that.getCountry()) &&
                Objects.equals(getPostalCode(), that.getPostalCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCompanyAddressID(), getBusinessID(), getAddressType(), getAddressLine1(), getAddressLine2(), getAddressLine3(), getState(), getCity(), getCountry(), getPostalCode());
    }

    @Override
    public String toString() {
        return "CompanyAddressModel{" +
                "companyAddressID=" + companyAddressID +
                ", businessID=" + businessID +
                ", addressType=" + addressType +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", addressLine3='" + addressLine3 + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", country=" + country +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
