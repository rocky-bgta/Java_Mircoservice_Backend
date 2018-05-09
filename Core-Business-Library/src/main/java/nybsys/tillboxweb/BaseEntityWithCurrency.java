/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 09-Mar-18
 * Time: 10:40 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntityWithCurrency extends BaseEntity {

    private Integer baseCurrencyID;
    private Double baseCurrencyAmount;
    private Integer entryCurrencyID;
    private Double exchangeRate;

    public Integer getBaseCurrencyID() {
        return baseCurrencyID;
    }

    public void setBaseCurrencyID(Integer baseCurrencyID) {
        this.baseCurrencyID = baseCurrencyID;
    }

    public Double getBaseCurrencyAmount() {
        return baseCurrencyAmount;
    }

    public void setBaseCurrencyAmount(Double baseCurrencyAmount) {
        this.baseCurrencyAmount = baseCurrencyAmount;
    }

    public Integer getEntryCurrencyID() {
        return entryCurrencyID;
    }

    public void setEntryCurrencyID(Integer entryCurrencyID) {
        this.entryCurrencyID = entryCurrencyID;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}