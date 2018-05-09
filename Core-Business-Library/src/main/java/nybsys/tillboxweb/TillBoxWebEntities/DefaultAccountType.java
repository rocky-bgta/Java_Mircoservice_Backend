package nybsys.tillboxweb.TillBoxWebEntities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class DefaultAccountType extends BaseEntity {
    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "accountTypeID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    @Column
    @NotNull
    private Integer accountTypeID;
    @Column
    private Integer accountClassificationID;
    @Column
    private String typeName;
    @Column
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
        if (!(o instanceof DefaultAccountType)) return false;
        if (!super.equals(o)) return false;
        DefaultAccountType that = (DefaultAccountType) o;
        return Objects.equals(getAccountTypeID(), that.getAccountTypeID()) &&
                Objects.equals(getAccountClassificationID(), that.getAccountClassificationID()) &&
                Objects.equals(getTypeName(), that.getTypeName()) &&
                Objects.equals(getCode(), that.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAccountTypeID(), getAccountClassificationID(), getTypeName(), getCode());
    }

    @Override
    public String toString() {
        return "AccountType{" +
                "accountTypeID=" + accountTypeID +
                ", accountClassificationID=" + accountClassificationID +
                ", typeName='" + typeName + '\'' +
                ", code=" + code +
                '}';
    }
}
