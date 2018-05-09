/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 19-Feb-18
 * Time: 11:46 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import nybsys.tillboxweb.BaseEntityWithCurrency;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;
//import org.hibernate.annotations.Parameter.*;

@Entity
public class ProductAdjustment extends BaseEntityWithCurrency {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "productAdjustmentID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

    private Integer productAdjustmentID;

    @Column
    private Integer adjustmentType;

    @Column
    private Integer businessID;

    @Column
    private Date date;

    @Column
    private Double totalPrice;

    @Column
    private Boolean isApproved;

    @Column
    private Boolean approvedBy;

    @Column
    private String reason;

    @Column
    private Integer productAdjustmentReferenceTypeID;

    public Integer getProductAdjustmentID() {
        return productAdjustmentID;
    }

    public void setProductAdjustmentID(Integer productAdjustmentID) {
        this.productAdjustmentID = productAdjustmentID;
    }

    public Integer getAdjustmentType() {
        return adjustmentType;
    }

    public void setAdjustmentType(Integer adjustmentType) {
        this.adjustmentType = adjustmentType;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }

    public Boolean getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Boolean approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getProductAdjustmentReferenceTypeID() {
        return productAdjustmentReferenceTypeID;
    }

    public void setProductAdjustmentReferenceTypeID(Integer productAdjustmentReferenceTypeID) {
        this.productAdjustmentReferenceTypeID = productAdjustmentReferenceTypeID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductAdjustment)) return false;
        if (!super.equals(o)) return false;
        ProductAdjustment that = (ProductAdjustment) o;
        return Objects.equals(getProductAdjustmentID(), that.getProductAdjustmentID()) &&
                Objects.equals(getAdjustmentType(), that.getAdjustmentType()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getTotalPrice(), that.getTotalPrice()) &&
                Objects.equals(isApproved, that.isApproved) &&
                Objects.equals(getApprovedBy(), that.getApprovedBy()) &&
                Objects.equals(getReason(), that.getReason()) &&
                Objects.equals(getProductAdjustmentReferenceTypeID(), that.getProductAdjustmentReferenceTypeID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getProductAdjustmentID(), getAdjustmentType(), getBusinessID(), getDate(), getTotalPrice(), isApproved, getApprovedBy(), getReason(), getProductAdjustmentReferenceTypeID());
    }

    @Override
    public String toString() {
        return "ProductAdjustment{" +
                "productAdjustmentID=" + productAdjustmentID +
                ", adjustmentType=" + adjustmentType +
                ", businessID=" + businessID +
                ", date=" + date +
                ", totalPrice=" + totalPrice +
                ", isApproved=" + isApproved +
                ", approvedBy=" + approvedBy +
                ", reason='" + reason + '\'' +
                ", productAdjustmentReferenceTypeID=" + productAdjustmentReferenceTypeID +
                '}';
    }
}
