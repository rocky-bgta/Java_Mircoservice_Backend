/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 23-Apr-18
 * Time: 1:01 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.coreBllManager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.coreEntities.VATSystem;
import nybsys.tillboxweb.coreModels.VATSystemModel;
import org.springframework.stereotype.Service;

@Service
public class CoreVATSystemBllManager extends BaseBll<VATSystem> {

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(VATSystem.class);
        Core.runTimeModelType.set(VATSystemModel.class);
    }
}