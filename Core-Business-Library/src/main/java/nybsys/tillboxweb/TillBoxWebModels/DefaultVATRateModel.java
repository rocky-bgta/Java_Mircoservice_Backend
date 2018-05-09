/**
 * Created By: Md. Abdul Hannan
 * Created Date: 4/16/2018
 * Time: 12:54 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.TillBoxWebModels;

import nybsys.tillboxweb.BaseModel;

public class DefaultVATRateModel extends BaseModel {

    Integer vATRateID;
    Integer businessID;
    String name;
    Double rate;
    Boolean isDefault;

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
}