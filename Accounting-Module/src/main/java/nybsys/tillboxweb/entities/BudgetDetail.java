package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class BudgetDetail extends BaseEntity {
    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "budgetDetailID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    @Column
    @NotNull
    private Integer budgetDetailID;
    @Column
    private Integer budgetID;
    @Column
    private Integer accountTypeID;
    @Column
    private Integer accountID;
    @Column
    private Integer period;
    @Column
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
        if (!(o instanceof BudgetDetail)) return false;
        if (!super.equals(o)) return false;
        BudgetDetail that = (BudgetDetail) o;
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
        return "BudgetDetail{" +
                "budgetDetailID=" + budgetDetailID +
                ", budgetID=" + budgetID +
                ", accountTypeID=" + accountTypeID +
                ", accountID=" + accountID +
                ", period=" + period +
                ", amount=" + amount +
                '}';
    }
}
