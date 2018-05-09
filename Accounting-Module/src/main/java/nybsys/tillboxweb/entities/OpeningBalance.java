/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 08/02/2018
 * Time: 10:29
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import nybsys.tillboxweb.BaseEntityWithCurrency;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
public class OpeningBalance extends BaseEntityWithCurrency {
    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "openingBalanceID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    @Column
    @NotNull
    private Integer openingBalanceID;
    @Column
    private Integer accountID;
    @Column
    private Integer businessID;
    @Column
    private Double amount;
    @Column
    private Date date;
    @Column
    private String note;
    @Column
    private Integer referenceID;
    @Column
    private Integer referenceType;

    public Integer getOpeningBalanceID() {
        return openingBalanceID;
    }

    public void setOpeningBalanceID(Integer openingBalanceID) {
        this.openingBalanceID = openingBalanceID;
    }

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getReferenceID() {
        return referenceID;
    }

    public void setReferenceID(Integer referenceID) {
        this.referenceID = referenceID;
    }

    public Integer getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(Integer referenceType) {
        this.referenceType = referenceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OpeningBalance)) return false;
        if (!super.equals(o)) return false;
        OpeningBalance that = (OpeningBalance) o;
        return Objects.equals(getOpeningBalanceID(), that.getOpeningBalanceID()) &&
                Objects.equals(getAccountID(), that.getAccountID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getAmount(), that.getAmount()) &&
                Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getNote(), that.getNote()) &&
                Objects.equals(getReferenceID(), that.getReferenceID()) &&
                Objects.equals(getReferenceType(), that.getReferenceType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getOpeningBalanceID(), getAccountID(), getBusinessID(), getAmount(), getDate(), getNote(), getReferenceID(), getReferenceType());
    }

    @Override
    public String toString() {
        return "OpeningBalance{" +
                "openingBalanceID=" + openingBalanceID +
                ", accountID=" + accountID +
                ", businessID=" + businessID +
                ", amount=" + amount +
                ", date=" + date +
                ", note='" + note + '\'' +
                ", referenceID=" + referenceID +
                ", referenceType=" + referenceType +
                '}';
    }
}
