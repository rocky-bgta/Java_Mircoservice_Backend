package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class RolePrivilegeMappingModel extends BaseModel{


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
        if (!(o instanceof RolePrivilegeMappingModel)) return false;
        if (!super.equals(o)) return false;
        RolePrivilegeMappingModel that = (RolePrivilegeMappingModel) o;
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
        return "RolePrivilegeMappingModel{" +
                "rolePrivilegeMappingID=" + rolePrivilegeMappingID +
                ", roleID=" + roleID +
                ", privilegeID=" + privilegeID +
                '}';
    }
}
