/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 22/02/2018
 * Time: 11:42
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
public class SupplierAdjustmentDetail extends BaseEntity {
    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "supplierAdjustmentDetailID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    private Integer supplierAdjustmentDetailID;
    @Column
    private Integer supplierAdjustmentID;
    @Column
    private Integer supplierInvoiceID;
    @Column
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
        if (!(o instanceof SupplierAdjustmentDetail)) return false;
        if (!super.equals(o)) return false;
        SupplierAdjustmentDetail that = (SupplierAdjustmentDetail) o;
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
        return "SupplierAdjustmentDetail{" +
                "supplierAdjustmentDetailID=" + supplierAdjustmentDetailID +
                ", supplierAdjustmentID=" + supplierAdjustmentID +
                ", supplierInvoiceID=" + supplierInvoiceID +
                ", adjustAmount=" + adjustAmount +
                '}';
    }
}
