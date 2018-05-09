/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 03:51
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class SalesDiscount extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "salesDiscountID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

    private Integer salesDiscountID;
    @Column
    private Integer businessID;
    @Column
    private Integer salesOrderID;
    @Column
    private Integer referenceType;
    @Column
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
        if (!(o instanceof SalesDiscount)) return false;
        if (!super.equals(o)) return false;
        SalesDiscount that = (SalesDiscount) o;
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
        return "SalesDiscount{" +
                "salesDiscountID=" + salesDiscountID +
                ", businessID=" + businessID +
                ", salesOrderID=" + salesOrderID +
                ", referenceType=" + referenceType +
                ", referenceID=" + referenceID +
                '}';
    }
}