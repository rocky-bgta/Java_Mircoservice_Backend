/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 06-Mar-18
 * Time: 11:26 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.coreUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("ALL")
public class CoreUtils {
    private static final Logger log = LoggerFactory.getLogger(CoreUtils.class);


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
