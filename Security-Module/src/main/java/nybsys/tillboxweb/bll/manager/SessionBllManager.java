package nybsys.tillboxweb.bll.manager;


import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.TillBoxWebEntities.Session;
import nybsys.tillboxweb.TillBoxWebModels.SessionModel;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SessionBllManager extends BaseBll<Session> {


    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(Session.class);
        Core.runTimeModelType.set(SessionModel.class);
    }
}