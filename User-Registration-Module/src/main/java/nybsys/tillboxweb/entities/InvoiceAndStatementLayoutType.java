/**
 * Created By: Md. Abdul Hannan
 * Created Date: 4/16/2018
 * Time: 2:53 PM
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
public class InvoiceAndStatementLayoutType extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "invoiceAndStatementLayoutTypeID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

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
        if (!(o instanceof InvoiceAndStatementLayoutType)) return false;
        if (!super.equals(o)) return false;
        InvoiceAndStatementLayoutType that = (InvoiceAndStatementLayoutType) o;
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
        return "InvoiceAndStatementLayoutType{" +
                "invoiceAndStatementLayoutTypeID=" + invoiceAndStatementLayoutTypeID +
                ", businessID=" + businessID +
                ", report=" + report +
                ", layout=" + layout +
                '}';
    }
}
