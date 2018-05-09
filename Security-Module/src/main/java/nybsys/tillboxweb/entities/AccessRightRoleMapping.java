package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class AccessRightRoleMapping extends  BaseEntity{

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "accessRightRoleMappingID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    private Integer accessRightRoleMappingID;
    private Integer accessRightID;
    private Integer roleID;


    public Integer getAccessRightRoleMappingID() {
        return accessRightRoleMappingID;
    }

    public void setAccessRightRoleMappingID(Integer accessRightRoleMappingID) {
        this.accessRightRoleMappingID = accessRightRoleMappingID;
    }

    public Integer getAccessRightID() {
        return accessRightID;
    }

    public void setAccessRightID(Integer accessRightID) {
        this.accessRightID = accessRightID;
    }

    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccessRightRoleMapping)) return false;
        if (!super.equals(o)) return false;
        AccessRightRoleMapping that = (AccessRightRoleMapping) o;
        return Objects.equals(getAccessRightRoleMappingID(), that.getAccessRightRoleMappingID()) &&
                Objects.equals(getAccessRightID(), that.getAccessRightID()) &&
                Objects.equals(getRoleID(), that.getRoleID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAccessRightRoleMappingID(), getAccessRightID(), getRoleID());
    }

    @Override
    public String toString() {
        return "AccessRightRoleMapping{" +
                "accessRightRoleMappingID=" + accessRightRoleMappingID +
                ", accessRightID=" + accessRightID +
                ", roleID=" + roleID +
                '}';
    }

}
