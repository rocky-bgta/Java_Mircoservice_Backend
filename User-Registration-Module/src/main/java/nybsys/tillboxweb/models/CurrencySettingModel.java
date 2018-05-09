/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 02/05/2018
 * Time: 03:42
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class CurrencySettingModel extends BaseModel{
    private Integer currencySettingID;
    private Integer businessID;
    private Boolean useLiveExchangeRate;
    private Integer bankChargesLedgerAccountID;
    private Integer exchangeRateGainLedgerAccountID;
    private Integer exchangeRateLossLedgerAccountID;

    public Integer getCurrencySettingID() {
        return currencySettingID;
    }

    public void setCurrencySettingID(Integer currencySettingID) {
        this.currencySettingID = currencySettingID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Boolean getUseLiveExchangeRate() {
        return useLiveExchangeRate;
    }

    public void setUseLiveExchangeRate(Boolean useLiveExchangeRate) {
        this.useLiveExchangeRate = useLiveExchangeRate;
    }

    public Integer getBankChargesLedgerAccountID() {
        return bankChargesLedgerAccountID;
    }

    public void setBankChargesLedgerAccountID(Integer bankChargesLedgerAccountID) {
        this.bankChargesLedgerAccountID = bankChargesLedgerAccountID;
    }

    public Integer getExchangeRateGainLedgerAccountID() {
        return exchangeRateGainLedgerAccountID;
    }

    public void setExchangeRateGainLedgerAccountID(Integer exchangeRateGainLedgerAccountID) {
        this.exchangeRateGainLedgerAccountID = exchangeRateGainLedgerAccountID;
    }

    public Integer getExchangeRateLossLedgerAccountID() {
        return exchangeRateLossLedgerAccountID;
    }

    public void setExchangeRateLossLedgerAccountID(Integer exchangeRateLossLedgerAccountID) {
        this.exchangeRateLossLedgerAccountID = exchangeRateLossLedgerAccountID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CurrencySettingModel)) return false;
        CurrencySettingModel that = (CurrencySettingModel) o;
        return Objects.equals(getCurrencySettingID(), that.getCurrencySettingID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getUseLiveExchangeRate(), that.getUseLiveExchangeRate()) &&
                Objects.equals(getBankChargesLedgerAccountID(), that.getBankChargesLedgerAccountID()) &&
                Objects.equals(getExchangeRateGainLedgerAccountID(), that.getExchangeRateGainLedgerAccountID()) &&
                Objects.equals(getExchangeRateLossLedgerAccountID(), that.getExchangeRateLossLedgerAccountID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCurrencySettingID(), getBusinessID(), getUseLiveExchangeRate(), getBankChargesLedgerAccountID(), getExchangeRateGainLedgerAccountID(), getExchangeRateLossLedgerAccountID());
    }

    @Override
    public String toString() {
        return "CurrencySettingModel{" +
                "currencySettingID=" + currencySettingID +
                ", businessID=" + businessID +
                ", useLiveExchangeRate=" + useLiveExchangeRate +
                ", bankChargesLedgerAccountID=" + bankChargesLedgerAccountID +
                ", exchangeRateGainLedgerAccountID=" + exchangeRateGainLedgerAccountID +
                ", exchangeRateLossLedgerAccountID=" + exchangeRateLossLedgerAccountID +
                '}';
    }
}
