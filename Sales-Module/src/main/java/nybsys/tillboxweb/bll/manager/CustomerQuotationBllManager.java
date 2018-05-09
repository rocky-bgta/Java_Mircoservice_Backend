/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 3/19/2018
 * Time: 11:109 AM
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
import nybsys.tillboxweb.entities.CustomerQuotation;
import nybsys.tillboxweb.models.CustomerInvoiceModel;
import nybsys.tillboxweb.models.CustomerQuotationDetailModel;
import nybsys.tillboxweb.models.CustomerQuotationModel;
import nybsys.tillboxweb.models.VMCustomerQuotationModel;
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
public class CustomerQuotationBllManager extends BaseBll<CustomerQuotation> {

    private static final Logger log = LoggerFactory.getLogger(CustomerQuotationBllManager.class);

    @Autowired
    private CustomerQuotationDetailBllManager customerQuotationDetailBllManager;
    
    @Autowired
    private CustomerInvoiceBllManager customerInvoiceBllManager;

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(CustomerQuotation.class);
        Core.runTimeModelType.set(CustomerQuotationModel.class);
    }


    public VMCustomerQuotationModel saveCustomerQuotation(VMCustomerQuotationModel vmCustomerQuotation) throws Exception {

        try {
            if (isValidCustomerQuotation(vmCustomerQuotation)) {

                if (vmCustomerQuotation.customerQuotationModel.getCustomerQuotationID() > 0) {
                    vmCustomerQuotation.customerQuotationModel.setUpdatedDate(new Date());
                    vmCustomerQuotation.customerQuotationModel = this.update(vmCustomerQuotation.customerQuotationModel);

                    CustomerQuotationDetailModel searchOrderDetailModel = new CustomerQuotationDetailModel();
                    searchOrderDetailModel.setCustomerQuotationID(vmCustomerQuotation.customerQuotationModel.getCustomerQuotationID());
                    searchOrderDetailModel.setStatus(TillBoxAppEnum.Status.Active.get());
                    List<CustomerQuotationDetailModel> lstExistingCustomerQuotationDetailModel = new ArrayList<>();
                    lstExistingCustomerQuotationDetailModel = this.customerQuotationDetailBllManager.getAllByConditions(searchOrderDetailModel);
                    for (CustomerQuotationDetailModel customerQuotationDetailModel : lstExistingCustomerQuotationDetailModel) {
                        customerQuotationDetailModel = this.customerQuotationDetailBllManager.softDelete(customerQuotationDetailModel);
                    }
                    this.customerQuotationDetailBllManager.saveCustomerQuotationDetail(vmCustomerQuotation);

                } else {
                    vmCustomerQuotation.customerQuotationModel = this.save(vmCustomerQuotation.customerQuotationModel);
               
                    /* detail save*/
                    this.customerQuotationDetailBllManager.saveCustomerQuotationDetail(vmCustomerQuotation);
                }
                if (Core.clientMessage.get().messageCode == null) {
                    Core.clientMessage.get().message = MessageConstant.CUSTOMER_QUOTATION_SAVE_SUCCESSFULLY;
                } else {

                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CUSTOMER_QUOTATION_SAVE_FAILED;
                }
            }


        } catch (Exception ex) {
            log.error("CustomerQuotationBllManager -> saveCustomerQuotation got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return vmCustomerQuotation;
    }


    private Boolean isValidCustomerQuotation(VMCustomerQuotationModel vmCustomerQuotation) throws Exception {
        CustomerQuotationModel existingProductModel = new CustomerQuotationModel();
        existingProductModel.setCustomerQuotationNo(vmCustomerQuotation.customerQuotationModel.getCustomerQuotationNo());

        List<CustomerQuotationModel> lstCustomerQuotationModel = new ArrayList<>();
        lstCustomerQuotationModel = this.getAllByConditions(existingProductModel);

        if (lstCustomerQuotationModel.size() > 0) {
            existingProductModel = lstCustomerQuotationModel.get(0);
        }


        if ((existingProductModel.getCustomerQuotationID() != null && existingProductModel.getCustomerQuotationID() > 0) && existingProductModel.getCustomerQuotationID().intValue() != vmCustomerQuotation.customerQuotationModel.getCustomerQuotationID().intValue()) {

            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            Core.clientMessage.get().userMessage = MessageConstant.DUPLICATE_QUOTATION_NUMBER;
            return false;
        }


        return true;
    }

    public List<VMCustomerQuotationModel> searchVMCustomerQuotationModel(CustomerQuotationModel customerQuotationModel) throws Exception {

        List<VMCustomerQuotationModel> lstVMCustomerQuotationModel = new ArrayList<>();
        try {
            List<CustomerQuotationModel> lstCustomerQuotationModel = new ArrayList<>();

            if (customerQuotationModel.getStatus() == null || customerQuotationModel.getStatus() == 0) {
                customerQuotationModel.setStatus(TillBoxAppEnum.Status.Active.get());
            }

            lstCustomerQuotationModel = this.getAllByConditions(customerQuotationModel);
            if (lstCustomerQuotationModel.size() > 0) {
                for (CustomerQuotationModel customerQuotationModel1 : lstCustomerQuotationModel) {
                    VMCustomerQuotationModel vmCustomerQuotation = new VMCustomerQuotationModel();
                    vmCustomerQuotation.customerQuotationModel = customerQuotationModel1;
                    CustomerQuotationDetailModel searchCustomerQuotationModel = new CustomerQuotationDetailModel();
                    searchCustomerQuotationModel.setCustomerQuotationID(customerQuotationModel1.getCustomerQuotationID());
                    searchCustomerQuotationModel.setStatus(TillBoxAppEnum.Status.Active.get());
                    vmCustomerQuotation.lstCustomerQuotationDetailModel = this.getAllByConditions(searchCustomerQuotationModel);
                    lstVMCustomerQuotationModel.add(vmCustomerQuotation);
                }
            }

        } catch (Exception ex) {
            log.error("CustomerQuotationBllManager -> searchVMCustomerQuotationModel got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstVMCustomerQuotationModel;
    }


    public CustomerQuotationModel deleteCustomerQuotation(CustomerQuotationModel customerQuotationModel) {
        try {
            if (isValidDeleteObject(customerQuotationModel)) {

            CustomerQuotationDetailModel customerQuotationDetailModel = new CustomerQuotationDetailModel();
            customerQuotationDetailModel.setCustomerQuotationID(customerQuotationModel.getCustomerQuotationID());
            List<CustomerQuotationDetailModel> lstCustomerQuotationDetailModel = new ArrayList<>();
            lstCustomerQuotationDetailModel = this.customerQuotationDetailBllManager.getAllByConditions(customerQuotationDetailModel);
            
            this.softDelete(customerQuotationModel);
            
            for (CustomerQuotationDetailModel customerQuotationDetailModel1 : lstCustomerQuotationDetailModel) {
                this.customerQuotationDetailBllManager.softDelete(customerQuotationDetailModel1);
            }
             }
        } catch (Exception ex) {
            customerQuotationModel = null;
        }

        return customerQuotationModel;
    }


    private boolean isValidDeleteObject(CustomerQuotationModel customerQuotationModel) throws Exception {

        if (customerQuotationModel.getCustomerQuotationID() == null || customerQuotationModel.getCustomerQuotationID() == 0) {
            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            Core.clientMessage.get().userMessage = MessageConstant.CUSTOMER_QUOTATION_ID_CANNOT_BE_BLANK;
            return false;
        }


        CustomerInvoiceModel searchCustomerInvoiceModel = new CustomerInvoiceModel();
        searchCustomerInvoiceModel.setSalesQuotationID(customerQuotationModel.getCustomerQuotationID());

        List<CustomerInvoiceModel> lstCustomerInvoiceModel = new ArrayList<>();
        lstCustomerInvoiceModel = this.customerInvoiceBllManager.getAllByConditions(searchCustomerInvoiceModel);
        if (lstCustomerInvoiceModel.size() > 0) {
            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            Core.clientMessage.get().userMessage = MessageConstant.CUSTOMER_QUOTATION_CANNOT_BE_DELETE_IT_IS_ALREADY_INVOICED;
            return false;
        }


        return true;
    }


}
