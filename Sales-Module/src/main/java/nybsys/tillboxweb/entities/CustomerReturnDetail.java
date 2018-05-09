/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 02:45
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
public class CustomerReturnDetail extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "customerReturnDetailID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

    private Integer customerReturnDetailID;
    @Column
    private Integer customerReturnID;
    @Column
    private Integer productID;
    @Column
    private Double price;
    @Column
    private Double quantity;
    @Column
    private Double vat;
    @Column
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
        if (!(o instanceof CustomerReturnDetail)) return false;
        if (!super.equals(o)) return false;
        CustomerReturnDetail that = (CustomerReturnDetail) o;
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
        return "CustomerReturnDetail{" +
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