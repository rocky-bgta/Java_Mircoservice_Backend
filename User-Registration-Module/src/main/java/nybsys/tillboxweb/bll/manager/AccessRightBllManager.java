package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.AccessRight;
import nybsys.tillboxweb.models.AccessRightModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AccessRightBllManager extends BaseBll<AccessRight>{
    private static final Logger log = LoggerFactory.getLogger(BusinessBllManager.class);
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(AccessRight.class);
        Core.runTimeModelType.set(AccessRightModel.class);
    }

    public List<AccessRightModel> getAllAccessRight() throws Exception {
        List<AccessRightModel> lstAccessRightModel = new ArrayList<>();
        AccessRightModel accessRightModel = new AccessRightModel();
        try {
            accessRightModel.setStatus(TillBoxAppEnum.Status.Active.get());
            lstAccessRightModel = this.getAllByConditions(accessRightModel);
            if (lstAccessRightModel.size() == 0) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_ACCESS_RIGHT;
            }
        } catch (Exception ex) {
            log.error("AccessRightBllManager -> getAllAccessRight got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstAccessRightModel;
    }
}
