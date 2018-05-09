/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 27/03/2018
 * Time: 04:47
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class ExpenseTypeModel extends BaseModel {
    private Integer expenseTypeID;
    private Integer businessID;
    private String docNumber;
    private Integer parentID;
    private String name;
    private String description;

    public Integer getExpenseTypeID() {
        return expenseTypeID;
    }

    public void setExpenseTypeID(Integer expenseTypeID) {
        this.expenseTypeID = expenseTypeID;
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

    public Integer getParentID() {
        return parentID;
    }

    public void setParentID(Integer parentID) {
        this.parentID = parentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExpenseTypeModel)) return false;
        if (!super.equals(o)) return false;
        ExpenseTypeModel that = (ExpenseTypeModel) o;
        return Objects.equals(getExpenseTypeID(), that.getExpenseTypeID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getDocNumber(), that.getDocNumber()) &&
                Objects.equals(getParentID(), that.getParentID()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getExpenseTypeID(), getBusinessID(), getDocNumber(), getParentID(), getName(), getDescription());
    }

    @Override
    public String toString() {
        return "ExpenseTypeModel{" +
                "expenseTypeID=" + expenseTypeID +
                ", businessID=" + businessID +
                ", docNumber='" + docNumber + '\'' +
                ", parentID=" + parentID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
