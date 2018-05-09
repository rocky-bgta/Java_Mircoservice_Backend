/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 12/03/2018
 * Time: 12:01
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.TillBoxWebEntities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class DefaultCustomerCategory extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "customerCategoryID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

    private Integer customerCategoryID;
    @Column
    private Integer businessID;
    @Column
    private String categoryName;

    public Integer getCustomerCategoryID() {
        return customerCategoryID;
    }

    public void setCustomerCategoryID(Integer customerCategoryID) {
        this.customerCategoryID = customerCategoryID;
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
        if (!(o instanceof DefaultCustomerCategory)) return false;
        if (!super.equals(o)) return false;
        DefaultCustomerCategory that = (DefaultCustomerCategory) o;
        return Objects.equals(getCustomerCategoryID(), that.getCustomerCategoryID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getCategoryName(), that.getCategoryName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCustomerCategoryID(), getBusinessID(), getCategoryName());
    }

    @Override
    public String toString() {
        return "CustomerCategory{" +
                "customerCategoryID=" + customerCategoryID +
                ", businessID=" + businessID +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}