/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 13/02/2018
 * Time: 04:00
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class UnitMeasurementConversion extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "unitMeasurementID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    @NotNull
    @Column
    private Integer unitMeasurementID;
    @Column
    private Integer businessID;
    @Column
    private String name;
    @Column
    private Integer fromUOMID;
    @Column
    private Integer toUOMID;
    @Column
    private Double conversionRate;

    public Integer getUnitMeasurementID() {
        return unitMeasurementID;
    }

    public void setUnitMeasurementID(Integer unitMeasurementID) {
        this.unitMeasurementID = unitMeasurementID;
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

    public Integer getFromUOMID() {
        return fromUOMID;
    }

    public void setFromUOMID(Integer fromUOMID) {
        this.fromUOMID = fromUOMID;
    }

    public Integer getToUOMID() {
        return toUOMID;
    }

    public void setToUOMID(Integer toUOMID) {
        this.toUOMID = toUOMID;
    }

    public Double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(Double conversionRate) {
        this.conversionRate = conversionRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UnitMeasurementConversion)) return false;
        if (!super.equals(o)) return false;
        UnitMeasurementConversion that = (UnitMeasurementConversion) o;
        return Objects.equals(getUnitMeasurementID(), that.getUnitMeasurementID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getFromUOMID(), that.getFromUOMID()) &&
                Objects.equals(getToUOMID(), that.getToUOMID()) &&
                Objects.equals(getConversionRate(), that.getConversionRate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUnitMeasurementID(), getBusinessID(), getName(), getFromUOMID(), getToUOMID(), getConversionRate());
    }

    @Override
    public String toString() {
        return "UnitMeasurementConversion{" +
                "unitMeasurementID=" + unitMeasurementID +
                ", businessID=" + businessID +
                ", name='" + name + '\'' +
                ", fromUOMID=" + fromUOMID +
                ", toUOMID=" + toUOMID +
                ", conversionRate=" + conversionRate +
                '}';
    }
}
