/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 13/03/2018
 * Time: 4:41
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModelWithCurrency;

import java.util.Date;
import java.util.Objects;

public class CustomerWriteOffModel extends BaseModelWithCurrency{
    private Integer customerWriteOffID;
    private Integer businessID;
    private Date date;
    private Integer customerID;
    private String docNumber;
    private String writeOffNumber;
    private Integer vatType;
    private Double totalVAT;
    private Double totalAmount;
    private Double unAllocatedAmount;
    private Integer accountID;
    private String note;
    private String description;

    public Integer getCustomerWriteOffID() {
        return customerWriteOffID;
    }

    public void setCustomerWriteOffID(Integer customerWriteOffID) {
        this.customerWriteOffID = customerWriteOffID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getWriteOffNumber() {
        return writeOffNumber;
    }

    public void setWriteOffNumber(String writeOffNumber) {
        this.writeOffNumber = writeOffNumber;
    }

    public Integer getVatType() {
        return vatType;
    }

    public void setVatType(Integer vatType) {
        this.vatType = vatType;
    }

    public Double getTotalVAT() {
        return totalVAT;
    }

    public void setTotalVAT(Double totalVAT) {
        this.totalVAT = totalVAT;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getUnAllocatedAmount() {
        return unAllocatedAmount;
    }

    public void setUnAllocatedAmount(Double unAllocatedAmount) {
        this.unAllocatedAmount = unAllocatedAmount;
    }

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerWriteOffModel)) return false;
        if (!super.equals(o)) return false;
        CustomerWriteOffModel that = (CustomerWriteOffModel) o;
        return Objects.equals(getCustomerWriteOffID(), that.getCustomerWriteOffID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getCustomerID(), that.getCustomerID()) &&
                Objects.equals(getDocNumber(), that.getDocNumber()) &&
                Objects.equals(getWriteOffNumber(), that.getWriteOffNumber()) &&
                Objects.equals(getVatType(), that.getVatType()) &&
                Objects.equals(getTotalVAT(), that.getTotalVAT()) &&
                Objects.equals(getTotalAmount(), that.getTotalAmount()) &&
                Objects.equals(getUnAllocatedAmount(), that.getUnAllocatedAmount()) &&
                Objects.equals(getAccountID(), that.getAccountID()) &&
                Objects.equals(getNote(), that.getNote()) &&
                Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCustomerWriteOffID(), getBusinessID(), getDate(), getCustomerID(), getDocNumber(), getWriteOffNumber(), getVatType(), getTotalVAT(), getTotalAmount(), getUnAllocatedAmount(), getAccountID(), getNote(), getDescription());
    }

    @Override
    public String toString() {
        return "CustomerWriteOffModel{" +
                "customerWriteOffID=" + customerWriteOffID +
                ", businessID=" + businessID +
                ", date=" + date +
                ", customerID=" + customerID +
                ", docNumber='" + docNumber + '\'' +
                ", writeOffNumber='" + writeOffNumber + '\'' +
                ", vatType=" + vatType +
                ", totalVAT=" + totalVAT +
                ", totalAmount=" + totalAmount +
                ", unAllocatedAmount=" + unAllocatedAmount +
                ", accountID=" + accountID +
                ", note='" + note + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
