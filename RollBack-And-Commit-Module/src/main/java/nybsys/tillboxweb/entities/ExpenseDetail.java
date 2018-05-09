/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 27/03/2018
 * Time: 05:42
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
import java.util.Date;
import java.util.Objects;

@Entity
public class ExpenseDetail extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "expenseDetailID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

    private Integer expenseDetailID;
    private Integer expenseID;
    private String accountNumber;
    private Double amount;
    private String note;
    private Integer accountIDFrom;
    private Date endDate;
    private Integer accountIDExpense;
    private Integer expenseTypeID;
    private Date effectiveDate;
    //private Byte attachment;
    private Integer paymentMethod;
    private String referenceNumber;

    public Integer getExpenseDetailID() {
        return expenseDetailID;
    }

    public void setExpenseDetailID(Integer expenseDetailID) {
        this.expenseDetailID = expenseDetailID;
    }

    public Integer getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(Integer expenseID) {
        this.expenseID = expenseID;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getAccountIDFrom() {
        return accountIDFrom;
    }

    public void setAccountIDFrom(Integer accountIDFrom) {
        this.accountIDFrom = accountIDFrom;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getAccountIDExpense() {
        return accountIDExpense;
    }

    public void setAccountIDExpense(Integer accountIDExpense) {
        this.accountIDExpense = accountIDExpense;
    }

    public Integer getExpenseTypeID() {
        return expenseTypeID;
    }

    public void setExpenseTypeID(Integer expenseTypeID) {
        this.expenseTypeID = expenseTypeID;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExpenseDetail)) return false;
        if (!super.equals(o)) return false;
        ExpenseDetail that = (ExpenseDetail) o;
        return Objects.equals(getExpenseDetailID(), that.getExpenseDetailID()) &&
                Objects.equals(getExpenseID(), that.getExpenseID()) &&
                Objects.equals(getAccountNumber(), that.getAccountNumber()) &&
                Objects.equals(getAmount(), that.getAmount()) &&
                Objects.equals(getNote(), that.getNote()) &&
                Objects.equals(getAccountIDFrom(), that.getAccountIDFrom()) &&
                Objects.equals(getEndDate(), that.getEndDate()) &&
                Objects.equals(getAccountIDExpense(), that.getAccountIDExpense()) &&
                Objects.equals(getExpenseTypeID(), that.getExpenseTypeID()) &&
                Objects.equals(getEffectiveDate(), that.getEffectiveDate()) &&
                Objects.equals(getPaymentMethod(), that.getPaymentMethod()) &&
                Objects.equals(getReferenceNumber(), that.getReferenceNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getExpenseDetailID(), getExpenseID(), getAccountNumber(), getAmount(), getNote(), getAccountIDFrom(), getEndDate(), getAccountIDExpense(), getExpenseTypeID(), getEffectiveDate(), getPaymentMethod(), getReferenceNumber());
    }

    @Override
    public String toString() {
        return "ExpenseDetail{" +
                "expenseDetailID=" + expenseDetailID +
                ", expenseID=" + expenseID +
                ", accountNumber='" + accountNumber + '\'' +
                ", amount=" + amount +
                ", note='" + note + '\'' +
                ", accountIDFrom=" + accountIDFrom +
                ", endDate=" + endDate +
                ", accountIDExpense=" + accountIDExpense +
                ", expenseTypeID=" + expenseTypeID +
                ", effectiveDate=" + effectiveDate +
                ", paymentMethod=" + paymentMethod +
                ", referenceNumber='" + referenceNumber + '\'' +
                '}';
    }
}