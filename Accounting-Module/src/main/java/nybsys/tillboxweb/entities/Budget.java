package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class Budget extends BaseEntity {
    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "budgetID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    @Column
    @NotNull
    private Integer budgetID;
    @Column
    private Integer businessID;
    @Column
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
        if (!(o instanceof Budget)) return false;
        if (!super.equals(o)) return false;
        Budget budget = (Budget) o;
        return Objects.equals(getBudgetID(), budget.getBudgetID()) &&
                Objects.equals(getBusinessID(), budget.getBusinessID()) &&
                Objects.equals(getFinancialYearID(), budget.getFinancialYearID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getBudgetID(), getBusinessID(), getFinancialYearID());
    }

    @Override
    public String toString() {
        return "Budget{" +
                "budgetID=" + budgetID +
                ", businessID=" + businessID +
                ", financialYearID=" + financialYearID +
                '}';
    }
}
