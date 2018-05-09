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
import nybsys.tillboxweb.coreEntities.CustomerType;
import nybsys.tillboxweb.coreModels.CustomerTypeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CustomerTypeBllManager extends BaseBll<CustomerType> {
    private static final Logger log = LoggerFactory.getLogger(CustomerTypeBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(CustomerType.class);
        Core.runTimeModelType.set(CustomerTypeModel.class);
    }
    public CustomerTypeModel saveOrUpdate(CustomerTypeModel customerTypeModelReq) throws Exception {
        CustomerTypeModel customerTypeModel = new CustomerTypeModel();
        CustomerTypeModel whereCondition = new CustomerTypeModel();
        List<CustomerTypeModel> lstCustomerTypeModel = new ArrayList<>();
        try {
            customerTypeModel = customerTypeModelReq;

            //search first
            whereCondition.setName(customerTypeModel.getName());
            whereCondition.setBusinessID(customerTypeModel.getBusinessID());
            lstCustomerTypeModel = this.searchCustomerType(whereCondition);
            Core.clientMessage.get().messageCode = null;
            //save
            if (customerTypeModel.getCustomerTypeID() == null || customerTypeModel.getCustomerTypeID() == 0)
            {
                //check duplicate save
                if(lstCustomerTypeModel.size() > 0){
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.NAME_ALREADY_EXISTS;
                    Core.clientMessage.get().userMessage = MessageConstant.NAME_ALREADY_EXISTS;
                    return customerTypeModel;
                }
                customerTypeModel = this.save(customerTypeModel);
                if (customerTypeModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CUSTOMER_TYPE_SAVE_FAILED;
                }
            } else { //update

                //check duplicate update
                if(lstCustomerTypeModel.size() > 0){
                    //not self reflection
                    if(lstCustomerTypeModel.get(0).getCustomerTypeID().intValue() != customerTypeModel.getCustomerTypeID().intValue()) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.NAME_ALREADY_EXISTS;
                        Core.clientMessage.get().userMessage = MessageConstant.NAME_ALREADY_EXISTS;
                        return customerTypeModel;
                    }
                }
                customerTypeModel = this.update(customerTypeModel);
                if (customerTypeModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CUSTOMER_TYPE_UPDATE_FAILED;
                }
            }

        } catch (Exception ex) {
            log.error("CustomerTypeBllManager -> saveOrUpdate got exception :"+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return customerTypeModel;
    }

    public List<CustomerTypeModel> searchCustomerType(CustomerTypeModel customerTypeModelReq) throws Exception {
        CustomerTypeModel customerTypeModel = new CustomerTypeModel();
        List<CustomerTypeModel> lstCustomerTypeModel = new ArrayList<>();
        try {
            customerTypeModel = customerTypeModelReq;
            lstCustomerTypeModel = this.getAllByConditions(customerTypeModel);
            if (lstCustomerTypeModel.size() == 0) {
                Core.clientMessage.get().message = MessageConstant.CUSTOMER_TYPE_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("CustomerTypeBllManager -> searchCustomerType got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstCustomerTypeModel;
    }
    public CustomerTypeModel getCustomerTypeByID(Integer customerTypeID) throws Exception {
        CustomerTypeModel customerTypeModel = new CustomerTypeModel();
        try {
            customerTypeModel = this.getById(customerTypeID);
            if (customerTypeModel == null) {
                Core.clientMessage.get().message = MessageConstant.CUSTOMER_TYPE_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("CustomerTypeBllManager -> getCustomerTypeByID got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return customerTypeModel;
    }

    public CustomerTypeModel deleteCustomerType(CustomerTypeModel customerTypeModelReq) throws Exception {
        CustomerTypeModel customerTypeModel = new CustomerTypeModel();
        try {
            customerTypeModel = customerTypeModelReq;
            customerTypeModel = this.softDelete(customerTypeModel);
            if (customerTypeModel == null) {
                Core.clientMessage.get().message = MessageConstant.CUSTOMER_TYPE_DELETE_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("CustomerTypeBllManager -> deleteCustomerType got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return customerTypeModel;
    }
}
