/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/23/2018
 * Time: 12:55 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class SupplierInvoiceDetail extends BaseEntity {
    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "supplierInvoiceDetailID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    private Integer supplierInvoiceDetailID;
    private Integer supplierInvoiceID;
    private Integer productID;
    private Double quantity;
    private Double unitPrice;
    private Double discount;
    private Double VAT;

    public Integer getSupplierInvoiceDetailID() {
        return supplierInvoiceDetailID;
    }

    public void setSupplierInvoiceDetailID(Integer supplierInvoiceDetailID) {
        this.supplierInvoiceDetailID = supplierInvoiceDetailID;
    }

    public Integer getSupplierInvoiceID() {
        return supplierInvoiceID;
    }

    public void setSupplierInvoiceID(Integer supplierInvoiceID) {
        this.supplierInvoiceID = supplierInvoiceID;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getVAT() {
        return VAT;
    }

    public void setVAT(Double VAT) {
        this.VAT = VAT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SupplierInvoiceDetail)) return false;
        if (!super.equals(o)) return false;
        SupplierInvoiceDetail that = (SupplierInvoiceDetail) o;
        return Objects.equals(getSupplierInvoiceDetailID(), that.getSupplierInvoiceDetailID()) &&
                Objects.equals(getSupplierInvoiceID(), that.getSupplierInvoiceID()) &&
                Objects.equals(getProductID(), that.getProductID()) &&
                Objects.equals(getQuantity(), that.getQuantity()) &&
                Objects.equals(getUnitPrice(), that.getUnitPrice()) &&
                Objects.equals(getDiscount(), that.getDiscount()) &&
                Objects.equals(getVAT(), that.getVAT());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSupplierInvoiceDetailID(), getSupplierInvoiceID(), getProductID(), getQuantity(), getUnitPrice(), getDiscount(), getVAT());
    }

    @Override
    public String toString() {
        return "SupplierInvoiceDetail{" +
                "supplierInvoiceDetailID=" + supplierInvoiceDetailID +
                ", supplierInvoiceID=" + supplierInvoiceID +
                ", productID=" + productID +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", discount=" + discount +
                ", VAT=" + VAT +
                '}';
    }
}
