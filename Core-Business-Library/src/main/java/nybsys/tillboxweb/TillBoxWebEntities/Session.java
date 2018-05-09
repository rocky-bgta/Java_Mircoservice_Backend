package nybsys.tillboxweb.TillBoxWebEntities;

import nybsys.tillboxweb.BaseEntity;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Entity
public class Session extends BaseEntity{

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "sessionID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    private Integer sessionID;

    @Column
    private String businessDBName;

    @Column
    @NotNull(message = "Business ID cannot be empty")
    private Integer businessID;

    @Id
    @Column
    @NotNull
    @Pattern(regexp = TillBoxAppConstant.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Email(message = "Email should be valid")
    private String userID;

    @Column
    @NotNull(message = "token cannot be empty")
    private String token;

    @Column
    @NotNull(message = "start cannot be empty")
    private Date start;

    @Column
    @NotNull(message = "Business end cannot be empty")
    private Date end;

    @Column
    @NotNull(message = "duration cannot be empty")
    private Integer duration;

    @Column
    @NotNull(message = "login status cannot be empty")
    private Integer loginStatus;

    @Column
    private String refreshToken;

    @Column
    private Integer currentCurrencyID;

    public Integer getSessionID() {
        return sessionID;
    }

    public void setSessionID(Integer sessionID) {
        this.sessionID = sessionID;
    }

    public String getBusinessDBName() {
        return businessDBName;
    }

    public void setBusinessDBName(String businessDBName) {
        this.businessDBName = businessDBName;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Integer loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Integer getCurrentCurrencyID() {
        return currentCurrencyID;
    }

    public void setCurrentCurrencyID(Integer currentCurrencyID) {
        this.currentCurrencyID = currentCurrencyID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Session)) return false;
        if (!super.equals(o)) return false;
        Session session = (Session) o;
        return Objects.equals(getSessionID(), session.getSessionID()) &&
                Objects.equals(getBusinessDBName(), session.getBusinessDBName()) &&
                Objects.equals(getBusinessID(), session.getBusinessID()) &&
                Objects.equals(getUserID(), session.getUserID()) &&
                Objects.equals(getToken(), session.getToken()) &&
                Objects.equals(getStart(), session.getStart()) &&
                Objects.equals(getEnd(), session.getEnd()) &&
                Objects.equals(getDuration(), session.getDuration()) &&
                Objects.equals(getLoginStatus(), session.getLoginStatus()) &&
                Objects.equals(getRefreshToken(), session.getRefreshToken()) &&
                Objects.equals(getCurrentCurrencyID(), session.getCurrentCurrencyID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSessionID(), getBusinessDBName(), getBusinessID(), getUserID(), getToken(), getStart(), getEnd(), getDuration(), getLoginStatus(), getRefreshToken(), getCurrentCurrencyID());
    }

    @Override
    public String toString() {
        return "Session{" +
                "sessionID=" + sessionID +
                ", businessDBName='" + businessDBName + '\'' +
                ", businessID=" + businessID +
                ", userID='" + userID + '\'' +
                ", token='" + token + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", duration=" + duration +
                ", loginStatus=" + loginStatus +
                ", refreshToken='" + refreshToken + '\'' +
                ", currentCurrencyID=" + currentCurrencyID +
                '}';
    }
}
