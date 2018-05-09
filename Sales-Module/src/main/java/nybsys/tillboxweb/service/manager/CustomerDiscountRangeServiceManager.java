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
import nybsys.tillboxweb.bll.manager.CustomerDiscountRangeBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.models.CustomerDiscountRangeModel;
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
public class CustomerDiscountRangeServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(CustomerDiscountRangeServiceManager.class);
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Autowired
    private CustomerDiscountRangeBllManager customerDiscountRangeBllManager;

    public ResponseMessage saveCustomerDiscountRange(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        CustomerDiscountRangeModel customerDiscountRangeModel = new CustomerDiscountRangeModel();
        try {
            customerDiscountRangeModel = Core.getRequestObject(requestMessage, CustomerDiscountRangeModel.class);

            /*Set<ConstraintViolation<CustomerDiscountRangeModel>> violations = this.validator.validate(customerDiscountRangeModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            customerDiscountRangeModel = this.customerDiscountRangeBllManager.saveOrUpdate(customerDiscountRangeModel);

            responseMessage.responseObj = customerDiscountRangeModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.CUSTOMER_DISCOUNT_RANGE_SAVE_SUCCESSFULLY;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.CUSTOMER_DISCOUNT_RANGE_SAVE_FAILED;
                }else
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("CustomerDiscountRangeServiceManager -> saveCustomerDiscountRange got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }
    public ResponseMessage searchCustomerDiscountRange(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<CustomerDiscountRangeModel> lstCustomerDiscountRangeModel = new ArrayList<>();
        CustomerDiscountRangeModel customerDiscountRangeModel = new CustomerDiscountRangeModel();
        try {
            customerDiscountRangeModel = Core.getRequestObject(requestMessage, CustomerDiscountRangeModel.class);

            lstCustomerDiscountRangeModel = this.customerDiscountRangeBllManager.searchCustomerDiscountRange(customerDiscountRangeModel);

            responseMessage.responseObj = lstCustomerDiscountRangeModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.CUSTOMER_DISCOUNT_RANGE_GET_SUCCESSFULLY;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.CUSTOMER_DISCOUNT_RANGE_GET_FAILED;
            }

        } catch (Exception ex) {
            log.error("CustomerDiscountRangeServiceManager -> searchCustomerDiscountRange got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }
    public ResponseMessage deleteCustomerDiscountRange(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        CustomerDiscountRangeModel customerDiscountRangeModel = new CustomerDiscountRangeModel();
        try {
            customerDiscountRangeModel = Core.getRequestObject(requestMessage, CustomerDiscountRangeModel.class);

            customerDiscountRangeModel = this.customerDiscountRangeBllManager.deleteCustomerDiscountRange(customerDiscountRangeModel);

            responseMessage.responseObj = customerDiscountRangeModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.CUSTOMER_DISCOUNT_RANGE_DELETE_SUCCESSFULLY;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.CUSTOMER_DISCOUNT_RANGE_DELETE_FAILED;
            }

        } catch (Exception ex) {
            log.error("CustomerDiscountRangeServiceManager -> deleteCustomerDiscountRange got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }
}
