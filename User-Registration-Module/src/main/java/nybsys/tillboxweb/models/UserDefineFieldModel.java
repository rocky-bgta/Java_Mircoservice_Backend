/**
 * Created By: Md. Abdul Hannan
 * Created Date: 4/23/2018
 * Time: 5:58 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class UserDefineFieldModel extends BaseModel {

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
        if (!(o instanceof UserDefineFieldModel)) return false;
        if (!super.equals(o)) return false;
        UserDefineFieldModel that = (UserDefineFieldModel) o;
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
        return "UserDefineFieldModel{" +
                "userDefinedFieldID=" + userDefinedFieldID +
                ", businessID=" + businessID +
                ", type=" + type +
                ", labelText='" + labelText + '\'' +
                ", dataType=" + dataType +
                ", sequence=" + sequence +
                '}';
    }
}
