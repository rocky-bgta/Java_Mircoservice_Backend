/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 23-Apr-18
 * Time: 12:50 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.coreBllManager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.coreEntities.AccountType;
import nybsys.tillboxweb.coreModels.AccountTypeModel;
import org.springframework.stereotype.Service;

@Service
public class CoreAccountTypeBllManager extends BaseBll<AccountType> {

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(AccountType.class);
        Core.runTimeModelType.set(AccountTypeModel.class);
    }
}