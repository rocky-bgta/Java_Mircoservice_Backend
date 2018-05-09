/**
 * Created By: Md. Abdul Hannan
 * Created Date: 4/16/2018
 * Time: 1:32 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class DocumentDescriptionModel extends BaseModel {
    Integer documentDescriptionID;
    Integer documentTypeID;
    Integer businessID;
    Integer documentType;
    String originalName;
    String copyName;

    public Integer getDocumentDescriptionID() {
        return documentDescriptionID;
    }

    public void setDocumentDescriptionID(Integer documentDescriptionID) {
        this.documentDescriptionID = documentDescriptionID;
    }

    public Integer getDocumentTypeID() {
        return documentTypeID;
    }

    public void setDocumentTypeID(Integer documentTypeID) {
        this.documentTypeID = documentTypeID;
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

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getCopyName() {
        return copyName;
    }

    public void setCopyName(String copyName) {
        this.copyName = copyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DocumentDescriptionModel)) return false;
        if (!super.equals(o)) return false;
        DocumentDescriptionModel that = (DocumentDescriptionModel) o;
        return Objects.equals(getDocumentDescriptionID(), that.getDocumentDescriptionID()) &&
                Objects.equals(getDocumentTypeID(), that.getDocumentTypeID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getDocumentType(), that.getDocumentType()) &&
                Objects.equals(getOriginalName(), that.getOriginalName()) &&
                Objects.equals(getCopyName(), that.getCopyName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDocumentDescriptionID(), getDocumentTypeID(), getBusinessID(), getDocumentType(), getOriginalName(), getCopyName());
    }

    @Override
    public String toString() {
        return "DocumentDescriptionModel{" +
                "documentDescriptionID=" + documentDescriptionID +
                ", documentTypeID=" + documentTypeID +
                ", businessID=" + businessID +
                ", documentType=" + documentType +
                ", originalName='" + originalName + '\'' +
                ", copyName='" + copyName + '\'' +
                '}';
    }
}
