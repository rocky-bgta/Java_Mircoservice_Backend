/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 24-Apr-18
 * Time: 11:04 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.coreEntities;

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

    private Integer documentNumberID;
    private Integer businessID;
    private String currentDocumentNumber;
    private String documentType;
    private String newDocumentNumber;

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

    public String getCurrentDocumentNumber() {
        return currentDocumentNumber;
    }

    public void setCurrentDocumentNumber(String currentDocumentNumber) {
        this.currentDocumentNumber = currentDocumentNumber;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
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
                Objects.equals(getCurrentDocumentNumber(), that.getCurrentDocumentNumber()) &&
                Objects.equals(getDocumentType(), that.getDocumentType()) &&
                Objects.equals(getNewDocumentNumber(), that.getNewDocumentNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDocumentNumberID(), getBusinessID(), getCurrentDocumentNumber(), getDocumentType(), getNewDocumentNumber());
    }

    @Override
    public String toString() {
        return "DocumentNumber{" +
                "documentNumberID=" + documentNumberID +
                ", businessID=" + businessID +
                ", currentDocumentNumber='" + currentDocumentNumber + '\'' +
                ", documentType='" + documentType + '\'' +
                ", newDocumentNumber='" + newDocumentNumber + '\'' +
                '}';
    }
}