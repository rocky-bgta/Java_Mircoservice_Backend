package nybsys.tillboxweb.coreModels;
import nybsys.tillboxweb.BaseModel;

import java.util.Date;
import java.util.Objects;

public class FinancialYearModel extends BaseModel{
    private Integer financialYearID;
    private String financialYearName;
    private Integer businessID;
    private Date startDate;
    private Date endDate;
    private Date lockMyDataAt;
    private Boolean lockData;
    private Boolean isCurrentFinancialYear;

    public Integer getFinancialYearID() {
        return financialYearID;
    }

    public void setFinancialYearID(Integer financialYearID) {
        this.financialYearID = financialYearID;
    }

    public String getFinancialYearName() {
        return financialYearName;
    }

    public void setFinancialYearName(String financialYearName) {
        this.financialYearName = financialYearName;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getLockMyDataAt() {
        return lockMyDataAt;
    }

    public void setLockMyDataAt(Date lockMyDataAt) {
        this.lockMyDataAt = lockMyDataAt;
    }

    public Boolean getIsCurrentFinancialYear() {
        return isCurrentFinancialYear;
    }

    public void setIsCurrentFinancialYear(Boolean isCurrentFinancialYear) {
        this.isCurrentFinancialYear = isCurrentFinancialYear;
    }

    public Boolean getLockData() {
        return lockData;
    }

    public void setLockData(Boolean lockData) {
        this.lockData = lockData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FinancialYearModel)) return false;
        if (!super.equals(o)) return false;
        FinancialYearModel that = (FinancialYearModel) o;
        return Objects.equals(getFinancialYearID(), that.getFinancialYearID()) &&
                Objects.equals(getFinancialYearName(), that.getFinancialYearName()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getStartDate(), that.getStartDate()) &&
                Objects.equals(getEndDate(), that.getEndDate()) &&
                Objects.equals(getLockMyDataAt(), that.getLockMyDataAt()) &&
                Objects.equals(getLockData(), that.getLockData()) &&
                Objects.equals(getIsCurrentFinancialYear(), that.getIsCurrentFinancialYear());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getFinancialYearID(), getFinancialYearName(), getBusinessID(), getStartDate(), getEndDate(), getLockMyDataAt(), getLockData(), getIsCurrentFinancialYear());
    }

    @Override
    public String toString() {
        return "FinancialYearModel{" +
                "financialYearID=" + financialYearID +
                ", financialYearName='" + financialYearName + '\'' +
                ", businessID=" + businessID +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", lockMyDataAt=" + lockMyDataAt +
                ", lockData=" + lockData +
                ", isCurrentFinancialYear=" + isCurrentFinancialYear +
                '}';
    }
}
