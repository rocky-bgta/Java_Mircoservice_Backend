/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 26-Feb-18
 * Time: 12:09 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class Attachment extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "attachmentID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

    private Integer attachmentID;
    private Integer businessID;
    private Integer referenceType;
    private Integer referenceID;
    private Byte[] attachment;
    private String attachmentName;

    public Integer getAttachmentID() {
        return attachmentID;
    }

    public void setAttachmentID(Integer attachmentID) {
        this.attachmentID = attachmentID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(Integer referenceType) {
        this.referenceType = referenceType;
    }

    public Integer getReferenceID() {
        return referenceID;
    }

    public void setReferenceID(Integer referenceID) {
        this.referenceID = referenceID;
    }

    public Byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(Byte[] attachment) {
        this.attachment = attachment;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attachment)) return false;
        if (!super.equals(o)) return false;
        Attachment that = (Attachment) o;
        return Objects.equals(getAttachmentID(), that.getAttachmentID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getReferenceType(), that.getReferenceType()) &&
                Objects.equals(getReferenceID(), that.getReferenceID()) &&
                Arrays.equals(getAttachment(), that.getAttachment()) &&
                Objects.equals(getAttachmentName(), that.getAttachmentName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAttachmentID(), getBusinessID(), getReferenceType(), getReferenceID(), getAttachment(), getAttachmentName());
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "attachmentID=" + attachmentID +
                ", businessID=" + businessID +
                ", referenceType=" + referenceType +
                ", referenceID=" + referenceID +
                ", attachment=" + Arrays.toString(attachment) +
                ", attachmentName='" + attachmentName + '\'' +
                '}';
    }
}