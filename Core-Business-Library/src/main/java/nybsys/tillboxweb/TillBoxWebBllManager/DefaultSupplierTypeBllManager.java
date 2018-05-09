/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 23-Apr-18
 * Time: 12:02 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.TillBoxWebBllManager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.TillBoxWebEntities.DefaultSupplierType;
import nybsys.tillboxweb.TillBoxWebModels.DefaultSupplierTypeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DefaultSupplierTypeBllManager extends BaseBll<DefaultSupplierType> {

    private static final Logger log = LoggerFactory.getLogger(DefaultSupplierTypeBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(DefaultSupplierType.class);
        Core.runTimeModelType.set(DefaultSupplierTypeModel.class);
    }
}