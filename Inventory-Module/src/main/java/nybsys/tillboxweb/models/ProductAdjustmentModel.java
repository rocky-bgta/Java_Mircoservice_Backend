/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 19-Feb-18
 * Time: 4:21 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;
import nybsys.tillboxweb.BaseModelWithCurrency;

import java.util.Date;
import java.util.Objects;

public class ProductAdjustmentModel extends BaseModelWithCurrency {


    private Integer productAdjustmentID;
    private Integer adjustmentType;
    private Integer businessID;
    private Date date;
    private Double totalPrice;
    private Boolean isApproved;
    private Boolean approvedBy;
    private String reason;
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
        if (!(o instanceof ProductAdjustmentModel)) return false;
        if (!super.equals(o)) return false;
        ProductAdjustmentModel that = (ProductAdjustmentModel) o;
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
        return "ProductAdjustmentModel{" +
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
