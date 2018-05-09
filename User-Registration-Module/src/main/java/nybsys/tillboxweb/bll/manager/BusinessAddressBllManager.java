package nybsys.tillboxweb.bll.manager;
import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.entities.BusinessAddress;
import nybsys.tillboxweb.models.BusinessAddressModel;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class BusinessAddressBllManager extends BaseBll<BusinessAddress> {
    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(BusinessAddress.class);
        Core.runTimeModelType.set(BusinessAddressModel.class);
    }

}
