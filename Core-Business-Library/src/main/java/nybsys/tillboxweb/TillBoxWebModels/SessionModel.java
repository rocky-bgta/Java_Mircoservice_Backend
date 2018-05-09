package nybsys.tillboxweb.TillBoxWebModels;

import nybsys.tillboxweb.BaseModel;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

public class SessionModel extends BaseModel {

    private Integer sessionID;

    @NotNull(message = "businessDBName cannot be empty")
    private String businessDBName;

    @NotNull(message = "businessID cannot be empty")
    private Integer businessID;

    @NotNull(message = "userID cannot be empty")
    @Email(message = "Email should be valid")
    private String userID;

    @NotNull(message = "token cannot be empty")
    private String token;

    @NotNull(message = "start cannot be empty")
    private Date start;

    @NotNull(message = "end cannot be empty")
    private Date end;

    @NotNull(message = "duration cannot be empty")
    private Integer duration;

    @NotNull(message = "loginStatus cannot be empty")
    private Integer loginStatus;

    private String refreshToken;

    private Integer currentCurrencyID;

    public Integer getCurrentCurrencyID() {
        return currentCurrencyID;
    }

    public void setCurrentCurrencyID(Integer currentCurrencyID) {
        this.currentCurrencyID = currentCurrencyID;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SessionModel)) return false;
        SessionModel that = (SessionModel) o;
        return Objects.equals(getSessionID(), that.getSessionID()) &&
                Objects.equals(getBusinessDBName(), that.getBusinessDBName()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getUserID(), that.getUserID()) &&
                Objects.equals(getToken(), that.getToken()) &&
                Objects.equals(getStart(), that.getStart()) &&
                Objects.equals(getEnd(), that.getEnd()) &&
                Objects.equals(getDuration(), that.getDuration()) &&
                Objects.equals(getLoginStatus(), that.getLoginStatus()) &&
                Objects.equals(getRefreshToken(), that.getRefreshToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSessionID(), getBusinessDBName(), getBusinessID(), getUserID(), getToken(), getStart(), getEnd(), getDuration(), getLoginStatus(), getRefreshToken());
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
                '}';
    }
}
