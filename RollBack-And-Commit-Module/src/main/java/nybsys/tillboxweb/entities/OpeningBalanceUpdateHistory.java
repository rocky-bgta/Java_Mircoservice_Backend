/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 06/03/2018
 * Time: 02:44
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class OpeningBalanceUpdateHistory extends BaseEntity {
    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "openingBalanceUpdateHistoryID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    @Column
    @NotNull
    private Integer openingBalanceUpdateHistoryID;
    @Column
    private Integer businessID;
    @Column
    private Integer referenceType;
    @Column
    private Integer referenceID;
    @Column
    private String reason;
    @Column
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
        if (!(o instanceof OpeningBalanceUpdateHistory)) return false;
        if (!super.equals(o)) return false;
        OpeningBalanceUpdateHistory that = (OpeningBalanceUpdateHistory) o;
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
        return "OpeningBalanceUpdateHistory{" +
                "openingBalanceUpdateHistoryID=" + openingBalanceUpdateHistoryID +
                ", businessID=" + businessID +
                ", referenceType=" + referenceType +
                ", referenceID=" + referenceID +
                ", reason='" + reason + '\'' +
                ", amount=" + amount +
                '}';
    }
}
