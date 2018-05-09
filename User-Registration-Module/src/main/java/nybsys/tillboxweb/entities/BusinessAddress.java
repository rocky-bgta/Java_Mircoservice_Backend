package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class BusinessAddress extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "addressID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    private Integer addressID;
    private Integer businessID;
    private Integer typeID;
    private String address;
    private String suburb;
    private String state;
    private Integer postCode;
    private Integer country;

    public Integer getAddressID() {
        return addressID;
    }

    public void setAddressID(Integer addressID) {
        this.addressID = addressID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getTypeID() {
        return typeID;
    }

    public void setTypeID(Integer typeID) {
        this.typeID = typeID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getPostCode() {
        return postCode;
    }

    public void setPostCode(Integer postCode) {
        this.postCode = postCode;
    }

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BusinessAddress)) return false;
        if (!super.equals(o)) return false;
        BusinessAddress that = (BusinessAddress) o;
        return Objects.equals(getAddressID(), that.getAddressID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getTypeID(), that.getTypeID()) &&
                Objects.equals(getAddress(), that.getAddress()) &&
                Objects.equals(getSuburb(), that.getSuburb()) &&
                Objects.equals(getState(), that.getState()) &&
                Objects.equals(getPostCode(), that.getPostCode()) &&
                Objects.equals(getCountry(), that.getCountry());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAddressID(), getBusinessID(), getTypeID(), getAddress(), getSuburb(), getState(), getPostCode(), getCountry());
    }

    @Override
    public String toString() {
        return "BusinessAddress{" +
                "addressID=" + addressID +
                ", businessID=" + businessID +
                ", typeID=" + typeID +
                ", address='" + address + '\'' +
                ", suburb='" + suburb + '\'' +
                ", state='" + state + '\'' +
                ", postCode=" + postCode +
                ", country=" + country +
                '}';
    }
}
