/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 14/02/2018
 * Time: 10:41
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.bll.manager.ProductAttributeBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.models.ProductAttributeModel;
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
public class ProductAttributeServiceManager extends BaseService{
    private static final Logger log = LoggerFactory.getLogger(ProductAttributeServiceManager.class);
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Autowired
    private ProductAttributeBllManager productAttributeBllManager;

    public ResponseMessage saveProductAttribute(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        ProductAttributeModel productAttributeModel = new ProductAttributeModel();
        try {
            productAttributeModel = Core.getRequestObject(requestMessage, ProductAttributeModel.class);

            /*Set<ConstraintViolation<ProductAttributeModel>> violations = this.validator.validate(productAttributeModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            productAttributeModel = this.productAttributeBllManager.saveOrUpdateProductAttributeWithBusinessLogic(productAttributeModel);

            responseMessage.responseObj = productAttributeModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_SAVE_PRODUCT_ATTRIBUTE;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.FAILED_TO_SAVE_PRODUCT_ATTRIBUTE;
                }else
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("ProductAttributeServiceManager -> saveProductAttribute got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }

    public ResponseMessage deleteProductAttribute(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        ProductAttributeModel productAttributeModel = new ProductAttributeModel();
        try {
            productAttributeModel = Core.getRequestObject(requestMessage, ProductAttributeModel.class);

            /*Set<ConstraintViolation<ProductAttributeModel>> violations = this.validator.validate(productAttributeModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            productAttributeModel = this.productAttributeBllManager.deleteProductAttribute(productAttributeModel);

            responseMessage.responseObj = productAttributeModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_DELETE_PRODUCT_ATTRIBUTE;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.FAILED_TO_DELETE_PRODUCT_ATTRIBUTE;
                }else
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("ProductAttributeServiceManager -> deleteProductAttribute got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }

    public ResponseMessage searchProductAttribute(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<ProductAttributeModel> lstProductAttributeModel = new ArrayList<>();
        ProductAttributeModel productAttributeModel = new ProductAttributeModel();
        try {
            productAttributeModel = Core.getRequestObject(requestMessage, ProductAttributeModel.class);

            lstProductAttributeModel = this.productAttributeBllManager.searchProductAttribute(productAttributeModel);

            responseMessage.responseObj = lstProductAttributeModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_GET_PRODUCT_ATTRIBUTE;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.FAILED_TO_GET_PRODUCT_ATTRIBUTE;
            }

        } catch (Exception ex) {
            log.error("ProductAttributeServiceManager -> searchProductAttribute got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }

    public ResponseMessage inactiveProductAttribute(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        ProductAttributeModel productAttributeModel = new ProductAttributeModel();
        try {
            productAttributeModel = Core.getRequestObject(requestMessage, ProductAttributeModel.class);

            /*Set<ConstraintViolation<ProductAttributeModel>> violations = this.validator.validate(productAttributeModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            productAttributeModel = this.productAttributeBllManager.inactiveProductAttribute(productAttributeModel);

            responseMessage.responseObj = productAttributeModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_INACTIVE_PRODUCT_ATTRIBUTE;
                this.commit();

            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.FAILED_TO_INACTIVE_PRODUCT_ATTRIBUTE;
                }else
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("ProductAttributeServiceManager -> filterProductAttribute got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }
}
