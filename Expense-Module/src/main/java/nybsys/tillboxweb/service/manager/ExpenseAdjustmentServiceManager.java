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
import nybsys.tillboxweb.bll.manager.ExpenseAdjustmentBllManager;
import nybsys.tillboxweb.bll.manager.ExpenseAdjustmentDetailBllManager;
import nybsys.tillboxweb.broker.client.CallBack;
import nybsys.tillboxweb.broker.client.PublisherForWorker;
import nybsys.tillboxweb.broker.client.SubscriberForWorker;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.constant.WorkerSubscriptionConstants;
import nybsys.tillboxweb.coreConstant.CurrencyConstant;
import nybsys.tillboxweb.coreEnum.AccountNegative;
import nybsys.tillboxweb.coreEnum.AccountPositive;
import nybsys.tillboxweb.coreEnum.DebitCreditIndicator;
import nybsys.tillboxweb.coreEnum.ReferenceType;
import nybsys.tillboxweb.coreModels.AccountModel;
import nybsys.tillboxweb.coreModels.CurrencyModel;
import nybsys.tillboxweb.coreModels.JournalModel;
import nybsys.tillboxweb.coreModels.VMJournalListModel;
import nybsys.tillboxweb.expense_enum.ExpenseCategory;
import nybsys.tillboxweb.models.ExpenseAdjustmentDetailModel;
import nybsys.tillboxweb.models.ExpenseAdjustmentModel;
import nybsys.tillboxweb.models.VMExpenseAdjustmentModel;
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
public class ExpenseAdjustmentServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(ExpenseAdjustmentServiceManager.class);
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Autowired
    ExpenseAdjustmentBllManager expenseAdjustmentBllManager;

    @Autowired
    ExpenseAdjustmentDetailBllManager expenseAdjustmentDetailBllManager;

    private ExpenseServiceManager expenseServiceManager = new ExpenseServiceManager();

    public ResponseMessage saveExpenseAdjustmentVM(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        ResponseMessage responseFromInterModule;
        ExpenseAdjustmentModel expenseAdjustmentModel;
        VMExpenseAdjustmentModel vmExpenseAdjustmentModel;
        List<ExpenseAdjustmentDetailModel> lstExpenseAdjustmentDetailModel = new ArrayList<>();
        CurrencyModel currencyModel;
        try {

            //get base currency and exchange rate
            currencyModel = this.expenseServiceManager.getBaseCurrency();
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

            vmExpenseAdjustmentModel = Core.getRequestObject(requestMessage, VMExpenseAdjustmentModel.class);
            expenseAdjustmentModel = vmExpenseAdjustmentModel.expenseAdjustmentModel;
            lstExpenseAdjustmentDetailModel = vmExpenseAdjustmentModel.lstExpenseAdjustmentDetailModel;


            /*Set<ConstraintViolation<ExpenseAdjustmentModel>> violations = this.validator.validate(expenseAdjustmentModel);
            if (violations.size() > 0) {
                responseMessage = this.buildResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }
            for(ExpenseAdjustmentDetailModel expenseAdjustmentDetailModel : lstExpenseAdjustmentDetailModel)
            {
                Set<ConstraintViolation<ExpenseAdjustmentDetailModel>> violations = this.validator.validate(expenseAdjustmentDetailModel);
                if (violations.size() > 0) {
                    responseMessage = this.buildResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                    return responseMessage;
                }
            }*/

            if (expenseAdjustmentModel.getExpenseAdjustmentID() != null && expenseAdjustmentModel.getExpenseAdjustmentID() > 0) {

                //(1)delete journal
                JournalModel deleteJournalModel = new JournalModel();
                deleteJournalModel.setReferenceType(ReferenceType.ExpenseAdjustment.get());
                deleteJournalModel.setReferenceID(expenseAdjustmentModel.getExpenseAdjustmentID());

                String deleteJournalServiceName = "api/journal/delete";
                responseFromInterModule = this.expenseServiceManager.journalInterModuleCommunication(deleteJournalServiceName,deleteJournalModel);
                if (responseFromInterModule.responseCode != 200) {
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    if (responseFromInterModule.message == null) {
                        responseMessage.message = MessageConstant.EXPENSE_ADJUSTMENT_SAVE_FAILED;
                    } else {
                        responseMessage.message = responseFromInterModule.message;
                    }
                    this.rollBack();
                    return responseMessage;
                }

                //(3)delete  detail first
                this.expenseAdjustmentDetailBllManager.deleteExpenseAdjustDetailByExpenseAdjustmentID(expenseAdjustmentModel.getExpenseAdjustmentID());
                if (Core.clientMessage.get().messageCode != null) {
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    if (Core.clientMessage.get().userMessage == null) {
                        responseMessage.message = MessageConstant.EXPENSE_ADJUSTMENT_SAVE_FAILED;
                    } else {
                        responseMessage.message = Core.clientMessage.get().userMessage;
                    }
                    this.rollBack();
                    return responseMessage;
                }
            }

            //(3) check balance is greater then or equal adjusted amount
            //calculate total adjusted amount
            AccountModel accountModel = new AccountModel();
            for (ExpenseAdjustmentDetailModel expenseAdjustmentDetailModel : vmExpenseAdjustmentModel.lstExpenseAdjustmentDetailModel) {
                accountModel.setAccountID(expenseAdjustmentDetailModel.getAccountIDExpense());

                if (vmExpenseAdjustmentModel.expenseAdjustmentModel.getAdjustmentType() == ExpenseCategory.AdvanceExpense.get()) {
                    accountModel.setAccountTypeID(AccountPositive.Asset.get());
                } else if (vmExpenseAdjustmentModel.expenseAdjustmentModel.getAdjustmentType() == ExpenseCategory.DueExpense.get()) {
                    accountModel.setAccountTypeID(AccountNegative.Liability.get());
                }

                responseFromInterModule = getBalanceFromJournal(accountModel, vmExpenseAdjustmentModel.expenseAdjustmentModel.getBusinessID());
                if (responseFromInterModule.responseCode != 200) {
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    if (responseFromInterModule.message == null) {
                        responseMessage.message = MessageConstant.EXPENSE_SAVE_FAILED;
                    } else {
                        responseMessage.message = responseFromInterModule.message;
                    }
                    this.rollBack();
                    return responseMessage;
                }

                if (Double.valueOf(responseFromInterModule.responseObj.toString()) < expenseAdjustmentDetailModel.getAmount()) {
                    //exceeds
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    responseMessage.message = MessageConstant.EXPENSE_ADJUSTED_AMOUNT_IS_GREATER_THAN_BALANCE;
                    this.rollBack();
                    return responseMessage;
                }
            }

            //(4)
            //add currency
            if(expenseAdjustmentModel.getExpenseAdjustmentID() == null ||expenseAdjustmentModel.getExpenseAdjustmentID() == 0) {
                expenseAdjustmentModel.setBaseCurrencyID(currencyModel.getCurrencyID());
                expenseAdjustmentModel.setEntryCurrencyID(requestMessage.entryCurrencyID);
                expenseAdjustmentModel.setBaseCurrencyAmount(expenseAdjustmentModel.getTotalAmount() * expenseAdjustmentModel.getExchangeRate());
            }

            expenseAdjustmentModel = this.expenseAdjustmentBllManager.saveOrUpdate(expenseAdjustmentModel);
            if (Core.clientMessage.get().messageCode != null) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.EXPENSE_ADJUSTMENT_SAVE_FAILED;
                } else {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
                return responseMessage;
            }

            //(5)save journal
            VMJournalListModel vmJournalListModel = new VMJournalListModel();
            JournalModel drJournalModel = new JournalModel();
            JournalModel crJournalModel = new JournalModel();

            for (ExpenseAdjustmentDetailModel expenseAdjustmentDetailModel : vmExpenseAdjustmentModel.lstExpenseAdjustmentDetailModel) {

                //dr
                drJournalModel.setDrCrIndicator(DebitCreditIndicator.Debit.get());
                drJournalModel.setBusinessID(expenseAdjustmentModel.getBusinessID());
                drJournalModel.setAmount(expenseAdjustmentDetailModel.getAmount());
                drJournalModel.setAccountID(expenseAdjustmentDetailModel.getAccountIDFrom());
                drJournalModel.setReferenceType(ReferenceType.ExpenseAdjustment.get());
                drJournalModel.setReferenceID(expenseAdjustmentModel.getExpenseAdjustmentID());

                drJournalModel.setBaseCurrencyID(currencyModel.getCurrencyID());
                drJournalModel.setEntryCurrencyID(requestMessage.entryCurrencyID);
                drJournalModel.setExchangeRate(expenseAdjustmentModel.getExchangeRate());
                drJournalModel.setBaseCurrencyAmount(expenseAdjustmentDetailModel.getAmount()*expenseAdjustmentModel.getExchangeRate());

                //cr
                crJournalModel.setDrCrIndicator(DebitCreditIndicator.Credit.get());
                crJournalModel.setBusinessID(expenseAdjustmentModel.getBusinessID());
                crJournalModel.setAmount(expenseAdjustmentDetailModel.getAmount());
                crJournalModel.setAccountID(expenseAdjustmentDetailModel.getAccountIDExpense());
                crJournalModel.setReferenceType(ReferenceType.ExpenseAdjustment.get());
                crJournalModel.setReferenceID(expenseAdjustmentModel.getExpenseAdjustmentID());

                crJournalModel.setBaseCurrencyID(currencyModel.getCurrencyID());
                crJournalModel.setEntryCurrencyID(requestMessage.entryCurrencyID);
                crJournalModel.setExchangeRate(expenseAdjustmentModel.getExchangeRate());
                crJournalModel.setBaseCurrencyAmount(expenseAdjustmentDetailModel.getAmount()*expenseAdjustmentModel.getExchangeRate());

                //make vm
                vmJournalListModel.lstJournalModel.add(drJournalModel);
                vmJournalListModel.lstJournalModel.add(crJournalModel);
                drJournalModel = new JournalModel();
                crJournalModel = new JournalModel();

            }


            String journalSaveServiceName = "api/journal/save";
            responseFromInterModule = this.expenseServiceManager.journalInterModuleCommunication(journalSaveServiceName,vmJournalListModel);
            if (responseFromInterModule.responseCode != 200) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (responseFromInterModule.message == null) {
                    responseMessage.message = MessageConstant.EXPENSE_ADJUSTMENT_SAVE_FAILED;
                } else {
                    responseMessage.message = responseFromInterModule.message;
                }
                this.rollBack();
                return responseMessage;
            }

            //(6)save or update expense adjustment detail
            this.expenseAdjustmentDetailBllManager.saveOrUpdateList(lstExpenseAdjustmentDetailModel, expenseAdjustmentModel.getExpenseAdjustmentID());
            if (Core.clientMessage.get().messageCode != null) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.EXPENSE_ADJUSTMENT_SAVE_FAILED;
                } else {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
                return responseMessage;
            }

            this.commit();
            responseMessage.message = MessageConstant.EXPENSE_ADJUSTMENT_SAVE_SUCCESSFULLY;
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

        } catch (Exception ex) {
            log.error("ExpenseAdjustmentServiceManager -> saveExpenseAdjustmentVM got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }

    public ResponseMessage searchExpenseAdjustmentVM(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        ExpenseAdjustmentModel expenseAdjustmentModel;
        List<ExpenseAdjustmentModel> lstExpenseAdjustmentModel;
        List<VMExpenseAdjustmentModel> lstVmExpenseAdjustmentModel = new ArrayList<>();
        try {
            expenseAdjustmentModel = Core.getRequestObject(requestMessage, ExpenseAdjustmentModel.class);

            lstExpenseAdjustmentModel = this.expenseAdjustmentBllManager.searchExpenseAdjustment(expenseAdjustmentModel);

            for (ExpenseAdjustmentModel expenseAdjustmentModelObject : lstExpenseAdjustmentModel) {
                VMExpenseAdjustmentModel vmExpenseAdjustmentModel = new VMExpenseAdjustmentModel();

                vmExpenseAdjustmentModel.expenseAdjustmentModel = this.expenseAdjustmentBllManager.searchExpenseAdjustmentByID(expenseAdjustmentModelObject.getExpenseAdjustmentID());

                if (vmExpenseAdjustmentModel.expenseAdjustmentModel != null && vmExpenseAdjustmentModel.expenseAdjustmentModel.getExpenseAdjustmentID() != null) {
                    vmExpenseAdjustmentModel.lstExpenseAdjustmentDetailModel = this.expenseAdjustmentDetailBllManager.searchExpenseAdjustmentDetailByAdjustmentID(vmExpenseAdjustmentModel.expenseAdjustmentModel.getExpenseAdjustmentID());
                }
                if (vmExpenseAdjustmentModel.expenseAdjustmentModel != null || vmExpenseAdjustmentModel.lstExpenseAdjustmentDetailModel.size() > 0) {
                    lstVmExpenseAdjustmentModel.add(vmExpenseAdjustmentModel);
                }
            }

            responseMessage.responseObj = lstVmExpenseAdjustmentModel;
            if (lstVmExpenseAdjustmentModel.size() == 0) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.EXPENSE_ADJUSTMENT_GET_FAILED;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.EXPENSE_ADJUSTMENT_GET_SUCCESSFULLY;
            }

        } catch (Exception ex) {
            log.error("ExpenseAdjustmentServiceManager -> searchExpenseAdjustmentVM got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }

    public ResponseMessage getExpenseAdjustmentVMByID(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        ExpenseAdjustmentModel expenseAdjustmentModel;
        VMExpenseAdjustmentModel vmExpenseAdjustmentModel = new VMExpenseAdjustmentModel();
        try {
            expenseAdjustmentModel = Core.getRequestObject(requestMessage, ExpenseAdjustmentModel.class);

            vmExpenseAdjustmentModel.expenseAdjustmentModel = this.expenseAdjustmentBllManager.searchExpenseAdjustmentByID(expenseAdjustmentModel.getExpenseAdjustmentID());

            if (vmExpenseAdjustmentModel.expenseAdjustmentModel != null && vmExpenseAdjustmentModel.expenseAdjustmentModel.getExpenseAdjustmentID() != null) {
                vmExpenseAdjustmentModel.lstExpenseAdjustmentDetailModel = this.expenseAdjustmentDetailBllManager.searchExpenseAdjustmentDetailByAdjustmentID(vmExpenseAdjustmentModel.expenseAdjustmentModel.getExpenseAdjustmentID());
            }

            responseMessage.responseObj = vmExpenseAdjustmentModel;
            if (vmExpenseAdjustmentModel.lstExpenseAdjustmentDetailModel.size() == 0 && vmExpenseAdjustmentModel.expenseAdjustmentModel == null) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.EXPENSE_ADJUSTMENT_GET_FAILED;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.EXPENSE_ADJUSTMENT_GET_SUCCESSFULLY;
            }

        } catch (Exception ex) {
            log.error("ExpenseAdjustmentServiceManager -> getExpenseAdjustmentVMByID got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }

    public ResponseMessage deleteExpenseAdjustmentAndDetail(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        ExpenseAdjustmentModel expenseAdjustmentModel;
        try {
            expenseAdjustmentModel = Core.getRequestObject(requestMessage, ExpenseAdjustmentModel.class);
            if (expenseAdjustmentModel.getExpenseAdjustmentID() != null && expenseAdjustmentModel.getExpenseAdjustmentID() > 0) {

                //(1)
                this.expenseAdjustmentBllManager.deleteExpenseAdjustmentByExpenseAdjustmentID(expenseAdjustmentModel.getExpenseAdjustmentID());
                if (Core.clientMessage.get().messageCode != null) {
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    if (Core.clientMessage.get().userMessage == null) {
                        responseMessage.message = MessageConstant.EXPENSE_ADJUSTMENT_DELETE_FAILED;
                    } else {
                        responseMessage.message = Core.clientMessage.get().userMessage;
                    }
                    this.rollBack();
                    return responseMessage;
                }

                //(2)delete journal
                JournalModel deleteJournalModel = new JournalModel();
                deleteJournalModel.setReferenceType(ReferenceType.ExpenseAdjustment.get());
                deleteJournalModel.setReferenceID(expenseAdjustmentModel.getExpenseAdjustmentID());

                String deleteJournalServiceName = "api/journal/delete";
                ResponseMessage responseFromInterModule = this.expenseServiceManager.journalInterModuleCommunication(deleteJournalServiceName,deleteJournalModel);
                if (responseFromInterModule.responseCode != 200) {
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = responseFromInterModule.message;
                    if (responseFromInterModule.message == null) {
                        responseMessage.message = MessageConstant.EXPENSE_ADJUSTMENT_DELETE_FAILED;
                    } else {
                        responseMessage.message = responseFromInterModule.message;
                    }
                    this.rollBack();
                    return responseMessage;
                }

                //(3)
                this.expenseAdjustmentDetailBllManager.deleteExpenseAdjustDetailByExpenseAdjustmentID(expenseAdjustmentModel.getExpenseAdjustmentID());
                if (Core.clientMessage.get().messageCode != null) {
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    if (Core.clientMessage.get().userMessage == null) {
                        responseMessage.message = MessageConstant.EXPENSE_ADJUSTMENT_DELETE_FAILED;
                    } else {
                        responseMessage.message = Core.clientMessage.get().userMessage;
                    }
                    this.rollBack();
                    return responseMessage;
                }
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.EXPENSE_ADJUSTMENT_DELETE_FAILED;
                this.rollBack();
                return responseMessage;
            }

            this.commit();
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
            responseMessage.message = MessageConstant.EXPENSE_ADJUSTMENT_DELETE_SUCCESSFULLY;

        } catch (Exception ex) {
            log.error("ExpenseAdjustmentServiceManager -> deleteExpenseAdjustment got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }

    private ResponseMessage getBalanceFromJournal(AccountModel accountModel, Integer businessID) {
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
            reqMessForWorker.brokerMessage.serviceName = "api/journal/availableBalanceByAccount/get";
            reqMessForWorker.requestObj = accountModel;
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
                    return responseMessage;
                } else {
                    //timeout
                    //TODO Implement role back logic
                }
            }
            this.closeBrokerClient(mqttClient, reqMessForWorker.brokerMessage.messageId);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("ExpenseAdjustmentServiceManager -> inter module communication getBalanceFromJournal got exception");
        }
        return responseMessage;
    }

}
