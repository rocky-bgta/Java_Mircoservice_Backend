package nybsys.tillboxweb.entities;
import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Objects;


@Entity
public class Address extends BaseEntity{

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

    @Column
    @NotNull(message = "Serial No  cannot be empty")
    private Integer businessID;

    @Column
    @NotNull(message = "Type cannot be empty")
    private Integer typeID;

    @Column
    @NotNull(message = "Address  cannot be empty")
    private String address;


    private String suburb;


    private String state;


    private String postCode;


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

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
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
        if (!(o instanceof Address)) return false;
        if (!super.equals(o)) return false;
        Address address1 = (Address) o;
        return Objects.equals(getAddressID(), address1.getAddressID()) &&
                Objects.equals(getBusinessID(), address1.getBusinessID()) &&
                Objects.equals(getTypeID(), address1.getTypeID()) &&
                Objects.equals(getAddress(), address1.getAddress()) &&
                Objects.equals(getSuburb(), address1.getSuburb()) &&
                Objects.equals(getState(), address1.getState()) &&
                Objects.equals(getPostCode(), address1.getPostCode()) &&
                Objects.equals(getCountry(), address1.getCountry());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAddressID(), getBusinessID(), getTypeID(), getAddress(), getSuburb(), getState(), getPostCode(), getCountry());
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressID=" + addressID +
                ", businessID=" + businessID +
                ", typeID=" + typeID +
                ", address='" + address + '\'' +
                ", suburb='" + suburb + '\'' +
                ", state='" + state + '\'' +
                ", postCode='" + postCode + '\'' +
                ", country=" + country +
                '}';
    }
}
