package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.Utils.TillBoxUtils;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.bll.manager.*;
import nybsys.tillboxweb.broker.client.CallBack;
import nybsys.tillboxweb.broker.client.PublisherForWorker;
import nybsys.tillboxweb.broker.client.SubscriberForWorker;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.constant.WorkerSubscriptionConstants;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.coreBllManager.RememberNoteBllManager;
import nybsys.tillboxweb.coreEnum.ReferenceType;
import nybsys.tillboxweb.coreEnum.RememberNoteReferenceType;
import nybsys.tillboxweb.coreModels.AccountClassificationModel;
import nybsys.tillboxweb.coreModels.AccountTypeModel;
import nybsys.tillboxweb.coreModels.RememberNoteModel;
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
public class AccountServiceManager extends BaseService {

    private static final Logger log = LoggerFactory.getLogger(AccountServiceManager.class);
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Autowired
    private AccountBllManager accountBllManager;
    @Autowired
    private AccountClassificationBllManager accountClassificationBllManager;
    @Autowired
    private AccountTypeBllManager accountTypeBllManager;
    @Autowired
    private CashFlowBllManager cashFlowBllManager;
    @Autowired
    private OpeningBalanceBllManager openingBalanceBllManager;
    private RememberNoteBllManager rememberNoteBllManager = new RememberNoteBllManager();

    private JournalBllManager journalBllManager = new JournalBllManager();

    // internal vm model
    private class VMAccountDropDownList {
        public List<AccountModel> lstAccountModel;
        public List<AccountTypeModel> lstAccountTypeModel;
        public List<TaxCodeModel> lstTaxCodeModel;
        public List<CashFlowModel> lstCashFlowModel;
        public List<AccountClassificationModel> lstAccountClassificationModel;
        public List<OpeningBalanceModel> lstOpeningBalanceModel;
        public List<RememberNoteModel> lstRememberNoteModel;
        public List<VMAccountBalance> lstVMAccountBalance;
    }

    private class VMAccountWithOpeningBalance {
        public AccountModel accountModel = new AccountModel();
        public OpeningBalanceModel openingBalanceModel = new OpeningBalanceModel();
    }

