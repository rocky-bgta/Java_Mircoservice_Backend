/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/23/2018
 * Time: 12:56 PM
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
public class SupplierReturnDetail extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "supplierReturnDetailID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    private Integer supplierReturnDetailID;
    private Integer supplierReturnID;
    private Integer supplierInvoiceID;
    private Integer productID;
    private Double quantity;
    private Double price;
    private Double VAT;
    private Double discount;

    public Integer getSupplierReturnDetailID() {
        return supplierReturnDetailID;
    }

    public void setSupplierReturnDetailID(Integer supplierReturnDetailID) {
        this.supplierReturnDetailID = supplierReturnDetailID;
    }

    public Integer getSupplierReturnID() {
        return supplierReturnID;
    }

    public void setSupplierReturnID(Integer supplierReturnID) {
        this.supplierReturnID = supplierReturnID;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getVAT() {
        return VAT;
    }

    public void setVAT(Double VAT) {
        this.VAT = VAT;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SupplierReturnDetail)) return false;
        if (!super.equals(o)) return false;
        SupplierReturnDetail that = (SupplierReturnDetail) o;
        return Objects.equals(getSupplierReturnDetailID(), that.getSupplierReturnDetailID()) &&
                Objects.equals(getSupplierReturnID(), that.getSupplierReturnID()) &&
                Objects.equals(getSupplierInvoiceID(), that.getSupplierInvoiceID()) &&
                Objects.equals(getProductID(), that.getProductID()) &&
                Objects.equals(getQuantity(), that.getQuantity()) &&
                Objects.equals(getPrice(), that.getPrice()) &&
                Objects.equals(getVAT(), that.getVAT()) &&
                Objects.equals(getDiscount(), that.getDiscount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSupplierReturnDetailID(), getSupplierReturnID(), getSupplierInvoiceID(), getProductID(), getQuantity(), getPrice(), getVAT(), getDiscount());
    }

    @Override
    public String toString() {
        return "SupplierReturnDetail{" +
                "supplierReturnDetailID=" + supplierReturnDetailID +
                ", supplierReturnID=" + supplierReturnID +
                ", supplierInvoiceID=" + supplierInvoiceID +
                ", productID=" + productID +
                ", quantity=" + quantity +
                ", price=" + price +
                ", VAT=" + VAT +
                ", discount=" + discount +
                '}';
    }
}
