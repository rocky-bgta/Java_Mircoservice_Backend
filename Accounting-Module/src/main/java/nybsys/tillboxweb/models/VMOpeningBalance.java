/**
 * Created By: Md. Abdul Hannan
 * Created Date: 4/26/2018
 * Time: 1:21 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.models;
import nybsys.tillboxweb.coreModels.RememberNoteModel;

import java.util.ArrayList;
import java.util.List;

public class VMOpeningBalance {
    public AccountModel accountModel = new AccountModel();
    public OpeningBalanceModel openingBalanceModel = new OpeningBalanceModel();
    public List<RememberNoteModel> lstRememberNoteModel = new ArrayList<>();
}
