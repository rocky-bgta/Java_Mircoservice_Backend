/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 04:22
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
public class CustomerDiscountRange extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "customerDiscountRangeID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

    private Integer customerDiscountRangeID;
    @Column
    private Integer businessID;
    @Column
    private Integer customerID;
    @Column
    private Double rangeStart;
    @Column
    private Double rangeEnd;
    @Column
    private Boolean isPercent;
    @Column
    private Double amount;

    public Integer getCustomerDiscountRangeID() {
        return customerDiscountRangeID;
    }

    public void setCustomerDiscountRangeID(Integer customerDiscountRangeID) {
        this.customerDiscountRangeID = customerDiscountRangeID;
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

    public Double getRangeStart() {
        return rangeStart;
    }

    public void setRangeStart(Double rangeStart) {
        this.rangeStart = rangeStart;
    }

    public Double getRangeEnd() {
        return rangeEnd;
    }

    public void setRangeEnd(Double rangeEnd) {
        this.rangeEnd = rangeEnd;
    }

    public Boolean getPercent() {
        return isPercent;
    }

    public void setPercent(Boolean percent) {
        isPercent = percent;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerDiscountRange)) return false;
        if (!super.equals(o)) return false;
        CustomerDiscountRange that = (CustomerDiscountRange) o;
        return Objects.equals(getCustomerDiscountRangeID(), that.getCustomerDiscountRangeID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getCustomerID(), that.getCustomerID()) &&
                Objects.equals(getRangeStart(), that.getRangeStart()) &&
                Objects.equals(getRangeEnd(), that.getRangeEnd()) &&
                Objects.equals(isPercent, that.isPercent) &&
                Objects.equals(getAmount(), that.getAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCustomerDiscountRangeID(), getBusinessID(), getCustomerID(), getRangeStart(), getRangeEnd(), isPercent, getAmount());
    }

    @Override
    public String toString() {
        return "CustomerDiscountRange{" +
                "customerDiscountRangeID=" + customerDiscountRangeID +
                ", businessID=" + businessID +
                ", customerID=" + customerID +
                ", rangeStart=" + rangeStart +
                ", rangeEnd=" + rangeEnd +
                ", isPercent=" + isPercent +
                ", amount=" + amount +
                '}';
    }
}