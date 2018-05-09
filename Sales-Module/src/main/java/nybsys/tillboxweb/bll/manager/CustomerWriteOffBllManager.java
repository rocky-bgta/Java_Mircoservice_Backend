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
import nybsys.tillboxweb.entities.CustomerWriteOff;
import nybsys.tillboxweb.models.CustomerWriteOffModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CustomerWriteOffBllManager extends BaseBll<CustomerWriteOff>{
    private static final Logger log = LoggerFactory.getLogger(CustomerWriteOffBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(CustomerWriteOff.class);
        Core.runTimeModelType.set(CustomerWriteOffModel.class);
    }

    public CustomerWriteOffModel saveOrUpdate(CustomerWriteOffModel customerWriteOffModelReq) throws Exception {
        CustomerWriteOffModel customerWriteOffModel = new CustomerWriteOffModel();
        List<CustomerWriteOffModel> lstCustomerWriteOffModel = new ArrayList<>();
        try {
            customerWriteOffModel = customerWriteOffModelReq;
            //save
            if (customerWriteOffModel.getCustomerWriteOffID() == null || customerWriteOffModel.getCustomerWriteOffID() == 0)
            {
                // ============================= Create CWO0000001 =============================
                String currentDBSequence = null , buildDbSequence, hsql;
                hsql = "SELECT sa FROM CustomerWriteOff sa ORDER BY sa.customerWriteOffID DESC";
                lstCustomerWriteOffModel = this.executeHqlQuery(hsql, CustomerWriteOffModel.class, TillBoxAppEnum.QueryType.GetOne.get());
                if (lstCustomerWriteOffModel.size() > 0) {
                    currentDBSequence = lstCustomerWriteOffModel.get(0).getWriteOffNumber();
                }
                buildDbSequence = CoreUtils.getSequence(currentDBSequence,"CWO");
                // ==========================End Create CWO0000001 =============================

                customerWriteOffModel.setWriteOffNumber(buildDbSequence);
                customerWriteOffModel = this.save(customerWriteOffModel);
                if (customerWriteOffModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CUSTOMER_WRITE_OFF_SAVE_FAILED;
                }
            } else { //update

                customerWriteOffModel = this.update(customerWriteOffModel);
                if (customerWriteOffModel == null) {
                    customerWriteOffModel = null;
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CUSTOMER_WRITE_OFF_UPDATE_FAILED;
                }
            }

        } catch (Exception ex) {
            log.error("CustomerWriteOffBllManager -> saveOrUpdate got exception :"+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return customerWriteOffModel;
    }
    public CustomerWriteOffModel searchCustomerWriteOffByID(int customerWriteOffID, int businessID) throws Exception {
        CustomerWriteOffModel customerWriteOffModel = new CustomerWriteOffModel();
        List<CustomerWriteOffModel> lstCustomerWriteOffModel = new ArrayList<>();
        try {
            customerWriteOffModel.setBusinessID(businessID);
            customerWriteOffModel.setCustomerWriteOffID(customerWriteOffID);
            customerWriteOffModel.setStatus(TillBoxAppEnum.Status.Active.get());
            lstCustomerWriteOffModel = this.getAllByConditions(customerWriteOffModel);
            if (lstCustomerWriteOffModel.size() > 0) {
                customerWriteOffModel = lstCustomerWriteOffModel.get(0);
            } else {
                customerWriteOffModel= null;
                Core.clientMessage.get().message = MessageConstant.CUSTOMER_WRITE_OFF_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("CustomerWriteOffBllManager -> searchCustomerWriteOffByID got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return customerWriteOffModel;
    }
    public List<CustomerWriteOffModel> searchCustomerWriteOff(CustomerWriteOffModel customerWriteOffModelReq) throws Exception {
        CustomerWriteOffModel customerWriteOffModel = new CustomerWriteOffModel();
        List<CustomerWriteOffModel> lstCustomerWriteOffModel = new ArrayList<>();
        try {
            customerWriteOffModel = customerWriteOffModelReq;

            customerWriteOffModel.setStatus(TillBoxAppEnum.Status.Active.get());
            lstCustomerWriteOffModel = this.getAllByConditions(customerWriteOffModel);
            if (lstCustomerWriteOffModel.size() == 0) {
                Core.clientMessage.get().message = MessageConstant.CUSTOMER_WRITE_OFF_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("CustomerWriteOffBllManager -> searchCustomerWriteOffByID got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstCustomerWriteOffModel;
    }
    public CustomerWriteOffModel deleteCustomerWriteOffByCustomerWriteOffID(Integer customerWriteOffID) throws Exception {
        CustomerWriteOffModel customerWriteOffModel = new CustomerWriteOffModel();
        try {
            customerWriteOffModel.setCustomerWriteOffID(customerWriteOffID);
            customerWriteOffModel = this.softDelete(customerWriteOffModel);
            if (customerWriteOffModel == null) {
                Core.clientMessage.get().message = MessageConstant.CUSTOMER_WRITE_OFF_DELETE_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("CustomerWriteOffBllManager -> deleteCustomerWriteOffByCustomerWriteOffID got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return customerWriteOffModel;
    }

}
