/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 24-Apr-18
 * Time: 11:17 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.TillBoxWebEntities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class DefaultCashFlow extends BaseEntity {

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
}