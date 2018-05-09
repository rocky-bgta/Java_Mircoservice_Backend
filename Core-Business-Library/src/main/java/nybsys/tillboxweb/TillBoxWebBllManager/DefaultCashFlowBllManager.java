/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 24-Apr-18
 * Time: 11:24 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.TillBoxWebBllManager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.TillBoxWebEntities.DefaultCashFlow;
import nybsys.tillboxweb.TillBoxWebModels.DefaultCashFlowModel;
import org.springframework.stereotype.Service;

@Service
public class DefaultCashFlowBllManager extends BaseBll<DefaultCashFlow> {

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(DefaultCashFlow.class);
        Core.runTimeModelType.set(DefaultCashFlowModel.class);
    }
}