/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 23/02/2018
 * Time: 10:57
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import nybsys.tillboxweb.models.SupplierContactModel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class SupplierContact extends BaseEntity {
    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "supplierContactID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    private Integer supplierContactID;
    @Column
    private Integer supplierID;
    @Column
    private Integer contactTypeID;
    @Column
    private String name;
    @Column
    private String phone;
    @Column
    private String email;
    @Column
    private String address;
    @Column
    private String state;
    @Column
    private String province;
    @Column
    private String zipCode;

    public Integer getSupplierContactID() {
        return supplierContactID;
    }

    public void setSupplierContactID(Integer supplierContactID) {
        this.supplierContactID = supplierContactID;
    }

    public Integer getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(Integer supplierID) {
        this.supplierID = supplierID;
    }

    public Integer getContactTypeID() {
        return contactTypeID;
    }

    public void setContactTypeID(Integer contactTypeID) {
        this.contactTypeID = contactTypeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SupplierContactModel)) return false;
        if (!super.equals(o)) return false;
        SupplierContactModel that = (SupplierContactModel) o;
        return Objects.equals(getSupplierContactID(), that.getSupplierContactID()) &&
                Objects.equals(getSupplierID(), that.getSupplierID()) &&
                Objects.equals(getContactTypeID(), that.getContactTypeID()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getPhone(), that.getPhone()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getAddress(), that.getAddress()) &&
                Objects.equals(getState(), that.getState()) &&
                Objects.equals(getProvince(), that.getProvince()) &&
                Objects.equals(getZipCode(), that.getZipCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSupplierContactID(), getSupplierID(), getContactTypeID(), getName(), getPhone(), getEmail(), getAddress(), getState(), getProvince(), getZipCode());
    }

    @Override
    public String toString() {
        return "SupplierContactModel{" +
                "supplierContactID=" + supplierContactID +
                ", supplierID=" + supplierID +
                ", contactTypeID=" + contactTypeID +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", state='" + state + '\'' +
                ", province='" + province + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
