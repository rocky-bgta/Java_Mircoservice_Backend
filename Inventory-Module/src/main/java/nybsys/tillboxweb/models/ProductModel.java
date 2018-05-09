/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 13/02/2018
 * Time: 10:35
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModelWithCurrency;

import java.util.Date;
import java.util.Objects;

public class ProductModel extends BaseModelWithCurrency {

    private Integer productID;
    private Integer businessID;
    private Integer docNumber;
    private String code;
    private String name;
    private Integer productCategoryID;
    private Integer ProductTypeID;
    private Integer uOMID;
    private String alternativeSupplierName;
    private Double minimumReorder;
    private Double maximumReorder;
    private Double economicOrderQty;
    private Double openingQuantity;
    private Double openingCost;
    private Date quantityOnHandAt;
    private Integer purchaseVATID;
    private Integer salesVATID;
    private Integer salesAccountID;
    private Integer purchaseAccountID;

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(Integer docNumber) {
        this.docNumber = docNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProductCategoryID() {
        return productCategoryID;
    }

    public void setProductCategoryID(Integer productCategoryID) {
        this.productCategoryID = productCategoryID;
    }

    public Integer getProductTypeID() {
        return ProductTypeID;
    }

    public void setProductTypeID(Integer productTypeID) {
        ProductTypeID = productTypeID;
    }

    public Integer getuOMID() {
        return uOMID;
    }

    public void setuOMID(Integer uOMID) {
        this.uOMID = uOMID;
    }

    public String getAlternativeSupplierName() {
        return alternativeSupplierName;
    }

    public void setAlternativeSupplierName(String alternativeSupplierName) {
        this.alternativeSupplierName = alternativeSupplierName;
    }

    public Double getMinimumReorder() {
        return minimumReorder;
    }

    public void setMinimumReorder(Double minimumReorder) {
        this.minimumReorder = minimumReorder;
    }

    public Double getMaximumReorder() {
        return maximumReorder;
    }

    public void setMaximumReorder(Double maximumReorder) {
        this.maximumReorder = maximumReorder;
    }

    public Double getEconomicOrderQty() {
        return economicOrderQty;
    }

    public void setEconomicOrderQty(Double economicOrderQty) {
        this.economicOrderQty = economicOrderQty;
    }

    public Double getOpeningQuantity() {
        return openingQuantity;
    }

    public void setOpeningQuantity(Double openingQuantity) {
        this.openingQuantity = openingQuantity;
    }

    public Double getOpeningCost() {
        return openingCost;
    }

    public void setOpeningCost(Double openingCost) {
        this.openingCost = openingCost;
    }

    public Date getQuantityOnHandAt() {
        return quantityOnHandAt;
    }

    public void setQuantityOnHandAt(Date quantityOnHandAt) {
        this.quantityOnHandAt = quantityOnHandAt;
    }

    public Integer getPurchaseVATID() {
        return purchaseVATID;
    }

    public void setPurchaseVATID(Integer purchaseVATID) {
        this.purchaseVATID = purchaseVATID;
    }

    public Integer getSalesVATID() {
        return salesVATID;
    }

    public void setSalesVATID(Integer salesVATID) {
        this.salesVATID = salesVATID;
    }

    public Integer getSalesAccountID() {
        return salesAccountID;
    }

    public void setSalesAccountID(Integer salesAccountID) {
        this.salesAccountID = salesAccountID;
    }

    public Integer getPurchaseAccountID() {
        return purchaseAccountID;
    }

    public void setPurchaseAccountID(Integer purchaseAccountID) {
        this.purchaseAccountID = purchaseAccountID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductModel)) return false;
        if (!super.equals(o)) return false;
        ProductModel that = (ProductModel) o;
        return Objects.equals(getProductID(), that.getProductID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getDocNumber(), that.getDocNumber()) &&
                Objects.equals(getCode(), that.getCode()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getProductCategoryID(), that.getProductCategoryID()) &&
                Objects.equals(getProductTypeID(), that.getProductTypeID()) &&
                Objects.equals(getuOMID(), that.getuOMID()) &&
                Objects.equals(getAlternativeSupplierName(), that.getAlternativeSupplierName()) &&
                Objects.equals(getMinimumReorder(), that.getMinimumReorder()) &&
                Objects.equals(getMaximumReorder(), that.getMaximumReorder()) &&
                Objects.equals(getEconomicOrderQty(), that.getEconomicOrderQty()) &&
                Objects.equals(getOpeningQuantity(), that.getOpeningQuantity()) &&
                Objects.equals(getOpeningCost(), that.getOpeningCost()) &&
                Objects.equals(getQuantityOnHandAt(), that.getQuantityOnHandAt()) &&
                Objects.equals(getPurchaseVATID(), that.getPurchaseVATID()) &&
                Objects.equals(getSalesVATID(), that.getSalesVATID()) &&
                Objects.equals(getSalesAccountID(), that.getSalesAccountID()) &&
                Objects.equals(getPurchaseAccountID(), that.getPurchaseAccountID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getProductID(), getBusinessID(), getDocNumber(), getCode(), getName(), getProductCategoryID(), getProductTypeID(), getuOMID(), getAlternativeSupplierName(), getMinimumReorder(), getMaximumReorder(), getEconomicOrderQty(), getOpeningQuantity(), getOpeningCost(), getQuantityOnHandAt(), getPurchaseVATID(), getSalesVATID(), getSalesAccountID(), getPurchaseAccountID());
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "productID=" + productID +
                ", businessID=" + businessID +
                ", docNumber=" + docNumber +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", productCategoryID=" + productCategoryID +
                ", ProductTypeID=" + ProductTypeID +
                ", uOMID=" + uOMID +
                ", alternativeSupplierName='" + alternativeSupplierName + '\'' +
                ", minimumReorder=" + minimumReorder +
                ", maximumReorder=" + maximumReorder +
                ", economicOrderQty=" + economicOrderQty +
                ", openingQuantity=" + openingQuantity +
                ", openingCost=" + openingCost +
                ", quantityOnHandAt=" + quantityOnHandAt +
                ", purchaseVATID=" + purchaseVATID +
                ", salesVATID=" + salesVATID +
                ", salesAccountID=" + salesAccountID +
                ", purchaseAccountID=" + purchaseAccountID +
                '}';
    }
}
