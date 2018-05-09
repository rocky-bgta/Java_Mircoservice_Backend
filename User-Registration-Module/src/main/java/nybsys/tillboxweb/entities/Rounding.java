/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 19-Apr-18
 * Time: 12:31 PM
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
public class Rounding extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "roundingID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

    private Integer roundingID;
    private Integer businessID;
    private Integer roundingType;
    private Double roundingValue;

    public Integer getRoundingID() {
        return roundingID;
    }

    public void setRoundingID(Integer roundingID) {
        this.roundingID = roundingID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getRoundingType() {
        return roundingType;
    }

    public void setRoundingType(Integer roundingType) {
        this.roundingType = roundingType;
    }

    public Double getRoundingValue() {
        return roundingValue;
    }

    public void setRoundingValue(Double roundingValue) {
        this.roundingValue = roundingValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rounding)) return false;
        if (!super.equals(o)) return false;
        Rounding rounding = (Rounding) o;
        return Objects.equals(getRoundingID(), rounding.getRoundingID()) &&
                Objects.equals(getBusinessID(), rounding.getBusinessID()) &&
                Objects.equals(getRoundingType(), rounding.getRoundingType()) &&
                Objects.equals(getRoundingValue(), rounding.getRoundingValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getRoundingID(), getBusinessID(), getRoundingType(), getRoundingValue());
    }

    @Override
    public String toString() {
        return "Rounding{" +
                "roundingID=" + roundingID +
                ", businessID=" + businessID +
                ", roundingType=" + roundingType +
                ", roundingValue=" + roundingValue +
                '}';
    }
}