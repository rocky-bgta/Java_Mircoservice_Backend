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
import nybsys.tillboxweb.entities.CustomerDiscountRange;
import nybsys.tillboxweb.models.CustomerDiscountRangeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CustomerDiscountRangeBllManager extends BaseBll<CustomerDiscountRange> {
    private static final Logger log = LoggerFactory.getLogger(CustomerDiscountRangeBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(CustomerDiscountRange.class);
        Core.runTimeModelType.set(CustomerDiscountRangeModel.class);
    }

    public CustomerDiscountRangeModel saveOrUpdate(CustomerDiscountRangeModel customerDiscountRangeModelReq) throws Exception {
        CustomerDiscountRangeModel customerDiscountRangeModel = new CustomerDiscountRangeModel();
        List<CustomerDiscountRangeModel> lstCustomerDiscountRangeModel = new ArrayList<>();
        try {
            customerDiscountRangeModel = customerDiscountRangeModelReq;
            //save
            if (customerDiscountRangeModel.getCustomerDiscountRangeID() == null || customerDiscountRangeModel.getCustomerDiscountRangeID() == 0)
            {
                customerDiscountRangeModel = this.save(customerDiscountRangeModel);
                if (customerDiscountRangeModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CUSTOMER_DISCOUNT_RANGE_SAVE_FAILED;
                }
            } else { //update

                customerDiscountRangeModel = this.update(customerDiscountRangeModel);
                if (customerDiscountRangeModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CUSTOMER_DISCOUNT_RANGE_UPDATE_FAILED;
                }
            }

        } catch (Exception ex) {
            log.error("CustomerDiscountRangeBllManager -> saveOrUpdate got exception :"+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return customerDiscountRangeModel;
    }

    public List<CustomerDiscountRangeModel> searchCustomerDiscountRange(CustomerDiscountRangeModel customerDiscountRangeModelReq) throws Exception {
        CustomerDiscountRangeModel customerDiscountRangeModel = new CustomerDiscountRangeModel();
        List<CustomerDiscountRangeModel> lstCustomerDiscountRangeModel = new ArrayList<>();
        try {
            customerDiscountRangeModel = customerDiscountRangeModelReq;
            lstCustomerDiscountRangeModel = this.getAllByConditions(customerDiscountRangeModel);
            if (lstCustomerDiscountRangeModel.size() == 0) {
                Core.clientMessage.get().message = MessageConstant.CUSTOMER_DISCOUNT_RANGE_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("CustomerDiscountRangeBllManager -> searchCustomerDiscountRange got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstCustomerDiscountRangeModel;
    }
    public CustomerDiscountRangeModel deleteCustomerDiscountRange(CustomerDiscountRangeModel customerDiscountRangeModelReq) throws Exception {
        CustomerDiscountRangeModel customerDiscountRangeModel = new CustomerDiscountRangeModel();
        try {
            customerDiscountRangeModel = customerDiscountRangeModelReq;
            customerDiscountRangeModel = this.softDelete(customerDiscountRangeModel);
            if (customerDiscountRangeModel == null) {
                Core.clientMessage.get().message = MessageConstant.CUSTOMER_DISCOUNT_RANGE_DELETE_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("CustomerDiscountRangeBllManager -> deleteCustomerDiscountRange got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return customerDiscountRangeModel;
    }
    
}
