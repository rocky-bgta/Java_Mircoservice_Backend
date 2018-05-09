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
import nybsys.tillboxweb.bll.manager.DiscountGiveProductBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.models.DiscountGiveProductModel;
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
public class DiscountGiveProductServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(DiscountGiveProductServiceManager.class);
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Autowired
    private DiscountGiveProductBllManager discountGiveProductBllManager;

    public ResponseMessage saveDiscountGiveProduct(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        DiscountGiveProductModel discountGiveProductModel = new DiscountGiveProductModel();
        try {
            discountGiveProductModel = Core.getRequestObject(requestMessage, DiscountGiveProductModel.class);

            /*Set<ConstraintViolation<DiscountGiveProductModel>> violations = this.validator.validate(discountGiveProductModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            discountGiveProductModel = this.discountGiveProductBllManager.saveOrUpdate(discountGiveProductModel);

            responseMessage.responseObj = discountGiveProductModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.DISCOUNT_GIVE_PRODUCT_SAVE_SUCCESSFULLY;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.DISCOUNT_GIVE_PRODUCT_SAVE_FAILED;
                }else
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("DiscountGiveProductServiceManager -> saveDiscountGiveProduct got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }
    public ResponseMessage searchDiscountGiveProduct(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<DiscountGiveProductModel> lstDiscountGiveProductModel = new ArrayList<>();
        DiscountGiveProductModel discountGiveProductModel = new DiscountGiveProductModel();
        try {
            discountGiveProductModel = Core.getRequestObject(requestMessage, DiscountGiveProductModel.class);

            lstDiscountGiveProductModel = this.discountGiveProductBllManager.searchDiscountGiveProduct(discountGiveProductModel);

            responseMessage.responseObj = lstDiscountGiveProductModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.DISCOUNT_GIVE_PRODUCT_GET_SUCCESSFULLY;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.DISCOUNT_GIVE_PRODUCT_GET_FAILED;
            }

        } catch (Exception ex) {
            log.error("DiscountGiveProductServiceManager -> searchDiscountGiveProduct got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }
    public ResponseMessage deleteDiscountGiveProduct(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        DiscountGiveProductModel discountGiveProductModel = new DiscountGiveProductModel();
        try {
            discountGiveProductModel = Core.getRequestObject(requestMessage, DiscountGiveProductModel.class);

            discountGiveProductModel = this.discountGiveProductBllManager.deleteDiscountGiveProduct(discountGiveProductModel);

            responseMessage.responseObj = discountGiveProductModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.DISCOUNT_GIVE_PRODUCT_DELETE_SUCCESSFULLY;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.DISCOUNT_GIVE_PRODUCT_DELETE_FAILED;
            }

        } catch (Exception ex) {
            log.error("DiscountGiveProductServiceManager -> deleteDiscountGiveProduct got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }
}
