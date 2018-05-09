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

    @Column
    @NotNull(message = "Business ID No  cannot be empty")
    private Integer businessID;

    @Column
    @NotNull(message = "First name  cannot be empty")
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Boolean primaryContact;

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
        if (!(o instanceof BusinessAddress)) return false;
        if (!super.equals(o)) return false;
        BusinessAddress that = (BusinessAddress) o;
        return Objects.equals(getAddressID(), that.getAddressID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getLastName(), that.getLastName()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getPhone(), that.getPhone()) &&
                Objects.equals(getPrimaryContact(), that.getPrimaryContact());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAddressID(), getBusinessID(), getFirstName(), getLastName(), getEmail(), getPhone(), getPrimaryContact());
    }

    @Override
    public String toString() {
        return "BusinessAddress{" +
                "addressID=" + addressID +
                ", businessID=" + businessID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", primaryContact=" + primaryContact +
                '}';
    }
}
