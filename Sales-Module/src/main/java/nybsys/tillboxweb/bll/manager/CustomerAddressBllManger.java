/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 12/03/2018
 * Time: 2:33
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
import nybsys.tillboxweb.entities.CustomerAddress;
import nybsys.tillboxweb.models.CustomerAddressModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CustomerAddressBllManger extends BaseBll<CustomerAddress> {
    private static final Logger log = LoggerFactory.getLogger(CustomerAddressBllManger.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(CustomerAddress.class);
        Core.runTimeModelType.set(CustomerAddressModel.class);
    }

    public CustomerAddressModel saveOrUpdate(CustomerAddressModel customerAddressModelReq) throws Exception {
        CustomerAddressModel customerAddressModel = new CustomerAddressModel();
        List<CustomerAddressModel> lstCustomerAddressModel = new ArrayList<>();
        try {
            customerAddressModel = customerAddressModelReq;
            //save
            if (customerAddressModel.getCustomerAddressID() == null || customerAddressModel.getCustomerAddressID() == 0)
            {
                customerAddressModel = this.save(customerAddressModel);
                if (customerAddressModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CUSTOMER_ADDRESS_SAVE_FAILED;
                }
            } else { //update

                customerAddressModel = this.update(customerAddressModel);
                if (customerAddressModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CUSTOMER_ADDRESS_UPDATE_FAILED;
                }
            }

        } catch (Exception ex) {
            log.error("CustomerAddressBllManager -> saveOrUpdate got exception :"+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return customerAddressModel;
    }

    public List<CustomerAddressModel> searchCustomerAddress(CustomerAddressModel customerAddressModelReq) throws Exception {
        CustomerAddressModel customerAddressModel = new CustomerAddressModel();
        List<CustomerAddressModel> lstCustomerAddressModel = new ArrayList<>();
        try {
            customerAddressModel = customerAddressModelReq;
            lstCustomerAddressModel = this.getAllByConditions(customerAddressModel);
            if (lstCustomerAddressModel.size() == 0) {
                Core.clientMessage.get().message = MessageConstant.CUSTOMER_ADDRESS_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("CustomerAddressBllManager -> searchCustomerAddress got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstCustomerAddressModel;
    }
    public Boolean deleteCustomerAddress(Integer customerID) throws Exception {
        CustomerAddressModel whereCondtions = new CustomerAddressModel();
        CustomerAddressModel modelToUpdate = new CustomerAddressModel();
        Integer numberOfRowDeleted = 0;
        try {
            whereCondtions.setCustomerID(customerID);
            whereCondtions.setStatus(TillBoxAppEnum.Status.Active.get());
            modelToUpdate.setStatus(TillBoxAppEnum.Status.Deleted.get());
            numberOfRowDeleted = this.updateByConditions(whereCondtions,modelToUpdate);
            if (numberOfRowDeleted == 0) {
                Core.clientMessage.get().message = MessageConstant.CUSTOMER_ADDRESS_DELETE_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                return false;
            }
        } catch (Exception ex) {
            log.error("CustomerAddressBllManager -> deleteCustomerAddress got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return false;
    }
}
