/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 02-Apr-18
 * Time: 10:52 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.TillBoxWebBllManager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.TillBoxWebEntities.DefaultCOA;
import nybsys.tillboxweb.TillBoxWebModels.DefaultCOAModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DefaultCOABllManager extends BaseBll<DefaultCOA> {

    private static final Logger log = LoggerFactory.getLogger(DefaultCOABllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(DefaultCOA.class);
        Core.runTimeModelType.set(DefaultCOAModel.class);
    }
}