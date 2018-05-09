/**
 * Created By: Md. Abdul Hannan
 * Created Date: 4/16/2018
 * Time: 1:27 PM
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
public class StatementMessage extends BaseEntity {
    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "statementMessageID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    Integer statementMessageID;
    Integer businessID;
    Integer messageType;
    String message;

    public Integer getStatementMessageID() {
        return statementMessageID;
    }

    public void setStatementMessageID(Integer statementMessageID) {
        this.statementMessageID = statementMessageID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
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
        if (!(o instanceof StatementMessage)) return false;
        if (!super.equals(o)) return false;
        StatementMessage that = (StatementMessage) o;
        return Objects.equals(getStatementMessageID(), that.getStatementMessageID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getMessageType(), that.getMessageType()) &&
                Objects.equals(getMessage(), that.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getStatementMessageID(), getBusinessID(), getMessageType(), getMessage());
    }

    @Override
    public String toString() {
        return "StatementMessage{" +
                "statementMessageID=" + statementMessageID +
                ", businessID=" + businessID +
                ", messageType=" + messageType +
                ", message='" + message + '\'' +
                '}';
    }
}
