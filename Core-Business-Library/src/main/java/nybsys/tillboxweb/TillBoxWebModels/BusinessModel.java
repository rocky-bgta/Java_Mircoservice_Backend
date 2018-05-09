/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 09-Jan-18
 * Time: 1:52 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */
package nybsys.tillboxweb.TillBoxWebModels;



import nybsys.tillboxweb.BaseModel;

import java.math.BigInteger;
import java.util.Objects;

public class BusinessModel extends BaseModel {
    private Integer businessID;
    private BigInteger serialNo;
    private Integer productTypeID;
    private String businessName;
    private Integer businessTypeID;
    private String businessDBName;
    private String phone;
    private String email;
    private String firstName;
    private String lastName;
    private Integer subscriptionStatus;
    private String owner;

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public BigInteger getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(BigInteger serialNo) {
        this.serialNo = serialNo;
    }

    public Integer getProductTypeID() {
        return productTypeID;
    }

    public void setProductTypeID(Integer productTypeID) {
        this.productTypeID = productTypeID;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Integer getBusinessTypeID() {
        return businessTypeID;
    }

    public void setBusinessTypeID(Integer businessTypeID) {
        this.businessTypeID = businessTypeID;
    }

    public String getBusinessDBName() {
        return businessDBName;
    }

    public void setBusinessDBName(String businessDBName) {
        this.businessDBName = businessDBName;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(Integer subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BusinessModel)) return false;
        if (!super.equals(o)) return false;
        BusinessModel that = (BusinessModel) o;
        return Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getSerialNo(), that.getSerialNo()) &&
                Objects.equals(getProductTypeID(), that.getProductTypeID()) &&
                Objects.equals(getBusinessName(), that.getBusinessName()) &&
                Objects.equals(getBusinessTypeID(), that.getBusinessTypeID()) &&
                Objects.equals(getBusinessDBName(), that.getBusinessDBName()) &&
                Objects.equals(getPhone(), that.getPhone()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getLastName(), that.getLastName()) &&
                Objects.equals(getSubscriptionStatus(), that.getSubscriptionStatus()) &&
                Objects.equals(getOwner(), that.getOwner());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getBusinessID(), getSerialNo(), getProductTypeID(), getBusinessName(), getBusinessTypeID(), getBusinessDBName(), getPhone(), getEmail(), getFirstName(), getLastName(), getSubscriptionStatus(), getOwner());
    }

    @Override
    public String toString() {
        return "BusinessModel{" +
                "businessID=" + businessID +
                ", serialNo=" + serialNo +
                ", productTypeID=" + productTypeID +
                ", businessName='" + businessName + '\'' +
                ", businessTypeID=" + businessTypeID +
                ", businessDBName='" + businessDBName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", subscriptionStatus=" + subscriptionStatus +
                ", owner='" + owner + '\'' +
                '}';
    }
}
