/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 06-Mar-18
 * Time: 11:26 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.coreUtil;

import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.coreEnum.DebitCreditIndicator;
import nybsys.tillboxweb.coreModels.CurrencyModel;
import nybsys.tillboxweb.coreModels.FinancialYearModel;
import nybsys.tillboxweb.coreModels.JournalModel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@SuppressWarnings("ALL")
public class CoreUtils {
    private static final Logger log = LoggerFactory.getLogger(CoreUtils.class);

    public static Integer getPeriodForJournal(FinancialYearModel financialYearModel) {
        int startMonth, currentMonth, startYear, currentYear, period;
        Calendar cal = Calendar.getInstance();
        cal.setTime(financialYearModel.getStartDate());
        startMonth = cal.get(Calendar.MONTH);
        startYear = cal.get(Calendar.YEAR);
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        currentMonth = localDate.getMonthValue();
        currentYear = localDate.getYear();
        period = (12 - startMonth + currentMonth + 1) + ((currentYear - (startYear + 1)) * 12);
        return period;
    }

    public static JournalModel buildJournalEntry(
            String journalReferenceNo,
            Integer coa,
            Integer referenceId,
            Integer referenceType,
            Integer partyId,
            Integer partyType,
            Double amount,
            Integer drCrIndicator,
            String note,
            CurrencyModel currencyModel,
            Double exchangeRate,
            Integer entryCurrencyID
    ) throws Exception {

        JournalModel journalModel = new JournalModel();

        journalModel.setJournalReferenceNo(journalReferenceNo);
        journalModel.setAccountID(coa);
        journalModel.setBusinessID(Core.businessId.get());
        journalModel.setPartyID(partyId);

        //if(partyType!=null)
        journalModel.setPartyType(partyType);

        journalModel.setAmount(amount);
        journalModel.setDrCrIndicator(drCrIndicator);
        journalModel.setReferenceID(referenceId);
        journalModel.setReferenceType(referenceType);
        journalModel.setDate(new Date());
        journalModel.setNote(note);

        journalModel.setBaseCurrencyID(currencyModel.getCurrencyID());
        journalModel.setEntryCurrencyID(entryCurrencyID);
        journalModel.setExchangeRate(exchangeRate);
        journalModel.setBaseCurrencyAmount(amount * exchangeRate);

        return journalModel;
    }


    /**
     * Build Debit Journal Model
     *
     * @param journalReferenceNo
     * @param coa
     * @param referenceId
     * @param referenceType
     * @param partyId
     * @param partyType
     * @param amount
     * @param note
     * @param entryCurrencyAmount
     * @return JournalModel
     */
    public static JournalModel buildDrJournalEntry
    (
            String journalReferenceNo,
            Integer coa,
            Integer referenceId,
            Integer referenceType,
            Integer partyId,
            Integer partyType,
            Double amount,
            String note,
            CurrencyModel currencyModel,
            Double exchangeRate,
            Integer entryCurrencyID

    ) throws Exception {
        return buildJournalEntry(
                journalReferenceNo,
                coa,
                referenceId,
                referenceType,
                partyId,
                partyType,
                amount,
                DebitCreditIndicator.Debit.get(),
                note,
                currencyModel,
                exchangeRate,
                entryCurrencyID);
    }

