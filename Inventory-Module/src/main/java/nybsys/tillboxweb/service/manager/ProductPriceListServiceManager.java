/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 14/02/2018
 * Time: 10:45
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.bll.manager.ProductPriceListBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.models.PriceCategoryModel;
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
public class ProductPriceListServiceManager extends BaseService{
    private static final Logger log = LoggerFactory.getLogger(ProductPriceListServiceManager.class);
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Autowired
    private ProductPriceListBllManager productPriceListBllManager;

    public ResponseMessage savePriceCategory(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        PriceCategoryModel priceCategoryModel = new PriceCategoryModel();
        try {
            priceCategoryModel = Core.getRequestObject(requestMessage, PriceCategoryModel.class);

            /*Set<ConstraintViolation<PriceCategoryModel>> violations = this.validator.validate(priceCategoryModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            priceCategoryModel = this.productPriceListBllManager.saveOrUpdatePriceCategoryWithBusinessLogic(priceCategoryModel);

            responseMessage.responseObj = priceCategoryModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_SAVE_PRICE_CATEGORY;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.FAILED_TO_SAVE_PRICE_CATEGORY;
                }else
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("ProductPriceListBllManager -> savePriceCategory got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }

    public ResponseMessage deletePriceCategory(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        PriceCategoryModel priceCategoryModel = new PriceCategoryModel();
        try {
            priceCategoryModel = Core.getRequestObject(requestMessage, PriceCategoryModel.class);

            /*Set<ConstraintViolation<PriceCategoryModel>> violations = this.validator.validate(priceCategoryModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            priceCategoryModel = this.productPriceListBllManager.deletePriceCategory(priceCategoryModel);

            responseMessage.responseObj = priceCategoryModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_DELETE_PRICE_CATEGORY;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.FAILED_TO_DELETE_PRICE_CATEGORY;
                }else
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("ProductPriceListBllManager ->  deletePriceCategory got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }

    public ResponseMessage searchPriceCategory(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<PriceCategoryModel> lstPriceCategoryModel = new ArrayList<>();
        PriceCategoryModel priceCategoryModel = new PriceCategoryModel();
        try {
            priceCategoryModel = Core.getRequestObject(requestMessage, PriceCategoryModel.class);

            lstPriceCategoryModel = this.productPriceListBllManager.searchPriceCategory(priceCategoryModel);

            responseMessage.responseObj = lstPriceCategoryModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_GET_PRICE_CATEGORY;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.FAILED_TO_GET_PRICE_CATEGORY;
            }

        } catch (Exception ex) {
            log.error("PriceCategoryServiceManager -> searchPriceCategory got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }

    public ResponseMessage inactivePriceCategory(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        PriceCategoryModel priceCategoryModel = new PriceCategoryModel();
        try {
            priceCategoryModel = Core.getRequestObject(requestMessage, PriceCategoryModel.class);

            /*Set<ConstraintViolation<PriceCategoryModel>> violations = this.validator.validate(priceCategoryModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            priceCategoryModel = this.productPriceListBllManager.inactivePriceCategory(priceCategoryModel);

            responseMessage.responseObj = priceCategoryModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_INACTIVE_PRICE_CATEGORY;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.FAILED_TO_INACTIVE_PRICE_CATEGORY;
                }else
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("ProductPriceListServiceManagerr -> inactivePriceCategory got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }
}
