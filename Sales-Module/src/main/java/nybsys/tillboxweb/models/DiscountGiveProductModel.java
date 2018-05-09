/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 03:16
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class DiscountGiveProductModel extends BaseModel {
    private Integer discountGiveProductID;
    private Integer discountSettingID;
    private Integer productID;
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
        if (!(o instanceof DiscountGiveProductModel)) return false;
        if (!super.equals(o)) return false;
        DiscountGiveProductModel that = (DiscountGiveProductModel) o;
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
        return "DiscountGiveProductModel{" +
                "discountGiveProductID=" + discountGiveProductID +
                ", discountSettingID=" + discountSettingID +
                ", productID=" + productID +
                ", amount=" + amount +
                '}';
    }
}
