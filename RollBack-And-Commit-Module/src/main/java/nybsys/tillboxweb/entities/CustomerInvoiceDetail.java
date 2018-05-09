/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 02:19
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
public class CustomerInvoiceDetail extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "customerInvoiceDetailID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

    private Integer customerInvoiceDetailID;
    @Column
    private Integer customerInvoiceID;
    @Column
    private Integer discountSettingID;
    @Column
    private Integer productID;
    @Column
    private Double quantity;
    @Column
    private Integer uomID;
    @Column
    private Double unitPrice;
    @Column
    private Double vatPercentage;
    @Column
    private Double vatAmount;
    @Column
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
        if (!(o instanceof CustomerInvoiceDetail)) return false;
        if (!super.equals(o)) return false;
        CustomerInvoiceDetail that = (CustomerInvoiceDetail) o;
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
        return "CustomerInvoiceDetail{" +
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