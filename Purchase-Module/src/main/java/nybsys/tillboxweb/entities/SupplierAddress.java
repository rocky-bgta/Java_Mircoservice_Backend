/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 22/02/2018
 * Time: 02:31
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
public class SupplierAddress extends BaseEntity {
    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "supplierAddressID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    private Integer supplierAddressID;
    @Column
    private Integer supplierID;
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

    public Integer getSupplierAddressID() {
        return supplierAddressID;
    }

    public void setSupplierAddressID(Integer supplierAddressID) {
        this.supplierAddressID = supplierAddressID;
    }

    public Integer getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(Integer supplierID) {
        this.supplierID = supplierID;
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
        if (!(o instanceof SupplierAddress)) return false;
        if (!super.equals(o)) return false;
        SupplierAddress that = (SupplierAddress) o;
        return Objects.equals(getSupplierAddressID(), that.getSupplierAddressID()) &&
                Objects.equals(getSupplierID(), that.getSupplierID()) &&
                Objects.equals(getAddressType(), that.getAddressType()) &&
                Objects.equals(getPhone(), that.getPhone()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getState(), that.getState()) &&
                Objects.equals(getProvince(), that.getProvince()) &&
                Objects.equals(getZipCode(), that.getZipCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSupplierAddressID(), getSupplierID(), getAddressType(), getPhone(), getEmail(), getState(), getProvince(), getZipCode());
    }

    @Override
    public String toString() {
        return "SupplierAddress{" +
                "supplierAddressID=" + supplierAddressID +
                ", supplierID=" + supplierID +
                ", addressType=" + addressType +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", state='" + state + '\'' +
                ", province='" + province + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
