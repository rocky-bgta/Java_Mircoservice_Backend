/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 14-Feb-18
 * Time: 12:38 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class UserDefineSetting extends BaseEntity {
    @Id
    @GeneratedValue(generator="IdGen")
    @GenericGenerator(name="IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name",value = "userDefineSettingID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer",value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    private Integer userDefineSettingID;

    private Integer businessID;
    private Integer referenceType;
    private Integer referenceID;

    public Integer getUserDefineSettingID() {
        return userDefineSettingID;
    }

    public void setUserDefineSettingID(Integer userDefineSettingID) {
        this.userDefineSettingID = userDefineSettingID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(Integer referenceType) {
        this.referenceType = referenceType;
    }

    public Integer getReferenceID() {
        return referenceID;
    }

    public void setReferenceID(Integer referenceID) {
        this.referenceID = referenceID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDefineSetting)) return false;
        if (!super.equals(o)) return false;
        UserDefineSetting that = (UserDefineSetting) o;
        return Objects.equals(getUserDefineSettingID(), that.getUserDefineSettingID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getReferenceType(), that.getReferenceType()) &&
                Objects.equals(getReferenceID(), that.getReferenceID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUserDefineSettingID(), getBusinessID(), getReferenceType(), getReferenceID());
    }

    @Override
    public String toString() {
        return "UserDefineSetting{" +
                "userDefineSettingID=" + userDefineSettingID +
                ", businessID=" + businessID +
                ", referenceType=" + referenceType +
                ", referenceID=" + referenceID +
                '}';
    }
}
