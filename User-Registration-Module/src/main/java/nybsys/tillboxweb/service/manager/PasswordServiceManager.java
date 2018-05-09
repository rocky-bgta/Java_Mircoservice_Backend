package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.TillBoxWebModels.ForgetPasswordTokenModel;
import nybsys.tillboxweb.bll.manager.PasswordBllManager;
import nybsys.tillboxweb.bll.manager.UserBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.models.VMChangePasswordModel;
import nybsys.tillboxweb.models.VMForgetPasswordModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PasswordServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceManager.class);
    private ResponseMessage responseMessage;

    @Autowired
    UserBllManager userBllManager;

    @Autowired
    PasswordBllManager passwordBllManager;


    public ResponseMessage getByUserID(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        ForgetPasswordTokenModel forgetPasswordTokenModel;

        try {
            forgetPasswordTokenModel = Core.getRequestObject(requestMessage, ForgetPasswordTokenModel.class);

            /*Set<ConstraintViolation<ForgetPasswordTokenModel>> violations = this.validator.validate(forgetPasswordTokenModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/
            forgetPasswordTokenModel = this.passwordBllManager.getPassword(forgetPasswordTokenModel);

            responseMessage.responseObj = forgetPasswordTokenModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_SEND_MAIL;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage != null)
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }else {
                    responseMessage.message = MessageConstant.FAILED_TO_SEND_MAIL;
                }
            }

        }catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.OPERATION_FAILED, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            log.error("PasswordServiceManager -> getByUserID got exception");
        }
        return  responseMessage;
    }

    public ResponseMessage forgetPasswordUpdate(RequestMessage requestMessage) {

        VMForgetPasswordModel forgetPasswordModel = new VMForgetPasswordModel();

        try {
            forgetPasswordModel = Core.getRequestObject(requestMessage, VMForgetPasswordModel.class);

            /*Set<ConstraintViolation<ForgetPasswordTokenModel>> violations = this.validator.validate(forgetPasswordTokenModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            forgetPasswordModel = this.passwordBllManager.forgetPassword(forgetPasswordModel);

            responseMessage.responseObj = forgetPasswordModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_CHANGED_PASSWORD;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage != null)
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }else {
                    responseMessage.message = MessageConstant.FAILED_TO_CHANGE_PASSWORD;
                }
                this.rollBack();
            }

        }catch (Exception ex)
        {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.OPERATION_FAILED, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            this.rollBack();
            log.error("PasswordServiceManager -> forgetPasswordUpdate got exception");
        }
        return  responseMessage;
    }

    public ResponseMessage changePasswordUpdate(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        VMChangePasswordModel changePasswordModel = new VMChangePasswordModel();

        try {
            changePasswordModel = Core.getRequestObject(requestMessage, VMChangePasswordModel.class);
            changePasswordModel.setUserID(requestMessage.userID);

            /*Set<ConstraintViolation<ChangePasswordModel>> violations = this.validator.validate(changePasswordModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/
            changePasswordModel = this.passwordBllManager.changePassword(changePasswordModel);

            responseMessage.responseObj = changePasswordModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_CHANGED_PASSWORD;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage != null)
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }else {
                    responseMessage.message = MessageConstant.FAILED_TO_CHANGE_PASSWORD;
                }
                this.rollBack();
            }

        }catch (Exception ex)
        {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.OPERATION_FAILED, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            ex.printStackTrace();
            this.rollBack();
            log.error("PasswordServiceManager -> changePasswordUpdate got exception");
        }
        return  responseMessage;
    }
}
