/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 16-Apr-18
 * Time: 5:49 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class RegionalSettingModel extends BaseModel {

    private Integer regionalSettingID;
    private Integer businessID;
    private Double quantityDecimalPlaces;
    private Double valueDecimalPlaces;
    private String currencySymbol;
    private String dateFormat;

    public Integer getRegionalSettingID() {
        return regionalSettingID;
    }

    public void setRegionalSettingID(Integer regionalSettingID) {
        this.regionalSettingID = regionalSettingID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Double getQuantityDecimalPlaces() {
        return quantityDecimalPlaces;
    }

    public void setQuantityDecimalPlaces(Double quantityDecimalPlaces) {
        this.quantityDecimalPlaces = quantityDecimalPlaces;
    }

    public Double getValueDecimalPlaces() {
        return valueDecimalPlaces;
    }

    public void setValueDecimalPlaces(Double valueDecimalPlaces) {
        this.valueDecimalPlaces = valueDecimalPlaces;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegionalSettingModel)) return false;
        if (!super.equals(o)) return false;
        RegionalSettingModel that = (RegionalSettingModel) o;
        return Objects.equals(getRegionalSettingID(), that.getRegionalSettingID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getQuantityDecimalPlaces(), that.getQuantityDecimalPlaces()) &&
                Objects.equals(getValueDecimalPlaces(), that.getValueDecimalPlaces()) &&
                Objects.equals(getCurrencySymbol(), that.getCurrencySymbol()) &&
                Objects.equals(getDateFormat(), that.getDateFormat());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getRegionalSettingID(), getBusinessID(), getQuantityDecimalPlaces(), getValueDecimalPlaces(), getCurrencySymbol(), getDateFormat());
    }

    @Override
    public String toString() {
        return "RegionalSettingModel{" +
                "regionalSettingID=" + regionalSettingID +
                ", businessID=" + businessID +
                ", quantityDecimalPlaces=" + quantityDecimalPlaces +
                ", valueDecimalPlaces=" + valueDecimalPlaces +
                ", currencySymbol='" + currencySymbol + '\'' +
                ", dateFormat='" + dateFormat + '\'' +
                '}';
    }
}
