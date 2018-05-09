/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 01:18
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class CustomerQuotationDetailModel extends BaseModel {
    private Integer customerQuotationDetailID;
    private Integer customerQuotationID;
    private Integer discountSettingID;
    private Integer productID;
    private Double quantity;
    private Double price;
    private Integer vatTypeID;
    private Double vatPercentage;
    private Double vatAmount;
    private Double discount;
    private Integer uom;

    public Integer getCustomerQuotationDetailID() {
        return customerQuotationDetailID;
    }

    public void setCustomerQuotationDetailID(Integer customerQuotationDetailID) {
        this.customerQuotationDetailID = customerQuotationDetailID;
    }

    public Integer getCustomerQuotationID() {
        return customerQuotationID;
    }

    public void setCustomerQuotationID(Integer customerQuotationID) {
        this.customerQuotationID = customerQuotationID;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getVatTypeID() {
        return vatTypeID;
    }

    public void setVatTypeID(Integer vatTypeID) {
        this.vatTypeID = vatTypeID;
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

    public Integer getUom() {
        return uom;
    }

    public void setUom(Integer uom) {
        this.uom = uom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerQuotationDetailModel)) return false;
        if (!super.equals(o)) return false;
        CustomerQuotationDetailModel that = (CustomerQuotationDetailModel) o;
        return Objects.equals(getCustomerQuotationDetailID(), that.getCustomerQuotationDetailID()) &&
                Objects.equals(getCustomerQuotationID(), that.getCustomerQuotationID()) &&
                Objects.equals(getDiscountSettingID(), that.getDiscountSettingID()) &&
                Objects.equals(getProductID(), that.getProductID()) &&
                Objects.equals(getQuantity(), that.getQuantity()) &&
                Objects.equals(getPrice(), that.getPrice()) &&
                Objects.equals(getVatTypeID(), that.getVatTypeID()) &&
                Objects.equals(getVatPercentage(), that.getVatPercentage()) &&
                Objects.equals(getVatAmount(), that.getVatAmount()) &&
                Objects.equals(getDiscount(), that.getDiscount()) &&
                Objects.equals(getUom(), that.getUom());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCustomerQuotationDetailID(), getCustomerQuotationID(), getDiscountSettingID(), getProductID(), getQuantity(), getPrice(), getVatTypeID(), getVatPercentage(), getVatAmount(), getDiscount(), getUom());
    }

    @Override
    public String toString() {
        return "CustomerQuotationDetailModel{" +
                "customerQuotationDetailID=" + customerQuotationDetailID +
                ", customerQuotationID=" + customerQuotationID +
                ", discountSettingID=" + discountSettingID +
                ", productID=" + productID +
                ", quantity=" + quantity +
                ", price=" + price +
                ", vatTypeID=" + vatTypeID +
                ", vatPercentage=" + vatPercentage +
                ", vatAmount=" + vatAmount +
                ", discount=" + discount +
                ", uom=" + uom +
                '}';
    }
}
