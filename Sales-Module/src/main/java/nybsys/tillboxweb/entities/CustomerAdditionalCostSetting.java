/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 03:14
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
import java.util.Objects;

@Entity
public class CustomerAdditionalCostSetting extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "customerAdditionalCostSettingID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

    private Integer customerAdditionalCostSettingID;
    @Column
    private Integer businessID;
    @Column
    private String description;

    public Integer getCustomerAdditionalCostSettingID() {
        return customerAdditionalCostSettingID;
    }

    public void setCustomerAdditionalCostSettingID(Integer customerAdditionalCostSettingID) {
        this.customerAdditionalCostSettingID = customerAdditionalCostSettingID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerAdditionalCostSetting)) return false;
        if (!super.equals(o)) return false;
        CustomerAdditionalCostSetting that = (CustomerAdditionalCostSetting) o;
        return Objects.equals(getCustomerAdditionalCostSettingID(), that.getCustomerAdditionalCostSettingID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCustomerAdditionalCostSettingID(), getBusinessID(), getDescription());
    }

    @Override
    public String toString() {
        return "CustomerAdditionalCostSetting{" +
                "customerAdditionalCostSettingID=" + customerAdditionalCostSettingID +
                ", businessID=" + businessID +
                ", description='" + description + '\'' +
                '}';
    }
}