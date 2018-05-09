package nybsys.tillboxweb.models;

import nybsys.tillboxweb.TillBoxWebModels.UserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VMUserModel {
    private Integer accessRightID;
    private String firstName;
    private String lastName;
    private Integer status;
    private String tokenInvitation;
    private UserModel userModel = new UserModel();
    private Boolean isBusinessOwner = false;

    public Integer getAccessRightID() {
        return accessRightID;
    }

    public void setAccessRightID(Integer accessRightID) {
        this.accessRightID = accessRightID;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTokenInvitation() {
        return tokenInvitation;
    }

    public void setTokenInvitation(String tokenInvitation) {
        this.tokenInvitation = tokenInvitation;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public Boolean getBusinessOwner() {
        return isBusinessOwner;
    }

    public void setBusinessOwner(Boolean businessOwner) {
        isBusinessOwner = businessOwner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VMUserModel)) return false;
        VMUserModel that = (VMUserModel) o;
        return Objects.equals(getAccessRightID(), that.getAccessRightID()) &&
                Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getLastName(), that.getLastName()) &&
                Objects.equals(getStatus(), that.getStatus()) &&
                Objects.equals(getTokenInvitation(), that.getTokenInvitation()) &&
                Objects.equals(getUserModel(), that.getUserModel()) &&
                Objects.equals(isBusinessOwner, that.isBusinessOwner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccessRightID(), getFirstName(), getLastName(), getStatus(), getTokenInvitation(), getUserModel(), isBusinessOwner);
    }

    @Override
    public String toString() {
        return "VMUserModel{" +
                "accessRightID=" + accessRightID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", status=" + status +
                ", tokenInvitation='" + tokenInvitation + '\'' +
                ", userModel=" + userModel +
                ", isBusinessOwner=" + isBusinessOwner +
                '}';
    }
}
