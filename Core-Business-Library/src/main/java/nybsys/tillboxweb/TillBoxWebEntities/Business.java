package nybsys.tillboxweb.TillBoxWebEntities;


import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigInteger;
import java.util.Objects;

@Entity
public class Business extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "businessID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    private Integer businessID;

    @Column
    //@NotNull(message = "Serial No  cannot be empty")
    private BigInteger serialNo;

    @Column
    //@NotNull(message = "Product Type ID No  cannot be empty")
    private Integer productTypeID;

    //@Column
    //@Size(min = 3, max = 100, message = "First name must be between 3 and 100 characters")
    private String businessName;

    //@Column
    //@NotNull(message = "Product Type ID No  cannot be empty")
    private Integer businessTypeID;

    //@Column
    //@NotNull(message = "businessDBName cannot be empty")
    private String businessDBName;

    @Column
    private String phone;

    //@Column
    //@Email(message = "Email should be valid")
    private String email;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private Integer subscriptionStatus;

    @Column
    //@Email(message = "Email should be valid")
    private String owner;

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public BigInteger getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(BigInteger serialNo) {
        this.serialNo = serialNo;
    }

    public Integer getProductTypeID() {
        return productTypeID;
    }

    public void setProductTypeID(Integer productTypeID) {
        this.productTypeID = productTypeID;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Integer getBusinessTypeID() {
        return businessTypeID;
    }

    public void setBusinessTypeID(Integer businessTypeID) {
        this.businessTypeID = businessTypeID;
    }

    public String getBusinessDBName() {
        return businessDBName;
    }

    public void setBusinessDBName(String businessDBName) {
        this.businessDBName = businessDBName;
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

    public Integer getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(Integer subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Business)) return false;
        Business business = (Business) o;
        return Objects.equals(getBusinessID(), business.getBusinessID()) &&
                Objects.equals(getSerialNo(), business.getSerialNo()) &&
                Objects.equals(getProductTypeID(), business.getProductTypeID()) &&
                Objects.equals(getBusinessName(), business.getBusinessName()) &&
                Objects.equals(getBusinessTypeID(), business.getBusinessTypeID()) &&
                Objects.equals(getBusinessDBName(), business.getBusinessDBName()) &&
                Objects.equals(getPhone(), business.getPhone()) &&
                Objects.equals(getEmail(), business.getEmail()) &&
                Objects.equals(getFirstName(), business.getFirstName()) &&
                Objects.equals(getLastName(), business.getLastName()) &&
                Objects.equals(getSubscriptionStatus(), business.getSubscriptionStatus()) &&
                Objects.equals(getOwner(), business.getOwner());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getBusinessID(), getSerialNo(), getProductTypeID(), getBusinessName(), getBusinessTypeID(), getBusinessDBName(), getPhone(), getEmail(), getFirstName(), getLastName(), getSubscriptionStatus(), getOwner());
    }

    @Override
    public String toString() {
        return "Business{" +
                "businessID=" + businessID +
                ", serialNo=" + serialNo +
                ", productTypeID=" + productTypeID +
                ", businessName='" + businessName + '\'' +
                ", businessTypeID=" + businessTypeID +
                ", businessDBName='" + businessDBName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", subscriptionStatus=" + subscriptionStatus +
                ", owner='" + owner + '\'' +
                '}';
    }
}
