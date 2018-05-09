/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 26-Feb-18
 * Time: 12:15 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Arrays;
import java.util.Objects;

public class AttachmentModel extends BaseModel {
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
        if (!(o instanceof AttachmentModel)) return false;
        if (!super.equals(o)) return false;
        AttachmentModel that = (AttachmentModel) o;
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
        return "AttachmentModel{" +
                "attachmentID=" + attachmentID +
                ", businessID=" + businessID +
                ", referenceType=" + referenceType +
                ", referenceID=" + referenceID +
                ", attachment=" + Arrays.toString(attachment) +
                ", attachmentName='" + attachmentName + '\'' +
                '}';
    }
}
