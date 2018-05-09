/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 19-Feb-18
 * Time: 11:46 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class ProductAdjustmentDetail extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "productAdjustmentDetailID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    @NotNull
    @Column
    private Integer productAdjustmentDetailID;

    @Column
    private Integer productAdjustmentID;

    @Column
    private Integer productID;

    @Column
    private Double qty;

    @Column
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
        if (!(o instanceof ProductAdjustmentDetail)) return false;
        if (!super.equals(o)) return false;
        ProductAdjustmentDetail that = (ProductAdjustmentDetail) o;
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
        return "ProductAdjustmentDetail{" +
                "productAdjustmentDetailID=" + productAdjustmentDetailID +
                ", productAdjustmentID=" + productAdjustmentID +
                ", productID=" + productID +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
