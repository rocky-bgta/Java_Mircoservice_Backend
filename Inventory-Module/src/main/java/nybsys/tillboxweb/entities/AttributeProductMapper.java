/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 13/02/2018
 * Time: 04:02
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class AttributeProductMapper extends BaseEntity{

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "attributeProductMapperID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    @NotNull
    @Column
    private Integer attributeProductMapperID;
    @Column
    private Integer businessID;
    @Column
    private Integer productID;
    @Column
    private Integer productAttributeID;
    @Column
    private Integer productAttributeValueID;

    public Integer getAttributeProductMapperID() {
        return attributeProductMapperID;
    }

    public void setAttributeProductMapperID(Integer attributeProductMapperID) {
        this.attributeProductMapperID = attributeProductMapperID;
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
        if (!(o instanceof AttributeProductMapper)) return false;
        if (!super.equals(o)) return false;
        AttributeProductMapper that = (AttributeProductMapper) o;
        return Objects.equals(getAttributeProductMapperID(), that.getAttributeProductMapperID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getProductID(), that.getProductID()) &&
                Objects.equals(getProductAttributeID(), that.getProductAttributeID()) &&
                Objects.equals(getProductAttributeValueID(), that.getProductAttributeValueID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAttributeProductMapperID(), getBusinessID(), getProductID(), getProductAttributeID(), getProductAttributeValueID());
    }

    @Override
    public String toString() {
        return "AttributeProductMapper{" +
                "attributeProductMapperID=" + attributeProductMapperID +
                ", businessID=" + businessID +
                ", productID=" + productID +
                ", productAttributeID=" + productAttributeID +
                ", productAttributeValueID=" + productAttributeValueID +
                '}';
    }
}
