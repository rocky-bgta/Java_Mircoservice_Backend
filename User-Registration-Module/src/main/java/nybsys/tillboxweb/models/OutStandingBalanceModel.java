/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 18-Apr-18
 * Time: 3:08 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class OutStandingBalanceModel extends BaseModel {

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
        if (!(o instanceof OutStandingBalanceModel)) return false;
        if (!super.equals(o)) return false;
        OutStandingBalanceModel that = (OutStandingBalanceModel) o;
        return Objects.equals(getOutStandingBalanceID(), that.getOutStandingBalanceID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getIsMonthly(), that.getIsMonthly()) &&
                Objects.equals(getRunAgeingBasedOn(), that.getRunAgeingBasedOn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getOutStandingBalanceID(), getBusinessID(), getIsMonthly(), getRunAgeingBasedOn());
    }

    @Override
    public String toString() {
        return "OutStandingBalanceModel{" +
                "outStandingBalanceID=" + outStandingBalanceID +
                ", businessID=" + businessID +
                ", isMonthly=" + isMonthly +
                ", runAgeingBasedOn=" + runAgeingBasedOn +
                '}';
    }
}
