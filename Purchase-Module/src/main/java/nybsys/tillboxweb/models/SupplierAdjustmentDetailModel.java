/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 22/02/2018
 * Time: 11:24
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class SupplierAdjustmentDetailModel extends BaseModel {
    private Integer supplierAdjustmentDetailID;
    private Integer supplierAdjustmentID;
    private Integer supplierInvoiceID;
    private Double adjustAmount;

    public Integer getSupplierAdjustmentDetailID() {
        return supplierAdjustmentDetailID;
    }

    public void setSupplierAdjustmentDetailID(Integer supplierAdjustmentDetailID) {
        this.supplierAdjustmentDetailID = supplierAdjustmentDetailID;
    }

    public Integer getSupplierAdjustmentID() {
        return supplierAdjustmentID;
    }

    public void setSupplierAdjustmentID(Integer supplierAdjustmentID) {
        this.supplierAdjustmentID = supplierAdjustmentID;
    }

    public Integer getSupplierInvoiceID() {
        return supplierInvoiceID;
    }

    public void setSupplierInvoiceID(Integer supplierInvoiceID) {
        this.supplierInvoiceID = supplierInvoiceID;
    }

    public Double getAdjustAmount() {
        return adjustAmount;
    }

    public void setAdjustAmount(Double adjustAmount) {
        this.adjustAmount = adjustAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SupplierAdjustmentDetailModel)) return false;
        if (!super.equals(o)) return false;
        SupplierAdjustmentDetailModel that = (SupplierAdjustmentDetailModel) o;
        return Objects.equals(getSupplierAdjustmentDetailID(), that.getSupplierAdjustmentDetailID()) &&
                Objects.equals(getSupplierAdjustmentID(), that.getSupplierAdjustmentID()) &&
                Objects.equals(getSupplierInvoiceID(), that.getSupplierInvoiceID()) &&
                Objects.equals(getAdjustAmount(), that.getAdjustAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSupplierAdjustmentDetailID(), getSupplierAdjustmentID(), getSupplierInvoiceID(), getAdjustAmount());
    }

    @Override
    public String toString() {
        return "SupplierAdjustmentDetailModel{" +
                "supplierAdjustmentDetailID=" + supplierAdjustmentDetailID +
                ", supplierAdjustmentID=" + supplierAdjustmentID +
                ", supplierInvoiceID=" + supplierInvoiceID +
                ", adjustAmount=" + adjustAmount +
                '}';
    }
}