    public ResponseMessage getAccountDropDownList(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<AccountModel> lstAccountModel;
        List<AccountTypeModel> lstAccountTypeModel;
        List<TaxCodeModel> lstTaxCodeModel;
        List<CashFlowModel> lstCashFlowModel;
        List<AccountClassificationModel> lstAccountClassificationModel;
        List<OpeningBalanceModel> lstOpeningBalanceModel = new ArrayList<>();
        List<RememberNoteModel> lstRememberNoteModel;
        List<VMAccountBalance> lstVMAccountBalance = new ArrayList<>();
        try {
            Integer businessID = requestMessage.businessID;

            lstAccountModel = this.accountBllManager.getAllAccount(businessID);
//            if (Core.clientMessage.get().messageCode != null) {
//                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
//                responseMessage.message = MessageConstant.FAILED_TO_GET_ACCOUNT_DROP_DOWN;
//                return responseMessage;
//            }


            lstAccountTypeModel = this.accountTypeBllManager.getAllAccountType();
//            if (Core.clientMessage.get().messageCode != null) {
//                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
//                responseMessage.message = MessageConstant.FAILED_TO_GET_ACCOUNT_DROP_DOWN;
//                return responseMessage;
//            }

            lstTaxCodeModel = getAllTaxCode();

            lstCashFlowModel = this.cashFlowBllManager.getAllCashFlow();
//            if (Core.clientMessage.get().messageCode != null) {
//                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
//                responseMessage.message = MessageConstant.FAILED_TO_GET_ACCOUNT_DROP_DOWN;
//                return responseMessage;
//            }

            lstAccountClassificationModel = this.accountClassificationBllManager.getAllAccountClassification();
//            if (Core.clientMessage.get().messageCode != null) {
//                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
//                responseMessage.message = MessageConstant.FAILED_TO_GET_ACCOUNT_DROP_DOWN;
//                return responseMessage;
//            }

            lstOpeningBalanceModel = this.openingBalanceBllManager.getAllOpeningBalance(requestMessage.businessID);


            for (AccountModel accountModel : lstAccountModel) {
                AccountModel searchAccountModel = new AccountModel();
                searchAccountModel.setAccountTypeID(accountModel.getAccountTypeID());
                searchAccountModel.setAccountClassificationID(accountModel.getAccountClassificationID());
                searchAccountModel.setAccountID(accountModel.getAccountID());

                OpeningBalanceModel openingBalanceModel = new OpeningBalanceModel();
                openingBalanceModel.setAccountID(accountModel.getAccountID());
                double balance = this.journalBllManager.getAvailableBalanceByAccountAndBusinessID(searchAccountModel, requestMessage.businessID);
                openingBalanceModel.setAmount(balance);

                VMAccountBalance vmAccountBalance = new VMAccountBalance();
                vmAccountBalance.setAccountID(accountModel.getAccountID());
                vmAccountBalance.setBalance(balance);
                lstVMAccountBalance.add(vmAccountBalance);
            }

            RememberNoteModel rememberNoteModel = new RememberNoteModel();
            rememberNoteModel.setReferenceType(RememberNoteReferenceType.Account.get());
            rememberNoteModel.setStatus(TillBoxAppEnum.Status.Active.get());
            rememberNoteModel.setBusinessID(businessID);

            lstRememberNoteModel = this.rememberNoteBllManager.getAllRememberNoteModelByBusinessID(requestMessage.businessID);


            //make final vm
            VMAccountDropDownList vmAccountDropDownList = new VMAccountDropDownList();
            vmAccountDropDownList.lstAccountModel = lstAccountModel;
            vmAccountDropDownList.lstAccountTypeModel = lstAccountTypeModel;
            vmAccountDropDownList.lstTaxCodeModel = lstTaxCodeModel;
            vmAccountDropDownList.lstCashFlowModel = lstCashFlowModel;
            vmAccountDropDownList.lstAccountClassificationModel = lstAccountClassificationModel;
            vmAccountDropDownList.lstOpeningBalanceModel = lstOpeningBalanceModel;
            vmAccountDropDownList.lstRememberNoteModel = lstRememberNoteModel;
            vmAccountDropDownList.lstVMAccountBalance = lstVMAccountBalance;

            responseMessage.responseObj = vmAccountDropDownList;
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
            responseMessage.message = MessageConstant.SUCCESSFULLY_GET_ACCOUNT_DROP_DOWN;

        } catch (Exception ex) {
            log.error("AccountServiceManager -> getAccountDropDownList got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }

    public ResponseMessage getAccountClassificationList(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<AccountClassificationModel> lstAccountClassificationModel = new ArrayList<>();
        try {

            lstAccountClassificationModel = this.accountClassificationBllManager.getAllAccountClassification();

            responseMessage.responseObj = lstAccountClassificationModel;
            if (Core.clientMessage.get().messageCode == null) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_GET_ACCOUNT_CLASSIFICATION;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.FAILED_TO_GET_ACCOUNT_CLASSIFICATION;
            }
        } catch (Exception ex) {
            log.error("AccountServiceManager -> getAccountClassificationList got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }
        return responseMessage;
    }

    public ResponseMessage getAccount(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<AccountModel> lstAccountModel = new ArrayList<>();
        try {
            Integer businessID = requestMessage.businessID;

            lstAccountModel = this.accountBllManager.getAllAccount(businessID);

            responseMessage.responseObj = lstAccountModel;
            if (Core.clientMessage.get().messageCode == null) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_GET_ACCOUNT;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.FAILED_TO_GET_ACCOUNT;
            }

        } catch (Exception ex) {
            log.error("AccountServiceManager -> getAccount got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }
        return responseMessage;
    }

    public ResponseMessage getDeActiveAccount(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<AccountModel> lstAccountModel = new ArrayList<>();
        try {
            Integer businessID = requestMessage.businessID;

            lstAccountModel = this.accountBllManager.getAllDeActiveAccount(businessID);

            responseMessage.responseObj = lstAccountModel;
            if (Core.clientMessage.get().messageCode == null) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_GET_ACCOUNT;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.FAILED_TO_GET_ACCOUNT;
            }

        } catch (Exception ex) {
            log.error("AccountServiceManager -> getDeActiveAccount got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }
        return responseMessage;
    }

    public ResponseMessage saveAccount(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        VMOpeningBalance vmOpeningBalance = new VMOpeningBalance();
        try {
            String userID = requestMessage.userID;
            Integer businessID = requestMessage.businessID;
            vmOpeningBalance = Core.getRequestObject(requestMessage, VMOpeningBalance.class);

            /*Set<ConstraintViolation<AccountModel>> violations = this.validator.validate(this.accountModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            vmOpeningBalance.accountModel.setBusinessID(requestMessage.businessID);
            vmOpeningBalance.accountModel = this.accountBllManager.saveAccountWithBusinessLogic(vmOpeningBalance.accountModel);

            responseMessage.responseObj = vmOpeningBalance;
            if (Core.clientMessage.get().messageCode == null) {

                if (vmOpeningBalance.openingBalanceModel.getAmount() != null && vmOpeningBalance.openingBalanceModel.getAmount() > 0) {
                    vmOpeningBalance.openingBalanceModel.setBusinessID(requestMessage.businessID);
                    vmOpeningBalance.openingBalanceModel.setAccountID(vmOpeningBalance.accountModel.getAccountID());
                    this.openingBalanceBllManager.saveOpeningBalance(vmOpeningBalance.openingBalanceModel);
                }


                if (Core.clientMessage.get().messageCode == null) {

                    List<RememberNoteModel> lstRememberNote = new ArrayList<>();

                    for (RememberNoteModel rememberNoteModel : vmOpeningBalance.lstRememberNoteModel) {
                        if (rememberNoteModel.getSubject() != "") {
                            rememberNoteModel.setReferenceType(RememberNoteReferenceType.Account.get());
                            rememberNoteModel.setStatus(TillBoxAppEnum.Status.Active.get());
                            rememberNoteModel.setReferenceID(vmOpeningBalance.accountModel.getAccountID());
                            rememberNoteModel.setBusinessID(businessID);
                            lstRememberNote.add(rememberNoteModel);
                        }
                    }

                    this.rememberNoteBllManager.saveRememberNote(lstRememberNote);

                    responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                    responseMessage.message = MessageConstant.SUCCESSFULLY_SAVE_ACCOUNT;
                    this.commit();
                }
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.FAILED_TO_SAVE_ACCOUNT;
                } else {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("AccountServiceManager -> saveAccount got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }
        return responseMessage;
    }

    public ResponseMessage getAllAccountWithOpeningBalance(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<AccountModel> lstAccountModel = new ArrayList<>();
        List<OpeningBalanceModel> lstOpeningBalanceModel = new ArrayList<>();
        List<VMAccountWithOpeningBalance> lstVmAccountWithOpeningBalance = new ArrayList<>();
        try {
            Integer businessID = requestMessage.businessID;

            //get all account of this business
            lstAccountModel = this.accountBllManager.getAllAccount(businessID);
            if (Core.clientMessage.get().messageCode != null) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.FAILED_TO_GET_ACCOUNT_WITH_OPENING_BALANCE;
                return responseMessage;
            }
            //get all opening balance of this business
            lstOpeningBalanceModel = this.openingBalanceBllManager.getAllOpeningBalance(businessID);

            //make vm
            for (AccountModel accountModel : lstAccountModel) {
                AccountModel searchAccountModel = new AccountModel();
                searchAccountModel.setAccountTypeID(accountModel.getAccountTypeID());
                searchAccountModel.setAccountClassificationID(accountModel.getAccountClassificationID());
                searchAccountModel.setAccountID(accountModel.getAccountID());

                VMAccountWithOpeningBalance vmAccountWithOpeningBalance = new VMAccountWithOpeningBalance();
                vmAccountWithOpeningBalance.accountModel = accountModel;

                OpeningBalanceModel openingBalanceModel = new OpeningBalanceModel();
                openingBalanceModel.setAccountID(accountModel.getAccountID());
                double balance = this.journalBllManager.getAvailableBalanceByAccountAndBusinessID(searchAccountModel, requestMessage.businessID);
                openingBalanceModel.setAmount(balance);
                vmAccountWithOpeningBalance.openingBalanceModel = openingBalanceModel;
                lstVmAccountWithOpeningBalance.add(vmAccountWithOpeningBalance);
            }

            responseMessage.responseObj = lstVmAccountWithOpeningBalance;
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
            responseMessage.message = MessageConstant.SUCCESSFULLY_GET_ACCOUNT_WITH_OPENING_BALANCE;

        } catch (Exception ex) {
            log.error("AccountServiceManager -> getAccountWithOpeningBalance got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }
        return responseMessage;
    }

    public ResponseMessage getRootAccount(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        AccountModel accountModel = new AccountModel();
        try {
            Integer businessID = requestMessage.businessID;
            accountModel = Core.getRequestObject(requestMessage, AccountModel.class);

            accountModel = this.accountBllManager.getRootAccount(accountModel);

            responseMessage.responseObj = accountModel;
            if (Core.clientMessage.get().messageCode == null) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_GET_ACCOUNT_ROOT;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.FAILED_TO_GET_ACCOUNT_ROOT;
            }

        } catch (Exception ex) {
            log.error("AccountServiceManager -> getRootAccount got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }
        return responseMessage;
    }


    private List<TaxCodeModel> getAllTaxCode() {
        List lstTaxCode = new ArrayList<>();
        TaxCodeModel taxCodeModel = new TaxCodeModel();
        List<TaxCodeModel> finalList = new ArrayList<>();

        MqttClient mqttClient;
        RequestMessage reqMessForWorker;
        ResponseMessage responseMessage;
        boolean workCompleteWithInAllowTime;
        try {

            Object lockObject = new Object();
            this.barrier = TillBoxUtils.getBarrier(1, lockObject);

            CallBack callBack;
            reqMessForWorker = Core.getDefaultWorkerRequestMessage();

            String pubTopic = WorkerSubscriptionConstants.WORKER_USER_REGISTRATION_MODULE_TOPIC;
            reqMessForWorker.brokerMessage.serviceName = "api/taxCode/get";
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
                    finalList = Core.convertResponseToList(responseMessage, taxCodeModel);
                } else {
                    //timeout
                    //TODO Implement role back logic
                }
            }
            this.closeBrokerClient(mqttClient, reqMessForWorker.brokerMessage.messageId);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("AccountServiceManager -> inter module communication getAllTaxCode got exception");
        }
        return finalList;
    }
}
