/**
 * Created By: Md. Abdul Hannan
 * Created Date: 4/16/2018
 * Time: 1:29 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class DocumentNumberModel extends BaseModel {

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
        if (!(o instanceof DocumentNumberModel)) return false;
        if (!super.equals(o)) return false;
        DocumentNumberModel that = (DocumentNumberModel) o;
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
        return "DocumentNumberModel{" +
                "documentNumberID=" + documentNumberID +
                ", businessID=" + businessID +
                ", documentType=" + documentType +
                ", currentDocumentNumber='" + currentDocumentNumber + '\'' +
                ", newDocumentNumber='" + newDocumentNumber + '\'' +
                '}';
    }
}
