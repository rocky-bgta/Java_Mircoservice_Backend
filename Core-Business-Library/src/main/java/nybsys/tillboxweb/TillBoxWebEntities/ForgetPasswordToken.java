package nybsys.tillboxweb.TillBoxWebEntities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
public class ForgetPasswordToken extends BaseEntity {
    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "forgetPasswordTokenID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    private Integer forgetPasswordTokenID;
    @Column
    private String userID;
    @Column
    private String token;
    @Column
    private Date validation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ForgetPasswordToken)) return false;
        if (!super.equals(o)) return false;
        ForgetPasswordToken that = (ForgetPasswordToken) o;
        return Objects.equals(getForgetPasswordTokenID(), that.getForgetPasswordTokenID()) &&
                Objects.equals(getUserID(), that.getUserID()) &&
                Objects.equals(getToken(), that.getToken()) &&
                Objects.equals(getValidation(), that.getValidation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getForgetPasswordTokenID(), getUserID(), getToken(), getValidation());
    }

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
    public String toString() {
        return "ForgetPasswordToken{" +
                "forgetPasswordTokenID=" + forgetPasswordTokenID +
                ", userID='" + userID + '\'' +
                ", token='" + token + '\'' +
                ", validation=" + validation +
                '}';
    }
}
