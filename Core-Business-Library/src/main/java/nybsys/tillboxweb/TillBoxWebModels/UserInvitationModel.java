package nybsys.tillboxweb.TillBoxWebModels;
import nybsys.tillboxweb.BaseModel;

import java.util.Date;
import java.util.Objects;

public class UserInvitationModel extends BaseModel {

    private Integer userInvitationEntityID;
    private String userID;
    private String firstName;
    private String lastName;
    private String token;
    private Integer businessID;
    private Integer accessRightID;
    private Date expireDate;
    private Boolean done;


    public Integer getUserInvitationEntityID() {
        return userInvitationEntityID;
    }

    public void setUserInvitationEntityID(Integer userInvitationEntityID) {
        this.userInvitationEntityID = userInvitationEntityID;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserInvitationModel)) return false;
        if (!super.equals(o)) return false;
        UserInvitationModel that = (UserInvitationModel) o;
        return isDone() == that.isDone() &&
                Objects.equals(getUserInvitationEntityID(), that.getUserInvitationEntityID()) &&
                Objects.equals(getUserID(), that.getUserID()) &&
                Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getLastName(), that.getLastName()) &&
                Objects.equals(getToken(), that.getToken()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getAccessRightID(), that.getAccessRightID()) &&
                Objects.equals(getExpireDate(), that.getExpireDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUserInvitationEntityID(), getUserID(), getFirstName(), getLastName(), getToken(), getBusinessID(), getAccessRightID(), getExpireDate(), isDone());
    }

    @Override
    public String toString() {
        return "UserInvitationModel{" +
                "userInvitationEntityID=" + userInvitationEntityID +
                ", userID='" + userID + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", token='" + token + '\'' +
                ", businessID=" + businessID +
                ", accessRightID=" + accessRightID +
                ", expireDate=" + expireDate +
                ", done=" + done +
                '}';
    }
}
