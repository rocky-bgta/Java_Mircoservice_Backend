/**
 * Created By: Md. Abdul Hannan
 * Created Date: 4/16/2018
 * Time: 11:49 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.bll.manager.AdditionalCompanyInformationBllManager;
import nybsys.tillboxweb.bll.manager.CompanyDetailBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.coreModels.CurrencyModel;
import nybsys.tillboxweb.models.AdditionalCompanyInformationModel;
import nybsys.tillboxweb.models.CompanyDetailModel;
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
public class AdditionalCompanyInformationModelServiceManager extends BaseService {

    private static final Logger log = LoggerFactory.getLogger(Core.class);
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    private AdditionalCompanyInformationBllManager additionalCompanyInformationBllManager = new AdditionalCompanyInformationBllManager();

    public ResponseMessage save(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        AdditionalCompanyInformationModel additionalCompanyInformationModel = new AdditionalCompanyInformationModel();
        try {
            if (requestMessage.businessID == null || requestMessage.businessID == 0) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SELECT_A_BUSINESS;
                Core.clientMessage.get().userMessage = MessageConstant.SELECT_A_BUSINESS;
                return responseMessage;
            }

            additionalCompanyInformationModel = Core.getRequestObject(requestMessage, AdditionalCompanyInformationModel.class);
            additionalCompanyInformationModel.setBusinessID(requestMessage.businessID);

            /* Set<ConstraintViolation<CurrencyModel>> violations = this.validator.validate(currencyModel);
             for (ConstraintViolation<BusinessModel> violation : violations) {
                 log.error(violation.getMessage());
             }*/

            additionalCompanyInformationModel = this.additionalCompanyInformationBllManager.saveAdditionalCompanyInformation(additionalCompanyInformationModel);

            responseMessage.responseObj = additionalCompanyInformationModel;

            if (Core.clientMessage.get().messageCode == null) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.ADDITIONAL_INFORMATION_SAVE_SUCCESSFULLY;
                this.commit();
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (Core.clientMessage.get().userMessage != null) {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                } else {
                    responseMessage.message = MessageConstant.ADDITIONAL_INFORMATION_SAVE_FAILED;
                }
                this.rollBack();
            }
        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.OPERATION_FAILED, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            this.rollBack();
            log.error("AdditionalCompanyInformationModelServiceManager -> save got exception");

        }
        return responseMessage;
    }


    public ResponseMessage search(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        AdditionalCompanyInformationModel additionalCompanyInformationModel = new AdditionalCompanyInformationModel();
        List<AdditionalCompanyInformationModel> additionalCompanyInformationModels;
        try {
            additionalCompanyInformationModel = Core.getRequestObject(requestMessage, AdditionalCompanyInformationModel.class);

            additionalCompanyInformationModel.setBusinessID(requestMessage.businessID);
            additionalCompanyInformationModels = this.additionalCompanyInformationBllManager.searchAdditionalCompanyInformation(additionalCompanyInformationModel);

            if (additionalCompanyInformationModels.size()>0)
            {
                responseMessage.responseObj = additionalCompanyInformationModels.get(0);
            }

            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.OPERATION_FAILED, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            log.error("AdditionalCompanyInformationModelServiceManager -> search got exception");
        }
        return responseMessage;
    }
}
