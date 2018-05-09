/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 14/02/2018
 * Time: 10:46
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.bll.manager.UOMMethodBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.models.UOMMethodModel;
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
public class UOMMethodServiceManager extends BaseService{
    private static final Logger log = LoggerFactory.getLogger(UOMMethodServiceManager.class);
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Autowired
    private UOMMethodBllManager uOMMethodModelBllManager;

    public ResponseMessage saveUOMMethod(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        UOMMethodModel uOMMethodModelModel = new UOMMethodModel();
        try {
            uOMMethodModelModel = Core.getRequestObject(requestMessage, UOMMethodModel.class);

            /*Set<ConstraintViolation<UOMMethodModel>> violations = this.validator.validate(uOMMethodModelModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            uOMMethodModelModel = this.uOMMethodModelBllManager.saveOrUpdateUOMMethodWithBusinessLogic(uOMMethodModelModel);

            responseMessage.responseObj = uOMMethodModelModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_SAVE_UOM_METHOD;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.FAILED_TO_SAVE_UOM_METHOD;
                }else
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("UOMMethodServiceManager -> saveUOMMethod got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }
        return responseMessage;
    }

    public ResponseMessage searchUOMMethod(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<UOMMethodModel> lstUOMMethodModel = new ArrayList<>();
        UOMMethodModel uOMMethodModelModel = new UOMMethodModel();
        try {
            uOMMethodModelModel = Core.getRequestObject(requestMessage, UOMMethodModel.class);

            lstUOMMethodModel = this.uOMMethodModelBllManager.searchUOMMethod(uOMMethodModelModel);

            responseMessage.responseObj = lstUOMMethodModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_GET_UOM_METHOD;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.FAILED_TO_GET_UOM_METHOD;
            }

        } catch (Exception ex) {
            log.error("UOMMethodServiceManager -> searchUOMMethod got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }
        return responseMessage;
    }

    public ResponseMessage inactiveUOMMethod(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        UOMMethodModel uOMMethodModelModel = new UOMMethodModel();
        try {
            uOMMethodModelModel = Core.getRequestObject(requestMessage, UOMMethodModel.class);

            /*Set<ConstraintViolation<UOMMethodModel>> violations = this.validator.validate(uOMMethodModelModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            uOMMethodModelModel = this.uOMMethodModelBllManager.inactiveUOMMethod(uOMMethodModelModel);

            responseMessage.responseObj = uOMMethodModelModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_INACTIVE_UOM_METHOD;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.FAILED_TO_INACTIVE_UOM_METHOD;
                }else
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("UOMMethodServiceManager -> inactiveUOMMethod got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }
        return responseMessage;
    }
}
