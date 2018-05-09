/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 23-Apr-18
 * Time: 12:59 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.coreBllManager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.coreEntities.SupplierType;
import nybsys.tillboxweb.coreModels.SupplierTypeModel;
import org.springframework.stereotype.Service;

@Service
public class CoreSupplierTypeBllManager extends BaseBll<SupplierType> {

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(SupplierType.class);
        Core.runTimeModelType.set(SupplierTypeModel.class);
    }

}