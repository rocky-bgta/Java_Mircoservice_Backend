package nybsys.tillboxweb.TillBoxWebModels;

import nybsys.tillboxweb.BaseModel;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class UserModel extends BaseModel {

    @Email(message = "Email should be valid")
    private String userID;
    private String password;
    private String name;
    private String surname;
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
        if (!(o instanceof UserModel)) return false;
        if (!super.equals(o)) return false;
        UserModel userModel = (UserModel) o;
        return Objects.equals(getUserID(), userModel.getUserID()) &&
                Objects.equals(getPassword(), userModel.getPassword()) &&
                Objects.equals(getName(), userModel.getName()) &&
                Objects.equals(getSurname(), userModel.getSurname()) &&
                Objects.equals(getCellPhone(), userModel.getCellPhone());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUserID(), getPassword(), getName(), getSurname(), getCellPhone());
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "userID='" + userID + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", cellPhone='" + cellPhone + '\'' +
                '}';
    }
}
