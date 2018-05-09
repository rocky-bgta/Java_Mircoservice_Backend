/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 19-Apr-18
 * Time: 12:37 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class RoundingModel extends BaseModel {

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
        if (!(o instanceof RoundingModel)) return false;
        if (!super.equals(o)) return false;
        RoundingModel that = (RoundingModel) o;
        return Objects.equals(getRoundingID(), that.getRoundingID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getRoundingType(), that.getRoundingType()) &&
                Objects.equals(getRoundingValue(), that.getRoundingValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getRoundingID(), getBusinessID(), getRoundingType(), getRoundingValue());
    }

    @Override
    public String toString() {
        return "RoundingModel{" +
                "roundingID=" + roundingID +
                ", businessID=" + businessID +
                ", roundingType=" + roundingType +
                ", roundingValue=" + roundingValue +
                '}';
    }
}
