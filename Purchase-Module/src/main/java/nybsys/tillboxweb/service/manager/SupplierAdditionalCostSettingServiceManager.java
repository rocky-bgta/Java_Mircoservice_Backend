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
import nybsys.tillboxweb.bll.manager.SupplierAdditionalCostSettingBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.models.SupplierAdditionalCostSettingModel;
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
public class SupplierAdditionalCostSettingServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(SupplierAdditionalCostServiceManager.class);
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Autowired
    private SupplierAdditionalCostSettingBllManager supplierAdditionalCostSettingBllManager;

    public ResponseMessage saveSupplierAdditionalCostSetting(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        SupplierAdditionalCostSettingModel supplierAdditionalCostSettingModel = new SupplierAdditionalCostSettingModel();
        try {
            supplierAdditionalCostSettingModel = Core.getRequestObject(requestMessage, SupplierAdditionalCostSettingModel.class);

            /*Set<ConstraintViolation<SupplierAdditionalCostSettingModel>> violations = this.validator.validate(supplierAdditionalCostSettingModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            supplierAdditionalCostSettingModel = this.supplierAdditionalCostSettingBllManager.saveOrUpdate(supplierAdditionalCostSettingModel);

            responseMessage.responseObj = supplierAdditionalCostSettingModel;
            if (Core.clientMessage.get().messageCode == null) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.ADDITIONAL_COST_SETTING_SAVE_SUCCESSFULLY;
                this.commit();
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.ADDITIONAL_COST_SETTING_SAVE_FAILED;
                } else {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("SupplierAdditionalCostSettingServiceManager -> saveSupplierAdditionalCost got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }

    public ResponseMessage searchSupplierAdditionalCostSetting(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<SupplierAdditionalCostSettingModel> lstSupplierAdditionalCostSettingModel = new ArrayList<>();
        SupplierAdditionalCostSettingModel supplierAdditionalCostSettingModel = new SupplierAdditionalCostSettingModel();
        try {
            supplierAdditionalCostSettingModel = Core.getRequestObject(requestMessage, SupplierAdditionalCostSettingModel.class);

            lstSupplierAdditionalCostSettingModel = this.supplierAdditionalCostSettingBllManager.searchAdditionalCostSetting(supplierAdditionalCostSettingModel);

            responseMessage.responseObj = lstSupplierAdditionalCostSettingModel;
            if (Core.clientMessage.get().messageCode == null) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.ADDITIONAL_COST_SETTING_GET_SUCCESSFULLY;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.ADDITIONAL_COST_SETTING_GET_FAILED;
            }

        } catch (Exception ex) {
            log.error("SupplierAdditionalCostSettingServiceManager -> searchSupplierAdditionalCost got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }

    public ResponseMessage deleteSupplierAdditionalCostSetting(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        SupplierAdditionalCostSettingModel supplierAdditionalCostSettingModel = new SupplierAdditionalCostSettingModel();
        try {
            supplierAdditionalCostSettingModel = Core.getRequestObject(requestMessage, SupplierAdditionalCostSettingModel.class);

            supplierAdditionalCostSettingModel = this.supplierAdditionalCostSettingBllManager.deleteAdditionalCostSetting(supplierAdditionalCostSettingModel);

            responseMessage.responseObj = supplierAdditionalCostSettingModel;
            if (Core.clientMessage.get().messageCode == null) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.ADDITIONAL_COST_SETTING_DELETE_SUCCESSFULLY;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.ADDITIONAL_COST_SETTING_DELETE_FAILED;
            }

        } catch (Exception ex) {
            log.error("SupplierAdditionalCostSettingServiceManager -> deleteSupplierAdditionalCost got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }
}
