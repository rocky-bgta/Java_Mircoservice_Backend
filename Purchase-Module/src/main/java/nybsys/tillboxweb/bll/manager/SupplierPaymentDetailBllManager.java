/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 28-Feb-18
 * Time: 10:28 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.SupplierPaymentDetail;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.models.SupplierPaymentDetailModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SupplierPaymentDetailBllManager extends BaseBll<SupplierPaymentDetail> {

    private static final Logger log = LoggerFactory.getLogger(SupplierPaymentDetailBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(SupplierPaymentDetail.class);
        Core.runTimeModelType.set(SupplierPaymentDetailModel.class);
    }



    public <M> M saveOrUpdate(RequestMessage requestMessage) throws Exception {
        SupplierPaymentDetailModel castRequestModel =
                Core.getRequestObject(requestMessage, SupplierPaymentDetailModel.class);
        Integer primaryKeyValue = castRequestModel.getSupplierPaymentDetailID();
        SupplierPaymentDetailModel processedModel = null;
        try {

            if (primaryKeyValue == null || primaryKeyValue == 0) {
                // Save Code
                processedModel = this.save(castRequestModel);
                if (processedModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "SupplierPaymentDetail Save Successfully";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to Save SupplierPaymentDetail";
                }
            } else {
                // Update Code
                processedModel = this.update(castRequestModel);
                if (processedModel != null) {
                    Core.clientMessage.get().userMessage = "SupplierPaymentDetail Update Successfully";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to Update SupplierPaymentDetail";
                }
            }

        } catch (Exception ex) {
            log.error("SupplierPaymentDetailBllManager -> saveOrUpdate got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return (M) processedModel;
    }


    public <M> List<M> search(RequestMessage requestMessage) throws Exception {
        SupplierPaymentDetailModel castRequestModel =
                Core.getRequestObject(requestMessage, SupplierPaymentDetailModel.class);

        List<SupplierPaymentDetailModel> resultingList;
        try {
            resultingList = this.getAllByConditions(castRequestModel);
            if (resultingList.size() > 0) {
                Core.clientMessage.get().userMessage = "Find the request SupplierPaymentDetail";
            } else {
                Core.clientMessage.get().message = "Failed to find the requested SupplierPaymentDetail";
            }
        } catch (Exception ex) {
            log.error("SupplierPaymentDetailBllManager -> search got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return (List<M>) resultingList;
    }





    public <M> Integer deleteByConditions(RequestMessage requestMessage) throws Exception {
        SupplierPaymentDetailModel castRequestModel =
                Core.getRequestObject(requestMessage, SupplierPaymentDetailModel.class);
        Integer numberOfDeleteRow = 0;
        try {
            numberOfDeleteRow = this.deleteByConditions(castRequestModel);
            if (numberOfDeleteRow > 0) {
                Core.clientMessage.get().userMessage = "Successfully deleted the requested SupplierPaymentDetail";
            } else {
                Core.clientMessage.get().message = "Failed to deleted the requested SupplierPaymentDetail";
            }
        } catch (Exception ex) {
            log.error("SupplierPaymentDetailBllManager -> deleteByConditions got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return numberOfDeleteRow;
    }


    public <M> M inActive(RequestMessage requestMessage) throws Exception {
        SupplierPaymentDetailModel castRequestModel =
                Core.getRequestObject(requestMessage, SupplierPaymentDetailModel.class);
        SupplierPaymentDetailModel processedModel = null;
        try {
            if (castRequestModel != null) {
                processedModel = this.inActive(castRequestModel);
                if (processedModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "Successfully inactive the requested SupplierPaymentDetail";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to inactive the requested SupplierPaymentDetail";
                }
            }

        } catch (Exception ex) {
            log.error("SupplierPaymentDetailBllManager -> inActive got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return (M) processedModel;
    }


    public <M> M delete(RequestMessage requestMessage) throws Exception {
        SupplierPaymentDetailModel castRequestModel =
                Core.getRequestObject(requestMessage, SupplierPaymentDetailModel.class);
        SupplierPaymentDetailModel processedModel = null;
        try {
            if (castRequestModel != null) {
                processedModel = this.softDelete(castRequestModel);
                if (processedModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "Successfully deleted the requested SupplierPaymentDetail";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to deleted the requested SupplierPaymentDetail";
                }
            }

        } catch (Exception ex) {
            log.error("SupplierPaymentDetailBllManager -> delete got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return (M) processedModel;
    }


    public Double getPriceSumByInvoiceID(Integer invoiceID) throws Exception {
        List<SupplierPaymentDetailModel> lstSupplierPaymentDetailModel = new ArrayList<>();
        SupplierPaymentDetailModel whereCondition = new SupplierPaymentDetailModel();
        Double priceSum = 0.0;
        Double tempRowSum = 0.0;
        try {
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());
            whereCondition.setSupplierInvoiceID(invoiceID);
            lstSupplierPaymentDetailModel = this.getAllByConditions(whereCondition);
            for (SupplierPaymentDetailModel supplierPaymentDetailModel : lstSupplierPaymentDetailModel)
            {
                tempRowSum = supplierPaymentDetailModel.getPaidAmount()+supplierPaymentDetailModel.getDiscount();
                priceSum += tempRowSum;
            }
        } catch (Exception ex) {
            log.error("SupplierPaymentDetailBllManager -> getPriceSumByInvoiceID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return priceSum;
    }
}