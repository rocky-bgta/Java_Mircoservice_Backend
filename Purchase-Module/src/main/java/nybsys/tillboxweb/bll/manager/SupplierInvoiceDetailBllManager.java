/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/23/2018
 * Time: 4:27 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.entities.SupplierInvoice;
import nybsys.tillboxweb.entities.SupplierInvoiceDetail;
import nybsys.tillboxweb.models.*;
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
public class SupplierInvoiceDetailBllManager extends BaseBll<SupplierInvoiceDetail> {

    private static final Logger log = LoggerFactory.getLogger(SupplierInvoiceDetailBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(SupplierInvoiceDetail.class);
        Core.runTimeModelType.set(SupplierInvoiceDetailModel.class);
    }


    public VMSupplierInvoice saveSupplierInvoiceDetail(VMSupplierInvoice vmSupplierInvoice) throws Exception {

        try {

            for (SupplierInvoiceDetailModel supplierInvoiceDetailModel : vmSupplierInvoice.lstSupplierInvoiceDetailModel) {

                supplierInvoiceDetailModel.setSupplierInvoiceID(vmSupplierInvoice.supplierInvoiceModel.getSupplierInvoiceID());
                supplierInvoiceDetailModel.setStatus(TillBoxAppEnum.Status.Active.get());
                supplierInvoiceDetailModel.setCreatedBy("");
                supplierInvoiceDetailModel.setCreatedDate(new Date());
                supplierInvoiceDetailModel = this.save(supplierInvoiceDetailModel);
            }

        } catch (Exception ex) {
            log.error("SupplierInvoiceDetailBllManager -> saveSupplierInvoiceDetail got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return vmSupplierInvoice;
    }

    public Double getPriceSumByInvoiceID(Integer invoiceID) throws Exception {
        List<SupplierInvoiceDetailModel> lstSupplierInvoiceDetailModel;
        SupplierInvoiceDetailModel whereCondition = new SupplierInvoiceDetailModel();
        Double priceSum = 0.0;
        Double tempRowSum = 0.0;
        try {
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());
            whereCondition.setSupplierInvoiceID(invoiceID);
            lstSupplierInvoiceDetailModel = this.getAllByConditions(whereCondition);
            for (SupplierInvoiceDetailModel supplierInvoiceDetailModel : lstSupplierInvoiceDetailModel) {
                tempRowSum = (supplierInvoiceDetailModel.getQuantity() * supplierInvoiceDetailModel.getUnitPrice()) - supplierInvoiceDetailModel.getDiscount() + supplierInvoiceDetailModel.getVAT();
                priceSum += tempRowSum;
            }
        } catch (Exception ex) {
            log.error("SupplierInvoiceDetailBllManager -> getPriceSumByInvoiceID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return priceSum;
    }

}
