/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 23-Apr-18
 * Time: 12:58 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.coreBllManager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.coreEntities.SupplierCategory;
import nybsys.tillboxweb.coreModels.SupplierCategoryModel;
import org.springframework.stereotype.Service;

@Service
public class CoreSupplierCategoryBllManager extends BaseBll<SupplierCategory> {

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(SupplierCategory.class);
        Core.runTimeModelType.set(SupplierCategoryModel.class);
    }
}