 /*

    *//**
     * Build Debit Journal Model
     *
     * @param journalReferenceNo
     * @param coa
     * @param referenceId
     * @param referenceType
     * @param partyId
     * @param partyType
     * @param amount
     * @param note
     * @param entryCurrencyAmount
     * @return JournalModel
     *//*
    public static JournalModel buildCrJournalEntry
    (
            String journalReferenceNo,
            Integer coa,
            Integer referenceId,
            Integer referenceType,
            Integer partyId,
            Integer partyType,
            Double amount,
            String note,
            CurrencyModel currencyModel,
            Integer entryCurrencyID

    ) throws Exception {
        return buildJournalEntry(
                journalReferenceNo,
                coa,
                referenceId,
                referenceType,
                partyId,
                partyType,
                amount,
                DebitCreditIndicator.Credit.get(),
                note,
                currencyModel,
                entryCurrencyID
                );
    }
*/
  /*


    *//**
     * Build Journal Model
     *
     * @param journalReferenceNo
     * @param coa
     * @param referenceId
     * @param referenceType
     * @param partyId
     * @param partyType
     * @param amount
     * @param note
     * @return JournalModel
     *//*
    public static JournalModel buildDrJournalEntry
    (
            String journalReferenceNo,
            Integer coa,
            Integer referenceId,
            Integer referenceType,
            Integer partyId,
            Integer partyType,
            Double amount,
            String note,
            CurrencyModel currencyModel,
            Integer entryCurrencyID

    ) throws Exception {

        JournalModel journalModel = new JournalModel();

        journalModel.setJournalReferenceNo(journalReferenceNo);
        journalModel.setAccountID(coa);
        journalModel.setBusinessID(Core.businessId.get());
        journalModel.setPartyID(partyId);

        //if(partyType!=null)
        journalModel.setPartyType(partyType);

        journalModel.setAmount(amount);
        journalModel.setDrCrIndicator(DebitCreditIndicator.Debit.get());
        journalModel.setReferenceID(referenceId);
        journalModel.setReferenceType(referenceType);
        journalModel.setDate(new Date());
        journalModel.setNote(note);


        journalModel.setBaseCurrencyID(currencyModel.getCurrencyID());
        journalModel.setEntryCurrencyID(entryCurrencyID);
        journalModel.setExchangeRate(currencyModel.getExchangeRate());
        journalModel.setBaseCurrencyAmount(amount * currencyModel.getExchangeRate());
        //journalModel.setEntryCurrencyAmount();

        return journalModel;
    }
*/


    /**
     * Build Journal Model
     *
     * @param journalReferenceNo
     * @param coa
     * @param referenceId
     * @param referenceType
     * @param partyId
     * @param partyType
     * @param amount
     * @param note
     * @return JournalModel
     */
    public static JournalModel buildCrJournalEntry(
            String journalReferenceNo,
            Integer coa,
            Integer referenceId,
            Integer referenceType,
            Integer partyId,
            Integer partyType,
            Double amount,
            String note,
            CurrencyModel currencyModel,
            Double exchangeRate,
            Integer entryCurrencyID
    ) throws Exception {

        JournalModel journalModel = new JournalModel();

        journalModel.setJournalReferenceNo(journalReferenceNo);
        journalModel.setAccountID(coa);
        journalModel.setBusinessID(Core.businessId.get());
        journalModel.setPartyID(partyId);

        //if(partyType!=null)
        journalModel.setPartyType(partyType);

        journalModel.setAmount(amount);
        journalModel.setDrCrIndicator(DebitCreditIndicator.Credit.get());
        journalModel.setReferenceID(referenceId);
        journalModel.setReferenceType(referenceType);
        journalModel.setDate(new Date());
        journalModel.setNote(note);

        journalModel.setBaseCurrencyID(currencyModel.getCurrencyID());
        journalModel.setEntryCurrencyID(entryCurrencyID);
        journalModel.setExchangeRate(exchangeRate);
        journalModel.setBaseCurrencyAmount(amount * exchangeRate);

        return journalModel;
    }

    public static String getSequence(String dbCurrentSequence, String prefix) {
        String bulidSquence;
        Integer sequenceNo = 0, sequenceNoLength = 0, zeroFillLength = 0;
        dbCurrentSequence = StringUtils.substringAfter(dbCurrentSequence, prefix);

        if (StringUtils.equals(dbCurrentSequence, "") || dbCurrentSequence == null)
            dbCurrentSequence = "0";

        try {
            sequenceNo = Integer.parseInt(dbCurrentSequence);
        } catch (Exception ex) {
            log.error("Parse exceptioin :" + ex.getMessage());
            throw ex;
        }

        if (sequenceNo > 0)
            sequenceNo += 1;
        else
            sequenceNo = 1;

        sequenceNoLength = String.valueOf(sequenceNo).length();

        zeroFillLength = 10 - sequenceNoLength;
        bulidSquence = StringUtils.rightPad(prefix, zeroFillLength, "0");
        bulidSquence = bulidSquence + String.valueOf(sequenceNo);
        return bulidSquence;
    }
}
