/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 12/03/2018
 * Time: 02:42
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "pkID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

    private Integer customerID;
    @Column
    private Integer businessID;
    @Column
    private Integer customerCategoryID;
    @Column
    private Integer customerTypeID;
    @Column
    private String customerName;
    @Column
    private String address;
    @Column
    private String phone;
    @Column
    private String email;
    @Column
    private Double openingBalanceAmount;
    @Column
    private Date openingBalanceDate;
    @Column
    private Integer preferredCurrencyID;
    @Column
    private Boolean autoAllocateReceipt;
    @Column
    private Boolean acceptElectronicInvoice;
    @Column
    private Boolean viewInvoiceOnline;

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getCustomerCategoryID() {
        return customerCategoryID;
    }

    public void setCustomerCategoryID(Integer customerCategoryID) {
        this.customerCategoryID = customerCategoryID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getOpeningBalanceAmount() {
        return openingBalanceAmount;
    }

    public void setOpeningBalanceAmount(Double openingBalanceAmount) {
        this.openingBalanceAmount = openingBalanceAmount;
    }

    public Integer getCustomerTypeID() {
        return customerTypeID;
    }

    public void setCustomerTypeID(Integer customerTypeID) {
        this.customerTypeID = customerTypeID;
    }

    public Date getOpeningBalanceDate() {
        return openingBalanceDate;
    }

    public void setOpeningBalanceDate(Date openingBalanceDate) {
        this.openingBalanceDate = openingBalanceDate;
    }

    public Integer getPreferredCurrencyID() {
        return preferredCurrencyID;
    }

    public void setPreferredCurrencyID(Integer preferredCurrencyID) {
        this.preferredCurrencyID = preferredCurrencyID;
    }

    public Boolean getAutoAllocateReceipt() {
        return autoAllocateReceipt;
    }

    public void setAutoAllocateReceipt(Boolean autoAllocateReceipt) {
        this.autoAllocateReceipt = autoAllocateReceipt;
    }

    public Boolean getAcceptElectronicInvoice() {
        return acceptElectronicInvoice;
    }

    public void setAcceptElectronicInvoice(Boolean acceptElectronicInvoice) {
        this.acceptElectronicInvoice = acceptElectronicInvoice;
    }

    public Boolean getViewInvoiceOnline() {
        return viewInvoiceOnline;
    }

    public void setViewInvoiceOnline(Boolean viewInvoiceOnline) {
        this.viewInvoiceOnline = viewInvoiceOnline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        if (!super.equals(o)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(getCustomerID(), customer.getCustomerID()) &&
                Objects.equals(getBusinessID(), customer.getBusinessID()) &&
                Objects.equals(getCustomerCategoryID(), customer.getCustomerCategoryID()) &&
                Objects.equals(getCustomerTypeID(), customer.getCustomerTypeID()) &&
                Objects.equals(getCustomerName(), customer.getCustomerName()) &&
                Objects.equals(getAddress(), customer.getAddress()) &&
                Objects.equals(getPhone(), customer.getPhone()) &&
                Objects.equals(getEmail(), customer.getEmail()) &&
                Objects.equals(getOpeningBalanceAmount(), customer.getOpeningBalanceAmount()) &&
                Objects.equals(getOpeningBalanceDate(), customer.getOpeningBalanceDate()) &&
                Objects.equals(getPreferredCurrencyID(), customer.getPreferredCurrencyID()) &&
                Objects.equals(getAutoAllocateReceipt(), customer.getAutoAllocateReceipt()) &&
                Objects.equals(getAcceptElectronicInvoice(), customer.getAcceptElectronicInvoice()) &&
                Objects.equals(getViewInvoiceOnline(), customer.getViewInvoiceOnline());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCustomerID(), getBusinessID(), getCustomerCategoryID(), getCustomerTypeID(), getCustomerName(), getAddress(), getPhone(), getEmail(), getOpeningBalanceAmount(), getOpeningBalanceDate(), getPreferredCurrencyID(), getAutoAllocateReceipt(), getAcceptElectronicInvoice(), getViewInvoiceOnline());
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerID=" + customerID +
                ", businessID=" + businessID +
                ", customerCategoryID=" + customerCategoryID +
                ", customerTypeID=" + customerTypeID +
                ", customerName='" + customerName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", openingBalanceAmount=" + openingBalanceAmount +
                ", openingBalanceDate=" + openingBalanceDate +
                ", preferredCurrencyID=" + preferredCurrencyID +
                ", autoAllocateReceipt=" + autoAllocateReceipt +
                ", acceptElectronicInvoice=" + acceptElectronicInvoice +
                ", viewInvoiceOnline=" + viewInvoiceOnline +
                '}';
    }
}