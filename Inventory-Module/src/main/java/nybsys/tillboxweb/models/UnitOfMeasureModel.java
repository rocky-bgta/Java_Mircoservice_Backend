/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 13/02/2018
 * Time: 12:16
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class UnitOfMeasureModel extends BaseModel {

    private Integer unitOfMeasureID;
    private Integer uOMMethodID;
    private Integer businessID;
    private String name;

    public Integer getUnitOfMeasureID() {
        return unitOfMeasureID;
    }

    public void setUnitOfMeasureID(Integer unitOfMeasureID) {
        this.unitOfMeasureID = unitOfMeasureID;
    }

    public Integer getuOMMethodID() {
        return uOMMethodID;
    }

    public void setuOMMethodID(Integer uOMMethodID) {
        this.uOMMethodID = uOMMethodID;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UnitOfMeasureModel)) return false;
        if (!super.equals(o)) return false;
        UnitOfMeasureModel that = (UnitOfMeasureModel) o;
        return Objects.equals(getUnitOfMeasureID(), that.getUnitOfMeasureID()) &&
                Objects.equals(getuOMMethodID(), that.getuOMMethodID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUnitOfMeasureID(), getuOMMethodID(), getBusinessID(), getName());
    }

    @Override
    public String toString() {
        return "UnitOfMeasureModel{" +
                "unitOfMeasureID=" + unitOfMeasureID +
                ", uOMMethodID=" + uOMMethodID +
                ", businessID=" + businessID +
                ", name='" + name + '\'' +
                '}';
    }
}
