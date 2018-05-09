/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 24-Apr-18
 * Time: 11:25 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.coreBllManager;


import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.coreEntities.CashFlow;
import nybsys.tillboxweb.coreModels.CashFlowModel;
import org.springframework.stereotype.Service;

@Service
public class CoreCashFlowBllManager extends BaseBll<CashFlow> {

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(CashFlow.class);
        Core.runTimeModelType.set(CashFlowModel.class);
    }
}