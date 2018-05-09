/**
 * Created By: Md. Abdul Hannan
 * Created Date: 4/23/2018
 * Time: 6:01 PM
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
public class UserDefineField extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "userDefinedFieldID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    private Integer userDefinedFieldID;
    private Integer businessID;
    private Integer type;
    private String labelText;
    private Integer dataType;
    private Integer sequence;
    private Boolean inActive;

    public Boolean getInActive() {
        return inActive;
    }

    public void setInActive(Boolean inActive) {
        this.inActive = inActive;
    }

    public Integer getUserDefinedFieldID() {
        return userDefinedFieldID;
    }

    public void setUserDefinedFieldID(Integer userDefinedFieldID) {
        this.userDefinedFieldID = userDefinedFieldID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLabelText() {
        return labelText;
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDefineField)) return false;
        if (!super.equals(o)) return false;
        UserDefineField that = (UserDefineField) o;
        return Objects.equals(getUserDefinedFieldID(), that.getUserDefinedFieldID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getType(), that.getType()) &&
                Objects.equals(getLabelText(), that.getLabelText()) &&
                Objects.equals(getDataType(), that.getDataType()) &&
                Objects.equals(getSequence(), that.getSequence());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUserDefinedFieldID(), getBusinessID(), getType(), getLabelText(), getDataType(), getSequence());
    }

    @Override
    public String toString() {
        return "UserDefineField{" +
                "userDefinedFieldID=" + userDefinedFieldID +
                ", businessID=" + businessID +
                ", type=" + type +
                ", labelText='" + labelText + '\'' +
                ", dataType=" + dataType +
                ", sequence=" + sequence +
                '}';
    }
}
