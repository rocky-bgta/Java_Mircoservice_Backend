/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 02:47
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntityWithCurrency;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
public class CustomerReturn extends BaseEntityWithCurrency {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "customerReturnID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

    private Integer customerReturnID;
    private Integer businessID;
    private String docNumber;
    private String customerReturnNo;
    private Integer customerInvoiceID;
    private Date returnDate;
    private Integer customerID;
    private Integer totalAmount;
    private Double totalDiscount;
    private Double totalVAT;
    private String message;
    private String comment;
    private Integer returnStatus;
    private Double paidAmount;

    public Integer getCustomerReturnID() {
        return customerReturnID;
    }

    public void setCustomerReturnID(Integer customerReturnID) {
        this.customerReturnID = customerReturnID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getCustomerReturnNo() {
        return customerReturnNo;
    }

    public void setCustomerReturnNo(String customerReturnNo) {
        this.customerReturnNo = customerReturnNo;
    }

    public Integer getCustomerInvoiceID() {
        return customerInvoiceID;
    }

    public void setCustomerInvoiceID(Integer customerInvoiceID) {
        this.customerInvoiceID = customerInvoiceID;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(Double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public Double getTotalVAT() {
        return totalVAT;
    }

    public void setTotalVAT(Double totalVAT) {
        this.totalVAT = totalVAT;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(Integer returnStatus) {
        this.returnStatus = returnStatus;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerReturn)) return false;
        if (!super.equals(o)) return false;
        CustomerReturn that = (CustomerReturn) o;
        return Objects.equals(getCustomerReturnID(), that.getCustomerReturnID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getDocNumber(), that.getDocNumber()) &&
                Objects.equals(getCustomerReturnNo(), that.getCustomerReturnNo()) &&
                Objects.equals(getCustomerInvoiceID(), that.getCustomerInvoiceID()) &&
                Objects.equals(getReturnDate(), that.getReturnDate()) &&
                Objects.equals(getCustomerID(), that.getCustomerID()) &&
                Objects.equals(getTotalAmount(), that.getTotalAmount()) &&
                Objects.equals(getTotalDiscount(), that.getTotalDiscount()) &&
                Objects.equals(getTotalVAT(), that.getTotalVAT()) &&
                Objects.equals(getMessage(), that.getMessage()) &&
                Objects.equals(getComment(), that.getComment()) &&
                Objects.equals(getReturnStatus(), that.getReturnStatus()) &&
                Objects.equals(getPaidAmount(), that.getPaidAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCustomerReturnID(), getBusinessID(), getDocNumber(), getCustomerReturnNo(), getCustomerInvoiceID(), getReturnDate(), getCustomerID(), getTotalAmount(), getTotalDiscount(), getTotalVAT(), getMessage(), getComment(), getReturnStatus(), getPaidAmount());
    }

    @Override
    public String toString() {
        return "CustomerReturn{" +
                "customerReturnID=" + customerReturnID +
                ", businessID=" + businessID +
                ", docNumber='" + docNumber + '\'' +
                ", customerReturnNo='" + customerReturnNo + '\'' +
                ", customerInvoiceID=" + customerInvoiceID +
                ", returnDate=" + returnDate +
                ", customerID=" + customerID +
                ", totalAmount=" + totalAmount +
                ", totalDiscount=" + totalDiscount +
                ", totalVAT=" + totalVAT +
                ", message='" + message + '\'' +
                ", comment='" + comment + '\'' +
                ", returnStatus=" + returnStatus +
                ", paidAmount=" + paidAmount +
                '}';
    }
}