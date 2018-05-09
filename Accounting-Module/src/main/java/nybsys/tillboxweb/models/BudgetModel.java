package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class BudgetModel extends BaseModel {
    private Integer budgetID;
    private Integer businessID;
    private Integer financialYearID;

    public Integer getBudgetID() {
        return budgetID;
    }

    public void setBudgetID(Integer budgetID) {
        this.budgetID = budgetID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getFinancialYearID() {
        return financialYearID;
    }

    public void setFinancialYearID(Integer financialYearID) {
        this.financialYearID = financialYearID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BudgetModel)) return false;
        if (!super.equals(o)) return false;
        BudgetModel that = (BudgetModel) o;
        return Objects.equals(getBudgetID(), that.getBudgetID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getFinancialYearID(), that.getFinancialYearID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getBudgetID(), getBusinessID(), getFinancialYearID());
    }

    @Override
    public String toString() {
        return "BudgetModel{" +
                "budgetID=" + budgetID +
                ", businessID=" + businessID +
                ", financialYearID=" + financialYearID +
                '}';
    }
}
