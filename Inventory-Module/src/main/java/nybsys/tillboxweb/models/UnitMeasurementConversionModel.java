/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 13/02/2018
 * Time: 12:18
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class UnitMeasurementConversionModel extends BaseModel {

    private Integer unitMeasurementID;
    private Integer businessID;
    private String name;
    private Integer fromUOMID;
    private Integer toUOMID;
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
        if (!(o instanceof UnitMeasurementConversionModel)) return false;
        if (!super.equals(o)) return false;
        UnitMeasurementConversionModel that = (UnitMeasurementConversionModel) o;
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
        return "UnitMeasurementConversionModel{" +
                "unitMeasurementID=" + unitMeasurementID +
                ", businessID=" + businessID +
                ", name='" + name + '\'' +
                ", fromUOMID=" + fromUOMID +
                ", toUOMID=" + toUOMID +
                ", conversionRate=" + conversionRate +
                '}';
    }
}
