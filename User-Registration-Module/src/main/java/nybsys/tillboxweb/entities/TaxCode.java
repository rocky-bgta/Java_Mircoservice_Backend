package nybsys.tillboxweb.entities;

import nybsys.tillboxweb.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class TaxCode extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "taxCodeID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    private Integer taxCodeID;
    @Column
    private Integer taxType;
    @Column
    private String taxCode;
    @Column
    private Double taxRate;
    @Column
    private String taxDescription;
    @Column
    private Integer taxCollectedAccountID;
    @Column
    private Integer taxPaidAccountID;

    public Integer getTaxCodeID() {
        return taxCodeID;
    }

    public void setTaxCodeID(Integer taxCodeID) {
        this.taxCodeID = taxCodeID;
    }

    public Integer getTaxType() {
        return taxType;
    }

    public void setTaxType(Integer taxType) {
        this.taxType = taxType;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public String getTaxDescription() {
        return taxDescription;
    }

    public void setTaxDescription(String taxDescription) {
        this.taxDescription = taxDescription;
    }

    public Integer getTaxCollectedAccountID() {
        return taxCollectedAccountID;
    }

    public void setTaxCollectedAccountID(Integer taxCollectedAccountID) {
        this.taxCollectedAccountID = taxCollectedAccountID;
    }

    public Integer getTaxPaidAccountID() {
        return taxPaidAccountID;
    }

    public void setTaxPaidAccountID(Integer taxPaidAccountID) {
        this.taxPaidAccountID = taxPaidAccountID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaxCode)) return false;
        if (!super.equals(o)) return false;
        TaxCode taxCode1 = (TaxCode) o;
        return Double.compare(taxCode1.getTaxRate(), getTaxRate()) == 0 &&
                Objects.equals(getTaxCodeID(), taxCode1.getTaxCodeID()) &&
                Objects.equals(getTaxType(), taxCode1.getTaxType()) &&
                Objects.equals(getTaxCode(), taxCode1.getTaxCode()) &&
                Objects.equals(getTaxDescription(), taxCode1.getTaxDescription()) &&
                Objects.equals(getTaxCollectedAccountID(), taxCode1.getTaxCollectedAccountID()) &&
                Objects.equals(getTaxPaidAccountID(), taxCode1.getTaxPaidAccountID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTaxCodeID(), getTaxType(), getTaxCode(), getTaxRate(), getTaxDescription(), getTaxCollectedAccountID(), getTaxPaidAccountID());
    }

    @Override
    public String toString() {
        return "TaxCode{" +
                "taxCodeID=" + taxCodeID +
                ", taxType=" + taxType +
                ", taxCode='" + taxCode + '\'' +
                ", taxRate=" + taxRate +
                ", taxDescription='" + taxDescription + '\'' +
                ", taxCollectedAccountID=" + taxCollectedAccountID +
                ", taxPaidAccountID=" + taxPaidAccountID +
                '}';
    }
}
