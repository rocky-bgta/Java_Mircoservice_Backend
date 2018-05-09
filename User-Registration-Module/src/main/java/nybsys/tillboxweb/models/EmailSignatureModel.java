/**
 * Created By: Md. Abdul Hannan
 * Created Date: 5/2/2018
 * Time: 4:40 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class EmailSignatureModel extends BaseModel {
    private Integer emailSignatureID;
    private Integer typeID;
    private Integer businessID;
    private String name;
    private String subject;
    private String body;
    private Boolean isDefault;

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getEmailSignatureID() {
        return emailSignatureID;
    }

    public void setEmailSignatureID(Integer emailSignatureID) {
        this.emailSignatureID = emailSignatureID;
    }

    public Integer getTypeID() {
        return typeID;
    }

    public void setTypeID(Integer typeID) {
        this.typeID = typeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean isDefault) {
       this.isDefault = isDefault;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmailSignatureModel)) return false;
        if (!super.equals(o)) return false;
        EmailSignatureModel that = (EmailSignatureModel) o;
        return Objects.equals(getEmailSignatureID(), that.getEmailSignatureID()) &&
                Objects.equals(getTypeID(), that.getTypeID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getSubject(), that.getSubject()) &&
                Objects.equals(getBody(), that.getBody()) &&
                Objects.equals(isDefault, that.isDefault);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getEmailSignatureID(), getTypeID(), getBusinessID(), getName(), getSubject(), getBody(), isDefault);
    }

    @Override
    public String toString() {
        return "EmailSignatureModel{" +
                "emailSignatureID=" + emailSignatureID +
                ", typeID=" + typeID +
                ", businessID=" + businessID +
                ", name='" + name + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", isDefault=" + isDefault +
                '}';
    }
}
