/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 01-Mar-18
 * Time: 2:35 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class AdjustmentReferenceTypeModel extends BaseModel {

    private Integer adjustmentReferenceTypeID;
    private String name;

    public Integer getAdjustmentReferenceTypeID() {
        return adjustmentReferenceTypeID;
    }

    public void setAdjustmentReferenceTypeID(Integer adjustmentReferenceTypeID) {
        this.adjustmentReferenceTypeID = adjustmentReferenceTypeID;
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
        if (!(o instanceof AdjustmentReferenceTypeModel)) return false;
        if (!super.equals(o)) return false;
        AdjustmentReferenceTypeModel that = (AdjustmentReferenceTypeModel) o;
        return Objects.equals(getAdjustmentReferenceTypeID(), that.getAdjustmentReferenceTypeID()) &&
                Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAdjustmentReferenceTypeID(), getName());
    }

    @Override
    public String toString() {
        return "AdjustmentReferenceTypeModel{" +
                "adjustmentReferenceTypeID=" + adjustmentReferenceTypeID +
                ", name='" + name + '\'' +
                '}';
    }
}
