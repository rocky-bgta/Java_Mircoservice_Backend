/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/23/2018
 * Time: 12:37 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.models;
import nybsys.tillboxweb.BaseModel;
import java.util.ArrayList;
import java.util.List;

public class VMPurchaseOrder extends BaseModel {

    public PurchaseOrderModel   purchaseOrderModel;
    public List<PurchaseOrderDetailModel> lstpurchaseOrderDetailModel;

    public VMPurchaseOrder()
    {
        purchaseOrderModel=new PurchaseOrderModel();
        lstpurchaseOrderDetailModel=new ArrayList<>();
    }
}
