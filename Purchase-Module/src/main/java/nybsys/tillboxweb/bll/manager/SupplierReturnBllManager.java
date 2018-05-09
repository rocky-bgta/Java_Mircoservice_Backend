/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/23/2018
 * Time: 4:37 PM
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
import nybsys.tillboxweb.entities.SupplierReturn;
import nybsys.tillboxweb.models.SupplierReturnDetailModel;
import nybsys.tillboxweb.models.SupplierReturnModel;
import nybsys.tillboxweb.models.VMSupplierReturn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SupplierReturnBllManager extends BaseBll<SupplierReturn> {
    private static final Logger log = LoggerFactory.getLogger(SupplierReturnBllManager.class);

    @Autowired
    private SupplierReturnDetailBllManager supplierReturnDetailBllManager;

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(SupplierReturn.class);
        Core.runTimeModelType.set(SupplierReturnModel.class);
    }

    public VMSupplierReturn saveSupplierReturn(VMSupplierReturn vmSupplierReturn) throws Exception {

        try {
            if (isValidSupplierReturn(vmSupplierReturn)) {
                if (vmSupplierReturn.supplierReturnModel.getSupplierReturnID() != null && vmSupplierReturn.supplierReturnModel.getSupplierReturnID() > 0) {

                    vmSupplierReturn.supplierReturnModel.setUpdatedDate(new Date());
                    vmSupplierReturn.supplierReturnModel = this.update(vmSupplierReturn.supplierReturnModel);

              /* detail save*/

                    SupplierReturnDetailModel searchSupplierReturnDetailModel = new SupplierReturnDetailModel();
                    searchSupplierReturnDetailModel.setSupplierReturnID(vmSupplierReturn.supplierReturnModel.getSupplierReturnID());
                    searchSupplierReturnDetailModel.setStatus(TillBoxAppEnum.Status.Active.get());
                    List<SupplierReturnDetailModel> lstExistingSupplierReturnDetailModel = new ArrayList<>();
                    lstExistingSupplierReturnDetailModel = this.supplierReturnDetailBllManager.getAllByConditions(searchSupplierReturnDetailModel);


                    for (SupplierReturnDetailModel supplierReturnDetailModel : lstExistingSupplierReturnDetailModel) {
                        supplierReturnDetailModel.setUpdatedDate(new Date());
                        supplierReturnDetailModel.setStatus(TillBoxAppEnum.Status.Deleted.get());
                        supplierReturnDetailModel = this.supplierReturnDetailBllManager.update(supplierReturnDetailModel);
                    }


                    this.supplierReturnDetailBllManager.saveSupplierReturnDetail(vmSupplierReturn);
                } else {
                    vmSupplierReturn.supplierReturnModel.setStatus(TillBoxAppEnum.Status.Active.get());
                    vmSupplierReturn.supplierReturnModel.setCreatedBy("");
                    vmSupplierReturn.supplierReturnModel.setCreatedDate(new Date());
                    vmSupplierReturn.supplierReturnModel = this.save(vmSupplierReturn.supplierReturnModel);

               /* detail save*/
                    this.supplierReturnDetailBllManager.saveSupplierReturnDetail(vmSupplierReturn);
                }
            }

        } catch (Exception ex) {
            log.error("SupplierReturnBllManager -> SupplierReturnBllManager got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return vmSupplierReturn;
    }

    public SupplierReturnModel deleteSupplierReturn(SupplierReturnModel supplierReturnModel) throws Exception {

        try {

            if (supplierReturnModel.getSupplierReturnID() > 0) {

                supplierReturnModel.setUpdatedDate(new Date());
                supplierReturnModel.setStatus(TillBoxAppEnum.Status.Deleted.get());
                supplierReturnModel = this.update(supplierReturnModel);

                SupplierReturnDetailModel searchSupplierReturnModel = new SupplierReturnDetailModel();
                searchSupplierReturnModel.setSupplierReturnID(supplierReturnModel.getSupplierReturnID());
                List<SupplierReturnDetailModel> lstSupplierReturnDetailModel = new ArrayList<>();
                lstSupplierReturnDetailModel = this.supplierReturnDetailBllManager.getAllByConditions(searchSupplierReturnModel);
                for (SupplierReturnDetailModel supplierReturnDetailModel : lstSupplierReturnDetailModel) {
                    supplierReturnDetailModel.setStatus(TillBoxAppEnum.Status.Deleted.get());
                    this.supplierReturnDetailBllManager.update(supplierReturnDetailModel);
                }
            }

        } catch (Exception ex) {
            log.error("SupplierReturnBllManager -> SupplierReturnBllManager got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return supplierReturnModel;
    }


    public List<VMSupplierReturn> searchVMSupplierReturn(SupplierReturnModel supplierReturnModel) throws Exception {

        List<VMSupplierReturn> lstVMSupplierReturn = new ArrayList<>();
        try {
            List<SupplierReturnModel> lstSupplierReturnModel = new ArrayList<>();

            if (supplierReturnModel.getStatus() == null || supplierReturnModel.getStatus() == 0) {
                supplierReturnModel.setStatus(TillBoxAppEnum.Status.Active.get());
            }

            lstSupplierReturnModel = this.getAllByConditions(supplierReturnModel);
            if (lstSupplierReturnModel.size() > 0) {
                for (SupplierReturnModel supplierInvoiceModel1 : lstSupplierReturnModel) {
                    VMSupplierReturn vmSupplierReturn = new VMSupplierReturn();
                    vmSupplierReturn.supplierReturnModel = supplierInvoiceModel1;
                    SupplierReturnDetailModel searchSupplierReturnModel = new SupplierReturnDetailModel();
                    searchSupplierReturnModel.setSupplierReturnID(supplierInvoiceModel1.getSupplierReturnID());
                    searchSupplierReturnModel.setStatus(TillBoxAppEnum.Status.Active.get());
                    vmSupplierReturn.lstSupplierReturnDetailModel = this.supplierReturnDetailBllManager.getAllByConditions(searchSupplierReturnModel);
                    lstVMSupplierReturn.add(vmSupplierReturn);
                }
            }

        } catch (Exception ex) {
            log.error("SupplierBllManager -> searchSupplier got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstVMSupplierReturn;
    }


    private Boolean isValidSupplierReturn(SupplierReturnModel supplierReturnModel) throws Exception {


        if (supplierReturnModel.getBusinessID() == null || supplierReturnModel.getBusinessID() == 0) {
            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            Core.clientMessage.get().userMessage = MessageConstant.DUPLICATE_SUPPLIER_RETURN_NUMBER;
            return false;
        }
        if (supplierReturnModel.getSupplierReturnID() == null || supplierReturnModel.getSupplierReturnID() == 0) {
            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            Core.clientMessage.get().userMessage = MessageConstant.DUPLICATE_SUPPLIER_RETURN_NUMBER;
            return false;
        }
        SupplierReturnModel existingSupplierReturnModel = this.getById(supplierReturnModel.getSupplierReturnID());

        if (existingSupplierReturnModel == null) {
            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            Core.clientMessage.get().userMessage = MessageConstant.DUPLICATE_SUPPLIER_RETURN_NUMBER;
        }


        return true;
    }


    private Boolean isValidSupplierReturn(VMSupplierReturn vmSupplierReturn) throws Exception {
        SupplierReturnModel existingSupplierReturnModel = new SupplierReturnModel();
        existingSupplierReturnModel.setSupplierReturnNumber(vmSupplierReturn.supplierReturnModel.getSupplierReturnNumber());
        List<SupplierReturnModel> lstSupplierReturnModel = new ArrayList<>();


        lstSupplierReturnModel = this.getAllByConditions(existingSupplierReturnModel);


        if (lstSupplierReturnModel.size() > 0) {
            existingSupplierReturnModel = lstSupplierReturnModel.get(0);
            if ((existingSupplierReturnModel.getSupplierReturnID() != null && existingSupplierReturnModel.getSupplierReturnID() > 0) && existingSupplierReturnModel.getSupplierReturnID().intValue() != vmSupplierReturn.supplierReturnModel.getSupplierReturnID().intValue()) {

                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().userMessage = MessageConstant.DUPLICATE_SUPPLIER_RETURN_NUMBER;
                return false;

            }
        }


        return true;
    }


}
