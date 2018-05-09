/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 23-Apr-18
 * Time: 12:55 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.coreBllManager;


import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.coreEntities.CustomerType;
import nybsys.tillboxweb.coreModels.CustomerTypeModel;
import org.springframework.stereotype.Service;

@Service
public class CoreCustomerTypeBllManager extends BaseBll<CustomerType> {

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(CustomerType.class);
        Core.runTimeModelType.set(CustomerTypeModel.class);
    }
}