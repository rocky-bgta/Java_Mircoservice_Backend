/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 08/02/2018
 * Time: 10:52
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
import nybsys.tillboxweb.bll.manager.OpeningBalanceBllManager;
import nybsys.tillboxweb.broker.client.CallBack;
import nybsys.tillboxweb.broker.client.PublisherForWorker;
import nybsys.tillboxweb.broker.client.SubscriberForWorker;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.constant.WorkerSubscriptionConstants;
import nybsys.tillboxweb.coreConstant.CurrencyConstant;
import nybsys.tillboxweb.coreModels.CurrencyModel;
import nybsys.tillboxweb.models.OpeningBalanceModel;
import org.eclipse.paho.client.mqttv3.MqttClient;
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
public class OpeningBalanceServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(OpeningBalanceServiceManager.class);
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Autowired
    private OpeningBalanceBllManager openingBalanceBllManager;

    public ResponseMessage getOpeningBalanceByAccountID(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        OpeningBalanceModel openingBalanceModel = new OpeningBalanceModel();

        try {
            openingBalanceModel = Core.getRequestObject(requestMessage, OpeningBalanceModel.class);

            openingBalanceModel = this.openingBalanceBllManager.getOpeningBalanceByAccountID(openingBalanceModel.getAccountID());

            responseMessage.responseObj = openingBalanceModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_GET_OPENING_BALANCE;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.FAILED_TO_GET_OPENING_BALANCE;
            }

        } catch (Exception ex) {
            log.error(" OpeningBalanceServiceManager -> getOpeningBalanceByAccountID got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }

    public ResponseMessage getOpeningBalanceByBusinessID(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        OpeningBalanceModel openingBalanceModel = new OpeningBalanceModel();
        List<OpeningBalanceModel> lstOpeningBalanceModel = new ArrayList<>();
        try {
            openingBalanceModel = Core.getRequestObject(requestMessage, OpeningBalanceModel.class);

            lstOpeningBalanceModel = this.openingBalanceBllManager.getAllOpeningBalance(openingBalanceModel.getBusinessID());

            responseMessage.responseObj = lstOpeningBalanceModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_GET_OPENING_BALANCE;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.FAILED_TO_GET_OPENING_BALANCE;
            }

        } catch (Exception ex) {
            log.error(" OpeningBalanceServiceManager -> getOpeningBalanceByAccountID got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }

    public ResponseMessage save(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        OpeningBalanceModel openingBalanceModel = new OpeningBalanceModel();
        List<OpeningBalanceModel> lstOpeningBalanceModel = new ArrayList<>();
        CurrencyModel currencyModel;
        try {

            //check business is selected
            if(requestMessage.businessID == null || requestMessage.businessID == 0)
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SELECT_A_BUSINESS;
                Core.clientMessage.get().userMessage =  MessageConstant.SELECT_A_BUSINESS;
                return responseMessage;
            }

            //get base currency and exchange rate
            currencyModel = getBaseCurrency();
            if(currencyModel == null){
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = CurrencyConstant.BASE_CURRENT_NOT_FOUND;
                Core.clientMessage.get().userMessage =  CurrencyConstant.BASE_CURRENT_NOT_FOUND;
                return responseMessage;
            }

            //check entry currency is present if not base currency will be entry currency
            if(requestMessage.entryCurrencyID == null || requestMessage.entryCurrencyID == 0)
            {
                requestMessage.entryCurrencyID = currencyModel.getCurrencyID();
            }

            openingBalanceModel = Core.getRequestObject(requestMessage, OpeningBalanceModel.class);
            openingBalanceModel.setBusinessID(requestMessage.businessID);

            /*Set<ConstraintViolation<OpeningBalanceModel>> violations = this.validator.validate(openingBalanceModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            if(openingBalanceModel.getOpeningBalanceID() == null || openingBalanceModel.getOpeningBalanceID() == 0) {
                openingBalanceModel.setBaseCurrencyID(currencyModel.getCurrencyID());
                openingBalanceModel.setEntryCurrencyID(requestMessage.entryCurrencyID);
                openingBalanceModel.setBaseCurrencyAmount(openingBalanceModel.getAmount() * openingBalanceModel.getExchangeRate());
            }

            openingBalanceModel = this.openingBalanceBllManager.saveOpeningBalance(openingBalanceModel);

            responseMessage.responseObj = openingBalanceModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_SAVE_OPENING_BALANCE;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.FAILED_TO_SAVE_OPENING_BALANCE;
                }else
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error(" OpeningBalanceServiceManager -> save got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }
    public ResponseMessage delete(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        OpeningBalanceModel openingBalanceModel = new OpeningBalanceModel();
        try {
            openingBalanceModel = Core.getRequestObject(requestMessage, OpeningBalanceModel.class);

            /*Set<ConstraintViolation<OpeningBalanceModel>> violations = this.validator.validate(openingBalanceModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            Boolean resultFlag = this.openingBalanceBllManager.deleteOpeningBalance(openingBalanceModel);

            responseMessage.responseObj = resultFlag;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_DELETE_OPENING_BALANCE;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.FAILED_TO_DELETE_OPENING_BALANCE;
                }else
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error(" OpeningBalanceServiceManager -> delete got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }


    public CurrencyModel getBaseCurrency() {
        MqttClient mqttClient;
        ResponseMessage responseMessage = new ResponseMessage();
        CurrencyModel currencyModel = new CurrencyModel();
        RequestMessage reqMessForWorker;
        boolean workCompleteWithInAllowTime;
        try {

            Object lockObject = new Object();
            this.barrier = TillBoxUtils.getBarrier(1, lockObject);

            CallBack callBack;
            reqMessForWorker = Core.getDefaultWorkerRequestMessage();

            String pubTopic = WorkerSubscriptionConstants.WORKER_USER_REGISTRATION_MODULE_TOPIC;
            reqMessForWorker.brokerMessage.serviceName = "api/currency/getBaseCurrency";
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
                    if (responseMessage.responseObj != null)
                    {
                        currencyModel = Core.jsonMapper.convertValue(responseMessage.responseObj,CurrencyModel.class);
                    }else {
                        currencyModel = null;
                    }
                } else {
                    //timeout
                    //TODO Implement role back logic
                    currencyModel = null;
                }
            }
            this.closeBrokerClient(mqttClient, reqMessForWorker.brokerMessage.messageId);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("OpeningBalanceServiceManager -> inter module communication getBaseCurrencyAndExchangeRate got exception");
        }
        return currencyModel;
    }

}
