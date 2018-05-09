/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 09-Jan-18
 * Time: 12:22 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */
package nybsys.tillboxweb.models;


import nybsys.tillboxweb.BaseModel;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class UserBusinessRightMapperModel extends BaseModel {

    private Integer userBusinessRightMapperID;

    @Email(message = "Email should be valid")
    private String userID;

    @Size(min = 0, max = 50, message = "First name must be between 0 and 50 characters")
    private String firstName;

    @Size(min = 0, max = 50, message = "First name must be between 0 and 50 characters")
    private String lastName;

    @NotNull(message = "Business ID cannot be null")
    private Integer businessID;

    @NotNull(message = "Access Right ID  cannot be null")

    private Integer accessRightID;

    @NotNull(message = "Business Status cannot be null")
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
        if (!(o instanceof UserBusinessRightMapperModel)) return false;
        UserBusinessRightMapperModel that = (UserBusinessRightMapperModel) o;
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

        return Objects.hash(getUserBusinessRightMapperID(), getUserID(), getFirstName(), getLastName(), getBusinessID(), getAccessRightID(), getBusinessStatus());
    }

    @Override
    public String toString() {
        return "UserBusinessRightMapperModel{" +
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
