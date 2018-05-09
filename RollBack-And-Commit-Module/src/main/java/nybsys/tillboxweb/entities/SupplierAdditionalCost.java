/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 19/03/2018
 * Time: 04:43
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
public class SupplierAdditionalCost extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "supplierAdditionalCostID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

    private Integer supplierAdditionalCostID;
    @Column
    private Integer businessID;
    @Column
    private Integer supplierAdditionalCostSettingID;
    @Column
    private Integer referenceID;
    @Column
    private Integer partyType;
    @Column
    private Integer partyID;
    @Column
    private Date dueDate;
    @Column
    private String description;
    @Column
    private Integer vatID;
    @Column
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
        if (!(o instanceof SupplierAdditionalCost)) return false;
        if (!super.equals(o)) return false;
        SupplierAdditionalCost that = (SupplierAdditionalCost) o;
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
        return "SupplierAdditionalCost{" +
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