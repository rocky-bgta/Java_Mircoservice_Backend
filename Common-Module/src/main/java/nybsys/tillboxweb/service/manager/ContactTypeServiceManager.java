/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 09/03/2018
 * Time: 10:53
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.bll.manager.ContactTypeBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.models.ContactTypeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ContactTypeServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(ContactTypeServiceManager.class);
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Autowired
    private ContactTypeBllManager contactTypeBllManager;

    public ResponseMessage saveContactType(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        ContactTypeModel contactTypeModel = new ContactTypeModel();
        try {
            contactTypeModel = Core.getRequestObject(requestMessage, ContactTypeModel.class);

            /*Set<ConstraintViolation<ContactTypeModel>> violations = this.validator.validate(contactTypeModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            contactTypeModel = this.contactTypeBllManager.saveOrUpdate(contactTypeModel);

            responseMessage.responseObj = contactTypeModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.CONTACT_TYPE_SAVE_SUCCESSFULLY;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.CONTACT_TYPE_SAVE_FAILED;
                }else
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("ContactTypeServiceManager -> saveContactType got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }
    public ResponseMessage searchContactType(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<ContactTypeModel> lstContactTypeModel = new ArrayList<>();
        ContactTypeModel contactTypeModel = new ContactTypeModel();
        try {
            contactTypeModel = Core.getRequestObject(requestMessage, ContactTypeModel.class);

            lstContactTypeModel = this.contactTypeBllManager.searchContactType(contactTypeModel);

            responseMessage.responseObj = lstContactTypeModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.CONTACT_TYPE_GET_SUCCESSFULLY;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.CONTACT_TYPE_GET_FAILED;
            }

        } catch (Exception ex) {
            log.error("ContactTypeServiceManager -> searchContactType got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }
    public ResponseMessage getContactTypeByID(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        ContactTypeModel contactTypeModel = new ContactTypeModel();
        try {
            contactTypeModel = Core.getRequestObject(requestMessage, ContactTypeModel.class);

            contactTypeModel = this.contactTypeBllManager.getContactTypeByID(contactTypeModel.getContactTypeID());

            responseMessage.responseObj = contactTypeModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.CONTACT_TYPE_GET_SUCCESSFULLY;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.CONTACT_TYPE_GET_FAILED;
            }

        } catch (Exception ex) {
            log.error("ContactTypeServiceManager -> getContactTypeByID got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }
}
