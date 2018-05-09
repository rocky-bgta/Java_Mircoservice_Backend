/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 13/02/2018
 * Time: 04:00
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import nybsys.tillboxweb.BaseEntityWithCurrency;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
public class Product extends BaseEntityWithCurrency {

    @Id
    @GeneratedValue(generator="IdGen")
    @GenericGenerator(name="IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name",value = "productID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer",value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
            })
    @NotNull
    private Integer productID;
    @Column
    private Integer businessID;
    @Column
    private Integer docNumber;
    @Column
    private String code;
    @Column
    private String name;
    @Column
    private Integer productCategoryID;
    @Column
    private Integer ProductTypeID;
    @Column
    private Integer uOMID;
    @Column
    private String alternativeSupplierName;
    @Column
    private Double minimumReorder;
    @Column
    private Double maximumReorder;
    @Column
    private Double economicOrderQty;
    @Column
    private Double openingQuantity;
    @Column
    private Double openingCost;
    @Column
    private Date quantityOnHandAt;
    @Column
    private Integer purchaseVATID;
    @Column
    private Integer salesVATID;
    @Column
    private Integer salesAccountID;
    @Column
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
        if (!(o instanceof Product)) return false;
        if (!super.equals(o)) return false;
        Product product = (Product) o;
        return Objects.equals(getProductID(), product.getProductID()) &&
                Objects.equals(getBusinessID(), product.getBusinessID()) &&
                Objects.equals(getDocNumber(), product.getDocNumber()) &&
                Objects.equals(getCode(), product.getCode()) &&
                Objects.equals(getName(), product.getName()) &&
                Objects.equals(getProductCategoryID(), product.getProductCategoryID()) &&
                Objects.equals(getProductTypeID(), product.getProductTypeID()) &&
                Objects.equals(getuOMID(), product.getuOMID()) &&
                Objects.equals(getAlternativeSupplierName(), product.getAlternativeSupplierName()) &&
                Objects.equals(getMinimumReorder(), product.getMinimumReorder()) &&
                Objects.equals(getMaximumReorder(), product.getMaximumReorder()) &&
                Objects.equals(getEconomicOrderQty(), product.getEconomicOrderQty()) &&
                Objects.equals(getOpeningQuantity(), product.getOpeningQuantity()) &&
                Objects.equals(getOpeningCost(), product.getOpeningCost()) &&
                Objects.equals(getQuantityOnHandAt(), product.getQuantityOnHandAt()) &&
                Objects.equals(getPurchaseVATID(), product.getPurchaseVATID()) &&
                Objects.equals(getSalesVATID(), product.getSalesVATID()) &&
                Objects.equals(getSalesAccountID(), product.getSalesAccountID()) &&
                Objects.equals(getPurchaseAccountID(), product.getPurchaseAccountID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getProductID(), getBusinessID(), getDocNumber(), getCode(), getName(), getProductCategoryID(), getProductTypeID(), getuOMID(), getAlternativeSupplierName(), getMinimumReorder(), getMaximumReorder(), getEconomicOrderQty(), getOpeningQuantity(), getOpeningCost(), getQuantityOnHandAt(), getPurchaseVATID(), getSalesVATID(), getSalesAccountID(), getPurchaseAccountID());
    }

    @Override
    public String toString() {
        return "Product{" +
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
