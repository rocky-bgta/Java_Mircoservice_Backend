/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 22/02/2018
 * Time: 02:46
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
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class AddressType extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "addressTypeID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    @Column
    @NotNull
    private Integer addressTypeID;
    @Column
    private String name;
    @Column
    private Integer referenceType;

    public Integer getAddressTypeID() {
        return addressTypeID;
    }

    public void setAddressTypeID(Integer addressTypeID) {
        this.addressTypeID = addressTypeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(Integer referenceType) {
        this.referenceType = referenceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddressType)) return false;
        if (!super.equals(o)) return false;
        AddressType that = (AddressType) o;
        return Objects.equals(getAddressTypeID(), that.getAddressTypeID()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getReferenceType(), that.getReferenceType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAddressTypeID(), getName(), getReferenceType());
    }

    @Override
    public String toString() {
        return "AddressType{" +
                "addressTypeID=" + addressTypeID +
                ", name='" + name + '\'' +
                ", referenceType=" + referenceType +
                '}';
    }
}
