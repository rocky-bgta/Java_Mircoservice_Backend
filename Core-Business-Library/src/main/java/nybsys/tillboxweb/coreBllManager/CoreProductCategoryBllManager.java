/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 23-Apr-18
 * Time: 12:56 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.coreBllManager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.coreEntities.ProductCategory;
import nybsys.tillboxweb.coreModels.ProductCategoryModel;
import org.springframework.stereotype.Service;

@Service
public class CoreProductCategoryBllManager extends BaseBll<ProductCategory> {

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(ProductCategory.class);
        Core.runTimeModelType.set(ProductCategoryModel.class);
    }
}