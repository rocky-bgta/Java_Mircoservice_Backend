/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 09-Jan-18
 * Time: 11:55 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */
package nybsys.tillboxweb.entities;



import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
public class GstSetting extends BaseEntity {
    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "gstSettingsID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    private Integer gstSettingsID;

    @Column
    @Email(message = "Email should be valid")
    @NotEmpty
    private String userID;

    @Column
    @NotNull(message = "BusinessID cannot be empty")
    private Integer businessID;

    @Column
    private Boolean isRegistered;

    @Column
    private Integer accountingBasic;

    @Column
    private Integer reportingFrequency;

    @Column
    private Integer gstOption;

    @Column
    private Integer basLodgement;

    @Column
    private Date annualReportDate;

    @Column
    private Integer selectedGstOption;

    @Column
    private Integer selectedBasLodgement;

    public Integer getGstSettingsID() {
        return gstSettingsID;
    }

    public void setGstSettingsID(Integer gstSettingsID) {
        this.gstSettingsID = gstSettingsID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Boolean getRegistered() {
        return isRegistered;
    }

    public void setRegistered(Boolean registered) {
        isRegistered = registered;
    }

    public Integer getAccountingBasic() {
        return accountingBasic;
    }

    public void setAccountingBasic(Integer accountingBasic) {
        this.accountingBasic = accountingBasic;
    }

    public Integer getReportingFrequency() {
        return reportingFrequency;
    }

    public void setReportingFrequency(Integer reportingFrequency) {
        this.reportingFrequency = reportingFrequency;
    }

    public Integer getGstOption() {
        return gstOption;
    }

    public void setGstOption(Integer gstOption) {
        this.gstOption = gstOption;
    }

    public Integer getBasLodgement() {
        return basLodgement;
    }

    public void setBasLodgement(Integer basLodgement) {
        this.basLodgement = basLodgement;
    }

    public Date getAnnualReportDate() {
        return annualReportDate;
    }

    public void setAnnualReportDate(Date annualReportDate) {
        this.annualReportDate = annualReportDate;
    }

    public Integer getSelectedGstOption() {
        return selectedGstOption;
    }

    public void setSelectedGstOption(Integer selectedGstOption) {
        this.selectedGstOption = selectedGstOption;
    }

    public Integer getSelectedBasLodgement() {
        return selectedBasLodgement;
    }

    public void setSelectedBasLodgement(Integer selectedBasLodgement) {
        this.selectedBasLodgement = selectedBasLodgement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GstSetting)) return false;
        if (!super.equals(o)) return false;
        GstSetting that = (GstSetting) o;
        return Objects.equals(getGstSettingsID(), that.getGstSettingsID()) &&
                Objects.equals(getUserID(), that.getUserID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(isRegistered, that.isRegistered) &&
                Objects.equals(getAccountingBasic(), that.getAccountingBasic()) &&
                Objects.equals(getReportingFrequency(), that.getReportingFrequency()) &&
                Objects.equals(getGstOption(), that.getGstOption()) &&
                Objects.equals(getBasLodgement(), that.getBasLodgement()) &&
                Objects.equals(getAnnualReportDate(), that.getAnnualReportDate()) &&
                Objects.equals(getSelectedGstOption(), that.getSelectedGstOption()) &&
                Objects.equals(getSelectedBasLodgement(), that.getSelectedBasLodgement());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getGstSettingsID(), getUserID(), getBusinessID(), isRegistered, getAccountingBasic(), getReportingFrequency(), getGstOption(), getBasLodgement(), getAnnualReportDate(), getSelectedGstOption(), getSelectedBasLodgement());
    }

    @Override
    public String toString() {
        return "GstSetting{" +
                "gstSettingsID=" + gstSettingsID +
                ", userID='" + userID + '\'' +
                ", businessID=" + businessID +
                ", isRegistered=" + isRegistered +
                ", accountingBasic=" + accountingBasic +
                ", reportingFrequency=" + reportingFrequency +
                ", gstOption=" + gstOption +
                ", basLodgement=" + basLodgement +
                ", annualReportDate=" + annualReportDate +
                ", selectedGstOption=" + selectedGstOption +
                ", selectedBasLodgement=" + selectedBasLodgement +
                '}';
    }
}
