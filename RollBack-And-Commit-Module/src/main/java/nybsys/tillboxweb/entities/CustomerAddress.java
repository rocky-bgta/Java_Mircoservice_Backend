/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 12/03/2018
 * Time: 03:06
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
import java.util.Objects;

@Entity
public class CustomerAddress extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "customerAddressID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

    private Integer customerAddressID;
    @Column
    private Integer customerID;
    @Column
    private Integer addressType;
    @Column
    private String phone;
    @Column
    private String email;
    @Column
    private String state;
    @Column
    private String province;
    @Column
    private String zipCode;

    public Integer getCustomerAddressID() {
        return customerAddressID;
    }

    public void setCustomerAddressID(Integer customerAddressID) {
        this.customerAddressID = customerAddressID;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public Integer getAddressType() {
        return addressType;
    }

    public void setAddressType(Integer addressType) {
        this.addressType = addressType;
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
        if (!(o instanceof CustomerAddress)) return false;
        if (!super.equals(o)) return false;
        CustomerAddress that = (CustomerAddress) o;
        return Objects.equals(getCustomerAddressID(), that.getCustomerAddressID()) &&
                Objects.equals(getCustomerID(), that.getCustomerID()) &&
                Objects.equals(getAddressType(), that.getAddressType()) &&
                Objects.equals(getPhone(), that.getPhone()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getState(), that.getState()) &&
                Objects.equals(getProvince(), that.getProvince()) &&
                Objects.equals(getZipCode(), that.getZipCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCustomerAddressID(), getCustomerID(), getAddressType(), getPhone(), getEmail(), getState(), getProvince(), getZipCode());
    }

    @Override
    public String toString() {
        return "CustomerAddress{" +
                "customerAddressID=" + customerAddressID +
                ", customerID=" + customerID +
                ", addressType=" + addressType +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", state='" + state + '\'' +
                ", province='" + province + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}