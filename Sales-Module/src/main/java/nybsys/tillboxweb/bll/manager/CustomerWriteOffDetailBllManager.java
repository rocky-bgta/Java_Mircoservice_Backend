/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 26/02/2018
 * Time: 05:18
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
import nybsys.tillboxweb.entities.CustomerWriteOffDetail;
import nybsys.tillboxweb.models.CustomerInvoiceModel;
import nybsys.tillboxweb.models.CustomerWriteOffDetailModel;
import nybsys.tillboxweb.models.CustomerWriteOffModel;
import nybsys.tillboxweb.sales_enum.PaymentStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CustomerWriteOffDetailBllManager extends BaseBll<CustomerWriteOffDetail> {
    private static final Logger log = LoggerFactory.getLogger(CustomerWriteOffDetailBllManager.class);

    @Autowired
    private CustomerInvoiceDetailBllManager customerInvoiceDetailBllManager;

    @Autowired
    private CustomerReceiveDetailBllManager customerReceiveDetailBllManager;

    @Autowired
    private CustomerReturnDetailBllManager customerReturnDetailBllManager;

    @Autowired
    private CustomerInvoiceBllManager customerInvoiceBllManager;

    private CustomerAdjustmentDetailBllManager customerAdjustmentDetailBllManager = new CustomerAdjustmentDetailBllManager();

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(CustomerWriteOffDetail.class);
        Core.runTimeModelType.set(CustomerWriteOffDetailModel.class);
    }

    public List<CustomerWriteOffDetailModel> saveOrUpdateList(List<CustomerWriteOffDetailModel> lstCustomerWriteOffDetailModelReq, Integer customerWriteOffID) throws Exception {
        List<CustomerWriteOffDetailModel> lstCustomerWriteOffDetailModel = new ArrayList<>();
        try {
            lstCustomerWriteOffDetailModel = lstCustomerWriteOffDetailModelReq;
            //check given adjusted amount is less then due amount
            for (CustomerWriteOffDetailModel customerWriteOffDetailModel : lstCustomerWriteOffDetailModel) {
                if (customerWriteOffDetailModel.getAmount().doubleValue() > getDueAmount(customerWriteOffDetailModel.getCustomerInvoiceID()).doubleValue()) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().userMessage = MessageConstant.CUSTOMER_WRITE_OFF_AMOUNT_IS_GREATER_THAN_DUE_AMOUNT;
                }
            }

            Core.clientMessage.get().messageCode = null;
            for (CustomerWriteOffDetailModel customerWriteOffDetailModel : lstCustomerWriteOffDetailModel) {
                customerWriteOffDetailModel.setCustomerWriteOffID(customerWriteOffID.intValue());
                customerWriteOffDetailModel.setCustomerWriteOffDetailID(null);

                //save
                customerWriteOffDetailModel = this.save(customerWriteOffDetailModel);
                if (customerWriteOffDetailModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CUSTOMER_WRITE_OFF_DETAIL_SAVE_FAILED;
                }

            }

        } catch (Exception ex) {
            log.error("CustomerWriteOffDetailBllManager -> saveOrUpdate got exception :" + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstCustomerWriteOffDetailModel;
    }

    public List<CustomerWriteOffDetailModel> searchCustomerWriteOffDetailByWriteOffID(int customerWriteOffID) throws Exception {
        CustomerWriteOffDetailModel customerWriteOffDetailModel = new CustomerWriteOffDetailModel();
        List<CustomerWriteOffDetailModel> lstCustomerWriteOffDetailModel = new ArrayList<>();
        try {
            customerWriteOffDetailModel.setCustomerWriteOffID(customerWriteOffID);
            customerWriteOffDetailModel.setStatus(TillBoxAppEnum.Status.Active.get());
            lstCustomerWriteOffDetailModel = this.getAllByConditions(customerWriteOffDetailModel);
            if (lstCustomerWriteOffDetailModel.size() == 0) {
                Core.clientMessage.get().message = MessageConstant.CUSTOMER_WRITE_OFF_DETAIL_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("CustomerWriteOffDetailBllManager -> searchCustomerByID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstCustomerWriteOffDetailModel;
    }

    public Double getPriceSumByInvoiceID(Integer invoiceID) throws Exception {
        List<CustomerWriteOffDetailModel> lstCustomerWriteOffDetailModel;
        CustomerWriteOffDetailModel whereCondition = new CustomerWriteOffDetailModel();
        CustomerWriteOffModel customerWriteOffModel = new CustomerWriteOffModel();
        Double priceSum = 0.0;
        try {
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());
            whereCondition.setCustomerInvoiceID(invoiceID);
            lstCustomerWriteOffDetailModel = this.getAllByConditions(whereCondition);
            for (CustomerWriteOffDetailModel customerWriteOffDetailModel : lstCustomerWriteOffDetailModel) {

                priceSum += customerWriteOffDetailModel.getAmount();
            }
        } catch (Exception ex) {
            log.error("CustomerWriteOffDetailBllManager -> getPriceSumByInvoiceID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return priceSum;
    }

    public Double getDueAmount(Integer invoiceID) throws Exception {
        Double totalDueAmount = 0.0;
        try {
            totalDueAmount -= this.getPriceSumByInvoiceID(invoiceID);
            totalDueAmount += this.customerInvoiceDetailBllManager.getPriceSumByInvoiceID(invoiceID);
            totalDueAmount -= this.customerReceiveDetailBllManager.getPriceSumByInvoiceID(invoiceID);
            totalDueAmount -= this.customerReturnDetailBllManager.getPriceSumByInvoiceID(invoiceID);
            totalDueAmount -= this.customerAdjustmentDetailBllManager.getPriceSumByInvoiceID(invoiceID);
        } catch (Exception ex) {
            log.error("CustomerWriteOffDetailBllManager -> getDueAmount got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return totalDueAmount;
    }

    public Integer deleteCustomerAdjustDetailByCustomerWriteOffID(Integer customerWriteOffID) throws Exception {
        CustomerWriteOffDetailModel whereCondition = new CustomerWriteOffDetailModel();
        List<CustomerWriteOffDetailModel> lstCustomerWriteOffDetailModel;
        CustomerInvoiceModel customerInvoiceModel;
        Integer deleteCounter = 0;
        try {
            whereCondition.setCustomerWriteOffID(customerWriteOffID);
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());

            lstCustomerWriteOffDetailModel = this.getAllByConditions(whereCondition);
            if (lstCustomerWriteOffDetailModel.size() > 0) {
                for (CustomerWriteOffDetailModel customerWriteOffDetailModel : lstCustomerWriteOffDetailModel) {
                    //change paid status in invoice
                    customerInvoiceModel = this.customerInvoiceBllManager.getByInvoiceID(customerWriteOffDetailModel.getCustomerInvoiceID());
                    if (customerInvoiceModel != null && customerInvoiceModel.getPaymentStatus().intValue() == PaymentStatus.Paid.get()) {
                        customerInvoiceModel.setPaymentStatus(PaymentStatus.Due.get());
                        customerInvoiceModel = this.customerInvoiceBllManager.update(customerInvoiceModel);
                        if (customerInvoiceModel == null) {
                            Core.clientMessage.get().message = MessageConstant.CUSTOMER_INVOICE_UPDATE_FAILED;
                            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                            return deleteCounter;
                        }
                    }
                    //delete customer adjustment detail;
                    customerWriteOffDetailModel = this.softDelete(customerWriteOffDetailModel);
                    if (customerWriteOffDetailModel == null) {
                        Core.clientMessage.get().message = MessageConstant.CUSTOMER_WRITE_OFF_DETAIL_DELETE_FAILED;
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        return deleteCounter;
                    }
                    deleteCounter++;
                }
            }

        } catch (Exception ex) {
            log.error("CustomerWriteOffDetailBllManager -> deleteCustomerAdjustDetailByCustomerWriteOffID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return deleteCounter;
    }
}
