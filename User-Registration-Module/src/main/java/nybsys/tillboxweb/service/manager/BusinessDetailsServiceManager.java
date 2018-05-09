
package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.Utils.TillBoxUtils;
import nybsys.tillboxweb.bll.manager.BusinessAddressBllManager;
import nybsys.tillboxweb.bll.manager.BusinessContactBllManager;
import nybsys.tillboxweb.bll.manager.BusinessDetailBllManager;
import nybsys.tillboxweb.bll.manager.CountryBllManager;
import nybsys.tillboxweb.broker.client.CallBack;
import nybsys.tillboxweb.broker.client.PublisherForWorker;
import nybsys.tillboxweb.broker.client.SubscriberForWorker;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.constant.WorkerSubscriptionConstants;
import nybsys.tillboxweb.coreModels.FinancialYearModel;
import nybsys.tillboxweb.models.BusinessDetailsModel;
import nybsys.tillboxweb.models.VMBusinessDetail;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class BusinessDetailsServiceManager extends BaseService {

    private static final Logger log = LoggerFactory.getLogger(Core.class);
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    
    @Autowired
    private BusinessDetailBllManager businessDetailBllManager;
    @Autowired
    private BusinessAddressBllManager businessAddressBllManager;

    @Autowired
    private BusinessContactBllManager businessContactBllManager;

    @Autowired
    private CountryBllManager countryBllManager;

    public ResponseMessage save(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        VMBusinessDetail vmBusinessDetail;
        try {
            if(requestMessage.businessID == null || requestMessage.businessID == 0) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SELECT_A_BUSINESS;
                Core.clientMessage.get().userMessage = MessageConstant.SELECT_A_BUSINESS;
                return responseMessage;
            }

            vmBusinessDetail = Core.getRequestObject(requestMessage, VMBusinessDetail.class);


          /* Set<ConstraintViolation<BusinessDetailsModel>> violations = this.validator.validate(this.accessRightModel);
           for (ConstraintViolation<BusinessDetailsModel> violation : violations) {
              log.error(violation.getMessage());
           }*/

            vmBusinessDetail = this.businessDetailBllManager.saveVMBusinessDetail(vmBusinessDetail,requestMessage.businessID);
            responseMessage.responseObj = vmBusinessDetail;

            responseMessage.message = MessageConstant.BUSINESS_DETAIL_SAVE_SUCCESSFULLY;
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
            this.commit();

        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            this.rollBack();
            log.error("BusinessDetailsServiceManager -> save got exception");
        }
        return responseMessage;
    }

    public ResponseMessage getByID(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        BusinessDetailsModel businessDetailsModel;
        try {
            businessDetailsModel = Core.getRequestObject(requestMessage, BusinessDetailsModel.class);

            businessDetailsModel = this.businessDetailBllManager.getById(businessDetailsModel.getBusinessDetailsID());
            responseMessage.responseObj = businessDetailsModel;
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            log.error("BusinessDetailsServiceManager -> getByID got exception");
        }
        return responseMessage;
    }

    public ResponseMessage search(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        BusinessDetailsModel businessDetailsModel;
        VMBusinessDetail vmBusinessDetail = new VMBusinessDetail();
        try {
            if(requestMessage.businessID == null || requestMessage.businessID == 0) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SELECT_A_BUSINESS;
                Core.clientMessage.get().userMessage = MessageConstant.SELECT_A_BUSINESS;
                return responseMessage;
            }

            businessDetailsModel = Core.getRequestObject(requestMessage, BusinessDetailsModel.class);
            businessDetailsModel.setBusinessID(requestMessage.businessID);
            vmBusinessDetail=this.businessDetailBllManager.searchVMBusinessDetail(businessDetailsModel);

            //put financial year of this business
            vmBusinessDetail.lstFinancialYearModel = getAllFinancialYear(requestMessage.businessID);
            responseMessage.responseObj = vmBusinessDetail;
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            log.error("BusinessDetailsServiceManager -> search got exception");
        }
        return responseMessage;

    }

    private List<FinancialYearModel> getAllFinancialYear(Integer businessID) {
        List lstFinancialYear = new ArrayList<>();
        List <FinancialYearModel> finalList = new ArrayList<>();

        MqttClient mqttClient;
        RequestMessage reqMessForWorker;
        ResponseMessage responseMessage;
        boolean workCompleteWithInAllowTime;
        try {

            Object lockObject = new Object();
            this.barrier = TillBoxUtils.getBarrier(1,lockObject);

            CallBack callBack;
            reqMessForWorker = Core.getDefaultWorkerRequestMessage();

            String pubTopic = WorkerSubscriptionConstants.WORKER_ACCOUNTING_MODULE_TOPIC;
            reqMessForWorker.brokerMessage.serviceName = "api/financialYear/get";
            reqMessForWorker.businessID = businessID;
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
                    if(responseMessage.responseObj != null) {
                        lstFinancialYear = (List<FinancialYearModel>) responseMessage.responseObj;
                        if (lstFinancialYear.size() > 0) {
                            for (Object model : lstFinancialYear) {
                                model = Core.modelMapper.map(model, FinancialYearModel.class);
                                finalList.add((FinancialYearModel) model);
                            }

                        }
                    }

                } else {
                    //timeout
                    //TODO Implement role back logic
                }
            }
            this.closeBrokerClient(mqttClient, reqMessForWorker.brokerMessage.messageId);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("BusinessDetailsServiceManager -> inter module communication getAllFinancialYear got exception");
        }
        return finalList;
    }

}
