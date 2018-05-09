/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/9/2018
 * Time: 11:08 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.coreEntities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
public class CurrencyExchangeRate extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "currencyExchangeRateID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
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
        if (!(o instanceof CurrencyExchangeRate)) return false;
        if (!super.equals(o)) return false;
        CurrencyExchangeRate that = (CurrencyExchangeRate) o;
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
        return "CurrencyExchangeRate{" +
                "currencyExchangeRateID=" + currencyExchangeRateID +
                ", date=" + date +
                ", currencyID=" + currencyID +
                ", rate=" + rate +
                '}';
    }
}
