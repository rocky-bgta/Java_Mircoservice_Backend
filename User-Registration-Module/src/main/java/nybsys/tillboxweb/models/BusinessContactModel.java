package nybsys.tillboxweb.models;
import nybsys.tillboxweb.BaseModel;
import org.omg.CORBA.INTERNAL;

import java.util.Objects;

public class BusinessContactModel extends BaseModel{

    private Integer businessContactID;
    private Integer businessID;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Boolean primaryContact;

    public Integer getBusinessContactID() {
        return businessContactID;
    }

    public void setBusinessContactID(Integer businessContactID) {
        this.businessContactID = businessContactID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getPrimaryContact() {
        return primaryContact;
    }

    public void setPrimaryContact(Boolean primaryContact) {
        this.primaryContact = primaryContact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BusinessContactModel)) return false;
        if (!super.equals(o)) return false;
        BusinessContactModel that = (BusinessContactModel) o;
        return Objects.equals(getBusinessContactID(), that.getBusinessContactID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getLastName(), that.getLastName()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getPhone(), that.getPhone()) &&
                Objects.equals(getPrimaryContact(), that.getPrimaryContact());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getBusinessContactID(), getBusinessID(), getFirstName(), getLastName(), getEmail(), getPhone(), getPrimaryContact());
    }

    @Override
    public String toString() {
        return "BusinessContactModel{" +
                "businessContactID=" + businessContactID +
                ", businessID=" + businessID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", primaryContact=" + primaryContact +
                '}';
    }
}
