package nybsys.tillboxweb.coreEntities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class AccountClassification extends BaseEntity{
    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "accountClassificationID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    @Column
    @NotNull
    private Integer accountClassificationID;
    @Column
    private String name;
    @Column
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
        if (!(o instanceof AccountClassification)) return false;
        if (!super.equals(o)) return false;
        AccountClassification that = (AccountClassification) o;
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
        return "AccountClassification{" +
                "accountClassificationID=" + accountClassificationID +
                ", name='" + name + '\'' +
                ", code=" + code +
                '}';
    }
}
