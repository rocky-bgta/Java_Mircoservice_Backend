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
import nybsys.tillboxweb.bll.manager.DocumentMessageBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.models.DocumentMessageModel;
import nybsys.tillboxweb.models.VMDocumentMessage;
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
public class DocumentMessageServiceManager extends BaseService {

    private static final Logger log = LoggerFactory.getLogger(Core.class);
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    private DocumentMessageBllManager DocumentMessageBllManager = new DocumentMessageBllManager();

    public ResponseMessage save(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        DocumentMessageModel documentMessageModel = new DocumentMessageModel();
        VMDocumentMessage vmDocumentMessage = new VMDocumentMessage();
        try {
            if (requestMessage.businessID == null || requestMessage.businessID == 0) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SELECT_A_BUSINESS;
                Core.clientMessage.get().userMessage = MessageConstant.SELECT_A_BUSINESS;
                return responseMessage;
            }

            vmDocumentMessage = Core.getRequestObject(requestMessage, VMDocumentMessage.class);
            documentMessageModel.setBusinessID(requestMessage.businessID);

            /* Set<ConstraintViolation<CurrencyModel>> violations = this.validator.validate(currencyModel);
             for (ConstraintViolation<BusinessModel> violation : violations) {
                 log.error(violation.getMessage());
             }*/

            vmDocumentMessage.lstDocumentMessage = this.DocumentMessageBllManager.saveDocumentMessage(vmDocumentMessage.lstDocumentMessage, requestMessage.businessID);

            responseMessage.responseObj = vmDocumentMessage;

            if (Core.clientMessage.get().messageCode == null) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.STATEMENT_MESSAGE_SAVE_SUCCESSFULLY;
                this.commit();
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (Core.clientMessage.get().userMessage != null) {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                } else {
                    responseMessage.message = MessageConstant.STATEMENT_MESSAGE_SAVE_FAILED;
                }
                this.rollBack();
            }
        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.OPERATION_FAILED, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            this.rollBack();
            log.error("DocumentMessageServiceManager -> save got exception");

        }
        return responseMessage;
    }


    public ResponseMessage search(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        DocumentMessageModel documentMessageModel = new DocumentMessageModel();
        List<DocumentMessageModel> documentMessageModels;
        try {
            documentMessageModel = Core.getRequestObject(requestMessage, DocumentMessageModel.class);
            documentMessageModel.setBusinessID(requestMessage.businessID);

            documentMessageModels = this.DocumentMessageBllManager.searchDocumentMessage(documentMessageModel);
            responseMessage.responseObj = documentMessageModels;
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.OPERATION_FAILED, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            log.error("DocumentMessageServiceManager -> search got exception");
        }
        return responseMessage;
    }


}
