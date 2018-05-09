/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 23-Apr-18
 * Time: 11:57 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.TillBoxWebBllManager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.TillBoxWebEntities.DefaultCurrencyExchangeRate;
import nybsys.tillboxweb.TillBoxWebModels.DefaultCurrencyExchangeRateModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DefaultCurrencyExchangeRateBllManager extends BaseBll<DefaultCurrencyExchangeRate> {

    private static final Logger log = LoggerFactory.getLogger(DefaultCurrencyExchangeRateBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(DefaultCurrencyExchangeRate.class);
        Core.runTimeModelType.set(DefaultCurrencyExchangeRateModel.class);
    }
}