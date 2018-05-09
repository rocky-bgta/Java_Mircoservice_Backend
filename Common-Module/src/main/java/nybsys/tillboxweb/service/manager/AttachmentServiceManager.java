/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 26-Feb-18
 * Time: 4:34 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.BaseServiceManager;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.bll.manager.AttachmentBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AttachmentServiceManager extends BaseService implements BaseServiceManager {

    private static final Logger log = LoggerFactory.getLogger(AttachmentServiceManager.class);

    @Autowired
    private AttachmentBllManager attachmentBllManager;

    @Override
    public ResponseMessage saveOrUpdate(RequestMessage requestMessage) {
        ResponseMessage responseMessage = this.getDefaultResponseMessage();
        Object resultObject;
        try {
            resultObject = this.attachmentBllManager.saveOrUpdate(requestMessage);
            if (Core.clientMessage.get().messageCode == TillBoxAppConstant.SUCCESS_CODE) {
                responseMessage.responseObj = resultObject;
                responseMessage.message = Core.clientMessage.get().userMessage;
                this.commit();
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = Core.clientMessage.get().userMessage;
                this.rollBack();
            }
        } catch (Exception e) {
            log.error("AttachmentServiceManager -> saveOrUpdate got exception");
            this.rollBack();
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(e);
        }


        return responseMessage;
    }

    @Override
    public ResponseMessage search(RequestMessage requestMessage) {
        ResponseMessage responseMessage = this.getDefaultResponseMessage();
        Object resultObject;
        try {
            resultObject = this.attachmentBllManager.search(requestMessage);
            if (Core.clientMessage.get().messageCode == TillBoxAppConstant.SUCCESS_CODE) {
                responseMessage.responseObj = resultObject;
                responseMessage.message = Core.clientMessage.get().userMessage;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                //responseMessage.message = MessageConstant.FAILED_TO_THE_FINED_REQUESTED_COMMON_MESSAGE;
            }
        } catch (Exception e) {
            log.error("AttachmentServiceManager -> search got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(e);
        }

        return responseMessage;
    }

    @Override
    public ResponseMessage delete(RequestMessage requestMessage) {
        ResponseMessage responseMessage = this.getDefaultResponseMessage();
        Object resultObject;
        try {
            resultObject = this.attachmentBllManager.delete(requestMessage);
            if (Core.clientMessage.get().messageCode == TillBoxAppConstant.SUCCESS_CODE) {
                responseMessage.responseObj = resultObject;
                responseMessage.message = Core.clientMessage.get().userMessage;
                this.commit();
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.FAILED_TO_DELETE_COMMON_MESSAGE;
                this.rollBack();
            }
        } catch (Exception e) {
            log.error("AttachmentServiceManager -> delete got exception");
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
            resultObject = this.attachmentBllManager.inActive(requestMessage);
            if (Core.clientMessage.get().messageCode == TillBoxAppConstant.SUCCESS_CODE) {
                responseMessage.responseObj = resultObject;
                responseMessage.message = Core.clientMessage.get().userMessage;
                this.commit();
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.FAILED_TO_DELETE_COMMON_MESSAGE;
                this.rollBack();
            }
        } catch (Exception e) {
            log.error("AttachmentServiceManager -> delete got exception");
            this.rollBack();
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(e);
        }

        return responseMessage;
    }

    @Override
    public ResponseMessage getById(RequestMessage requestMessage) {
        ResponseMessage responseMessage = this.getDefaultResponseMessage();
        Object resultObject;
        try {
            resultObject = this.attachmentBllManager.getByID(requestMessage);
            if (Core.clientMessage.get().messageCode == TillBoxAppConstant.SUCCESS_CODE) {
                responseMessage.responseObj = resultObject;
                responseMessage.message = Core.clientMessage.get().userMessage;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.FAILED_TO_GET_REQUESTED_COMMON_MESSAGE;
            }
        } catch (Exception e) {
            log.error("AttachmentServiceManager -> getByID got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(e);
        }
        return responseMessage;
    }
}