/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 01:38
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
public class CustomerQuotation extends BaseEntityWithCurrency {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "customerQuotationID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

    private Integer customerQuotationID;
    @Column
    private Integer businessID;
    @Column
    private Date expiryDate;
    @Column
    private String docNumber;
    @Column
    private String customerQuotationNo;
    @Column
    private Integer customerID;
    @Column
    private String customerRef;
    @Column
    private String vatReference;
    @Column
    private Double totalVAT;
    @Column
    private Double totalDiscount;
    @Column
    private Double totalAmount;
    @Column
    private Integer quotationStatus;
    @Column
    private Integer layoutID;
    @Column
    private Integer customerRepresentativeID;
    @Column
    private Double discountPercent;
    @Column
    private String message;

    public Integer getCustomerQuotationID() {
        return customerQuotationID;
    }

    public void setCustomerQuotationID(Integer customerQuotationID) {
        this.customerQuotationID = customerQuotationID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getCustomerQuotationNo() {
        return customerQuotationNo;
    }

    public void setCustomerQuotationNo(String customerQuotationNo) {
        this.customerQuotationNo = customerQuotationNo;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getCustomerRef() {
        return customerRef;
    }

    public void setCustomerRef(String customerRef) {
        this.customerRef = customerRef;
    }

    public String getVatReference() {
        return vatReference;
    }

    public void setVatReference(String vatReference) {
        this.vatReference = vatReference;
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

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getQuotationStatus() {
        return quotationStatus;
    }

    public void setQuotationStatus(Integer quotationStatus) {
        this.quotationStatus = quotationStatus;
    }

    public Integer getLayoutID() {
        return layoutID;
    }

    public void setLayoutID(Integer layoutID) {
        this.layoutID = layoutID;
    }

    public Integer getCustomerRepresentativeID() {
        return customerRepresentativeID;
    }

    public void setCustomerRepresentativeID(Integer customerRepresentativeID) {
        this.customerRepresentativeID = customerRepresentativeID;
    }

    public Double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerQuotation)) return false;
        if (!super.equals(o)) return false;
        CustomerQuotation that = (CustomerQuotation) o;
        return Objects.equals(getCustomerQuotationID(), that.getCustomerQuotationID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getExpiryDate(), that.getExpiryDate()) &&
                Objects.equals(getDocNumber(), that.getDocNumber()) &&
                Objects.equals(getCustomerQuotationNo(), that.getCustomerQuotationNo()) &&
                Objects.equals(getCustomerID(), that.getCustomerID()) &&
                Objects.equals(getCustomerRef(), that.getCustomerRef()) &&
                Objects.equals(getVatReference(), that.getVatReference()) &&
                Objects.equals(getTotalVAT(), that.getTotalVAT()) &&
                Objects.equals(getTotalDiscount(), that.getTotalDiscount()) &&
                Objects.equals(getTotalAmount(), that.getTotalAmount()) &&
                Objects.equals(getQuotationStatus(), that.getQuotationStatus()) &&
                Objects.equals(getLayoutID(), that.getLayoutID()) &&
                Objects.equals(getCustomerRepresentativeID(), that.getCustomerRepresentativeID()) &&
                Objects.equals(getDiscountPercent(), that.getDiscountPercent()) &&
                Objects.equals(getMessage(), that.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCustomerQuotationID(), getBusinessID(), getExpiryDate(), getDocNumber(), getCustomerQuotationNo(), getCustomerID(), getCustomerRef(), getVatReference(), getTotalVAT(), getTotalDiscount(), getTotalAmount(), getQuotationStatus(), getLayoutID(), getCustomerRepresentativeID(), getDiscountPercent(), getMessage());
    }

    @Override
    public String toString() {
        return "CustomerQuotation{" +
                "customerQuotationID=" + customerQuotationID +
                ", businessID=" + businessID +
                ", expiryDate=" + expiryDate +
                ", docNumber='" + docNumber + '\'' +
                ", customerQuotationNo='" + customerQuotationNo + '\'' +
                ", customerID=" + customerID +
                ", customerRef='" + customerRef + '\'' +
                ", vatReference='" + vatReference + '\'' +
                ", totalVAT=" + totalVAT +
                ", totalDiscount=" + totalDiscount +
                ", totalAmount=" + totalAmount +
                ", quotationStatus=" + quotationStatus +
                ", layoutID=" + layoutID +
                ", customerRepresentativeID=" + customerRepresentativeID +
                ", discountPercent=" + discountPercent +
                ", message='" + message + '\'' +
                '}';
    }
}