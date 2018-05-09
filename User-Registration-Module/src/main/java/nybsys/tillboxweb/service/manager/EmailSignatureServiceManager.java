package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.bll.manager.EmailSignatureBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.models.EmailSignatureModel;
import nybsys.tillboxweb.models.ProductTypeModel;
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
public class EmailSignatureServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(Core.class);
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    private EmailSignatureBllManager emailSignatureBllManager = new EmailSignatureBllManager();

    public ResponseMessage save(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        EmailSignatureModel emailSignatureModel;
        try {
            emailSignatureModel = Core.getRequestObject(requestMessage, EmailSignatureModel.class);

           /* Set<ConstraintViolation<ProductTypeModel>> violations = this.validator.validate(emailSignatureModel);
            for (ConstraintViolation<ProductTypeModel> violation : violations) {
                log.error(violation.getMessage());
            }*/
            emailSignatureModel.setBusinessID(requestMessage.businessID);
            emailSignatureModel = this.emailSignatureBllManager.saveEmailSignature(emailSignatureModel);

            responseMessage.responseObj = emailSignatureModel;
            if (Core.clientMessage.get().messageCode == null) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.EMAIL_SETTING_SAVE_SUCCESSFULLY;
                this.commit();
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (Core.clientMessage.get().userMessage != null) {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                } else {
                    responseMessage.message = MessageConstant.EMAIL_SETTING_SAVE_FAILED;
                }
                this.rollBack();
            }
        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.OPERATION_FAILED, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            this.rollBack();
            log.error("EmailSignatureModelServiceManager -> save got exception");
        }
        return responseMessage;
    }


    public ResponseMessage search(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        EmailSignatureModel emailSignatureModel;
        List<EmailSignatureModel> emailSignatureModels;
        try {
            emailSignatureModel = Core.getRequestObject(requestMessage, EmailSignatureModel.class);
            emailSignatureModel.setBusinessID(requestMessage.businessID);
            emailSignatureModels = this.emailSignatureBllManager.getAllByConditions(emailSignatureModel);

            responseMessage.responseObj = emailSignatureModels;
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.OPERATION_FAILED, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            log.error("EmailSignatureModelServiceManager -> search got exception");
        }
        return responseMessage;
    }
}
