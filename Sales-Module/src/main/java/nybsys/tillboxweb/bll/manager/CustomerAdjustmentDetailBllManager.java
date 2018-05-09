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
import nybsys.tillboxweb.coreEnum.Adjustment;
import nybsys.tillboxweb.entities.CustomerAdjustmentDetail;
import nybsys.tillboxweb.models.CustomerAdjustmentDetailModel;
import nybsys.tillboxweb.models.CustomerAdjustmentModel;
import nybsys.tillboxweb.models.CustomerInvoiceModel;
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
public class CustomerAdjustmentDetailBllManager extends BaseBll<CustomerAdjustmentDetail> {
    private static final Logger log = LoggerFactory.getLogger(CustomerAdjustmentDetailBllManager.class);

    @Autowired
    private CustomerInvoiceDetailBllManager customerInvoiceDetailBllManager;

    @Autowired
    private CustomerReceiveDetailBllManager customerReceiveDetailBllManager;

    @Autowired
    private CustomerReturnDetailBllManager customerReturnDetailBllManager;

    @Autowired
    private CustomerInvoiceBllManager customerInvoiceBllManager;

    @Autowired
    private CustomerWriteOffDetailBllManager customerWriteOffDetailBllManager;


    private CustomerAdjustmentBllManager customerAdjustmentBllManager = new CustomerAdjustmentBllManager();

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(CustomerAdjustmentDetail.class);
        Core.runTimeModelType.set(CustomerAdjustmentDetailModel.class);
    }

    public List<CustomerAdjustmentDetailModel> saveOrUpdateList(List<CustomerAdjustmentDetailModel> lstCustomerAdjustmentDetailModelReq, Integer customerAdjustmentID) throws Exception {
        List<CustomerAdjustmentDetailModel> lstCustomerAdjustmentDetailModel = new ArrayList<>();
        try {
            lstCustomerAdjustmentDetailModel = lstCustomerAdjustmentDetailModelReq;
            //check given adjusted amount is less then due amount
            for (CustomerAdjustmentDetailModel customerAdjustmentDetailModel : lstCustomerAdjustmentDetailModel) {
                if (customerAdjustmentDetailModel.getAdjustAmount().doubleValue() > getDueAmount(customerAdjustmentDetailModel.getCustomerInvoiceID()).doubleValue()) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().userMessage = MessageConstant.CUSTOMER_ADJUSTED_AMOUNT_IS_GREATER_THAN_DUE_AMOUNT;
                }
            }

            Core.clientMessage.get().messageCode = null;
            for (CustomerAdjustmentDetailModel customerAdjustmentDetailModel : lstCustomerAdjustmentDetailModel) {
                customerAdjustmentDetailModel.setCustomerAdjustmentID(customerAdjustmentID.intValue());
                customerAdjustmentDetailModel.setCustomerAdjustmentDetailID(null);

                //save
                customerAdjustmentDetailModel = this.save(customerAdjustmentDetailModel);
                if (customerAdjustmentDetailModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CUSTOMER_ADJUSTMENT_DETAIL_SAVE_FAILED;
                }

            }

        } catch (Exception ex) {
            log.error("CustomerAdjustmentDetailBllManager -> saveOrUpdate got exception :" + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstCustomerAdjustmentDetailModel;
    }

    public List<CustomerAdjustmentDetailModel> searchCustomerAdjustmentDetailByAdjustmentID(int customerAdjustmentID) throws Exception {
        CustomerAdjustmentDetailModel customerAdjustmentDetailModel = new CustomerAdjustmentDetailModel();
        List<CustomerAdjustmentDetailModel> lstCustomerAdjustmentDetailModel = new ArrayList<>();
        try {
            customerAdjustmentDetailModel.setCustomerAdjustmentID(customerAdjustmentID);
            customerAdjustmentDetailModel.setStatus(TillBoxAppEnum.Status.Active.get());
            lstCustomerAdjustmentDetailModel = this.getAllByConditions(customerAdjustmentDetailModel);
            if (lstCustomerAdjustmentDetailModel.size() == 0) {
                Core.clientMessage.get().message = MessageConstant.CUSTOMER_ADJUSTMENT_DETAIL_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("CustomerAdjustmentDetailBllManager -> searchCustomerByID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstCustomerAdjustmentDetailModel;
    }

    public Double getPriceSumByInvoiceID(Integer invoiceID) throws Exception {
        List<CustomerAdjustmentDetailModel> lstCustomerAdjustmentDetailModel;
        CustomerAdjustmentDetailModel whereCondition = new CustomerAdjustmentDetailModel();
        CustomerAdjustmentModel customerAdjustmentModel = new CustomerAdjustmentModel();
        Double priceSum = 0.0;
        try {
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());
            whereCondition.setCustomerInvoiceID(invoiceID);
            lstCustomerAdjustmentDetailModel = this.getAllByConditions(whereCondition);
            for (CustomerAdjustmentDetailModel customerAdjustmentDetailModel : lstCustomerAdjustmentDetailModel) {
                customerAdjustmentModel = this.customerAdjustmentBllManager.getById(customerAdjustmentDetailModel.getCustomerAdjustmentID(), TillBoxAppEnum.Status.Active.get());
                if (customerAdjustmentModel != null && customerAdjustmentModel.getEffectType() == Adjustment.Increase.get()) {
                    priceSum -= customerAdjustmentDetailModel.getAdjustAmount();
                } else {
                    priceSum += customerAdjustmentDetailModel.getAdjustAmount();
                }
            }
        } catch (Exception ex) {
            log.error("CustomerAdjustmentDetailBllManager -> getPriceSumByInvoiceID got exception : " + ex.getMessage());
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
            totalDueAmount -= this.customerWriteOffDetailBllManager.getPriceSumByInvoiceID(invoiceID);
        } catch (Exception ex) {
            log.error("CustomerAdjustmentDetailBllManager -> getDueAmount got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return totalDueAmount;
    }

    public Integer deleteCustomerAdjustDetailByCustomerAdjustmentID(Integer customerAdjustmentID) throws Exception {
        CustomerAdjustmentDetailModel whereCondition = new CustomerAdjustmentDetailModel();
        CustomerAdjustmentDetailModel modelUpdateCondition = new CustomerAdjustmentDetailModel();
        List<CustomerAdjustmentDetailModel> lstCustomerAdjustmentDetailModel;
        CustomerInvoiceModel customerInvoiceModel;
        Integer deleteCounter = 0;
        try {
            whereCondition.setCustomerAdjustmentID(customerAdjustmentID);
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());

            lstCustomerAdjustmentDetailModel = this.getAllByConditions(whereCondition);
            if (lstCustomerAdjustmentDetailModel.size() > 0) {
                for (CustomerAdjustmentDetailModel customerAdjustmentDetailModel : lstCustomerAdjustmentDetailModel) {
                    //change paid status in invoice
                    customerInvoiceModel = this.customerInvoiceBllManager.getByInvoiceID(customerAdjustmentDetailModel.getCustomerInvoiceID());
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
                    customerAdjustmentDetailModel = this.softDelete(customerAdjustmentDetailModel);
                    if (customerAdjustmentDetailModel == null) {
                        Core.clientMessage.get().message = MessageConstant.CUSTOMER_ADJUSTMENT_DETAIL_DELETE_FAILED;
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        return deleteCounter;
                    }
                    deleteCounter++;
                }
            }

        } catch (Exception ex) {
            log.error("CustomerAdjustmentDetailBllManager -> deleteCustomerAdjustDetailByCustomerAdjustmentID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return deleteCounter;
    }
}
