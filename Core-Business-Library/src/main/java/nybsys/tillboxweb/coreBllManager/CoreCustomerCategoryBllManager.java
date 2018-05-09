/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 23-Apr-18
 * Time: 12:54 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.coreBllManager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.coreEntities.CustomerCategory;
import nybsys.tillboxweb.coreModels.CustomerCategoryModel;
import org.springframework.stereotype.Service;

@Service
public class CoreCustomerCategoryBllManager extends BaseBll<CustomerCategory> {

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(CustomerCategory.class);
        Core.runTimeModelType.set(CustomerCategoryModel.class);
    }
}