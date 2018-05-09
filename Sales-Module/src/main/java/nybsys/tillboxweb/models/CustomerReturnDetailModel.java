/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 02:40
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class CustomerReturnDetailModel extends BaseModel {
    private Integer customerReturnDetailID;
    private Integer customerReturnID;
    private Integer productID;
    private Double price;
    private Double quantity;
    private Double vat;
    private Double discount;

    public Integer getCustomerReturnDetailID() {
        return customerReturnDetailID;
    }

    public void setCustomerReturnDetailID(Integer customerReturnDetailID) {
        this.customerReturnDetailID = customerReturnDetailID;
    }

    public Integer getCustomerReturnID() {
        return customerReturnID;
    }

    public void setCustomerReturnID(Integer customerReturnID) {
        this.customerReturnID = customerReturnID;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
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
        if (!(o instanceof CustomerReturnDetailModel)) return false;
        if (!super.equals(o)) return false;
        CustomerReturnDetailModel that = (CustomerReturnDetailModel) o;
        return Objects.equals(getCustomerReturnDetailID(), that.getCustomerReturnDetailID()) &&
                Objects.equals(getCustomerReturnID(), that.getCustomerReturnID()) &&
                Objects.equals(getProductID(), that.getProductID()) &&
                Objects.equals(getPrice(), that.getPrice()) &&
                Objects.equals(getQuantity(), that.getQuantity()) &&
                Objects.equals(getVat(), that.getVat()) &&
                Objects.equals(getDiscount(), that.getDiscount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCustomerReturnDetailID(), getCustomerReturnID(), getProductID(), getPrice(), getQuantity(), getVat(), getDiscount());
    }

    @Override
    public String toString() {
        return "CustomerReturnDetailModel{" +
                "customerReturnDetailID=" + customerReturnDetailID +
                ", customerReturnID=" + customerReturnID +
                ", productID=" + productID +
                ", price=" + price +
                ", quantity=" + quantity +
                ", vat=" + vat +
                ", discount=" + discount +
                '}';
    }
}
