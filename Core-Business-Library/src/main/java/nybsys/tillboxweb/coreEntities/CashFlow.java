/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 24-Apr-18
 * Time: 11:21 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.coreEntities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

    private Integer cashFlowID;
    private String cashFlowName;

    public Integer getCashFlowID() {
        return cashFlowID;
    }

    public void setCashFlowID(Integer cashFlowID) {
        this.cashFlowID = cashFlowID;
    }

    public String getCashFlowName() {
        return cashFlowName;
    }

    public void setCashFlowName(String cashFlowName) {
        this.cashFlowName = cashFlowName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CashFlow)) return false;
        if (!super.equals(o)) return false;
        CashFlow cashFlow = (CashFlow) o;
        return Objects.equals(getCashFlowID(), cashFlow.getCashFlowID()) &&
                Objects.equals(getCashFlowName(), cashFlow.getCashFlowName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCashFlowID(), getCashFlowName());
    }

    @Override
    public String toString() {
        return "CashFlow{" +
                "cashFlowID=" + cashFlowID +
                ", cashFlowName='" + cashFlowName + '\'' +
                '}';
    }
}