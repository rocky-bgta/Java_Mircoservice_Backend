/**
 * Created By: Md. Abdul Hannan
 * Created Date: 4/16/2018
 * Time: 1:31 PM
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
public class DocumentNumber extends BaseEntity {
    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "documentNumberID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    Integer documentNumberID;
    Integer businessID;
    Integer documentType;
    String currentDocumentNumber;
    String newDocumentNumber;

    public Integer getDocumentNumberID() {
        return documentNumberID;
    }

    public void setDocumentNumberID(Integer documentNumberID) {
        this.documentNumberID = documentNumberID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getDocumentType() {
        return documentType;
    }

    public void setDocumentType(Integer documentType) {
        this.documentType = documentType;
    }

    public String getCurrentDocumentNumber() {
        return currentDocumentNumber;
    }

    public void setCurrentDocumentNumber(String currentDocumentNumber) {
        this.currentDocumentNumber = currentDocumentNumber;
    }

    public String getNewDocumentNumber() {
        return newDocumentNumber;
    }

    public void setNewDocumentNumber(String newDocumentNumber) {
        this.newDocumentNumber = newDocumentNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DocumentNumber)) return false;
        if (!super.equals(o)) return false;
        DocumentNumber that = (DocumentNumber) o;
        return Objects.equals(getDocumentNumberID(), that.getDocumentNumberID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getDocumentType(), that.getDocumentType()) &&
                Objects.equals(getCurrentDocumentNumber(), that.getCurrentDocumentNumber()) &&
                Objects.equals(getNewDocumentNumber(), that.getNewDocumentNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDocumentNumberID(), getBusinessID(), getDocumentType(), getCurrentDocumentNumber(), getNewDocumentNumber());
    }

    @Override
    public String toString() {
        return "DocumentNumber{" +
                "documentNumberID=" + documentNumberID +
                ", businessID=" + businessID +
                ", documentType=" + documentType +
                ", currentDocumentNumber='" + currentDocumentNumber + '\'' +
                ", newDocumentNumber='" + newDocumentNumber + '\'' +
                '}';
    }
}
