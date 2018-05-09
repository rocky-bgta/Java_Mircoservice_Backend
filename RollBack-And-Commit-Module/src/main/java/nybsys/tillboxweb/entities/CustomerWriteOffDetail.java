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
public class CustomerWriteOffDetail extends BaseEntity {
    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "customerWriteOffDetailID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    private Integer customerWriteOffDetailID;
    @Column
    private Integer customerWriteOffID;
    @Column
    private Integer customerInvoiceID;
    @Column
    private Double amount;

    public Integer getCustomerWriteOffDetailID() {
        return customerWriteOffDetailID;
    }

    public void setCustomerWriteOffDetailID(Integer customerWriteOffDetailID) {
        this.customerWriteOffDetailID = customerWriteOffDetailID;
    }

    public Integer getCustomerWriteOffID() {
        return customerWriteOffID;
    }

    public void setCustomerWriteOffID(Integer customerWriteOffID) {
        this.customerWriteOffID = customerWriteOffID;
    }

    public Integer getCustomerInvoiceID() {
        return customerInvoiceID;
    }

    public void setCustomerInvoiceID(Integer customerInvoiceID) {
        this.customerInvoiceID = customerInvoiceID;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerWriteOffDetail)) return false;
        if (!super.equals(o)) return false;
        CustomerWriteOffDetail that = (CustomerWriteOffDetail) o;
        return Objects.equals(getCustomerWriteOffDetailID(), that.getCustomerWriteOffDetailID()) &&
                Objects.equals(getCustomerWriteOffID(), that.getCustomerWriteOffID()) &&
                Objects.equals(getCustomerInvoiceID(), that.getCustomerInvoiceID()) &&
                Objects.equals(getAmount(), that.getAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCustomerWriteOffDetailID(), getCustomerWriteOffID(), getCustomerInvoiceID(), getAmount());
    }

    @Override
    public String toString() {
        return "CustomerWriteOffDetail{" +
                "customerWriteOffDetailID=" + customerWriteOffDetailID +
                ", customerWriteOffID=" + customerWriteOffID +
                ", customerInvoiceID=" + customerInvoiceID +
                ", amount=" + amount +
                '}';
    }
}
