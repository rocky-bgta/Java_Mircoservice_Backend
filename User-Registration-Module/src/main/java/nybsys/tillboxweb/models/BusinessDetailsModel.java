package nybsys.tillboxweb.models;
import nybsys.tillboxweb.BaseModel;

import java.util.Date;
import java.util.Objects;

public class BusinessDetailsModel extends BaseModel{
    private Integer businessDetailsID;
    private Integer businessID;
    private Integer financialYearID;
    private String tradingName;
    private String abnacn;
    private String abnBranch;
    private Boolean taxPaymentsReporting;
    private String emailAddress;
    private String phone;
    private String fax;
    private String website;
    private Date openingBalanceDate;
    private Date lockmyDataAt;

    public Integer getBusinessDetailsID() {
        return businessDetailsID;
    }

    public void setBusinessDetailsID(Integer businessDetailsID) {
        this.businessDetailsID = businessDetailsID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getFinancialYearID() {
        return financialYearID;
    }

    public void setFinancialYearID(Integer financialYearID) {
        this.financialYearID = financialYearID;
    }

    public String getTradingName() {
        return tradingName;
    }

    public void setTradingName(String tradingName) {
        this.tradingName = tradingName;
    }

    public String getAbnacn() {
        return abnacn;
    }

    public void setAbnacn(String abnacn) {
        this.abnacn = abnacn;
    }

    public String getAbnBranch() {
        return abnBranch;
    }

    public void setAbnBranch(String abnBranch) {
        this.abnBranch = abnBranch;
    }

    public Boolean getTaxPaymentsReporting() {
        return taxPaymentsReporting;
    }

    public void setTaxPaymentsReporting(Boolean taxPaymentsReporting) {
        this.taxPaymentsReporting = taxPaymentsReporting;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Date getOpeningBalanceDate() {
        return openingBalanceDate;
    }

    public void setOpeningBalanceDate(Date openingBalanceDate) {
        this.openingBalanceDate = openingBalanceDate;
    }

    public Date getLockmyDataAt() {
        return lockmyDataAt;
    }

    public void setLockmyDataAt(Date lockmyDataAt) {
        this.lockmyDataAt = lockmyDataAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BusinessDetailsModel)) return false;
        if (!super.equals(o)) return false;
        BusinessDetailsModel that = (BusinessDetailsModel) o;
        return Objects.equals(getBusinessDetailsID(), that.getBusinessDetailsID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getFinancialYearID(), that.getFinancialYearID()) &&
                Objects.equals(getTradingName(), that.getTradingName()) &&
                Objects.equals(getAbnacn(), that.getAbnacn()) &&
                Objects.equals(getAbnBranch(), that.getAbnBranch()) &&
                Objects.equals(getTaxPaymentsReporting(), that.getTaxPaymentsReporting()) &&
                Objects.equals(getEmailAddress(), that.getEmailAddress()) &&
                Objects.equals(getPhone(), that.getPhone()) &&
                Objects.equals(getFax(), that.getFax()) &&
                Objects.equals(getWebsite(), that.getWebsite()) &&
                Objects.equals(getOpeningBalanceDate(), that.getOpeningBalanceDate()) &&
                Objects.equals(getLockmyDataAt(), that.getLockmyDataAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getBusinessDetailsID(), getBusinessID(), getFinancialYearID(), getTradingName(), getAbnacn(), getAbnBranch(), getTaxPaymentsReporting(), getEmailAddress(), getPhone(), getFax(), getWebsite(), getOpeningBalanceDate(), getLockmyDataAt());
    }

    @Override
    public String toString() {
        return "BusinessDetailsModel{" +
                "businessDetailsID=" + businessDetailsID +
                ", businessID=" + businessID +
                ", financialYearID=" + financialYearID +
                ", tradingName='" + tradingName + '\'' +
                ", abnacn='" + abnacn + '\'' +
                ", abnBranch='" + abnBranch + '\'' +
                ", taxPaymentsReporting=" + taxPaymentsReporting +
                ", emailAddress='" + emailAddress + '\'' +
                ", phone='" + phone + '\'' +
                ", fax='" + fax + '\'' +
                ", website='" + website + '\'' +
                ", openingBalanceDate=" + openingBalanceDate +
                ", lockmyDataAt=" + lockmyDataAt +
                '}';
    }
}
