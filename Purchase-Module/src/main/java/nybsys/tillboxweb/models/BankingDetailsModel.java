/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 22/02/2018
 * Time: 02:43
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class BankingDetailsModel extends BaseModel {
    private Integer bankingDetailID;
    private Integer businessID;
    private Integer accountType;
    private String accountHolder;
    private String accountNumber;
    private String bankName;
    private Integer bankCode;
    private Integer contactID;
    private Integer referenceType;
    private Integer referenceID;

    public Integer getBankingDetailID() {
        return bankingDetailID;
    }

    public void setBankingDetailID(Integer bankingDetailID) {
        this.bankingDetailID = bankingDetailID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Integer getBankCode() {
        return bankCode;
    }

    public void setBankCode(Integer bankCode) {
        this.bankCode = bankCode;
    }

    public Integer getContactID() {
        return contactID;
    }

    public void setContactID(Integer contactID) {
        this.contactID = contactID;
    }

    public Integer getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(Integer referenceType) {
        this.referenceType = referenceType;
    }

    public Integer getReferenceID() {
        return referenceID;
    }

    public void setReferenceID(Integer referenceID) {
        this.referenceID = referenceID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankingDetailsModel)) return false;
        if (!super.equals(o)) return false;
        BankingDetailsModel that = (BankingDetailsModel) o;
        return Objects.equals(getBankingDetailID(), that.getBankingDetailID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getAccountType(), that.getAccountType()) &&
                Objects.equals(getAccountHolder(), that.getAccountHolder()) &&
                Objects.equals(getAccountNumber(), that.getAccountNumber()) &&
                Objects.equals(getBankName(), that.getBankName()) &&
                Objects.equals(getBankCode(), that.getBankCode()) &&
                Objects.equals(getContactID(), that.getContactID()) &&
                Objects.equals(getReferenceType(), that.getReferenceType()) &&
                Objects.equals(getReferenceID(), that.getReferenceID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getBankingDetailID(), getBusinessID(), getAccountType(), getAccountHolder(), getAccountNumber(), getBankName(), getBankCode(), getContactID(), getReferenceType(), getReferenceID());
    }

    @Override
    public String toString() {
        return "BankingDetailsModel{" +
                "bankingDetailID=" + bankingDetailID +
                ", businessID=" + businessID +
                ", accountType=" + accountType +
                ", accountHolder='" + accountHolder + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankCode=" + bankCode +
                ", contactID=" + contactID +
                ", referenceType=" + referenceType +
                ", referenceID=" + referenceID +
                '}';
    }
}
