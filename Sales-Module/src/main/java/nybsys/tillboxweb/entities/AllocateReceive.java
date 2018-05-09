/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 21-Mar-18
 * Time: 4:33 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
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
public class AllocateReceive extends BaseEntityWithCurrency {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "allocateReceiveID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

    private Integer allocateReceiveID;
    private Integer businessID;
    private String docNumber;
    private String allocateReceiveNo;
    private Date date;
    private Integer referenceType;
    private Integer referenceID;
    private Integer customerInvoiceID;
    private Integer customerReceiveID;
    private Double amount;
    private Integer paymentID;

    public Integer getAllocateReceiveID() {
        return allocateReceiveID;
    }

    public void setAllocateReceiveID(Integer allocateReceiveID) {
        this.allocateReceiveID = allocateReceiveID;
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

    public String getAllocateReceiveNo() {
        return allocateReceiveNo;
    }

    public void setAllocateReceiveNo(String allocateReceiveNo) {
        this.allocateReceiveNo = allocateReceiveNo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(Integer referenceType) {
        this.referenceType = referenceType;
    }

    public Integer getReferenceID() {
        return referenceID;
    }

    public void setReferenceID(Integer referenceID) {
        this.referenceID = referenceID;
    }

    public Integer getCustomerInvoiceID() {
        return customerInvoiceID;
    }

    public void setCustomerInvoiceID(Integer customerInvoiceID) {
        this.customerInvoiceID = customerInvoiceID;
    }

    public Integer getCustomerReceiveID() {
        return customerReceiveID;
    }

    public void setCustomerReceiveID(Integer customerReceiveID) {
        this.customerReceiveID = customerReceiveID;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(Integer paymentID) {
        this.paymentID = paymentID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AllocateReceive)) return false;
        if (!super.equals(o)) return false;
        AllocateReceive that = (AllocateReceive) o;
        return Objects.equals(getAllocateReceiveID(), that.getAllocateReceiveID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getDocNumber(), that.getDocNumber()) &&
                Objects.equals(getAllocateReceiveNo(), that.getAllocateReceiveNo()) &&
                Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getReferenceType(), that.getReferenceType()) &&
                Objects.equals(getReferenceID(), that.getReferenceID()) &&
                Objects.equals(getCustomerInvoiceID(), that.getCustomerInvoiceID()) &&
                Objects.equals(getCustomerReceiveID(), that.getCustomerReceiveID()) &&
                Objects.equals(getAmount(), that.getAmount()) &&
                Objects.equals(getPaymentID(), that.getPaymentID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAllocateReceiveID(), getBusinessID(), getDocNumber(), getAllocateReceiveNo(), getDate(), getReferenceType(), getReferenceID(), getCustomerInvoiceID(), getCustomerReceiveID(), getAmount(), getPaymentID());
    }

    @Override
    public String toString() {
        return "AllocateReceive{" +
                "allocateReceiveID=" + allocateReceiveID +
                ", businessID=" + businessID +
                ", docNumber='" + docNumber + '\'' +
                ", allocateReceiveNo='" + allocateReceiveNo + '\'' +
                ", date=" + date +
                ", referenceType=" + referenceType +
                ", referenceID=" + referenceID +
                ", customerInvoiceID=" + customerInvoiceID +
                ", customerReceiveID=" + customerReceiveID +
                ", amount=" + amount +
                ", paymentID=" + paymentID +
                '}';
    }
}