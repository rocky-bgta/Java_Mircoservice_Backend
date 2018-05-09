package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class PrivilegeMapping extends BaseEntity{

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "privilegeMappingID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
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
        if (!(o instanceof PrivilegeMapping)) return false;
        if (!super.equals(o)) return false;
        PrivilegeMapping that = (PrivilegeMapping) o;
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
        return "PrivilegeMapping{" +
                "privilegeMappingID=" + privilegeMappingID +
                ", privilegeID=" + privilegeID +
                ", serviceURI='" + serviceURI + '\'' +
                '}';
    }
}
