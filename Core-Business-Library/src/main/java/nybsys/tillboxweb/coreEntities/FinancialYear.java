package nybsys.tillboxweb.coreEntities;
import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
public class FinancialYear extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "financialYearID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

    private Integer financialYearID;
    @Column
    @NotNull
    private String financialYearName;
    @Column
    @NotNull
    private Integer businessID;
    @Column
    @NotNull
    private Date startDate;
    @Column
    @NotNull
    private Date endDate;
    @Column
    private Date lockMyDataAt;
    @Column
    private Boolean lockData;
    @Column
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
        if (!(o instanceof FinancialYear)) return false;
        if (!super.equals(o)) return false;
        FinancialYear that = (FinancialYear) o;
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
        return "FinancialYear{" +
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
