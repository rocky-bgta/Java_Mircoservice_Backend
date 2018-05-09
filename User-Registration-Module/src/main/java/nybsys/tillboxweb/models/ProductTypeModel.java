package nybsys.tillboxweb.models;
import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class ProductTypeModel extends BaseModel {

    private Integer productTypeID;
    private String name;

    public Integer getProductTypeID() {
        return productTypeID;
    }

    public void setProductTypeID(Integer productTypeID) {
        this.productTypeID = productTypeID;
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
        if (!(o instanceof ProductTypeModel)) return false;
        if (!super.equals(o)) return false;
        ProductTypeModel that = (ProductTypeModel) o;
        return Objects.equals(getProductTypeID(), that.getProductTypeID()) &&
                Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getProductTypeID(), getName());
    }

    @Override
    public String toString() {
        return "ProductTypeModel{" +
                "productTypeID=" + productTypeID +
                ", name='" + name + '\'' +
                '}';
    }
}
