/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 06/03/2018
 * Time: 02:33
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class OpeningBalanceUpdateHistoryModel extends BaseModel {
    private Integer openingBalanceUpdateHistoryID;
    private Integer businessID;
    private Integer referenceType;
    private Integer referenceID;
    private String reason;
    private Double amount;

    public Integer getOpeningBalanceUpdateHistoryID() {
        return openingBalanceUpdateHistoryID;
    }

    public void setOpeningBalanceUpdateHistoryID(Integer openingBalanceUpdateHistoryID) {
        this.openingBalanceUpdateHistoryID = openingBalanceUpdateHistoryID;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OpeningBalanceUpdateHistoryModel)) return false;
        if (!super.equals(o)) return false;
        OpeningBalanceUpdateHistoryModel that = (OpeningBalanceUpdateHistoryModel) o;
        return Objects.equals(getOpeningBalanceUpdateHistoryID(), that.getOpeningBalanceUpdateHistoryID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getReferenceType(), that.getReferenceType()) &&
                Objects.equals(getReferenceID(), that.getReferenceID()) &&
                Objects.equals(getReason(), that.getReason()) &&
                Objects.equals(getAmount(), that.getAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getOpeningBalanceUpdateHistoryID(), getBusinessID(), getReferenceType(), getReferenceID(), getReason(), getAmount());
    }

    @Override
    public String toString() {
        return "OpeningBalanceUpdateHistoryModel{" +
                "openingBalanceUpdateHistoryID=" + openingBalanceUpdateHistoryID +
                ", businessID=" + businessID +
                ", referenceType=" + referenceType +
                ", referenceID=" + referenceID +
                ", reason='" + reason + '\'' +
                ", amount=" + amount +
                '}';
    }
}
