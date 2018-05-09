/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/23/2018
 * Time: 2:39 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.PurchaseOrder;
import nybsys.tillboxweb.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PurchaseOrderBllManager extends BaseBll<PurchaseOrder> {

    private static final Logger log = LoggerFactory.getLogger(PurchaseOrderBllManager.class);

    @Autowired
    private PurchaseOrderDetailBllManager purchaseOrderDetailBllManager;
    @Autowired
    private SupplierInvoiceBllManager supplierInvoiceBllManager;

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(PurchaseOrder.class);
        Core.runTimeModelType.set(PurchaseOrderModel.class);
    }


    public VMPurchaseOrder savePurchaseOrder(VMPurchaseOrder vmPurchaseOrder) throws Exception {

        try {
            if (isValidPurchaseOrder(vmPurchaseOrder)) {

                if (vmPurchaseOrder.purchaseOrderModel.getPurchaseOrderID() > 0) {
                    vmPurchaseOrder.purchaseOrderModel.setUpdatedDate(new Date());
                    vmPurchaseOrder.purchaseOrderModel = this.update(vmPurchaseOrder.purchaseOrderModel);
              /* detail save*/

                    PurchaseOrderDetailModel searchOrderDetailModel = new PurchaseOrderDetailModel();
                    searchOrderDetailModel.setPurchaseOrderID(vmPurchaseOrder.purchaseOrderModel.getPurchaseOrderID());
                    searchOrderDetailModel.setStatus(TillBoxAppEnum.Status.Active.get());
                    List<PurchaseOrderDetailModel> lstExistingPurchaseOrderDetailModel = new ArrayList<>();
                    lstExistingPurchaseOrderDetailModel = this.purchaseOrderDetailBllManager.getAllByConditions(searchOrderDetailModel);
                    for (PurchaseOrderDetailModel purchaseOrderDetailModel : lstExistingPurchaseOrderDetailModel) {
                        purchaseOrderDetailModel.setUpdatedDate(new Date());
                        purchaseOrderDetailModel.setStatus(TillBoxAppEnum.Status.Deleted.get());
                        purchaseOrderDetailModel = this.purchaseOrderDetailBllManager.update(purchaseOrderDetailModel);
                    }
                    this.purchaseOrderDetailBllManager.savePurchaseOrderDetail(vmPurchaseOrder);

                } else {
                    vmPurchaseOrder.purchaseOrderModel.setStatus(TillBoxAppEnum.Status.Active.get());
                    vmPurchaseOrder.purchaseOrderModel.setCreatedBy("");
                    vmPurchaseOrder.purchaseOrderModel.setCreatedDate(new Date());
                    vmPurchaseOrder.purchaseOrderModel = this.save(vmPurchaseOrder.purchaseOrderModel);
               /* detail save*/
                    this.purchaseOrderDetailBllManager.savePurchaseOrderDetail(vmPurchaseOrder);
                }
                if (Core.clientMessage.get().messageCode == null) {
                    Core.clientMessage.get().message = MessageConstant.PURCHASE_ORDER_SAVE_SUCCESSFULLY;
                } else {

                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.PURCHASE_ORDER_SAVE_FAILED;
                }
            }


        } catch (Exception ex) {
            log.error("ProductAttributeMapperBllManger -> saveProductAttributeMapper got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return vmPurchaseOrder;
    }


    private Boolean isValidPurchaseOrder(VMPurchaseOrder vmPurchaseOrder) throws Exception {
        PurchaseOrderModel existingProductModel = new PurchaseOrderModel();
        existingProductModel.setOrderNumber(vmPurchaseOrder.purchaseOrderModel.getOrderNumber());

        List<PurchaseOrderModel> lstPurchaseOrderModel = new ArrayList<>();
        lstPurchaseOrderModel = this.getAllByConditions(existingProductModel);

        if (lstPurchaseOrderModel.size() > 0) {
            existingProductModel = lstPurchaseOrderModel.get(0);
        }


        if ((existingProductModel.getPurchaseOrderID() != null && existingProductModel.getPurchaseOrderID() > 0) && existingProductModel.getPurchaseOrderID().intValue() != vmPurchaseOrder.purchaseOrderModel.getPurchaseOrderID().intValue()) {

            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            Core.clientMessage.get().userMessage = MessageConstant.DUPLICATE_ORDER_NUMBER;
            return false;
        }


        return true;
    }

    public List<VMPurchaseOrder> searchVMPurchaseOrder(PurchaseOrderModel purchaseOrderModel) throws Exception {

        List<VMPurchaseOrder> lstVMPurchaseOrder = new ArrayList<>();
        try {
            List<PurchaseOrderModel> lstPurchaseOrderModel = new ArrayList<>();

            if (purchaseOrderModel.getStatus() == null || purchaseOrderModel.getStatus() == 0) {
                purchaseOrderModel.setStatus(TillBoxAppEnum.Status.Active.get());
            }

            lstPurchaseOrderModel = this.getAllByConditions(purchaseOrderModel);
            if (lstPurchaseOrderModel.size() > 0) {
                for (PurchaseOrderModel purchaseOrderModel1 : lstPurchaseOrderModel) {
                    VMPurchaseOrder vmPurchaseOrder = new VMPurchaseOrder();
                    vmPurchaseOrder.purchaseOrderModel = purchaseOrderModel1;
                    PurchaseOrderDetailModel searchPurchaseOrderModel = new PurchaseOrderDetailModel();
                    searchPurchaseOrderModel.setPurchaseOrderID(purchaseOrderModel1.getPurchaseOrderID());
                    searchPurchaseOrderModel.setStatus(TillBoxAppEnum.Status.Active.get());
                    vmPurchaseOrder.lstpurchaseOrderDetailModel = this.purchaseOrderDetailBllManager.getAllByConditions(searchPurchaseOrderModel);
                    lstVMPurchaseOrder.add(vmPurchaseOrder);
                }
            }

        } catch (Exception ex) {
            log.error("SupplierBllManager -> searchSupplier got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstVMPurchaseOrder;
    }


    public PurchaseOrderModel deletePurchaseOrder(PurchaseOrderModel purchaseOrderModel) {
        try {
            if (isValidDeleteObject(purchaseOrderModel)) {

                PurchaseOrderDetailModel purchaseOrderDetailModel = new PurchaseOrderDetailModel();
                purchaseOrderDetailModel.setPurchaseOrderID(purchaseOrderModel.getPurchaseOrderID());
                List<PurchaseOrderDetailModel> lstPurchaseOrderDetailModel = new ArrayList<>();
                lstPurchaseOrderDetailModel = this.purchaseOrderDetailBllManager.getAllByConditions(purchaseOrderDetailModel);

                purchaseOrderModel.setStatus(TillBoxAppEnum.Status.Deleted.get());
                this.update(purchaseOrderModel);
                for (PurchaseOrderDetailModel purchaseOrderDetailModel1 : lstPurchaseOrderDetailModel) {
                    purchaseOrderDetailModel1.setStatus(TillBoxAppEnum.Status.Deleted.get());
                    this.purchaseOrderDetailBllManager.update(purchaseOrderDetailModel1);
                }
            }
        } catch (Exception ex) {
            purchaseOrderModel = null;
        }

        return purchaseOrderModel;
    }


    private boolean isValidDeleteObject(PurchaseOrderModel purchaseOrderModel) throws Exception {

        if (purchaseOrderModel.getPurchaseOrderID() == null || purchaseOrderModel.getPurchaseOrderID() == 0) {
            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            Core.clientMessage.get().userMessage = MessageConstant.PURCHASE_ORDER_ID_CANNOT_BE_BLANK;
            return false;
        }


        SupplierInvoiceModel searchSupplierInvoiceModel = new SupplierInvoiceModel();
        searchSupplierInvoiceModel.setPurchaseOrderID(purchaseOrderModel.getPurchaseOrderID());

        List<SupplierInvoiceModel> lstSupplierInvoiceModel = new ArrayList<>();
        lstSupplierInvoiceModel = this.supplierInvoiceBllManager.getAllByConditions(searchSupplierInvoiceModel);
        if (lstSupplierInvoiceModel.size() > 0) {
            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            Core.clientMessage.get().userMessage = MessageConstant.PURCHASE_ORDER_CANNOT_BE_DELETE_IT_IS_ALREADY_INVOICED;
            return false;
        }


        return true;
    }


}
