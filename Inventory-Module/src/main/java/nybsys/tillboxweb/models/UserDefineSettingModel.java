/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/16/2018
 * Time: 12:51 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class UserDefineSettingModel extends BaseModel {

    private Integer userDefinedSettingsID;
    private Integer businessID;
    private Integer referenceType;
    private Integer referenceID;
    private String fieldName;
    private String fieldValue;
    private Integer fieldType;

    public Integer getUserDefinedSettingsID() {
        return userDefinedSettingsID;
    }

    public void setUserDefinedSettingsID(Integer userDefinedSettingsID) {
        this.userDefinedSettingsID = userDefinedSettingsID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(Integer referenceType) {
        this.referenceType = referenceType;
    }

    public Integer getReferenceID() {
        return referenceID;
    }

    public void setReferenceID(Integer referenceID) {
        this.referenceID = referenceID;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public Integer getFieldType() {
        return fieldType;
    }

    public void setFieldType(Integer fieldType) {
        this.fieldType = fieldType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDefineSettingModel)) return false;
        if (!super.equals(o)) return false;
        UserDefineSettingModel that = (UserDefineSettingModel) o;
        return Objects.equals(getUserDefinedSettingsID(), that.getUserDefinedSettingsID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getReferenceType(), that.getReferenceType()) &&
                Objects.equals(getReferenceID(), that.getReferenceID()) &&
                Objects.equals(getFieldName(), that.getFieldName()) &&
                Objects.equals(getFieldValue(), that.getFieldValue()) &&
                Objects.equals(getFieldType(), that.getFieldType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUserDefinedSettingsID(), getBusinessID(), getReferenceType(), getReferenceID(), getFieldName(), getFieldValue(), getFieldType());
    }

    @Override
    public String toString() {
        return "UserDefineSettingModel{" +
                "userDefinedSettingsID=" + userDefinedSettingsID +
                ", businessID=" + businessID +
                ", referenceType=" + referenceType +
                ", referenceID=" + referenceID +
                ", fieldName='" + fieldName + '\'' +
                ", fieldValue='" + fieldValue + '\'' +
                ", fieldType=" + fieldType +
                '}';
    }
}
