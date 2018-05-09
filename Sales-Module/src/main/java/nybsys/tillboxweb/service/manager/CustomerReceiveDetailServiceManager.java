/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 13-Mar-18
 * Time: 11:12 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.BaseServiceManager;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.models.CustomerReceiveDetailModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import nybsys.tillboxweb.bll.manager.CustomerReceiveDetailBllManager;

import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CustomerReceiveDetailServiceManager extends BaseService implements BaseServiceManager {

    private static final Logger log = LoggerFactory.getLogger(CustomerReceiveDetailServiceManager.class);

    @Autowired
    private CustomerReceiveDetailBllManager customerReceiveDetailBllManager;

    @Override
    public ResponseMessage saveOrUpdate(RequestMessage requestMessage) {
        ResponseMessage responseMessage = this.getDefaultResponseMessage();
        CustomerReceiveDetailModel saveOrUpdatedCustomerReceiveDetailModel;
        try {
            CustomerReceiveDetailModel reqCustomerReceiveDetailModel =
                    Core.getRequestObject(requestMessage, CustomerReceiveDetailModel.class);
            saveOrUpdatedCustomerReceiveDetailModel = this.customerReceiveDetailBllManager.saveOrUpdate(reqCustomerReceiveDetailModel);
            if (Core.clientMessage.get().messageCode == TillBoxAppConstant.SUCCESS_CODE) {
                responseMessage.responseObj = saveOrUpdatedCustomerReceiveDetailModel;
                responseMessage.message = "CustomerReceiveDetail Save Successfully";
                this.commit();
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = Core.clientMessage.get().message;
                this.rollBack();
            }
        } catch (Exception e) {
            log.error("CustomerReceiveDetailServiceManager -> saveOrUpdate got exception");
            this.rollBack();
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(e);
        }

        return responseMessage;
    }

    @Override
    public ResponseMessage search(RequestMessage requestMessage) {
        ResponseMessage responseMessage = this.getDefaultResponseMessage();
        List<CustomerReceiveDetailModel> finedCustomerReceiveDetailList;
        try {
            CustomerReceiveDetailModel reqCustomerReceiveDetailModel =
                    Core.getRequestObject(requestMessage, CustomerReceiveDetailModel.class);
            finedCustomerReceiveDetailList = this.customerReceiveDetailBllManager.search(reqCustomerReceiveDetailModel);
            if (Core.clientMessage.get().messageCode == TillBoxAppConstant.SUCCESS_CODE) {
                responseMessage.responseObj = finedCustomerReceiveDetailList;
                responseMessage.message = "Find the request CustomerReceiveDetail"; //Core.wrapperModel.get().userMessage;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = Core.clientMessage.get().message;
            }
        } catch (Exception e) {
            log.error("CustomerReceiveDetailServiceManager -> search got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(e);
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage delete(RequestMessage requestMessage) {
        ResponseMessage responseMessage = this.getDefaultResponseMessage();
        CustomerReceiveDetailModel deletedCustomerReceiveDetailModel;
        try {
            CustomerReceiveDetailModel reqCustomerReceiveDetailModel =
                    Core.getRequestObject(requestMessage, CustomerReceiveDetailModel.class);

            deletedCustomerReceiveDetailModel = this.customerReceiveDetailBllManager.delete(reqCustomerReceiveDetailModel);
            if (Core.clientMessage.get().messageCode == TillBoxAppConstant.SUCCESS_CODE) {
                responseMessage.responseObj = deletedCustomerReceiveDetailModel;
                responseMessage.message = Core.clientMessage.get().userMessage;
                this.commit();
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = Core.clientMessage.get().message;
                this.rollBack();
            }
        } catch (Exception e) {
            log.error("CustomerReceiveDetailServiceManager -> delete got exception");
            this.rollBack();
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(e);
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage inActive(RequestMessage requestMessage) {
        ResponseMessage responseMessage = this.getDefaultResponseMessage();
        Object resultObject;
        try {
            CustomerReceiveDetailModel reqCustomerReceiveDetailModel =
                    Core.getRequestObject(requestMessage, CustomerReceiveDetailModel.class);
            resultObject = this.customerReceiveDetailBllManager.inActive(requestMessage);
            if (Core.clientMessage.get().messageCode == TillBoxAppConstant.SUCCESS_CODE) {
                responseMessage.responseObj = resultObject;
                responseMessage.message = Core.clientMessage.get().userMessage;
                this.commit();
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = Core.clientMessage.get().message;
                this.rollBack();
            }
        } catch (Exception e) {
            log.error("CustomerReceiveDetailServiceManager -> delete got exception");
            this.rollBack();
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(e);
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage getById(RequestMessage requestMessage) {
        ResponseMessage responseMessage = this.getDefaultResponseMessage();
        CustomerReceiveDetailModel findCustomerReceiveDetailModel;
        try {
            CustomerReceiveDetailModel reqCustomerReceiveDetailModel =
                    Core.getRequestObject(requestMessage, CustomerReceiveDetailModel.class);
            findCustomerReceiveDetailModel = this.customerReceiveDetailBllManager.getById(reqCustomerReceiveDetailModel);
            if (Core.clientMessage.get().messageCode == TillBoxAppConstant.SUCCESS_CODE) {
                responseMessage.responseObj = findCustomerReceiveDetailModel;
                responseMessage.message = Core.clientMessage.get().userMessage;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = Core.clientMessage.get().message;
            }
        } catch (Exception e) {
            log.error("CustomerReceiveDetailServiceManager -> getById got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(e);
        }
        return responseMessage;
    }
}