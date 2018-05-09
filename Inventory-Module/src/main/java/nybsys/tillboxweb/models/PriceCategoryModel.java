/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 14/02/2018
 * Time: 12:25
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class PriceCategoryModel extends BaseModel{

    private Integer priceCategoryID;
    private Integer businessID;
    private String name;
    private Boolean isDefault;

    public Integer getPriceCategoryID() {
        return priceCategoryID;
    }

    public void setPriceCategoryID(Integer priceCategoryID) {
        this.priceCategoryID = priceCategoryID;
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

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PriceCategoryModel)) return false;
        if (!super.equals(o)) return false;
        PriceCategoryModel that = (PriceCategoryModel) o;
        return Objects.equals(getPriceCategoryID(), that.getPriceCategoryID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(isDefault, that.isDefault);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPriceCategoryID(), getBusinessID(), getName(), isDefault);
    }

    @Override
    public String toString() {
        return "PriceCategoryModel{" +
                "priceCategoryID=" + priceCategoryID +
                ", businessID=" + businessID +
                ", name='" + name + '\'' +
                ", isDefault=" + isDefault +
                '}';
    }
}
