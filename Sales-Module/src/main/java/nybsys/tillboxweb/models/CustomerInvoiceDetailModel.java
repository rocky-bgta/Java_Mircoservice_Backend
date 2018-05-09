/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 02:15
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class CustomerInvoiceDetailModel extends BaseModel {
    private Integer customerInvoiceDetailID;
    private Integer customerInvoiceID;
    private Integer discountSettingID;
    private Integer productID;
    private Double quantity;
    private Integer uomID;
    private Double unitPrice;
    private Double vatPercentage;
    private Double vatAmount;
    private Double discount;

    public Integer getCustomerInvoiceDetailID() {
        return customerInvoiceDetailID;
    }

    public void setCustomerInvoiceDetailID(Integer customerInvoiceDetailID) {
        this.customerInvoiceDetailID = customerInvoiceDetailID;
    }

    public Integer getCustomerInvoiceID() {
        return customerInvoiceID;
    }

    public void setCustomerInvoiceID(Integer customerInvoiceID) {
        this.customerInvoiceID = customerInvoiceID;
    }

    public Integer getDiscountSettingID() {
        return discountSettingID;
    }

    public void setDiscountSettingID(Integer discountSettingID) {
        this.discountSettingID = discountSettingID;
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

    public Integer getUomID() {
        return uomID;
    }

    public void setUomID(Integer uomID) {
        this.uomID = uomID;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getVatPercentage() {
        return vatPercentage;
    }

    public void setVatPercentage(Double vatPercentage) {
        this.vatPercentage = vatPercentage;
    }

    public Double getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(Double vatAmount) {
        this.vatAmount = vatAmount;
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
        if (!(o instanceof CustomerInvoiceDetailModel)) return false;
        if (!super.equals(o)) return false;
        CustomerInvoiceDetailModel that = (CustomerInvoiceDetailModel) o;
        return Objects.equals(getCustomerInvoiceDetailID(), that.getCustomerInvoiceDetailID()) &&
                Objects.equals(getCustomerInvoiceID(), that.getCustomerInvoiceID()) &&
                Objects.equals(getDiscountSettingID(), that.getDiscountSettingID()) &&
                Objects.equals(getProductID(), that.getProductID()) &&
                Objects.equals(getQuantity(), that.getQuantity()) &&
                Objects.equals(getUomID(), that.getUomID()) &&
                Objects.equals(getUnitPrice(), that.getUnitPrice()) &&
                Objects.equals(getVatPercentage(), that.getVatPercentage()) &&
                Objects.equals(getVatAmount(), that.getVatAmount()) &&
                Objects.equals(getDiscount(), that.getDiscount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCustomerInvoiceDetailID(), getCustomerInvoiceID(), getDiscountSettingID(), getProductID(), getQuantity(), getUomID(), getUnitPrice(), getVatPercentage(), getVatAmount(), getDiscount());
    }

    @Override
    public String toString() {
        return "CustomerInvoiceDetailModel{" +
                "customerInvoiceDetailID=" + customerInvoiceDetailID +
                ", customerInvoiceID=" + customerInvoiceID +
                ", discountSettingID=" + discountSettingID +
                ", productID=" + productID +
                ", quantity=" + quantity +
                ", uomID=" + uomID +
                ", unitPrice=" + unitPrice +
                ", vatPercentage=" + vatPercentage +
                ", vatAmount=" + vatAmount +
                ", discount=" + discount +
                '}';
    }
}
