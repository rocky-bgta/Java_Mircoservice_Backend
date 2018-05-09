/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 12/03/2018
 * Time: 12:07
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.bll.manager.SupplierTypeBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.coreModels.SupplierTypeModel;
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
public class SupplierTypeServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(SupplierTypeServiceManager.class);
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Autowired
    private SupplierTypeBllManager supplierTypeBllManager;

    public ResponseMessage saveSupplierType(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        SupplierTypeModel supplierTypeModel = new SupplierTypeModel();
        try {
            supplierTypeModel = Core.getRequestObject(requestMessage, SupplierTypeModel.class);

            /*Set<ConstraintViolation<SupplierTypeModel>> violations = this.validator.validate(supplierTypeModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            supplierTypeModel = this.supplierTypeBllManager.saveOrUpdate(supplierTypeModel);

            responseMessage.responseObj = supplierTypeModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUPPLIER_TYPE_SAVE_SUCCESSFULLY;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.SUPPLIER_TYPE_SAVE_FAILED;
                }else
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("SupplierTypeServiceManager -> saveSupplierType got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }
    public ResponseMessage searchSupplierType(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<SupplierTypeModel> lstSupplierTypeModel = new ArrayList<>();
        SupplierTypeModel supplierTypeModel = new SupplierTypeModel();
        try {
            supplierTypeModel = Core.getRequestObject(requestMessage, SupplierTypeModel.class);

            lstSupplierTypeModel = this.supplierTypeBllManager.searchSupplierType(supplierTypeModel);

            responseMessage.responseObj = lstSupplierTypeModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUPPLIER_TYPE_GET_SUCCESSFULLY;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SUPPLIER_TYPE_GET_FAILED;
            }

        } catch (Exception ex) {
            log.error("SupplierTypeServiceManager -> searchSupplierType got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }
    public ResponseMessage getSupplierTypeByID(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        SupplierTypeModel supplierTypeModel = new SupplierTypeModel();
        try {
            supplierTypeModel = Core.getRequestObject(requestMessage, SupplierTypeModel.class);

            supplierTypeModel = this.supplierTypeBllManager.getSupplierTypeByID(supplierTypeModel.getSupplierTypeID());

            responseMessage.responseObj = supplierTypeModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUPPLIER_TYPE_GET_SUCCESSFULLY;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SUPPLIER_TYPE_GET_FAILED;
            }

        } catch (Exception ex) {
            log.error("SupplierTypeServiceManager -> getSupplierTypeByID got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }
    public ResponseMessage deleteSupplierType(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        SupplierTypeModel supplierTypeModel = new SupplierTypeModel();
        try {
            supplierTypeModel = Core.getRequestObject(requestMessage, SupplierTypeModel.class);

            supplierTypeModel = this.supplierTypeBllManager.deleteSupplierType(supplierTypeModel);

            responseMessage.responseObj = supplierTypeModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUPPLIER_TYPE_DELETE_SUCCESSFULLY;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SUPPLIER_TYPE_DELETE_FAILED;
            }

        } catch (Exception ex) {
            log.error("SupplierTypeServiceManager -> deleteSupplierType got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }
}
