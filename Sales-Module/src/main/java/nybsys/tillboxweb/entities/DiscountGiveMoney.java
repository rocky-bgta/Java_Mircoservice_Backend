/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 03:55
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
public class DiscountGiveMoney extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "discountMoneyID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

    private Integer discountMoneyID;
    @Column
    private Integer discountSettingID;
    @Column
    private Boolean isPercentage;
    @Column
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
        if (!(o instanceof DiscountGiveMoney)) return false;
        if (!super.equals(o)) return false;
        DiscountGiveMoney that = (DiscountGiveMoney) o;
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
        return "DiscountGiveMoney{" +
                "discountMoneyID=" + discountMoneyID +
                ", discountSettingID=" + discountSettingID +
                ", isPercentage=" + isPercentage +
                ", amount=" + amount +
                '}';
    }
}