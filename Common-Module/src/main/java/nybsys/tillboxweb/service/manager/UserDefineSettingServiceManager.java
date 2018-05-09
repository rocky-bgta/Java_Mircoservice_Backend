/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 14-Feb-18
 * Time: 3:24 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.bll.manager.UserDefineSettingBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.models.UserDefineSettingModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserDefineSettingServiceManager extends BaseService {

    private static final Logger log = LoggerFactory.getLogger(UserDefineSettingServiceManager.class);

    @Autowired
    private UserDefineSettingBllManager userDefineSettingBllManager;

    public ResponseMessage saveOrUpdateUserDefineSetting(RequestMessage requestMessage) {
        ResponseMessage responseMessage;
        UserDefineSettingModel userDefineSettingModel;
        try {
            responseMessage = Core.buildDefaultResponseMessage();
            userDefineSettingModel = Core.getRequestObject(requestMessage, UserDefineSettingModel.class);
            userDefineSettingModel = userDefineSettingBllManager.saveOrUpdateUserDefineSetting(userDefineSettingModel);

            responseMessage.responseCode = Core.clientMessage.get().messageCode;
            responseMessage.message = Core.clientMessage.get().message;

            if (Core.clientMessage.get().messageCode == TillBoxAppConstant.SUCCESS_CODE) {
                responseMessage.responseObj = userDefineSettingModel;
                this.commit();
            } else {
                responseMessage.responseObj = requestMessage.requestObj;
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("UserDefineSettingServiceManager -> saveOrUpdateUserDefineSetting got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            this.WriteExceptionLog(ex);
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.rollBack();
        }
        return responseMessage;
    }

    public ResponseMessage getUserDefineSettingById(RequestMessage requestMessage) {
        ResponseMessage responseMessage;
        UserDefineSettingModel userDefineSettingModel;
        Integer ID;
        try {
            responseMessage = Core.buildDefaultResponseMessage();
            userDefineSettingModel = Core.getRequestObject(requestMessage, UserDefineSettingModel.class);
            ID = userDefineSettingModel.getUserDefineSettingID();
            userDefineSettingModel = userDefineSettingBllManager.getById(ID);

            if (userDefineSettingModel != null) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.responseObj = userDefineSettingModel;
                responseMessage.message = MessageConstant.FINED_THE_REQUESTED_USER_DEFINE_SETTING_SUCCESSFUL;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.responseObj = requestMessage.requestObj;
                responseMessage.message = MessageConstant.FAILED_TO_THE_FINED_REQUESTED_USER_DEFINE_SETTING;
            }

        } catch (Exception ex) {
            log.error("UserDefineSettingServiceManager -> getUserDefineSettingById got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            this.WriteExceptionLog(ex);
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.rollBack();
        }
        return responseMessage;
    }


    public ResponseMessage getAllUserDefineSetting(RequestMessage requestMessage) {
        List list;
        ResponseMessage responseMessage;
        try {
            responseMessage = Core.buildDefaultResponseMessage();
            list = userDefineSettingBllManager.getAll();
            if (list != null) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.responseObj = list;
                responseMessage.message = MessageConstant.GET_ALL_USER_DEFINE_SETTING_SUCCESSFUL;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.responseObj = requestMessage.requestObj;
                responseMessage.message = MessageConstant.FAILED_TO_GET_ALL_USER_DEFINE_SETTING;
            }
        } catch (Exception ex) {
            log.error("UserDefineSettingServiceManager -> getAllUserDefineSetting got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            this.WriteExceptionLog(ex);
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
        }
        return responseMessage;
    }

    public ResponseMessage deleteUserDefineSetting(RequestMessage requestMessage) {
        ResponseMessage responseMessage;
        UserDefineSettingModel userDefineSettingModel;
        try {
            responseMessage = Core.buildDefaultResponseMessage();
            userDefineSettingModel = Core.getRequestObject(requestMessage, UserDefineSettingModel.class);
            userDefineSettingModel = userDefineSettingBllManager.softDelete(userDefineSettingModel);

            if (userDefineSettingModel != null) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.responseObj = userDefineSettingModel;
                responseMessage.message = MessageConstant.DELETE_USER_DEFINE_SETTING_SUCCESSFUL;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.responseObj = requestMessage.requestObj;
                responseMessage.message = MessageConstant.FAILED_TO_DELETE_DEFINE_SETTING;
            }

        } catch (Exception ex) {
            log.error("UserDefineSettingServiceManager -> deleteUserDefineSetting got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            this.WriteExceptionLog(ex);
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
        }
        return responseMessage;
    }

    public ResponseMessage searchUserDefineSetting(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<UserDefineSettingModel> lstProductAttributeModel;
        UserDefineSettingModel userDefineSettingModel;
        try {
            userDefineSettingModel = Core.getRequestObject(requestMessage, UserDefineSettingModel.class);

            lstProductAttributeModel = this.userDefineSettingBllManager.searchUserDefineSetting(userDefineSettingModel);

            responseMessage.responseObj = lstProductAttributeModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.GET_ALL_USER_DEFINE_SETTING_SUCCESSFUL;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.FAILED_TO_GET_ALL_USER_DEFINE_SETTING;
            }

        } catch (Exception ex) {
            log.error("UserDefineSettingServiceManager -> searchProductAttribute got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }
}
