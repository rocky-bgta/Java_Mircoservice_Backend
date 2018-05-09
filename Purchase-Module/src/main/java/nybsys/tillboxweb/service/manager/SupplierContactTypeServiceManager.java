/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 22/02/2018
 * Time: 02:21
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.Utils.TillBoxUtils;
import nybsys.tillboxweb.broker.client.CallBack;
import nybsys.tillboxweb.broker.client.PublisherForWorker;
import nybsys.tillboxweb.broker.client.SubscriberForWorker;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.constant.WorkerSubscriptionConstants;
import nybsys.tillboxweb.coreEnum.PartyType;
import nybsys.tillboxweb.models.ContactTypeModel;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SupplierContactTypeServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(SupplierContactTypeServiceManager.class);
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    

    public ResponseMessage saveContactType(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        ContactTypeModel contactTypeModel = new ContactTypeModel();
        try {
            contactTypeModel = Core.getRequestObject(requestMessage, ContactTypeModel.class);
            contactTypeModel.setReferenceType(PartyType.Supplier.get());

            /*Set<ConstraintViolation<ContactTypeModel>> violations = this.validator.validate(contactTypeModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            String contactTypeSaveServiceLink = "api/commonModule/contactType/save";
            ResponseMessage internalResponse = interModuleCommunication(contactTypeModel,contactTypeSaveServiceLink);

            responseMessage.responseObj = internalResponse.responseObj;
            if(internalResponse.responseCode == TillBoxAppConstant.SUCCESS_CODE)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.CONTACT_TYPE_SAVE_SUCCESSFULLY;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(internalResponse.message == null) {
                    responseMessage.message = MessageConstant.CONTACT_TYPE_SAVE_FAILED;
                }else
                {
                    responseMessage.message = internalResponse.message;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("SupplierContactTypeServiceManager -> saveContactType got exception");
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
            contactTypeModel.setReferenceType(PartyType.Supplier.get());

            String contactTypeSearchServiceLink = "api/commonModule/contactType/search";
            ResponseMessage internalResponse = interModuleCommunication(contactTypeModel,contactTypeSearchServiceLink);

            responseMessage.responseObj = internalResponse.responseObj;
            if(internalResponse.responseCode == TillBoxAppConstant.SUCCESS_CODE)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.CONTACT_TYPE_GET_SUCCESSFULLY;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(internalResponse.message == null) {
                    responseMessage.message = MessageConstant.CONTACT_TYPE_GET_FAILED;
                }else
                {
                    responseMessage.message = internalResponse.message;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("SupplierContactTypeServiceManager -> searchContactType got exception");
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
            contactTypeModel.setReferenceType(PartyType.Supplier.get());

            String contactTypeSearchServiceLink = "api/commonModule/contactType/getByID";
            ResponseMessage internalResponse = interModuleCommunication(contactTypeModel,contactTypeSearchServiceLink);

            responseMessage.responseObj = internalResponse.responseObj;
            if(internalResponse.responseCode == TillBoxAppConstant.SUCCESS_CODE)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.CONTACT_TYPE_GET_SUCCESSFULLY;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(internalResponse.message == null) {
                    responseMessage.message = MessageConstant.CONTACT_TYPE_GET_FAILED;
                }else
                {
                    responseMessage.message = internalResponse.message;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("SupplierContactTypeServiceManager -> getContactTypeByID got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }
    private ResponseMessage interModuleCommunication(ContactTypeModel contactTypeModel, String serviceLink) {
        MqttClient mqttClient;
        ResponseMessage responseMessage = new ResponseMessage();
        RequestMessage reqMessForWorker;
        boolean workCompleteWithInAllowTime;
        try {

            Object lockObject = new Object();
            this.barrier = TillBoxUtils.getBarrier(1, lockObject);

            CallBack callBack;
            reqMessForWorker = Core.getDefaultWorkerRequestMessage();

            String pubTopic = WorkerSubscriptionConstants.WORKER_COMMON_TOPIC;
            reqMessForWorker.brokerMessage.serviceName = serviceLink;
            reqMessForWorker.requestObj = contactTypeModel;
            reqMessForWorker.token = Core.requestToken.get();

            SubscriberForWorker subForWorker = new SubscriberForWorker(reqMessForWorker.brokerMessage.messageId, this.barrier);
            mqttClient = subForWorker.subscribe();
            callBack = subForWorker.getCallBack();
            PublisherForWorker pubForWorker = new PublisherForWorker(pubTopic, mqttClient);
            pubForWorker.publishedMessageToWorker(reqMessForWorker);

            synchronized (lockObject) {
                long startTime = System.nanoTime();
                lockObject.wait(allowedTime);
                workCompleteWithInAllowTime = this.isResponseWithInAllowedTime(startTime);

                if (workCompleteWithInAllowTime) {
                    responseMessage = callBack.getResponseMessage();
                } else {
                    //timeout
                    //TODO Implement role back logic
                }
            }
            this.closeBrokerClient(mqttClient, reqMessForWorker.brokerMessage.messageId);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("SupplierAddressTypeServiceManager -> interModuleCommunication got exception");
        }
        return responseMessage;
    }
}
