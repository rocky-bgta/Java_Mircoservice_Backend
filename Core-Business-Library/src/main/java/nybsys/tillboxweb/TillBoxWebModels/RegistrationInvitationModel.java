/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 18/04/2018
 * Time: 10:41
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.TillBoxWebModels;

import nybsys.tillboxweb.BaseModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

public class RegistrationInvitationModel extends BaseModel{

    private Integer registrationInvitationID;
    private String userID;
    private String name;
    private String surName;
    private String cellPhone;
    @NotNull(message = "password cannot be empty")
    @Size(min = 3, max = 50, message = "password name must be between 3 and 50 characters")
    private String password;
    private String token;
    private Integer productTypeID;
    private Integer businessTypeID;
    private Date expireDate;
    private Boolean done;

    public Integer getRegistrationInvitationID() {
        return registrationInvitationID;
    }

    public void setRegistrationInvitationID(Integer registrationInvitationID) {
        this.registrationInvitationID = registrationInvitationID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getProductTypeID() {
        return productTypeID;
    }

    public void setProductTypeID(Integer productTypeID) {
        this.productTypeID = productTypeID;
    }

    public Integer getBusinessTypeID() {
        return businessTypeID;
    }

    public void setBusinessTypeID(Integer businessTypeID) {
        this.businessTypeID = businessTypeID;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegistrationInvitationModel)) return false;
        if (!super.equals(o)) return false;
        RegistrationInvitationModel that = (RegistrationInvitationModel) o;
        return Objects.equals(getRegistrationInvitationID(), that.getRegistrationInvitationID()) &&
                Objects.equals(getUserID(), that.getUserID()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getSurName(), that.getSurName()) &&
                Objects.equals(getCellPhone(), that.getCellPhone()) &&
                Objects.equals(getPassword(), that.getPassword()) &&
                Objects.equals(getToken(), that.getToken()) &&
                Objects.equals(getProductTypeID(), that.getProductTypeID()) &&
                Objects.equals(getBusinessTypeID(), that.getBusinessTypeID()) &&
                Objects.equals(getExpireDate(), that.getExpireDate()) &&
                Objects.equals(getDone(), that.getDone());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getRegistrationInvitationID(), getUserID(), getName(), getSurName(), getCellPhone(), getPassword(), getToken(), getProductTypeID(), getBusinessTypeID(), getExpireDate(), getDone());
    }

    @Override
    public String toString() {
        return "RegistrationInvitationModel{" +
                "registrationInvitationID=" + registrationInvitationID +
                ", userID='" + userID + '\'' +
                ", name='" + name + '\'' +
                ", surName='" + surName + '\'' +
                ", cellPhone='" + cellPhone + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", productTypeID=" + productTypeID +
                ", businessTypeID=" + businessTypeID +
                ", expireDate=" + expireDate +
                ", done=" + done +
                '}';
    }
}
