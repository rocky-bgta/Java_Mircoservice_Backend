package nybsys.tillboxweb.coreBllManager;


import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.coreEntities.Journal;
import nybsys.tillboxweb.coreEnum.DebitCreditIndicator;
import nybsys.tillboxweb.coreModels.FinancialYearModel;
import nybsys.tillboxweb.coreModels.JournalModel;
import nybsys.tillboxweb.coreUtil.CoreUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("ALL")
@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CoreJournalBllManager extends BaseBll<Journal> {

    private static final Logger log = LoggerFactory.getLogger(CoreJournalBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(Journal.class);
        Core.runTimeModelType.set(JournalModel.class);
    }


    @Autowired
    private CoreFinancialYearBllManager financialYearBllManager;

    public boolean saveOrUpdate(List<JournalModel> reqJournalModelList) throws Exception {
        JournalModel resultJournalModel;
        List<JournalModel> journalModelList;
        FinancialYearModel financialYearModel = null;
        Integer numOfJournalEntry = 0;
        boolean completionFlag = false;
        boolean isDrCrEqual = false;
        Integer businessID = Core.businessId.get();
        try {
            journalModelList = reqJournalModelList;
            if (journalModelList.get(0).getFinancialYearID() == null) {
                financialYearModel = this.financialYearBllManager.getCurrentFinancialYear(businessID);
                if (financialYearModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "current financial year not found for this business";
                    Core.clientMessage.get().userMessage = "current financial year not found for this business";
                    return false;
                }
            }
            //check debit and credit sum are same or not
            isDrCrEqual = this.isDebitCreditEqual(journalModelList);

            if (!isDrCrEqual) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = "debit and credit amount sum are not same";
                Core.clientMessage.get().userMessage = "debit and credit amount sum are not same";
            } else {
                //save
                //String journalReferenceNumber = TillBoxUtils.getUUID();
                for (JournalModel journalModel : journalModelList) {
                    //if financial year id not present
                    if (financialYearModel != null) {
                        journalModel.setFinancialYearID(financialYearModel.getFinancialYearID());
                    }
                    if (journalModel.getJournalID() == null) {
                        //journalModel.setJournalReferenceNo(journalReferenceNumber);
                        journalModel.setPeriod(CoreUtils.getPeriodForJournal(financialYearModel));
                        journalModel.setFinancialYearID(financialYearModel.getFinancialYearID());
                        resultJournalModel = this.save(journalModel);
                        if (resultJournalModel != null) {
                            numOfJournalEntry++;
                        }

                    } else {
                        //update
                        this.update(journalModel);
                        numOfJournalEntry++;
                    }
                }
                if (numOfJournalEntry == 2)
                    completionFlag = true;
                else {
                    completionFlag = false;
                    Core.clientMessage.get().message = "Error in Journal entry";
                    throw new Exception("Error in Journal entry");
                }
            }

        } catch (Exception ex) {
            log.error("JournalBllManager -> saveOrUpdateJournal got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return completionFlag;
    }

    public boolean deleteJournalPair(Integer referenceID, Integer referenceType) throws Exception {
        Boolean isJournalDeleted = false;
        JournalModel journalModelForDelete = new JournalModel();
        List<JournalModel> journalModelList;
        journalModelForDelete.setReferenceID(referenceID);
        journalModelForDelete.setReferenceType(referenceType);
        journalModelForDelete.setStatus(TillBoxAppEnum.Status.Active.get());
        try {
            journalModelList = this.getAllByConditions(journalModelForDelete);
            if (journalModelList.size() == 2) {
                for (JournalModel journalModel : journalModelList) {
                    this.softDelete(journalModel);
                }
                isJournalDeleted = true;
            } else {
                isJournalDeleted = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return isJournalDeleted;
    }

    private Boolean isDebitCreditEqual(List<JournalModel> journalModelList) {
        Boolean isEqual = false;
        //check debit and credit sum are same or not
        Double creditSum = 0.0, debitSum = 0.0;
        for (JournalModel journalModel : journalModelList) {
            if (journalModel.getDrCrIndicator() == DebitCreditIndicator.Credit.get()) {
                creditSum += journalModel.getAmount();
            }
            if (journalModel.getDrCrIndicator() == DebitCreditIndicator.Debit.get()) {
                debitSum += journalModel.getAmount();
            }
        }
        if (debitSum.doubleValue() == debitSum.doubleValue()) {
            isEqual = true;
        }
        return isEqual;
    }
}
