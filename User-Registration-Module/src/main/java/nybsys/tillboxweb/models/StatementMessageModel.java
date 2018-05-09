/**
 * Created By: Md. Abdul Hannan
 * Created Date: 4/16/2018
 * Time: 1:10 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class StatementMessageModel extends BaseModel {

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
        if (!(o instanceof StatementMessageModel)) return false;
        if (!super.equals(o)) return false;
        StatementMessageModel that = (StatementMessageModel) o;
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
        return "StatementMessageModel{" +
                "statementMessageID=" + statementMessageID +
                ", businessID=" + businessID +
                ", messageType=" + messageType +
                ", message=" + message +
                '}';
    }
}
