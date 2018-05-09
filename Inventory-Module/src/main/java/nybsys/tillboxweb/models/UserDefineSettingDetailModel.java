/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/16/2018
 * Time: 12:33 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class UserDefineSettingDetailModel extends BaseModel {

    private Integer userDefinedSettingDetailsID;
    private Integer businessID;
    private Integer userDefinedSettingsID;
    private Integer referenceType;
    private String value;

    public Integer getUserDefinedSettingDetailsID() {
        return userDefinedSettingDetailsID;
    }

    public void setUserDefinedSettingDetailsID(Integer userDefinedSettingDetailsID) {
        this.userDefinedSettingDetailsID = userDefinedSettingDetailsID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getUserDefinedSettingsID() {
        return userDefinedSettingsID;
    }

    public void setUserDefinedSettingsID(Integer userDefinedSettingsID) {
        this.userDefinedSettingsID = userDefinedSettingsID;
    }

    public Integer getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(Integer referenceType) {
        this.referenceType = referenceType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDefineSettingDetailModel)) return false;
        if (!super.equals(o)) return false;
        UserDefineSettingDetailModel that = (UserDefineSettingDetailModel) o;
        return Objects.equals(getUserDefinedSettingDetailsID(), that.getUserDefinedSettingDetailsID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getUserDefinedSettingsID(), that.getUserDefinedSettingsID()) &&
                Objects.equals(getReferenceType(), that.getReferenceType()) &&
                Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUserDefinedSettingDetailsID(), getBusinessID(), getUserDefinedSettingsID(), getReferenceType(), getValue());
    }

    @Override
    public String toString() {
        return "UserDefineSettingDetailModel{" +
                "userDefinedSettingDetailsID=" + userDefinedSettingDetailsID +
                ", businessID=" + businessID +
                ", userDefinedSettingsID=" + userDefinedSettingsID +
                ", referenceType=" + referenceType +
                ", value='" + value + '\'' +
                '}';
    }
}
