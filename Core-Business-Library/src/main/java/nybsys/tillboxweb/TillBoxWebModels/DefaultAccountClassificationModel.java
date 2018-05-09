package nybsys.tillboxweb.TillBoxWebModels;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class DefaultAccountClassificationModel extends BaseModel {
    private Integer accountClassificationID;
    private String name;
    private Integer code;

    public Integer getAccountClassificationID() {
        return accountClassificationID;
    }

    public void setAccountClassificationID(Integer accountClassificationID) {
        this.accountClassificationID = accountClassificationID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultAccountClassificationModel)) return false;
        if (!super.equals(o)) return false;
        DefaultAccountClassificationModel that = (DefaultAccountClassificationModel) o;
        return Objects.equals(getAccountClassificationID(), that.getAccountClassificationID()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getCode(), that.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAccountClassificationID(), getName(), getCode());
    }

    @Override
    public String toString() {
        return "AccountClassificationModel{" +
                "accountClassificationID=" + accountClassificationID +
                ", name='" + name + '\'' +
                ", code=" + code +
                '}';
    }
}
