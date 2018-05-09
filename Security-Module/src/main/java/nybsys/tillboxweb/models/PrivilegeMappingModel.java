package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class PrivilegeMappingModel extends BaseModel{


    private Integer privilegeMappingID;
    private Integer privilegeID;
    private String serviceURI;

    public Integer getPrivilegeMappingID() {
        return privilegeMappingID;
    }

    public void setPrivilegeMappingID(Integer privilegeMappingID) {
        this.privilegeMappingID = privilegeMappingID;
    }

    public Integer getPrivilegeID() {
        return privilegeID;
    }

    public void setPrivilegeID(Integer privilegeID) {
        this.privilegeID = privilegeID;
    }

    public String getServiceURI() {
        return serviceURI;
    }

    public void setServiceURI(String serviceURI) {
        this.serviceURI = serviceURI;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PrivilegeMappingModel)) return false;
        if (!super.equals(o)) return false;
        PrivilegeMappingModel that = (PrivilegeMappingModel) o;
        return Objects.equals(getPrivilegeMappingID(), that.getPrivilegeMappingID()) &&
                Objects.equals(getPrivilegeID(), that.getPrivilegeID()) &&
                Objects.equals(getServiceURI(), that.getServiceURI());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPrivilegeMappingID(), getPrivilegeID(), getServiceURI());
    }

    @Override
    public String toString() {
        return "PrivilegeMappingModel{" +
                "privilegeMappingID=" + privilegeMappingID +
                ", privilegeID=" + privilegeID +
                ", serviceURI='" + serviceURI + '\'' +
                '}';
    }
}
