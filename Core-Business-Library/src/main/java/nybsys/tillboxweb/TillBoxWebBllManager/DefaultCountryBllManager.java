/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 23-Apr-18
 * Time: 11:55 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.TillBoxWebBllManager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.TillBoxWebEntities.DefaultCountry;
import nybsys.tillboxweb.TillBoxWebModels.DefaultCountryModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DefaultCountryBllManager extends BaseBll<DefaultCountry> {

    private static final Logger log = LoggerFactory.getLogger(DefaultCountryBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(DefaultCountry.class);
        Core.runTimeModelType.set(DefaultCountryModel.class);
    }
}