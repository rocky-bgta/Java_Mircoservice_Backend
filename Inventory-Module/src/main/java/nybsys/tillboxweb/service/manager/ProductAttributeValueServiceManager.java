/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 14/02/2018
 * Time: 10:42
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.bll.manager.ProductAttributeValueBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.models.ProductAttributeValueModel;
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
public class ProductAttributeValueServiceManager extends BaseService{
    private static final Logger log = LoggerFactory.getLogger(ProductAttributeValueServiceManager.class);
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Autowired
    private ProductAttributeValueBllManager productAttributeValueBllManager;

    public ResponseMessage saveProductAttributeValue(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        ProductAttributeValueModel productAttributeValueModel = new ProductAttributeValueModel();
        try {
            productAttributeValueModel = Core.getRequestObject(requestMessage, ProductAttributeValueModel.class);

            /*Set<ConstraintViolation<ProductAttributeValueModel>> violations = this.validator.validate(productAttributeValueModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            productAttributeValueModel = this.productAttributeValueBllManager.saveOrUpdateProductAttributeValueWithBusinessLogic(productAttributeValueModel);

            responseMessage.responseObj = productAttributeValueModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_SAVE_PRODUCT_ATTRIBUTE_VALUE;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.FAILED_TO_SAVE_PRODUCT_ATTRIBUTE_VALUE;
                }else
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("ProductAttributeValueServiceManager -> saveProductAttributeValue got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }

    public ResponseMessage deleteProductAttributeValue(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        ProductAttributeValueModel productAttributeValueModel = new ProductAttributeValueModel();
        try {
            productAttributeValueModel = Core.getRequestObject(requestMessage, ProductAttributeValueModel.class);

            /*Set<ConstraintViolation<ProductAttributeValueModel>> violations = this.validator.validate(productAttributeValueModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            productAttributeValueModel = this.productAttributeValueBllManager.deleteProductAttributeValue(productAttributeValueModel);

            responseMessage.responseObj = productAttributeValueModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_DELETE_PRODUCT_ATTRIBUTE_VALUE;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.FAILED_TO_DELETE_PRODUCT_ATTRIBUTE_VALUE;
                }else
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("ProductAttributeValueServiceManager -> deleteProductAttributeValue got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }

    public ResponseMessage searchProductAttributeValue(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<ProductAttributeValueModel> lstProductAttributeValueModel = new ArrayList<>();
        ProductAttributeValueModel productAttributeValueModel = new ProductAttributeValueModel();
        try {
            productAttributeValueModel = Core.getRequestObject(requestMessage, ProductAttributeValueModel.class);

            lstProductAttributeValueModel = this.productAttributeValueBllManager.searchProductAttributeValue(productAttributeValueModel);

            responseMessage.responseObj = lstProductAttributeValueModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_GET_PRODUCT_ATTRIBUTE_VALUE;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.FAILED_TO_GET_PRODUCT_ATTRIBUTE_VALUE;
            }

        } catch (Exception ex) {
            log.error("ProductAttributeValueServiceManager -> searchProductAttributeValue got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }

    public ResponseMessage inactiveProductAttributeValue(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        ProductAttributeValueModel productAttributeValueModel = new ProductAttributeValueModel();
        try {
            productAttributeValueModel = Core.getRequestObject(requestMessage, ProductAttributeValueModel.class);

            /*Set<ConstraintViolation<ProductAttributeValueModel>> violations = this.validator.validate(productAttributeValueModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            productAttributeValueModel = this.productAttributeValueBllManager.inactiveProductAttributeValue(productAttributeValueModel);

            responseMessage.responseObj = productAttributeValueModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_INACTIVE_PRODUCT_ATTRIBUTE_VALUE;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.FAILED_TO_INACTIVE_PRODUCT_ATTRIBUTE_VALUE;
                }else
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("ProductAttributeValueServiceManager -> filterProductAttributeValue got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }
}
