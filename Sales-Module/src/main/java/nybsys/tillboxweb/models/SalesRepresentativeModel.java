/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 12/03/2018
 * Time: 11:53
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class SalesRepresentativeModel extends BaseModel {
    private Integer salesRepresentativeID;
    private Integer businessID;
    private String name;
    private String designation;
    private String phone;

    public Integer getSalesRepresentativeID() {
        return salesRepresentativeID;
    }

    public void setSalesRepresentativeID(Integer salesRepresentativeID) {
        this.salesRepresentativeID = salesRepresentativeID;
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SalesRepresentativeModel)) return false;
        if (!super.equals(o)) return false;
        SalesRepresentativeModel that = (SalesRepresentativeModel) o;
        return Objects.equals(getSalesRepresentativeID(), that.getSalesRepresentativeID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDesignation(), that.getDesignation()) &&
                Objects.equals(getPhone(), that.getPhone());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSalesRepresentativeID(), getBusinessID(), getName(), getDesignation(), getPhone());
    }

    @Override
    public String toString() {
        return "SalesRepresentativeModel{" +
                "salesRepresentativeID=" + salesRepresentativeID +
                ", businessID=" + businessID +
                ", name='" + name + '\'' +
                ", designation='" + designation + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
