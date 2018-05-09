package nybsys.tillboxweb.models;
import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class AddressModel extends BaseModel {

    private Integer addressID;
    private Integer businessID;
    private Integer typeID;
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
        if (!(o instanceof AddressModel)) return false;
        if (!super.equals(o)) return false;
        AddressModel that = (AddressModel) o;
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
        return "AddressModel{" +
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
