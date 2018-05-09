package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModelWithCurrency;

import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;

public class JournalModel extends BaseModelWithCurrency {

    private BigInteger journalID;
    private Integer businessID;
    private String journalReferenceNo;
    private Integer financialYearID;
    private Date date;
    private Integer period;
    private Integer accountID;
    private Double amount;
    private Integer drCrIndicator;
    private Integer referenceID;
    private Integer referenceType;
    private Integer partyID;
    private Integer partyType;
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
        if (!(o instanceof JournalModel)) return false;
        if (!super.equals(o)) return false;
        JournalModel that = (JournalModel) o;
        return Objects.equals(getJournalID(), that.getJournalID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getJournalReferenceNo(), that.getJournalReferenceNo()) &&
                Objects.equals(getFinancialYearID(), that.getFinancialYearID()) &&
                Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getPeriod(), that.getPeriod()) &&
                Objects.equals(getAccountID(), that.getAccountID()) &&
                Objects.equals(getAmount(), that.getAmount()) &&
                Objects.equals(getDrCrIndicator(), that.getDrCrIndicator()) &&
                Objects.equals(getReferenceID(), that.getReferenceID()) &&
                Objects.equals(getReferenceType(), that.getReferenceType()) &&
                Objects.equals(getPartyID(), that.getPartyID()) &&
                Objects.equals(getPartyType(), that.getPartyType()) &&
                Objects.equals(getNote(), that.getNote());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getJournalID(), getBusinessID(), getJournalReferenceNo(), getFinancialYearID(), getDate(), getPeriod(), getAccountID(), getAmount(), getDrCrIndicator(), getReferenceID(), getReferenceType(), getPartyID(), getPartyType(), getNote());
    }

    @Override
    public String toString() {
        return "JournalModel{" +
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
