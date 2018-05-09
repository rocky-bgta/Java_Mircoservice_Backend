/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 02:21
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntityWithCurrency;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
public class CustomerInvoice extends BaseEntityWithCurrency {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "customerInvoiceID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

    private Integer customerInvoiceID;
    @Column
    private Integer businessID;
    @Column
    private Integer salesQuotationID;
    @Column
    private Integer customerID;
    @Column
    private String docNumber;
    @Column
    private Integer customerInvoiceNo;
    @Column
    private Date invoiceDate;
    @Column
    private Date dueDate;
    @Column
    private Integer allowOnlinePayment;
    @Column
    private Double totalAmount;
    @Column
    private Double totalVAT;
    @Column
    private Double totalDiscount;
    @Column
    private String message;
    @Column
    private Integer paymentStatus;

    public Integer getCustomerInvoiceID() {
        return customerInvoiceID;
    }

    public void setCustomerInvoiceID(Integer customerInvoiceID) {
        this.customerInvoiceID = customerInvoiceID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getSalesQuotationID() {
        return salesQuotationID;
    }

    public void setSalesQuotationID(Integer salesQuotationID) {
        this.salesQuotationID = salesQuotationID;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public Integer getCustomerInvoiceNo() {
        return customerInvoiceNo;
    }

    public void setCustomerInvoiceNo(Integer customerInvoiceNo) {
        this.customerInvoiceNo = customerInvoiceNo;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getAllowOnlinePayment() {
        return allowOnlinePayment;
    }

    public void setAllowOnlinePayment(Integer allowOnlinePayment) {
        this.allowOnlinePayment = allowOnlinePayment;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getTotalVAT() {
        return totalVAT;
    }

    public void setTotalVAT(Double totalVAT) {
        this.totalVAT = totalVAT;
    }

    public Double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(Double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerInvoice)) return false;
        if (!super.equals(o)) return false;
        CustomerInvoice that = (CustomerInvoice) o;
        return Objects.equals(getCustomerInvoiceID(), that.getCustomerInvoiceID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getSalesQuotationID(), that.getSalesQuotationID()) &&
                Objects.equals(getCustomerID(), that.getCustomerID()) &&
                Objects.equals(getDocNumber(), that.getDocNumber()) &&
                Objects.equals(getCustomerInvoiceNo(), that.getCustomerInvoiceNo()) &&
                Objects.equals(getInvoiceDate(), that.getInvoiceDate()) &&
                Objects.equals(getDueDate(), that.getDueDate()) &&
                Objects.equals(getAllowOnlinePayment(), that.getAllowOnlinePayment()) &&
                Objects.equals(getTotalAmount(), that.getTotalAmount()) &&
                Objects.equals(getTotalVAT(), that.getTotalVAT()) &&
                Objects.equals(getTotalDiscount(), that.getTotalDiscount()) &&
                Objects.equals(getMessage(), that.getMessage()) &&
                Objects.equals(getPaymentStatus(), that.getPaymentStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCustomerInvoiceID(), getBusinessID(), getSalesQuotationID(), getCustomerID(), getDocNumber(), getCustomerInvoiceNo(), getInvoiceDate(), getDueDate(), getAllowOnlinePayment(), getTotalAmount(), getTotalVAT(), getTotalDiscount(), getMessage(), getPaymentStatus());
    }

    @Override
    public String toString() {
        return "CustomerInvoice{" +
                "customerInvoiceID=" + customerInvoiceID +
                ", businessID=" + businessID +
                ", salesQuotationID=" + salesQuotationID +
                ", customerID=" + customerID +
                ", docNumber='" + docNumber + '\'' +
                ", customerInvoiceNo=" + customerInvoiceNo +
                ", invoiceDate=" + invoiceDate +
                ", dueDate=" + dueDate +
                ", allowOnlinePayment=" + allowOnlinePayment +
                ", totalAmount=" + totalAmount +
                ", totalVAT=" + totalVAT +
                ", totalDiscount=" + totalDiscount +
                ", message='" + message + '\'' +
                ", paymentStatus=" + paymentStatus +
                '}';
    }
}