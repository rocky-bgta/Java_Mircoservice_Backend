package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
public class BusinessDetail extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "businessDetailsID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    private Integer businessDetailsID;

    @Column
    @NotNull(message = "Business id cannot be empty")
    private Integer businessID;

    @Column
    @NotNull(message = "Serial No  cannot be empty")
    private Integer financialYearID;

    private String tradingName;

    private String abnacn;

    private String abnBranch;

    private Boolean taxPaymentsReporting;

    private String emailAddress;

    private String phone;

    private String fax;

    private String website;

    @Column
    @NotNull(message = "Opening balance date cannot be empty")
    private Date openingBalanceDate;

    @Column
    @NotNull(message = "Lock my date cannot be empty")
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
        if (!(o instanceof BusinessDetail)) return false;
        if (!super.equals(o)) return false;
        BusinessDetail that = (BusinessDetail) o;
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
        return "BusinessDetail{" +
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
