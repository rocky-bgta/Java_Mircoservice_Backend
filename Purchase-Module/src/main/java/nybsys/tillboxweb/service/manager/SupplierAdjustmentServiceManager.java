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
import nybsys.tillboxweb.bll.manager.SupplierAdjustmentBllManager;
import nybsys.tillboxweb.bll.manager.SupplierAdjustmentDetailBllManager;
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
public class SupplierAdjustmentServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(SupplierAdjustmentServiceManager.class);
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Autowired
    SupplierAdjustmentBllManager supplierAdjustmentBllManager;

    @Autowired
    SupplierAdjustmentDetailBllManager supplierAdjustmentDetailBllManager;

    public ResponseMessage saveSupplierAdjustmentVM(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        ResponseMessage responseFromInterModule;
        SupplierAdjustmentModel supplierAdjustmentModel;
        VMSupplierAdjustmentModel vmSupplierAdjustmentModel;
        List<SupplierAdjustmentDetailModel> lstSupplierAdjustmentDetailModel;
        CurrencyModel currencyModel;
        try {
            //get base currency and exchange rate
            currencyModel = getBaseCurrency();
            if(currencyModel == null){
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = CurrencyConstant.BASE_CURRENT_NOT_FOUND;
                return responseMessage;
            }

            //check entry currency is present if not base currency will be entry currency
            if(requestMessage.entryCurrencyID == null || requestMessage.entryCurrencyID == 0)
            {
                requestMessage.entryCurrencyID = currencyModel.getCurrencyID();
            }

            vmSupplierAdjustmentModel = Core.getRequestObject(requestMessage, VMSupplierAdjustmentModel.class);
            supplierAdjustmentModel = vmSupplierAdjustmentModel.supplierAdjustmentModel;
            lstSupplierAdjustmentDetailModel = vmSupplierAdjustmentModel.lstSupplierAdjustmentDetailModel;


            /*Set<ConstraintViolation<SupplierAdjustmentModel>> violations = this.validator.validate(supplierAdjustmentModel);
            if (violations.size() > 0) {
                responseMessage = this.buildResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }
            for(SupplierAdjustmentDetailModel supplierAdjustmentDetailModel : lstSupplierAdjustmentDetailModel)
            {
                Set<ConstraintViolation<SupplierAdjustmentDetailModel>> violations = this.validator.validate(supplierAdjustmentDetailModel);
                if (violations.size() > 0) {
                    responseMessage = this.buildResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                    return responseMessage;
                }
            }*/


            if (supplierAdjustmentModel.getSupplierAdjustmentID() != null && supplierAdjustmentModel.getSupplierAdjustmentID() > 0) {

                //(1)delete journal
                JournalModel deleteJournalModel = new JournalModel();
                deleteJournalModel.setReferenceType(ReferenceType.SupplierAdjustment.get());
                deleteJournalModel.setReferenceID(supplierAdjustmentModel.getSupplierAdjustmentID());

                String deleteJournalServiceName = "api/journal/delete";
                responseFromInterModule = accountingInterModuleCommunication(deleteJournalServiceName,deleteJournalModel);
                if (responseFromInterModule.responseCode != 200) {
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    if (responseFromInterModule.message == null) {
                        responseMessage.message = MessageConstant.SUPPLIER_ADJUSTMENT_SAVE_FAILED;
                    } else {
                        responseMessage.message = responseFromInterModule.message;
                    }
                    this.rollBack();
                    return responseMessage;
                }

                //(2)delete detail
                this.supplierAdjustmentDetailBllManager.deleteSupplierAdjustDetailBySupplierAdjustmentID(supplierAdjustmentModel.getSupplierAdjustmentID());
                if (Core.clientMessage.get().messageCode != null) {
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    if (Core.clientMessage.get().userMessage == null) {
                        responseMessage.message = MessageConstant.SUPPLIER_ADJUSTMENT_SAVE_FAILED;
                    } else {
                        responseMessage.message = Core.clientMessage.get().userMessage;
                    }
                    this.rollBack();
                    return responseMessage;
                }

            }
            //(3)
            //add currency
            if(supplierAdjustmentModel.getSupplierAdjustmentID() == null || supplierAdjustmentModel.getSupplierAdjustmentID() == 0){
                supplierAdjustmentModel.setBaseCurrencyID(currencyModel.getCurrencyID());
                supplierAdjustmentModel.setEntryCurrencyID(requestMessage.entryCurrencyID);
                supplierAdjustmentModel.setBaseCurrencyAmount(supplierAdjustmentModel.getTotalAmount()* supplierAdjustmentModel.getExchangeRate());
            }
            supplierAdjustmentModel = this.supplierAdjustmentBllManager.saveOrUpdate(supplierAdjustmentModel);
            if (Core.clientMessage.get().messageCode != null) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.SUPPLIER_ADJUSTMENT_SAVE_FAILED;
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

            if (supplierAdjustmentModel.getEffectType().intValue() == Adjustment.Decrease.get()) {
                //dr
                drJournalModel.setDrCrIndicator(DebitCreditIndicator.Debit.get());
                drJournalModel.setBusinessID(supplierAdjustmentModel.getBusinessID());
                drJournalModel.setAmount(supplierAdjustmentModel.getTotalAmount());
                drJournalModel.setAccountID(DefaultCOA.TradeCreditors.get());
                drJournalModel.setReferenceType(ReferenceType.SupplierAdjustment.get());
                drJournalModel.setReferenceID(supplierAdjustmentModel.getSupplierAdjustmentID());
                drJournalModel.setPartyID(supplierAdjustmentModel.getSupplierID());
                drJournalModel.setPartyType(PartyType.Supplier.get());

                drJournalModel.setBaseCurrencyID(currencyModel.getCurrencyID());
                drJournalModel.setEntryCurrencyID(requestMessage.entryCurrencyID);
                drJournalModel.setExchangeRate(supplierAdjustmentModel.getExchangeRate());
                drJournalModel.setBaseCurrencyAmount(supplierAdjustmentModel.getTotalAmount()*supplierAdjustmentModel.getExchangeRate());

                //cr
                crJournalModel.setDrCrIndicator(DebitCreditIndicator.Credit.get());
                crJournalModel.setBusinessID(supplierAdjustmentModel.getBusinessID());
                crJournalModel.setAmount(supplierAdjustmentModel.getTotalAmount());
                crJournalModel.setAccountID(DefaultCOA.AdjustmentIncome.get());
                crJournalModel.setReferenceType(ReferenceType.SupplierAdjustment.get());
                crJournalModel.setReferenceID(supplierAdjustmentModel.getSupplierAdjustmentID());

                crJournalModel.setBaseCurrencyID(currencyModel.getCurrencyID());
                crJournalModel.setEntryCurrencyID(requestMessage.entryCurrencyID);
                crJournalModel.setExchangeRate(supplierAdjustmentModel.getExchangeRate());
                crJournalModel.setBaseCurrencyAmount(supplierAdjustmentModel.getTotalAmount()*supplierAdjustmentModel.getExchangeRate());

            } else {
                //dr
                drJournalModel.setDrCrIndicator(DebitCreditIndicator.Debit.get());
                drJournalModel.setBusinessID(supplierAdjustmentModel.getBusinessID());
                drJournalModel.setAmount(supplierAdjustmentModel.getTotalAmount());
                drJournalModel.setAccountID(DefaultCOA.AdjustmentLoss.get());
                drJournalModel.setReferenceType(ReferenceType.SupplierAdjustment.get());
                drJournalModel.setReferenceID(supplierAdjustmentModel.getSupplierAdjustmentID());

                drJournalModel.setBaseCurrencyID(currencyModel.getCurrencyID());
                drJournalModel.setEntryCurrencyID(requestMessage.entryCurrencyID);
                drJournalModel.setExchangeRate(supplierAdjustmentModel.getExchangeRate());
                drJournalModel.setBaseCurrencyAmount(supplierAdjustmentModel.getTotalAmount()*supplierAdjustmentModel.getExchangeRate());
                //cr
                crJournalModel.setDrCrIndicator(DebitCreditIndicator.Credit.get());
                crJournalModel.setBusinessID(supplierAdjustmentModel.getBusinessID());
                crJournalModel.setAmount(supplierAdjustmentModel.getTotalAmount());
                crJournalModel.setAccountID(DefaultCOA.TradeCreditors.get());
                crJournalModel.setReferenceType(ReferenceType.SupplierAdjustment.get());
                crJournalModel.setReferenceID(supplierAdjustmentModel.getSupplierAdjustmentID());
                crJournalModel.setPartyID(supplierAdjustmentModel.getSupplierID());
                crJournalModel.setPartyType(PartyType.Supplier.get());

                crJournalModel.setBaseCurrencyID(currencyModel.getCurrencyID());
                crJournalModel.setEntryCurrencyID(requestMessage.entryCurrencyID);
                crJournalModel.setExchangeRate(supplierAdjustmentModel.getExchangeRate());
                crJournalModel.setBaseCurrencyAmount(supplierAdjustmentModel.getTotalAmount()*supplierAdjustmentModel.getExchangeRate());
            }
            //make vm
            vmJournalListModel.lstJournalModel.add(drJournalModel);
            vmJournalListModel.lstJournalModel.add(crJournalModel);

            String saveJournalServiceName = "api/journal/save";
            responseFromInterModule = accountingInterModuleCommunication(saveJournalServiceName,vmJournalListModel);
            if (responseFromInterModule.responseCode != 200) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (responseFromInterModule.message == null) {
                    responseMessage.message = MessageConstant.SUPPLIER_ADJUSTMENT_SAVE_FAILED;
                } else {
                    responseMessage.message = responseFromInterModule.message;
                }
                this.rollBack();
                return responseMessage;
            }

            //(5)save or update supplier adjustment detail+ business validation
            this.supplierAdjustmentDetailBllManager.saveOrUpdateList(lstSupplierAdjustmentDetailModel, supplierAdjustmentModel.getSupplierAdjustmentID());
            if (Core.clientMessage.get().messageCode != null) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.SUPPLIER_ADJUSTMENT_SAVE_FAILED;
                } else {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
                return responseMessage;
            }

            this.commit();
            responseMessage.message = MessageConstant.SUPPLIER_ADJUSTMENT_SAVE_SUCCESSFULLY;
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

        } catch (Exception ex) {
            log.error("SupplierAdjustmentServiceManager -> saveSupplierAdjustmentVM got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }

    public ResponseMessage searchSupplierAdjustmentVM(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        SupplierAdjustmentModel supplierAdjustmentModel;
        List<SupplierAdjustmentModel> lstSupplierAdjustmentModel;
        List<VMSupplierAdjustmentModel> lstVmSupplierAdjustmentModel = new ArrayList<>();
        try {
            supplierAdjustmentModel = Core.getRequestObject(requestMessage, SupplierAdjustmentModel.class);

            lstSupplierAdjustmentModel = this.supplierAdjustmentBllManager.searchSupplierAdjustment(supplierAdjustmentModel);
            for (SupplierAdjustmentModel supplierAdjustmentModelObject : lstSupplierAdjustmentModel) {
                VMSupplierAdjustmentModel vmSupplierAdjustmentModel = new VMSupplierAdjustmentModel();
                vmSupplierAdjustmentModel.supplierAdjustmentModel = this.supplierAdjustmentBllManager.searchSupplierAdjustmentByID(supplierAdjustmentModelObject.getSupplierAdjustmentID(), supplierAdjustmentModelObject.getBusinessID());
                if (vmSupplierAdjustmentModel.supplierAdjustmentModel != null && vmSupplierAdjustmentModel.supplierAdjustmentModel.getSupplierAdjustmentID() != null) {
                    vmSupplierAdjustmentModel.lstSupplierAdjustmentDetailModel = this.supplierAdjustmentDetailBllManager.searchSupplierAdjustmentDetailByAdjustmentID(vmSupplierAdjustmentModel.supplierAdjustmentModel.getSupplierAdjustmentID());
                }
                if (vmSupplierAdjustmentModel.supplierAdjustmentModel != null || vmSupplierAdjustmentModel.lstSupplierAdjustmentDetailModel.size() > 0) {
                    lstVmSupplierAdjustmentModel.add(vmSupplierAdjustmentModel);
                }
            }

            responseMessage.responseObj = lstVmSupplierAdjustmentModel;
            if (lstVmSupplierAdjustmentModel.size() == 0) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SUPPLIER_ADJUSTMENT_GET_FAILED;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUPPLIER_ADJUSTMENT_GET_SUCCESSFULLY;
            }

        } catch (Exception ex) {
            log.error("SupplierAdjustmentServiceManager -> searchSupplierAdjustmentVM got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }

    public ResponseMessage getSupplierAdjustmentVMByID(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        SupplierAdjustmentModel supplierAdjustmentModel;
        VMSupplierAdjustmentModel vmSupplierAdjustmentModel = new VMSupplierAdjustmentModel();
        try {
            supplierAdjustmentModel = Core.getRequestObject(requestMessage, SupplierAdjustmentModel.class);
            Integer businessID = requestMessage.businessID;
            vmSupplierAdjustmentModel.supplierAdjustmentModel = this.supplierAdjustmentBllManager.searchSupplierAdjustmentByID(supplierAdjustmentModel.getSupplierAdjustmentID(), businessID);
            if (vmSupplierAdjustmentModel.supplierAdjustmentModel != null && vmSupplierAdjustmentModel.supplierAdjustmentModel.getSupplierAdjustmentID() != null) {
                vmSupplierAdjustmentModel.lstSupplierAdjustmentDetailModel = this.supplierAdjustmentDetailBllManager.searchSupplierAdjustmentDetailByAdjustmentID(vmSupplierAdjustmentModel.supplierAdjustmentModel.getSupplierAdjustmentID());
            }
            responseMessage.responseObj = vmSupplierAdjustmentModel;
            if (vmSupplierAdjustmentModel.lstSupplierAdjustmentDetailModel.size() == 0 && vmSupplierAdjustmentModel.supplierAdjustmentModel == null) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SUPPLIER_ADJUSTMENT_GET_FAILED;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUPPLIER_ADJUSTMENT_GET_SUCCESSFULLY;
            }

        } catch (Exception ex) {
            log.error("SupplierAdjustmentServiceManager -> getSupplierAdjustmentVMByID got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }

    public ResponseMessage deleteSupplierAdjustmentAndDetail(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        SupplierAdjustmentModel supplierAdjustmentModel;
        try {
            supplierAdjustmentModel = Core.getRequestObject(requestMessage, SupplierAdjustmentModel.class);
            if (supplierAdjustmentModel.getSupplierAdjustmentID() != null && supplierAdjustmentModel.getSupplierAdjustmentID() > 0) {

                //(1)
                this.supplierAdjustmentBllManager.deleteSupplierAdjustmentBySupplierAdjustmentID(supplierAdjustmentModel.getSupplierAdjustmentID());
                if (Core.clientMessage.get().messageCode != null) {
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    if (Core.clientMessage.get().userMessage == null) {
                        responseMessage.message = MessageConstant.SUPPLIER_ADJUSTMENT_DELETE_FAILED;
                    } else {
                        responseMessage.message = Core.clientMessage.get().userMessage;
                    }
                    this.rollBack();
                    return responseMessage;
                }
                //(2)delete journal
                JournalModel deleteJournalModel = new JournalModel();
                deleteJournalModel.setReferenceType(DefaultCOA.AccountPayable.get());
                deleteJournalModel.setReferenceID(supplierAdjustmentModel.getSupplierAdjustmentID());

                String deleteJournalServiceName = "api/journal/delete";
                ResponseMessage responseFromInterModule = accountingInterModuleCommunication(deleteJournalServiceName,deleteJournalModel);
                if (responseFromInterModule.responseCode != 200) {
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = responseFromInterModule.message;
                    if (responseFromInterModule.message == null) {
                        responseMessage.message = MessageConstant.SUPPLIER_ADJUSTMENT_DELETE_FAILED;
                    } else {
                        responseMessage.message = responseFromInterModule.message;
                    }
                    this.rollBack();
                    return responseMessage;
                }

                //(3)
                this.supplierAdjustmentDetailBllManager.deleteSupplierAdjustDetailBySupplierAdjustmentID(supplierAdjustmentModel.getSupplierAdjustmentID());
                if (Core.clientMessage.get().messageCode != null) {
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    if (Core.clientMessage.get().userMessage == null) {
                        responseMessage.message = MessageConstant.SUPPLIER_ADJUSTMENT_DELETE_FAILED;
                    } else {
                        responseMessage.message = Core.clientMessage.get().userMessage;
                    }
                    this.rollBack();
                    return responseMessage;
                }
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SUPPLIER_ADJUSTMENT_DELETE_FAILED;
                this.rollBack();
                return responseMessage;
            }

            this.commit();
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
            responseMessage.message = MessageConstant.SUPPLIER_ADJUSTMENT_DELETE_SUCCESSFULLY;

        } catch (Exception ex) {
            log.error("SupplierAdjustmentServiceManager -> deleteSupplierAdjustment got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }

    public ResponseMessage getSupplierDueByInvoiceID(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        SupplierInvoiceModel supplierInvoiceModel;
        Double dueAmount = 0.0;
        try {
            supplierInvoiceModel = Core.getRequestObject(requestMessage, SupplierInvoiceModel.class);

            dueAmount = this.supplierAdjustmentDetailBllManager.getDueAmount(supplierInvoiceModel.getSupplierInvoiceID());

            responseMessage.responseObj = dueAmount;
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
            responseMessage.message = MessageConstant.SUPPLIER_DUE_GET_SUCCESSFULLY;

        } catch (Exception ex) {
            log.error("SupplierAdjustmentServiceManager -> getSupplierDueByInvoiceID got exception");
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
                openingBalanceModel.setReferenceType(BankReferenceType.Supplier.get());

                String openingBalanceSaveServiceName = "api/openingBalance/save";
                ResponseMessage responseFromInterModule = accountingInterModuleCommunication(openingBalanceSaveServiceName,openingBalanceModel);
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
            log.error("SupplierAdjustmentServiceManager -> adjustOpeningBalance got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }
        return responseMessage;
    }

    public ResponseMessage accountingInterModuleCommunication(String serviceName , Object requestModel) {
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
            log.error("SupplierAdjustmentServiceManager -> inter module communication Journal got exception");
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
            log.error("SupplierAdjustmentServiceManager -> inter module communication getBaseCurrencyAndExchangeRate got exception");
        }
        return currencyModel;
    }

}
