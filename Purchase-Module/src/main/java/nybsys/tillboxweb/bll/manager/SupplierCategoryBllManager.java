/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 22/02/2018
 * Time: 11:52
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.coreEntities.SupplierCategory;
import nybsys.tillboxweb.coreModels.SupplierCategoryModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SupplierCategoryBllManager extends BaseBll<SupplierCategory> {
    private static final Logger log = LoggerFactory.getLogger(SupplierCategoryBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(SupplierCategory.class);
        Core.runTimeModelType.set(SupplierCategoryModel.class);
    }
    public SupplierCategoryModel saveOrUpdate(SupplierCategoryModel supplierCategoryModelReq) throws Exception {
        SupplierCategoryModel supplierCategoryModel = new SupplierCategoryModel();
        SupplierCategoryModel whereCondition = new SupplierCategoryModel();
        List<SupplierCategoryModel> lstSupplierCategoryModel = new ArrayList<>();
        try {
            supplierCategoryModel = supplierCategoryModelReq;

            //search first
            whereCondition.setCategoryName(supplierCategoryModel.getCategoryName());
            whereCondition.setBusinessID(supplierCategoryModel.getBusinessID());
            lstSupplierCategoryModel = this.searchSupplierCategory(whereCondition);
            Core.clientMessage.get().messageCode = null;
            //save
            if (supplierCategoryModel.getSupplierCategoryID() == null || supplierCategoryModel.getSupplierCategoryID() == 0)
            {
                //check duplicate save
                if(lstSupplierCategoryModel.size() > 0){
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.NAME_ALREADY_EXISTS;
                    Core.clientMessage.get().userMessage = MessageConstant.NAME_ALREADY_EXISTS;
                    return supplierCategoryModel;
                }
                supplierCategoryModel = this.save(supplierCategoryModel);
                if (supplierCategoryModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.SUPPLIER_CATEGORY_SAVE_FAILED;
                }
            } else { //update

                //check duplicate update
                if(lstSupplierCategoryModel.size() > 0){
                    //not self reflection
                    if(lstSupplierCategoryModel.get(0).getSupplierCategoryID().intValue() != supplierCategoryModel.getSupplierCategoryID().intValue()) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.NAME_ALREADY_EXISTS;
                        Core.clientMessage.get().userMessage = MessageConstant.NAME_ALREADY_EXISTS;
                        return supplierCategoryModel;
                    }
                }
                supplierCategoryModel = this.update(supplierCategoryModel);
                if (supplierCategoryModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.SUPPLIER_CATEGORY_UPDATE_FAILED;
                }
            }

        } catch (Exception ex) {
            log.error("SupplierCategoryBllManager -> saveOrUpdate got exception :"+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return supplierCategoryModel;
    }
    
    public List<SupplierCategoryModel> searchSupplierCategory(SupplierCategoryModel supplierCategoryModelReq) throws Exception {
        SupplierCategoryModel supplierCategoryModel = new SupplierCategoryModel();
        List<SupplierCategoryModel> lstSupplierCategoryModel = new ArrayList<>();
        try {
            supplierCategoryModel = supplierCategoryModelReq;
            lstSupplierCategoryModel = this.getAllByConditions(supplierCategoryModel);
            if (lstSupplierCategoryModel.size() == 0) {
                Core.clientMessage.get().message = MessageConstant.SUPPLIER_CATEGORY_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("SupplierCategoryBllManager -> searchSupplierCategory got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstSupplierCategoryModel;
    }
    public SupplierCategoryModel getSupplierCategoryByID(Integer supplierCategoryID) throws Exception {
        SupplierCategoryModel supplierCategoryModel = new SupplierCategoryModel();
        try {
            supplierCategoryModel = this.getById(supplierCategoryID);
            if (supplierCategoryModel == null) {
                Core.clientMessage.get().message = MessageConstant.SUPPLIER_CATEGORY_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("SupplierCategoryBllManager -> getSupplierCategoryByID got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return supplierCategoryModel;
    }
}
