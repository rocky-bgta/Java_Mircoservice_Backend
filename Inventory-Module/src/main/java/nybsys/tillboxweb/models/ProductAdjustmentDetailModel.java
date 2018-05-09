/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 19-Feb-18
 * Time: 4:22 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class ProductAdjustmentDetailModel extends BaseModel {


    private Integer productAdjustmentDetailID;
    private Integer productAdjustmentID;
    private Integer productID;
    private Double qty;
    private Double unitPrice;

    public Integer getProductAdjustmentDetailID() {
        return productAdjustmentDetailID;
    }

    public void setProductAdjustmentDetailID(Integer productAdjustmentDetailID) {
        this.productAdjustmentDetailID = productAdjustmentDetailID;
    }

    public Integer getProductAdjustmentID() {
        return productAdjustmentID;
    }


    public void setProductAdjustmentID(Integer productAdjustmentID) {
        this.productAdjustmentID = productAdjustmentID;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductAdjustmentDetailModel)) return false;
        if (!super.equals(o)) return false;
        ProductAdjustmentDetailModel that = (ProductAdjustmentDetailModel) o;
        return Objects.equals(getProductAdjustmentDetailID(), that.getProductAdjustmentDetailID()) &&
                Objects.equals(getProductAdjustmentID(), that.getProductAdjustmentID()) &&
                Objects.equals(getProductID(), that.getProductID()) &&
                Objects.equals(getQty(), that.getQty()) &&
                Objects.equals(getUnitPrice(), that.getUnitPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getProductAdjustmentDetailID(), getProductAdjustmentID(), getProductID(), getQty(), getUnitPrice());
    }

    @Override
    public String toString() {
        return "ProductAdjustmentDetailModel{" +
                "productAdjustmentDetailID=" + productAdjustmentDetailID +
                ", productAdjustmentID=" + productAdjustmentID +
                ", productID=" + productID +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
