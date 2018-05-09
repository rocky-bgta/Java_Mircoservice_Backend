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
import nybsys.tillboxweb.bll.manager.CustomerTypeBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.coreModels.CustomerTypeModel;
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
public class CustomerTypeServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(CustomerTypeServiceManager.class);
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Autowired
    private CustomerTypeBllManager customerTypeBllManager;

    public ResponseMessage saveCustomerType(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        CustomerTypeModel customerTypeModel = new CustomerTypeModel();
        try {
            customerTypeModel = Core.getRequestObject(requestMessage, CustomerTypeModel.class);

            /*Set<ConstraintViolation<CustomerTypeModel>> violations = this.validator.validate(customerTypeModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            customerTypeModel = this.customerTypeBllManager.saveOrUpdate(customerTypeModel);

            responseMessage.responseObj = customerTypeModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.CUSTOMER_TYPE_SAVE_SUCCESSFULLY;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.CUSTOMER_TYPE_SAVE_FAILED;
                }else
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("CustomerTypeServiceManager -> saveCustomerType got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }
    public ResponseMessage searchCustomerType(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<CustomerTypeModel> lstCustomerTypeModel = new ArrayList<>();
        CustomerTypeModel customerTypeModel = new CustomerTypeModel();
        try {
            customerTypeModel = Core.getRequestObject(requestMessage, CustomerTypeModel.class);

            lstCustomerTypeModel = this.customerTypeBllManager.searchCustomerType(customerTypeModel);

            responseMessage.responseObj = lstCustomerTypeModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.CUSTOMER_TYPE_GET_SUCCESSFULLY;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.CUSTOMER_TYPE_GET_FAILED;
            }

        } catch (Exception ex) {
            log.error("CustomerTypeServiceManager -> searchCustomerType got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }
    public ResponseMessage getCustomerTypeByID(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        CustomerTypeModel customerTypeModel = new CustomerTypeModel();
        try {
            customerTypeModel = Core.getRequestObject(requestMessage, CustomerTypeModel.class);

            customerTypeModel = this.customerTypeBllManager.getCustomerTypeByID(customerTypeModel.getCustomerTypeID());

            responseMessage.responseObj = customerTypeModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.CUSTOMER_TYPE_GET_SUCCESSFULLY;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.CUSTOMER_TYPE_GET_FAILED;
            }

        } catch (Exception ex) {
            log.error("CustomerTypeServiceManager -> getCustomerTypeByID got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }
    public ResponseMessage deleteCustomerType(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        CustomerTypeModel customerTypeModel = new CustomerTypeModel();
        try {
            customerTypeModel = Core.getRequestObject(requestMessage, CustomerTypeModel.class);

            customerTypeModel = this.customerTypeBllManager.deleteCustomerType(customerTypeModel);

            responseMessage.responseObj = customerTypeModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.CUSTOMER_TYPE_DELETE_SUCCESSFULLY;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.CUSTOMER_TYPE_DELETE_FAILED;
            }

        } catch (Exception ex) {
            log.error("CustomerTypeServiceManager -> deleteCustomerType got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }
}
