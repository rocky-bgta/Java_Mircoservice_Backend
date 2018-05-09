package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
public class MoneyTransfer extends BaseEntity {
    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "moneyTransferID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    @Column
    @NotNull
    private Integer moneyTransferID;
    @Column
    private Integer businessID;
    @Column
    private String trackingNo;
    @Column
    private String docNumber;
    @Column
    private Date date;
    @Column
    private Double amount;
    @Column
    private Integer accountIDTo;
    @Column
    private Integer accountIDFrom;
    @Column
    private String note;

    public Integer getMoneyTransferID() {
        return moneyTransferID;
    }

    public void setMoneyTransferID(Integer moneyTransferID) {
        this.moneyTransferID = moneyTransferID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getAccountIDTo() {
        return accountIDTo;
    }

    public void setAccountIDTo(Integer accountIDTo) {
        this.accountIDTo = accountIDTo;
    }

    public Integer getAccountIDFrom() {
        return accountIDFrom;
    }

    public void setAccountIDFrom(Integer accountIDFrom) {
        this.accountIDFrom = accountIDFrom;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MoneyTransfer)) return false;
        if (!super.equals(o)) return false;
        MoneyTransfer that = (MoneyTransfer) o;
        return Objects.equals(getMoneyTransferID(), that.getMoneyTransferID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getTrackingNo(), that.getTrackingNo()) &&
                Objects.equals(getDocNumber(), that.getDocNumber()) &&
                Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getAmount(), that.getAmount()) &&
                Objects.equals(getAccountIDTo(), that.getAccountIDTo()) &&
                Objects.equals(getAccountIDFrom(), that.getAccountIDFrom()) &&
                Objects.equals(getNote(), that.getNote());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getMoneyTransferID(), getBusinessID(), getTrackingNo(), getDocNumber(), getDate(), getAmount(), getAccountIDTo(), getAccountIDFrom(), getNote());
    }

    @Override
    public String toString() {
        return "MoneyTransfer{" +
                "moneyTransferID=" + moneyTransferID +
                ", businessID=" + businessID +
                ", trackingNo='" + trackingNo + '\'' +
                ", docNumber='" + docNumber + '\'' +
                ", date=" + date +
                ", amount=" + amount +
                ", accountIDTo=" + accountIDTo +
                ", accountIDFrom=" + accountIDFrom +
                ", note='" + note + '\'' +
                '}';
    }
}
