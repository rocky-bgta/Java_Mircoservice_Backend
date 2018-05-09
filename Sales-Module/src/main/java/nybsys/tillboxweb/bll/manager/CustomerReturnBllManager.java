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
import nybsys.tillboxweb.entities.CustomerReturn;
import nybsys.tillboxweb.models.CustomerReturnDetailModel;
import nybsys.tillboxweb.models.CustomerReturnModel;
import nybsys.tillboxweb.models.VMCustomerReturn;
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
public class CustomerReturnBllManager extends BaseBll<CustomerReturn> {
    private static final Logger log = LoggerFactory.getLogger(CustomerReturnBllManager.class);

    @Autowired
    private CustomerReturnDetailBllManager customerReturnDetailBllManager;

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(CustomerReturn.class);
        Core.runTimeModelType.set(CustomerReturnModel.class);
    }

    public VMCustomerReturn saveCustomerReturn(VMCustomerReturn vmCustomerReturn) throws Exception {

        try {
            if (isValidCustomerReturn(vmCustomerReturn)) {
                if (vmCustomerReturn.customerReturnModel.getCustomerReturnID() != null && vmCustomerReturn.customerReturnModel.getCustomerReturnID() > 0) {

                    vmCustomerReturn.customerReturnModel = this.update(vmCustomerReturn.customerReturnModel);

              /* detail save*/

                    CustomerReturnDetailModel searchCustomerReturnDetailModel = new CustomerReturnDetailModel();
                    searchCustomerReturnDetailModel.setCustomerReturnID(vmCustomerReturn.customerReturnModel.getCustomerReturnID());
                    searchCustomerReturnDetailModel.setStatus(TillBoxAppEnum.Status.Active.get());
                    List<CustomerReturnDetailModel> lstExistingCustomerReturnDetailModel = new ArrayList<>();
                    lstExistingCustomerReturnDetailModel = this.customerReturnDetailBllManager.getAllByConditions(searchCustomerReturnDetailModel);


                    for (CustomerReturnDetailModel customerReturnDetailModel : lstExistingCustomerReturnDetailModel) {
                        customerReturnDetailModel.setStatus(TillBoxAppEnum.Status.Deleted.get());
                        customerReturnDetailModel = this.customerReturnDetailBllManager.update(customerReturnDetailModel);
                    }


                    this.customerReturnDetailBllManager.saveCustomerReturnDetail(vmCustomerReturn);
                } else {
                    vmCustomerReturn.customerReturnModel = this.save(vmCustomerReturn.customerReturnModel);

               /* detail save*/
                    this.customerReturnDetailBllManager.saveCustomerReturnDetail(vmCustomerReturn);
                }
            }

        } catch (Exception ex) {
            log.error("CustomerReturnBllManager -> CustomerReturnBllManager got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return vmCustomerReturn;
    }

    public CustomerReturnModel deleteCustomerReturn(CustomerReturnModel customerReturnModel) throws Exception {

        try {
            if (isValidCustomerReturn(customerReturnModel)) {
                if (customerReturnModel.getCustomerReturnID() > 0) {

                    customerReturnModel.setUpdatedDate(new Date());
                    customerReturnModel.setStatus(TillBoxAppEnum.Status.Deleted.get());
                    customerReturnModel = this.update(customerReturnModel);

                    CustomerReturnDetailModel searchCustomerReturnModel = new CustomerReturnDetailModel();
                    searchCustomerReturnModel.setCustomerReturnID(customerReturnModel.getCustomerReturnID());
                    List<CustomerReturnDetailModel> lstCustomerReturnDetailModel = new ArrayList<>();
                    lstCustomerReturnDetailModel = this.customerReturnDetailBllManager.getAllByConditions(searchCustomerReturnModel);
                    for (CustomerReturnDetailModel customerReturnDetailModel : lstCustomerReturnDetailModel) {
                        customerReturnDetailModel.setStatus(TillBoxAppEnum.Status.Deleted.get());
                        this.customerReturnDetailBllManager.update(customerReturnDetailModel);
                    }
                }
            }
        } catch (Exception ex) {
            log.error("CustomerReturnBllManager -> CustomerReturnBllManager got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return customerReturnModel;
    }


    public List<VMCustomerReturn> searchVMCustomerReturn(CustomerReturnModel customerReturnModel) throws Exception {

        List<VMCustomerReturn> lstVMCustomerReturn = new ArrayList<>();
        try {
            List<CustomerReturnModel> lstCustomerReturnModel = new ArrayList<>();

            if (customerReturnModel.getStatus() == null || customerReturnModel.getStatus() == 0) {
                customerReturnModel.setStatus(TillBoxAppEnum.Status.Active.get());
            }

            lstCustomerReturnModel = this.getAllByConditions(customerReturnModel);
            if (lstCustomerReturnModel.size() > 0) {
                for (CustomerReturnModel customerInvoiceModel1 : lstCustomerReturnModel) {
                    VMCustomerReturn vmCustomerReturn = new VMCustomerReturn();
                    vmCustomerReturn.customerReturnModel = customerInvoiceModel1;
                    CustomerReturnDetailModel searchCustomerReturnModel = new CustomerReturnDetailModel();
                    searchCustomerReturnModel.setCustomerReturnID(customerInvoiceModel1.getCustomerReturnID());
                    searchCustomerReturnModel.setStatus(TillBoxAppEnum.Status.Active.get());
                    vmCustomerReturn.lstCustomerReturnDetailModel = this.customerReturnDetailBllManager.getAllByConditions(searchCustomerReturnModel);
                    lstVMCustomerReturn.add(vmCustomerReturn);
                }
            }

        } catch (Exception ex) {
            log.error("CustomerBllManager -> searchCustomer got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstVMCustomerReturn;
    }


    private Boolean isValidCustomerReturn(CustomerReturnModel customerReturnModel) throws Exception {


        if (customerReturnModel.getBusinessID() == null || customerReturnModel.getBusinessID() == 0) {
            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            Core.clientMessage.get().userMessage = MessageConstant.DUPLICATE_SUPPLIER_RETURN_NUMBER;
            return false;
        }
        if (customerReturnModel.getCustomerReturnID() == null || customerReturnModel.getCustomerReturnID() == 0) {
            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            Core.clientMessage.get().userMessage = MessageConstant.DUPLICATE_SUPPLIER_RETURN_NUMBER;
            return false;
        }
        CustomerReturnModel existingCustomerReturnModel = this.getById(customerReturnModel.getCustomerReturnID());

        if (existingCustomerReturnModel == null) {
            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            Core.clientMessage.get().userMessage = MessageConstant.DUPLICATE_SUPPLIER_RETURN_NUMBER;
        }


        return true;
    }


    private Boolean isValidCustomerReturn(VMCustomerReturn vmCustomerReturn) throws Exception {
        CustomerReturnModel existingCustomerReturnModel = new CustomerReturnModel();
        existingCustomerReturnModel.setCustomerReturnNo(vmCustomerReturn.customerReturnModel.getCustomerReturnNo());
        List<CustomerReturnModel> lstCustomerReturnModel = new ArrayList<>();


        lstCustomerReturnModel = this.getAllByConditions(existingCustomerReturnModel);


        if (lstCustomerReturnModel.size() > 0) {
            existingCustomerReturnModel = lstCustomerReturnModel.get(0);
            if ((existingCustomerReturnModel.getCustomerReturnID() != null && existingCustomerReturnModel.getCustomerReturnID() > 0) && existingCustomerReturnModel.getCustomerReturnID().intValue() != vmCustomerReturn.customerReturnModel.getCustomerReturnID().intValue()) {

                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().userMessage = MessageConstant.DUPLICATE_SUPPLIER_RETURN_NUMBER;
                return false;

            }
        }


        return true;
    }


}
