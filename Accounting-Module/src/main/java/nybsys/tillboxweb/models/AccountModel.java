package nybsys.tillboxweb.models;


import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class AccountModel extends BaseModel {

    private Integer accountID;
    private Integer businessID;
    private Integer accountCode;
    private String accountName;
    private Integer accountClassificationID;
    private Integer accountTypeID;
    private Integer parentAccountID;
    private Integer taxCodeID;
    private Integer cashFlowID;
    private Boolean isDefault;
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
        AccountModel that = (AccountModel) o;
        return Objects.equals(accountID, that.accountID) &&
                Objects.equals(businessID, that.businessID) &&
                Objects.equals(accountCode, that.accountCode) &&
                Objects.equals(accountName, that.accountName) &&
                Objects.equals(accountClassificationID, that.accountClassificationID) &&
                Objects.equals(accountTypeID, that.accountTypeID) &&
                Objects.equals(parentAccountID, that.parentAccountID) &&
                Objects.equals(taxCodeID, that.taxCodeID) &&
                Objects.equals(cashFlowID, that.cashFlowID) &&
                Objects.equals(isDefault, that.isDefault) &&
                Objects.equals(businessType, that.businessType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), accountID, businessID, accountCode, accountName, accountClassificationID, accountTypeID, parentAccountID, taxCodeID, cashFlowID, isDefault, businessType);
    }

    @Override
    public String toString() {
        return "AccountModel{" +
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
