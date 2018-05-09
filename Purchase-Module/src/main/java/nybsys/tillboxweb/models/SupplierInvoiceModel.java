/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/23/2018
 * Time: 12:12 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;
import nybsys.tillboxweb.BaseModelWithCurrency;

import java.util.Date;
import java.util.Objects;

public class SupplierInvoiceModel extends BaseModelWithCurrency {
    private Integer supplierInvoiceID;
    private Integer businessID;
    private Integer SupplierID;
    private Integer purchaseOrderID;
    private String docNumber;
    private String supplierInvoiceNo;
    private String deliveryNoteNo;
    private Date date;
    private Double totalAmount;
    private Double totalVAT;
    private Double totalDiscount;
    private String receivedBy;
    private Integer receivedProductStatus;
    private String note;
    private Integer paymentStatus;



    public Integer getSupplierID() {
        return SupplierID;
    }

    public void setSupplierID(Integer supplierID) {
        SupplierID = supplierID;
    }

    public Integer getSupplierInvoiceID() {
        return supplierInvoiceID;
    }

    public void setSupplierInvoiceID(Integer supplierInvoiceID) {
        this.supplierInvoiceID = supplierInvoiceID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getPurchaseOrderID() {
        return purchaseOrderID;
    }

    public void setPurchaseOrderID(Integer purchaseOrderID) {
        this.purchaseOrderID = purchaseOrderID;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getSupplierInvoiceNo() {
        return supplierInvoiceNo;
    }

    public void setSupplierInvoiceNo(String supplierInvoiceNo) {
        this.supplierInvoiceNo = supplierInvoiceNo;
    }

    public String getDeliveryNoteNo() {
        return deliveryNoteNo;
    }

    public void setDeliveryNoteNo(String deliveryNoteNo) {
        this.deliveryNoteNo = deliveryNoteNo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public Integer getReceivedProductStatus() {
        return receivedProductStatus;
    }

    public void setReceivedProductStatus(Integer receivedProductStatus) {
        this.receivedProductStatus = receivedProductStatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
        if (!(o instanceof SupplierInvoiceModel)) return false;
        if (!super.equals(o)) return false;
        SupplierInvoiceModel that = (SupplierInvoiceModel) o;
        return Objects.equals(getSupplierInvoiceID(), that.getSupplierInvoiceID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getSupplierID(), that.getSupplierID()) &&
                Objects.equals(getPurchaseOrderID(), that.getPurchaseOrderID()) &&
                Objects.equals(getDocNumber(), that.getDocNumber()) &&
                Objects.equals(getSupplierInvoiceNo(), that.getSupplierInvoiceNo()) &&
                Objects.equals(getDeliveryNoteNo(), that.getDeliveryNoteNo()) &&
                Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getTotalAmount(), that.getTotalAmount()) &&
                Objects.equals(getTotalVAT(), that.getTotalVAT()) &&
                Objects.equals(getTotalDiscount(), that.getTotalDiscount()) &&
                Objects.equals(getReceivedBy(), that.getReceivedBy()) &&
                Objects.equals(getReceivedProductStatus(), that.getReceivedProductStatus()) &&
                Objects.equals(getNote(), that.getNote()) &&
                Objects.equals(getPaymentStatus(), that.getPaymentStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSupplierInvoiceID(), getBusinessID(), getSupplierID(), getPurchaseOrderID(), getDocNumber(), getSupplierInvoiceNo(), getDeliveryNoteNo(), getDate(), getTotalAmount(), getTotalVAT(), getTotalDiscount(), getReceivedBy(), getReceivedProductStatus(), getNote(), getPaymentStatus());
    }

    @Override
    public String toString() {
        return "SupplierInvoiceModel{" +
                "supplierInvoiceID=" + supplierInvoiceID +
                ", businessID=" + businessID +
                ", SupplierID=" + SupplierID +
                ", purchaseOrderID=" + purchaseOrderID +
                ", docNumber='" + docNumber + '\'' +
                ", supplierInvoiceNo='" + supplierInvoiceNo + '\'' +
                ", deliveryNoteNo='" + deliveryNoteNo + '\'' +
                ", date=" + date +
                ", totalAmount=" + totalAmount +
                ", totalVAT=" + totalVAT +
                ", totalDiscount=" + totalDiscount +
                ", receivedBy='" + receivedBy + '\'' +
                ", receivedProductStatus=" + receivedProductStatus +
                ", note='" + note + '\'' +
                ", paymentStatus=" + paymentStatus +
                '}';
    }
}
