/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/23/2018
 * Time: 12:28 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;
import nybsys.tillboxweb.BaseModelWithCurrency;

import java.util.Date;
import java.util.Objects;

public class SupplierReturnModel extends BaseModelWithCurrency {

    private Integer supplierReturnID;
    private Integer businessID;
    private Integer purchaseOrderID;
    private String supplierReturnNumber;
    private String docNumber;
    private Integer supplierID;
    private Double totalAmount;
    private Double totalVAT;
    private Double totalDiscount;
    private Date returnDate;

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(Double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public Integer getSupplierReturnID() {
        return supplierReturnID;
    }

    public void setSupplierReturnID(Integer supplierReturnID) {
        this.supplierReturnID = supplierReturnID;
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

    public String getSupplierReturnNumber() {
        return supplierReturnNumber;
    }

    public void setSupplierReturnNumber(String supplierReturnNumber) {
        this.supplierReturnNumber = supplierReturnNumber;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public Integer getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(Integer supplierID) {
        this.supplierID = supplierID;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SupplierReturnModel)) return false;
        if (!super.equals(o)) return false;
        SupplierReturnModel that = (SupplierReturnModel) o;
        return Objects.equals(getSupplierReturnID(), that.getSupplierReturnID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getPurchaseOrderID(), that.getPurchaseOrderID()) &&
                Objects.equals(getSupplierReturnNumber(), that.getSupplierReturnNumber()) &&
                Objects.equals(getDocNumber(), that.getDocNumber()) &&
                Objects.equals(getSupplierID(), that.getSupplierID()) &&
                Objects.equals(getTotalAmount(), that.getTotalAmount()) &&
                Objects.equals(getTotalVAT(), that.getTotalVAT());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSupplierReturnID(), getBusinessID(), getPurchaseOrderID(), getSupplierReturnNumber(), getDocNumber(), getSupplierID(), getTotalAmount(), getTotalVAT());
    }

    @Override
    public String toString() {
        return "SupplierReturnModel{" +
                "supplierReturnID=" + supplierReturnID +
                ", businessID=" + businessID +
                ", purchaseOrderID=" + purchaseOrderID +
                ", supplierReturnNumber='" + supplierReturnNumber + '\'' +
                ", docNumber='" + docNumber + '\'' +
                ", supplierID=" + supplierID +
                ", totalAmount=" + totalAmount +
                ", totalVAT=" + totalVAT +
                '}';
    }
}
