/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 12/03/2018
 * Time: 11:56
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class SalesRepresentative extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "salesRepresentativeID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

    private Integer salesRepresentativeID;
    @Column
    private Integer businessID;
    @Column
    private String name;
    @Column
    private String designation;
    @Column
    private String phone;

    public Integer getSalesRepresentativeID() {
        return salesRepresentativeID;
    }

    public void setSalesRepresentativeID(Integer salesRepresentativeID) {
        this.salesRepresentativeID = salesRepresentativeID;
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SalesRepresentative)) return false;
        if (!super.equals(o)) return false;
        SalesRepresentative that = (SalesRepresentative) o;
        return Objects.equals(getSalesRepresentativeID(), that.getSalesRepresentativeID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDesignation(), that.getDesignation()) &&
                Objects.equals(getPhone(), that.getPhone());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSalesRepresentativeID(), getBusinessID(), getName(), getDesignation(), getPhone());
    }

    @Override
    public String toString() {
        return "SalesRepresentative{" +
                "salesRepresentativeID=" + salesRepresentativeID +
                ", businessID=" + businessID +
                ", name='" + name + '\'' +
                ", designation='" + designation + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}