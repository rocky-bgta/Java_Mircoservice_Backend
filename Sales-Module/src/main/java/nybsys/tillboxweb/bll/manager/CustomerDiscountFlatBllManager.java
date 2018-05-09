/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 4:53
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.CustomerDiscountFlat;
import nybsys.tillboxweb.models.CustomerDiscountFlatModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CustomerDiscountFlatBllManager extends BaseBll<CustomerDiscountFlat> {
    private static final Logger log = LoggerFactory.getLogger(CustomerDiscountFlatBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(CustomerDiscountFlat.class);
        Core.runTimeModelType.set(CustomerDiscountFlatModel.class);
    }

    public CustomerDiscountFlatModel saveOrUpdate(CustomerDiscountFlatModel customerDiscountFlatModelReq) throws Exception {
        CustomerDiscountFlatModel customerDiscountFlatModel = new CustomerDiscountFlatModel();
        List<CustomerDiscountFlatModel> lstCustomerDiscountFlatModel = new ArrayList<>();
        try {
            customerDiscountFlatModel = customerDiscountFlatModelReq;
            //save
            if (customerDiscountFlatModel.getCustomerDiscountFlatID() == null || customerDiscountFlatModel.getCustomerDiscountFlatID() == 0)
            {
                customerDiscountFlatModel = this.save(customerDiscountFlatModel);
                if (customerDiscountFlatModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CUSTOMER_DISCOUNT_FLAT_SAVE_FAILED;
                }
            } else { //update

                customerDiscountFlatModel = this.update(customerDiscountFlatModel);
                if (customerDiscountFlatModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CUSTOMER_DISCOUNT_FLAT_UPDATE_FAILED;
                }
            }

        } catch (Exception ex) {
            log.error("CustomerDiscountFlatBllManager -> saveOrUpdate got exception :"+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return customerDiscountFlatModel;
    }

    public List<CustomerDiscountFlatModel> searchCustomerDiscountFlat(CustomerDiscountFlatModel customerDiscountFlatModelReq) throws Exception {
        CustomerDiscountFlatModel customerDiscountFlatModel = new CustomerDiscountFlatModel();
        List<CustomerDiscountFlatModel> lstCustomerDiscountFlatModel = new ArrayList<>();
        try {
            customerDiscountFlatModel = customerDiscountFlatModelReq;
            lstCustomerDiscountFlatModel = this.getAllByConditions(customerDiscountFlatModel);
            if (lstCustomerDiscountFlatModel.size() == 0) {
                Core.clientMessage.get().message = MessageConstant.CUSTOMER_DISCOUNT_FLAT_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("CustomerDiscountFlatBllManager -> searchCustomerDiscountFlat got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstCustomerDiscountFlatModel;
    }
    public CustomerDiscountFlatModel deleteCustomerDiscountFlat(CustomerDiscountFlatModel customerDiscountFlatModelReq) throws Exception {
        CustomerDiscountFlatModel customerDiscountFlatModel = new CustomerDiscountFlatModel();
        try {
            customerDiscountFlatModel = customerDiscountFlatModelReq;
            customerDiscountFlatModel = this.softDelete(customerDiscountFlatModel);
            if (customerDiscountFlatModel == null) {
                Core.clientMessage.get().message = MessageConstant.CUSTOMER_DISCOUNT_FLAT_DELETE_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("CustomerDiscountFlatBllManager -> deleteCustomerDiscountFlat got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return customerDiscountFlatModel;
    }
    
}
