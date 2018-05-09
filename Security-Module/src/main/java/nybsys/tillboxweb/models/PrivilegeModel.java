package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class PrivilegeModel extends BaseModel{


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
        if (!(o instanceof PrivilegeModel)) return false;
        if (!super.equals(o)) return false;
        PrivilegeModel that = (PrivilegeModel) o;
        return Objects.equals(getPrivilegeID(), that.getPrivilegeID()) &&
                Objects.equals(getParentID(), that.getParentID()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getActionCode(), that.getActionCode()) &&
                Objects.equals(getShowName(), that.getShowName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPrivilegeID(), getParentID(), getName(), getActionCode(), getShowName());
    }

    @Override
    public String toString() {
        return "PrivilegeModel{" +
                "privilegeID=" + privilegeID +
                ", parentID=" + parentID +
                ", name='" + name + '\'' +
                ", actionCode='" + actionCode + '\'' +
                ", showName='" + showName + '\'' +
                '}';
    }
}
