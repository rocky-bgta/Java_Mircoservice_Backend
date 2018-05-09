/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/23/2018
 * Time: 10:29 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.bll.manager.RememberNoteBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.coreModels.RememberNoteModel;
import nybsys.tillboxweb.models.VMRememberNoteModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class RememberNoteServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(UserDefineSettingServiceManager.class);
    @Autowired
    private RememberNoteBllManager rememberNoteBllManager;

    public ResponseMessage save(RequestMessage requestMessage) {
        ResponseMessage responseMessage;


        try {
            responseMessage = Core.buildDefaultResponseMessage();

            VMRememberNoteModel vmRememberNoteModel = Core.getRequestObject(requestMessage, VMRememberNoteModel.class);

            for (RememberNoteModel rememberNoteModel :vmRememberNoteModel.lstRememberNoteModel) {
                rememberNoteModel = this.rememberNoteBllManager.saveRememberNote(rememberNoteModel);
            }

            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
            responseMessage.responseObj = vmRememberNoteModel;
            responseMessage.message = MessageConstant.SAVE_USER_DEFINE_SETTING_DETAIL_SUCCESSFUL;


        } catch (Exception ex) {
            log.error("RememberNoteServiceManager -> save remember  note got exception : " + ex.getMessage());
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


    public ResponseMessage search(RequestMessage requestMessage) {
        List<RememberNoteModel> lstRememberNoteModel;
        ResponseMessage responseMessage;
        RememberNoteModel rememberNoteModel;
        try {
            responseMessage = Core.buildDefaultResponseMessage();
            rememberNoteModel = Core.getRequestObject(requestMessage, RememberNoteModel.class);

            lstRememberNoteModel = rememberNoteBllManager.getAllByConditions(rememberNoteModel);

            for (RememberNoteModel rememberNoteModel1 : lstRememberNoteModel)
            {
                rememberNoteBllManager.delete(rememberNoteModel1);
            }

        } catch (Exception ex) {
            log.error("RememberNoteServiceManager -> search remember note got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            this.WriteExceptionLog(ex);
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
        }
        return responseMessage;
    }



}
