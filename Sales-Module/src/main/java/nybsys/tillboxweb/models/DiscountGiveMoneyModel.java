/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 03:52
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class DiscountGiveMoneyModel extends BaseModel {
    private Integer discountMoneyID;
    private Integer discountSettingID;
    private Boolean isPercentage;
    private Double amount;

    public Integer getDiscountMoneyID() {
        return discountMoneyID;
    }

    public void setDiscountMoneyID(Integer discountMoneyID) {
        this.discountMoneyID = discountMoneyID;
    }

    public Integer getDiscountSettingID() {
        return discountSettingID;
    }

    public void setDiscountSettingID(Integer discountSettingID) {
        this.discountSettingID = discountSettingID;
    }

    public Boolean getPercentage() {
        return isPercentage;
    }

    public void setPercentage(Boolean percentage) {
        isPercentage = percentage;
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
        if (!(o instanceof DiscountGiveMoneyModel)) return false;
        if (!super.equals(o)) return false;
        DiscountGiveMoneyModel that = (DiscountGiveMoneyModel) o;
        return Objects.equals(getDiscountMoneyID(), that.getDiscountMoneyID()) &&
                Objects.equals(getDiscountSettingID(), that.getDiscountSettingID()) &&
                Objects.equals(isPercentage, that.isPercentage) &&
                Objects.equals(getAmount(), that.getAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDiscountMoneyID(), getDiscountSettingID(), isPercentage, getAmount());
    }

    @Override
    public String toString() {
        return "DiscountGiveMoneyModel{" +
                "discountMoneyID=" + discountMoneyID +
                ", discountSettingID=" + discountSettingID +
                ", isPercentage=" + isPercentage +
                ", amount=" + amount +
                '}';
    }
}
