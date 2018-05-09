/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 22/02/2018
 * Time: 11:44
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.coreEntities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class SupplierCategory extends BaseEntity {
    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "supplierCategoryID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    private Integer supplierCategoryID;
    @Column
    private Integer businessID;
    @Column
    private String categoryName;

    public Integer getSupplierCategoryID() {
        return supplierCategoryID;
    }

    public void setSupplierCategoryID(Integer supplierCategoryID) {
        this.supplierCategoryID = supplierCategoryID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SupplierCategory)) return false;
        if (!super.equals(o)) return false;
        SupplierCategory that = (SupplierCategory) o;
        return Objects.equals(getSupplierCategoryID(), that.getSupplierCategoryID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getCategoryName(), that.getCategoryName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSupplierCategoryID(), getBusinessID(), getCategoryName());
    }

    @Override
    public String toString() {
        return "SupplierCategory{" +
                "supplierCategoryID=" + supplierCategoryID +
                ", businessID=" + businessID +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
