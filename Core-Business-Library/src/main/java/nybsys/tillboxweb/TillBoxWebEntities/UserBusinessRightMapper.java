/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 09-Jan-18
 * Time: 11:28 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */
package nybsys.tillboxweb.TillBoxWebEntities;


import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class UserBusinessRightMapper extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "userBusinessRightMapperID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    private Integer userBusinessRightMapperID;

    @Column
    //@Email(message = "Email should be valid")
    private String userID;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    //@NotNull(message = "Business ID cannot be empty")
    private Integer businessID;

    @Column
    //@NotNull(message = "Access Right ID  cannot be empty")
    private Integer accessRightID;

    @Column
    //@NotNull(message = "Business Status cannot be empty")
    private Integer businessStatus;

    public Integer getUserBusinessRightMapperID() {
        return userBusinessRightMapperID;
    }

    public void setUserBusinessRightMapperID(Integer userBusinessRightMapperID) {
        this.userBusinessRightMapperID = userBusinessRightMapperID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getAccessRightID() {
        return accessRightID;
    }

    public void setAccessRightID(Integer accessRightID) {
        this.accessRightID = accessRightID;
    }

    public Integer getBusinessStatus() {
        return businessStatus;
    }

    public void setBusinessStatus(Integer businessStatus) {
        this.businessStatus = businessStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserBusinessRightMapper)) return false;
        if (!super.equals(o)) return false;
        UserBusinessRightMapper that = (UserBusinessRightMapper) o;
        return Objects.equals(getUserBusinessRightMapperID(), that.getUserBusinessRightMapperID()) &&
                Objects.equals(getUserID(), that.getUserID()) &&
                Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getLastName(), that.getLastName()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getAccessRightID(), that.getAccessRightID()) &&
                Objects.equals(getBusinessStatus(), that.getBusinessStatus());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getUserBusinessRightMapperID(), getUserID(), getFirstName(), getLastName(), getBusinessID(), getAccessRightID(), getBusinessStatus());
    }

    @Override
    public String toString() {
        return "UserBusinessRightMapper{" +
                "userBusinessRightMapperID=" + userBusinessRightMapperID +
                ", userID='" + userID + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", businessID='" + businessID + '\'' +
                ", accessRightID=" + accessRightID +
                ", businessStatus=" + businessStatus +
                '}';
    }
}
