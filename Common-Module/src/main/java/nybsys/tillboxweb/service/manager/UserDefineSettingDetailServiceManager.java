/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/19/2018
 * Time: 4:05 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.bll.manager.UserDefineSettingDetailBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.models.UserDefineSettingDetailModel;
import nybsys.tillboxweb.models.VMUserDetailSettingDetailModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserDefineSettingDetailServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(UserDefineSettingServiceManager.class);
    @Autowired
    private UserDefineSettingDetailBllManager userDefineSettingDetailBllManager;

    public ResponseMessage saveUserDefineSettingDetail(RequestMessage requestMessage) {
        ResponseMessage responseMessage;
        VMUserDetailSettingDetailModel vmUserDetailSettingDetail;
        List<UserDefineSettingDetailModel> lst = new ArrayList<>();
        try {
            responseMessage = Core.buildDefaultResponseMessage();
            vmUserDetailSettingDetail = Core.getRequestObject(requestMessage, VMUserDetailSettingDetailModel.class);
          //  lst = Core.getRequestObject(requestMessage, lst.getClass());

            for (UserDefineSettingDetailModel userDefineSettingDetailModel : vmUserDetailSettingDetail.lstUserDefineSettingDetailModel) {
                userDefineSettingDetailModel = this.userDefineSettingDetailBllManager.saveUserDefineSettingDetail(userDefineSettingDetailModel);
            }

            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
            responseMessage.responseObj = vmUserDetailSettingDetail;
            responseMessage.message = MessageConstant.SAVE_USER_DEFINE_SETTING_DETAIL_SUCCESSFUL;


        } catch (Exception ex) {
            log.error("UserDefineSettingDetailServiceManager -> save user define setting detail got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            this.WriteExceptionLog(ex);
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.rollBack();
            responseMessage.message = MessageConstant.FAILED_TO_SAVE_USER_DEFINE_SETTING_DETAIL;
        }
        return responseMessage;
    }


    public ResponseMessage searchUserDefineSettingDetail(RequestMessage requestMessage) {
        List list;
        ResponseMessage responseMessage;
        try {
            responseMessage = Core.buildDefaultResponseMessage();
            UserDefineSettingDetailModel userDefineSettingDetailModel = new UserDefineSettingDetailModel();
            userDefineSettingDetailModel = Core.getRequestObject(requestMessage, UserDefineSettingDetailModel.class);

            list = userDefineSettingDetailBllManager.getAllByConditions(userDefineSettingDetailModel);
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


    public ResponseMessage deleteUserDefineSettingDetail(RequestMessage requestMessage) {
        List<UserDefineSettingDetailModel> lstUserDefineSettingDetailModel;
        ResponseMessage responseMessage;
        try {
            responseMessage = Core.buildDefaultResponseMessage();
            UserDefineSettingDetailModel userDefineSettingDetailModel = new UserDefineSettingDetailModel();
            userDefineSettingDetailModel = Core.getRequestObject(requestMessage, UserDefineSettingDetailModel.class);

            lstUserDefineSettingDetailModel = userDefineSettingDetailBllManager.getAllByConditions(userDefineSettingDetailModel);
            for (UserDefineSettingDetailModel userDefineSettingDetailModel1 : lstUserDefineSettingDetailModel) {
                userDefineSettingDetailBllManager.delete(userDefineSettingDetailModel1);
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


}
