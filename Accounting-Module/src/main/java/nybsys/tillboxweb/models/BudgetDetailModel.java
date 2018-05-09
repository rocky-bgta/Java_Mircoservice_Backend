package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class BudgetDetailModel extends BaseModel {
    private Integer budgetDetailID;
    private Integer budgetID;
    private Integer accountTypeID;
    private Integer accountID;
    private Integer period;
    private Float amount;

    public Integer getBudgetDetailID() {
        return budgetDetailID;
    }

    public void setBudgetDetailID(Integer budgetDetailID) {
        this.budgetDetailID = budgetDetailID;
    }

    public Integer getBudgetID() {
        return budgetID;
    }

    public void setBudgetID(Integer budgetID) {
        this.budgetID = budgetID;
    }

    public Integer getAccountTypeID() {
        return accountTypeID;
    }

    public void setAccountTypeID(Integer accountTypeID) {
        this.accountTypeID = accountTypeID;
    }

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BudgetDetailModel)) return false;
        if (!super.equals(o)) return false;
        BudgetDetailModel that = (BudgetDetailModel) o;
        return Objects.equals(getBudgetDetailID(), that.getBudgetDetailID()) &&
                Objects.equals(getBudgetID(), that.getBudgetID()) &&
                Objects.equals(getAccountTypeID(), that.getAccountTypeID()) &&
                Objects.equals(getAccountID(), that.getAccountID()) &&
                Objects.equals(getPeriod(), that.getPeriod()) &&
                Objects.equals(getAmount(), that.getAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getBudgetDetailID(), getBudgetID(), getAccountTypeID(), getAccountID(), getPeriod(), getAmount());
    }

    @Override
    public String toString() {
        return "BudgetDetailModel{" +
                "budgetDetailID=" + budgetDetailID +
                ", budgetID=" + budgetID +
                ", accountTypeID=" + accountTypeID +
                ", accountID=" + accountID +
                ", period=" + period +
                ", amount=" + amount +
                '}';
    }
}
