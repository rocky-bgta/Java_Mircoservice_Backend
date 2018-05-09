/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/23/2018
 * Time: 2:48 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.entities.PurchasesOrderDetail;
import nybsys.tillboxweb.models.PurchaseOrderDetailModel;
import nybsys.tillboxweb.models.VMPurchaseOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PurchaseOrderDetailBllManager extends BaseBll<PurchasesOrderDetail> {

    private static final Logger log = LoggerFactory.getLogger(PurchaseOrderDetailBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(PurchasesOrderDetail.class);
        Core.runTimeModelType.set(PurchaseOrderDetailModel.class);
    }


    public VMPurchaseOrder savePurchaseOrderDetail(VMPurchaseOrder vmPurchaseOrder) throws Exception {

        try {
            for (PurchaseOrderDetailModel purchaseOrderDetailModel : vmPurchaseOrder.lstpurchaseOrderDetailModel) {
                purchaseOrderDetailModel.setPurchaseOrderID(vmPurchaseOrder.purchaseOrderModel.getPurchaseOrderID());
                purchaseOrderDetailModel.setStatus(TillBoxAppEnum.Status.Active.get());
                purchaseOrderDetailModel.setCreatedBy("");
                purchaseOrderDetailModel.setCreatedDate(new Date());
                purchaseOrderDetailModel = this.save(purchaseOrderDetailModel);
            }

        } catch (Exception ex) {
            log.error("PurchaseOrderDetailBllManager -> save Purchase OrderDetail manager got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return vmPurchaseOrder;
    }


}
