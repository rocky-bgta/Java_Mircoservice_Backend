/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 18-Apr-18
 * Time: 12:04 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class ItemSettingModel extends BaseModel {

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
        if (!(o instanceof ItemSettingModel)) return false;
        if (!super.equals(o)) return false;
        ItemSettingModel that = (ItemSettingModel) o;
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
        return "ItemSettingModel{" +
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
