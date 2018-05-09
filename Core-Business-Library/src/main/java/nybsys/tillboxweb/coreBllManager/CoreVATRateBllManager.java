/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 23-Apr-18
 * Time: 1:00 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.coreBllManager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.coreEntities.VATRate;
import nybsys.tillboxweb.coreModels.VATRateModel;
import org.springframework.stereotype.Service;

@Service
public class CoreVATRateBllManager extends BaseBll<VATRate> {

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(VATRate.class);
        Core.runTimeModelType.set(VATRateModel.class);
    }
}