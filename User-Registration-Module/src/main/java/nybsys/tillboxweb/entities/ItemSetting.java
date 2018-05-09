/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 18-Apr-18
 * Time: 11:16 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class ItemSetting extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "itemSettingID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

    private Integer itemSettingID;
    private Integer businessID;
    private Boolean warnWhenItemQuantitiesFallBelowZero;
    private Boolean doNotAllowItemQuantitiesBelowZero;
    private Boolean warnWhenItemCostIsZero;
    private Boolean warnWhenItemSellingPriceIsBelowCost;
    private Boolean displayInactiveItemsForSelectionOnDocumentLines;
    private Boolean displayInactiveItemsForSelectionOnReports;

    public Integer getItemSettingID() {
        return itemSettingID;
    }

    public void setItemSettingID(Integer itemSettingID) {
        this.itemSettingID = itemSettingID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Boolean getWarnWhenItemQuantitiesFallBelowZero() {
        return warnWhenItemQuantitiesFallBelowZero;
    }

    public void setWarnWhenItemQuantitiesFallBelowZero(Boolean warnWhenItemQuantitiesFallBelowZero) {
        this.warnWhenItemQuantitiesFallBelowZero = warnWhenItemQuantitiesFallBelowZero;
    }

    public Boolean getDoNotAllowItemQuantitiesBelowZero() {
        return doNotAllowItemQuantitiesBelowZero;
    }

    public void setDoNotAllowItemQuantitiesBelowZero(Boolean doNotAllowItemQuantitiesBelowZero) {
        this.doNotAllowItemQuantitiesBelowZero = doNotAllowItemQuantitiesBelowZero;
    }

    public Boolean getWarnWhenItemCostIsZero() {
        return warnWhenItemCostIsZero;
    }

    public void setWarnWhenItemCostIsZero(Boolean warnWhenItemCostIsZero) {
        this.warnWhenItemCostIsZero = warnWhenItemCostIsZero;
    }

    public Boolean getWarnWhenItemSellingPriceIsBelowCost() {
        return warnWhenItemSellingPriceIsBelowCost;
    }

    public void setWarnWhenItemSellingPriceIsBelowCost(Boolean warnWhenItemSellingPriceIsBelowCost) {
        this.warnWhenItemSellingPriceIsBelowCost = warnWhenItemSellingPriceIsBelowCost;
    }

    public Boolean getDisplayInactiveItemsForSelectionOnDocumentLines() {
        return displayInactiveItemsForSelectionOnDocumentLines;
    }

    public void setDisplayInactiveItemsForSelectionOnDocumentLines(Boolean displayInactiveItemsForSelectionOnDocumentLines) {
        this.displayInactiveItemsForSelectionOnDocumentLines = displayInactiveItemsForSelectionOnDocumentLines;
    }

    public Boolean getDisplayInactiveItemsForSelectionOnReports() {
        return displayInactiveItemsForSelectionOnReports;
    }

    public void setDisplayInactiveItemsForSelectionOnReports(Boolean displayInactiveItemsForSelectionOnReports) {
        this.displayInactiveItemsForSelectionOnReports = displayInactiveItemsForSelectionOnReports;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemSetting)) return false;
        if (!super.equals(o)) return false;
        ItemSetting that = (ItemSetting) o;
        return Objects.equals(getItemSettingID(), that.getItemSettingID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getWarnWhenItemQuantitiesFallBelowZero(), that.getWarnWhenItemQuantitiesFallBelowZero()) &&
                Objects.equals(getDoNotAllowItemQuantitiesBelowZero(), that.getDoNotAllowItemQuantitiesBelowZero()) &&
                Objects.equals(getWarnWhenItemCostIsZero(), that.getWarnWhenItemCostIsZero()) &&
                Objects.equals(getWarnWhenItemSellingPriceIsBelowCost(), that.getWarnWhenItemSellingPriceIsBelowCost()) &&
                Objects.equals(getDisplayInactiveItemsForSelectionOnDocumentLines(), that.getDisplayInactiveItemsForSelectionOnDocumentLines()) &&
                Objects.equals(getDisplayInactiveItemsForSelectionOnReports(), that.getDisplayInactiveItemsForSelectionOnReports());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getItemSettingID(), getBusinessID(), getWarnWhenItemQuantitiesFallBelowZero(), getDoNotAllowItemQuantitiesBelowZero(), getWarnWhenItemCostIsZero(), getWarnWhenItemSellingPriceIsBelowCost(), getDisplayInactiveItemsForSelectionOnDocumentLines(), getDisplayInactiveItemsForSelectionOnReports());
    }

    @Override
    public String toString() {
        return "ItemSetting{" +
                "itemSettingID=" + itemSettingID +
                ", businessID=" + businessID +
                ", warnWhenItemQuantitiesFallBelowZero=" + warnWhenItemQuantitiesFallBelowZero +
                ", doNotAllowItemQuantitiesBelowZero=" + doNotAllowItemQuantitiesBelowZero +
                ", warnWhenItemCostIsZero=" + warnWhenItemCostIsZero +
                ", warnWhenItemSellingPriceIsBelowCost=" + warnWhenItemSellingPriceIsBelowCost +
                ", displayInactiveItemsForSelectionOnDocumentLines=" + displayInactiveItemsForSelectionOnDocumentLines +
                ", displayInactiveItemsForSelectionOnReports=" + displayInactiveItemsForSelectionOnReports +
                '}';
    }
}