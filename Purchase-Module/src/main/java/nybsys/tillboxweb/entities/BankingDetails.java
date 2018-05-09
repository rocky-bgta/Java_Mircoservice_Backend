/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 22/02/2018
 * Time: 02:43
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import nybsys.tillboxweb.BaseModel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;
@Entity
public class BankingDetails extends BaseEntity {
    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "bankingDetailID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    private Integer bankingDetailID;
    @Column
    private Integer businessID;
    @Column
    private Integer accountType;
    @Column
    private String accountHolder;
    @Column
    private String accountNumber;
    @Column
    private String bankName;
    @Column
    private Integer bankCode;
    @Column
    private Integer contactID;
    @Column
    private Integer referenceType;
    @Column
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
        if (!(o instanceof BankingDetails)) return false;
        if (!super.equals(o)) return false;
        BankingDetails that = (BankingDetails) o;
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
        return "BankingDetails{" +
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
