package nybsys.tillboxweb.service.manager;
import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.TillBoxWebModels.SessionModel;
import nybsys.tillboxweb.bll.manager.SessionBllManager;
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

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SessionServiceManager extends BaseService {

    private static final Logger log = LoggerFactory.getLogger(Core.class);
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    
    @Autowired
    private SessionBllManager sessionBllManager;

    public ResponseMessage save(RequestMessage requestMessage){
        ResponseMessage responseMessage = new ResponseMessage();
        SessionModel sessionModel;
        try {

            if(requestMessage.businessID == null || requestMessage.businessID == 0) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SELECT_A_BUSINESS;
                Core.clientMessage.get().userMessage = MessageConstant.SELECT_A_BUSINESS;
                return responseMessage;
            }

            sessionModel = Core.getRequestObject(requestMessage,SessionModel.class);
            sessionModel.setBusinessID(requestMessage.businessID);

            /*Set<ConstraintViolation<SessionModel>> violations = this.validator.validate(sessionModel);
            for (ConstraintViolation<SessionModel> violation : violations) {
                log.error(violation.getMessage());
            }*/

            this.setDefaultDateBase();
            sessionModel = this.sessionBllManager.saveOrUpdate(sessionModel);
            //change dbConfig
            this.selectDataBase(Core.userDataBase.get());

            responseMessage.responseObj = sessionModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SESSION_SAVE_SUCCESSFULLY;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage != null)
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }else {
                    responseMessage.message = MessageConstant.SESSION_SAVE_FAILED;
                }
                this.rollBack();
            }
        }catch (Exception ex){
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            this.rollBack();
            log.error("SessionServiceManager -> save got exception");
        }
        return responseMessage;
    }

    public ResponseMessage getByID(RequestMessage requestMessage){
        ResponseMessage responseMessage = new ResponseMessage();
        SessionModel sessionModel;
        try {

            if(requestMessage.businessID == null || requestMessage.businessID == 0) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SELECT_A_BUSINESS;
                Core.clientMessage.get().userMessage = MessageConstant.SELECT_A_BUSINESS;
                return responseMessage;
            }

            sessionModel = Core.getRequestObject(requestMessage,SessionModel.class);
            sessionModel.setBusinessID(requestMessage.businessID);

            //select default dbConfig
            this.setDefaultDateBase();

            sessionModel = this.sessionBllManager.getById(sessionModel);

            //change dbConfig
            this.selectDataBase(Core.userDataBase.get());

            responseMessage.responseObj = sessionModel;
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

        }catch (Exception ex){
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            log.error("SessionServiceManager -> getByID got exception");
        }
        return responseMessage;
    }

    public ResponseMessage search(RequestMessage requestMessage){
        ResponseMessage responseMessage = new ResponseMessage();
        SessionModel sessionModel;
        List<SessionModel> lstSessionModel;
        try {

            if(requestMessage.businessID == null || requestMessage.businessID == 0) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SELECT_A_BUSINESS;
                Core.clientMessage.get().userMessage = MessageConstant.SELECT_A_BUSINESS;
                return responseMessage;
            }

            sessionModel = Core.getRequestObject(requestMessage,SessionModel.class);
            sessionModel.setBusinessID(requestMessage.businessID);

            this.setDefaultDateBase();
            lstSessionModel = this.sessionBllManager.getAllByConditions(sessionModel);
            //change dbConfig
            this.selectDataBase(Core.userDataBase.get());

            responseMessage.responseObj = lstSessionModel;
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
        }catch (Exception ex){
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            log.error("SessionServiceManager -> search got exception");
        }
        return responseMessage;
    }
}
