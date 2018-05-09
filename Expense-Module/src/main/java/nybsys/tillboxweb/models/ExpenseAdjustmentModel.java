/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 27/03/2018
 * Time: 05:32
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModelWithCurrency;

import java.util.Date;
import java.util.Objects;

public class ExpenseAdjustmentModel extends BaseModelWithCurrency {
    private Integer expenseAdjustmentID;
    private Integer businessID;
    private Integer adjustmentType;
    private Integer expenseID;
    private String docNumber;
    private String adjustmentNumber;
    private String note;
    private Double totalAmount;

    public Integer getExpenseAdjustmentID() {
        return expenseAdjustmentID;
    }

    public void setExpenseAdjustmentID(Integer expenseAdjustmentID) {
        this.expenseAdjustmentID = expenseAdjustmentID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getAdjustmentType() {
        return adjustmentType;
    }

    public void setAdjustmentType(Integer adjustmentType) {
        this.adjustmentType = adjustmentType;
    }

    public Integer getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(Integer expenseID) {
        this.expenseID = expenseID;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getAdjustmentNumber() {
        return adjustmentNumber;
    }

    public void setAdjustmentNumber(String adjustmentNumber) {
        this.adjustmentNumber = adjustmentNumber;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExpenseAdjustmentModel)) return false;
        if (!super.equals(o)) return false;
        ExpenseAdjustmentModel that = (ExpenseAdjustmentModel) o;
        return Objects.equals(getExpenseAdjustmentID(), that.getExpenseAdjustmentID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getAdjustmentType(), that.getAdjustmentType()) &&
                Objects.equals(getExpenseID(), that.getExpenseID()) &&
                Objects.equals(getDocNumber(), that.getDocNumber()) &&
                Objects.equals(getAdjustmentNumber(), that.getAdjustmentNumber()) &&
                Objects.equals(getNote(), that.getNote()) &&
                Objects.equals(getTotalAmount(), that.getTotalAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getExpenseAdjustmentID(), getBusinessID(), getAdjustmentType(), getExpenseID(), getDocNumber(), getAdjustmentNumber(), getNote(), getTotalAmount());
    }

    @Override
    public String toString() {
        return "ExpenseAdjustmentModel{" +
                "expenseAdjustmentID=" + expenseAdjustmentID +
                ", businessID=" + businessID +
                ", adjustmentType=" + adjustmentType +
                ", expenseID=" + expenseID +
                ", docNumber='" + docNumber + '\'' +
                ", adjustmentNumber='" + adjustmentNumber + '\'' +
                ", note='" + note + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
