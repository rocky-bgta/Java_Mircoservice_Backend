/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 04:15
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
import java.util.Date;
import java.util.Objects;

@Entity
public class CustomerDiscountFlat extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "customerDiscountFlatID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

    private Integer customerDiscountFlatID;
    @Column
    private Integer businessID;
    @Column
    private Integer customerID;
    @Column
    private Double percentAmount;

    public Integer getCustomerDiscountFlatID() {
        return customerDiscountFlatID;
    }

    public void setCustomerDiscountFlatID(Integer customerDiscountFlatID) {
        this.customerDiscountFlatID = customerDiscountFlatID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public Double getPercentAmount() {
        return percentAmount;
    }

    public void setPercentAmount(Double percentAmount) {
        this.percentAmount = percentAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerDiscountFlat)) return false;
        if (!super.equals(o)) return false;
        CustomerDiscountFlat that = (CustomerDiscountFlat) o;
        return Objects.equals(getCustomerDiscountFlatID(), that.getCustomerDiscountFlatID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getCustomerID(), that.getCustomerID()) &&
                Objects.equals(getPercentAmount(), that.getPercentAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCustomerDiscountFlatID(), getBusinessID(), getCustomerID(), getPercentAmount());
    }

    @Override
    public String toString() {
        return "CustomerDiscountFlat{" +
                "customerDiscountFlatID=" + customerDiscountFlatID +
                ", businessID=" + businessID +
                ", customerID=" + customerID +
                ", percentAmount=" + percentAmount +
                '}';
    }
}