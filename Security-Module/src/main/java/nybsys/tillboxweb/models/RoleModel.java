package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class RoleModel extends BaseModel{


    private Integer roleID;
    private String name;
    private String description;

    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
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
        if (!(o instanceof RoleModel)) return false;
        if (!super.equals(o)) return false;
        RoleModel roleModel = (RoleModel) o;
        return Objects.equals(getRoleID(), roleModel.getRoleID()) &&
                Objects.equals(getName(), roleModel.getName()) &&
                Objects.equals(getDescription(), roleModel.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getRoleID(), getName(), getDescription());
    }

    @Override
    public String toString() {
        return "RoleModel{" +
                "roleID=" + roleID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
