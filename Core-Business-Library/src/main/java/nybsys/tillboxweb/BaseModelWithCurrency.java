/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 09-Mar-18
 * Time: 10:38 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb;

public abstract class BaseModelWithCurrency extends BaseModel {

    private Integer baseCurrencyID;
    private Integer entryCurrencyID;
    private Double exchangeRate;
    private Double baseCurrencyAmount;

    public Integer getBaseCurrencyID() {
        return baseCurrencyID;
    }

    public void setBaseCurrencyID(Integer baseCurrencyID) {
        this.baseCurrencyID = baseCurrencyID;
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

    public Double getBaseCurrencyAmount() {
        return baseCurrencyAmount;
    }

    public void setBaseCurrencyAmount(Double baseCurrencyAmount) {
        this.baseCurrencyAmount = baseCurrencyAmount;
    }
}
