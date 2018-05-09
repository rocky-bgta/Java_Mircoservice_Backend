package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.bll.manager.GstSettingBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.models.GstSettingModel;
import nybsys.tillboxweb.models.VMgstSettings;
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
public class GstSettingServiceManager extends BaseService {

    private static final Logger log = LoggerFactory.getLogger(Core.class);
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    
    @Autowired
    private GstSettingBllManager gstSettingBllManager;

    public ResponseMessage save(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        GstSettingModel gstSettingModel;
        try {

            if(requestMessage.businessID == null || requestMessage.businessID == 0) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SELECT_A_BUSINESS;
                Core.clientMessage.get().userMessage = MessageConstant.SELECT_A_BUSINESS;
                return responseMessage;
            }

            gstSettingModel = Core.getRequestObject(requestMessage, GstSettingModel.class);
            gstSettingModel.setBusinessID(requestMessage.businessID);
            gstSettingModel.setUserID(requestMessage.userID);

 /*           Set<ConstraintViolation<GstSettingModel>> violations = this.validator.validate(gstSettingModel);
            for (ConstraintViolation<GstSettingModel> violation : violations) {
                log.error(violation.getMessage());
            }*/

            gstSettingModel = this.gstSettingBllManager.saveOrUpdateGstSetting(gstSettingModel);

            responseMessage.responseObj = gstSettingModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.GST_SETTINGS_SAVE_SUCCESSFULLY;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage != null)
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }else {
                    responseMessage.message = MessageConstant.GST_SETTINGS_SAVE_FAILED;
                }
                this.rollBack();
            }
        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.OPERATION_FAILED, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            this.rollBack();
            log.error("GstSettingServiceManager -> save got exception");
        }
        return responseMessage;
    }

    public ResponseMessage getByID(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        GstSettingModel gstSettingModel;
        try {
            gstSettingModel = Core.getRequestObject(requestMessage, GstSettingModel.class);

            gstSettingModel = this.gstSettingBllManager.getById(gstSettingModel.getGstSettingsID());
            responseMessage.responseObj = gstSettingModel;
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.OPERATION_FAILED, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            log.error("GstSettingServiceManager -> getByID got exception");
        }
        return responseMessage;
    }

    public ResponseMessage getByBusinessID(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();

        List<GstSettingModel> lstGstSettingModel;
        VMgstSettings vMgstSettings = new VMgstSettings();
        try {
            if(requestMessage.businessID == null || requestMessage.businessID == 0) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SELECT_A_BUSINESS;
                Core.clientMessage.get().userMessage = MessageConstant.SELECT_A_BUSINESS;
                return responseMessage;
            }

            GstSettingModel whereCondition = new GstSettingModel();
            whereCondition.setBusinessID(requestMessage.businessID);
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());
            
            lstGstSettingModel = this.gstSettingBllManager.getAllByConditions(whereCondition);
            if (lstGstSettingModel.size() > 0) {
                
                vMgstSettings.gstSettingModel = lstGstSettingModel.get(0);
            } else {
                vMgstSettings = new VMgstSettings();
                responseMessage.responseObj = vMgstSettings;
            }
            responseMessage.responseObj = vMgstSettings;
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.OPERATION_FAILED, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            log.error("GstSettingServiceManager -> getByBusinessID got exception");
        }
        return responseMessage;
    }
}
