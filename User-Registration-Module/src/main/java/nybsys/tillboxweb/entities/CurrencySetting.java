/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 02/05/2018
 * Time: 03:49
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
public class CurrencySetting extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "currencySettingID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

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
        if (!(o instanceof CurrencySetting)) return false;
        if (!super.equals(o)) return false;
        CurrencySetting that = (CurrencySetting) o;
        return Objects.equals(getCurrencySettingID(), that.getCurrencySettingID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getUseLiveExchangeRate(), that.getUseLiveExchangeRate()) &&
                Objects.equals(getBankChargesLedgerAccountID(), that.getBankChargesLedgerAccountID()) &&
                Objects.equals(getExchangeRateGainLedgerAccountID(), that.getExchangeRateGainLedgerAccountID()) &&
                Objects.equals(getExchangeRateLossLedgerAccountID(), that.getExchangeRateLossLedgerAccountID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCurrencySettingID(), getBusinessID(), getUseLiveExchangeRate(), getBankChargesLedgerAccountID(), getExchangeRateGainLedgerAccountID(), getExchangeRateLossLedgerAccountID());
    }

    @Override
    public String toString() {
        return "CurrencySetting{" +
                "currencySettingID=" + currencySettingID +
                ", businessID=" + businessID +
                ", useLiveExchangeRate=" + useLiveExchangeRate +
                ", bankChargesLedgerAccountID=" + bankChargesLedgerAccountID +
                ", exchangeRateGainLedgerAccountID=" + exchangeRateGainLedgerAccountID +
                ", exchangeRateLossLedgerAccountID=" + exchangeRateLossLedgerAccountID +
                '}';
    }
}