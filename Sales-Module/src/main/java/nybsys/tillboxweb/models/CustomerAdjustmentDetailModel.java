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

public class CustomerAdjustmentDetailModel extends BaseModel {
    private Integer customerAdjustmentDetailID;
    private Integer customerAdjustmentID;
    private Integer customerInvoiceID;
    private Double adjustAmount;

    public Integer getCustomerAdjustmentDetailID() {
        return customerAdjustmentDetailID;
    }

    public void setCustomerAdjustmentDetailID(Integer customerAdjustmentDetailID) {
        this.customerAdjustmentDetailID = customerAdjustmentDetailID;
    }

    public Integer getCustomerAdjustmentID() {
        return customerAdjustmentID;
    }

    public void setCustomerAdjustmentID(Integer customerAdjustmentID) {
        this.customerAdjustmentID = customerAdjustmentID;
    }

    public Integer getCustomerInvoiceID() {
        return customerInvoiceID;
    }

    public void setCustomerInvoiceID(Integer customerInvoiceID) {
        this.customerInvoiceID = customerInvoiceID;
    }

    public Double getAdjustAmount() {
        return adjustAmount;
    }

    public void setAdjustAmount(Double adjustAmount) {
        this.adjustAmount = adjustAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerAdjustmentDetailModel)) return false;
        if (!super.equals(o)) return false;
        CustomerAdjustmentDetailModel that = (CustomerAdjustmentDetailModel) o;
        return Objects.equals(getCustomerAdjustmentDetailID(), that.getCustomerAdjustmentDetailID()) &&
                Objects.equals(getCustomerAdjustmentID(), that.getCustomerAdjustmentID()) &&
                Objects.equals(getCustomerInvoiceID(), that.getCustomerInvoiceID()) &&
                Objects.equals(getAdjustAmount(), that.getAdjustAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCustomerAdjustmentDetailID(), getCustomerAdjustmentID(), getCustomerInvoiceID(), getAdjustAmount());
    }

    @Override
    public String toString() {
        return "CustomerAdjustmentDetailModel{" +
                "customerAdjustmentDetailID=" + customerAdjustmentDetailID +
                ", customerAdjustmentID=" + customerAdjustmentID +
                ", customerInvoiceID=" + customerInvoiceID +
                ", adjustAmount=" + adjustAmount +
                '}';
    }
}
