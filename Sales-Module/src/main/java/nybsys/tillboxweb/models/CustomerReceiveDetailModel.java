/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 13-Mar-18
 * Time: 10:56 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class CustomerReceiveDetailModel extends BaseModel {
    private Integer customerReceiveDetailID;
    private Integer customerReceiveID;
    private Integer customerInvoiceID;
    private Double amount;
    private Double discount;
    private Integer receiveStatus;

    public Integer getCustomerReceiveDetailID() {
        return customerReceiveDetailID;
    }

    public void setCustomerReceiveDetailID(Integer customerReceiveDetailID) {
        this.customerReceiveDetailID = customerReceiveDetailID;
    }

    public Integer getCustomerReceiveID() {
        return customerReceiveID;
    }

    public void setCustomerReceiveID(Integer customerReceiveID) {
        this.customerReceiveID = customerReceiveID;
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

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Integer getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(Integer receiveStatus) {
        this.receiveStatus = receiveStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerReceiveDetailModel)) return false;
        if (!super.equals(o)) return false;
        CustomerReceiveDetailModel that = (CustomerReceiveDetailModel) o;
        return Objects.equals(getCustomerReceiveDetailID(), that.getCustomerReceiveDetailID()) &&
                Objects.equals(getCustomerReceiveID(), that.getCustomerReceiveID()) &&
                Objects.equals(getCustomerInvoiceID(), that.getCustomerInvoiceID()) &&
                Objects.equals(getAmount(), that.getAmount()) &&
                Objects.equals(getDiscount(), that.getDiscount()) &&
                Objects.equals(getReceiveStatus(), that.getReceiveStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCustomerReceiveDetailID(), getCustomerReceiveID(), getCustomerInvoiceID(), getAmount(), getDiscount(), getReceiveStatus());
    }

    @Override
    public String toString() {
        return "CustomerReceiveDetailModel{" +
                "customerReceiveDetailID=" + customerReceiveDetailID +
                ", customerReceiveID=" + customerReceiveID +
                ", customerInvoiceID=" + customerInvoiceID +
                ", amount=" + amount +
                ", discount=" + discount +
                ", receiveStatus=" + receiveStatus +
                '}';
    }
}
