/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 3/19/2018
 * Time: 2:05 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.SupplierAdditionalCostSetting;
import nybsys.tillboxweb.models.SupplierAdditionalCostSettingModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SupplierAdditionalCostSettingBllManager extends BaseBll<SupplierAdditionalCostSetting> {
    private static final Logger log = LoggerFactory.getLogger(SupplierAdditionalCostSettingBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(SupplierAdditionalCostSetting.class);
        Core.runTimeModelType.set(SupplierAdditionalCostSettingModel.class);
    }

    public SupplierAdditionalCostSettingModel saveOrUpdate(SupplierAdditionalCostSettingModel supplierAdditionalCostSettingModelReq) throws Exception {
        SupplierAdditionalCostSettingModel supplierAdditionalCostSettingModel = new SupplierAdditionalCostSettingModel();
        SupplierAdditionalCostSettingModel whereCondition = new SupplierAdditionalCostSettingModel();
        List<SupplierAdditionalCostSettingModel> lstSupplierAdditionalCostSettingModel = new ArrayList<>();
        try {
            supplierAdditionalCostSettingModel = supplierAdditionalCostSettingModelReq;

            //search first
            whereCondition.setDescription(supplierAdditionalCostSettingModel.getDescription());
            whereCondition.setBusinessID(supplierAdditionalCostSettingModel.getBusinessID());
            lstSupplierAdditionalCostSettingModel = this.searchAdditionalCostSetting(whereCondition);
            Core.clientMessage.get().messageCode = null;
            //save
            if (supplierAdditionalCostSettingModel.getSupplierAdditionalCostSettingID() == null || supplierAdditionalCostSettingModel.getSupplierAdditionalCostSettingID() == 0) {
                //check duplicate save
                if (lstSupplierAdditionalCostSettingModel.size() > 0) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.NAME_ALREADY_EXISTS;
                    Core.clientMessage.get().userMessage = MessageConstant.NAME_ALREADY_EXISTS;
                    return supplierAdditionalCostSettingModel;
                }
                supplierAdditionalCostSettingModel = this.save(supplierAdditionalCostSettingModel);
                if (supplierAdditionalCostSettingModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.ADDITIONAL_COST_SETTING_SAVE_FAILED;
                }
            } else { //update

                //check duplicate update
                if (lstSupplierAdditionalCostSettingModel.size() > 0) {
                    //not self reflection
                    if (lstSupplierAdditionalCostSettingModel.get(0).getSupplierAdditionalCostSettingID().intValue() != supplierAdditionalCostSettingModel.getSupplierAdditionalCostSettingID().intValue()) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.NAME_ALREADY_EXISTS;
                        Core.clientMessage.get().userMessage = MessageConstant.NAME_ALREADY_EXISTS;
                        return supplierAdditionalCostSettingModel;
                    }
                }
                supplierAdditionalCostSettingModel = this.update(supplierAdditionalCostSettingModel);
                if (supplierAdditionalCostSettingModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.ADDITIONAL_COST_SETTING_UPDATE_FAILED;
                }
            }

        } catch (Exception ex) {
            log.error("SupplierAdditionalCostSettingBllManager -> saveOrUpdate got exception :" + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return supplierAdditionalCostSettingModel;
    }

    public List<SupplierAdditionalCostSettingModel> searchAdditionalCostSetting(SupplierAdditionalCostSettingModel supplierAdditionalCostSettingModelReq) throws Exception {
        SupplierAdditionalCostSettingModel supplierAdditionalCostSettingModel = new SupplierAdditionalCostSettingModel();
        List<SupplierAdditionalCostSettingModel> lstSupplierAdditionalCostSettingModel = new ArrayList<>();
        try {
            supplierAdditionalCostSettingModel = supplierAdditionalCostSettingModelReq;
            lstSupplierAdditionalCostSettingModel = this.getAllByConditions(supplierAdditionalCostSettingModel);
            if (lstSupplierAdditionalCostSettingModel.size() == 0) {
                Core.clientMessage.get().message = MessageConstant.ADDITIONAL_COST_SETTING_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("SupplierAdditionalCostSettingBllManager -> searchAdditionalCostSetting got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstSupplierAdditionalCostSettingModel;
    }

    public SupplierAdditionalCostSettingModel deleteAdditionalCostSetting(SupplierAdditionalCostSettingModel supplierAdditionalCostSettingModelReq) throws Exception {
        SupplierAdditionalCostSettingModel supplierAdditionalCostSettingModel = new SupplierAdditionalCostSettingModel();
        try {
            supplierAdditionalCostSettingModel = supplierAdditionalCostSettingModelReq;
            supplierAdditionalCostSettingModel = this.softDelete(supplierAdditionalCostSettingModel);
            if (supplierAdditionalCostSettingModel == null) {
                Core.clientMessage.get().message = MessageConstant.ADDITIONAL_COST_SETTING_DELETE_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("SupplierAdditionalCostSettingBllManager -> deleteAdditionalCostSetting got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return supplierAdditionalCostSettingModel;
    }

}
