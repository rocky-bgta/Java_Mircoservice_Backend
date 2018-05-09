/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/23/2018
 * Time: 12:52 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;

import java.util.ArrayList;
import java.util.List;

public class VMSupplierReturn extends BaseModel {
    public SupplierReturnModel supplierReturnModel;
    public List<SupplierReturnDetailModel> lstSupplierReturnDetailModel;

    public VMSupplierReturn()
    {
        supplierReturnModel=new SupplierReturnModel();
        lstSupplierReturnDetailModel=new ArrayList<>();
    }

}
