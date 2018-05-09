package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.UserDefineField;
import nybsys.tillboxweb.models.UserDefineFieldModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserDefineFieldBllManager extends BaseBll<UserDefineField>{
    private static final Logger log = LoggerFactory.getLogger(UserDefineFieldBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(UserDefineField.class);
        Core.runTimeModelType.set(UserDefineFieldModel.class);
    }

    public List<UserDefineFieldModel> saveUserDefineField(List<UserDefineFieldModel> userDefineFieldModels, Integer businessID) throws Exception {

        UserDefineFieldModel defineFieldModel = new UserDefineFieldModel();
        try {

            for (UserDefineFieldModel userDefineFieldModel : userDefineFieldModels) {
                userDefineFieldModel.setBusinessID(businessID);

                if (userDefineFieldModel.getUserDefinedFieldID() == null || userDefineFieldModel.getUserDefinedFieldID() == 0) {
                    defineFieldModel = this.save(userDefineFieldModel);
                    if (defineFieldModel == null) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.CURRENCY_SAVE_FAILED;
                    }
                } else {
                    defineFieldModel = this.update(userDefineFieldModel);
                    if (defineFieldModel == null) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.CURRENCY_UPDATE_FAILED;
                    }
                }
            }


            //save

        } catch (Exception ex) {
            log.error("UserDefineFieldBllManager -> saveOrUpdate got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return userDefineFieldModels;
    }


    public List<UserDefineFieldModel> searchUserDefineField(UserDefineFieldModel userDefineFieldModel) throws Exception {

        List<UserDefineFieldModel> userDefineFieldModels = new ArrayList<>();
        try {
            userDefineFieldModels = this.getAllByConditions(userDefineFieldModel);
        } catch (Exception ex) {
            log.error("UserDefineFieldBllManager -> saveOrUpdate got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return userDefineFieldModels;
    }



}
