/**
 * Created By: Md. Abdul Hannan
 * Created Date: 4/16/2018
 * Time: 10:39 AM
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
public class CompanyDetail extends BaseEntity {
    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "companyDetailID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })
    Integer companyDetailID;
    Integer businessID;
    String companyName;
    String telephone;
    String fax;
    String mobile;
    String contactName;
    String primaryEmail;
    String ccEmail;
    String usePrimaryMailForCommunication;
    String useMailWebsiteEmail;
    String alwaysCCWithCCMail;

    public Integer getCompanyDetailID() {
        return companyDetailID;
    }

    public void setCompanyDetailID(Integer companyDetailID) {
        this.companyDetailID = companyDetailID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public String getCcEmail() {
        return ccEmail;
    }

    public void setCcEmail(String ccEmail) {
        this.ccEmail = ccEmail;
    }

    public String getUsePrimaryMailForCommunication() {
        return usePrimaryMailForCommunication;
    }

    public void setUsePrimaryMailForCommunication(String usePrimaryMailForCommunication) {
        this.usePrimaryMailForCommunication = usePrimaryMailForCommunication;
    }

    public String getUseMailWebsiteEmail() {
        return useMailWebsiteEmail;
    }

    public void setUseMailWebsiteEmail(String useMailWebsiteEmail) {
        this.useMailWebsiteEmail = useMailWebsiteEmail;
    }

    public String getAlwaysCCWithCCMail() {
        return alwaysCCWithCCMail;
    }

    public void setAlwaysCCWithCCMail(String alwaysCCWithCCMail) {
        this.alwaysCCWithCCMail = alwaysCCWithCCMail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompanyDetail)) return false;
        if (!super.equals(o)) return false;
        CompanyDetail that = (CompanyDetail) o;
        return Objects.equals(getCompanyDetailID(), that.getCompanyDetailID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getCompanyName(), that.getCompanyName()) &&
                Objects.equals(getTelephone(), that.getTelephone()) &&
                Objects.equals(getFax(), that.getFax()) &&
                Objects.equals(getMobile(), that.getMobile()) &&
                Objects.equals(getContactName(), that.getContactName()) &&
                Objects.equals(getPrimaryEmail(), that.getPrimaryEmail()) &&
                Objects.equals(getCcEmail(), that.getCcEmail()) &&
                Objects.equals(getUsePrimaryMailForCommunication(), that.getUsePrimaryMailForCommunication()) &&
                Objects.equals(getUseMailWebsiteEmail(), that.getUseMailWebsiteEmail()) &&
                Objects.equals(getAlwaysCCWithCCMail(), that.getAlwaysCCWithCCMail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCompanyDetailID(), getBusinessID(), getCompanyName(), getTelephone(), getFax(), getMobile(), getContactName(), getPrimaryEmail(), getCcEmail(), getUsePrimaryMailForCommunication(), getUseMailWebsiteEmail(), getAlwaysCCWithCCMail());
    }

    @Override
    public String toString() {
        return "CompanyDetail{" +
                "companyDetailID=" + companyDetailID +
                ", businessID=" + businessID +
                ", companyName='" + companyName + '\'' +
                ", telephone='" + telephone + '\'' +
                ", fax='" + fax + '\'' +
                ", mobile='" + mobile + '\'' +
                ", contactName='" + contactName + '\'' +
                ", primaryEmail='" + primaryEmail + '\'' +
                ", ccEmail='" + ccEmail + '\'' +
                ", usePrimaryMailForCommunication='" + usePrimaryMailForCommunication + '\'' +
                ", useMailWebsiteEmail='" + useMailWebsiteEmail + '\'' +
                ", alwaysCCWithCCMail='" + alwaysCCWithCCMail + '\'' +
                '}';
    }
}
