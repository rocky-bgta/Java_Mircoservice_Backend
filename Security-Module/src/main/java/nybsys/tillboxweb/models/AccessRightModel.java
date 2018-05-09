package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class AccessRightModel extends BaseModel {


    private Integer accessRightID;
    private String name;
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
        if (!(o instanceof AccessRightModel)) return false;
        if (!super.equals(o)) return false;
        AccessRightModel that = (AccessRightModel) o;
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
        return "AccessRightModel{" +
                "accessRightID=" + accessRightID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
