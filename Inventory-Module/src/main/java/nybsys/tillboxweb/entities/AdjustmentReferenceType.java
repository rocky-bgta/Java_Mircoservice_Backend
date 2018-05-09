/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 01-Mar-18
 * Time: 2:33 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class AdjustmentReferenceType extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "adjustmentReferenceTypeID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

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
        if (!(o instanceof AdjustmentReferenceType)) return false;
        if (!super.equals(o)) return false;
        AdjustmentReferenceType that = (AdjustmentReferenceType) o;
        return Objects.equals(getAdjustmentReferenceTypeID(), that.getAdjustmentReferenceTypeID()) &&
                Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAdjustmentReferenceTypeID(), getName());
    }

    @Override
    public String toString() {
        return "AdjustmentReferenceType{" +
                "adjustmentReferenceTypeID=" + adjustmentReferenceTypeID +
                ", name='" + name + '\'' +
                '}';
    }
}