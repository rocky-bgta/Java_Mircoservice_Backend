/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 13/02/2018
 * Time: 03:59
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
public class UnitOfMeasure extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "unitOfMeasureID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    @NotNull
    @Column
    private Integer unitOfMeasureID;
    @Column
    private Integer uOMMethodID;
    @Column
    private Integer businessID;
    @Column
    private String name;

    public Integer getUnitOfMeasureID() {
        return unitOfMeasureID;
    }

    public void setUnitOfMeasureID(Integer unitOfMeasureID) {
        this.unitOfMeasureID = unitOfMeasureID;
    }

    public Integer getuOMMethodID() {
        return uOMMethodID;
    }

    public void setuOMMethodID(Integer uOMMethodID) {
        this.uOMMethodID = uOMMethodID;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UnitOfMeasure)) return false;
        if (!super.equals(o)) return false;
        UnitOfMeasure that = (UnitOfMeasure) o;
        return Objects.equals(getUnitOfMeasureID(), that.getUnitOfMeasureID()) &&
                Objects.equals(getuOMMethodID(), that.getuOMMethodID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUnitOfMeasureID(), getuOMMethodID(), getBusinessID(), getName());
    }

    @Override
    public String toString() {
        return "UnitOfMeasure{" +
                "unitOfMeasureID=" + unitOfMeasureID +
                ", uOMMethodID=" + uOMMethodID +
                ", businessID=" + businessID +
                ", name='" + name + '\'' +
                '}';
    }
}
