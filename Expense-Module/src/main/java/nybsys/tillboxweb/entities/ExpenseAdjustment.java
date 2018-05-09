/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 27/03/2018
 * Time: 05:41
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
import java.util.Objects;

@Entity
public class ExpenseAdjustment extends BaseEntityWithCurrency {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "expenseAdjustmentID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

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
        if (!(o instanceof ExpenseAdjustment)) return false;
        if (!super.equals(o)) return false;
        ExpenseAdjustment that = (ExpenseAdjustment) o;
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
        return "ExpenseAdjustment{" +
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