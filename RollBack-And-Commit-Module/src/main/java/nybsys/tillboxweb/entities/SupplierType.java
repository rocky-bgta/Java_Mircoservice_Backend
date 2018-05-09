/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 21/03/2018
 * Time: 02:05
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
public class SupplierType extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "supplierTypeID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

    private Integer supplierTypeID;
    @Column
    private Integer businessID;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private Double discountPercent;

    public Integer getSupplierTypeID() {
        return supplierTypeID;
    }

    public void setSupplierTypeID(Integer supplierTypeID) {
        this.supplierTypeID = supplierTypeID;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Double discountPercent) {
        this.discountPercent = discountPercent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SupplierType)) return false;
        if (!super.equals(o)) return false;
        SupplierType that = (SupplierType) o;
        return Objects.equals(getSupplierTypeID(), that.getSupplierTypeID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getDiscountPercent(), that.getDiscountPercent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSupplierTypeID(), getBusinessID(), getName(), getDescription(), getDiscountPercent());
    }

    @Override
    public String toString() {
        return "SupplierType{" +
                "supplierTypeID=" + supplierTypeID +
                ", businessID=" + businessID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", discountPercent=" + discountPercent +
                '}';
    }
}