/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/02/2018
 * Time: 11:21
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.bll.manager.UnitMeasurementConversionBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.models.UnitMeasurementConversionModel;
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
public class UnitMeasurementConversionServiceManager extends BaseService{
    private static final Logger log = LoggerFactory.getLogger(UnitMeasurementConversionServiceManager.class);
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Autowired
    private UnitMeasurementConversionBllManager unitMeasurementConversionBllManager;

    public ResponseMessage saveUnitMeasurementConversion(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        UnitMeasurementConversionModel unitMeasurementConversionModel = new UnitMeasurementConversionModel();
        try {
            unitMeasurementConversionModel = Core.getRequestObject(requestMessage, UnitMeasurementConversionModel.class);

            /*Set<ConstraintViolation<UnitMeasurementConversionModel>> violations = this.validator.validate(unitMeasurementConversionModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            unitMeasurementConversionModel = this.unitMeasurementConversionBllManager.saveOrUpdateWithBusinessLogic(unitMeasurementConversionModel);

            responseMessage.responseObj = unitMeasurementConversionModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_SAVE_UNIT_OF_MEASURE_CONVERSION;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.FAILED_TO_SAVE_UNIT_OF_MEASURE_CONVERSION;
                }else
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("UnitMeasurementConversionServiceManager -> saveUnitMeasurementConversion got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }

    public ResponseMessage searchUnitMeasurementConversion(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<UnitMeasurementConversionModel> lstUnitMeasurementConversionModel = new ArrayList<>();
        UnitMeasurementConversionModel unitMeasurementConversionModel = new UnitMeasurementConversionModel();
        try {
            unitMeasurementConversionModel = Core.getRequestObject(requestMessage, UnitMeasurementConversionModel.class);

            lstUnitMeasurementConversionModel = this.unitMeasurementConversionBllManager.searchUnitMeasurementConversion(unitMeasurementConversionModel);

            responseMessage.responseObj = lstUnitMeasurementConversionModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_GET_UNIT_OF_MEASURE_CONVERSION;

            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.FAILED_TO_GET_UNIT_OF_MEASURE_CONVERSION;
            }

        } catch (Exception ex) {
            log.error("UnitMeasurementConversionServiceManager -> searchUnitMeasurementConversion got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }

    public ResponseMessage inactiveUnitMeasurementConversion(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        UnitMeasurementConversionModel unitMeasurementConversionModel = new UnitMeasurementConversionModel();
        try {
            unitMeasurementConversionModel = Core.getRequestObject(requestMessage, UnitMeasurementConversionModel.class);

            /*Set<ConstraintViolation<UnitMeasurementConversionModel>> violations = this.validator.validate(unitMeasurementConversionModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            unitMeasurementConversionModel = this.unitMeasurementConversionBllManager.inactiveUnitMeasurementConversion(unitMeasurementConversionModel);

            responseMessage.responseObj = unitMeasurementConversionModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_INACTIVE_UNIT_OF_MEASURE_CONVERSION;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.FAILED_TO_INACTIVE_UNIT_OF_MEASURE_CONVERSION;
                }else
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("UnitMeasurementConversionServiceManager -> filterUnitMeasurementConversion got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }
}
