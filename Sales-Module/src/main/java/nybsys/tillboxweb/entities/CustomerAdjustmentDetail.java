/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 22/02/2018
 * Time: 11:42
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
public class CustomerAdjustmentDetail extends BaseEntity {
    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "customerAdjustmentDetailID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    private Integer customerAdjustmentDetailID;
    @Column
    private Integer customerAdjustmentID;
    @Column
    private Integer customerInvoiceID;
    @Column
    private Double adjustAmount;

    public Integer getCustomerAdjustmentDetailID() {
        return customerAdjustmentDetailID;
    }

    public void setCustomerAdjustmentDetailID(Integer customerAdjustmentDetailID) {
        this.customerAdjustmentDetailID = customerAdjustmentDetailID;
    }

    public Integer getCustomerAdjustmentID() {
        return customerAdjustmentID;
    }

    public void setCustomerAdjustmentID(Integer customerAdjustmentID) {
        this.customerAdjustmentID = customerAdjustmentID;
    }

    public Integer getCustomerInvoiceID() {
        return customerInvoiceID;
    }

    public void setCustomerInvoiceID(Integer customerInvoiceID) {
        this.customerInvoiceID = customerInvoiceID;
    }

    public Double getAdjustAmount() {
        return adjustAmount;
    }

    public void setAdjustAmount(Double adjustAmount) {
        this.adjustAmount = adjustAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerAdjustmentDetail)) return false;
        if (!super.equals(o)) return false;
        CustomerAdjustmentDetail that = (CustomerAdjustmentDetail) o;
        return Objects.equals(getCustomerAdjustmentDetailID(), that.getCustomerAdjustmentDetailID()) &&
                Objects.equals(getCustomerAdjustmentID(), that.getCustomerAdjustmentID()) &&
                Objects.equals(getCustomerInvoiceID(), that.getCustomerInvoiceID()) &&
                Objects.equals(getAdjustAmount(), that.getAdjustAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCustomerAdjustmentDetailID(), getCustomerAdjustmentID(), getCustomerInvoiceID(), getAdjustAmount());
    }

    @Override
    public String toString() {
        return "CustomerAdjustmentDetail{" +
                "customerAdjustmentDetailID=" + customerAdjustmentDetailID +
                ", customerAdjustmentID=" + customerAdjustmentID +
                ", customerInvoiceID=" + customerInvoiceID +
                ", adjustAmount=" + adjustAmount +
                '}';
    }
}
