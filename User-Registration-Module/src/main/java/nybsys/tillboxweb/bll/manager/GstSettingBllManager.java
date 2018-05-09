/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 09-Jan-18
 * Time: 2:42 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */
package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.GstSetting;
import nybsys.tillboxweb.models.GstSettingModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GstSettingBllManager extends BaseBll<GstSetting> {
    private static final Logger log = LoggerFactory.getLogger(GstSettingBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(GstSetting.class);
        Core.runTimeModelType.set(GstSettingModel.class);
    }

    public GstSettingModel saveOrUpdateGstSetting(GstSettingModel gstSettingModelReq) throws Exception {
        GstSettingModel gstSettingModel;
        try {
            gstSettingModel = gstSettingModelReq;

            //save
            if(gstSettingModel.getGstSettingsID() == null || gstSettingModel.getGstSettingsID() == 0) {
                gstSettingModel = this.save(gstSettingModel);
                if (gstSettingModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.GST_SETTINGS_SAVE_FAILED;
                }

            }else {
                gstSettingModel = this.update(gstSettingModel);
                if (gstSettingModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.GST_SETTINGS_UPDATE_FAILED;
                }
            }
        } catch (Exception ex) {
            log.error("GstSettingBllManager -> saveGstSetting got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return gstSettingModel;
    }

}
