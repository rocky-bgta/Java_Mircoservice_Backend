/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 09-Jan-18
 * Time: 12:24 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */
package nybsys.tillboxweb.models;



import nybsys.tillboxweb.BaseModel;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;
import java.util.Objects;

public class GstSettingModel extends BaseModel {

    private Integer gstSettingsID;


    @Email(message = "Email should be valid")
    private String userID;

    @NotNull(message = "BusinessID cannot be empty")
    private Integer businessID;

    private Boolean isRegistered;


    private Integer accountingBasic;

    private Integer reportingFrequency;


    private Integer gstOption;

    private Integer basLodgement;

    private Date annualReportDate;

    private Integer selectedGstOption;

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

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
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
        if (!(o instanceof GstSettingModel)) return false;
        GstSettingModel that = (GstSettingModel) o;
        return isRegistered() == that.isRegistered() &&
                Objects.equals(getGstSettingsID(), that.getGstSettingsID()) &&
                Objects.equals(getUserID(), that.getUserID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
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

        return Objects.hash(getGstSettingsID(), getUserID(), getBusinessID(), isRegistered(), getAccountingBasic(), getReportingFrequency(), getGstOption(), getBasLodgement(), getAnnualReportDate(), getSelectedGstOption(), getSelectedBasLodgement());
    }

    @Override
    public String toString() {
        return "GstSettingModel{" +
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
