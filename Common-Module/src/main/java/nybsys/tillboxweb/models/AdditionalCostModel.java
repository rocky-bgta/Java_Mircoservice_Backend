/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 27-Feb-18
 * Time: 2:54 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;
import nybsys.tillboxweb.BaseModelWithCurrency;

import java.util.Objects;

public class AdditionalCostModel extends BaseModelWithCurrency {
    private Integer additionalCostID;
    private Integer businessID;
    private Integer referenceType;
    private Integer referenceID;
    private String description;
    private Double amount;

    public Integer getAdditionalCostID() {
        return additionalCostID;
    }

    public void setAdditionalCostID(Integer additionalCostID) {
        this.additionalCostID = additionalCostID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(Integer referenceType) {
        this.referenceType = referenceType;
    }

    public Integer getReferenceID() {
        return referenceID;
    }

    public void setReferenceID(Integer referenceID) {
        this.referenceID = referenceID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        if (!(o instanceof AdditionalCostModel)) return false;
        if (!super.equals(o)) return false;
        AdditionalCostModel that = (AdditionalCostModel) o;
        return Objects.equals(getAdditionalCostID(), that.getAdditionalCostID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getReferenceType(), that.getReferenceType()) &&
                Objects.equals(getReferenceID(), that.getReferenceID()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getAmount(), that.getAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAdditionalCostID(), getBusinessID(), getReferenceType(), getReferenceID(), getDescription(), getAmount());
    }

    @Override
    public String toString() {
        return "AdditionalCostModel{" +
                "additionalCostID=" + additionalCostID +
                ", businessID=" + businessID +
                ", referenceType=" + referenceType +
                ", referenceID=" + referenceID +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                '}';
    }
}
