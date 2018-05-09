package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.bll.manager.JournalBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;

import nybsys.tillboxweb.coreConstant.CurrencyConstant;
import nybsys.tillboxweb.coreEnum.PartyType;
import nybsys.tillboxweb.coreModels.CurrencyModel;
import nybsys.tillboxweb.models.*;
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
public class JournalServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(JournalServiceManager.class);
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Autowired
    private JournalBllManager journalBllManager;

    private OpeningBalanceServiceManager openingBalanceServiceManager = new OpeningBalanceServiceManager();


    public ResponseMessage saveJournal(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        VMJournalReqModel vmJournalListModel = new VMJournalReqModel();
        try {

            if (requestMessage.businessID == null || requestMessage.businessID == 0) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SELECT_A_BUSINESS;
                Core.clientMessage.get().userMessage = MessageConstant.SELECT_A_BUSINESS;
                return responseMessage;
            }

            vmJournalListModel = Core.getRequestObject(requestMessage, VMJournalReqModel.class);

            //check model validation of list
            /*for (JournalModel journalModel :lstJournalModel) {
                 Set<ConstraintViolation<JournalModel>> violations = this.validator.validate(journalModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }
            }*/

            this.journalBllManager.saveOrUpdateJournalWithBusinessLogic(vmJournalListModel.lstJournalModel, requestMessage.businessID);

            if (Core.clientMessage.get().messageCode == null) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_SAVE_JOURNAL;
                this.commit();
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.FAILED_TO_SAVE_JOURNAL;
                } else {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }


        } catch (Exception ex) {
            log.error("JournalServiceManager -> saveJournal got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }
        return responseMessage;
    }

    public ResponseMessage saveBypassEntryJournal(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        VMJournalReqModel vmJournalListModel = new VMJournalReqModel();
        CurrencyModel currencyModel;
        try {

            //check business is selected
            if (requestMessage.businessID == null || requestMessage.businessID == 0) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SELECT_A_BUSINESS;
                Core.clientMessage.get().userMessage = MessageConstant.SELECT_A_BUSINESS;
                return responseMessage;
            }

            //get base currency and exchange rate
            currencyModel = this.openingBalanceServiceManager.getBaseCurrency();
            if (currencyModel == null) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = CurrencyConstant.BASE_CURRENT_NOT_FOUND;
                return responseMessage;
            }

            //check entry currency is present if not base currency will be entry currency
            if (requestMessage.entryCurrencyID == null || requestMessage.entryCurrencyID == 0) {
                requestMessage.entryCurrencyID = currencyModel.getCurrencyID();
            }

            vmJournalListModel = Core.getRequestObject(requestMessage, VMJournalReqModel.class);

            //check model validation of list
            /*for (JournalModel journalModel :lstJournalModel) {
                 Set<ConstraintViolation<JournalModel>> violations = this.validator.validate(journalModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }
            }*/

            List<JournalModel> lstJournalModel = new ArrayList<>();
            for (JournalModel journalModel : vmJournalListModel.lstJournalModel) {

                journalModel.setBaseCurrencyID(currencyModel.getCurrencyID());
                journalModel.setEntryCurrencyID(requestMessage.entryCurrencyID);
                journalModel.setBaseCurrencyAmount(journalModel.getAmount() * journalModel.getExchangeRate());

                lstJournalModel.add(journalModel);
            }


            this.journalBllManager.saveOrUpdateJournalWithBusinessLogic(lstJournalModel, requestMessage.businessID);

            if (Core.clientMessage.get().messageCode == null) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_SAVE_JOURNAL;
                this.commit();
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.FAILED_TO_SAVE_JOURNAL;
                } else {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }


        } catch (Exception ex) {
            log.error("JournalServiceManager -> saveJournal got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }
        return responseMessage;
    }

    public ResponseMessage getAvailableBalanceByAccount(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        AccountModel accountModel = new AccountModel();
        Double availableBalance = 0.0;
        try {
            Integer businessID = requestMessage.businessID;
            accountModel = Core.getRequestObject(requestMessage, AccountModel.class);
            availableBalance = this.journalBllManager.getAvailableBalanceByAccountAndBusinessID(accountModel, businessID);

            responseMessage.responseObj = availableBalance;
            //  if (Core.clientMessage.get().messageCode == null) {
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
            responseMessage.message = MessageConstant.SUCCESSFULLY_GET_AVAILABLE_BALANCE;
//            } else {
//                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
//                responseMessage.message = MessageConstant.FAILED_TO_GET_AVAILABLE_BALANCE;
//            }

        } catch (Exception ex) {
            log.error("JournalServiceManager -> getAvailableBalanceByAccountID got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }
        return responseMessage;
    }

    public ResponseMessage getAvailableBalancePartyID(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        JournalModel journalModel = new JournalModel();
        Double availableBalance = 0.0;
        try {
            Integer businessID = requestMessage.businessID;
            journalModel = Core.getRequestObject(requestMessage, JournalModel.class);

            availableBalance = this.journalBllManager.getAvailableBalanceByPartyIDAndBusinessID(journalModel, businessID);

            responseMessage.responseObj = availableBalance;
            if (Core.clientMessage.get().messageCode == null) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_GET_AVAILABLE_BALANCE;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.FAILED_TO_GET_AVAILABLE_BALANCE;
            }

        } catch (Exception ex) {
            log.error("JournalServiceManager -> getAvailableBalancePartyIDAndBusinessID got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }
        return responseMessage;
    }

    public ResponseMessage getSupplierCurrentDue(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        SupplierModel supplierModel = new SupplierModel();
        Double availableDue = 0.0;
        try {
            Integer businessID = requestMessage.businessID;
            supplierModel = Core.getRequestObject(requestMessage, SupplierModel.class);
            availableDue = this.journalBllManager.getCurrentDueByParty(supplierModel.getSupplierID(), PartyType.Supplier.get(), businessID);

            responseMessage.responseObj = availableDue;
            if (Core.clientMessage.get().messageCode == null) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_GET_AVAILABLE_BALANCE;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.FAILED_TO_GET_AVAILABLE_BALANCE;
            }

        } catch (Exception ex) {
            log.error("JournalServiceManager -> getSupplierCurrentDue got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }
        return responseMessage;
    }

    public ResponseMessage getCustomerCurrentDue(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        CustomerModel customerModel = new CustomerModel();
        Double availableDue = 0.0;
        try {
            Integer businessID = requestMessage.businessID;
            customerModel = Core.getRequestObject(requestMessage, CustomerModel.class);
            availableDue = this.journalBllManager.getCurrentDueByParty(customerModel.getCustomerID(), PartyType.Customer.get(), businessID);

            responseMessage.responseObj = availableDue;
            if (Core.clientMessage.get().messageCode == null) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_GET_AVAILABLE_BALANCE;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.FAILED_TO_GET_AVAILABLE_BALANCE;
            }

        } catch (Exception ex) {
            log.error("JournalServiceManager -> getCustomerCurrentDue got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }
        return responseMessage;
    }

    public ResponseMessage search(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        JournalModel journalModel = new JournalModel();
        List<JournalModel> lstJournalModel = new ArrayList<>();
        try {
            journalModel = Core.getRequestObject(requestMessage, JournalModel.class);


            lstJournalModel = this.journalBllManager.search(journalModel);

            responseMessage.responseObj = lstJournalModel;
            if (Core.clientMessage.get().messageCode == null) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_JOURNAL_DATA_FOUND;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.NO_JOURNAL_DATA_FOUND;
            }

        } catch (Exception ex) {
            log.error("JournalServiceManager -> search got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }
        return responseMessage;
    }

    public ResponseMessage dataExistsExcludeOpeningBalance(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        JournalModel journalModel = new JournalModel();
        List<JournalModel> lstJournalModel = new ArrayList<>();
        Boolean dataExists = false;
        try {
            journalModel = Core.getRequestObject(requestMessage, JournalModel.class);


            dataExists = this.journalBllManager.searchExcludeOpeningBalance(journalModel);

            responseMessage.responseObj = dataExists;
            if (Core.clientMessage.get().messageCode == null) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_JOURNAL_DATA_FOUND;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;//also
                responseMessage.message = MessageConstant.NO_JOURNAL_DATA_FOUND;
            }

        } catch (Exception ex) {
            log.error("JournalServiceManager -> search got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }
        return responseMessage;
    }

    public ResponseMessage delete(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        JournalModel journalModel;
        List<JournalModel> lstJournalModel;
        try {
            journalModel = Core.getRequestObject(requestMessage, JournalModel.class);

            lstJournalModel = this.journalBllManager.deleteJournalByCondition(journalModel);

            responseMessage.responseObj = lstJournalModel;
            if (Core.clientMessage.get().messageCode == null) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.JOURNAL_DELETE_FAILED;
                this.rollBack();
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.JOURNAL_DELETE_SUCCESSFUL;
                this.commit();
            }

        } catch (Exception ex) {
            log.error("JournalServiceManager -> delete got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }
        return responseMessage;
    }
}
