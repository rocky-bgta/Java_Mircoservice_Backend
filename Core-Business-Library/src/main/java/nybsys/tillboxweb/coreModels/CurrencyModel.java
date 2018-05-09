/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/9/2018
 * Time: 9:45 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.coreModels;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class CurrencyModel extends BaseModel {

    private Integer currencyID;
    private Integer businessID;
    private String currencyName;
    private String currencyCode;
    private Boolean baseCurrency;
    private Boolean used;
    private Double exchangeRate;
    private String symbol;

    public Integer getCurrencyID() {
        return currencyID;
    }

    public void setCurrencyID(Integer currencyID) {
        this.currencyID = currencyID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
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
        return baseCurrency;
    }

    public void setBaseCurrency(Boolean baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}
