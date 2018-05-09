/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/02/2018
 * Time: 11:30
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.bll.manager.UnitOfMeasureBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.models.UnitOfMeasureModel;
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
public class UnitOfMeasureServiceManager extends BaseService {
    
    private static final Logger log = LoggerFactory.getLogger(UnitOfMeasureServiceManager.class);
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Autowired
    private UnitOfMeasureBllManager unitOfMeasureBllManager;

    public ResponseMessage saveUnitOfMeasure(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        UnitOfMeasureModel unitOfMeasureModel = new UnitOfMeasureModel();
        try {
            unitOfMeasureModel = Core.getRequestObject(requestMessage, UnitOfMeasureModel.class);

            /*Set<ConstraintViolation<UnitOfMeasureModel>> violations = this.validator.validate(unitOfMeasureModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            unitOfMeasureModel = this.unitOfMeasureBllManager.saveOrUpdateWithBusinessLogic(unitOfMeasureModel);

            responseMessage.responseObj = unitOfMeasureModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_SAVE_UNIT_OF_MEASURE;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.FAILED_TO_SAVE_UNIT_OF_MEASURE;
                }else
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("UnitOfMeasureServiceManager -> saveUnitOfMeasure got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }
        return responseMessage;
    }

    public ResponseMessage searchUnitOfMeasure(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<UnitOfMeasureModel> lstUnitOfMeasureModel = new ArrayList<>();
        UnitOfMeasureModel unitOfMeasureModel = new UnitOfMeasureModel();
        try {
            unitOfMeasureModel = Core.getRequestObject(requestMessage, UnitOfMeasureModel.class);

            lstUnitOfMeasureModel = this.unitOfMeasureBllManager.searchUnitOfMeasure(unitOfMeasureModel);

            responseMessage.responseObj = lstUnitOfMeasureModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_GET_UNIT_OF_MEASURE;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.FAILED_TO_GET_UNIT_OF_MEASURE;
            }

        } catch (Exception ex) {
            log.error("UnitOfMeasureServiceManager -> searchUnitOfMeasure got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }
        return responseMessage;
    }

    public ResponseMessage inactiveUnitOfMeasure(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        UnitOfMeasureModel unitOfMeasureModel = new UnitOfMeasureModel();
        try {
            unitOfMeasureModel = Core.getRequestObject(requestMessage, UnitOfMeasureModel.class);
            
            /*Set<ConstraintViolation<UnitOfMeasureModel>> violations = this.validator.validate(unitOfMeasureModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            unitOfMeasureModel = this.unitOfMeasureBllManager.inactiveUnitOfMeasure(unitOfMeasureModel);

            responseMessage.responseObj = unitOfMeasureModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_INACTIVE_UNIT_OF_MEASURE;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.FAILED_TO_INACTIVE_UNIT_OF_MEASURE;
                }else
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("UnitOfMeasureServiceManager -> inactiveUnitOfMeasure got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }
        return responseMessage;
    }
}
