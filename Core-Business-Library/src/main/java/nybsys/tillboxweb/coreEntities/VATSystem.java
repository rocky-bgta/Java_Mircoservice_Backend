/**
 * Created By: Md. Abdul Hannan
 * Created Date: 4/16/2018
 * Time: 12:30 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.coreEntities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
public class VATSystem extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "vATSystemID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
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
        if (!(o instanceof VATSystem)) return false;
        if (!super.equals(o)) return false;
        VATSystem vatSystem = (VATSystem) o;
        return Objects.equals(getvATSystemID(), vatSystem.getvATSystemID()) &&
                Objects.equals(getBusinessID(), vatSystem.getBusinessID()) &&
                Objects.equals(getvATSystem(), vatSystem.getvATSystem()) &&
                Objects.equals(getvATNumber(), vatSystem.getvATNumber()) &&
                Objects.equals(getLastVATPeriodEndDate(), vatSystem.getLastVATPeriodEndDate()) &&
                Objects.equals(getLastVATSubmissionDue(), vatSystem.getLastVATSubmissionDue()) &&
                Objects.equals(getvATReportingFrequency(), vatSystem.getvATReportingFrequency());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getvATSystemID(), getBusinessID(), getvATSystem(), getvATNumber(), getLastVATPeriodEndDate(), getLastVATSubmissionDue(), getvATReportingFrequency());
    }

    @Override
    public String toString() {
        return "VATSystem{" +
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
