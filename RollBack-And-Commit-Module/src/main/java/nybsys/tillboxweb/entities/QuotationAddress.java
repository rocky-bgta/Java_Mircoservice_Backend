/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 04:05
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
public class QuotationAddress extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "quotationAddressID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

    private Integer quotationAddressID;
    @Column
    private Integer businessID;
    @Column
    private Integer customerAddressTypeID;
    @Column
    private Integer customerQuotationID;
    @Column
    private Integer customerAddressID;

    public Integer getQuotationAddressID() {
        return quotationAddressID;
    }

    public void setQuotationAddressID(Integer quotationAddressID) {
        this.quotationAddressID = quotationAddressID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getCustomerAddressTypeID() {
        return customerAddressTypeID;
    }

    public void setCustomerAddressTypeID(Integer customerAddressTypeID) {
        this.customerAddressTypeID = customerAddressTypeID;
    }

    public Integer getCustomerQuotationID() {
        return customerQuotationID;
    }

    public void setCustomerQuotationID(Integer customerQuotationID) {
        this.customerQuotationID = customerQuotationID;
    }

    public Integer getCustomerAddressID() {
        return customerAddressID;
    }

    public void setCustomerAddressID(Integer customerAddressID) {
        this.customerAddressID = customerAddressID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuotationAddress)) return false;
        if (!super.equals(o)) return false;
        QuotationAddress that = (QuotationAddress) o;
        return Objects.equals(getQuotationAddressID(), that.getQuotationAddressID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getCustomerAddressTypeID(), that.getCustomerAddressTypeID()) &&
                Objects.equals(getCustomerQuotationID(), that.getCustomerQuotationID()) &&
                Objects.equals(getCustomerAddressID(), that.getCustomerAddressID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getQuotationAddressID(), getBusinessID(), getCustomerAddressTypeID(), getCustomerQuotationID(), getCustomerAddressID());
    }

    @Override
    public String toString() {
        return "QuotationAddress{" +
                "quotationAddressID=" + quotationAddressID +
                ", businessID=" + businessID +
                ", customerAddressTypeID=" + customerAddressTypeID +
                ", customerQuotationID=" + customerQuotationID +
                ", customerAddressID=" + customerAddressID +
                '}';
    }
}