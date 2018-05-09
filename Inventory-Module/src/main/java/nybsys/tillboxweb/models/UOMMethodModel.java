/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 13/02/2018
 * Time: 11:35
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class UOMMethodModel extends BaseModel {

    private Integer uOMMethodID;
    private Integer businessID;
    private String name;

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
        if (!(o instanceof UOMMethodModel)) return false;
        if (!super.equals(o)) return false;
        UOMMethodModel that = (UOMMethodModel) o;
        return Objects.equals(getuOMMethodID(), that.getuOMMethodID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getuOMMethodID(), getBusinessID(), getName());
    }

    @Override
    public String toString() {
        return "UOMMethodModel{" +
                "uOMMethodID=" + uOMMethodID +
                ", businessID=" + businessID +
                ", name='" + name + '\'' +
                '}';
    }
}
