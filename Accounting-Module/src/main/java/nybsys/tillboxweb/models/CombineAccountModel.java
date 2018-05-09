/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/8/2018
 * Time: 9:58 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;
import nybsys.tillboxweb.BaseModelWithCurrency;

import java.util.Date;
import java.util.Objects;

public class CombineAccountModel extends BaseModelWithCurrency {
    private Integer combineAccountID;
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
        if (!(o instanceof CombineAccountModel)) return false;
        if (!super.equals(o)) return false;
        CombineAccountModel that = (CombineAccountModel) o;
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
        return "CombineAccountModel{" +
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
