package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
public class UserInvitation extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "userInvitationEntityID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    private Integer userInvitationEntityID;
    @Column
    private String userID;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String token;
    @Column
    private Integer businessID;
    @Column
    private Integer accessRightID;
    @Column
    private Date expireDate;
    @Column
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
        if (!(o instanceof UserInvitation)) return false;
        if (!super.equals(o)) return false;
        UserInvitation that = (UserInvitation) o;
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
        return "UserInvitation{" +
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
