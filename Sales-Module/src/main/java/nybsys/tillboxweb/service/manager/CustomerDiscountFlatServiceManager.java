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
import nybsys.tillboxweb.bll.manager.CustomerDiscountFlatBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.models.CustomerDiscountFlatModel;
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
public class CustomerDiscountFlatServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(CustomerDiscountFlatServiceManager.class);
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Autowired
    private CustomerDiscountFlatBllManager customerDiscountFlatBllManager;

    public ResponseMessage saveCustomerDiscountFlat(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        CustomerDiscountFlatModel customerDiscountFlatModel = new CustomerDiscountFlatModel();
        try {
            customerDiscountFlatModel = Core.getRequestObject(requestMessage, CustomerDiscountFlatModel.class);

            /*Set<ConstraintViolation<CustomerDiscountFlatModel>> violations = this.validator.validate(customerDiscountFlatModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            customerDiscountFlatModel = this.customerDiscountFlatBllManager.saveOrUpdate(customerDiscountFlatModel);

            responseMessage.responseObj = customerDiscountFlatModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.CUSTOMER_DISCOUNT_FLAT_SAVE_SUCCESSFULLY;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.CUSTOMER_DISCOUNT_FLAT_SAVE_FAILED;
                }else
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("CustomerDiscountFlatServiceManager -> saveCustomerDiscountFlat got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }
    public ResponseMessage searchCustomerDiscountFlat(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<CustomerDiscountFlatModel> lstCustomerDiscountFlatModel = new ArrayList<>();
        CustomerDiscountFlatModel customerDiscountFlatModel = new CustomerDiscountFlatModel();
        try {
            customerDiscountFlatModel = Core.getRequestObject(requestMessage, CustomerDiscountFlatModel.class);

            lstCustomerDiscountFlatModel = this.customerDiscountFlatBllManager.searchCustomerDiscountFlat(customerDiscountFlatModel);

            responseMessage.responseObj = lstCustomerDiscountFlatModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.CUSTOMER_DISCOUNT_FLAT_GET_SUCCESSFULLY;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.CUSTOMER_DISCOUNT_FLAT_GET_FAILED;
            }

        } catch (Exception ex) {
            log.error("CustomerDiscountFlatServiceManager -> searchCustomerDiscountFlat got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }
    public ResponseMessage deleteCustomerDiscountFlat(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        CustomerDiscountFlatModel customerDiscountFlatModel = new CustomerDiscountFlatModel();
        try {
            customerDiscountFlatModel = Core.getRequestObject(requestMessage, CustomerDiscountFlatModel.class);

            customerDiscountFlatModel = this.customerDiscountFlatBllManager.deleteCustomerDiscountFlat(customerDiscountFlatModel);

            responseMessage.responseObj = customerDiscountFlatModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.CUSTOMER_DISCOUNT_FLAT_DELETE_SUCCESSFULLY;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.CUSTOMER_DISCOUNT_FLAT_DELETE_FAILED;
            }

        } catch (Exception ex) {
            log.error("CustomerDiscountFlatServiceManager -> deleteCustomerDiscountFlat got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }
}
