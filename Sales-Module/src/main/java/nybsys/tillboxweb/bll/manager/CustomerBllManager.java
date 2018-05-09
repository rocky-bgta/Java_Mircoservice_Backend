/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 12/03/2018
 * Time: 4:15
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
import nybsys.tillboxweb.entities.Customer;
import nybsys.tillboxweb.models.CustomerModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CustomerBllManager extends BaseBll<Customer> {
    private static final Logger log = LoggerFactory.getLogger(CustomerBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(Customer.class);
        Core.runTimeModelType.set(CustomerModel.class);
    }

    public CustomerModel saveOrUpdate(CustomerModel customerModelReq) throws Exception {
        CustomerModel customerModel = new CustomerModel();
        List<CustomerModel> lstCustomerModel = new ArrayList<>();
        try {
            customerModel = customerModelReq;
            //save
            if (customerModel.getCustomerID() == null || customerModel.getCustomerID() == 0) {
                customerModel = this.save(customerModel);
                if (customerModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CUSTOMER_SAVE_FAILED;
                }
            } else { //update

                customerModel = this.update(customerModel);
                if (customerModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CUSTOMER_UPDATE_FAILED;
                }
            }

        } catch (Exception ex) {
            log.error("CustomerBllManager -> saveOrUpdate got exception :" + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return customerModel;
    }

    public List<CustomerModel> searchCustomer(CustomerModel customerModelReq) throws Exception {
        CustomerModel customerModel = new CustomerModel();
        List<CustomerModel> lstCustomerModel = new ArrayList<>();
        try {
            customerModel = customerModelReq;
            lstCustomerModel = this.getAllByConditions(customerModel);
            if (lstCustomerModel.size() == 0) {
                Core.clientMessage.get().message = MessageConstant.CUSTOMER_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("CustomerBllManager -> searchCustomer got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstCustomerModel;
    }

    public CustomerModel searchCustomerByID(int customerID, int businessID) throws Exception {
        CustomerModel customerModel = new CustomerModel();
        List<CustomerModel> lstCustomerModel = new ArrayList<>();
        try {
            customerModel.setBusinessID(businessID);
            customerModel.setCustomerID(customerID);
            customerModel.setStatus(TillBoxAppEnum.Status.Active.get());
            lstCustomerModel = this.getAllByConditions(customerModel);
            if (lstCustomerModel.size() > 0) {
                customerModel = lstCustomerModel.get(0);
            } else {
                Core.clientMessage.get().message = MessageConstant.CUSTOMER_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("CustomerBllManager -> searchCustomerByID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return customerModel;
    }

    public CustomerModel deleteCustomerByID(int customerID, int businessID) throws Exception {
        CustomerModel customerModel = new CustomerModel();
        try {
            customerModel.setCustomerID(customerID);
            customerModel.setBusinessID(businessID);
            customerModel = this.softDelete(customerModel);

            if (customerModel == null) {
                Core.clientMessage.get().message = MessageConstant.CUSTOMER_DELETE_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("CustomerBllManager -> deleteCustomerByID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return customerModel;
    }
}
