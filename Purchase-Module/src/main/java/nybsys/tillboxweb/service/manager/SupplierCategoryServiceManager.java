/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 22/02/2018
 * Time: 11:54
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.bll.manager.SupplierCategoryBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.coreModels.SupplierCategoryModel;
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
public class SupplierCategoryServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(SupplierCategoryServiceManager.class);
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Autowired
    private SupplierCategoryBllManager supplierCategoryBllManager;

    public ResponseMessage saveSupplierCategory(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        SupplierCategoryModel supplierCategoryModel = new SupplierCategoryModel();
        try {
            supplierCategoryModel = Core.getRequestObject(requestMessage, SupplierCategoryModel.class);

            /*Set<ConstraintViolation<SupplierCategoryModel>> violations = this.validator.validate(supplierCategoryModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            supplierCategoryModel = this.supplierCategoryBllManager.saveOrUpdate(supplierCategoryModel);

            responseMessage.responseObj = supplierCategoryModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUPPLIER_CATEGORY_SAVE_SUCCESSFULLY;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.SUPPLIER_CATEGORY_SAVE_FAILED;
                }else
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("SupplierCategoryServiceManager -> saveSupplierCategory got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }
    public ResponseMessage searchSupplierCategory(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<SupplierCategoryModel> lstSupplierCategoryModel = new ArrayList<>();
        SupplierCategoryModel supplierCategoryModel = new SupplierCategoryModel();
        try {
            supplierCategoryModel = Core.getRequestObject(requestMessage, SupplierCategoryModel.class);

            lstSupplierCategoryModel = this.supplierCategoryBllManager.searchSupplierCategory(supplierCategoryModel);

            responseMessage.responseObj = lstSupplierCategoryModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUPPLIER_CATEGORY_GET_SUCCESSFULLY;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SUPPLIER_CATEGORY_GET_FAILED;
            }

        } catch (Exception ex) {
            log.error("SupplierCategoryServiceManager -> searchSupplierCategory got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }
    public ResponseMessage getSupplierCategoryByID(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        SupplierCategoryModel supplierCategoryModel = new SupplierCategoryModel();
        try {
            supplierCategoryModel = Core.getRequestObject(requestMessage, SupplierCategoryModel.class);

            supplierCategoryModel = this.supplierCategoryBllManager.getSupplierCategoryByID(supplierCategoryModel.getSupplierCategoryID());

            responseMessage.responseObj = supplierCategoryModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUPPLIER_CATEGORY_GET_SUCCESSFULLY;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SUPPLIER_CATEGORY_GET_FAILED;
            }

        } catch (Exception ex) {
            log.error("SupplierCategoryServiceManager -> getSupplierCategoryByID got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }
}
