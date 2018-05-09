/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 09/03/2018
 * Time: 10:51
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.bll.manager.AddressTypeBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.models.AddressTypeModel;
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
public class AddressTypeServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(AddressTypeServiceManager.class);
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Autowired
    private AddressTypeBllManager addressTypeBllManager;

    public ResponseMessage saveAddressType(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        AddressTypeModel addressTypeModel = new AddressTypeModel();
        try {
            addressTypeModel = Core.getRequestObject(requestMessage, AddressTypeModel.class);

            /*Set<ConstraintViolation<AddressTypeModel>> violations = this.validator.validate(addressTypeModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            addressTypeModel = this.addressTypeBllManager.saveOrUpdate(addressTypeModel);

            responseMessage.responseObj = addressTypeModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.ADDRESS_TYPE_SAVE_SUCCESSFULLY;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.ADDRESS_TYPE_SAVE_FAILED;
                }else
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("AddressTypeServiceManager -> saveAddressType got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }
    public ResponseMessage searchAddressType(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<AddressTypeModel> lstAddressTypeModel = new ArrayList<>();
        AddressTypeModel addressTypeModel = new AddressTypeModel();
        try {
            addressTypeModel = Core.getRequestObject(requestMessage, AddressTypeModel.class);

            lstAddressTypeModel = this.addressTypeBllManager.searchAddressType(addressTypeModel);

            responseMessage.responseObj = lstAddressTypeModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.ADDRESS_TYPE_GET_SUCCESSFULLY;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.ADDRESS_TYPE_GET_FAILED;
            }

        } catch (Exception ex) {
            log.error("AddressTypeServiceManager -> searchAddressType got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }
    public ResponseMessage getAddressTypeByID(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        AddressTypeModel addressTypeModel = new AddressTypeModel();
        try {
            addressTypeModel = Core.getRequestObject(requestMessage, AddressTypeModel.class);

            addressTypeModel = this.addressTypeBllManager.getAddressTypeByID(addressTypeModel.getAddressTypeID());

            responseMessage.responseObj = addressTypeModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.ADDRESS_TYPE_GET_SUCCESSFULLY;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.ADDRESS_TYPE_GET_FAILED;
            }

        } catch (Exception ex) {
            log.error("AddressTypeServiceManager -> getAddressTypeByID got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }
}
