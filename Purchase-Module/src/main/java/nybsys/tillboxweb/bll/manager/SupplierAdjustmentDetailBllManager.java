/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 26/02/2018
 * Time: 05:18
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
import nybsys.tillboxweb.coreEnum.Adjustment;
import nybsys.tillboxweb.entities.SupplierAdjustmentDetail;
import nybsys.tillboxweb.enumpurches.PaymentStatus;
import nybsys.tillboxweb.models.SupplierAdjustmentDetailModel;
import nybsys.tillboxweb.models.SupplierAdjustmentModel;
import nybsys.tillboxweb.models.SupplierInvoiceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SupplierAdjustmentDetailBllManager extends BaseBll<SupplierAdjustmentDetail> {
    private static final Logger log = LoggerFactory.getLogger(SupplierAdjustmentDetailBllManager.class);

    @Autowired
    private SupplierInvoiceDetailBllManager supplierInvoiceDetailBllManager;
    @Autowired
    private SupplierPaymentDetailBllManager supplierPaymentDetailBllManager;
    @Autowired
    private SupplierReturnDetailBllManager supplierReturnDetailBllManager;
    @Autowired
    private SupplierInvoiceBllManager supplierInvoiceBllManager;

    private SupplierAdjustmentBllManager supplierAdjustmentBllManager = new SupplierAdjustmentBllManager();

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(SupplierAdjustmentDetail.class);
        Core.runTimeModelType.set(SupplierAdjustmentDetailModel.class);
    }

    public List<SupplierAdjustmentDetailModel> saveOrUpdateList(List<SupplierAdjustmentDetailModel> lstSupplierAdjustmentDetailModelReq,Integer supplierAdjustmentID) throws Exception {
        List<SupplierAdjustmentDetailModel> lstSupplierAdjustmentDetailModel = new ArrayList<>();
        try {
            lstSupplierAdjustmentDetailModel = lstSupplierAdjustmentDetailModelReq;
            //check given adjusted amount is less then due amount
            for (SupplierAdjustmentDetailModel supplierAdjustmentDetailModel : lstSupplierAdjustmentDetailModel) {
                if (supplierAdjustmentDetailModel.getAdjustAmount().doubleValue() > getDueAmount(supplierAdjustmentDetailModel.getSupplierInvoiceID()).doubleValue()) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().userMessage = MessageConstant.SUPPLIER_ADJUSTED_AMOUNT_IS_GREATER_THAN_DUE_AMOUNT;
                }
            }

            Core.clientMessage.get().messageCode = null;
            for (SupplierAdjustmentDetailModel supplierAdjustmentDetailModel : lstSupplierAdjustmentDetailModel) {
                supplierAdjustmentDetailModel.setSupplierAdjustmentID(supplierAdjustmentID.intValue());
                supplierAdjustmentDetailModel.setSupplierAdjustmentDetailID(null);

                //save
                    supplierAdjustmentDetailModel = this.save(supplierAdjustmentDetailModel);
                    if (supplierAdjustmentDetailModel == null) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.SUPPLIER_ADJUSTMENT_DETAIL_SAVE_FAILED;
                    }

            }

        } catch (Exception ex) {
            log.error("SupplierAdjustmentDetailBllManager -> saveOrUpdate got exception :" + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstSupplierAdjustmentDetailModel;
    }

    public List<SupplierAdjustmentDetailModel> searchSupplierAdjustmentDetailByAdjustmentID(int supplierAdjustmentID) throws Exception {
        SupplierAdjustmentDetailModel supplierAdjustmentDetailModel = new SupplierAdjustmentDetailModel();
        List<SupplierAdjustmentDetailModel> lstSupplierAdjustmentDetailModel = new ArrayList<>();
        try {
            supplierAdjustmentDetailModel.setSupplierAdjustmentID(supplierAdjustmentID);
            supplierAdjustmentDetailModel.setStatus(TillBoxAppEnum.Status.Active.get());
            lstSupplierAdjustmentDetailModel = this.getAllByConditions(supplierAdjustmentDetailModel);
            if (lstSupplierAdjustmentDetailModel.size() == 0) {
                Core.clientMessage.get().message = MessageConstant.SUPPLIER_ADJUSTMENT_DETAIL_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("SupplierAdjustmentDetailBllManager -> searchSupplierByID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstSupplierAdjustmentDetailModel;
    }

    public Double getPriceSumByInvoiceID(Integer invoiceID) throws Exception {
        List<SupplierAdjustmentDetailModel> lstSupplierAdjustmentDetailModel;
        SupplierAdjustmentDetailModel whereCondition = new SupplierAdjustmentDetailModel();
        SupplierAdjustmentModel supplierAdjustmentModel = new SupplierAdjustmentModel();
        Double priceSum = 0.0;
        try {
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());
            whereCondition.setSupplierInvoiceID(invoiceID);
            lstSupplierAdjustmentDetailModel = this.getAllByConditions(whereCondition);
            for (SupplierAdjustmentDetailModel supplierAdjustmentDetailModel : lstSupplierAdjustmentDetailModel) {
                supplierAdjustmentModel = this.supplierAdjustmentBllManager.getById(supplierAdjustmentDetailModel.getSupplierAdjustmentID(),TillBoxAppEnum.Status.Active.get());
                if(supplierAdjustmentModel != null && supplierAdjustmentModel.getEffectType() == Adjustment.Increase.get())
                {
                    priceSum -= supplierAdjustmentDetailModel.getAdjustAmount();
                }else
                {
                    priceSum += supplierAdjustmentDetailModel.getAdjustAmount();
                }
            }
        } catch (Exception ex) {
            log.error("SupplierAdjustmentDetailBllManager -> getPriceSumByInvoiceID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return priceSum;
    }

    public Double getDueAmount(Integer invoiceID) throws Exception {
        Double totalDueAmount = 0.0;
        try {
            totalDueAmount -= this.getPriceSumByInvoiceID(invoiceID);
            totalDueAmount += this.supplierInvoiceDetailBllManager.getPriceSumByInvoiceID(invoiceID);
            totalDueAmount -= this.supplierPaymentDetailBllManager.getPriceSumByInvoiceID(invoiceID);
            totalDueAmount -= this.supplierReturnDetailBllManager.getPriceSumByInvoiceID(invoiceID);
        } catch (Exception ex) {
            log.error("SupplierAdjustmentDetailBllManager -> getDueAmount got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return totalDueAmount;
    }

    public Integer deleteSupplierAdjustDetailBySupplierAdjustmentID(Integer supplierAdjustmentID) throws Exception {
        SupplierAdjustmentDetailModel whereCondition = new SupplierAdjustmentDetailModel();
        SupplierAdjustmentDetailModel modelUpdateCondition = new SupplierAdjustmentDetailModel();
        List<SupplierAdjustmentDetailModel> lstSupplierAdjustmentDetailModel;
        SupplierInvoiceModel supplierInvoiceModel;
        Integer deleteCounter = 0;
        try {
            whereCondition.setSupplierAdjustmentID(supplierAdjustmentID);
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());

            lstSupplierAdjustmentDetailModel = this.getAllByConditions(whereCondition);
            if (lstSupplierAdjustmentDetailModel.size() > 0) {
                for (SupplierAdjustmentDetailModel supplierAdjustmentDetailModel : lstSupplierAdjustmentDetailModel) {
                    //change paid status in invoice
                    supplierInvoiceModel = this.supplierInvoiceBllManager.getByInvoiceID(supplierAdjustmentDetailModel.getSupplierInvoiceID());
                    if(supplierInvoiceModel != null && supplierInvoiceModel.getPaymentStatus().intValue() == PaymentStatus.Paid.get()){
                        supplierInvoiceModel.setPaymentStatus(PaymentStatus.Due.get());
                        supplierInvoiceModel = this.supplierInvoiceBllManager.update(supplierInvoiceModel);
                        if(supplierInvoiceModel == null)
                        {
                            Core.clientMessage.get().message = MessageConstant.SUPPLIER_INVOICE_UPDATE_FAILED;
                            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                            return deleteCounter;
                        }
                    }
                    //delete supplier adjustment detail;
                    supplierAdjustmentDetailModel = this.softDelete(supplierAdjustmentDetailModel);
                    if(supplierAdjustmentDetailModel == null)
                    {
                        Core.clientMessage.get().message = MessageConstant.SUPPLIER_ADJUSTMENT_DETAIL_DELETE_FAILED;
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        return deleteCounter;
                    }
                    deleteCounter++;
                }
            }

        } catch (Exception ex) {
            log.error("SupplierAdjustmentDetailBllManager -> deleteSupplierAdjustDetailBySupplierAdjustmentID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return deleteCounter;
    }
}
