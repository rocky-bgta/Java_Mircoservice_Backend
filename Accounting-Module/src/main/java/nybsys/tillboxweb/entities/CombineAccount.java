/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/8/2018
 * Time: 10:09 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import nybsys.tillboxweb.BaseEntityWithCurrency;
import nybsys.tillboxweb.models.CombineAccountModel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
public class CombineAccount extends BaseEntityWithCurrency {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "combineAccountID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    @Column
    @NotNull
    private Integer combineAccountID;
    @NotNull
    private Integer businessID;
    private Date date;
    private String docNumber;
    private Double amount;
    private Integer accountIDTo;
    private Integer accountIDFrom;
    private String note;

    public Integer getCombineAccountID() {
        return combineAccountID;
    }

    public void setCombineAccountID(Integer combineAccountID) {
        this.combineAccountID = combineAccountID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getAccountIDTo() {
        return accountIDTo;
    }

    public void setAccountIDTo(Integer accountIDTo) {
        this.accountIDTo = accountIDTo;
    }

    public Integer getAccountIDFrom() {
        return accountIDFrom;
    }

    public void setAccountIDFrom(Integer accountIDFrom) {
        this.accountIDFrom = accountIDFrom;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CombineAccount)) return false;
        if (!super.equals(o)) return false;
        CombineAccount that = (CombineAccount) o;
        return Objects.equals(getCombineAccountID(), that.getCombineAccountID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getDocNumber(), that.getDocNumber()) &&
                Objects.equals(getAmount(), that.getAmount()) &&
                Objects.equals(getAccountIDTo(), that.getAccountIDTo()) &&
                Objects.equals(getAccountIDFrom(), that.getAccountIDFrom()) &&
                Objects.equals(getNote(), that.getNote());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCombineAccountID(), getBusinessID(), getDate(), getDocNumber(), getAmount(), getAccountIDTo(), getAccountIDFrom(), getNote());
    }

    @Override
    public String toString() {
        return "CombineAccount{" +
                "combineAccountID=" + combineAccountID +
                ", businessID=" + businessID +
                ", date=" + date +
                ", docNumber='" + docNumber + '\'' +
                ", amount=" + amount +
                ", accountIDTo=" + accountIDTo +
                ", accountIDFrom=" + accountIDFrom +
                ", note='" + note + '\'' +
                '}';
    }
}
