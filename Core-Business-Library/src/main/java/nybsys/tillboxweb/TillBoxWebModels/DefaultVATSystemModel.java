/**
 * Created By: Md. Abdul Hannan
 * Created Date: 4/16/2018
 * Time: 12:06 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.TillBoxWebModels;

import nybsys.tillboxweb.BaseModel;

import java.util.Date;
import java.util.Objects;

public class DefaultVATSystemModel extends BaseModel {
    Integer vATSystemID;
    Integer businessID;
    Integer vATSystem;
    Integer vATNumber;
    Date lastVATPeriodEndDate;
    Date lastVATSubmissionDue;
    Integer vATReportingFrequency;

    public Integer getvATSystemID() {
        return vATSystemID;
    }

    public void setvATSystemID(Integer vATSystemID) {
        this.vATSystemID = vATSystemID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getvATSystem() {
        return vATSystem;
    }

    public void setvATSystem(Integer vATSystem) {
        this.vATSystem = vATSystem;
    }

    public Integer getvATNumber() {
        return vATNumber;
    }

    public void setvATNumber(Integer vATNumber) {
        this.vATNumber = vATNumber;
    }

    public Date getLastVATPeriodEndDate() {
        return lastVATPeriodEndDate;
    }

    public void setLastVATPeriodEndDate(Date lastVATPeriodEndDate) {
        this.lastVATPeriodEndDate = lastVATPeriodEndDate;
    }

    public Date getLastVATSubmissionDue() {
        return lastVATSubmissionDue;
    }

    public void setLastVATSubmissionDue(Date lastVATSubmissionDue) {
        this.lastVATSubmissionDue = lastVATSubmissionDue;
    }

    public Integer getvATReportingFrequency() {
        return vATReportingFrequency;
    }

    public void setvATReportingFrequency(Integer vATReportingFrequency) {
        this.vATReportingFrequency = vATReportingFrequency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultVATSystemModel)) return false;
        if (!super.equals(o)) return false;
        DefaultVATSystemModel that = (DefaultVATSystemModel) o;
        return Objects.equals(getvATSystemID(), that.getvATSystemID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getvATSystem(), that.getvATSystem()) &&
                Objects.equals(getvATNumber(), that.getvATNumber()) &&
                Objects.equals(getLastVATPeriodEndDate(), that.getLastVATPeriodEndDate()) &&
                Objects.equals(getLastVATSubmissionDue(), that.getLastVATSubmissionDue()) &&
                Objects.equals(getvATReportingFrequency(), that.getvATReportingFrequency());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getvATSystemID(), getBusinessID(), getvATSystem(), getvATNumber(), getLastVATPeriodEndDate(), getLastVATSubmissionDue(), getvATReportingFrequency());
    }

    @Override
    public String toString() {
        return "VATSystemModel{" +
                "vATSystemID=" + vATSystemID +
                ", businessID=" + businessID +
                ", vATSystem=" + vATSystem +
                ", vATNumber=" + vATNumber +
                ", lastVATPeriodEndDate=" + lastVATPeriodEndDate +
                ", lastVATSubmissionDue=" + lastVATSubmissionDue +
                ", vATReportingFrequency=" + vATReportingFrequency +
                '}';
    }
}
