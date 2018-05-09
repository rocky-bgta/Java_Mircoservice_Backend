package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class RolePrivilegeMapping extends BaseEntity{
    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "rolePrivilegeMappingID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    private Integer rolePrivilegeMappingID;
    private Integer roleID;
    private Integer privilegeID;


    public Integer getRolePrivilegeMappingID() {
        return rolePrivilegeMappingID;
    }

    public void setRolePrivilegeMappingID(Integer rolePrivilegeMappingID) {
        this.rolePrivilegeMappingID = rolePrivilegeMappingID;
    }

    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }

    public Integer getPrivilegeID() {
        return privilegeID;
    }

    public void setPrivilegeID(Integer privilegeID) {
        this.privilegeID = privilegeID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RolePrivilegeMapping)) return false;
        if (!super.equals(o)) return false;
        RolePrivilegeMapping that = (RolePrivilegeMapping) o;
        return Objects.equals(getRolePrivilegeMappingID(), that.getRolePrivilegeMappingID()) &&
                Objects.equals(getRoleID(), that.getRoleID()) &&
                Objects.equals(getPrivilegeID(), that.getPrivilegeID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getRolePrivilegeMappingID(), getRoleID(), getPrivilegeID());
    }

    @Override
    public String toString() {
        return "RolePrivilegeMapping{" +
                "rolePrivilegeMappingID=" + rolePrivilegeMappingID +
                ", roleID=" + roleID +
                ", privilegeID=" + privilegeID +
                '}';
    }
}
