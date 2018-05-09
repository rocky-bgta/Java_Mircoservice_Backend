/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/16/2018
 * Time: 12:46 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.entities;
import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Entity
public class RememberNote extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "rememberNoteID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    @NotNull
    @Column
    private Integer rememberNoteID;
    private Integer businessID;
    private Integer referenceType;
    private Integer referenceID;
    private Date entryDate;
    private Date actionDate;
    private String subject;
    private String note;
    private Boolean isComplete;
    private Byte[] attachment;

    public Integer getRememberNoteID() {
        return rememberNoteID;
    }

    public void setRememberNoteID(Integer rememberNoteID) {
        this.rememberNoteID = rememberNoteID;
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

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getComplete() {
        return isComplete;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }

    public Byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(Byte[] attachment) {
        this.attachment = attachment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RememberNote)) return false;
        if (!super.equals(o)) return false;
        RememberNote that = (RememberNote) o;
        return Objects.equals(getRememberNoteID(), that.getRememberNoteID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getReferenceType(), that.getReferenceType()) &&
                Objects.equals(getReferenceID(), that.getReferenceID()) &&
                Objects.equals(getEntryDate(), that.getEntryDate()) &&
                Objects.equals(getActionDate(), that.getActionDate()) &&
                Objects.equals(getSubject(), that.getSubject()) &&
                Objects.equals(getNote(), that.getNote()) &&
                Objects.equals(isComplete, that.isComplete) &&
                Arrays.equals(getAttachment(), that.getAttachment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getRememberNoteID(), getBusinessID(), getReferenceType(), getReferenceID(), getEntryDate(), getActionDate(), getSubject(), getNote(), isComplete, getAttachment());
    }


    @Override
    public String toString() {
        return "RememberNote{" +
                "rememberNoteID=" + rememberNoteID +
                ", businessID=" + businessID +
                ", referenceType=" + referenceType +
                ", referenceID=" + referenceID +
                ", entryDate=" + entryDate +
                ", actionDate=" + actionDate +
                ", subject='" + subject + '\'' +
                ", note='" + note + '\'' +
                ", isComplete=" + isComplete +
                ", attachment=" + Arrays.toString(attachment) +
                '}';
    }
}
