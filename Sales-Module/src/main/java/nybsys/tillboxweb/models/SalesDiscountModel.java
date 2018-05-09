/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 03:45
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class SalesDiscountModel extends BaseModel {
    private Integer salesDiscountID;
    private Integer businessID;
    private Integer salesOrderID;
    private Integer referenceType;
    private Integer referenceID;

    public Integer getSalesDiscountID() {
        return salesDiscountID;
    }

    public void setSalesDiscountID(Integer salesDiscountID) {
        this.salesDiscountID = salesDiscountID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getSalesOrderID() {
        return salesOrderID;
    }

    public void setSalesOrderID(Integer salesOrderID) {
        this.salesOrderID = salesOrderID;
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
        if (!(o instanceof SalesDiscountModel)) return false;
        if (!super.equals(o)) return false;
        SalesDiscountModel that = (SalesDiscountModel) o;
        return Objects.equals(getSalesDiscountID(), that.getSalesDiscountID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getSalesOrderID(), that.getSalesOrderID()) &&
                Objects.equals(getReferenceType(), that.getReferenceType()) &&
                Objects.equals(getReferenceID(), that.getReferenceID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSalesDiscountID(), getBusinessID(), getSalesOrderID(), getReferenceType(), getReferenceID());
    }

    @Override
    public String toString() {
        return "SalesDiscountModel{" +
                "salesDiscountID=" + salesDiscountID +
                ", businessID=" + businessID +
                ", salesOrderID=" + salesOrderID +
                ", referenceType=" + referenceType +
                ", referenceID=" + referenceID +
                '}';
    }
}
