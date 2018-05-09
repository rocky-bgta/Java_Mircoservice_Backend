/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 28-Feb-18
 * Time: 10:20 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class SupplierPaymentDetail extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "supplierPaymentDetailID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

    private Integer supplierPaymentDetailID;
    private Integer supplierPaymentID;
    private Integer supplierInvoiceID;
    private Double paidAmount;
    private Double discount;
    private Integer paymentStatus;

    public Integer getSupplierPaymentDetailID() {
        return supplierPaymentDetailID;
    }

    public void setSupplierPaymentDetailID(Integer supplierPaymentDetailID) {
        this.supplierPaymentDetailID = supplierPaymentDetailID;
    }

    public Integer getSupplierPaymentID() {
        return supplierPaymentID;
    }

    public void setSupplierPaymentID(Integer supplierPaymentID) {
        this.supplierPaymentID = supplierPaymentID;
    }

    public Integer getSupplierInvoiceID() {
        return supplierInvoiceID;
    }

    public void setSupplierInvoiceID(Integer supplierInvoiceID) {
        this.supplierInvoiceID = supplierInvoiceID;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SupplierPaymentDetail)) return false;
        if (!super.equals(o)) return false;
        SupplierPaymentDetail that = (SupplierPaymentDetail) o;
        return Objects.equals(getSupplierPaymentDetailID(), that.getSupplierPaymentDetailID()) &&
                Objects.equals(getSupplierPaymentID(), that.getSupplierPaymentID()) &&
                Objects.equals(getSupplierInvoiceID(), that.getSupplierInvoiceID()) &&
                Objects.equals(getPaidAmount(), that.getPaidAmount()) &&
                Objects.equals(getDiscount(), that.getDiscount()) &&
                Objects.equals(getPaymentStatus(), that.getPaymentStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSupplierPaymentDetailID(), getSupplierPaymentID(), getSupplierInvoiceID(), getPaidAmount(), getDiscount(), getPaymentStatus());
    }

    @Override
    public String toString() {
        return "SupplierPaymentDetail{" +
                "supplierPaymentDetailID=" + supplierPaymentDetailID +
                ", supplierPaymentID=" + supplierPaymentID +
                ", supplierInvoiceID=" + supplierInvoiceID +
                ", paidAmount=" + paidAmount +
                ", discount=" + discount +
                ", paymentStatus=" + paymentStatus +
                '}';
    }
}