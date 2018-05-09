/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/23/2018
 * Time: 12:22 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class SupplierInvoiceDetailModel extends BaseModel {

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
        if (!(o instanceof SupplierInvoiceDetailModel)) return false;
        if (!super.equals(o)) return false;
        SupplierInvoiceDetailModel that = (SupplierInvoiceDetailModel) o;
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
        return "SupplierInvoiceDetailModel{" +
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
