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
import nybsys.tillboxweb.entities.Branding;
import nybsys.tillboxweb.models.BrandingModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class BrandingSettingBllManager extends BaseBll<Branding> {
    private static final Logger log = LoggerFactory.getLogger(BrandingSettingBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(Branding.class);
        Core.runTimeModelType.set(BrandingModel.class);
    }

    public BrandingModel saveOrUpdateBranding(BrandingModel brandingModelReq) throws Exception {
        BrandingModel brandingModel;
        try {
            brandingModel = brandingModelReq;

            //save
            if(brandingModel.getBrandingID() == null || brandingModel.getBrandingID() == 0) {
                brandingModel = this.save(brandingModel);
                if (brandingModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.BRANDING_SAVE_FAILED;
                }

            }else {
                brandingModel = this.update(brandingModel);
                if (brandingModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.BRANDING_UPDATE_FAILED;
                }
            }
        } catch (Exception ex) {
            log.error("BrandingBllManager -> saveBranding got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return brandingModel;
    }

}
