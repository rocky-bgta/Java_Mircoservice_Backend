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
import nybsys.tillboxweb.bll.manager.VATRateBllManager;
import nybsys.tillboxweb.bll.manager.VATSystemBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.coreModels.VATRateModel;
import nybsys.tillboxweb.coreModels.VATSystemModel;
import nybsys.tillboxweb.models.VMVATSystem;
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
public class VATSystemServiceManager extends BaseService {

    private static final Logger log = LoggerFactory.getLogger(Core.class);
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    private VATSystemBllManager VATSystemBllManager = new VATSystemBllManager();
    private VATRateBllManager vatRateBllManager = new VATRateBllManager();

    public ResponseMessage save(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        VATSystemModel vatSystemModel = new VATSystemModel();
        VMVATSystem vmvatSystem = new VMVATSystem();
        try {
            if (requestMessage.businessID == null || requestMessage.businessID == 0) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SELECT_A_BUSINESS;
                Core.clientMessage.get().userMessage = MessageConstant.SELECT_A_BUSINESS;
                return responseMessage;
            }

            vmvatSystem = Core.getRequestObject(requestMessage, VMVATSystem.class);

            /* Set<ConstraintViolation<CurrencyModel>> violations = this.validator.validate(currencyModel);
             for (ConstraintViolation<BusinessModel> violation : violations) {
                 log.error(violation.getMessage());
             }*/

            vatSystemModel = this.VATSystemBllManager.saveVATSystem(vmvatSystem, requestMessage.businessID);

            responseMessage.responseObj = vatSystemModel;

            if (Core.clientMessage.get().messageCode == null) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.VAT_SETTING_SAVE_SUCCESSFULLY;
                this.commit();
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (Core.clientMessage.get().userMessage != null) {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                } else {
                    responseMessage.message = MessageConstant.VAT_SETTING_SAVE_FAILED;
                }
                this.rollBack();
            }
        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.OPERATION_FAILED, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            this.rollBack();
            log.error("VATSystemServiceManager -> save got exception");

        }
        return responseMessage;
    }


    public ResponseMessage search(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        VATSystemModel vatSystemModel = new VATSystemModel();
        VMVATSystem vmvatSystem = new VMVATSystem();
        List<VATSystemModel> VATSystemModels;
        try {
            vatSystemModel = Core.getRequestObject(requestMessage, VATSystemModel.class);

            vatSystemModel.setBusinessID(requestMessage.businessID);
            VATRateModel vatRateModel = new VATRateModel();
            vatRateModel.setBusinessID(requestMessage.businessID);

            VATSystemModels = this.VATSystemBllManager.searchVATSystem(vatSystemModel);
            if (VATSystemModels.size() > 0) {
                vmvatSystem.vatSystem = VATSystemModels.get(0);
                vmvatSystem.lstVATRate = this.vatRateBllManager.getAllByConditions(vatRateModel);
            }


            responseMessage.responseObj = vmvatSystem;
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.OPERATION_FAILED, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            log.error("VATSystemServiceManager -> search got exception");
        }
        return responseMessage;
    }


}
