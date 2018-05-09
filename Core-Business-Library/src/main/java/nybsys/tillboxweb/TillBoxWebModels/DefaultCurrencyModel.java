/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/9/2018
 * Time: 9:45 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.TillBoxWebModels;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class DefaultCurrencyModel extends BaseModel {

    private Integer currencyID;
    private Integer businessID;
    private String currencyName;
    private String currencyCode;
    private Boolean isBaseCurrency;
    private Double exchangeRate;


    public Integer getCurrencyID() {
        return currencyID;
    }

    public void setCurrencyID(Integer currencyID) {
        this.currencyID = currencyID;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Boolean getBaseCurrency() {
        return isBaseCurrency;
    }

    public void setBaseCurrency(Boolean baseCurrency) {
        isBaseCurrency = baseCurrency;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultCurrencyModel)) return false;
        if (!super.equals(o)) return false;
        DefaultCurrencyModel that = (DefaultCurrencyModel) o;
        return Objects.equals(getCurrencyID(), that.getCurrencyID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getCurrencyName(), that.getCurrencyName()) &&
                Objects.equals(getCurrencyCode(), that.getCurrencyCode()) &&
                Objects.equals(isBaseCurrency, that.isBaseCurrency) &&
                Objects.equals(getExchangeRate(), that.getExchangeRate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCurrencyID(), getBusinessID(), getCurrencyName(), getCurrencyCode(), isBaseCurrency, getExchangeRate());
    }

    @Override
    public String toString() {
        return "Currency{" +
                "currencyID=" + currencyID +
                ", currencyName='" + currencyName + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                ", isBaseCurrency=" + isBaseCurrency +
                ", exchangeRate=" + exchangeRate +
                ", businessID=" + businessID +
                '}';
    }
}
