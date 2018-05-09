/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 23-Apr-18
 * Time: 12:48 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.coreBllManager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.coreEntities.AccountClassification;
import nybsys.tillboxweb.coreModels.AccountClassificationModel;
import org.springframework.stereotype.Service;

@Service
public class CoreAccountClassificationBllManager extends BaseBll<AccountClassification> {

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(AccountClassification.class);
        Core.runTimeModelType.set(AccountClassificationModel.class);
    }
}