/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/8/2018
 * Time: 10:20 AM
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
import nybsys.tillboxweb.coreEnum.AccountType;
import nybsys.tillboxweb.coreEnum.DebitCreditIndicator;
import nybsys.tillboxweb.coreEnum.ReferenceType;
import nybsys.tillboxweb.coreModels.CurrencyModel;
import nybsys.tillboxweb.coreUtil.CoreUtils;
import nybsys.tillboxweb.entities.CombineAccount;
import nybsys.tillboxweb.models.AccountModel;
import nybsys.tillboxweb.models.CombineAccountModel;
import nybsys.tillboxweb.models.JournalModel;
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
public class CombineAccountBllManager extends BaseBll<CombineAccount> {
    private static final Logger log = LoggerFactory.getLogger(CombineAccountBllManager.class);
    @Autowired
    private AccountBllManager accountBllManager;

    @Autowired
    private JournalBllManager journalBllManager;

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(CombineAccount.class);
        Core.runTimeModelType.set(CombineAccountModel.class);
    }

    public CombineAccountModel saveCombineAccount(CombineAccountModel combineAccountModel, CurrencyModel currencyModel, Integer entryCurrencyID) throws Exception {
        AccountModel accountModel = new AccountModel();
        List<CombineAccountModel> lstCombineAccountModel;
        try {
            if (IsValidCombineAccount(combineAccountModel)) {
                accountModel = this.accountBllManager.getById(combineAccountModel.getAccountIDFrom());
                double availableBalance = this.journalBllManager.getAvailableBalanceByAccountAndBusinessID(accountModel, combineAccountModel.getBusinessID());

//                if (combineAccountModel.getCombineAccountID() != null && combineAccountModel.getCombineAccountID() > 0) {
//                    CombineAccountModel existingCombineAccount = this.getById(combineAccountModel.getCombineAccountID());
//                    if (existingCombineAccount != null) {
//                        availableBalance = availableBalance - existingCombineAccount.getAmount();
//                    }
//                }


                // ============================= Create COM0000001 =============================
                String currentDBSequence = null, buildDbSequence, hsql;
                hsql = "SELECT ca FROM CombineAccount ca ORDER BY ca.combineAccountID DESC";
                lstCombineAccountModel = this.executeHqlQuery(hsql, CombineAccountModel.class, TillBoxAppEnum.QueryType.GetOne.get());
                if (lstCombineAccountModel.size() > 0) {
                    currentDBSequence = lstCombineAccountModel.get(0).getDocNumber();
                }
                buildDbSequence = CoreUtils.getSequence(currentDBSequence, "COM");
                // ==========================End Create COM0000001 =============================

                combineAccountModel.setAmount(availableBalance);
                combineAccountModel.setDocNumber(buildDbSequence);
                //add currency
                combineAccountModel.setBaseCurrencyID(currencyModel.getCurrencyID());
                combineAccountModel.setEntryCurrencyID(entryCurrencyID);
                combineAccountModel.setBaseCurrencyAmount(availableBalance * combineAccountModel.getExchangeRate());

                combineAccountModel = this.save(combineAccountModel);
                if (combineAccountModel != null) {

                    //journal save only if account has balance
                    if (availableBalance > 0) {
                        saveJournalInformation(combineAccountModel, combineAccountModel.getBusinessID());
                    }
//                    AccountModel accountModelFrom = new AccountModel();
//                    accountModelFrom.setAccountID(combineAccountModel.getAccountIDFrom());
//                    accountModelFrom = this.accountBllManager.getAccountByAccountID(combineAccountModel.getAccountIDFrom());

                    accountModel.setStatus(TillBoxAppEnum.Status.Inactive.get());

                    this.accountBllManager.updateCombineAccount(accountModel);
                    if (Core.clientMessage.get().messageCode == null) {
                        Core.clientMessage.get().messageCode = null;
                        Core.clientMessage.get().userMessage = MessageConstant.SUCCESSFULLY_COMBINE_ACCOUNT;
                    } else {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().userMessage = MessageConstant.FAILED_TO_SAVE_COMBINE_ACCOUNT;
                    }

                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().userMessage = MessageConstant.FAILED_TO_SAVE_COMBINE_ACCOUNT;
                }
            }

        } catch (Exception ex) {
            log.error("CombineAccountBllManager -> save combine account got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return combineAccountModel;
    }

    private void saveJournalInformation(CombineAccountModel combineAccountModel, Integer businessID) throws Exception {

        try {
            String journalReferenceNo = java.util.UUID.randomUUID().toString();
            AccountModel accountModel;

            if (Core.clientMessage.get().messageCode == null) {
                //Debit journal
                JournalModel journalModelDebit = new JournalModel();
                journalModelDebit.setBusinessID(combineAccountModel.getBusinessID());
                journalModelDebit.setAmount(combineAccountModel.getAmount());
                journalModelDebit.setAccountID(combineAccountModel.getAccountIDTo());
                journalModelDebit.setReferenceID(combineAccountModel.getCombineAccountID());
                journalModelDebit.setReferenceType(ReferenceType.CombineAccount.get());
                journalModelDebit.setDrCrIndicator(DebitCreditIndicator.Debit.get());
                journalModelDebit.setDate(new Date());
                journalModelDebit.setJournalReferenceNo(journalReferenceNo);

                //add currency
                journalModelDebit.setBaseCurrencyID(combineAccountModel.getBaseCurrencyID());
                journalModelDebit.setBaseCurrencyAmount(combineAccountModel.getAmount() * combineAccountModel.getExchangeRate());
                journalModelDebit.setExchangeRate(combineAccountModel.getExchangeRate());
                journalModelDebit.setEntryCurrencyID(combineAccountModel.getEntryCurrencyID());

                //Credit journal
                JournalModel journalModelCredit = new JournalModel();
                journalModelCredit.setBusinessID(combineAccountModel.getBusinessID());
                journalModelCredit.setAmount(combineAccountModel.getAmount());
                journalModelCredit.setAccountID(combineAccountModel.getAccountIDFrom());
                journalModelCredit.setReferenceID(combineAccountModel.getCombineAccountID());
                journalModelCredit.setReferenceType(ReferenceType.CombineAccount.get());
                journalModelCredit.setDrCrIndicator(DebitCreditIndicator.Credit.get());
                journalModelCredit.setDate(new Date());
                journalModelCredit.setJournalReferenceNo(journalReferenceNo);

                //add currency
                journalModelCredit.setBaseCurrencyID(combineAccountModel.getBaseCurrencyID());
                journalModelCredit.setBaseCurrencyAmount(combineAccountModel.getAmount() * combineAccountModel.getExchangeRate());
                journalModelCredit.setExchangeRate(combineAccountModel.getExchangeRate());
                journalModelCredit.setEntryCurrencyID(combineAccountModel.getEntryCurrencyID());

                List<JournalModel> lstJournalModel = new ArrayList<>();
                lstJournalModel.add(journalModelDebit);
                lstJournalModel.add(journalModelCredit);
                if (!this.journalBllManager.saveOrUpdateJournalWithBusinessLogic(lstJournalModel, businessID)) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                }
            } else {
                Core.clientMessage.get().messageCode = null;
            }


        } catch (Exception ex) {
            log.error("CombineAccountBllManager -> saveJournalInformation got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

    }


    private boolean IsValidCombineAccount(CombineAccountModel combineAccountModel) throws Exception {
        try {

            AccountModel accountModelFrom = new AccountModel();
            List<AccountModel> lstAccountModel = new ArrayList<>();
            AccountModel accountModelTo = new AccountModel();
            if (combineAccountModel.getAccountIDTo() == combineAccountModel.getAccountIDFrom()) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.SAME_ACCOUNT_CANNOT_BE_COMBINE;
                Core.clientMessage.get().userMessage = MessageConstant.SAME_ACCOUNT_CANNOT_BE_COMBINE;
                return false;
            }

            accountModelFrom.setAccountID(combineAccountModel.getAccountIDFrom());
            lstAccountModel = this.accountBllManager.getAllByConditions(accountModelFrom);
            accountModelTo.setAccountID(combineAccountModel.getAccountIDTo());

            if (lstAccountModel.size() > 0) {
                accountModelFrom = lstAccountModel.get(0);
                if (accountModelFrom.getDefault()) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().userMessage = MessageConstant.DEFAULT_ACCOUNT_CAN_NOT_BE_COMBINE;
                    return false;
                }
            } else {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.INVALID_ACCOUNT;
                Core.clientMessage.get().userMessage = MessageConstant.INVALID_ACCOUNT;
                return false;
            }

            if (accountModelFrom.getStatus() != TillBoxAppEnum.Status.Active.get()) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().userMessage = MessageConstant.INVALID_ACCOUNT;
                return false;
            }

            accountModelTo.setAccountID(combineAccountModel.getAccountIDTo());
            lstAccountModel = this.accountBllManager.getAllByConditions(accountModelTo);


            if (lstAccountModel.size() > 0) {
                accountModelTo = lstAccountModel.get(0);
            } else {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.INVALID_ACCOUNT;
                Core.clientMessage.get().userMessage = MessageConstant.INVALID_ACCOUNT;
                return false;
            }

            if (accountModelFrom.getAccountTypeID() != accountModelTo.getAccountTypeID()) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.COMBINE_ACCOUNT_TYPE_NOT_SAME;
                Core.clientMessage.get().userMessage = MessageConstant.COMBINE_ACCOUNT_TYPE_NOT_SAME;
                return false;
            }

            if (accountModelFrom.getAccountTypeID() != AccountType.CurrentAsset.get() && accountModelFrom.getAccountTypeID() != AccountType.ShortTermLiabilities.get()) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.COMBINE_INVALID_ACCOUNT_TYPE;
                Core.clientMessage.get().userMessage = MessageConstant.COMBINE_INVALID_ACCOUNT_TYPE;
                return false;
            }

        } catch (Exception ex) {
            log.error("CombineAccountBllManager -> IsValidCombineAccount got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return true;
    }

}
