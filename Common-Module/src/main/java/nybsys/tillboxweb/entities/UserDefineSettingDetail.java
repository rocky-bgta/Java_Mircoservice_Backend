/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/16/2018
 * Time: 12:36 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.entities;
import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class UserDefineSettingDetail extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "userDefinedSettingDetailsID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    @Column
    @NotNull
    private Integer userDefinedSettingDetailsID;
    private Integer businessID;
    private Integer userDefinedSettingsID;
    private Integer referenceID;
    private Integer referenceType;
    private String value;

    public Integer getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(Integer referenceType) {
        this.referenceType = referenceType;
    }

    public Integer getUserDefinedSettingDetailsID() {
        return userDefinedSettingDetailsID;
    }

    public void setUserDefinedSettingDetailsID(Integer userDefinedSettingDetailsID) {
        this.userDefinedSettingDetailsID = userDefinedSettingDetailsID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getUserDefinedSettingsID() {
        return userDefinedSettingsID;
    }

    public void setUserDefinedSettingsID(Integer userDefinedSettingsID) {
        this.userDefinedSettingsID = userDefinedSettingsID;
    }

    public Integer getReferenceID() {
        return referenceID;
    }

    public void setReferenceID(Integer referenceID) {
        this.referenceID = referenceID;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDefineSettingDetail)) return false;
        if (!super.equals(o)) return false;
        UserDefineSettingDetail that = (UserDefineSettingDetail) o;
        return Objects.equals(getUserDefinedSettingDetailsID(), that.getUserDefinedSettingDetailsID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getUserDefinedSettingsID(), that.getUserDefinedSettingsID()) &&
                Objects.equals(getReferenceID(), that.getReferenceID()) &&
                Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUserDefinedSettingDetailsID(), getBusinessID(), getUserDefinedSettingsID(), getReferenceID(), getValue());
    }

    @Override
    public String toString() {
        return "UserDefineSettingDetail{" +
                "userDefinedSettingDetailsID=" + userDefinedSettingDetailsID +
                ", businessID=" + businessID +
                ", userDefinedSettingsID=" + userDefinedSettingsID +
                ", referenceID=" + referenceID +
                ", value='" + value + '\'' +
                '}';
    }
}
