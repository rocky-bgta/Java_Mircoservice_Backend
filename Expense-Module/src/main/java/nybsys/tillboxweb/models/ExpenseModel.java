/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 27/03/2018
 * Time: 04:17
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModelWithCurrency;

import java.util.Date;
import java.util.Objects;

public class ExpenseModel extends BaseModelWithCurrency {
    private Integer expenseID;
    private Integer businessID;
    private String docNumber;
    private Integer fiscalYearID;
    private String expenseCode;
    private Integer categoryID;
    private String note;
    private Double totalAmount;

    public Integer getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(Integer expenseID) {
        this.expenseID = expenseID;
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

    public Integer getFiscalYearID() {
        return fiscalYearID;
    }

    public void setFiscalYearID(Integer fiscalYearID) {
        this.fiscalYearID = fiscalYearID;
    }

    public String getExpenseCode() {
        return expenseCode;
    }

    public void setExpenseCode(String expenseCode) {
        this.expenseCode = expenseCode;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
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
        if (!(o instanceof ExpenseModel)) return false;
        if (!super.equals(o)) return false;
        ExpenseModel that = (ExpenseModel) o;
        return Objects.equals(getExpenseID(), that.getExpenseID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getDocNumber(), that.getDocNumber()) &&
                Objects.equals(getFiscalYearID(), that.getFiscalYearID()) &&
                Objects.equals(getExpenseCode(), that.getExpenseCode()) &&
                Objects.equals(getCategoryID(), that.getCategoryID()) &&
                Objects.equals(getNote(), that.getNote()) &&
                Objects.equals(getTotalAmount(), that.getTotalAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getExpenseID(), getBusinessID(), getDocNumber(), getFiscalYearID(), getExpenseCode(), getCategoryID(), getNote(), getTotalAmount());
    }

    @Override
    public String toString() {
        return "ExpenseModel{" +
                "expenseID=" + expenseID +
                ", businessID=" + businessID +
                ", docNumber='" + docNumber + '\'' +
                ", fiscalYearID=" + fiscalYearID +
                ", expenseCode='" + expenseCode + '\'' +
                ", categoryID=" + categoryID +
                ", note='" + note + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
