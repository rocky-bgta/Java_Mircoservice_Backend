package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class AccessRightRoleMappingModel extends BaseModel{


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
        if (!(o instanceof AccessRightRoleMappingModel)) return false;
        if (!super.equals(o)) return false;
        AccessRightRoleMappingModel that = (AccessRightRoleMappingModel) o;
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
        return "AccessRightRoleMappingModel{" +
                "accessRightRoleMappingID=" + accessRightRoleMappingID +
                ", accessRightID=" + accessRightID +
                ", roleID=" + roleID +
                '}';
    }
}
