/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 23/02/2018
 * Time: 11:33
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
import nybsys.tillboxweb.entities.SupplierAddress;
import nybsys.tillboxweb.models.SupplierAddressModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SupplierAddressBllManger extends BaseBll<SupplierAddress> {
    private static final Logger log = LoggerFactory.getLogger(SupplierAddressBllManger.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(SupplierAddress.class);
        Core.runTimeModelType.set(SupplierAddressModel.class);
    }

    public SupplierAddressModel saveOrUpdate(SupplierAddressModel supplierAddressModelReq) throws Exception {
        SupplierAddressModel supplierAddressModel = new SupplierAddressModel();
        List<SupplierAddressModel> lstSupplierAddressModel = new ArrayList<>();
        try {
            supplierAddressModel = supplierAddressModelReq;
            //save
            if (supplierAddressModel.getSupplierAddressID() == null || supplierAddressModel.getSupplierAddressID() == 0)
            {
                supplierAddressModel = this.save(supplierAddressModel);
                if (supplierAddressModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.SUPPLIER_ADDRESS_SAVE_FAILED;
                }
            } else { //update

                supplierAddressModel = this.update(supplierAddressModel);
                if (supplierAddressModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.SUPPLIER_ADDRESS_UPDATE_FAILED;
                }
            }

        } catch (Exception ex) {
            log.error("SupplierAddressBllManager -> saveOrUpdate got exception :"+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return supplierAddressModel;
    }

    public List<SupplierAddressModel> searchSupplierAddress(SupplierAddressModel supplierAddressModelReq) throws Exception {
        SupplierAddressModel supplierAddressModel = new SupplierAddressModel();
        List<SupplierAddressModel> lstSupplierAddressModel = new ArrayList<>();
        try {
            supplierAddressModel = supplierAddressModelReq;
            lstSupplierAddressModel = this.getAllByConditions(supplierAddressModel);
            if (lstSupplierAddressModel.size() == 0) {
                Core.clientMessage.get().message = MessageConstant.SUPPLIER_ADDRESS_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("SupplierAddressBllManager -> searchSupplierAddress got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstSupplierAddressModel;
    }
    public Boolean deleteSupplierAddress(Integer supplierID) throws Exception {
        SupplierAddressModel whereConditions = new SupplierAddressModel();
        SupplierAddressModel modelToUpdate = new SupplierAddressModel();
        Integer numberOfRowDeleted = 0;
        try {
            whereConditions.setSupplierID(supplierID);
            whereConditions.setStatus(TillBoxAppEnum.Status.Active.get());
            modelToUpdate.setStatus(TillBoxAppEnum.Status.Deleted.get());
            numberOfRowDeleted = this.updateByConditions(whereConditions,modelToUpdate);
            if (numberOfRowDeleted == 0) {
                Core.clientMessage.get().message = MessageConstant.SUPPLIER_ADDRESS_DELETE_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                return false;
            }
        } catch (Exception ex) {
            log.error("SupplierAddressBllManager -> deleteSupplierAddress got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return false;
    }
}
