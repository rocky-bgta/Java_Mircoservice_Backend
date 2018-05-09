/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 02-Apr-18
 * Time: 11:35 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.coreBllManager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.coreEntities.Account;
import nybsys.tillboxweb.coreModels.AccountModel;
import org.springframework.stereotype.Service;

@Service
public class CoreAccountBllManager extends BaseBll<Account> {
    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(Account.class);
        Core.runTimeModelType.set(AccountModel.class);
    }
}