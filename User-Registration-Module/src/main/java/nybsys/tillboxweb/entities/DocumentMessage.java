/**
 * Created By: Md. Abdul Hannan
 * Created Date: 4/20/2018
 * Time: 12:30 PM
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
public class DocumentMessage extends BaseEntity{

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "documentMessageID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DocumentMessage)) return false;
        if (!super.equals(o)) return false;
        DocumentMessage that = (DocumentMessage) o;
        return Objects.equals(getDocumentMessageID(), that.getDocumentMessageID()) &&
                Objects.equals(getDocumentType(), that.getDocumentType()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getMessage(), that.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDocumentMessageID(), getDocumentType(), getBusinessID(), getMessage());
    }

    @Override
    public String toString() {
        return "DocumentMessage{" +
                "documentMessageID=" + documentMessageID +
                ", documentType=" + documentType +
                ", businessID=" + businessID +
                ", message='" + message + '\'' +
                '}';
    }
}
