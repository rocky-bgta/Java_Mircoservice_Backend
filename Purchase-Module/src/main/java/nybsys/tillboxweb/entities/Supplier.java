/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 22/02/2018
 * Time: 11:44
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
public class Supplier extends BaseEntity {
    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "supplierID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    private Integer supplierID;
    @Column
    private Integer businessID;
    @Column
    private Integer supplierCategoryID;
    @Column
    private Integer supplierTypeID;
    @Column
    private String supplierName;
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
    private Boolean autoAllocatePayment;
    @Column
    private Integer preferredCurrencyID;

    public Integer getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(Integer supplierID) {
        this.supplierID = supplierID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getSupplierCategoryID() {
        return supplierCategoryID;
    }

    public void setSupplierCategoryID(Integer supplierCategoryID) {
        this.supplierCategoryID = supplierCategoryID;
    }

    public Integer getSupplierTypeID() {
        return supplierTypeID;
    }

    public void setSupplierTypeID(Integer supplierTypeID) {
        this.supplierTypeID = supplierTypeID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    public Date getOpeningBalanceDate() {
        return openingBalanceDate;
    }

    public void setOpeningBalanceDate(Date openingBalanceDate) {
        this.openingBalanceDate = openingBalanceDate;
    }

    public Boolean getAutoAllocatePayment() {
        return autoAllocatePayment;
    }

    public void setAutoAllocatePayment(Boolean autoAllocatePayment) {
        this.autoAllocatePayment = autoAllocatePayment;
    }

    public Integer getPreferredCurrencyID() {
        return preferredCurrencyID;
    }

    public void setPreferredCurrencyID(Integer preferredCurrencyID) {
        this.preferredCurrencyID = preferredCurrencyID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Supplier)) return false;
        if (!super.equals(o)) return false;
        Supplier supplier = (Supplier) o;
        return Objects.equals(getSupplierID(), supplier.getSupplierID()) &&
                Objects.equals(getBusinessID(), supplier.getBusinessID()) &&
                Objects.equals(getSupplierCategoryID(), supplier.getSupplierCategoryID()) &&
                Objects.equals(getSupplierTypeID(), supplier.getSupplierTypeID()) &&
                Objects.equals(getSupplierName(), supplier.getSupplierName()) &&
                Objects.equals(getAddress(), supplier.getAddress()) &&
                Objects.equals(getPhone(), supplier.getPhone()) &&
                Objects.equals(getEmail(), supplier.getEmail()) &&
                Objects.equals(getOpeningBalanceAmount(), supplier.getOpeningBalanceAmount()) &&
                Objects.equals(getOpeningBalanceDate(), supplier.getOpeningBalanceDate()) &&
                Objects.equals(getAutoAllocatePayment(), supplier.getAutoAllocatePayment()) &&
                Objects.equals(getPreferredCurrencyID(), supplier.getPreferredCurrencyID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSupplierID(), getBusinessID(), getSupplierCategoryID(), getSupplierTypeID(), getSupplierName(), getAddress(), getPhone(), getEmail(), getOpeningBalanceAmount(), getOpeningBalanceDate(), getAutoAllocatePayment(), getPreferredCurrencyID());
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supplierID=" + supplierID +
                ", businessID=" + businessID +
                ", supplierCategoryID=" + supplierCategoryID +
                ", supplierTypeID=" + supplierTypeID +
                ", supplierName='" + supplierName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", openingBalanceAmount=" + openingBalanceAmount +
                ", openingBalanceDate=" + openingBalanceDate +
                ", autoAllocatePayment=" + autoAllocatePayment +
                ", preferredCurrencyID=" + preferredCurrencyID +
                '}';
    }
}
