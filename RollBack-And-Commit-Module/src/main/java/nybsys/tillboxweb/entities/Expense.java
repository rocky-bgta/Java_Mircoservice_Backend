/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 27/03/2018
 * Time: 05:43
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Expense extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "expenseID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

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
        if (!(o instanceof Expense)) return false;
        if (!super.equals(o)) return false;
        Expense expense = (Expense) o;
        return Objects.equals(getExpenseID(), expense.getExpenseID()) &&
                Objects.equals(getBusinessID(), expense.getBusinessID()) &&
                Objects.equals(getDocNumber(), expense.getDocNumber()) &&
                Objects.equals(getFiscalYearID(), expense.getFiscalYearID()) &&
                Objects.equals(getExpenseCode(), expense.getExpenseCode()) &&
                Objects.equals(getCategoryID(), expense.getCategoryID()) &&
                Objects.equals(getNote(), expense.getNote()) &&
                Objects.equals(getTotalAmount(), expense.getTotalAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getExpenseID(), getBusinessID(), getDocNumber(), getFiscalYearID(), getExpenseCode(), getCategoryID(), getNote(), getTotalAmount());
    }

    @Override
    public String toString() {
        return "Expense{" +
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