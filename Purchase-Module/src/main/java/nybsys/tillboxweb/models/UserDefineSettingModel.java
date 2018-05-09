/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 14-Feb-18
 * Time: 12:46 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class UserDefineSettingModel extends BaseModel {

    private Integer userDefineSettingID;

    private Integer businessID;

    private Integer referenceType;
    private Integer fieldType;
    private String labelName;

    public Integer getUserDefineSettingID() {
        return userDefineSettingID;
    }

    public void setUserDefineSettingID(Integer userDefineSettingID) {
        this.userDefineSettingID = userDefineSettingID;
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

    public Integer getFieldType() {
        return fieldType;
    }

    public void setFieldType(Integer fieldType) {
        this.fieldType = fieldType;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserDefineSettingModel that = (UserDefineSettingModel) o;
        return Objects.equals(userDefineSettingID, that.userDefineSettingID) &&
                Objects.equals(businessID, that.businessID) &&
                Objects.equals(referenceType, that.referenceType) &&
                Objects.equals(fieldType, that.fieldType) &&
                Objects.equals(labelName, that.labelName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userDefineSettingID, businessID, referenceType, fieldType, labelName);
    }

    @Override
    public String toString() {
        return "UserDefineSettingModel{" +
                "userDefineSettingID=" + userDefineSettingID +
                ", businessID=" + businessID +
                ", referenceType=" + referenceType +
                ", fieldType=" + fieldType +
                ", labelName='" + labelName + '\'' +
                '}';
    }
}
