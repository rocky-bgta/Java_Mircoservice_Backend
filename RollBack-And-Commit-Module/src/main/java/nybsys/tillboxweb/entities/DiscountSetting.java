/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 04:11
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
public class DiscountSetting extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "discountSettingID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

    private Integer discountSettingID;
    @Column
    private Integer discountDependingOn;
    @Column
    private Integer type;
    @Column
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
        if (!(o instanceof DiscountSetting)) return false;
        if (!super.equals(o)) return false;
        DiscountSetting that = (DiscountSetting) o;
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
        return "DiscountSetting{" +
                "discountSettingID=" + discountSettingID +
                ", discountDependingOn=" + discountDependingOn +
                ", type=" + type +
                ", targetAmount=" + targetAmount +
                '}';
    }
}