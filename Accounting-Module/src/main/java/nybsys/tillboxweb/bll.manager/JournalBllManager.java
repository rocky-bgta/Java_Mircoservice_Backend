package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.Enum.AccountingEnum;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.coreEnum.DebitCreditIndicator;
import nybsys.tillboxweb.coreEnum.PartyType;
import nybsys.tillboxweb.coreModels.FinancialYearModel;
import nybsys.tillboxweb.entities.Journal;
import nybsys.tillboxweb.models.AccountModel;
import nybsys.tillboxweb.models.JournalModel;
import nybsys.tillboxweb.utils.AccountingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class JournalBllManager extends BaseBll<Journal> {
    private static final Logger log = LoggerFactory.getLogger(JournalBllManager.class);
    @Autowired
    private FinancialYearBllManager financialYearBllManager;

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(Journal.class);
        Core.runTimeModelType.set(JournalModel.class);
    }

    public static class JournalBalanceResult {
        public Double amountSum;
        public Integer drCrIndicator;
    }


    private JournalModel saveJournal(JournalModel journalModelReq, FinancialYearModel financialYearModel) throws Exception {
        JournalModel journalModel = new JournalModel();
        try {
            journalModel = journalModelReq;

            journalModel.setPeriod(AccountingUtils.periodCalculator(financialYearModel));
            journalModel.setFinancialYearID(financialYearModel.getFinancialYearID());
            journalModel = this.save(journalModel);
            if (journalModel != null) {
                Core.clientMessage.get().messageCode = null;
            } else {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_JOURNAL;
            }

        } catch (Exception ex) {
            log.error("JournalBllManager -> saveJournal got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return journalModel;
    }

    private JournalModel updateJournal(JournalModel journalModelReq) throws Exception {
        JournalModel journalModel = new JournalModel();
        try {
            journalModel = journalModelReq;

            journalModel = this.update(journalModel);
            if (journalModel == null) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_UPDATE_JOURNAL;
            }
        } catch (Exception ex) {
            log.error("JournalBllManager -> updateJournal got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return journalModel;
    }

    public boolean saveOrUpdateJournalWithBusinessLogic(List<JournalModel> lstJournalModelReq, Integer businessID ) throws Exception {
        List<JournalModel> lstJournalModel;
        FinancialYearModel financialYearModel = null;
        boolean completionFlag = false;
        try {
            lstJournalModel = lstJournalModelReq;
            if (lstJournalModel.get(0).getFinancialYearID() == null || lstJournalModel.get(0).getFinancialYearID() == 0) {
                financialYearModel = this.financialYearBllManager.getCurrentFinancialYear(businessID);
                if (financialYearModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CURRENT_FINANCIAL_YEAR_NOT_FOUND_FOR_THIS_BUSINESS;
                    Core.clientMessage.get().userMessage = MessageConstant.CURRENT_FINANCIAL_YEAR_NOT_FOUND_FOR_THIS_BUSINESS;
                    return false;
                }
            } else {
                financialYearModel = this.financialYearBllManager.getById(lstJournalModel.get(0).getFinancialYearID());
                if (financialYearModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CURRENT_FINANCIAL_YEAR_NOT_FOUND_FOR_THIS_BUSINESS;
                    Core.clientMessage.get().userMessage = MessageConstant.CURRENT_FINANCIAL_YEAR_NOT_FOUND_FOR_THIS_BUSINESS;
                    return false;
                }
            }
            //check debit and credit sum are same or not
            Double creditSum = 0.0, debitSum = 0.0;
            for (JournalModel journalModel : lstJournalModel) {
                if (journalModel.getDrCrIndicator() == DebitCreditIndicator.Credit.get()) {
                    creditSum += journalModel.getAmount();
                }
                if (journalModel.getDrCrIndicator() == DebitCreditIndicator.Debit.get()) {
                    debitSum += journalModel.getAmount();
                }
            }
            if (debitSum.doubleValue() != creditSum.doubleValue()) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.DEBIT_AND_CREDIT_AMOUNT_SUM_ARE_NOT_SAME;
                Core.clientMessage.get().userMessage = MessageConstant.DEBIT_AND_CREDIT_AMOUNT_SUM_ARE_NOT_SAME;
            } else { //save or update list
                String journalReferenceNumber = AccountingUtils.getReferenceNumber();
                for (JournalModel journalModel : lstJournalModel) {
                    //if financial year id not present
                    if (financialYearModel != null) {
                        journalModel.setFinancialYearID(financialYearModel.getFinancialYearID());
                    }
                    if (journalModel.getJournalID() == null) {
                        journalModel.setJournalReferenceNo(journalReferenceNumber);
                        this.saveJournal(journalModel, financialYearModel);
                        if (Core.clientMessage.get().messageCode != null) return false;
                    } else {
                        this.updateJournal(journalModel);
                        if (Core.clientMessage.get().messageCode != null) return false;
                    }
                }
                completionFlag = true;
            }

        } catch (Exception ex) {
            log.error("JournalBllManager -> saveOrUpdateJournalWithBusinessLogic got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return completionFlag;
    }

    public Double getAvailableBalanceByAccountAndBusinessID(AccountModel accountModelReq, Integer businessID) throws Exception {
        List<JournalBalanceResult> lstJournalBalanceResult = new ArrayList<>();
        AccountModel accountModel = new AccountModel();
        Double debitAmountSum = 0.0, creditAmountSum = 0.0, balance = 0.0;
        try {
            accountModel = accountModelReq;

            String hql = "SELECT sum(J.amount) as  amountSum, J.drCrIndicator as drCrIndicator FROM Journal J WHERE J.status = " + TillBoxAppEnum.Status.Active.get() + " AND J.businessID = " + businessID + " AND J.accountID = " + accountModel.getAccountID() + " GROUP BY J.drCrIndicator";
            lstJournalBalanceResult = this.executeHqlQuery(hql, JournalBalanceResult.class, TillBoxAppEnum.QueryType.Join.get());
            if (lstJournalBalanceResult.size() > 0) {
                for (JournalBalanceResult journalBalanceResult : lstJournalBalanceResult) {
                    if (journalBalanceResult.drCrIndicator == DebitCreditIndicator.Debit.get()) {
                        debitAmountSum = journalBalanceResult.amountSum;
                    } else if (journalBalanceResult.drCrIndicator == DebitCreditIndicator.Credit.get()) {
                        creditAmountSum = journalBalanceResult.amountSum;
                    }
                }
                // check account positive (debit-credit)
                boolean accountPositiveFlag = false;
                for (Integer itemValue : AccountingEnum.AccountPositive.getMAP().values()) {
                    if (itemValue == accountModel.getAccountTypeID()) {
                        accountPositiveFlag = true;
                    }
                }
                if (accountPositiveFlag) {
                    balance = debitAmountSum - creditAmountSum;
                } else {
                    balance = creditAmountSum - debitAmountSum;
                }
            } else {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_AVAILABLE_BALANCE;
            }

        } catch (Exception ex) {
            log.error("JournalBllManager -> getAvailableBalanceByAccountAndBusinessID got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return balance;
    }

    public Double getAvailableBalanceByPartyIDAndBusinessID(JournalModel journalModelReq, Integer businessID) throws Exception {
        List<JournalBalanceResult> lstJournalBalanceResult = new ArrayList<>();
        JournalModel journalModel = new JournalModel();
        Double debitAmountSum = 0.0, creditAmountSum = 0.0, balance = 0.0;
        try {
            journalModel = journalModelReq;

            String hql = "SELECT sum(J.amount) as  amountSum, J.drCrIndicator as drCrIndicator FROM Journal J WHERE J.status = " + TillBoxAppEnum.Status.Active.get() + " AND J.businessID = " + businessID + " AND J.partyID = " + journalModel.getPartyID() + " GROUP BY J.drCrIndicator";
            lstJournalBalanceResult = this.executeHqlQuery(hql, JournalBalanceResult.class, TillBoxAppEnum.QueryType.Join.get());
            if (lstJournalBalanceResult.size() > 0) {
                for (JournalBalanceResult journalBalanceResult : lstJournalBalanceResult) {
                    if (journalBalanceResult.drCrIndicator == DebitCreditIndicator.Debit.get()) {
                        debitAmountSum = journalBalanceResult.amountSum;
                    } else if (journalBalanceResult.drCrIndicator == DebitCreditIndicator.Credit.get()) {
                        creditAmountSum = journalBalanceResult.amountSum;
                    }
                }

                // check party positive (debit-credit)
                boolean partyPositiveFlag = false;
                for (Integer itemValue : AccountingEnum.PartyPositive.getMAP().values()) {
                    if (itemValue == journalModel.getPartyType()) {
                        partyPositiveFlag = true;
                    }
                }
                if (partyPositiveFlag) {
                    balance = debitAmountSum - creditAmountSum;
                } else {
                    balance = creditAmountSum - debitAmountSum;
                }
            } else {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_AVAILABLE_BALANCE;
            }

        } catch (Exception ex) {
            log.error("JournalBllManager -> getAvailableBalanceByPartyIDAndBusinessID got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return balance;
    }

    public Double getCurrentDueByParty(Integer partyID, Integer partyType, Integer businessID) throws Exception {
        List<JournalBalanceResult> lstJournalBalanceResult = new ArrayList<>();
        Double debitAmountSum = 0.0, creditAmountSum = 0.0, balance = 0.0;
        try {

            String hql = "SELECT sum(J.amount) as  amountSum, J.drCrIndicator as drCrIndicator FROM Journal J WHERE J.status = " + TillBoxAppEnum.Status.Active.get() + " AND J.businessID = " + businessID + " AND J.partyID = " + partyID + " AND J.partyType = " + partyType + " GROUP BY J.drCrIndicator";
            lstJournalBalanceResult = this.executeHqlQuery(hql, JournalBalanceResult.class, TillBoxAppEnum.QueryType.Join.get());
            if (lstJournalBalanceResult.size() > 0) {
                for (JournalBalanceResult journalBalanceResult : lstJournalBalanceResult) {
                    if (journalBalanceResult.drCrIndicator == DebitCreditIndicator.Debit.get()) {
                        debitAmountSum = journalBalanceResult.amountSum;
                    } else if (journalBalanceResult.drCrIndicator == DebitCreditIndicator.Credit.get()) {
                        creditAmountSum = journalBalanceResult.amountSum;
                    }
                }

                if (partyType.intValue() == PartyType.Supplier.get()) {
                    balance = creditAmountSum - debitAmountSum;
                } else if (partyType.intValue() == PartyType.Customer.get()) {
                    balance = debitAmountSum - creditAmountSum;
                }

            } else {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_SUPPLIER_CURRENT_DUE;
            }

        } catch (Exception ex) {
            log.error("JournalBllManager -> getSupplierCurrentDue got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return balance;
    }

    public List<JournalModel> search(JournalModel journalModelReq) throws Exception {
        List<JournalModel> lstJournalModel = new ArrayList<>();
        JournalModel journalModel = new JournalModel();
        try {
            journalModel = journalModelReq;
            lstJournalModel = this.getAllByConditions(journalModel);
            if (lstJournalModel.size() == 0) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.NO_JOURNAL_DATA_FOUND;
            }

        } catch (Exception ex) {
            log.error("JournalBllManager -> search got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstJournalModel;
    }

    public List<JournalModel> deleteJournalByCondition(JournalModel journalModelReq) throws Exception {
        List<JournalModel> lstJournalModel = new ArrayList<>();
        List<JournalModel> lstDeletedJournalModel = new ArrayList<>();
        JournalModel whereCondition = new JournalModel();
        try {
            whereCondition = journalModelReq;
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());

            lstJournalModel = this.getAllByConditions(whereCondition);
            if (lstJournalModel.size() == 0) {
                Core.clientMessage.get().messageCode = null;
                Core.clientMessage.get().message = MessageConstant.NO_JOURNAL_DATA_FOUND;
                return lstDeletedJournalModel;
            }
            for (JournalModel journalModel : lstJournalModel) {
                journalModel.setStatus(TillBoxAppEnum.Status.Deleted.get());
                journalModel = this.update(journalModel);
                lstDeletedJournalModel.add(journalModel);
            }
            if (lstDeletedJournalModel.size() == 0) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.JOURNAL_DELETE_FAILED;
            }

        } catch (Exception ex) {
            log.error("JournalBllManager -> deleteJournalByCondition got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstDeletedJournalModel;
    }

    public Boolean searchExcludeOpeningBalance(JournalModel journalModelReq) throws Exception {
        List<JournalModel> lstJournalModel = new ArrayList<>();
        JournalModel journalModel = new JournalModel();
        Boolean existsFlag = false;
        try {
            journalModel = journalModelReq;
            String hql = "SELECT J.journalID FROM Journal J WHERE J.status = " + TillBoxAppEnum.Status.Active.get() + " AND J.businessID = " + journalModel.getBusinessID()
                    + " AND J.partyID = " + journalModel.getPartyID() + " AND J.partyType = " + journalModel.getPartyType() + " AND J.referenceType != " + journalModel.getReferenceType();
            lstJournalModel = this.executeHqlQuery(hql, JournalModel.class, TillBoxAppEnum.QueryType.Select.get());
            if (lstJournalModel.size() > 0) {
                existsFlag = true;
            } else {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.NO_JOURNAL_DATA_FOUND;
            }

        } catch (Exception ex) {
            log.error("JournalBllManager -> search got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return existsFlag;
    }


}
