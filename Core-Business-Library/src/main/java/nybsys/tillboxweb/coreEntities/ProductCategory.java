/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 13/02/2018
 * Time: 04:01
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.coreEntities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class ProductCategory extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "productCategoryID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    @NotNull
    @Column
    private Integer productCategoryID;
    @Column
    private Integer businessID;
    @Column
    private String name;

    public Integer getProductCategoryID() {
        return productCategoryID;
    }

    public void setProductCategoryID(Integer productCategoryID) {
        this.productCategoryID = productCategoryID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductCategory)) return false;
        if (!super.equals(o)) return false;
        ProductCategory that = (ProductCategory) o;
        return Objects.equals(getProductCategoryID(), that.getProductCategoryID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getProductCategoryID(), getBusinessID(), getName());
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "productCategoryID=" + productCategoryID +
                ", businessID=" + businessID +
                ", name='" + name + '\'' +
                '}';
    }
}
