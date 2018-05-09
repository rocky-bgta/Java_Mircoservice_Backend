package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.Enum.EnumsOfUserRegistrationModule;
import nybsys.tillboxweb.bll.manager.TaxCodeBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.models.TaxCodeModel;
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
public class TaxCodeServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceManager.class);

    @Autowired
    private TaxCodeBllManager taxCodeBllManager;


    public ResponseMessage saveTaxCode(RequestMessage requestMessage) {
       ResponseMessage responseMessage = new ResponseMessage();
        TaxCodeModel taxCodeModel = new TaxCodeModel();

        try {
            taxCodeModel = Core.getRequestObject(requestMessage, TaxCodeModel.class);

             /*Set<ConstraintViolation<TaxCodeModel>> violations = this.validator.validate(taxCodeModel);
            for (ConstraintViolation<TaxCodeModel> violation : violations) {
                log.error(violation.getMessage());
            }*/

            this.taxCodeBllManager.saveOrUpdate(taxCodeModel);

            responseMessage.responseObj = taxCodeModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SAVE_TAX_CODE_SUCCESSFULLY;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage != null)
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }else {
                    responseMessage.message = MessageConstant.FAILED_TO_SAVE_TAX_CODE;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            this.rollBack();
            log.error("TaxCodeServiceManager -> saveTaxCode got exception");
        }
        return responseMessage;
    }

    public ResponseMessage getTaxCodeByTaxType(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {

            responseMessage.responseObj = EnumsOfUserRegistrationModule.TaxType.getMAP();
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            log.error("TaxCodeServiceManager -> getTaxCodeByTaxType got exception");

        }

        return responseMessage;
    }

    public ResponseMessage getTaxCode(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {

            responseMessage.responseObj =  this.taxCodeBllManager.getAllTaxCode();
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_GET_TAX_CODE;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage != null)
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }else {
                    responseMessage.message = MessageConstant.FAILED_TO_GET_TAX_CODE;
                }
            }
        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            log.error("TaxCodeServiceManager -> getTaxCode got exception");
        }
        return responseMessage;
    }

}
