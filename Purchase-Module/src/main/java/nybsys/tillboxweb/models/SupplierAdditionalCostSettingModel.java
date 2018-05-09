/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 03:11
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class SupplierAdditionalCostSettingModel extends BaseModel {
    private Integer supplierAdditionalCostSettingID;
    private Integer businessID;
    private String description;

    public Integer getSupplierAdditionalCostSettingID() {
        return supplierAdditionalCostSettingID;
    }

    public void setSupplierAdditionalCostSettingID(Integer supplierAdditionalCostSettingID) {
        this.supplierAdditionalCostSettingID = supplierAdditionalCostSettingID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SupplierAdditionalCostSettingModel)) return false;
        if (!super.equals(o)) return false;
        SupplierAdditionalCostSettingModel that = (SupplierAdditionalCostSettingModel) o;
        return Objects.equals(getSupplierAdditionalCostSettingID(), that.getSupplierAdditionalCostSettingID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSupplierAdditionalCostSettingID(), getBusinessID(), getDescription());
    }

    @Override
    public String toString() {
        return "SupplierAdditionalCostSettingModel{" +
                "supplierAdditionalCostSettingID=" + supplierAdditionalCostSettingID +
                ", businessID=" + businessID +
                ", description='" + description + '\'' +
                '}';
    }
}
