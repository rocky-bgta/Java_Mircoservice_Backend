/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 08/02/2018
 * Time: 10:33
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.coreEnum.*;
import nybsys.tillboxweb.entities.OpeningBalance;
import nybsys.tillboxweb.models.AccountModel;
import nybsys.tillboxweb.models.JournalModel;
import nybsys.tillboxweb.models.OpeningBalanceModel;
import nybsys.tillboxweb.models.OpeningBalanceUpdateHistoryModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class OpeningBalanceBllManager extends BaseBll<OpeningBalance> {
    private static final Logger log = LoggerFactory.getLogger(OpeningBalanceBllManager.class);
    @Autowired
    private AccountBllManager accountBllManager;
    @Autowired
    private JournalBllManager journalBllManager;
    @Autowired
    private OpeningBalanceUpdateHistoryBllManager openingBalanceUpdateHistoryBllManager;

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(OpeningBalance.class);
        Core.runTimeModelType.set(OpeningBalanceModel.class);
    }

    public OpeningBalanceModel saveOpeningBalance(OpeningBalanceModel openingBalanceModelReq) throws Exception {
        OpeningBalanceModel openingBalanceModel = new OpeningBalanceModel();
        openingBalanceModel = openingBalanceModelReq;
        try {
            if (IsValidOpeningBalance(openingBalanceModelReq)) {

                OpeningBalanceModel existingOpeningBalance = new OpeningBalanceModel();
                // making where condition to get existing opening balance
                if (openingBalanceModelReq.getReferenceType() == null) {
                    openingBalanceModelReq.setReferenceType(ReferenceType.OpeningBalance.get());
                }
                if (openingBalanceModelReq.getReferenceID() == null) {
                    openingBalanceModelReq.setReferenceID(openingBalanceModelReq.getAccountID());
                }
//                if (openingBalanceModelReq.getAccountID() != null) {
//                    existingOpeningBalance.setAccountID(openingBalanceModelReq.getAccountID());
//                }

                OpeningBalanceModel searchOpeningBalanceModel = new OpeningBalanceModel();
                searchOpeningBalanceModel.setAccountID(openingBalanceModelReq.getAccountID());
                existingOpeningBalance = getOpeningBalanceByCondition(searchOpeningBalanceModel);
                if (existingOpeningBalance != null) {
                    existingOpeningBalance.setAmount(openingBalanceModel.getAmount());
                    existingOpeningBalance.setDate(openingBalanceModelReq.getDate());
                    existingOpeningBalance.setNote(openingBalanceModelReq.getNote());
                    existingOpeningBalance.setEntryCurrencyID(openingBalanceModel.getEntryCurrencyID());
                    existingOpeningBalance.setExchangeRate(openingBalanceModel.getExchangeRate());
                    openingBalanceModel = this.update(existingOpeningBalance);

                    List<JournalModel> lstJournalModel = new ArrayList<>();
                    JournalModel journalModel = new JournalModel();
                    journalModel.setReferenceType(ReferenceType.OpeningBalance.get());
                    journalModel.setReferenceID(existingOpeningBalance.getOpeningBalanceID());
                    lstJournalModel = this.journalBllManager.getAllByConditions(journalModel);

                    if (lstJournalModel.size() > 0) {
                        for (JournalModel journalModel1 : lstJournalModel) {
                            journalModel1.setStatus(TillBoxAppEnum.Status.Deleted.get());
                            this.journalBllManager.update(journalModel1);
                        }
                    }
                    Core.clientMessage.get().messageCode = null;
                    if (this.saveJournalInformation(existingOpeningBalance, existingOpeningBalance.getBusinessID())) {
                        Core.clientMessage.get().message = MessageConstant.SUCCESSFULLY_SAVE_OPENING_BALANCE;
                        //save opening balance history;
                        OpeningBalanceUpdateHistoryModel whereConditionOpeningBalanceHistory = new OpeningBalanceUpdateHistoryModel();
                        whereConditionOpeningBalanceHistory.setBusinessID(openingBalanceModel.getBusinessID());
                        whereConditionOpeningBalanceHistory.setReason(openingBalanceModel.getNote());
                        whereConditionOpeningBalanceHistory.setAmount(openingBalanceModel.getAmount());
                        whereConditionOpeningBalanceHistory.setReferenceType(openingBalanceModel.getReferenceType());
                        whereConditionOpeningBalanceHistory.setReferenceID(openingBalanceModel.getReferenceID());

                        whereConditionOpeningBalanceHistory = this.openingBalanceUpdateHistoryBllManager.saveOpeningBalanceUpdateHistory(whereConditionOpeningBalanceHistory);
                        if (whereConditionOpeningBalanceHistory == null) {
                            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                            Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_OPENING_BALANCE;
                            return openingBalanceModel;
                        }
                    } else {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_OPENING_BALANCE;
                        return openingBalanceModel;
                    }

                } else {

                    //openingBalanceModel.setReferenceID(openingBalanceModel.getAccountID());
                    //openingBalanceModel.setReferenceType(ReferenceType.OpeningBalance.get());
                    if (openingBalanceModel.getOpeningBalanceID() == null || openingBalanceModel.getOpeningBalanceID() == 0) {

                        openingBalanceModel = this.save(openingBalanceModel);
                        if (openingBalanceModel == null) {
                            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                            Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_OPENING_BALANCE;
                            return openingBalanceModel;
                        }

                        this.saveJournalInformation(openingBalanceModel, openingBalanceModel.getBusinessID());
                        if (Core.clientMessage.get().messageCode == null) {

                            Core.clientMessage.get().message = MessageConstant.SUCCESSFULLY_SAVE_OPENING_BALANCE;
                            //save opening balance history;
                            OpeningBalanceUpdateHistoryModel whereConditionOpeningBalanceHistory = new OpeningBalanceUpdateHistoryModel();
                            whereConditionOpeningBalanceHistory.setBusinessID(openingBalanceModel.getBusinessID());
                            whereConditionOpeningBalanceHistory.setAmount(openingBalanceModel.getAmount());
                            whereConditionOpeningBalanceHistory.setReason(openingBalanceModel.getNote());
                            whereConditionOpeningBalanceHistory.setReferenceType(openingBalanceModel.getReferenceType());
                            whereConditionOpeningBalanceHistory.setReferenceID(openingBalanceModel.getReferenceID());

                            whereConditionOpeningBalanceHistory = this.openingBalanceUpdateHistoryBllManager.saveOpeningBalanceUpdateHistory(whereConditionOpeningBalanceHistory);
                            if (whereConditionOpeningBalanceHistory == null) {
                                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                                Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_OPENING_BALANCE;
                                return openingBalanceModel;
                            }
                        } else {
                            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                            Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_OPENING_BALANCE;
                            return openingBalanceModel;
                        }

                    } else {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_OPENING_BALANCE;
                        return openingBalanceModel;
                    }
                }
            }
        } catch (Exception ex) {
            log.error("OpeningBalanceBllManager -> saveOpeningBalance got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return openingBalanceModel;
    }

    private boolean saveJournalInformation(OpeningBalanceModel openingBalanceModel, Integer businessID) throws Exception {

        try {
            String journalReferenceNo = java.util.UUID.randomUUID().toString();
            AccountModel accountModel = new AccountModel();
            accountModel = this.accountBllManager.getById(openingBalanceModel.getAccountID());
            List<JournalModel> lstJournalModel = new ArrayList<>();
            if (accountModel.getAccountClassificationID() == AccountClassification.Asset.get()) {
                //Debit journal
                JournalModel journalModelDebit = new JournalModel();
                journalModelDebit.setBusinessID(openingBalanceModel.getBusinessID());
                journalModelDebit.setAmount(openingBalanceModel.getAmount());
                journalModelDebit.setAccountID(openingBalanceModel.getAccountID());
                journalModelDebit.setReferenceID(openingBalanceModel.getOpeningBalanceID());
                journalModelDebit.setReferenceType(ReferenceType.OpeningBalance.get());
                journalModelDebit.setDrCrIndicator(DebitCreditIndicator.Debit.get());

                if (openingBalanceModel.getReferenceType().intValue() == ReferenceType.CustomerOpeningBalance.get()) {
                    journalModelDebit.setPartyID(openingBalanceModel.getReferenceID());
                    journalModelDebit.setPartyType(PartyType.Customer.get());
                } else if (openingBalanceModel.getReferenceType().intValue() == ReferenceType.SupplierOpeningBalance.get()) {
                    journalModelDebit.setPartyID(openingBalanceModel.getReferenceID());
                    journalModelDebit.setPartyType(PartyType.Supplier.get());
                } else if (openingBalanceModel.getReferenceType().intValue() == PartyType.Bank.get()) {
                    journalModelDebit.setPartyID(openingBalanceModel.getReferenceID());
                    journalModelDebit.setPartyType(PartyType.Bank.get());
                }

                journalModelDebit.setDate(new Date());
                journalModelDebit.setJournalReferenceNo(journalReferenceNo);

                journalModelDebit.setBaseCurrencyID(openingBalanceModel.getBaseCurrencyID());
                journalModelDebit.setEntryCurrencyID(openingBalanceModel.getEntryCurrencyID());
                journalModelDebit.setExchangeRate(openingBalanceModel.getExchangeRate());
                journalModelDebit.setBaseCurrencyAmount(openingBalanceModel.getBaseCurrencyAmount());

                //Credit journal
                JournalModel journalModelCredit = new JournalModel();
                journalModelCredit.setBusinessID(openingBalanceModel.getBusinessID());
                journalModelCredit.setAmount(openingBalanceModel.getAmount());
                journalModelCredit.setAccountID(DefaultCOA.HistoricalBalance.get());
                journalModelCredit.setReferenceID(openingBalanceModel.getOpeningBalanceID());
                journalModelCredit.setReferenceType(ReferenceType.OpeningBalance.get());
                journalModelCredit.setDrCrIndicator(DebitCreditIndicator.Credit.get());
                journalModelCredit.setDate(new Date());
                journalModelCredit.setJournalReferenceNo(journalReferenceNo);

                journalModelCredit.setBaseCurrencyID(openingBalanceModel.getBaseCurrencyID());
                journalModelCredit.setEntryCurrencyID(openingBalanceModel.getEntryCurrencyID());
                journalModelCredit.setExchangeRate(openingBalanceModel.getExchangeRate());
                journalModelCredit.setBaseCurrencyAmount(openingBalanceModel.getBaseCurrencyAmount());

                lstJournalModel.add(journalModelDebit);
                lstJournalModel.add(journalModelCredit);

            } else if (accountModel.getAccountClassificationID() == AccountClassification.Liability.get() || accountModel.getAccountClassificationID() == AccountClassification.OwnerEquities.get()) {

                //Debit journal
                JournalModel journalModelDebit = new JournalModel();
                journalModelDebit.setBusinessID(openingBalanceModel.getBusinessID());
                journalModelDebit.setAmount(openingBalanceModel.getAmount());
                journalModelDebit.setAccountID(DefaultCOA.HistoricalBalance.get());
                journalModelDebit.setReferenceID(openingBalanceModel.getOpeningBalanceID());
                journalModelDebit.setReferenceType(ReferenceType.OpeningBalance.get());
                journalModelDebit.setDrCrIndicator(DebitCreditIndicator.Debit.get());
                journalModelDebit.setDate(new Date());
                journalModelDebit.setJournalReferenceNo(journalReferenceNo);

                journalModelDebit.setBaseCurrencyID(openingBalanceModel.getBaseCurrencyID());
                journalModelDebit.setEntryCurrencyID(openingBalanceModel.getEntryCurrencyID());
                journalModelDebit.setExchangeRate(openingBalanceModel.getExchangeRate());
                journalModelDebit.setBaseCurrencyAmount(openingBalanceModel.getBaseCurrencyAmount());

                //Credit journal
                JournalModel journalModelCredit = new JournalModel();
                journalModelCredit.setBusinessID(openingBalanceModel.getBusinessID());
                journalModelCredit.setAmount(openingBalanceModel.getAmount());
                journalModelCredit.setAccountID(openingBalanceModel.getAccountID());
                journalModelCredit.setReferenceID(openingBalanceModel.getOpeningBalanceID());
                journalModelCredit.setReferenceType(ReferenceType.OpeningBalance.get());
                journalModelCredit.setDrCrIndicator(DebitCreditIndicator.Credit.get());

                journalModelCredit.setBaseCurrencyID(openingBalanceModel.getBaseCurrencyID());
                journalModelCredit.setEntryCurrencyID(openingBalanceModel.getEntryCurrencyID());
                journalModelCredit.setExchangeRate(openingBalanceModel.getExchangeRate());
                journalModelCredit.setBaseCurrencyAmount(openingBalanceModel.getBaseCurrencyAmount());

                if (openingBalanceModel.getReferenceType().intValue() == ReferenceType.CustomerOpeningBalance.get()) {
                    journalModelCredit.setPartyID(openingBalanceModel.getReferenceID());
                    journalModelCredit.setPartyType(PartyType.Customer.get());
                } else if (openingBalanceModel.getReferenceType().intValue() == ReferenceType.SupplierOpeningBalance.get()) {
                    journalModelCredit.setPartyID(openingBalanceModel.getReferenceID());
                    journalModelCredit.setPartyType(PartyType.Supplier.get());
                }
                journalModelCredit.setDate(new Date());
                journalModelCredit.setJournalReferenceNo(journalReferenceNo);

                lstJournalModel.add(journalModelDebit);
                lstJournalModel.add(journalModelCredit);
            }
            this.journalBllManager.saveOrUpdateJournalWithBusinessLogic(lstJournalModel, businessID);
            return true;


        } catch (
                Exception ex)

        {
            log.error("OpeningBalanceBllManager -> saveJournalInformation got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

    }

    private boolean IsValidOpeningBalance(OpeningBalanceModel openingBalanceModel) throws Exception {
        try {

            AccountModel accountModel = new AccountModel();
            List<AccountModel> lstAccountModel = new ArrayList<>();

            accountModel.setAccountID(openingBalanceModel.getAccountID());
            lstAccountModel = this.accountBllManager.getAllByConditions(accountModel);

            if (lstAccountModel.size() > 0) {
                accountModel = lstAccountModel.get(0);
//                if (accountModel.getDefault() && (accountModel.getAccountID().intValue() != TillBoxAppEnum.DefaultCOA.TradeCreditors.get()
//                        && accountModel.getAccountID().intValue() != TillBoxAppEnum.DefaultCOA.TradeDebtors.get())) {
//                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
//                    Core.clientMessage.get().userMessage = MessageConstant.YOU_CAN_NOT_GIVE_OPENING_BALANCE_TO_THIS_ACCOUNT;
//                    return false;
//                }
            } else {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().userMessage = MessageConstant.INVALID_ACCOUNT;
                return false;
            }

            if (!isValidAccountType(accountModel.getAccountClassificationID())) {

                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().userMessage = MessageConstant.INVALID_ACCOUNT_TYPE;
                return false;
            }
        } catch (Exception ex) {
            log.error("OpeningBalanceBllManager -> IsValidOpeningBalance got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return true;
    }

    private Boolean isValidAccountType(int accountClassificaitonID) {

        if (accountClassificaitonID == AccountClassification.Asset.get()) {
            return true;
        }
        if (accountClassificaitonID == AccountClassification.Liability.get()) {
            return true;
        }
        if (accountClassificaitonID == AccountClassification.OwnerEquities.get()) {
            return true;
        }

        return false;
    }

    public OpeningBalanceModel getOpeningBalanceByAccountID(Integer accountID) throws Exception {
        List<OpeningBalanceModel> lstOpeningBalanceModel = new ArrayList<>();
        OpeningBalanceModel openingBalanceModel = new OpeningBalanceModel();
        try {
            openingBalanceModel.setAccountID(accountID);
            openingBalanceModel.setStatus(TillBoxAppEnum.Status.Active.get());
            lstOpeningBalanceModel = this.getAllByConditions(openingBalanceModel);
            if (lstOpeningBalanceModel.size() == 0) {
                openingBalanceModel = null;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_OPENING_BALANCE;
            } else {
                openingBalanceModel = lstOpeningBalanceModel.get(0);
            }
        } catch (Exception ex) {
            log.error("OpeningBalanceBllManager -> getOpeningBalanceByAccountID got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return openingBalanceModel;
    }

    public OpeningBalanceModel getOpeningBalanceByCondition(OpeningBalanceModel openingBalanceModelReq) throws Exception {
        List<OpeningBalanceModel> lstOpeningBalanceModel = new ArrayList<>();
        OpeningBalanceModel openingBalanceModel;
        try {
            openingBalanceModel = openingBalanceModelReq;
            openingBalanceModel.setStatus(TillBoxAppEnum.Status.Active.get());
            lstOpeningBalanceModel = this.getAllByConditions(openingBalanceModel);
            if (lstOpeningBalanceModel.size() == 0) {
                openingBalanceModel = null;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_OPENING_BALANCE;
            } else {
                openingBalanceModel = lstOpeningBalanceModel.get(0);
            }
        } catch (Exception ex) {
            log.error("OpeningBalanceBllManager -> getOpeningBalanceByAccountID got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return openingBalanceModel;
    }

    public List<OpeningBalanceModel> getAllOpeningBalance(Integer businessID) throws Exception {
        List<OpeningBalanceModel> lstOpeningBalanceModel = new ArrayList<>();
        OpeningBalanceModel whereCondition = new OpeningBalanceModel();

        try {
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());
            whereCondition.setBusinessID(businessID);
            lstOpeningBalanceModel = this.getAllByConditions(whereCondition);
            if (lstOpeningBalanceModel.size() == 0) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_OPENING_BALANCE;
            }
        } catch (Exception ex) {
            log.error("OpeningBalanceBllManager -> getAllOpeningBalance got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstOpeningBalanceModel;
    }

    public Boolean deleteOpeningBalance(OpeningBalanceModel openingBalanceModelReq) throws Exception {
        OpeningBalanceModel whereCondition = new OpeningBalanceModel();
        OpeningBalanceModel openingBalanceModel = new OpeningBalanceModel();
        Integer numberOfRowDeleted = 0;

        try {
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());
            whereCondition.setBusinessID(openingBalanceModelReq.getBusinessID());
            whereCondition.setReferenceType(openingBalanceModelReq.getReferenceType());
            whereCondition.setReferenceID(openingBalanceModelReq.getReferenceID());

            openingBalanceModel.setStatus(TillBoxAppEnum.Status.Deleted.get());

            numberOfRowDeleted = this.updateByConditions(whereCondition, openingBalanceModel);
            if (numberOfRowDeleted == 0) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_OPENING_BALANCE;
                return false;
            }
            //delete opening balance history
            if (!this.openingBalanceUpdateHistoryBllManager.deleteOpeningBalanceHistory(openingBalanceModelReq.getBusinessID(), openingBalanceModelReq.getReferenceID(), openingBalanceModelReq.getReferenceType())) {
                return false;
            }


        } catch (Exception ex) {
            log.error("OpeningBalanceBllManager -> deleteOpeningBalance got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return true;
    }
}
