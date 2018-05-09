/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 22/02/2018
 * Time: 02:25
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class AddressTypeModel extends BaseModel {
    private Integer addressTypeID;
    private String name;
    private Integer referenceType;

    public Integer getAddressTypeID() {
        return addressTypeID;
    }

    public void setAddressTypeID(Integer addressTypeID) {
        this.addressTypeID = addressTypeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(Integer referenceType) {
        this.referenceType = referenceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddressTypeModel)) return false;
        if (!super.equals(o)) return false;
        AddressTypeModel that = (AddressTypeModel) o;
        return Objects.equals(getAddressTypeID(), that.getAddressTypeID()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getReferenceType(), that.getReferenceType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAddressTypeID(), getName(), getReferenceType());
    }

    @Override
    public String toString() {
        return "AddressTypeModel{" +
                "addressTypeID=" + addressTypeID +
                ", name='" + name + '\'' +
                ", referenceType=" + referenceType +
                '}';
    }
}
