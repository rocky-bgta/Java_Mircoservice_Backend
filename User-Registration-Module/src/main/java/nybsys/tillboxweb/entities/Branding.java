/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 20/04/2018
 * Time: 10:09
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
import java.util.Date;
import java.util.Objects;

@Entity
public class Branding extends BaseEntity {

    @Id
    @GeneratedValue(generator = "IdGen")
    @GenericGenerator(name = "IdGen", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters =
                    {
                            @org.hibernate.annotations.Parameter(name = "sequence_name", value = "brandingID_seq"),
                            @org.hibernate.annotations.Parameter(name = "optimizer", value = "pooled"),
                            @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                            @org.hibernate.annotations.Parameter(name = "increment_size", value = "1"),
                    })

    private Integer brandingID;
    private Integer businessID;
    private Byte[] logo;
    private Integer positionOfLogoOnInvoiceAndStatement;
    private Boolean isLogoOnInvoiceAndStatementEmail;
    private Boolean isLogoOnCustomerZone;

    public Integer getBrandingID() {
        return brandingID;
    }

    public void setBrandingID(Integer brandingID) {
        this.brandingID = brandingID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Byte[] getLogo() {
        return logo;
    }

    public void setLogo(Byte[] logo) {
        this.logo = logo;
    }

    public Integer getPositionOfLogoOnInvoiceAndStatement() {
        return positionOfLogoOnInvoiceAndStatement;
    }

    public void setPositionOfLogoOnInvoiceAndStatement(Integer positionOfLogoOnInvoiceAndStatement) {
        this.positionOfLogoOnInvoiceAndStatement = positionOfLogoOnInvoiceAndStatement;
    }

    public Boolean getIsLogoOnInvoiceAndStatementEmail() {
        return isLogoOnInvoiceAndStatementEmail;
    }

    public void setIsLogoOnInvoiceAndStatementEmail(Boolean isLogoOnInvoiceAndStatementEmail) {
        this.isLogoOnInvoiceAndStatementEmail = isLogoOnInvoiceAndStatementEmail;
    }

    public Boolean getIsLogoOnCustomerZone() {
        return isLogoOnCustomerZone;
    }

    public void setIsLogoOnCustomerZone(Boolean isLogoOnCustomerZone) {
        this.isLogoOnCustomerZone = isLogoOnCustomerZone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Branding)) return false;
        if (!super.equals(o)) return false;
        Branding branding = (Branding) o;
        return Objects.equals(getBrandingID(), branding.getBrandingID()) &&
                Objects.equals(getBusinessID(), branding.getBusinessID()) &&
                Objects.equals(getLogo(), branding.getLogo()) &&
                Objects.equals(getPositionOfLogoOnInvoiceAndStatement(), branding.getPositionOfLogoOnInvoiceAndStatement()) &&
                Objects.equals(isLogoOnInvoiceAndStatementEmail, branding.isLogoOnInvoiceAndStatementEmail) &&
                Objects.equals(isLogoOnCustomerZone, branding.isLogoOnCustomerZone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getBrandingID(), getBusinessID(), getLogo(), getPositionOfLogoOnInvoiceAndStatement(), isLogoOnInvoiceAndStatementEmail, isLogoOnCustomerZone);
    }

    @Override
    public String toString() {
        return "Branding{" +
                "brandingID=" + brandingID +
                ", businessID=" + businessID +
                ", logo=" + logo +
                ", positionOfLogoOnInvoiceAndStatement=" + positionOfLogoOnInvoiceAndStatement +
                ", isLogoOnInvoiceAndStatementEmail=" + isLogoOnInvoiceAndStatementEmail +
                ", isLogoOnCustomerZone=" + isLogoOnCustomerZone +
                '}';
    }
}