/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 23-Apr-18
 * Time: 12:52 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.coreBllManager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.coreEntities.Currency;
import nybsys.tillboxweb.coreModels.CurrencyModel;
import org.springframework.stereotype.Service;

@Service
public class CoreCurrencyBllManager extends BaseBll<Currency> {

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(Currency.class);
        Core.runTimeModelType.set(CurrencyModel.class);
    }
}