package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.coreConstant.AccountConstant;
import nybsys.tillboxweb.coreEnum.AccountType;
import nybsys.tillboxweb.coreEnum.DebitCreditIndicator;
import nybsys.tillboxweb.coreEnum.PartyType;
import nybsys.tillboxweb.coreEnum.ReferenceType;
import nybsys.tillboxweb.coreModels.CurrencyModel;
import nybsys.tillboxweb.coreUtil.CoreUtils;
import nybsys.tillboxweb.entities.MoneyTransfer;
import nybsys.tillboxweb.models.AccountModel;
import nybsys.tillboxweb.models.MoneyTransferModel;
import nybsys.tillboxweb.models.JournalModel;
import nybsys.tillboxweb.models.MoneyTransferModel;
import nybsys.tillboxweb.utils.AccountingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MoneyTransferBllManager extends BaseBll<MoneyTransfer> {
    private static final Logger log = LoggerFactory.getLogger(MoneyTransferBllManager.class);

    @Autowired
    private AccountBllManager accountBllManager;
    
    @Autowired
    private JournalBllManager journalBllManager;

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(MoneyTransfer.class);
        Core.runTimeModelType.set(MoneyTransferModel.class);
    }

    public MoneyTransferModel saveOrUpdateWithBusinessLogic(MoneyTransferModel moneyTransferModelReq, Integer businessID, CurrencyModel currencyModel, Integer entryCurrencyID) throws Exception {
        MoneyTransferModel moneyTransferModel = new MoneyTransferModel();
        List<MoneyTransferModel>  lstMoneyTransferModel;
        AccountModel fromAccountModel = new AccountModel();
        AccountModel toAccountModel = new AccountModel();
        AccountModel dummyAccountModel = new AccountModel();
        Boolean isExecutionTypeSave = true;
        try {
            
            moneyTransferModel = moneyTransferModelReq;
            
            // check from and to account is same or not
            if (moneyTransferModel.getAccountIDFrom() == moneyTransferModel.getAccountIDTo()) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FROM_AND_TO_ACCOUNT_CAN_NOT_SAME;
                Core.clientMessage.get().userMessage = MessageConstant.FROM_AND_TO_ACCOUNT_CAN_NOT_SAME;
                return moneyTransferModel;
            }
            //get from account
            fromAccountModel = this.accountBllManager.getAccountByAccountID(moneyTransferModel.getAccountIDFrom());
            if (fromAccountModel == null) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FROM_ACCOUNT_NOT_FOUND;
                Core.clientMessage.get().userMessage = MessageConstant.FROM_ACCOUNT_NOT_FOUND;
                return moneyTransferModel;
            }
            //get to account
            toAccountModel = this.accountBllManager.getAccountByAccountID(moneyTransferModel.getAccountIDTo());
            if (toAccountModel == null) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.TO_ACCOUNT_NOT_FOUND;
                Core.clientMessage.get().userMessage = MessageConstant.TO_ACCOUNT_NOT_FOUND;
                return moneyTransferModel;
            }
            //check money transfer account condition
            if (fromAccountModel.getAccountTypeID() == toAccountModel.getAccountTypeID() && fromAccountModel.getAccountTypeID() == AccountType.CurrentAsset.get()) {
                //check available balance
                Double availableBalance = 0.0;
                availableBalance = this.journalBllManager.getAvailableBalanceByAccountAndBusinessID(fromAccountModel, businessID);
                Core.clientMessage.get().messageCode = null;
                if (moneyTransferModel.getAmount() > availableBalance) {
                    Core.clientMessage.get().message = MessageConstant.INSUFFICIENT_BALANCE;
                    Core.clientMessage.get().userMessage = MessageConstant.INSUFFICIENT_BALANCE;
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                } else {
                    //save money transfer
                    if (moneyTransferModel.getMoneyTransferID() == null || moneyTransferModel.getMoneyTransferID() == 0) {
                        //add currency
                        moneyTransferModel.setBaseCurrencyID(currencyModel.getCurrencyID());
                        moneyTransferModel.setEntryCurrencyID(entryCurrencyID);
                        moneyTransferModel.setBaseCurrencyAmount(availableBalance * moneyTransferModel.getExchangeRate());

                        // ============================= Create MTR0000001 =============================
                        String currentDBSequence = null, buildDbSequence, hsql;
                        hsql = "SELECT mt FROM MoneyTransfer mt ORDER BY mt.moneyTransferID DESC";
                        lstMoneyTransferModel = this.executeHqlQuery(hsql, MoneyTransferModel.class, TillBoxAppEnum.QueryType.GetOne.get());
                        if (lstMoneyTransferModel.size() > 0) {
                            currentDBSequence = lstMoneyTransferModel.get(0).getDocNumber();
                        }
                        buildDbSequence = CoreUtils.getSequence(currentDBSequence, "MTR");
                        // ==========================End Create MTR0000001 =============================

                        moneyTransferModel.setBusinessID(businessID);
                        moneyTransferModel.setDocNumber(buildDbSequence);
                        moneyTransferModel = this.save(moneyTransferModel);
                        if (moneyTransferModel == null) {
                            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                            Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_MONEY_TRANSFER;
                        }
                    }else{
                        isExecutionTypeSave = false;
                        moneyTransferModel = this.update(moneyTransferModel);
                        if (moneyTransferModel == null) {
                            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                            Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_MONEY_TRANSFER;
                        }
                    }

                    // save journal
                    if(!isExecutionTypeSave){
                        //delete the previous journal for update
                       JournalModel whereCondition = new JournalModel();
                       whereCondition.setReferenceID(moneyTransferModel.getMoneyTransferID());
                       whereCondition.setReferenceType(ReferenceType.MoneyTransfer.get());
                        this.journalBllManager.deleteJournalByCondition(whereCondition);
                    }
                    List<JournalModel> lstJournalModel = new ArrayList<>();
                    JournalModel drJournalModel = new JournalModel();
                    JournalModel crJournalModel = new JournalModel();

                    //build dr model
                    drJournalModel.setAccountID(toAccountModel.getAccountID());
                    drJournalModel.setAmount(moneyTransferModel.getAmount());
                    drJournalModel.setBusinessID(businessID);
                    drJournalModel.setDrCrIndicator(DebitCreditIndicator.Debit.get());
                    drJournalModel.setNote(moneyTransferModel.getNote());
                    drJournalModel.setReferenceID(toAccountModel.getAccountID());
                    drJournalModel.setReferenceType(ReferenceType.MoneyTransfer.get());

                    //check party condition
                    dummyAccountModel = this.accountBllManager.getRootAccount(fromAccountModel);
                    if (dummyAccountModel == null) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_MONEY_TRANSFER;
                        Core.clientMessage.get().userMessage = MessageConstant.FAILED_TO_SAVE_MONEY_TRANSFER;
                        //roll back
                        return moneyTransferModel;
                    } else if (dummyAccountModel.getAccountID().intValue() == AccountConstant.BANK_ACCOUNT_ID.intValue()) {
                        drJournalModel.setPartyID(dummyAccountModel.getAccountID());
                        drJournalModel.setPartyType(PartyType.Bank.get());
                    }

                    //add currency
                    drJournalModel.setBaseCurrencyID(moneyTransferModel.getBaseCurrencyID());
                    drJournalModel.setBaseCurrencyAmount(moneyTransferModel.getAmount() * moneyTransferModel.getExchangeRate());
                    drJournalModel.setExchangeRate(moneyTransferModel.getExchangeRate());
                    drJournalModel.setEntryCurrencyID(moneyTransferModel.getEntryCurrencyID());

                    //build cr model
                    crJournalModel.setAccountID(fromAccountModel.getAccountID());
                    crJournalModel.setAmount(moneyTransferModel.getAmount());
                    crJournalModel.setBusinessID(businessID);
                    crJournalModel.setDrCrIndicator(DebitCreditIndicator.Credit.get());
                    crJournalModel.setNote(moneyTransferModel.getNote());
                    crJournalModel.setReferenceID(fromAccountModel.getAccountID());
                    crJournalModel.setReferenceType(ReferenceType.MoneyTransfer.get());

                    //check party condition
                    dummyAccountModel = this.accountBllManager.getRootAccount(fromAccountModel);
                    if (dummyAccountModel == null) {
                        //to do return
                    } else if (dummyAccountModel.getAccountID().intValue() == AccountConstant.BANK_ACCOUNT_ID.intValue()) {
                        crJournalModel.setPartyID(dummyAccountModel.getAccountID());
                        crJournalModel.setPartyType(AccountConstant.BANK_ACCOUNT_ID);
                    }

                    //add currency
                    crJournalModel.setBaseCurrencyID(moneyTransferModel.getBaseCurrencyID());
                    crJournalModel.setBaseCurrencyAmount(moneyTransferModel.getAmount() * moneyTransferModel.getExchangeRate());
                    crJournalModel.setExchangeRate(moneyTransferModel.getExchangeRate());
                    crJournalModel.setEntryCurrencyID(moneyTransferModel.getEntryCurrencyID());

                    // add double entry
                    lstJournalModel.add(drJournalModel);
                    lstJournalModel.add(crJournalModel);
                    Core.clientMessage.get().messageCode = null;
                    Core.clientMessage.get().message = "";
                    boolean flag = this.journalBllManager.saveOrUpdateJournalWithBusinessLogic(lstJournalModel, businessID);

                    if (Core.clientMessage.get().messageCode != null) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        if (Core.clientMessage.get().message == null) {
                            Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_MONEY_TRANSFER;
                            Core.clientMessage.get().userMessage = MessageConstant.FAILED_TO_SAVE_MONEY_TRANSFER;
                        }
                    }
                }

            } else {
                Core.clientMessage.get().message = MessageConstant.MONEY_TRANSFER_NOT_VALID_FOR_TO_OR_FROM_ACCOUNT;
                Core.clientMessage.get().userMessage = MessageConstant.MONEY_TRANSFER_NOT_VALID_FOR_TO_OR_FROM_ACCOUNT;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) { log.error("MoneyTransferBllManager -> saveOrUpdateWithBusinessLogic got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return moneyTransferModel;
    }

    public List<MoneyTransferModel> getAllMoneyTransfer(Integer businessID) throws Exception {
        List<MoneyTransferModel> lstMoneyTransferModel = new ArrayList<>();
        MoneyTransferModel whereCondition = new MoneyTransferModel();
        try {
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());
            whereCondition.setBusinessID(businessID);
            lstMoneyTransferModel = this.getAllByConditions(whereCondition);
            if (lstMoneyTransferModel.size() == 0) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_MONEY_TRANSFER;
            }
        } catch (Exception ex) {
            log.error("MoneyTransferBllManager -> getAllMoneyTransfer got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstMoneyTransferModel;
    }
}
