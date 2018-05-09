package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.TillBoxWebModels.UserInvitationModel;
import nybsys.tillboxweb.TillBoxWebModels.UserModel;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.bll.manager.UserBllManager;
import nybsys.tillboxweb.bll.manager.UserInvitationBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.models.UserBusinessRightMapperModel;
import nybsys.tillboxweb.models.VMTokenModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserInvitationServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(Core.class);
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @Autowired
    private UserInvitationBllManager userInvitationBllManager ;
    @Autowired
    private UserBllManager userBllManager;


    public ResponseMessage invite(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<UserBusinessRightMapperModel> lstUserBusinessRightMapper;
        UserInvitationModel userInvitationModel;
        try {

            if(requestMessage.businessID == null || requestMessage.businessID == 0) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SELECT_A_BUSINESS;
                Core.clientMessage.get().userMessage = MessageConstant.SELECT_A_BUSINESS;
                return responseMessage;
            }

            userInvitationModel = Core.getRequestObject(requestMessage, UserInvitationModel.class);
            userInvitationModel.setBusinessID(requestMessage.businessID);

             /*Set<ConstraintViolation<UserInvitationModel>> violations = this.validator.validate(userInvitationModel);
            for (ConstraintViolation<UserInvitationModel> violation : violations) {
                log.error(violation.getMessage());
            }*/


          this.userInvitationBllManager.invite(userInvitationModel, requestMessage.businessID);
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_SEND_MAIL;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage != null)
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }else {
                    responseMessage.message = MessageConstant.FAILED_TO_SEND_USER_INVITATION;
                }
                this.rollBack();
            }
        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.OPERATION_FAILED, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            this.rollBack();
            log.error("UserInvitationServiceManager -> invite got exception");
        }
        return responseMessage;
    }

    public ResponseMessage reInvite(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        UserInvitationModel userInvitationModel;
        try {

            if(requestMessage.businessID == null || requestMessage.businessID == 0) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SELECT_A_BUSINESS;
                Core.clientMessage.get().userMessage = MessageConstant.SELECT_A_BUSINESS;
                return responseMessage;
            }

            userInvitationModel = Core.getRequestObject(requestMessage, UserInvitationModel.class);
            userInvitationModel.setBusinessID(requestMessage.businessID);

             /*Set<ConstraintViolation<UserInvitationModel>> violations = this.validator.validate(userInvitationModel);
            for (ConstraintViolation<UserInvitationModel> violation : violations) {
                log.error(violation.getMessage());
            }*/
             this.userInvitationBllManager.reInvite(userInvitationModel, requestMessage.businessID);
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_SEND_MAIL;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage != null)
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }else {
                    responseMessage.message = MessageConstant.FAILED_TO_SEND_USER_INVITATION;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.OPERATION_FAILED, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            this.rollBack();
            log.error("UserInvitationServiceManager -> reInvite got exception");
        }
        return responseMessage;
    }

    public ResponseMessage removeInvitation(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        UserInvitationModel userInvitationModel;
        try {

            if(requestMessage.businessID == null || requestMessage.businessID == 0) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SELECT_A_BUSINESS;
                Core.clientMessage.get().userMessage = MessageConstant.SELECT_A_BUSINESS;
                return responseMessage;
            }

            userInvitationModel = Core.getRequestObject(requestMessage, UserInvitationModel.class);

             /*Set<ConstraintViolation<UserInvitationModel>> violations = this.validator.validate(userInvitationModel);
            for (ConstraintViolation<UserInvitationModel> violation : violations) {
                log.error(violation.getMessage());
            }*/

            this.userInvitationBllManager.removeInvitation(userInvitationModel, requestMessage.businessID);
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_REMOVE_USER_INVITATION;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage != null)
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }else {
                    responseMessage.message = MessageConstant.FAILED_TO_REMOVE_USER_INVITATION;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.OPERATION_FAILED, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            this.rollBack();
            log.error("UserInvitationServiceManager -> removeInvitation got exception");

        }
        return responseMessage;
    }

    public ResponseMessage createUserWithBusinessId(RequestMessage requestMessage) {

        ResponseMessage responseMessage = new ResponseMessage();
        VMTokenModel tokenModel;
        try {

            tokenModel = Core.getRequestObject(requestMessage,VMTokenModel.class);

            /*Set<ConstraintViolation<VMTokenModel>> violations = this.validator.validate(tokenModel);
            for (ConstraintViolation<VMTokenModel> violation : violations) {
                log.error(violation.getMessage());
            }*/

            this.userBllManager.createUserWithBusinessId(tokenModel);

            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SIGN_UP_SUCCESSFULLY;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage != null)
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }else {
                    responseMessage.message = MessageConstant.SIGN_UP_FAILED;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.OPERATION_FAILED, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            this.rollBack();
            log.error("UserInvitationServiceManager -> createUserWithBusinessId got exception");
        }
        return responseMessage;
    }

    public ResponseMessage getUserByToken(RequestMessage requestMessage) {

        ResponseMessage responseMessage = new ResponseMessage();
        UserInvitationModel userInvitationModel ;
        InvitedUserResponse invitedUserResponse = new InvitedUserResponse();
        UserModel userModel;
        try {

            userInvitationModel = Core.getRequestObject(requestMessage,UserInvitationModel.class);

            /*Set<ConstraintViolation<UserInvitationModel>> violations = this.validator.validate(userInvitationModel);
            for (ConstraintViolation<UserInvitationModel> violation : violations) {
                log.error(violation.getMessage());
            }*/


            //select default db
            this.setDefaultDateBase();

            //check invitation
            invitedUserResponse.userInvitationModel = this.userInvitationBllManager.getInvitedUserByToken(userInvitationModel.getToken());

            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_GET_INVITED_USER;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage != null)
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }else {
                    responseMessage.message = MessageConstant.FAILED_TO_GET_INVITED_USER;
                }
            }

            //check is system user
            if(invitedUserResponse.userInvitationModel != null){
                userModel = this.userBllManager.getById(invitedUserResponse.userInvitationModel.getUserID(), TillBoxAppEnum.Status.Active.get());
                if( userModel != null)
                {
                    invitedUserResponse.isSystemUser = true;

                    //if system user create relation with this business
                    this.userBllManager.createSystemUserRelationWithInvitedBusiness(invitedUserResponse.userInvitationModel,userModel);

                    if(Core.clientMessage.get().messageCode == null)
                    {
                        responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                        responseMessage.message = MessageConstant.SUCCESSFULLY_SAVE_USER;
                        this.commit();
                    }else
                    {
                        responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        if(Core.clientMessage.get().userMessage != null)
                        {
                            responseMessage.message = Core.clientMessage.get().userMessage;
                        }else {
                            responseMessage.message = MessageConstant.FAILED_TO_SAVE_USER;
                        }
                        this.rollBack();
                    }

                }
            }

            responseMessage.responseObj = invitedUserResponse;


        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.OPERATION_FAILED, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            log.error("UserInvitationServiceManager -> getUserByToken got exception");
            this.rollBack();
        }
        return responseMessage;
    }


    private class InvitedUserResponse
    {
       public UserInvitationModel userInvitationModel = new UserInvitationModel();
       public Boolean isSystemUser = false;
    }
}
