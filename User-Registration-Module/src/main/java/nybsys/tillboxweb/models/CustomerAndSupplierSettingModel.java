/**
 * Created By: Md. Abdul Hannan
 * Created Date: 4/16/2018
 * Time: 1:36 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.Objects;

public class CustomerAndSupplierSettingModel extends BaseModel {

    Integer customerAndSupplierSettingID;
    Integer businessID;
    Boolean warnWhenDuplicateCustomerReferenceUsedOnCustomerInvoices;
    Boolean warnWhenDuplicateSupplierInvoiceNumberUsedOnSupplierInvoices;
    Boolean displayInactiveCustomersForDetectionWhenProcessing;
    Boolean displayInactiveSuppliersForSelectionWhenProcessing;
    Boolean displayInactiveCustomersForSelectionOnReports;
    Boolean displayInactiveSuppliersForSelectionOnReports;
    Boolean useInclusiveProcessingOnCustomerSupplierDocumentsByDefault;
    Boolean useAccountAsDefaultDocumentLineTypeSelection;

    public Integer getCustomerAndSupplierSettingID() {
        return customerAndSupplierSettingID;
    }

    public void setCustomerAndSupplierSettingID(Integer customerAndSupplierSettingID) {
        this.customerAndSupplierSettingID = customerAndSupplierSettingID;
    }

    public Integer getBusinessID() {
        return businessID;
    }

    public void setBusinessID(Integer businessID) {
        this.businessID = businessID;
    }

    public Boolean getWarnWhenDuplicateCustomerReferenceUsedOnCustomerInvoices() {
        return warnWhenDuplicateCustomerReferenceUsedOnCustomerInvoices;
    }

    public void setWarnWhenDuplicateCustomerReferenceUsedOnCustomerInvoices(Boolean warnWhenDuplicateCustomerReferenceUsedOnCustomerInvoices) {
        this.warnWhenDuplicateCustomerReferenceUsedOnCustomerInvoices = warnWhenDuplicateCustomerReferenceUsedOnCustomerInvoices;
    }

    public Boolean getWarnWhenDuplicateSupplierInvoiceNumberUsedOnSupplierInvoices() {
        return warnWhenDuplicateSupplierInvoiceNumberUsedOnSupplierInvoices;
    }

    public void setWarnWhenDuplicateSupplierInvoiceNumberUsedOnSupplierInvoices(Boolean warnWhenDuplicateSupplierInvoiceNumberUsedOnSupplierInvoices) {
        this.warnWhenDuplicateSupplierInvoiceNumberUsedOnSupplierInvoices = warnWhenDuplicateSupplierInvoiceNumberUsedOnSupplierInvoices;
    }

    public Boolean getDisplayInactiveCustomersForDetectionWhenProcessing() {
        return displayInactiveCustomersForDetectionWhenProcessing;
    }

    public void setDisplayInactiveCustomersForDetectionWhenProcessing(Boolean displayInactiveCustomersForDetectionWhenProcessing) {
        this.displayInactiveCustomersForDetectionWhenProcessing = displayInactiveCustomersForDetectionWhenProcessing;
    }

    public Boolean getDisplayInactiveSuppliersForSelectionWhenProcessing() {
        return displayInactiveSuppliersForSelectionWhenProcessing;
    }

    public void setDisplayInactiveSuppliersForSelectionWhenProcessing(Boolean displayInactiveSuppliersForSelectionWhenProcessing) {
        this.displayInactiveSuppliersForSelectionWhenProcessing = displayInactiveSuppliersForSelectionWhenProcessing;
    }

    public Boolean getDisplayInactiveCustomersForSelectionOnReports() {
        return displayInactiveCustomersForSelectionOnReports;
    }

    public void setDisplayInactiveCustomersForSelectionOnReports(Boolean displayInactiveCustomersForSelectionOnReports) {
        this.displayInactiveCustomersForSelectionOnReports = displayInactiveCustomersForSelectionOnReports;
    }

    public Boolean getDisplayInactiveSuppliersForSelectionOnReports() {
        return displayInactiveSuppliersForSelectionOnReports;
    }

    public void setDisplayInactiveSuppliersForSelectionOnReports(Boolean displayInactiveSuppliersForSelectionOnReports) {
        this.displayInactiveSuppliersForSelectionOnReports = displayInactiveSuppliersForSelectionOnReports;
    }

    public Boolean getUseInclusiveProcessingOnCustomerSupplierDocumentsByDefault() {
        return useInclusiveProcessingOnCustomerSupplierDocumentsByDefault;
    }

    public void setUseInclusiveProcessingOnCustomerSupplierDocumentsByDefault(Boolean useInclusiveProcessingOnCustomerSupplierDocumentsByDefault) {
        this.useInclusiveProcessingOnCustomerSupplierDocumentsByDefault = useInclusiveProcessingOnCustomerSupplierDocumentsByDefault;
    }

    public Boolean getUseAccountAsDefaultDocumentLineTypeSelection() {
        return useAccountAsDefaultDocumentLineTypeSelection;
    }

    public void setUseAccountAsDefaultDocumentLineTypeSelection(Boolean useAccountAsDefaultDocumentLineTypeSelection) {
        this.useAccountAsDefaultDocumentLineTypeSelection = useAccountAsDefaultDocumentLineTypeSelection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerAndSupplierSettingModel)) return false;
        if (!super.equals(o)) return false;
        CustomerAndSupplierSettingModel that = (CustomerAndSupplierSettingModel) o;
        return Objects.equals(getCustomerAndSupplierSettingID(), that.getCustomerAndSupplierSettingID()) &&
                Objects.equals(getBusinessID(), that.getBusinessID()) &&
                Objects.equals(getWarnWhenDuplicateCustomerReferenceUsedOnCustomerInvoices(), that.getWarnWhenDuplicateCustomerReferenceUsedOnCustomerInvoices()) &&
                Objects.equals(getWarnWhenDuplicateSupplierInvoiceNumberUsedOnSupplierInvoices(), that.getWarnWhenDuplicateSupplierInvoiceNumberUsedOnSupplierInvoices()) &&
                Objects.equals(getDisplayInactiveCustomersForDetectionWhenProcessing(), that.getDisplayInactiveCustomersForDetectionWhenProcessing()) &&
                Objects.equals(getDisplayInactiveSuppliersForSelectionWhenProcessing(), that.getDisplayInactiveSuppliersForSelectionWhenProcessing()) &&
                Objects.equals(getDisplayInactiveCustomersForSelectionOnReports(), that.getDisplayInactiveCustomersForSelectionOnReports()) &&
                Objects.equals(getDisplayInactiveSuppliersForSelectionOnReports(), that.getDisplayInactiveSuppliersForSelectionOnReports()) &&
                Objects.equals(getUseInclusiveProcessingOnCustomerSupplierDocumentsByDefault(), that.getUseInclusiveProcessingOnCustomerSupplierDocumentsByDefault()) &&
                Objects.equals(getUseAccountAsDefaultDocumentLineTypeSelection(), that.getUseAccountAsDefaultDocumentLineTypeSelection());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCustomerAndSupplierSettingID(), getBusinessID(), getWarnWhenDuplicateCustomerReferenceUsedOnCustomerInvoices(), getWarnWhenDuplicateSupplierInvoiceNumberUsedOnSupplierInvoices(), getDisplayInactiveCustomersForDetectionWhenProcessing(), getDisplayInactiveSuppliersForSelectionWhenProcessing(), getDisplayInactiveCustomersForSelectionOnReports(), getDisplayInactiveSuppliersForSelectionOnReports(), getUseInclusiveProcessingOnCustomerSupplierDocumentsByDefault(), getUseAccountAsDefaultDocumentLineTypeSelection());
    }

    @Override
    public String toString() {
        return "CustomerAndSupplierSettingModel{" +
                "customerAndSupplierSettingID=" + customerAndSupplierSettingID +
                ", businessID=" + businessID +
                ", warnWhenDuplicateCustomerReferenceUsedOnCustomerInvoices=" + warnWhenDuplicateCustomerReferenceUsedOnCustomerInvoices +
                ", warnWhenDuplicateSupplierInvoiceNumberUsedOnSupplierInvoices=" + warnWhenDuplicateSupplierInvoiceNumberUsedOnSupplierInvoices +
                ", displayInactiveCustomersForDetectionWhenProcessing=" + displayInactiveCustomersForDetectionWhenProcessing +
                ", displayInactiveSuppliersForSelectionWhenProcessing=" + displayInactiveSuppliersForSelectionWhenProcessing +
                ", displayInactiveCustomersForSelectionOnReports=" + displayInactiveCustomersForSelectionOnReports +
                ", displayInactiveSuppliersForSelectionOnReports=" + displayInactiveSuppliersForSelectionOnReports +
                ", useInclusiveProcessingOnCustomerSupplierDocumentsByDefault=" + useInclusiveProcessingOnCustomerSupplierDocumentsByDefault +
                ", useAccountAsDefaultDocumentLineTypeSelection=" + useAccountAsDefaultDocumentLineTypeSelection +
                '}';
    }
}
