/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 18-Apr-18
 * Time: 2:55 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class OutStandingBalance extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "outStandingBalanceID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

    private Integer outStandingBalanceID;
    private Integer businessID;
    private Boolean isMonthly;
    private Integer runAgeingBasedOn;

    public Integer getOutStandingBalanceID() {
        return outStandingBalanceID;
    }

    public void setOutStandingBalanceID(Integer outStandingBalanceID) {
        this.outStandingBalanceID = outStandingBalanceID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Boolean getIsMonthly() {
        return isMonthly;
    }

    public void setIsMonthly(Boolean isMonthly) {
        this.isMonthly = isMonthly;
    }

    public Integer getRunAgeingBasedOn() {
        return runAgeingBasedOn;
    }

    public void setRunAgeingBasedOn(Integer runAgeingBasedOn) {
        this.runAgeingBasedOn = runAgeingBasedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OutStandingBalance)) return false;
        if (!super.equals(o)) return false;
        OutStandingBalance that = (OutStandingBalance) o;
        return Objects.equals(getOutStandingBalanceID(), that.getOutStandingBalanceID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(isMonthly, that.isMonthly) &&
                Objects.equals(getRunAgeingBasedOn(), that.getRunAgeingBasedOn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getOutStandingBalanceID(), getBusinessID(), isMonthly, getRunAgeingBasedOn());
    }

    @Override
    public String toString() {
        return "OutStandingBalance{" +
                "outStandingBalanceID=" + outStandingBalanceID +
                ", businessID=" + businessID +
                ", isMonthly=" + isMonthly +
                ", runAgeingBasedOn=" + runAgeingBasedOn +
                '}';
    }
}