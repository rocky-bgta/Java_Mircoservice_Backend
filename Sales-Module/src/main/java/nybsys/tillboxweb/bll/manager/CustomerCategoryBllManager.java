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
import nybsys.tillboxweb.coreEntities.CustomerCategory;
import nybsys.tillboxweb.coreModels.CustomerCategoryModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CustomerCategoryBllManager extends BaseBll<CustomerCategory> {
    private static final Logger log = LoggerFactory.getLogger(CustomerCategoryBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(CustomerCategory.class);
        Core.runTimeModelType.set(CustomerCategoryModel.class);
    }
    public CustomerCategoryModel saveOrUpdate(CustomerCategoryModel customerCategoryModelReq) throws Exception {
        CustomerCategoryModel customerCategoryModel = new CustomerCategoryModel();
        CustomerCategoryModel whereCondition = new CustomerCategoryModel();
        List<CustomerCategoryModel> lstCustomerCategoryModel = new ArrayList<>();
        try {
            customerCategoryModel = customerCategoryModelReq;

            //search first
            whereCondition.setCategoryName(customerCategoryModel.getCategoryName());
            whereCondition.setBusinessID(customerCategoryModel.getBusinessID());
            lstCustomerCategoryModel = this.searchCustomerCategory(whereCondition);
            Core.clientMessage.get().messageCode = null;
            //save
            if (customerCategoryModel.getCustomerCategoryID() == null || customerCategoryModel.getCustomerCategoryID() == 0)
            {
                //check duplicate save
                if(lstCustomerCategoryModel.size() > 0){
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.NAME_ALREADY_EXISTS;
                    Core.clientMessage.get().userMessage = MessageConstant.NAME_ALREADY_EXISTS;
                    return customerCategoryModel;
                }
                customerCategoryModel = this.save(customerCategoryModel);
                if (customerCategoryModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CUSTOMER_CATEGORY_SAVE_FAILED;
                }
            } else { //update

                //check duplicate update
                if(lstCustomerCategoryModel.size() > 0){
                    //not self reflection
                    if(lstCustomerCategoryModel.get(0).getCustomerCategoryID().intValue() != customerCategoryModel.getCustomerCategoryID().intValue()) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.NAME_ALREADY_EXISTS;
                        Core.clientMessage.get().userMessage = MessageConstant.NAME_ALREADY_EXISTS;
                        return customerCategoryModel;
                    }
                }
                customerCategoryModel = this.update(customerCategoryModel);
                if (customerCategoryModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CUSTOMER_CATEGORY_UPDATE_FAILED;
                }
            }

        } catch (Exception ex) {
            log.error("CustomerCategoryBllManager -> saveOrUpdate got exception :"+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return customerCategoryModel;
    }

    public List<CustomerCategoryModel> searchCustomerCategory(CustomerCategoryModel customerCategoryModelReq) throws Exception {
        CustomerCategoryModel customerCategoryModel = new CustomerCategoryModel();
        List<CustomerCategoryModel> lstCustomerCategoryModel = new ArrayList<>();
        try {
            customerCategoryModel = customerCategoryModelReq;
            lstCustomerCategoryModel = this.getAllByConditions(customerCategoryModel);
            if (lstCustomerCategoryModel.size() == 0) {
                Core.clientMessage.get().message = MessageConstant.CUSTOMER_CATEGORY_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("CustomerCategoryBllManager -> searchCustomerCategory got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstCustomerCategoryModel;
    }
    public CustomerCategoryModel getCustomerCategoryByID(Integer customerCategoryID) throws Exception {
        CustomerCategoryModel customerCategoryModel = new CustomerCategoryModel();
        try {
            customerCategoryModel = this.getById(customerCategoryID);
            if (customerCategoryModel == null) {
                Core.clientMessage.get().message = MessageConstant.CUSTOMER_CATEGORY_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("CustomerCategoryBllManager -> getCustomerCategoryByID got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return customerCategoryModel;
    }
}
