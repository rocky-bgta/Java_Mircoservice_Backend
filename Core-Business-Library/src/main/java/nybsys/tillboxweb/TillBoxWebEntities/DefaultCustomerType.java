/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 21/03/2018
 * Time: 02:05
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.TillBoxWebEntities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class DefaultCustomerType extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "customerTypeID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

    private Integer customerTypeID;
    @Column
    private Integer businessID;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private Double discountPercent;

    public Integer getCustomerTypeID() {
        return customerTypeID;
    }

    public void setCustomerTypeID(Integer customerTypeID) {
        this.customerTypeID = customerTypeID;
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
        if (!(o instanceof DefaultCustomerType)) return false;
        if (!super.equals(o)) return false;
        DefaultCustomerType that = (DefaultCustomerType) o;
        return Objects.equals(getCustomerTypeID(), that.getCustomerTypeID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getDiscountPercent(), that.getDiscountPercent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCustomerTypeID(), getBusinessID(), getName(), getDescription(), getDiscountPercent());
    }

    @Override
    public String toString() {
        return "CustomerType{" +
                "customerTypeID=" + customerTypeID +
                ", businessID=" + businessID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", discountPercent=" + discountPercent +
                '}';
    }
}