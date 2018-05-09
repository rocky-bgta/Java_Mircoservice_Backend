package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class AccessRight extends BaseEntity{
    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "accessRightID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    private Integer accessRightID;

    @Column
    @NotNull(message = "Name cannot be empty")
    private String name;

    @Column
    private String description;

    public Integer getAccessRightID() {
        return accessRightID;
    }

    public void setAccessRightID(Integer accessRightID) {
        this.accessRightID = accessRightID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccessRight)) return false;
        if (!super.equals(o)) return false;
        AccessRight that = (AccessRight) o;
        return Objects.equals(getAccessRightID(), that.getAccessRightID()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAccessRightID(), getName(), getDescription());
    }

    @Override
    public String toString() {
        return "AccessRight{" +
                "accessRightID=" + accessRightID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
