/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 13/02/2018
 * Time: 03:49
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.bll.manager.ProductCategoryBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.coreModels.ProductCategoryModel;
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
public class ProductCategoryServiceManager extends BaseService{
    private static final Logger log = LoggerFactory.getLogger(ProductCategoryServiceManager.class);
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Autowired
    private ProductCategoryBllManager productCategoryBllManager;

    public ResponseMessage saveProductCategory(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        ProductCategoryModel productCategoryModel = new ProductCategoryModel();
        try {
            productCategoryModel = Core.getRequestObject(requestMessage, ProductCategoryModel.class);

            /*Set<ConstraintViolation<ProductCategoryModel>> violations = this.validator.validate(productCategoryModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            productCategoryModel = this.productCategoryBllManager.saveOrUpdateProductCategoryWithBusinessLogic(productCategoryModel);

            responseMessage.responseObj = productCategoryModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_SAVE_PRODUCT_CATEGORY;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.FAILED_TO_SAVE_PRODUCT_CATEGORY;
                }else
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("ProductCategoryServiceManager -> saveProductCategory got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }

    public ResponseMessage deleteProductCategory(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        ProductCategoryModel productCategoryModel = new ProductCategoryModel();
        try {
            productCategoryModel = Core.getRequestObject(requestMessage, ProductCategoryModel.class);

            /*Set<ConstraintViolation<ProductCategoryModel>> violations = this.validator.validate(productCategoryModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            productCategoryModel = this.productCategoryBllManager.deleteProductCategory(productCategoryModel);

            responseMessage.responseObj = productCategoryModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_DELETE_PRODUCT_CATEGORY;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.FAILED_TO_DELETE_PRODUCT_CATEGORY;
                }else
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("ProductCategoryServiceManager -> deleteProductCategory got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }

    public ResponseMessage searchProductCategory(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<ProductCategoryModel> lstProductCategoryModel = new ArrayList<>();
        ProductCategoryModel productCategoryModel = new ProductCategoryModel();
        try {
            productCategoryModel = Core.getRequestObject(requestMessage, ProductCategoryModel.class);

            lstProductCategoryModel = this.productCategoryBllManager.searchProductCategory(productCategoryModel);

            responseMessage.responseObj = lstProductCategoryModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_GET_PRODUCT_CATEGORY;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.FAILED_TO_GET_PRODUCT_CATEGORY;
            }

        } catch (Exception ex) {
            log.error("ProductCategoryServiceManager -> searchProductCategory got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }

    public ResponseMessage inactiveProductCategory(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        ProductCategoryModel productCategoryModel = new ProductCategoryModel();
        try {
            productCategoryModel = Core.getRequestObject(requestMessage, ProductCategoryModel.class);

            /*Set<ConstraintViolation<ProductCategoryModel>> violations = this.validator.validate(productCategoryModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            productCategoryModel = this.productCategoryBllManager.inactiveProductCategory(productCategoryModel);

            responseMessage.responseObj = productCategoryModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_INACTIVE_PRODUCT_CATEGORY;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.FAILED_TO_INACTIVE_PRODUCT_CATEGORY;
                }else
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("ProductCategoryServiceManager -> filterProductCategory got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }
}
