package nybsys.tillboxweb.models;
import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class BusinessTypeModel extends BaseModel {
    private Integer businessTypeID;
    private String value;

    public Integer getBusinessTypeID() {
        return businessTypeID;
    }

    public void setBusinessTypeID(Integer businessTypeID) {
        this.businessTypeID = businessTypeID;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BusinessTypeModel)) return false;
        if (!super.equals(o)) return false;
        BusinessTypeModel that = (BusinessTypeModel) o;
        return Objects.equals(getBusinessTypeID(), that.getBusinessTypeID()) &&
                Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getBusinessTypeID(), getValue());
    }

    @Override
    public String toString() {
        return "BusinessTypeModel{" +
                "businessTypeID=" + businessTypeID +
                ", value='" + value + '\'' +
                '}';
    }
}
