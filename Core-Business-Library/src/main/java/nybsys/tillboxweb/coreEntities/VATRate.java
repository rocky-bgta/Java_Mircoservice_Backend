/**
 * Created By: Md. Abdul Hannan
 * Created Date: 4/16/2018
 * Time: 1:01 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.coreEntities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class VATRate extends BaseEntity {
    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "vATRateID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    Integer vATRateID;
    Integer businessID;
    String name;
    Double Rate;
    Boolean isDefault;
    Boolean userDefine;

    public Integer getvATRateID() {
        return vATRateID;
    }

    public void setvATRateID(Integer vATRateID) {
        this.vATRateID = vATRateID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRate() {
        return Rate;
    }

    public void setRate(Double rate) {
        Rate = rate;
    }


    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Boolean getUserDefine() {
        return userDefine;
    }

    public void setUserDefine(Boolean userDefine) {
        this.userDefine = userDefine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VATRate)) return false;
        if (!super.equals(o)) return false;
        VATRate vatRate = (VATRate) o;
        return Objects.equals(getvATRateID(), vatRate.getvATRateID()) &&
                Objects.equals(getBusinessID(), vatRate.getBusinessID()) &&
                Objects.equals(getName(), vatRate.getName()) &&
                Objects.equals(getRate(), vatRate.getRate()) &&
                Objects.equals(isDefault, vatRate.isDefault) &&
                Objects.equals(getUserDefine(), vatRate.getUserDefine());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getvATRateID(), getBusinessID(), getName(), getRate(), isDefault, getUserDefine());
    }

    @Override
    public String toString() {
        return "VATRate{" +
                "vATRateID=" + vATRateID +
                ", businessID=" + businessID +
                ", name='" + name + '\'' +
                ", Rate=" + Rate +
                ", isDefault=" + isDefault +
                ", userDefine=" + userDefine +
                '}';
    }
}
