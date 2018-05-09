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
import nybsys.tillboxweb.entities.CustomerAdjustment;
import nybsys.tillboxweb.models.CustomerAdjustmentModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CustomerAdjustmentBllManager extends BaseBll<CustomerAdjustment>{
    private static final Logger log = LoggerFactory.getLogger(CustomerAdjustmentBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(CustomerAdjustment.class);
        Core.runTimeModelType.set(CustomerAdjustmentModel.class);
    }

    public CustomerAdjustmentModel saveOrUpdate(CustomerAdjustmentModel customerAdjustmentModelReq) throws Exception {
        CustomerAdjustmentModel customerAdjustmentModel = new CustomerAdjustmentModel();
        List<CustomerAdjustmentModel> lstCustomerAdjustmentModel = new ArrayList<>();
        try {
            customerAdjustmentModel = customerAdjustmentModelReq;
            //save
            if (customerAdjustmentModel.getCustomerAdjustmentID() == null || customerAdjustmentModel.getCustomerAdjustmentID() == 0)
            {
                // ============================= Create CAD0000001 =============================
                String currentDBSequence = null , buildDbSequence, hsql;
                hsql = "SELECT sa FROM CustomerAdjustment sa ORDER BY sa.customerAdjustmentID DESC";
                lstCustomerAdjustmentModel = this.executeHqlQuery(hsql, CustomerAdjustmentModel.class, TillBoxAppEnum.QueryType.GetOne.get());
                if (lstCustomerAdjustmentModel.size() > 0) {
                    currentDBSequence = lstCustomerAdjustmentModel.get(0).getAdjustmentNumber();
                }
                buildDbSequence = CoreUtils.getSequence(currentDBSequence,"CAD");
                // ==========================End Create CAD0000001 =============================

                customerAdjustmentModel.setAdjustmentNumber(buildDbSequence);
                customerAdjustmentModel = this.save(customerAdjustmentModel);
                if (customerAdjustmentModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CUSTOMER_ADJUSTMENT_SAVE_FAILED;
                }
            } else { //update

                customerAdjustmentModel = this.update(customerAdjustmentModel);
                if (customerAdjustmentModel == null) {
                    customerAdjustmentModel = null;
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CUSTOMER_ADJUSTMENT_UPDATE_FAILED;
                }
            }

        } catch (Exception ex) {
            log.error("CustomerAdjustmentBllManager -> saveOrUpdate got exception :"+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return customerAdjustmentModel;
    }
    public CustomerAdjustmentModel searchCustomerAdjustmentByID(int customerAdjustmentID, int businessID) throws Exception {
        CustomerAdjustmentModel customerAdjustmentModel = new CustomerAdjustmentModel();
        List<CustomerAdjustmentModel> lstCustomerAdjustmentModel = new ArrayList<>();
        try {
            customerAdjustmentModel.setBusinessID(businessID);
            customerAdjustmentModel.setCustomerAdjustmentID(customerAdjustmentID);
            customerAdjustmentModel.setStatus(TillBoxAppEnum.Status.Active.get());
            lstCustomerAdjustmentModel = this.getAllByConditions(customerAdjustmentModel);
            if (lstCustomerAdjustmentModel.size() > 0) {
                customerAdjustmentModel = lstCustomerAdjustmentModel.get(0);
            } else {
                customerAdjustmentModel= null;
                Core.clientMessage.get().message = MessageConstant.CUSTOMER_ADJUSTMENT_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("CustomerAdjustmentBllManager -> searchCustomerAdjustmentByID got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return customerAdjustmentModel;
    }
    public List<CustomerAdjustmentModel> searchCustomerAdjustment(CustomerAdjustmentModel customerAdjustmentModelReq) throws Exception {
        CustomerAdjustmentModel customerAdjustmentModel = new CustomerAdjustmentModel();
        List<CustomerAdjustmentModel> lstCustomerAdjustmentModel = new ArrayList<>();
        try {
            customerAdjustmentModel = customerAdjustmentModelReq;

            customerAdjustmentModel.setStatus(TillBoxAppEnum.Status.Active.get());
            lstCustomerAdjustmentModel = this.getAllByConditions(customerAdjustmentModel);
            if (lstCustomerAdjustmentModel.size() == 0) {
                Core.clientMessage.get().message = MessageConstant.CUSTOMER_ADJUSTMENT_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("CustomerAdjustmentBllManager -> searchCustomerAdjustmentByID got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstCustomerAdjustmentModel;
    }
    public CustomerAdjustmentModel deleteCustomerAdjustmentByCustomerAdjustmentID(Integer customerAdjustmentID) throws Exception {
        CustomerAdjustmentModel customerAdjustmentModel = new CustomerAdjustmentModel();
        try {
            customerAdjustmentModel.setCustomerAdjustmentID(customerAdjustmentID);
            customerAdjustmentModel = this.softDelete(customerAdjustmentModel);
            if (customerAdjustmentModel == null) {
                Core.clientMessage.get().message = MessageConstant.CUSTOMER_ADJUSTMENT_DELETE_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("CustomerAdjustmentBllManager -> deleteCustomerAdjustmentByCustomerAdjustmentID got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return customerAdjustmentModel;
    }

}
