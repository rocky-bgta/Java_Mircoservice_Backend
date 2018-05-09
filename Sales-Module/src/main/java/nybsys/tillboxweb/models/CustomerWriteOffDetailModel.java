/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 13/03/2018
 * Time: 4:52
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class CustomerWriteOffDetailModel extends BaseModel {
    private Integer customerWriteOffDetailID;
    private Integer customerWriteOffID;
    private Integer customerInvoiceID;
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
        if (!(o instanceof CustomerWriteOffDetailModel)) return false;
        if (!super.equals(o)) return false;
        CustomerWriteOffDetailModel that = (CustomerWriteOffDetailModel) o;
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
        return "CustomerWriteOffDetailModel{" +
                "customerWriteOffDetailID=" + customerWriteOffDetailID +
                ", customerWriteOffID=" + customerWriteOffID +
                ", customerInvoiceID=" + customerInvoiceID +
                ", amount=" + amount +
                '}';
    }
}
