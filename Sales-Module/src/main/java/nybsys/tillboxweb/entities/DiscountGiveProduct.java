/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 03:19
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
public class DiscountGiveProduct extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "discountGiveProductID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

    private Integer discountGiveProductID;
    @Column
    private Integer discountSettingID;
    @Column
    private Integer productID;
    @Column
    private Double  amount;

    public Integer getDiscountGiveProductID() {
        return discountGiveProductID;
    }

    public void setDiscountGiveProductID(Integer discountGiveProductID) {
        this.discountGiveProductID = discountGiveProductID;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiscountGiveProduct)) return false;
        if (!super.equals(o)) return false;
        DiscountGiveProduct that = (DiscountGiveProduct) o;
        return Objects.equals(getDiscountGiveProductID(), that.getDiscountGiveProductID()) &&
                Objects.equals(getDiscountSettingID(), that.getDiscountSettingID()) &&
                Objects.equals(getProductID(), that.getProductID()) &&
                Objects.equals(getAmount(), that.getAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDiscountGiveProductID(), getDiscountSettingID(), getProductID(), getAmount());
    }

    @Override
    public String toString() {
        return "DiscountGiveProduct{" +
                "discountGiveProductID=" + discountGiveProductID +
                ", discountSettingID=" + discountSettingID +
                ", productID=" + productID +
                ", amount=" + amount +
                '}';
    }
}