/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 4:53
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.SupplierAdditionalCost;
import nybsys.tillboxweb.models.SupplierAdditionalCostModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SupplierAdditionalCostBllManager extends BaseBll<SupplierAdditionalCost> {
    private static final Logger log = LoggerFactory.getLogger(SupplierAdditionalCostBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(SupplierAdditionalCost.class);
        Core.runTimeModelType.set(SupplierAdditionalCostModel.class);
    }

    public SupplierAdditionalCostModel saveOrUpdate(SupplierAdditionalCostModel supplierAdditionalCostModelReq) throws Exception {
        SupplierAdditionalCostModel supplierAdditionalCostModel = new SupplierAdditionalCostModel();
        List<SupplierAdditionalCostModel> lstAdditionalCostModel = new ArrayList<>();
        try {
            supplierAdditionalCostModel = supplierAdditionalCostModelReq;
            //save
            if (supplierAdditionalCostModel.getSupplierAdditionalCostID() == null || supplierAdditionalCostModel.getSupplierAdditionalCostID() == 0)
            {
                supplierAdditionalCostModel = this.save(supplierAdditionalCostModel);
                if (supplierAdditionalCostModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.ADDITIONAL_COST_SAVE_FAILED;
                }
            } else { //update

                supplierAdditionalCostModel = this.update(supplierAdditionalCostModel);
                if (supplierAdditionalCostModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.ADDITIONAL_COST_UPDATE_FAILED;
                }
            }

        } catch (Exception ex) {
            log.error("SupplierAdditionalCostBllManager -> saveOrUpdate got exception :"+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return supplierAdditionalCostModel;
    }

    public List<SupplierAdditionalCostModel> searchAdditionalCost(SupplierAdditionalCostModel supplierAdditionalCostModelReq) throws Exception {
        SupplierAdditionalCostModel supplierAdditionalCostModel = new SupplierAdditionalCostModel();
        List<SupplierAdditionalCostModel> lstAdditionalCostModel = new ArrayList<>();
        try {
            supplierAdditionalCostModel = supplierAdditionalCostModelReq;
            lstAdditionalCostModel = this.getAllByConditions(supplierAdditionalCostModel);
            if (lstAdditionalCostModel.size() == 0) {
                Core.clientMessage.get().message = MessageConstant.ADDITIONAL_COST_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("SupplierAdditionalCostBllManager -> searchAdditionalCost got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstAdditionalCostModel;
    }
    public SupplierAdditionalCostModel deleteAdditionalCost(SupplierAdditionalCostModel supplierAdditionalCostModelReq) throws Exception {
        SupplierAdditionalCostModel supplierAdditionalCostModel = new SupplierAdditionalCostModel();
        try {
            supplierAdditionalCostModel = supplierAdditionalCostModelReq;
            supplierAdditionalCostModel = this.softDelete(supplierAdditionalCostModel);
            if (supplierAdditionalCostModel == null) {
                Core.clientMessage.get().message = MessageConstant.ADDITIONAL_COST_DELETE_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("SupplierAdditionalCostBllManager -> deleteAdditionalCost got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return supplierAdditionalCostModel;
    }
    
}
