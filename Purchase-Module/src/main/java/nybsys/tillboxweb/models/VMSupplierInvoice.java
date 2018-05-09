/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/23/2018
 * Time: 12:50 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.ArrayList;
import java.util.List;

public class VMSupplierInvoice extends BaseModel {

    public SupplierInvoiceModel supplierInvoiceModel;
    public List<SupplierInvoiceDetailModel> lstSupplierInvoiceDetailModel;

    public VMSupplierInvoice()
    {
        supplierInvoiceModel=new SupplierInvoiceModel();
        lstSupplierInvoiceDetailModel=new ArrayList<>();
    }

}
