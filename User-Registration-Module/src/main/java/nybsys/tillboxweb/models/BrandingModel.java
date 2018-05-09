/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 20/04/2018
 * Time: 10:02
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class BrandingModel extends BaseModel {

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
        if (!(o instanceof BrandingModel)) return false;
        if (!super.equals(o)) return false;
        BrandingModel that = (BrandingModel) o;
        return Objects.equals(getBrandingID(), that.getBrandingID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getLogo(), that.getLogo()) &&
                Objects.equals(getPositionOfLogoOnInvoiceAndStatement(), that.getPositionOfLogoOnInvoiceAndStatement()) &&
                Objects.equals(isLogoOnInvoiceAndStatementEmail, that.isLogoOnInvoiceAndStatementEmail) &&
                Objects.equals(isLogoOnCustomerZone, that.isLogoOnCustomerZone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getBrandingID(), getBusinessID(), getLogo(), getPositionOfLogoOnInvoiceAndStatement(), isLogoOnInvoiceAndStatementEmail, isLogoOnCustomerZone);
    }

    @Override
    public String toString() {
        return "BrandingModel{" +
                "brandingID=" + brandingID +
                ", businessID=" + businessID +
                ", logo=" + logo +
                ", positionOfLogoOnInvoiceAndStatement=" + positionOfLogoOnInvoiceAndStatement +
                ", isLogoOnInvoiceAndStatementEmail=" + isLogoOnInvoiceAndStatementEmail +
                ", isLogoOnCustomerZone=" + isLogoOnCustomerZone +
                '}';
    }
}
