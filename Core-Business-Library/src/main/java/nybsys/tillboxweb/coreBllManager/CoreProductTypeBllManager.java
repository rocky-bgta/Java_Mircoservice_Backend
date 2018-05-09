/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 23-Apr-18
 * Time: 12:57 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.coreBllManager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.coreEntities.ProductType;
import nybsys.tillboxweb.coreModels.ProductTypeModel;
import org.springframework.stereotype.Service;

@Service
public class CoreProductTypeBllManager extends BaseBll<ProductType> {

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(ProductType.class);
        Core.runTimeModelType.set(ProductTypeModel.class);
    }
}