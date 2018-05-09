/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 08/02/2018
 * Time: 10:27
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;
import nybsys.tillboxweb.BaseModelWithCurrency;

import java.util.Date;
import java.util.Objects;

public class OpeningBalanceModel extends BaseModelWithCurrency{
    private Integer openingBalanceID;
    private Integer accountID;
    private Integer businessID;
    private Double amount;
    private Date date;
    private String note;
    private Integer referenceID;
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
        if (!(o instanceof OpeningBalanceModel)) return false;
        if (!super.equals(o)) return false;
        OpeningBalanceModel that = (OpeningBalanceModel) o;
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
        return "OpeningBalanceModel{" +
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
