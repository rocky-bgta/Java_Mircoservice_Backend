/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 5:09
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.bll.manager.DiscountSettingBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.models.DiscountSettingModel;
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
public class DiscountSettingServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(DiscountSettingServiceManager.class);
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Autowired
    private DiscountSettingBllManager discountSettingBllManager;

    public ResponseMessage saveDiscountSetting(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        DiscountSettingModel discountSettingModel = new DiscountSettingModel();
        try {
            discountSettingModel = Core.getRequestObject(requestMessage, DiscountSettingModel.class);

            /*Set<ConstraintViolation<DiscountSettingModel>> violations = this.validator.validate(discountSettingModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            discountSettingModel = this.discountSettingBllManager.saveOrUpdate(discountSettingModel);

            responseMessage.responseObj = discountSettingModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.DISCOUNT_SETTING_SAVE_SUCCESSFULLY;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.DISCOUNT_SETTING_SAVE_FAILED;
                }else
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("DiscountSettingServiceManager -> saveDiscountSetting got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }
    public ResponseMessage searchDiscountSetting(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<DiscountSettingModel> lstDiscountSettingModel = new ArrayList<>();
        DiscountSettingModel discountSettingModel = new DiscountSettingModel();
        try {
            discountSettingModel = Core.getRequestObject(requestMessage, DiscountSettingModel.class);

            lstDiscountSettingModel = this.discountSettingBllManager.searchDiscountSetting(discountSettingModel);

            responseMessage.responseObj = lstDiscountSettingModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.DISCOUNT_SETTING_GET_SUCCESSFULLY;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.DISCOUNT_SETTING_GET_FAILED;
            }

        } catch (Exception ex) {
            log.error("DiscountSettingServiceManager -> searchDiscountSetting got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }
    public ResponseMessage deleteDiscountSetting(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        DiscountSettingModel discountSettingModel = new DiscountSettingModel();
        try {
            discountSettingModel = Core.getRequestObject(requestMessage, DiscountSettingModel.class);

            discountSettingModel = this.discountSettingBllManager.deleteDiscountSetting(discountSettingModel);

            responseMessage.responseObj = discountSettingModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.DISCOUNT_SETTING_DELETE_SUCCESSFULLY;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.DISCOUNT_SETTING_DELETE_FAILED;
            }

        } catch (Exception ex) {
            log.error("DiscountSettingServiceManager -> deleteDiscountSetting got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }
}
