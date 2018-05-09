package nybsys.tillboxweb.TillBoxWebEntities;



import nybsys.tillboxweb.BaseEntity;
import nybsys.tillboxweb.constant.TillBoxAppConstant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class User extends BaseEntity {




    @Id
    @Column
    @NotNull
    @Pattern(regexp = TillBoxAppConstant.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Email(message = "Email should be valid")
    private String userID;

    @Column
    private String password;

	@Column
    private String name;

    @Column
    private String surname;

    @Column
    private String cellPhone;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(getUserID(), user.getUserID()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                Objects.equals(getName(), user.getName()) &&
                Objects.equals(getSurname(), user.getSurname()) &&
                Objects.equals(getCellPhone(), user.getCellPhone());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUserID(), getPassword(), getName(), getSurname(), getCellPhone());
    }

    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", cellPhone='" + cellPhone + '\'' +
                '}';
    }
}