/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 12/03/2018
 * Time: 2:47
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
import nybsys.tillboxweb.entities.CustomerContact;
import nybsys.tillboxweb.models.CustomerContactModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CustomerContactBllManager extends BaseBll<CustomerContact> {
    private static final Logger log = LoggerFactory.getLogger(CustomerContactBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(CustomerContact.class);
        Core.runTimeModelType.set(CustomerContactModel.class);
    }

    public CustomerContactModel saveOrUpdate(CustomerContactModel customerContactModelReq) throws Exception {
        CustomerContactModel customerContactModel = new CustomerContactModel();
        List<CustomerContactModel> lstCustomerContactModel = new ArrayList<>();
        try {
            customerContactModel = customerContactModelReq;
            //save
            if (customerContactModel.getCustomerContactID() == null || customerContactModel.getCustomerContactID() == 0)
            {
                customerContactModel = this.save(customerContactModel);
                if (customerContactModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CUSTOMER_CONTACT_SAVE_FAILED;
                }
            } else { //update

                customerContactModel = this.update(customerContactModel);
                if (customerContactModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CUSTOMER_CONTACT_UPDATE_FAILED;
                }
            }

        } catch (Exception ex) {
            log.error("CustomerContactBllManager -> saveOrUpdate got exception :"+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return customerContactModel;
    }

    public List<CustomerContactModel> searchCustomerContact(CustomerContactModel customerContactModelReq) throws Exception {
        CustomerContactModel customerContactModel = new CustomerContactModel();
        List<CustomerContactModel> lstCustomerContactModel = new ArrayList<>();
        try {
            customerContactModel = customerContactModelReq;
            lstCustomerContactModel = this.getAllByConditions(customerContactModel);
            if (lstCustomerContactModel.size() == 0) {
                Core.clientMessage.get().message = MessageConstant.CUSTOMER_CONTACT_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("CustomerContactBllManager -> searchCustomerContact got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstCustomerContactModel;
    }
    public Boolean deleteCustomerContact(Integer customerID) throws Exception {
        CustomerContactModel whereCondtions = new CustomerContactModel();
        CustomerContactModel modelToUpdate = new CustomerContactModel();
        Integer numberOfRowDeleted = 0;
        try {
            whereCondtions.setCustomerID(customerID);
            whereCondtions.setStatus(TillBoxAppEnum.Status.Active.get());
            modelToUpdate.setStatus(TillBoxAppEnum.Status.Deleted.get());
            numberOfRowDeleted = this.updateByConditions(whereCondtions,modelToUpdate);
            if (numberOfRowDeleted == 0) {
                Core.clientMessage.get().message = MessageConstant.CUSTOMER_CONTACT_DELETE_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                return false;
            }
        } catch (Exception ex) {
            log.error("CustomerContactBllManager -> deleteCustomerAddress got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return false;
    }
    
}
