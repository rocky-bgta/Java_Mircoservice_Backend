package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class CashFlow extends BaseEntity {
    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "cashFlowID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    @Column
    @NotNull
    private Integer cashFlowID;
    @Column
    private String cashFlowName;

    public Integer getCashFlowID() {
        return cashFlowID;
    }

    public void setCashFlowID(Integer cashFlowID) {
        this.cashFlowID = cashFlowID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CashFlow)) return false;
        if (!super.equals(o)) return false;
        CashFlow cashFlow = (CashFlow) o;
        return Objects.equals(getCashFlowID(), cashFlow.getCashFlowID()) &&
                Objects.equals(cashFlowName, cashFlow.cashFlowName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCashFlowID(), cashFlowName);
    }

    @Override
    public String toString() {
        return "CashFlow{" +
                "cashFlowID=" + cashFlowID +
                ", cashFlowName='" + cashFlowName + '\'' +
                '}';
    }
}
