/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/9/2018
 * Time: 9:46 AM
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
import java.util.Objects;

@Entity
public class Currency extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "currencyID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    private Integer currencyID;
    private Integer businessID;
    private String currencyName;
    private String currencyCode;
    private Boolean isBaseCurrency;
    private String symbol;
    private Double exchangeRate;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

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
        if (!(o instanceof Currency)) return false;
        if (!super.equals(o)) return false;
        Currency currency = (Currency) o;
        return Objects.equals(getCurrencyID(), currency.getCurrencyID()) &&
                Objects.equals(getCurrencyName(), currency.getCurrencyName()) &&
                Objects.equals(getCurrencyCode(), currency.getCurrencyCode()) &&
                Objects.equals(isBaseCurrency, currency.isBaseCurrency) &&
                Objects.equals(getExchangeRate(), currency.getExchangeRate()) &&
                Objects.equals(getBusinessID(), currency.getBusinessID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCurrencyID(), getCurrencyName(), getCurrencyCode(), isBaseCurrency, getExchangeRate(), getBusinessID());
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
