/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 26/02/2018
 * Time: 04:41
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
import nybsys.tillboxweb.coreUtil.CoreUtils;
import nybsys.tillboxweb.entities.SupplierAdjustment;
import nybsys.tillboxweb.models.SupplierAdjustmentModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SupplierAdjustmentBllManager extends BaseBll<SupplierAdjustment>{
    private static final Logger log = LoggerFactory.getLogger(SupplierAdjustmentBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(SupplierAdjustment.class);
        Core.runTimeModelType.set(SupplierAdjustmentModel.class);
    }

    public SupplierAdjustmentModel saveOrUpdate(SupplierAdjustmentModel supplierAdjustmentModelReq) throws Exception {
        SupplierAdjustmentModel supplierAdjustmentModel = new SupplierAdjustmentModel();
        List<SupplierAdjustmentModel> lstSupplierAdjustmentModel = new ArrayList<>();
        try {
            supplierAdjustmentModel = supplierAdjustmentModelReq;
            //save
            if (supplierAdjustmentModel.getSupplierAdjustmentID() == null || supplierAdjustmentModel.getSupplierAdjustmentID() == 0)
            {
                // ============================= Create SAD0000001 =============================
                String currentDBSequence = null , buildDbSequence, hsql;
                hsql = "SELECT sa FROM SupplierAdjustment sa ORDER BY sa.supplierAdjustmentID DESC";
                lstSupplierAdjustmentModel = this.executeHqlQuery(hsql, SupplierAdjustmentModel.class, TillBoxAppEnum.QueryType.GetOne.get());
                if (lstSupplierAdjustmentModel.size() > 0) {
                    currentDBSequence = lstSupplierAdjustmentModel.get(0).getAdjustmentNumber();
                }
                buildDbSequence = CoreUtils.getSequence(currentDBSequence,"SAD");
                // ==========================End Create SAD0000001 =============================

                supplierAdjustmentModel.setAdjustmentNumber(buildDbSequence);
                supplierAdjustmentModel = this.save(supplierAdjustmentModel);
                if (supplierAdjustmentModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.SUPPLIER_ADJUSTMENT_SAVE_FAILED;
                }
            } else { //update

                supplierAdjustmentModel = this.update(supplierAdjustmentModel);
                if (supplierAdjustmentModel == null) {
                    supplierAdjustmentModel = null;
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.SUPPLIER_ADJUSTMENT_UPDATE_FAILED;
                }
            }

        } catch (Exception ex) {
            log.error("SupplierAdjustmentBllManager -> saveOrUpdate got exception :"+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return supplierAdjustmentModel;
    }
    public SupplierAdjustmentModel searchSupplierAdjustmentByID(int supplierAdjustmentID, int businessID) throws Exception {
        SupplierAdjustmentModel supplierAdjustmentModel = new SupplierAdjustmentModel();
        List<SupplierAdjustmentModel> lstSupplierAdjustmentModel = new ArrayList<>();
        try {
            supplierAdjustmentModel.setBusinessID(businessID);
            supplierAdjustmentModel.setSupplierAdjustmentID(supplierAdjustmentID);
            supplierAdjustmentModel.setStatus(TillBoxAppEnum.Status.Active.get());
            lstSupplierAdjustmentModel = this.getAllByConditions(supplierAdjustmentModel);
            if (lstSupplierAdjustmentModel.size() > 0) {
                supplierAdjustmentModel = lstSupplierAdjustmentModel.get(0);
            } else {
                supplierAdjustmentModel= null;
                Core.clientMessage.get().message = MessageConstant.SUPPLIER_ADJUSTMENT_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("SupplierAdjustmentBllManager -> searchSupplierAdjustmentByID got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return supplierAdjustmentModel;
    }
    public List<SupplierAdjustmentModel> searchSupplierAdjustment(SupplierAdjustmentModel supplierAdjustmentModelReq) throws Exception {
        SupplierAdjustmentModel supplierAdjustmentModel = new SupplierAdjustmentModel();
        List<SupplierAdjustmentModel> lstSupplierAdjustmentModel = new ArrayList<>();
        try {
            supplierAdjustmentModel = supplierAdjustmentModelReq;

            supplierAdjustmentModel.setStatus(TillBoxAppEnum.Status.Active.get());
            lstSupplierAdjustmentModel = this.getAllByConditions(supplierAdjustmentModel);
            if (lstSupplierAdjustmentModel.size() == 0) {
                Core.clientMessage.get().message = MessageConstant.SUPPLIER_ADJUSTMENT_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("SupplierAdjustmentBllManager -> searchSupplierAdjustmentByID got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstSupplierAdjustmentModel;
    }
    public SupplierAdjustmentModel deleteSupplierAdjustmentBySupplierAdjustmentID(Integer supplierAdjustmentID) throws Exception {
        SupplierAdjustmentModel supplierAdjustmentModel = new SupplierAdjustmentModel();
        try {
            supplierAdjustmentModel.setSupplierAdjustmentID(supplierAdjustmentID);
            supplierAdjustmentModel = this.softDelete(supplierAdjustmentModel);
            if (supplierAdjustmentModel == null) {
                Core.clientMessage.get().message = MessageConstant.SUPPLIER_ADJUSTMENT_DELETE_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("SupplierAdjustmentBllManager -> deleteSupplierAdjustmentBySupplierAdjustmentID got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return supplierAdjustmentModel;
    }

}
