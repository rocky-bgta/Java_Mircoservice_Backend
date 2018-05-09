/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/9/2018
 * Time: 11:05 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.TillBoxWebModels;

import nybsys.tillboxweb.BaseModel;

import java.util.Date;
import java.util.Objects;

public class DefaultCurrencyExchangeRateModel extends BaseModel {

    private Integer currencyExchangeRateID;
    private Date date;
    private Integer currencyID;
    private Double rate;

    public Integer getCurrencyExchangeRateID() {
        return currencyExchangeRateID;
    }

    public void setCurrencyExchangeRateID(Integer currencyExchangeRateID) {
        this.currencyExchangeRateID = currencyExchangeRateID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getCurrencyID() {
        return currencyID;
    }

    public void setCurrencyID(Integer currencyID) {
        this.currencyID = currencyID;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultCurrencyExchangeRateModel)) return false;
        if (!super.equals(o)) return false;
        DefaultCurrencyExchangeRateModel that = (DefaultCurrencyExchangeRateModel) o;
        return Objects.equals(getCurrencyExchangeRateID(), that.getCurrencyExchangeRateID()) &&
                Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getCurrencyID(), that.getCurrencyID()) &&
                Objects.equals(getRate(), that.getRate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCurrencyExchangeRateID(), getDate(), getCurrencyID(), getRate());
    }

    @Override
    public String toString() {
        return "CurrencyExchangeRateModel{" +
                "currencyExchangeRateID=" + currencyExchangeRateID +
                ", date=" + date +
                ", currencyID=" + currencyID +
                ", rate=" + rate +
                '}';
    }
}
