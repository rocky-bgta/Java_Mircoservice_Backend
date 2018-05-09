/**
 * Created By: Md. Abdul Hannan
 * Created Date: 4/16/2018
 * Time: 11:23 AM
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
public class AdditionalCompanyInformation extends BaseEntity {
    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "additionalCompanyInformationID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    Integer additionalCompanyInformationID;
    Integer businessID;
    String city;
    String province;
    Integer country;
    String companyTaxNo;
    String registeredName;
    String taxOffice;
    Integer entityType;
    String registrationNumber;
    String customsCode;

    public String getCustomsCode() {
        return customsCode;
    }

    public void setCustomsCode(String customsCode) {
        this.customsCode = customsCode;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Integer getAdditionalCompanyInformationID() {
        return additionalCompanyInformationID;
    }

    public void setAdditionalCompanyInformationID(Integer additionalCompanyInformationID) {
        this.additionalCompanyInformationID = additionalCompanyInformationID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public String getCompanyTaxNo() {
        return companyTaxNo;
    }

    public void setCompanyTaxNo(String companyTaxNo) {
        this.companyTaxNo = companyTaxNo;
    }

    public String getRegisteredName() {
        return registeredName;
    }

    public void setRegisteredName(String registeredName) {
        this.registeredName = registeredName;
    }

    public String getTaxOffice() {
        return taxOffice;
    }

    public void setTaxOffice(String taxOffice) {
        this.taxOffice = taxOffice;
    }

    public Integer getEntityType() {
        return entityType;
    }

    public void setEntityType(Integer entityType) {
        this.entityType = entityType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdditionalCompanyInformation)) return false;
        if (!super.equals(o)) return false;
        AdditionalCompanyInformation that = (AdditionalCompanyInformation) o;
        return Objects.equals(getAdditionalCompanyInformationID(), that.getAdditionalCompanyInformationID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getCity(), that.getCity()) &&
                Objects.equals(getProvince(), that.getProvince()) &&
                Objects.equals(getCountry(), that.getCountry()) &&
                Objects.equals(getCompanyTaxNo(), that.getCompanyTaxNo()) &&
                Objects.equals(getRegisteredName(), that.getRegisteredName()) &&
                Objects.equals(getTaxOffice(), that.getTaxOffice()) &&
                Objects.equals(getEntityType(), that.getEntityType()) &&
                Objects.equals(getRegistrationNumber(), that.getRegistrationNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAdditionalCompanyInformationID(), getBusinessID(), getCity(), getProvince(), getCountry(), getCompanyTaxNo(), getRegisteredName(), getTaxOffice(), getEntityType(), getRegistrationNumber());
    }

    @Override
    public String toString() {
        return "AdditionalCompanyInformation{" +
                "additionalCompanyInformationID=" + additionalCompanyInformationID +
                ", businessID=" + businessID +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", country=" + country +
                ", companyTaxNo='" + companyTaxNo + '\'' +
                ", registeredName='" + registeredName + '\'' +
                ", taxOffice='" + taxOffice + '\'' +
                ", entityType=" + entityType +
                ", registrationNumber='" + registrationNumber + '\'' +
                '}';
    }
}
