package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class Account extends BaseEntity {
    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "accountID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    @Column
    @NotNull
    private Integer accountID;
    @Column
    private Integer businessID;
    @Column
    private Integer accountCode;
    @Column
    private String accountName;
    @Column
    private Integer accountClassificationID;
    @Column
    private Integer accountTypeID;
    @Column
    private Integer parentAccountID;
    @Column
    private Integer taxCodeID;
    @Column
    private Integer cashFlowID;
    @Column
    private Boolean isDefault;
    @Column
    private Integer businessType;

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(Integer accountCode) {
        this.accountCode = accountCode;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Integer getAccountClassificationID() {
        return accountClassificationID;
    }

    public void setAccountClassificationID(Integer accountClassificationID) {
        this.accountClassificationID = accountClassificationID;
    }

    public Integer getAccountTypeID() {
        return accountTypeID;
    }

    public void setAccountTypeID(Integer accountTypeID) {
        this.accountTypeID = accountTypeID;
    }

    public Integer getParentAccountID() {
        return parentAccountID;
    }

    public void setParentAccountID(Integer parentAccountID) {
        this.parentAccountID = parentAccountID;
    }

    public Integer getTaxCodeID() {
        return taxCodeID;
    }

    public void setTaxCodeID(Integer taxCodeID) {
        this.taxCodeID = taxCodeID;
    }

    public Integer getCashFlowID() {
        return cashFlowID;
    }

    public void setCashFlowID(Integer cashFlowID) {
        this.cashFlowID = cashFlowID;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Account account = (Account) o;
        return Objects.equals(accountID, account.accountID) &&
                Objects.equals(businessID, account.businessID) &&
                Objects.equals(accountCode, account.accountCode) &&
                Objects.equals(accountName, account.accountName) &&
                Objects.equals(accountClassificationID, account.accountClassificationID) &&
                Objects.equals(accountTypeID, account.accountTypeID) &&
                Objects.equals(parentAccountID, account.parentAccountID) &&
                Objects.equals(taxCodeID, account.taxCodeID) &&
                Objects.equals(cashFlowID, account.cashFlowID) &&
                Objects.equals(isDefault, account.isDefault) &&
                Objects.equals(businessType, account.businessType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), accountID, businessID, accountCode, accountName, accountClassificationID, accountTypeID, parentAccountID, taxCodeID, cashFlowID, isDefault, businessType);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountID=" + accountID +
                ", businessID=" + businessID +
                ", accountCode=" + accountCode +
                ", accountName='" + accountName + '\'' +
                ", accountClassificationID=" + accountClassificationID +
                ", accountTypeID=" + accountTypeID +
                ", parentAccountID=" + parentAccountID +
                ", taxCodeID=" + taxCodeID +
                ", cashFlowID=" + cashFlowID +
                ", isDefault=" + isDefault +
                ", businessType=" + businessType +
                '}';
    }
}
