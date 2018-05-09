package nybsys.tillboxweb.TillBoxWebModels;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class DefaultAccountTypeModel extends BaseModel{
    private Integer accountTypeID;
    private Integer accountClassificationID;
    private String typeName;
    private Integer code;

    public Integer getAccountTypeID() {
        return accountTypeID;
    }

    public void setAccountTypeID(Integer accountTypeID) {
        this.accountTypeID = accountTypeID;
    }

    public Integer getAccountClassificationID() {
        return accountClassificationID;
    }

    public void setAccountClassificationID(Integer accountClassificationID) {
        this.accountClassificationID = accountClassificationID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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
        if (!(o instanceof DefaultAccountTypeModel)) return false;
        DefaultAccountTypeModel that = (DefaultAccountTypeModel) o;
        return Objects.equals(getAccountTypeID(), that.getAccountTypeID()) &&
                Objects.equals(getAccountClassificationID(), that.getAccountClassificationID()) &&
                Objects.equals(getTypeName(), that.getTypeName()) &&
                Objects.equals(getCode(), that.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccountTypeID(), getAccountClassificationID(), getTypeName(), getCode());
    }

    @Override
    public String toString() {
        return "AccountTypeModel{" +
                "accountTypeID=" + accountTypeID +
                ", accountClassificationID=" + accountClassificationID +
                ", typeName='" + typeName + '\'' +
                ", code=" + code +
                '}';
    }
}
