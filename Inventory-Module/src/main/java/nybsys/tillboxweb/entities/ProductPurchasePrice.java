/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/14/2018
 * Time: 10:35 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.entities;
import nybsys.tillboxweb.BaseEntity;
import nybsys.tillboxweb.models.ProductPurchasePriceModel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
public class ProductPurchasePrice extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "productPurchasePriceID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    @NotNull
    @Column
    private Integer productPurchasePriceID;
    @Column
    @NotNull(message = "business id  cannot be empty")
    private Integer businessID;
    private Date date;
    @Column
    @NotNull(message = "product id  cannot be empty")
    private Integer productID;

    private Double  purchasePrice;

    public Integer getProductPurchasePriceID() {
        return productPurchasePriceID;
    }

    public void setProductPurchasePriceID(Integer productPurchasePriceID) {
        this.productPurchasePriceID = productPurchasePriceID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductPurchasePrice)) return false;
        if (!super.equals(o)) return false;
        ProductPurchasePrice that = (ProductPurchasePrice) o;
        return Objects.equals(getProductPurchasePriceID(), that.getProductPurchasePriceID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getProductID(), that.getProductID()) &&
                Objects.equals(getPurchasePrice(), that.getPurchasePrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getProductPurchasePriceID(), getBusinessID(), getDate(), getProductID(), getPurchasePrice());
    }

    @Override
    public String toString() {
        return "ProductPurchasePrice{" +
                "productPurchasePriceID=" + productPurchasePriceID +
                ", businessID=" + businessID +
                ", date=" + date +
                ", productID=" + productID +
                ", purchasePrice=" + purchasePrice +
                '}';
    }
}
