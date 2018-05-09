/**
 * Created By: Md. Abdul Hannan
 * Created Date: 4/20/2018
 * Time: 12:26 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

public class DocumentMessageModel extends BaseModel {

    private Integer documentMessageID;
    private Integer documentType;
    private Integer businessID;
    private String message;

    public Integer getDocumentMessageID() {
        return documentMessageID;
    }

    public void setDocumentMessageID(Integer documentMessageID) {
        this.documentMessageID = documentMessageID;
    }

    public Integer getDocumentType() {
        return documentType;
    }

    public void setDocumentType(Integer documentType) {
        this.documentType = documentType;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
