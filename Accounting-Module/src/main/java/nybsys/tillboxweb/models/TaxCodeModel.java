package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class TaxCodeModel extends BaseModel {

    private Integer taxCodeID;
    private Integer taxType;
    private String taxCode;
    private Double taxRate;
    private String taxDescription;
    private Integer taxCollectedAccountID;
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
        if (!(o instanceof TaxCodeModel)) return false;
        if (!super.equals(o)) return false;
        TaxCodeModel that = (TaxCodeModel) o;
        return Double.compare(that.getTaxRate(), getTaxRate()) == 0 &&
                Objects.equals(getTaxCodeID(), that.getTaxCodeID()) &&
                Objects.equals(getTaxType(), that.getTaxType()) &&
                Objects.equals(getTaxCode(), that.getTaxCode()) &&
                Objects.equals(getTaxDescription(), that.getTaxDescription()) &&
                Objects.equals(getTaxCollectedAccountID(), that.getTaxCollectedAccountID()) &&
                Objects.equals(getTaxPaidAccountID(), that.getTaxPaidAccountID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTaxCodeID(), getTaxType(), getTaxCode(), getTaxRate(), getTaxDescription(), getTaxCollectedAccountID(), getTaxPaidAccountID());
    }

    @Override
    public String toString() {
        return "TaxCodeModel{" +
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
