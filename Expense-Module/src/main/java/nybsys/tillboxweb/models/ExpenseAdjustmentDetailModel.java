/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 27/03/2018
 * Time: 05:38
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class ExpenseAdjustmentDetailModel extends BaseModel {
    private Integer expenseAdjustmentDetailID;
    private Integer expenseAdjustmentID;
    private Integer expenseDetailID;
    private Double amount;
    private Integer accountIDFrom;
    private Integer accountIDExpense;

    public Integer getExpenseAdjustmentDetailID() {
        return expenseAdjustmentDetailID;
    }

    public void setExpenseAdjustmentDetailID(Integer expenseAdjustmentDetailID) {
        this.expenseAdjustmentDetailID = expenseAdjustmentDetailID;
    }

    public Integer getExpenseAdjustmentID() {
        return expenseAdjustmentID;
    }

    public void setExpenseAdjustmentID(Integer expenseAdjustmentID) {
        this.expenseAdjustmentID = expenseAdjustmentID;
    }

    public Integer getExpenseDetailID() {
        return expenseDetailID;
    }

    public void setExpenseDetailID(Integer expenseDetailID) {
        this.expenseDetailID = expenseDetailID;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getAccountIDFrom() {
        return accountIDFrom;
    }

    public void setAccountIDFrom(Integer accountIDFrom) {
        this.accountIDFrom = accountIDFrom;
    }

    public Integer getAccountIDExpense() {
        return accountIDExpense;
    }

    public void setAccountIDExpense(Integer accountIDExpense) {
        this.accountIDExpense = accountIDExpense;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExpenseAdjustmentDetailModel)) return false;
        if (!super.equals(o)) return false;
        ExpenseAdjustmentDetailModel that = (ExpenseAdjustmentDetailModel) o;
        return Objects.equals(getExpenseAdjustmentDetailID(), that.getExpenseAdjustmentDetailID()) &&
                Objects.equals(getExpenseAdjustmentID(), that.getExpenseAdjustmentID()) &&
                Objects.equals(getExpenseDetailID(), that.getExpenseDetailID()) &&
                Objects.equals(getAmount(), that.getAmount()) &&
                Objects.equals(getAccountIDFrom(), that.getAccountIDFrom()) &&
                Objects.equals(getAccountIDExpense(), that.getAccountIDExpense());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getExpenseAdjustmentDetailID(), getExpenseAdjustmentID(), getExpenseDetailID(), getAmount(), getAccountIDFrom(), getAccountIDExpense());
    }

    @Override
    public String toString() {
        return "ExpenseAdjustmentDetailModel{" +
                "expenseAdjustmentDetailID=" + expenseAdjustmentDetailID +
                ", expenseAdjustmentID=" + expenseAdjustmentID +
                ", expenseDetailID=" + expenseDetailID +
                ", amount=" + amount +
                ", accountIDFrom=" + accountIDFrom +
                ", accountIDExpense=" + accountIDExpense +
                '}';
    }
}
