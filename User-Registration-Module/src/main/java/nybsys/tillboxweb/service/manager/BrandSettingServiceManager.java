package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.bll.manager.BrandingSettingBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.models.BrandingModel;
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
public class BrandSettingServiceManager extends BaseService {

    private static final Logger log = LoggerFactory.getLogger(Core.class);
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    
    @Autowired
    private BrandingSettingBllManager brandingSettingBllManager;

    public ResponseMessage save(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        BrandingModel brandingModel;
        try {

            if(requestMessage.businessID == null || requestMessage.businessID == 0) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SELECT_A_BUSINESS;
                Core.clientMessage.get().userMessage = MessageConstant.SELECT_A_BUSINESS;
                return responseMessage;
            }

            brandingModel = Core.getRequestObject(requestMessage, BrandingModel.class);
            brandingModel.setBusinessID(requestMessage.businessID);

 /*           Set<ConstraintViolation<BrandingModel>> violations = this.validator.validate(brandingModel);
            for (ConstraintViolation<BrandingModel> violation : violations) {
                log.error(violation.getMessage());
            }*/

            brandingModel = this.brandingSettingBllManager.saveOrUpdateBranding(brandingModel);

            responseMessage.responseObj = brandingModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.BRANDING_SAVE_SUCCESSFULLY;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage != null)
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }else {
                    responseMessage.message = MessageConstant.BRANDING_SAVE_FAILED;
                }
                this.rollBack();
            }
        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.OPERATION_FAILED, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            this.rollBack();
            log.error("BrandingServiceManager -> save got exception");
        }
        return responseMessage;
    }
    

    public ResponseMessage getByBusinessID(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        BrandingModel brandingModel = new BrandingModel();
        List<BrandingModel> lstBrandingModel;
        try {
            if(requestMessage.businessID == null || requestMessage.businessID == 0) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SELECT_A_BUSINESS;
                Core.clientMessage.get().userMessage = MessageConstant.SELECT_A_BUSINESS;
                return responseMessage;
            }

            BrandingModel whereCondition = new BrandingModel();
            whereCondition.setBusinessID(requestMessage.businessID);
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());
            
            lstBrandingModel = this.brandingSettingBllManager.getAllByConditions(whereCondition);
            if (lstBrandingModel.size() > 0) {

                brandingModel = lstBrandingModel.get(0);
            }else{
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.BRANDING_GET_FAILED;
                return responseMessage;
            }
            responseMessage.responseObj = brandingModel;
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.OPERATION_FAILED, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            log.error("BrandingServiceManager -> getByBusinessID got exception");
        }
        return responseMessage;
    }
}
