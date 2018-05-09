package nybsys.tillboxweb.bll.manager;
import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.coreEntities.Country;
import nybsys.tillboxweb.coreModels.CountryModel;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CountryBllManager extends BaseBll<Country> {

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(Country.class);
        Core.runTimeModelType.set(CountryModel.class);
    }

}
