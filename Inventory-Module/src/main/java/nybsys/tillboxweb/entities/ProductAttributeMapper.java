/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/14/2018
 * Time: 10:26 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.entities;
import nybsys.tillboxweb.BaseEntity;
import nybsys.tillboxweb.models.ProductAttributeMapperModel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class ProductAttributeMapper extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "productAttributeMapperID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    @NotNull
    @Column
    private Integer productAttributeMapperID;

    @Column
    @NotNull(message = "business id  cannot be empty")
    private Integer businessID;
    @Column
    @NotNull(message = "product id  cannot be empty")
    private Integer productID;
    @Column
    @NotNull(message = "product attribute id  cannot be empty")
    private Integer productAttributeID;
    @Column
    @NotNull(message = "product attribute value id  cannot be empty")
    private Integer productAttributeValueID;

    public Integer getProductAttributeMapperID() {
        return productAttributeMapperID;
    }

    public void setProductAttributeMapperID(Integer productAttributeMapperID) {
        this.productAttributeMapperID = productAttributeMapperID;
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

    public Integer getProductAttributeID() {
        return productAttributeID;
    }

    public void setProductAttributeID(Integer productAttributeID) {
        this.productAttributeID = productAttributeID;
    }

    public Integer getProductAttributeValueID() {
        return productAttributeValueID;
    }

    public void setProductAttributeValueID(Integer productAttributeValueID) {
        this.productAttributeValueID = productAttributeValueID;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductAttributeMapper)) return false;
        if (!super.equals(o)) return false;
        ProductAttributeMapper that = (ProductAttributeMapper) o;
        return Objects.equals(getProductAttributeMapperID(), that.getProductAttributeMapperID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getProductID(), that.getProductID()) &&
                Objects.equals(getProductAttributeID(), that.getProductAttributeID()) &&
                Objects.equals(getProductAttributeValueID(), that.getProductAttributeValueID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getProductAttributeMapperID(), getBusinessID(), getProductID(), getProductAttributeID(), getProductAttributeValueID());
    }

    @Override
    public String toString() {
        return "ProductAttributeMapper{" +
                "productAttributeMapperID=" + productAttributeMapperID +
                ", businessID=" + businessID +
                ", productID=" + productID +
                ", productAttributeID=" + productAttributeID +
                ", productAttributeValueID=" + productAttributeValueID +
                '}';
    }
}
