/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 14-Feb-18
 * Time: 3:07 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.UserDefineSetting;
import nybsys.tillboxweb.models.UserDefineSettingModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserDefineSettingBllManager extends BaseBll<UserDefineSetting> {

    private static final Logger log = LoggerFactory.getLogger(UserDefineSettingBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(UserDefineSetting.class);
        Core.runTimeModelType.set(UserDefineSettingModel.class);
    }

    public UserDefineSettingModel saveOrUpdateUserDefineSetting(UserDefineSettingModel userDefineSettingModel) throws Exception {
        Integer primaryKeyValue = userDefineSettingModel.getUserDefineSettingID();
        UserDefineSettingModel processedUserDefineSettingModel = null;
        try {

            if (primaryKeyValue == null || primaryKeyValue == 0) {
                // Save Code
                processedUserDefineSettingModel = this.save(userDefineSettingModel);
                if (userDefineSettingModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_DEFINE_SETTING;
                }
            } else {
                // Update Code
                processedUserDefineSettingModel = this.update(userDefineSettingModel);
                if (userDefineSettingModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = (Core.clientMessage.get().userMessage != null) ? Core.clientMessage.get().userMessage : MessageConstant.FAILED_TO_UPDATE_DEFINE_SETTING;
                }
            }

        } catch (Exception ex) {
            log.error("UserDefineSettingBllManager -> saveOrUpdateUserDefineSetting got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return processedUserDefineSettingModel;
    }

    public List<UserDefineSettingModel> searchUserDefineSetting(UserDefineSettingModel userDefineSettingModelReq) throws Exception {
        UserDefineSettingModel userDefineSettingModel;
        List<UserDefineSettingModel> lstUserDefineSettingModel;
        try {
            userDefineSettingModel = userDefineSettingModelReq;
            lstUserDefineSettingModel = this.getAllByConditions(userDefineSettingModel);
            if (lstUserDefineSettingModel.size() == 0) {
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_ALL_USER_DEFINE_SETTING;
            }
        } catch (Exception ex) {
            log.error("UserDefineSettingBllManager -> searchUserDefineSetting got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstUserDefineSettingModel;
    }
}
