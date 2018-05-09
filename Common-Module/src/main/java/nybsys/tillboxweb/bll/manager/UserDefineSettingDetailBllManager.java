/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/19/2018
 * Time: 4:07 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.entities.UserDefineSettingDetail;
import nybsys.tillboxweb.models.UserDefineSettingDetailModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserDefineSettingDetailBllManager extends BaseBll<UserDefineSettingDetail> {
    private static final Logger log = LoggerFactory.getLogger(UserDefineSettingBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(UserDefineSettingDetail.class);
        Core.runTimeModelType.set(UserDefineSettingDetailModel.class);
    }


    public UserDefineSettingDetailModel saveUserDefineSettingDetail(UserDefineSettingDetailModel userDefineSettingDetailModel) throws Exception {
        try {

            if (userDefineSettingDetailModel.getUserDefinedSettingDetailsID() > 0) {
                this.update(userDefineSettingDetailModel);
            } else {
                userDefineSettingDetailModel = this.save(userDefineSettingDetailModel);
            }

        } catch (Exception ex) {

            log.error("UserDefineSettingBllManager -> saveOrUpdateUserDefineSetting got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return userDefineSettingDetailModel;
    }
}
