/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 23/02/2018
 * Time: 11:47
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
import nybsys.tillboxweb.entities.SupplierContact;
import nybsys.tillboxweb.models.SupplierContactModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SupplierContactBllManager extends BaseBll<SupplierContact> {
    private static final Logger log = LoggerFactory.getLogger(SupplierContactBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(SupplierContact.class);
        Core.runTimeModelType.set(SupplierContactModel.class);
    }

    public SupplierContactModel saveOrUpdate(SupplierContactModel supplierContactModelReq) throws Exception {
        SupplierContactModel supplierContactModel = new SupplierContactModel();
        List<SupplierContactModel> lstSupplierContactModel = new ArrayList<>();
        try {
            supplierContactModel = supplierContactModelReq;
            //save
            if (supplierContactModel.getSupplierContactID() == null || supplierContactModel.getSupplierContactID() == 0)
            {
                supplierContactModel = this.save(supplierContactModel);
                if (supplierContactModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.SUPPLIER_CONTACT_SAVE_FAILED;
                }
            } else { //update

                supplierContactModel = this.update(supplierContactModel);
                if (supplierContactModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.SUPPLIER_CONTACT_UPDATE_FAILED;
                }
            }

        } catch (Exception ex) {
            log.error("SupplierContactBllManager -> saveOrUpdate got exception :"+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return supplierContactModel;
    }

    public List<SupplierContactModel> searchSupplierContact(SupplierContactModel supplierContactModelReq) throws Exception {
        SupplierContactModel supplierContactModel = new SupplierContactModel();
        List<SupplierContactModel> lstSupplierContactModel = new ArrayList<>();
        try {
            supplierContactModel = supplierContactModelReq;
            lstSupplierContactModel = this.getAllByConditions(supplierContactModel);
            if (lstSupplierContactModel.size() == 0) {
                Core.clientMessage.get().message = MessageConstant.SUPPLIER_CONTACT_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("SupplierContactBllManager -> searchSupplierContact got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstSupplierContactModel;
    }
    public Boolean deleteSupplierContact(Integer supplierID) throws Exception {
        SupplierContactModel whereConditions = new SupplierContactModel();
        SupplierContactModel modelToUpdate = new SupplierContactModel();
        Integer numberOfRowDeleted = 0;
        try {
            whereConditions.setSupplierID(supplierID);
            whereConditions.setStatus(TillBoxAppEnum.Status.Active.get());
            modelToUpdate.setStatus(TillBoxAppEnum.Status.Deleted.get());
            numberOfRowDeleted = this.updateByConditions(whereConditions,modelToUpdate);
            if (numberOfRowDeleted == 0) {
                Core.clientMessage.get().message = MessageConstant.SUPPLIER_CONTACT_DELETE_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                return false;
            }
        } catch (Exception ex) {
            log.error("SupplierContactBllManager -> deleteSupplierAddress got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return false;
    }
    
}
