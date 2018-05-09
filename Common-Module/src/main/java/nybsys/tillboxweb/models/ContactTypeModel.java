/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 22/02/2018
 * Time: 02:28
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class ContactTypeModel extends BaseModel {
   private Integer contactTypeID;
   private String name;
   private Integer referenceType;

    public Integer getContactTypeID() {
        return contactTypeID;
    }

    public void setContactTypeID(Integer contactTypeID) {
        this.contactTypeID = contactTypeID;
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
        if (!(o instanceof ContactTypeModel)) return false;
        if (!super.equals(o)) return false;
        ContactTypeModel that = (ContactTypeModel) o;
        return Objects.equals(getContactTypeID(), that.getContactTypeID()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getReferenceType(), that.getReferenceType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getContactTypeID(), getName(), getReferenceType());
    }

    @Override
    public String toString() {
        return "ContactTypeModel{" +
                "contactTypeID=" + contactTypeID +
                ", name='" + name + '\'' +
                ", referenceType=" + referenceType +
                '}';
    }
}
