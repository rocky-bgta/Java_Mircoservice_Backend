/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 23-Feb-18
 * Time: 11:18 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class CommonMessage extends BaseEntity {

    @Id
    @GeneratedValue(generator="IdGen")
    @GenericGenerator(name="IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name",value = "commonMessageID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer",value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    private Integer commonMessageID;

    private Integer businessID;
    private String description;
    private String message;
    private Integer referenceType;
    private Boolean isDefault;


    public Integer getCommonMessageID() {
        return commonMessageID;
    }

    public void setCommonMessageID(Integer commonMessageID) {
        this.commonMessageID = commonMessageID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(Integer referenceType) {
        this.referenceType = referenceType;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommonMessage)) return false;
        if (!super.equals(o)) return false;
        CommonMessage that = (CommonMessage) o;
        return Objects.equals(getCommonMessageID(), that.getCommonMessageID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getMessage(), that.getMessage()) &&
                Objects.equals(getReferenceType(), that.getReferenceType()) &&
                Objects.equals(isDefault, that.isDefault);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCommonMessageID(), getBusinessID(), getDescription(), getMessage(), getReferenceType(), isDefault);
    }

    @Override
    public String toString() {
        return "CommonMessage{" +
                "commonMessageID=" + commonMessageID +
                ", businessID=" + businessID +
                ", description='" + description + '\'' +
                ", message='" + message + '\'' +
                ", referenceType=" + referenceType +
                ", isDefault=" + isDefault +
                '}';
    }
}
