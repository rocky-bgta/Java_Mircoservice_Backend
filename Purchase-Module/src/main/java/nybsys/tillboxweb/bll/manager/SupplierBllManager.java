/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 23/02/2018
 * Time: 11:15
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
import nybsys.tillboxweb.entities.Supplier;
import nybsys.tillboxweb.models.SupplierModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SupplierBllManager extends BaseBll<Supplier> {
    private static final Logger log = LoggerFactory.getLogger(SupplierBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(Supplier.class);
        Core.runTimeModelType.set(SupplierModel.class);
    }

    public SupplierModel saveOrUpdate(SupplierModel supplierModelReq) throws Exception {
        SupplierModel supplierModel = new SupplierModel();
        List<SupplierModel> lstSupplierModel = new ArrayList<>();
        try {
            supplierModel = supplierModelReq;
            //save
            if (supplierModel.getSupplierID() == null || supplierModel.getSupplierID() == 0)
            {
                supplierModel = this.save(supplierModel);
                if (supplierModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.SUPPLIER_SAVE_FAILED;
                }
            } else { //update

                supplierModel = this.update(supplierModel);
                if (supplierModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.SUPPLIER_UPDATE_FAILED;
                }
            }

        } catch (Exception ex) {
            log.error("SupplierBllManager -> saveOrUpdate got exception :"+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return supplierModel;
    }

    public List<SupplierModel> searchSupplier(SupplierModel supplierModelReq) throws Exception {
        SupplierModel supplierModel = new SupplierModel();
        List<SupplierModel> lstSupplierModel = new ArrayList<>();
        try {
            supplierModel = supplierModelReq;
            lstSupplierModel = this.getAllByConditions(supplierModel);
            if (lstSupplierModel.size() == 0) {
                Core.clientMessage.get().message = MessageConstant.SUPPLIER_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("SupplierBllManager -> searchSupplier got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstSupplierModel;
    }
    public SupplierModel searchSupplierByID(int supplierID,int businessID) throws Exception {
        SupplierModel supplierModel = new SupplierModel();
        List<SupplierModel> lstSupplierModel = new ArrayList<>();
        try {
            supplierModel.setBusinessID(businessID);
            supplierModel.setSupplierID(supplierID);
            supplierModel.setStatus(TillBoxAppEnum.Status.Active.get());
            lstSupplierModel = this.getAllByConditions(supplierModel);
            if (lstSupplierModel.size() > 0) {
                supplierModel = lstSupplierModel.get(0);
            } else {
                Core.clientMessage.get().message = MessageConstant.SUPPLIER_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("SupplierBllManager -> searchSupplierByID got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return supplierModel;
    }
    public SupplierModel deleteSupplierByID(int supplierID,int businessID) throws Exception {
        SupplierModel supplierModel = new SupplierModel();
        try {
            supplierModel.setSupplierID(supplierID);
            supplierModel.setBusinessID(businessID);
            supplierModel = this.softDelete(supplierModel);
            if (supplierModel == null) {
                Core.clientMessage.get().message = MessageConstant.SUPPLIER_DELETE_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("SupplierBllManager -> deleteSupplierByID got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return supplierModel;
    }
}
