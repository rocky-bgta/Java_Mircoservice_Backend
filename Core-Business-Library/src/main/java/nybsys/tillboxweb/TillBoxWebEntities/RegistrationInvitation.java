/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 18/04/2018
 * Time: 10:46
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.TillBoxWebEntities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Entity
public class RegistrationInvitation extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "registrationInvitationID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

    private Integer registrationInvitationID;
    @Column
    @NotNull
    private String userID;
    @Column
    private String name;
    @Column
    private String surName;
    @Column
    private String cellPhone;
    @Column
    @NotNull(message = "password cannot be empty")
    @Size(min = 3, max = 50, message = "password name must be between 3 and 50 characters")
    private String password;
    @Column
    @NotNull
    private String token;
    @Column
    @NotNull
    private Integer productTypeID;
    @Column
    @NotNull
    private Integer businessTypeID;
    @Column
    @NotNull
    private Date expireDate;
    @Column
    @NotNull
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
        if (!(o instanceof RegistrationInvitation)) return false;
        if (!super.equals(o)) return false;
        RegistrationInvitation that = (RegistrationInvitation) o;
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
        return "RegistrationInvitation{" +
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