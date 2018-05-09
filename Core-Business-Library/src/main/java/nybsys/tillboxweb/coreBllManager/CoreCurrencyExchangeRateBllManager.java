/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 23-Apr-18
 * Time: 12:53 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.coreBllManager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.coreEntities.CurrencyExchangeRate;
import nybsys.tillboxweb.coreModels.CurrencyExchangeRateModel;
import org.springframework.stereotype.Service;

@Service
public class CoreCurrencyExchangeRateBllManager extends BaseBll<CurrencyExchangeRate> {

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(CurrencyExchangeRate.class);
        Core.runTimeModelType.set(CurrencyExchangeRateModel.class);
    }
}