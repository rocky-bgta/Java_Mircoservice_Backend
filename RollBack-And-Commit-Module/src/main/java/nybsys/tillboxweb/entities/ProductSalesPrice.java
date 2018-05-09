/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/14/2018
 * Time: 10:36 AM
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
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
public class ProductSalesPrice extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "productSalesPriceID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    @NotNull
    @Column
    private Integer productSalesPriceID;
    @Column
    @NotNull(message = "business id  cannot be empty")
    private Integer businessID;
    @Column
    @NotNull(message = "product id  cannot be empty")
    private Integer productID;
    private Date date;
    private Integer priceCategoryID;
    private Double salesPrice;

    public Integer getProductSalesPriceID() {
        return productSalesPriceID;
    }

    public void setProductSalesPriceID(Integer productSalesPriceID) {
        this.productSalesPriceID = productSalesPriceID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getPriceCategoryID() {
        return priceCategoryID;
    }

    public void setPriceCategoryID(Integer priceCategoryID) {
        this.priceCategoryID = priceCategoryID;
    }

    public Double getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(Double salesPrice) {
        this.salesPrice = salesPrice;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductSalesPrice)) return false;
        if (!super.equals(o)) return false;
        ProductSalesPrice that = (ProductSalesPrice) o;
        return Objects.equals(getProductSalesPriceID(), that.getProductSalesPriceID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getProductID(), that.getProductID()) &&
                Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getPriceCategoryID(), that.getPriceCategoryID()) &&
                Objects.equals(getSalesPrice(), that.getSalesPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getProductSalesPriceID(), getBusinessID(), getProductID(), getDate(), getPriceCategoryID(), getSalesPrice());
    }

    @Override
    public String toString() {
        return "ProductSalesPrice{" +
                "productSalesPriceID=" + productSalesPriceID +
                ", businessID=" + businessID +
                ", productID=" + productID +
                ", date=" + date +
                ", priceCategoryID=" + priceCategoryID +
                ", salesPrice=" + salesPrice +
                '}';
    }
}


