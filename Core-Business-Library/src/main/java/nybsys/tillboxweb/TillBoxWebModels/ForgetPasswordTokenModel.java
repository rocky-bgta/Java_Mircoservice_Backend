package nybsys.tillboxweb.TillBoxWebModels;

import nybsys.tillboxweb.BaseModel;

import java.util.Date;
import java.util.Objects;

public class ForgetPasswordTokenModel extends BaseModel {
    private Integer forgetPasswordTokenID;
    private String userID;
    private String token;
    private Date validation;

    public Integer getForgetPasswordTokenID() {
        return forgetPasswordTokenID;
    }

    public void setForgetPasswordTokenID(Integer forgetPasswordTokenID) {
        this.forgetPasswordTokenID = forgetPasswordTokenID;
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

    public Date getValidation() {
        return validation;
    }

    public void setValidation(Date validation) {
        this.validation = validation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ForgetPasswordTokenModel)) return false;
        if (!super.equals(o)) return false;
        ForgetPasswordTokenModel that = (ForgetPasswordTokenModel) o;
        return Objects.equals(getForgetPasswordTokenID(), that.getForgetPasswordTokenID()) &&
                Objects.equals(getUserID(), that.getUserID()) &&
                Objects.equals(getToken(), that.getToken()) &&
                Objects.equals(getValidation(), that.getValidation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getForgetPasswordTokenID(), getUserID(), getToken(), getValidation());
    }

    @Override
    public String toString() {
        return "ForgetPasswordTokenModel{" +
                "forgetPasswordTokenID=" + forgetPasswordTokenID +
                ", userID='" + userID + '\'' +
                ", token='" + token + '\'' +
                ", validation=" + validation +
                '}';
    }
}
