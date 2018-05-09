/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 5:09
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
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.bll.manager.ExpenseBllManager;
import nybsys.tillboxweb.bll.manager.ExpenseDetailBllManager;
import nybsys.tillboxweb.broker.client.CallBack;
import nybsys.tillboxweb.broker.client.PublisherForWorker;
import nybsys.tillboxweb.broker.client.SubscriberForWorker;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.constant.WorkerSubscriptionConstants;
import nybsys.tillboxweb.coreConstant.CurrencyConstant;
import nybsys.tillboxweb.coreEnum.DebitCreditIndicator;
import nybsys.tillboxweb.coreEnum.ReferenceType;
import nybsys.tillboxweb.coreModels.CurrencyModel;
import nybsys.tillboxweb.coreModels.JournalModel;
import nybsys.tillboxweb.coreModels.VMJournalListModel;
import nybsys.tillboxweb.expense_enum.ExpenseCategory;
import nybsys.tillboxweb.models.ExpenseDetailModel;
import nybsys.tillboxweb.models.ExpenseModel;
import nybsys.tillboxweb.models.VMExpenseModel;
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
public class ExpenseServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(ExpenseServiceManager.class);
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Autowired
    private ExpenseBllManager expenseBllManager;

    @Autowired
    private ExpenseDetailBllManager expenseDetailBllManager;

    public ResponseMessage save(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        ResponseMessage responseFromInterModule;
        VMExpenseModel vmExpenseModel;
        ExpenseModel expenseModel;
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

            vmExpenseModel = Core.getRequestObject(requestMessage, VMExpenseModel.class);

            /*Set<ConstraintViolation<ExpenseModel>> violations = this.validator.validate(expenseModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            if (vmExpenseModel.expenseModel.getExpenseID() != null && vmExpenseModel.expenseModel.getExpenseID() > 0) {

                //(1)delete journal
                JournalModel deleteJournalModel = new JournalModel();
                deleteJournalModel.setReferenceType(ReferenceType.Expense.get());
                deleteJournalModel.setReferenceID(vmExpenseModel.expenseModel.getExpenseID());
                String deleteJournalServiceName = "api/journal/delete";
                responseFromInterModule = journalInterModuleCommunication(deleteJournalServiceName,deleteJournalModel);
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

                //(2)delete detail
                this.expenseDetailBllManager.deleteExpenseDetailByExpenseID(vmExpenseModel.expenseModel.getExpenseID());
                if (Core.clientMessage.get().messageCode != null) {
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    responseMessage.message = MessageConstant.EXPENSE_SAVE_FAILED;
                    this.rollBack();
                    return responseMessage;
                }
            }

            //(3)
            //add currency
            if(vmExpenseModel.expenseModel.getExpenseID() == null || vmExpenseModel.expenseModel.getExpenseID() == 0) {
                vmExpenseModel.expenseModel.setBaseCurrencyID(currencyModel.getCurrencyID());
                vmExpenseModel.expenseModel.setEntryCurrencyID(requestMessage.entryCurrencyID);
            }
            vmExpenseModel.expenseModel.setBaseCurrencyAmount(vmExpenseModel.expenseModel.getTotalAmount()* vmExpenseModel.expenseModel.getExchangeRate());

            expenseModel = this.expenseBllManager.saveOrUpdate(vmExpenseModel.expenseModel);
            if (Core.clientMessage.get().messageCode != null) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.EXPENSE_SAVE_FAILED;
                this.rollBack();
                return responseMessage;
            }

            //(4)save journal
            VMJournalListModel vmJournalListModel = new VMJournalListModel();
            JournalModel drJournalModel = new JournalModel();
            JournalModel crJournalModel = new JournalModel();

            if ((vmExpenseModel.expenseModel.getCategoryID() == ExpenseCategory.Expense.get()) || (vmExpenseModel.expenseModel.getCategoryID() == ExpenseCategory.AdvanceExpense.get())) {
                for (ExpenseDetailModel expenseDetailModel : vmExpenseModel.lstExpenseDetailModel) {
                    //dr
                    drJournalModel.setDrCrIndicator(DebitCreditIndicator.Debit.get());
                    drJournalModel.setBusinessID(vmExpenseModel.expenseModel.getBusinessID());
                    drJournalModel.setAmount(expenseDetailModel.getAmount());
                    drJournalModel.setAccountID(expenseDetailModel.getAccountIDExpense());
                    drJournalModel.setReferenceType(ReferenceType.Expense.get());
                    drJournalModel.setReferenceID(expenseModel.getExpenseID());

                    drJournalModel.setBaseCurrencyID(currencyModel.getCurrencyID());
                    drJournalModel.setEntryCurrencyID(requestMessage.entryCurrencyID);
                    drJournalModel.setExchangeRate(vmExpenseModel.expenseModel.getExchangeRate());
                    drJournalModel.setBaseCurrencyAmount(expenseDetailModel.getAmount()*vmExpenseModel.expenseModel.getExchangeRate());

                    //cr
                    crJournalModel.setDrCrIndicator(DebitCreditIndicator.Credit.get());
                    crJournalModel.setBusinessID(vmExpenseModel.expenseModel.getBusinessID());
                    crJournalModel.setAmount(expenseDetailModel.getAmount());
                    crJournalModel.setAccountID(expenseDetailModel.getAccountIDFrom());
                    crJournalModel.setReferenceType(ReferenceType.Expense.get());
                    crJournalModel.setReferenceID(expenseModel.getExpenseID());

                    crJournalModel.setBaseCurrencyID(currencyModel.getCurrencyID());
                    crJournalModel.setEntryCurrencyID(requestMessage.entryCurrencyID);
                    crJournalModel.setExchangeRate(vmExpenseModel.expenseModel.getExchangeRate());
                    crJournalModel.setBaseCurrencyAmount(expenseDetailModel.getAmount()*vmExpenseModel.expenseModel.getExchangeRate());

                    //make vm
                    vmJournalListModel.lstJournalModel.add(drJournalModel);
                    vmJournalListModel.lstJournalModel.add(crJournalModel);
                    drJournalModel = new JournalModel();
                    crJournalModel = new JournalModel();
                }
            } else if (vmExpenseModel.expenseModel.getCategoryID() == ExpenseCategory.DueExpense.get()) {
                for (ExpenseDetailModel expenseDetailModel : vmExpenseModel.lstExpenseDetailModel) {
                    //dr
                    drJournalModel.setDrCrIndicator(DebitCreditIndicator.Debit.get());
                    drJournalModel.setBusinessID(vmExpenseModel.expenseModel.getBusinessID());
                    drJournalModel.setAmount(expenseDetailModel.getAmount());
                    drJournalModel.setAccountID(expenseDetailModel.getAccountIDExpense());
                    drJournalModel.setReferenceType(ReferenceType.Expense.get());
                    drJournalModel.setReferenceID(expenseModel.getExpenseID());

                    drJournalModel.setBaseCurrencyID(currencyModel.getCurrencyID());
                    drJournalModel.setEntryCurrencyID(requestMessage.entryCurrencyID);
                    drJournalModel.setExchangeRate(vmExpenseModel.expenseModel.getExchangeRate());
                    drJournalModel.setBaseCurrencyAmount(expenseDetailModel.getAmount()*vmExpenseModel.expenseModel.getExchangeRate());

                    //cr
                    crJournalModel.setDrCrIndicator(DebitCreditIndicator.Credit.get());
                    crJournalModel.setBusinessID(vmExpenseModel.expenseModel.getBusinessID());
                    crJournalModel.setAmount(expenseDetailModel.getAmount());
                    crJournalModel.setAccountID(expenseDetailModel.getAccountIDFrom());
                    crJournalModel.setReferenceType(ReferenceType.Expense.get());
                    crJournalModel.setReferenceID(expenseModel.getExpenseID());

                    crJournalModel.setBaseCurrencyID(currencyModel.getCurrencyID());
                    crJournalModel.setEntryCurrencyID(requestMessage.entryCurrencyID);
                    crJournalModel.setExchangeRate(vmExpenseModel.expenseModel.getExchangeRate());
                    crJournalModel.setBaseCurrencyAmount(expenseDetailModel.getAmount()*vmExpenseModel.expenseModel.getExchangeRate());

                    //make vm
                    vmJournalListModel.lstJournalModel.add(drJournalModel);
                    vmJournalListModel.lstJournalModel.add(crJournalModel);
                    drJournalModel = new JournalModel();
                    crJournalModel = new JournalModel();
                }
            }

            String journalSaveServiceName = "api/journal/save";
            responseFromInterModule = journalInterModuleCommunication(journalSaveServiceName,vmJournalListModel);
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

            //(5)inset detail
            for (ExpenseDetailModel expenseDetailModel : vmExpenseModel.lstExpenseDetailModel) {

                expenseDetailModel.setExpenseID(expenseModel.getExpenseID());
                expenseDetailModel.setExpenseDetailID(null);
                this.expenseDetailBllManager.saveOrUpdate(expenseDetailModel);
                if (Core.clientMessage.get().messageCode != null) {
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    responseMessage.message = MessageConstant.EXPENSE_SAVE_FAILED;
                    this.rollBack();
                    return responseMessage;
                }
            }

            this.commit();
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
            responseMessage.message = MessageConstant.EXPENSE_SAVE_SUCCESSFULLY;


        } catch (Exception ex) {
            log.error("ExpenseServiceManager -> saveExpense got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }

    public ResponseMessage search(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<VMExpenseModel> lstVmExpenseModel = new ArrayList<>();
        List<ExpenseModel> lstExpenseModel = new ArrayList<>();
        ExpenseModel expenseModel = new ExpenseModel();
        try {
            expenseModel = Core.getRequestObject(requestMessage, ExpenseModel.class);

            lstExpenseModel = this.expenseBllManager.searchExpense(expenseModel);

            ExpenseDetailModel whereCondition = new ExpenseDetailModel();
            List<ExpenseDetailModel> lstExpenseDetailModel;
            VMExpenseModel vmExpenseModel = new VMExpenseModel();
            for (ExpenseModel expenseItem : lstExpenseModel) {
                whereCondition.setExpenseID(expenseItem.getExpenseID());
                whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());

                lstExpenseDetailModel = this.expenseDetailBllManager.searchExpenseDetail(whereCondition);

                vmExpenseModel.expenseModel = expenseItem;
                vmExpenseModel.lstExpenseDetailModel = lstExpenseDetailModel;

                lstVmExpenseModel.add(vmExpenseModel);
                vmExpenseModel = new VMExpenseModel();
            }

            responseMessage.responseObj = lstVmExpenseModel;
            if (Core.clientMessage.get().messageCode == null) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.EXPENSE_GET_SUCCESSFULLY;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.EXPENSE_GET_FAILED;
            }

        } catch (Exception ex) {
            log.error("ExpenseServiceManager -> searchExpense got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }

    public ResponseMessage delete(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        ExpenseModel expenseModel;
        try {
            expenseModel = Core.getRequestObject(requestMessage, ExpenseModel.class);
            if (expenseModel.getExpenseID() != null && expenseModel.getExpenseID() > 0) {

                //(1)
                this.expenseBllManager.deleteExpenseByID(expenseModel.getExpenseID());
                if (Core.clientMessage.get().messageCode != null) {
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    if (Core.clientMessage.get().userMessage == null) {
                        responseMessage.message = MessageConstant.EXPENSE_DELETE_FAILED;
                    } else {
                        responseMessage.message = Core.clientMessage.get().userMessage;
                    }
                    this.rollBack();
                    return responseMessage;
                }
                //(2)delete journal
                JournalModel deleteJournalModel = new JournalModel();
                deleteJournalModel.setReferenceType(ReferenceType.Expense.get());
                deleteJournalModel.setReferenceID(expenseModel.getExpenseID());

                String deleteJournalServiceName = "api/journal/delete";
                ResponseMessage responseFromInterModule = journalInterModuleCommunication(deleteJournalServiceName,deleteJournalModel);
                if (responseFromInterModule.responseCode != 200) {
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = responseFromInterModule.message;
                    if (responseFromInterModule.message == null) {
                        responseMessage.message = MessageConstant.EXPENSE_DELETE_FAILED;
                    } else {
                        responseMessage.message = responseFromInterModule.message;
                    }
                    this.rollBack();
                    return responseMessage;
                }

                //(3)
                this.expenseDetailBllManager.deleteExpenseDetailByExpenseID(expenseModel.getExpenseID());
                if (Core.clientMessage.get().messageCode != null) {
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    if (Core.clientMessage.get().userMessage == null) {
                        responseMessage.message = MessageConstant.EXPENSE_DELETE_FAILED;
                    } else {
                        responseMessage.message = Core.clientMessage.get().userMessage;
                    }
                    this.rollBack();
                    return responseMessage;
                }
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.EXPENSE_DELETE_FAILED;
                this.rollBack();
                return responseMessage;
            }

            this.commit();
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
            responseMessage.message = MessageConstant.EXPENSE_DELETE_SUCCESSFULLY;

        } catch (Exception ex) {
            log.error("ExpenseServiceManager -> deleteExpense got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }

    public ResponseMessage expenseCategories(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {

            responseMessage.responseObj = ExpenseCategory.getMAP();
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

        } catch (Exception ex) {
            log.error("ExpenseServiceManager -> expenseCategories got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }


    public ResponseMessage journalInterModuleCommunication(String serviceName ,Object requestModel) {
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
            log.error("ExpenseServiceManager -> inter module communication Journal got exception");
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
            log.error("ExpenseServiceManager -> inter module communication getBaseCurrencyAndExchangeRate got exception");
        }
        return currencyModel;
    }


}
