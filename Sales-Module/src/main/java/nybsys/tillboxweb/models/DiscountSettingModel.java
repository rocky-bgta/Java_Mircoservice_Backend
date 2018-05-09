/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 04:09
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class DiscountSettingModel extends BaseModel {
    private Integer discountSettingID;
    private Integer discountDependingOn;
    private Integer type;
    private Double targetAmount;

    public Integer getDiscountSettingID() {
        return discountSettingID;
    }

    public void setDiscountSettingID(Integer discountSettingID) {
        this.discountSettingID = discountSettingID;
    }

    public Integer getDiscountDependingOn() {
        return discountDependingOn;
    }

    public void setDiscountDependingOn(Integer discountDependingOn) {
        this.discountDependingOn = discountDependingOn;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(Double targetAmount) {
        this.targetAmount = targetAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiscountSettingModel)) return false;
        if (!super.equals(o)) return false;
        DiscountSettingModel that = (DiscountSettingModel) o;
        return Objects.equals(getDiscountSettingID(), that.getDiscountSettingID()) &&
                Objects.equals(getDiscountDependingOn(), that.getDiscountDependingOn()) &&
                Objects.equals(getType(), that.getType()) &&
                Objects.equals(getTargetAmount(), that.getTargetAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDiscountSettingID(), getDiscountDependingOn(), getType(), getTargetAmount());
    }

    @Override
    public String toString() {
        return "DiscountSettingModel{" +
                "discountSettingID=" + discountSettingID +
                ", discountDependingOn=" + discountDependingOn +
                ", type=" + type +
                ", targetAmount=" + targetAmount +
                '}';
    }
}
