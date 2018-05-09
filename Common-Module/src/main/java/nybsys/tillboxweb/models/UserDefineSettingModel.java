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
    private Integer referenceID;

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

    public Integer getReferenceID() {
        return referenceID;
    }

    public void setReferenceID(Integer referenceID) {
        this.referenceID = referenceID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDefineSettingModel)) return false;
        if (!super.equals(o)) return false;
        UserDefineSettingModel that = (UserDefineSettingModel) o;
        return Objects.equals(getUserDefineSettingID(), that.getUserDefineSettingID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getReferenceType(), that.getReferenceType()) &&
                Objects.equals(getReferenceID(), that.getReferenceID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUserDefineSettingID(), getBusinessID(), getReferenceType(), getReferenceID());
    }

    @Override
    public String toString() {
        return "UserDefineSettingModel{" +
                "userDefineSettingID=" + userDefineSettingID +
                ", businessID=" + businessID +
                ", referenceType=" + referenceType +
                ", referenceID=" + referenceID +
                '}';
    }
}
