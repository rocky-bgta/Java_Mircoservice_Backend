/**
 * Created By: Md. Abdul Hannan
 * Created Date: 4/16/2018
 * Time: 2:43 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class InvoiceAndStatementLayoutTypeModel extends BaseModel {

    Integer invoiceAndStatementLayoutTypeID;
    Integer businessID;
    Integer report;
    Integer layout;

    public Integer getInvoiceAndStatementLayoutTypeID() {
        return invoiceAndStatementLayoutTypeID;
    }

    public void setInvoiceAndStatementLayoutTypeID(Integer invoiceAndStatementLayoutTypeID) {
        this.invoiceAndStatementLayoutTypeID = invoiceAndStatementLayoutTypeID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getReport() {
        return report;
    }

    public void setReport(Integer report) {
        this.report = report;
    }

    public Integer getLayout() {
        return layout;
    }

    public void setLayout(Integer layout) {
        this.layout = layout;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvoiceAndStatementLayoutTypeModel)) return false;
        if (!super.equals(o)) return false;
        InvoiceAndStatementLayoutTypeModel that = (InvoiceAndStatementLayoutTypeModel) o;
        return Objects.equals(getInvoiceAndStatementLayoutTypeID(), that.getInvoiceAndStatementLayoutTypeID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getReport(), that.getReport()) &&
                Objects.equals(getLayout(), that.getLayout());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getInvoiceAndStatementLayoutTypeID(), getBusinessID(), getReport(), getLayout());
    }

    @Override
    public String toString() {
        return "InvoiceAndStatementLayoutTypeModel{" +
                "invoiceAndStatementLayoutTypeID=" + invoiceAndStatementLayoutTypeID +
                ", businessID=" + businessID +
                ", report=" + report +
                ", layout=" + layout +
                '}';
    }
}
