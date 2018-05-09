/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 02:56
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModelWithCurrency;

import java.util.Date;
import java.util.Objects;

public class SupplierAdditionalCostModel extends BaseModelWithCurrency {
    private Integer supplierAdditionalCostID;
    private Integer businessID;
    private Integer supplierAdditionalCostSettingID;
    private Integer referenceID;
    private Integer partyType;
    private Integer partyID;
    private Date dueDate;
    private String description;
    private Integer vatID;
    private Integer amount;

    public Integer getSupplierAdditionalCostID() {
        return supplierAdditionalCostID;
    }

    public void setSupplierAdditionalCostID(Integer supplierAdditionalCostID) {
        this.supplierAdditionalCostID = supplierAdditionalCostID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getSupplierAdditionalCostSettingID() {
        return supplierAdditionalCostSettingID;
    }

    public void setSupplierAdditionalCostSettingID(Integer supplierAdditionalCostSettingID) {
        this.supplierAdditionalCostSettingID = supplierAdditionalCostSettingID;
    }

    public Integer getReferenceID() {
        return referenceID;
    }

    public void setReferenceID(Integer referenceID) {
        this.referenceID = referenceID;
    }

    public Integer getPartyType() {
        return partyType;
    }

    public void setPartyType(Integer partyType) {
        this.partyType = partyType;
    }

    public Integer getPartyID() {
        return partyID;
    }

    public void setPartyID(Integer partyID) {
        this.partyID = partyID;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getVatID() {
        return vatID;
    }

    public void setVatID(Integer vatID) {
        this.vatID = vatID;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SupplierAdditionalCostModel)) return false;
        if (!super.equals(o)) return false;
        SupplierAdditionalCostModel that = (SupplierAdditionalCostModel) o;
        return Objects.equals(getSupplierAdditionalCostID(), that.getSupplierAdditionalCostID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getSupplierAdditionalCostSettingID(), that.getSupplierAdditionalCostSettingID()) &&
                Objects.equals(getReferenceID(), that.getReferenceID()) &&
                Objects.equals(getPartyType(), that.getPartyType()) &&
                Objects.equals(getPartyID(), that.getPartyID()) &&
                Objects.equals(getDueDate(), that.getDueDate()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getVatID(), that.getVatID()) &&
                Objects.equals(getAmount(), that.getAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSupplierAdditionalCostID(), getBusinessID(), getSupplierAdditionalCostSettingID(), getReferenceID(), getPartyType(), getPartyID(), getDueDate(), getDescription(), getVatID(), getAmount());
    }

    @Override
    public String toString() {
        return "SupplierAdditionalCostModel{" +
                "supplierAdditionalCostID=" + supplierAdditionalCostID +
                ", businessID=" + businessID +
                ", supplierAdditionalCostSettingID=" + supplierAdditionalCostSettingID +
                ", referenceID=" + referenceID +
                ", partyType=" + partyType +
                ", partyID=" + partyID +
                ", dueDate=" + dueDate +
                ", description='" + description + '\'' +
                ", vatID=" + vatID +
                ", amount=" + amount +
                '}';
    }
}
