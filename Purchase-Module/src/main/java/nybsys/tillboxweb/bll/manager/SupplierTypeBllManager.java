/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 12/03/2018
 * Time: 12:03
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.coreEntities.SupplierType;
import nybsys.tillboxweb.coreModels.SupplierTypeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SupplierTypeBllManager extends BaseBll<SupplierType> {
    private static final Logger log = LoggerFactory.getLogger(SupplierTypeBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(SupplierType.class);
        Core.runTimeModelType.set(SupplierTypeModel.class);
    }
    public SupplierTypeModel saveOrUpdate(SupplierTypeModel supplierTypeModelReq) throws Exception {
        SupplierTypeModel supplierTypeModel = new SupplierTypeModel();
        SupplierTypeModel whereCondition = new SupplierTypeModel();
        List<SupplierTypeModel> lstSupplierTypeModel = new ArrayList<>();
        try {
            supplierTypeModel = supplierTypeModelReq;

            //search first
            whereCondition.setName(supplierTypeModel.getName());
            whereCondition.setBusinessID(supplierTypeModel.getBusinessID());
            lstSupplierTypeModel = this.searchSupplierType(whereCondition);
            Core.clientMessage.get().messageCode = null;
            //save
            if (supplierTypeModel.getSupplierTypeID() == null || supplierTypeModel.getSupplierTypeID() == 0)
            {
                //check duplicate save
                if(lstSupplierTypeModel.size() > 0){
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.NAME_ALREADY_EXISTS;
                    Core.clientMessage.get().userMessage = MessageConstant.NAME_ALREADY_EXISTS;
                    return supplierTypeModel;
                }
                supplierTypeModel = this.save(supplierTypeModel);
                if (supplierTypeModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.SUPPLIER_TYPE_SAVE_FAILED;
                }
            } else { //update

                //check duplicate update
                if(lstSupplierTypeModel.size() > 0){
                    //not self reflection
                    if(lstSupplierTypeModel.get(0).getSupplierTypeID().intValue() != supplierTypeModel.getSupplierTypeID().intValue()) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.NAME_ALREADY_EXISTS;
                        Core.clientMessage.get().userMessage = MessageConstant.NAME_ALREADY_EXISTS;
                        return supplierTypeModel;
                    }
                }
                supplierTypeModel = this.update(supplierTypeModel);
                if (supplierTypeModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.SUPPLIER_TYPE_UPDATE_FAILED;
                }
            }

        } catch (Exception ex) {
            log.error("SupplierTypeBllManager -> saveOrUpdate got exception :"+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return supplierTypeModel;
    }

    public List<SupplierTypeModel> searchSupplierType(SupplierTypeModel supplierTypeModelReq) throws Exception {
        SupplierTypeModel supplierTypeModel = new SupplierTypeModel();
        List<SupplierTypeModel> lstSupplierTypeModel = new ArrayList<>();
        try {
            supplierTypeModel = supplierTypeModelReq;
            lstSupplierTypeModel = this.getAllByConditions(supplierTypeModel);
            if (lstSupplierTypeModel.size() == 0) {
                Core.clientMessage.get().message = MessageConstant.SUPPLIER_TYPE_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("SupplierTypeBllManager -> searchSupplierType got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstSupplierTypeModel;
    }
    public SupplierTypeModel getSupplierTypeByID(Integer supplierTypeID) throws Exception {
        SupplierTypeModel supplierTypeModel = new SupplierTypeModel();
        try {
            supplierTypeModel = this.getById(supplierTypeID);
            if (supplierTypeModel == null) {
                Core.clientMessage.get().message = MessageConstant.SUPPLIER_TYPE_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("SupplierTypeBllManager -> getSupplierTypeByID got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return supplierTypeModel;
    }

    public SupplierTypeModel deleteSupplierType(SupplierTypeModel supplierTypeModelReq) throws Exception {
        SupplierTypeModel supplierTypeModel = new SupplierTypeModel();
        try {
            supplierTypeModel = supplierTypeModelReq;
            supplierTypeModel = this.softDelete(supplierTypeModel);
            if (supplierTypeModel == null) {
                Core.clientMessage.get().message = MessageConstant.SUPPLIER_TYPE_DELETE_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("SupplierTypeBllManager -> deleteSupplierType got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return supplierTypeModel;
    }
}
