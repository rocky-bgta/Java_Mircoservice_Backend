/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 08/02/2018
 * Time: 02:13
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.utils;


import nybsys.tillboxweb.coreModels.FinancialYearModel;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class AccountingUtils {
    public static String getReferenceNumber() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static Integer periodCalculator(FinancialYearModel financialYearModel) {
        int startMonth,currentMonth,startYear,currentYear,period;
        Calendar cal = Calendar.getInstance();
        cal.setTime(financialYearModel.getStartDate());
        startMonth = cal.get(Calendar.MONTH);
        startYear = cal.get(Calendar.YEAR);
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        currentMonth = localDate.getMonthValue();
        currentYear = localDate.getYear();
        period = (12-startMonth+currentMonth+1)+((currentYear-(startYear+1))*12);
        return period;
    }

    public static String getDocNumber() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
