/**
 * Created By: Md. Abdul Hannan
 * Created Date: 4/16/2018
 * Time: 10:49 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.bll.manager.UserDefineFieldBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.models.UserDefineFieldModel;
import nybsys.tillboxweb.models.VMUserDefineField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserDefineFieldServiceManager extends BaseService {

    private static final Logger log = LoggerFactory.getLogger(Core.class);
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    private UserDefineFieldBllManager UserDefineFieldBllManager = new UserDefineFieldBllManager();

    public ResponseMessage save(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        VMUserDefineField vmUserDefineField = new VMUserDefineField();
        try {
            if (requestMessage.businessID == null || requestMessage.businessID == 0) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SELECT_A_BUSINESS;
                Core.clientMessage.get().userMessage = MessageConstant.SELECT_A_BUSINESS;
                return responseMessage;
            }

            vmUserDefineField = Core.getRequestObject(requestMessage, VMUserDefineField.class);



            /* Set<ConstraintViolation<CurrencyModel>> violations = this.validator.validate(currencyModel);
             for (ConstraintViolation<BusinessModel> violation : violations) {
                 log.error(violation.getMessage());
             }*/

            vmUserDefineField.lstUserDefineField = this.UserDefineFieldBllManager.saveUserDefineField(vmUserDefineField.lstUserDefineField, requestMessage.businessID);

            responseMessage.responseObj = vmUserDefineField;
////
            if (Core.clientMessage.get().messageCode == null) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.USER_DEFINE_FIELD_SAVE_SUCCESSFULLY;
                this.commit();
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (Core.clientMessage.get().userMessage != null) {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                } else {
                    responseMessage.message = MessageConstant.USER_DEFINE_FIELD_SAVE_FAILED;
                }
                this.rollBack();
            }
        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.OPERATION_FAILED, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            this.rollBack();
            log.error("UserDefineFieldServiceManager -> save got exception");

        }
        return responseMessage;
    }


    public ResponseMessage search(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        UserDefineFieldModel UserDefineFieldModel = new UserDefineFieldModel();
        List<UserDefineFieldModel> UserDefineFieldModels;
        try {
            UserDefineFieldModel = Core.getRequestObject(requestMessage, UserDefineFieldModel.class);
            UserDefineFieldModel.setBusinessID(requestMessage.businessID);

            UserDefineFieldModels = this.UserDefineFieldBllManager.searchUserDefineField(UserDefineFieldModel);
            responseMessage.responseObj = UserDefineFieldModels;
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.OPERATION_FAILED, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            log.error("UserDefineFieldServiceManager -> search got exception");
        }
        return responseMessage;
    }


}
