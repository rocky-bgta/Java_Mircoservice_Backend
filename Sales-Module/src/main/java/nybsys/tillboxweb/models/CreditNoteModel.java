/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 02:49
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModelWithCurrency;

import java.util.Date;
import java.util.Objects;

public class CreditNoteModel extends BaseModelWithCurrency {
    private Integer creditNoteID;
    private Integer businessID;
    private Integer customerInvoiceID;
    private Integer salesReturnID;
    private String creditNoteCode;
    private Date creditNoteDate;
    private Date validDate;
    private Double amount;

    public Integer getCreditNoteID() {
        return creditNoteID;
    }

    public void setCreditNoteID(Integer creditNoteID) {
        this.creditNoteID = creditNoteID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getCustomerInvoiceID() {
        return customerInvoiceID;
    }

    public void setCustomerInvoiceID(Integer customerInvoiceID) {
        this.customerInvoiceID = customerInvoiceID;
    }

    public Integer getSalesReturnID() {
        return salesReturnID;
    }

    public void setSalesReturnID(Integer salesReturnID) {
        this.salesReturnID = salesReturnID;
    }

    public String getCreditNoteCode() {
        return creditNoteCode;
    }

    public void setCreditNoteCode(String creditNoteCode) {
        this.creditNoteCode = creditNoteCode;
    }

    public Date getCreditNoteDate() {
        return creditNoteDate;
    }

    public void setCreditNoteDate(Date creditNoteDate) {
        this.creditNoteDate = creditNoteDate;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
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
        if (!(o instanceof CreditNoteModel)) return false;
        if (!super.equals(o)) return false;
        CreditNoteModel that = (CreditNoteModel) o;
        return Objects.equals(getCreditNoteID(), that.getCreditNoteID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getCustomerInvoiceID(), that.getCustomerInvoiceID()) &&
                Objects.equals(getSalesReturnID(), that.getSalesReturnID()) &&
                Objects.equals(getCreditNoteCode(), that.getCreditNoteCode()) &&
                Objects.equals(getCreditNoteDate(), that.getCreditNoteDate()) &&
                Objects.equals(getValidDate(), that.getValidDate()) &&
                Objects.equals(getAmount(), that.getAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCreditNoteID(), getBusinessID(), getCustomerInvoiceID(), getSalesReturnID(), getCreditNoteCode(), getCreditNoteDate(), getValidDate(), getAmount());
    }

    @Override
    public String toString() {
        return "CreditNoteModel{" +
                "creditNoteID=" + creditNoteID +
                ", businessID=" + businessID +
                ", customerInvoiceID=" + customerInvoiceID +
                ", salesReturnID=" + salesReturnID +
                ", creditNoteCode='" + creditNoteCode + '\'' +
                ", creditNoteDate=" + creditNoteDate +
                ", validDate=" + validDate +
                ", amount=" + amount +
                '}';
    }
}
