/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 13/02/2018
 * Time: 03:58
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
public class UOMMethod extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "uOMMethodID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    @NotNull
    @Column
    private Integer uOMMethodID;
    @Column
    private Integer businessID;
    @Column
    private String name;

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
        if (!(o instanceof UOMMethod)) return false;
        if (!super.equals(o)) return false;
        UOMMethod uomMethod = (UOMMethod) o;
        return Objects.equals(getuOMMethodID(), uomMethod.getuOMMethodID()) &&
                Objects.equals(getBusinessID(), uomMethod.getBusinessID()) &&
                Objects.equals(getName(), uomMethod.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getuOMMethodID(), getBusinessID(), getName());
    }

    @Override
    public String toString() {
        return "UOMMethod{" +
                "uOMMethodID=" + uOMMethodID +
                ", businessID=" + businessID +
                ", name='" + name + '\'' +
                '}';
    }
}
