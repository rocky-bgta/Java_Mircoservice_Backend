/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 12/03/2018
 * Time: 12:07
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.bll.manager.SupplierAdditionalCostBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.models.SupplierAdditionalCostModel;
import nybsys.tillboxweb.models.VMSupplierAdditionalCostModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SupplierAdditionalCostServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(SupplierAdditionalCostServiceManager.class);
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Autowired
    private SupplierAdditionalCostBllManager supplierAdditionalCostBllManager;

    public ResponseMessage saveSupplierAdditionalCost(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        VMSupplierAdditionalCostModel vmSupplierAdditionalCostModel;
        try {
            vmSupplierAdditionalCostModel = Core.getRequestObject(requestMessage, VMSupplierAdditionalCostModel.class);

           /* for (SupplierAdditionalCostModel supplierAdditionalCostModel : vmSupplierAdditionalCostModel.lstSupplierAdditionalCostModel)
            {
                Set<ConstraintViolation<SupplierAdditionalCostModel>> violations = this.validator.validate(supplierAdditionalCostModel);
                if (violations.size() > 0) {
                    responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                    return responseMessage;
                }
            }*/

            for (SupplierAdditionalCostModel supplierAdditionalCostModel : vmSupplierAdditionalCostModel.lstSupplierAdditionalCostModel) {
                this.supplierAdditionalCostBllManager.saveOrUpdate(supplierAdditionalCostModel);
            }


            if (Core.clientMessage.get().messageCode == null) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.ADDITIONAL_COST_SAVE_SUCCESSFULLY;
                this.commit();
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.ADDITIONAL_COST_SAVE_FAILED;
                } else {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("SupplierAdditionalCostServiceManager -> saveSupplierAdditionalCost got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }

    public ResponseMessage searchSupplierAdditionalCost(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<SupplierAdditionalCostModel> lstSupplierAdditionalCostModel = new ArrayList<>();
        SupplierAdditionalCostModel supplierAdditionalCostModel = new SupplierAdditionalCostModel();
        try {
            supplierAdditionalCostModel = Core.getRequestObject(requestMessage, SupplierAdditionalCostModel.class);

            lstSupplierAdditionalCostModel = this.supplierAdditionalCostBllManager.searchAdditionalCost(supplierAdditionalCostModel);

            responseMessage.responseObj = lstSupplierAdditionalCostModel;
            if (Core.clientMessage.get().messageCode == null) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.ADDITIONAL_COST_GET_SUCCESSFULLY;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.ADDITIONAL_COST_GET_FAILED;
            }

        } catch (Exception ex) {
            log.error("SupplierAdditionalCostServiceManager -> searchSupplierAdditionalCost got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }

    public ResponseMessage deleteSupplierAdditionalCost(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        SupplierAdditionalCostModel supplierAdditionalCostModel = new SupplierAdditionalCostModel();
        try {
            supplierAdditionalCostModel = Core.getRequestObject(requestMessage, SupplierAdditionalCostModel.class);

            supplierAdditionalCostModel = this.supplierAdditionalCostBllManager.deleteAdditionalCost(supplierAdditionalCostModel);

            responseMessage.responseObj = supplierAdditionalCostModel;
            if (Core.clientMessage.get().messageCode == null) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.ADDITIONAL_COST_DELETE_SUCCESSFULLY;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.ADDITIONAL_COST_DELETE_FAILED;
            }

        } catch (Exception ex) {
            log.error("SupplierAdditionalCostServiceManager -> deleteSupplierAdditionalCost got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }
}
