/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 01:27
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
public class CustomerQuotationDetail extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "customerQuotationDetailID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

    private Integer customerQuotationDetailID;
    @Column
    private Integer customerQuotationID;
    @Column
    private Integer discountSettingID;
    @Column
    private Integer productID;
    @Column
    private Double quantity;
    @Column
    private Double price;
    @Column
    private Integer vatTypeID;
    @Column
    private Double vatPercentage;
    @Column
    private Double vatAmount;
    @Column
    private Double discount;
    @Column
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
        if (!(o instanceof CustomerQuotationDetail)) return false;
        if (!super.equals(o)) return false;
        CustomerQuotationDetail that = (CustomerQuotationDetail) o;
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
        return "CustomerQuotationDetail{" +
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