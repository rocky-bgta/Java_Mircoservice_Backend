/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 26/02/2018
 * Time: 05:45
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
import nybsys.tillboxweb.bll.manager.CustomerAdjustmentBllManager;
import nybsys.tillboxweb.bll.manager.CustomerAdjustmentDetailBllManager;
import nybsys.tillboxweb.broker.client.CallBack;
import nybsys.tillboxweb.broker.client.PublisherForWorker;
import nybsys.tillboxweb.broker.client.SubscriberForWorker;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.constant.WorkerSubscriptionConstants;
import nybsys.tillboxweb.coreConstant.CurrencyConstant;
import nybsys.tillboxweb.coreEnum.*;
import nybsys.tillboxweb.coreModels.CurrencyModel;
import nybsys.tillboxweb.coreModels.JournalModel;
import nybsys.tillboxweb.coreModels.VMJournalListModel;
import nybsys.tillboxweb.models.*;
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
public class CustomerAdjustmentServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(CustomerAdjustmentServiceManager.class);
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Autowired
    CustomerAdjustmentBllManager customerAdjustmentBllManager;
    @Autowired
    CustomerAdjustmentDetailBllManager customerAdjustmentDetailBllManager;

    public ResponseMessage saveCustomerAdjustmentVM(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        ResponseMessage responseFromInterModule;
        CustomerAdjustmentModel customerAdjustmentModel;
        VMCustomerAdjustmentModel vmCustomerAdjustmentModel;
        List<CustomerAdjustmentDetailModel> lstCustomerAdjustmentDetailModel;
        CurrencyModel currencyModel;
        try {

            //get base currency and exchange rate
            currencyModel = getBaseCurrency();
            if (currencyModel == null) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = CurrencyConstant.BASE_CURRENT_NOT_FOUND;
                return responseMessage;
            }

            //check entry currency is present if not base currency will be entry currency
            if (requestMessage.entryCurrencyID == null || requestMessage.entryCurrencyID == 0) {
                requestMessage.entryCurrencyID = currencyModel.getCurrencyID();
            }

            vmCustomerAdjustmentModel = Core.getRequestObject(requestMessage, VMCustomerAdjustmentModel.class);
            customerAdjustmentModel = vmCustomerAdjustmentModel.customerAdjustmentModel;
            lstCustomerAdjustmentDetailModel = vmCustomerAdjustmentModel.lstCustomerAdjustmentDetailModel;


            /*Set<ConstraintViolation<CustomerAdjustmentModel>> violations = this.validator.validate(customerAdjustmentModel);
            if (violations.size() > 0) {
                responseMessage = this.buildResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }
            for(CustomerAdjustmentDetailModel customerAdjustmentDetailModel : lstCustomerAdjustmentDetailModel)
            {
                Set<ConstraintViolation<CustomerAdjustmentDetailModel>> violations = this.validator.validate(customerAdjustmentDetailModel);
                if (violations.size() > 0) {
                    responseMessage = this.buildResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                    return responseMessage;
                }
            }*/

            if (customerAdjustmentModel.getCustomerAdjustmentID() != null && customerAdjustmentModel.getCustomerAdjustmentID() > 0) {

                //(1)delete journal
                JournalModel deleteJournalModel = new JournalModel();
                deleteJournalModel.setReferenceType(ReferenceType.CustomerAdjustment.get());
                deleteJournalModel.setReferenceID(customerAdjustmentModel.getCustomerAdjustmentID());

                String deleteJournalServiceName = "api/journal/delete";
                responseFromInterModule = accountingInterModuleCommunication(deleteJournalServiceName, deleteJournalModel);
                if (responseFromInterModule.responseCode != 200) {
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    if (responseFromInterModule.message == null) {
                        responseMessage.message = MessageConstant.CUSTOMER_ADJUSTMENT_SAVE_FAILED;
                    } else {
                        responseMessage.message = responseFromInterModule.message;
                    }
                    this.rollBack();
                    return responseMessage;
                }

                //(2)delete detail
                this.customerAdjustmentDetailBllManager.deleteCustomerAdjustDetailByCustomerAdjustmentID(customerAdjustmentModel.getCustomerAdjustmentID());
                if (Core.clientMessage.get().messageCode != null) {
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    if (Core.clientMessage.get().userMessage == null) {
                        responseMessage.message = MessageConstant.CUSTOMER_ADJUSTMENT_SAVE_FAILED;
                    } else {
                        responseMessage.message = Core.clientMessage.get().userMessage;
                    }
                    this.rollBack();
                    return responseMessage;
                }
            }


            //(3)
            //add currency
            if (customerAdjustmentModel.getCustomerAdjustmentID() == null || customerAdjustmentModel.getCustomerAdjustmentID() == 0) {
                customerAdjustmentModel.setBaseCurrencyID(currencyModel.getCurrencyID());
                customerAdjustmentModel.setEntryCurrencyID(requestMessage.entryCurrencyID);
                customerAdjustmentModel.setBaseCurrencyAmount(customerAdjustmentModel.getTotalAmount() * customerAdjustmentModel.getExchangeRate());
            }

            customerAdjustmentModel = this.customerAdjustmentBllManager.saveOrUpdate(customerAdjustmentModel);
            if (Core.clientMessage.get().messageCode != null) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.CUSTOMER_ADJUSTMENT_SAVE_FAILED;
                } else {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
                return responseMessage;
            }

            //(4)save journal
            VMJournalListModel vmJournalListModel = new VMJournalListModel();
            JournalModel drJournalModel = new JournalModel();
            JournalModel crJournalModel = new JournalModel();

            if (customerAdjustmentModel.getEffectType().intValue() == Adjustment.Decrease.get()) {
                //dr
                drJournalModel.setDrCrIndicator(DebitCreditIndicator.Debit.get());
                drJournalModel.setBusinessID(customerAdjustmentModel.getBusinessID());
                drJournalModel.setAmount(customerAdjustmentModel.getTotalAmount());
                drJournalModel.setAccountID(DefaultCOA.AdjustmentLoss.get());
                drJournalModel.setReferenceType(ReferenceType.CustomerAdjustment.get());
                drJournalModel.setReferenceID(customerAdjustmentModel.getCustomerAdjustmentID());

                drJournalModel.setBaseCurrencyID(currencyModel.getCurrencyID());
                drJournalModel.setEntryCurrencyID(requestMessage.entryCurrencyID);
                drJournalModel.setExchangeRate(customerAdjustmentModel.getExchangeRate());
                drJournalModel.setBaseCurrencyAmount(customerAdjustmentModel.getTotalAmount() * customerAdjustmentModel.getExchangeRate());
                //cr
                crJournalModel.setDrCrIndicator(DebitCreditIndicator.Credit.get());
                crJournalModel.setBusinessID(customerAdjustmentModel.getBusinessID());
                crJournalModel.setAmount(customerAdjustmentModel.getTotalAmount());
                crJournalModel.setAccountID(DefaultCOA.TradeDebtors.get());
                crJournalModel.setReferenceType(ReferenceType.CustomerAdjustment.get());
                crJournalModel.setReferenceID(customerAdjustmentModel.getCustomerAdjustmentID());
                drJournalModel.setPartyID(customerAdjustmentModel.getCustomerID());
                drJournalModel.setPartyType(PartyType.Customer.get());

                crJournalModel.setBaseCurrencyID(currencyModel.getCurrencyID());
                crJournalModel.setEntryCurrencyID(requestMessage.entryCurrencyID);
                crJournalModel.setExchangeRate(customerAdjustmentModel.getExchangeRate());
                crJournalModel.setBaseCurrencyAmount(customerAdjustmentModel.getTotalAmount() * customerAdjustmentModel.getExchangeRate());
            } else {
                //dr
                drJournalModel.setDrCrIndicator(DebitCreditIndicator.Debit.get());
                drJournalModel.setBusinessID(customerAdjustmentModel.getBusinessID());
                drJournalModel.setAmount(customerAdjustmentModel.getTotalAmount());
                drJournalModel.setAccountID(DefaultCOA.TradeDebtors.get());
                drJournalModel.setReferenceType(ReferenceType.CustomerAdjustment.get());
                drJournalModel.setReferenceID(customerAdjustmentModel.getCustomerAdjustmentID());
                crJournalModel.setPartyID(customerAdjustmentModel.getCustomerID());
                crJournalModel.setPartyType(PartyType.Customer.get());

                drJournalModel.setBaseCurrencyID(currencyModel.getCurrencyID());
                drJournalModel.setEntryCurrencyID(requestMessage.entryCurrencyID);
                drJournalModel.setExchangeRate(customerAdjustmentModel.getExchangeRate());
                drJournalModel.setBaseCurrencyAmount(customerAdjustmentModel.getTotalAmount() * customerAdjustmentModel.getExchangeRate());
                //cr
                crJournalModel.setDrCrIndicator(DebitCreditIndicator.Credit.get());
                crJournalModel.setBusinessID(customerAdjustmentModel.getBusinessID());
                crJournalModel.setAmount(customerAdjustmentModel.getTotalAmount());
                crJournalModel.setAccountID(DefaultCOA.AdjustmentIncome.get());
                crJournalModel.setReferenceType(ReferenceType.CustomerAdjustment.get());
                crJournalModel.setReferenceID(customerAdjustmentModel.getCustomerAdjustmentID());

                crJournalModel.setBaseCurrencyID(currencyModel.getCurrencyID());
                crJournalModel.setEntryCurrencyID(requestMessage.entryCurrencyID);
                crJournalModel.setExchangeRate(customerAdjustmentModel.getExchangeRate());
                crJournalModel.setBaseCurrencyAmount(customerAdjustmentModel.getTotalAmount() * customerAdjustmentModel.getExchangeRate());
            }
            //make vm
            vmJournalListModel.lstJournalModel.add(drJournalModel);
            vmJournalListModel.lstJournalModel.add(crJournalModel);

            String saveJournalServiceName = "api/journal/save";
            responseFromInterModule = accountingInterModuleCommunication(saveJournalServiceName, vmJournalListModel);
            if (responseFromInterModule.responseCode != 200) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (responseFromInterModule.message == null) {
                    responseMessage.message = MessageConstant.CUSTOMER_ADJUSTMENT_SAVE_FAILED;
                } else {
                    responseMessage.message = responseFromInterModule.message;
                }
                this.rollBack();
                return responseMessage;
            }

            //(5)save or update customer adjustment detail+ business validation
            this.customerAdjustmentDetailBllManager.saveOrUpdateList(lstCustomerAdjustmentDetailModel, customerAdjustmentModel.getCustomerAdjustmentID());
            if (Core.clientMessage.get().messageCode != null) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.CUSTOMER_ADJUSTMENT_SAVE_FAILED;
                } else {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
                return responseMessage;
            }

            this.commit();
            responseMessage.message = MessageConstant.CUSTOMER_ADJUSTMENT_SAVE_SUCCESSFULLY;
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

        } catch (Exception ex) {
            log.error("CustomerAdjustmentServiceManager -> saveCustomerAdjustmentVM got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }

    public ResponseMessage searchCustomerAdjustmentVM(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        CustomerAdjustmentModel customerAdjustmentModel;
        List<CustomerAdjustmentModel> lstCustomerAdjustmentModel;
        List<VMCustomerAdjustmentModel> lstVmCustomerAdjustmentModel = new ArrayList<>();
        try {
            customerAdjustmentModel = Core.getRequestObject(requestMessage, CustomerAdjustmentModel.class);

            lstCustomerAdjustmentModel = this.customerAdjustmentBllManager.searchCustomerAdjustment(customerAdjustmentModel);
            for (CustomerAdjustmentModel customerAdjustmentModelObject : lstCustomerAdjustmentModel) {
                VMCustomerAdjustmentModel vmCustomerAdjustmentModel = new VMCustomerAdjustmentModel();
                vmCustomerAdjustmentModel.customerAdjustmentModel = this.customerAdjustmentBllManager.searchCustomerAdjustmentByID(customerAdjustmentModelObject.getCustomerAdjustmentID(), customerAdjustmentModelObject.getBusinessID());
                if (vmCustomerAdjustmentModel.customerAdjustmentModel != null && vmCustomerAdjustmentModel.customerAdjustmentModel.getCustomerAdjustmentID() != null) {
                    vmCustomerAdjustmentModel.lstCustomerAdjustmentDetailModel = this.customerAdjustmentDetailBllManager.searchCustomerAdjustmentDetailByAdjustmentID(vmCustomerAdjustmentModel.customerAdjustmentModel.getCustomerAdjustmentID());
                }
                if (vmCustomerAdjustmentModel.customerAdjustmentModel != null || vmCustomerAdjustmentModel.lstCustomerAdjustmentDetailModel.size() > 0) {
                    lstVmCustomerAdjustmentModel.add(vmCustomerAdjustmentModel);
                }
            }

            responseMessage.responseObj = lstVmCustomerAdjustmentModel;
            if (lstVmCustomerAdjustmentModel.size() == 0) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.CUSTOMER_ADJUSTMENT_GET_FAILED;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.CUSTOMER_ADJUSTMENT_GET_SUCCESSFULLY;
            }

        } catch (Exception ex) {
            log.error("CustomerAdjustmentServiceManager -> searchCustomerAdjustmentVM got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }

    public ResponseMessage getCustomerAdjustmentVMByID(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        CustomerAdjustmentModel customerAdjustmentModel;
        VMCustomerAdjustmentModel vmCustomerAdjustmentModel = new VMCustomerAdjustmentModel();
        try {
            customerAdjustmentModel = Core.getRequestObject(requestMessage, CustomerAdjustmentModel.class);
            Integer businessID = requestMessage.businessID;
            vmCustomerAdjustmentModel.customerAdjustmentModel = this.customerAdjustmentBllManager.searchCustomerAdjustmentByID(customerAdjustmentModel.getCustomerAdjustmentID(), businessID);
            if (vmCustomerAdjustmentModel.customerAdjustmentModel != null && vmCustomerAdjustmentModel.customerAdjustmentModel.getCustomerAdjustmentID() != null) {
                vmCustomerAdjustmentModel.lstCustomerAdjustmentDetailModel = this.customerAdjustmentDetailBllManager.searchCustomerAdjustmentDetailByAdjustmentID(vmCustomerAdjustmentModel.customerAdjustmentModel.getCustomerAdjustmentID());
            }
            responseMessage.responseObj = vmCustomerAdjustmentModel;
            if (vmCustomerAdjustmentModel.lstCustomerAdjustmentDetailModel.size() == 0 && vmCustomerAdjustmentModel.customerAdjustmentModel == null) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.CUSTOMER_ADJUSTMENT_GET_FAILED;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.CUSTOMER_ADJUSTMENT_GET_SUCCESSFULLY;
            }

        } catch (Exception ex) {
            log.error("CustomerAdjustmentServiceManager -> getCustomerAdjustmentVMByID got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }

    public ResponseMessage deleteCustomerAdjustmentAndDetail(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        CustomerAdjustmentModel customerAdjustmentModel;
        try {
            customerAdjustmentModel = Core.getRequestObject(requestMessage, CustomerAdjustmentModel.class);
            if (customerAdjustmentModel.getCustomerAdjustmentID() != null && customerAdjustmentModel.getCustomerAdjustmentID() > 0) {

                //(1)
                this.customerAdjustmentBllManager.deleteCustomerAdjustmentByCustomerAdjustmentID(customerAdjustmentModel.getCustomerAdjustmentID());
                if (Core.clientMessage.get().messageCode != null) {
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    if (Core.clientMessage.get().userMessage == null) {
                        responseMessage.message = MessageConstant.CUSTOMER_ADJUSTMENT_DELETE_FAILED;
                    } else {
                        responseMessage.message = Core.clientMessage.get().userMessage;
                    }
                    this.rollBack();
                    return responseMessage;
                }
                //(2)delete journal
                JournalModel deleteJournalModel = new JournalModel();
                deleteJournalModel.setReferenceType(DefaultCOA.AccountPayable.get());
                deleteJournalModel.setReferenceID(customerAdjustmentModel.getCustomerAdjustmentID());

                String deleteJournalServiceName = "api/journal/delete";
                ResponseMessage responseFromInterModule = accountingInterModuleCommunication(deleteJournalServiceName, deleteJournalModel);
                if (responseFromInterModule.responseCode != 200) {
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = responseFromInterModule.message;
                    if (responseFromInterModule.message == null) {
                        responseMessage.message = MessageConstant.CUSTOMER_ADJUSTMENT_DELETE_FAILED;
                    } else {
                        responseMessage.message = responseFromInterModule.message;
                    }
                    this.rollBack();
                    return responseMessage;
                }

                //(3)
                this.customerAdjustmentDetailBllManager.deleteCustomerAdjustDetailByCustomerAdjustmentID(customerAdjustmentModel.getCustomerAdjustmentID());
                if (Core.clientMessage.get().messageCode != null) {
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    if (Core.clientMessage.get().userMessage == null) {
                        responseMessage.message = MessageConstant.CUSTOMER_ADJUSTMENT_DELETE_FAILED;
                    } else {
                        responseMessage.message = Core.clientMessage.get().userMessage;
                    }
                    this.rollBack();
                    return responseMessage;
                }
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.CUSTOMER_ADJUSTMENT_DELETE_FAILED;
                this.rollBack();
                return responseMessage;
            }

            this.commit();
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
            responseMessage.message = MessageConstant.CUSTOMER_ADJUSTMENT_DELETE_SUCCESSFULLY;

        } catch (Exception ex) {
            log.error("CustomerAdjustmentServiceManager -> deleteCustomerAdjustment got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }

    public ResponseMessage getCustomerDueByInvoiceID(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        CustomerInvoiceModel customerInvoiceModel;
        Double dueAmount = 0.0;
        try {
            customerInvoiceModel = Core.getRequestObject(requestMessage, CustomerInvoiceModel.class);

            dueAmount = this.customerAdjustmentDetailBllManager.getDueAmount(customerInvoiceModel.getCustomerInvoiceID());

            responseMessage.responseObj = dueAmount;
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
            responseMessage.message = MessageConstant.CUSTOMER_DUE_GET_SUCCESSFULLY;

        } catch (Exception ex) {
            log.error("CustomerAdjustmentServiceManager -> getCustomerDueByInvoiceID got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }
        return responseMessage;
    }

    public ResponseMessage adjustOpeningBalance(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        OpeningBalanceModel openingBalanceModel = new OpeningBalanceModel();
        try {
            openingBalanceModel = Core.getRequestObject(requestMessage, OpeningBalanceModel.class);

            if (openingBalanceModel.getOpeningBalanceID() != null && openingBalanceModel.getOpeningBalanceID() > 0) {
                openingBalanceModel.setReferenceType(BankReferenceType.Customer.get());

                String openingBalanceSaveServiceName = "api/openingBalance/save";
                ResponseMessage responseFromInterModule = accountingInterModuleCommunication(openingBalanceSaveServiceName, openingBalanceModel);
                requestMessage.requestObj = responseFromInterModule.responseObj;
                if (responseFromInterModule.responseCode == 200) {
                    responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                    responseMessage.message = MessageConstant.ADJUST_OPENING_BALANCE_SAVE_SUCCESSFULLY;
                    this.commit();
                } else {
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    if (responseFromInterModule.message == null) {
                        responseMessage.message = MessageConstant.ADJUST_OPENING_BALANCE_SAVE_FAILED;
                    } else {
                        responseMessage.message = responseFromInterModule.message;
                    }
                    this.rollBack();
                }
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.ADJUST_OPENING_BALANCE_SAVE_FAILED;
            }
        } catch (Exception ex) {
            log.error("CustomerAdjustmentServiceManager -> adjustOpeningBalance got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }
        return responseMessage;
    }

    public ResponseMessage accountingInterModuleCommunication(String serviceName, Object requestModel) {
        MqttClient mqttClient;
        ResponseMessage responseMessage = new ResponseMessage();
        RequestMessage reqMessForWorker;
        boolean workCompleteWithInAllowTime;
        try {

            Object lockObject = new Object();
            this.barrier = TillBoxUtils.getBarrier(1, lockObject);

            CallBack callBack;
            reqMessForWorker = Core.getDefaultWorkerRequestMessage();

            String pubTopic = WorkerSubscriptionConstants.WORKER_ACCOUNTING_MODULE_TOPIC;
            reqMessForWorker.brokerMessage.serviceName = serviceName;
            reqMessForWorker.requestObj = requestModel;
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
                    return responseMessage;
                } else {
                    //timeout
                    //TODO Implement role back logic
                }
            }
            this.closeBrokerClient(mqttClient, reqMessForWorker.brokerMessage.messageId);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("CustomerAdjustmentServiceManager -> inter module communication Journal got exception");
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
                    if (responseMessage.responseObj != null) {
                        currencyModel = Core.jsonMapper.convertValue(responseMessage.responseObj, CurrencyModel.class);
                    } else {
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
            log.error("CustomerAdjustmentServiceManager -> inter module communication getBaseCurrencyAndExchangeRate got exception");
        }
        return currencyModel;
    }

}
