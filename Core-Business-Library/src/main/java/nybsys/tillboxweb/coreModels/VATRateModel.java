/**
 * Created By: Md. Abdul Hannan
 * Created Date: 4/16/2018
 * Time: 12:54 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.coreModels;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class VATRateModel extends BaseModel {

    Integer vATRateID;
    Integer businessID;
    String name;
    Double rate;
    Boolean isDefault;
    Boolean userDefine;

    public Integer getvATRateID() {
        return vATRateID;
    }

    public void setvATRateID(Integer vATRateID) {
        this.vATRateID = vATRateID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Boolean getUserDefine() {
        return userDefine;
    }

    public void setUserDefine(Boolean userDefine) {
        this.userDefine = userDefine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VATRateModel)) return false;
        if (!super.equals(o)) return false;
        VATRateModel that = (VATRateModel) o;
        return Objects.equals(getvATRateID(), that.getvATRateID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getRate(), that.getRate()) &&
                Objects.equals(isDefault, that.isDefault) &&
                Objects.equals(getUserDefine(), that.getUserDefine());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getvATRateID(), getBusinessID(), getName(), getRate(), isDefault, getUserDefine());
    }


    @Override
    public String toString() {
        return "VATRateModel{" +
                "vATRateID=" + vATRateID +
                ", businessID=" + businessID +
                ", name='" + name + '\'' +
                ", rate=" + rate +
                ", isDefault=" + isDefault +
                ", userDefine=" + userDefine +
                '}';
    }
}