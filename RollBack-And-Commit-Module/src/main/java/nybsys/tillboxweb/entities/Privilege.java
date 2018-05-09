package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
//@Table(indexes = {@Index(unique = true,columnList = "name",name = "i_name")})
public class Privilege extends BaseEntity{

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "privilegeID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    private Integer privilegeID;
    private Integer parentID;
    private String name;
    private String actionCode;
    private String showName;


    public Integer getPrivilegeID() {
        return privilegeID;
    }

    public void setPrivilegeID(Integer privilegeID) {
        this.privilegeID = privilegeID;
    }

    public Integer getParentID() {
        return parentID;
    }

    public void setParentID(Integer parentID) {
        this.parentID = parentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Privilege)) return false;
        if (!super.equals(o)) return false;
        Privilege privilege = (Privilege) o;
        return Objects.equals(getPrivilegeID(), privilege.getPrivilegeID()) &&
                Objects.equals(getParentID(), privilege.getParentID()) &&
                Objects.equals(getName(), privilege.getName()) &&
                Objects.equals(getActionCode(), privilege.getActionCode()) &&
                Objects.equals(getShowName(), privilege.getShowName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPrivilegeID(), getParentID(), getName(), getActionCode(), getShowName());
    }

    @Override
    public String toString() {
        return "Privilege{" +
                "privilegeID=" + privilegeID +
                ", parentID=" + parentID +
                ", name='" + name + '\'' +
                ", actionCode='" + actionCode + '\'' +
                ", showName='" + showName + '\'' +
                '}';
    }
}
