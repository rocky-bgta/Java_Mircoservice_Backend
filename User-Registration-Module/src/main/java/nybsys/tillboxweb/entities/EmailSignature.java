/**
 * Created By: Md. Abdul Hannan
 * Created Date: 5/2/2018
 * Time: 4:56 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class EmailSignature extends BaseEntity {
    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "emailSignatureID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

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
        if (!(o instanceof EmailSignature)) return false;
        if (!super.equals(o)) return false;
        EmailSignature that = (EmailSignature) o;
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
        return "EmailSignature{" +
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
