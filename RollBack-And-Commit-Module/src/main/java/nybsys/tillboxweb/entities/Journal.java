package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntityWithCurrency;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;

@Entity
public class Journal extends BaseEntityWithCurrency {
    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "journalID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    @Column
    @NotNull
    private BigInteger journalID;
    @Column
    private Integer businessID;
    @Column
    private String journalReferenceNo;
    @Column
    private Integer financialYearID;
    @Column
    private Date date;
    @Column
    private Integer period;
    @Column
    private Integer accountID;
    @Column
    private Double amount;
    @Column
    private Integer drCrIndicator;
    @Column
    private Integer referenceID;
    @Column
    private Integer referenceType;
    @Column
    private Integer partyID;
    @Column
    private Integer partyType;
    @Column
    private String note;

    public BigInteger getJournalID() {
        return journalID;
    }

    public void setJournalID(BigInteger journalID) {
        this.journalID = journalID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public String getJournalReferenceNo() {
        return journalReferenceNo;
    }

    public void setJournalReferenceNo(String journalReferenceNo) {
        this.journalReferenceNo = journalReferenceNo;
    }

    public Integer getFinancialYearID() {
        return financialYearID;
    }

    public void setFinancialYearID(Integer financialYearID) {
        this.financialYearID = financialYearID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getDrCrIndicator() {
        return drCrIndicator;
    }

    public void setDrCrIndicator(Integer drCrIndicator) {
        this.drCrIndicator = drCrIndicator;
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

    public Integer getPartyID() {
        return partyID;
    }

    public void setPartyID(Integer partyID) {
        this.partyID = partyID;
    }

    public Integer getPartyType() {
        return partyType;
    }

    public void setPartyType(Integer partyType) {
        this.partyType = partyType;
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
        if (!(o instanceof Journal)) return false;
        if (!super.equals(o)) return false;
        Journal journal = (Journal) o;
        return Objects.equals(getJournalID(), journal.getJournalID()) &&
                Objects.equals(getBusinessID(), journal.getBusinessID()) &&
                Objects.equals(getJournalReferenceNo(), journal.getJournalReferenceNo()) &&
                Objects.equals(getFinancialYearID(), journal.getFinancialYearID()) &&
                Objects.equals(getDate(), journal.getDate()) &&
                Objects.equals(getPeriod(), journal.getPeriod()) &&
                Objects.equals(getAccountID(), journal.getAccountID()) &&
                Objects.equals(getAmount(), journal.getAmount()) &&
                Objects.equals(getDrCrIndicator(), journal.getDrCrIndicator()) &&
                Objects.equals(getReferenceID(), journal.getReferenceID()) &&
                Objects.equals(getReferenceType(), journal.getReferenceType()) &&
                Objects.equals(getPartyID(), journal.getPartyID()) &&
                Objects.equals(getPartyType(), journal.getPartyType()) &&
                Objects.equals(getNote(), journal.getNote());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getJournalID(), getBusinessID(), getJournalReferenceNo(), getFinancialYearID(), getDate(), getPeriod(), getAccountID(), getAmount(), getDrCrIndicator(), getReferenceID(), getReferenceType(), getPartyID(), getPartyType(), getNote());
    }

    @Override
    public String toString() {
        return "Journal{" +
                "journalID=" + journalID +
                ", businessID=" + businessID +
                ", journalReferenceNo='" + journalReferenceNo + '\'' +
                ", financialYearID=" + financialYearID +
                ", date=" + date +
                ", period=" + period +
                ", accountID=" + accountID +
                ", amount=" + amount +
                ", drCrIndicator=" + drCrIndicator +
                ", referenceID=" + referenceID +
                ", referenceType=" + referenceType +
                ", partyID=" + partyID +
                ", partyType=" + partyType +
                ", note='" + note + '\'' +
                '}';
    }
}
