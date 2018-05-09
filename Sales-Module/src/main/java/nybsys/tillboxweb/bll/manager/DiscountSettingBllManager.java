/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 4:53
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.DiscountSetting;
import nybsys.tillboxweb.models.DiscountSettingModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DiscountSettingBllManager extends BaseBll<DiscountSetting> {
    private static final Logger log = LoggerFactory.getLogger(DiscountSettingBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(DiscountSetting.class);
        Core.runTimeModelType.set(DiscountSettingModel.class);
    }

    public DiscountSettingModel saveOrUpdate(DiscountSettingModel discountSettingModelReq) throws Exception {
        DiscountSettingModel discountSettingModel = new DiscountSettingModel();
        List<DiscountSettingModel> lstDiscountSettingModel = new ArrayList<>();
        try {
            discountSettingModel = discountSettingModelReq;
            //save
            if (discountSettingModel.getDiscountSettingID() == null || discountSettingModel.getDiscountSettingID() == 0)
            {
                discountSettingModel = this.save(discountSettingModel);
                if (discountSettingModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.DISCOUNT_SETTING_SAVE_FAILED;
                }
            } else { //update

                discountSettingModel = this.update(discountSettingModel);
                if (discountSettingModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.DISCOUNT_SETTING_UPDATE_FAILED;
                }
            }

        } catch (Exception ex) {
            log.error("DiscountSettingBllManager -> saveOrUpdate got exception :"+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return discountSettingModel;
    }

    public List<DiscountSettingModel> searchDiscountSetting(DiscountSettingModel discountSettingModelReq) throws Exception {
        DiscountSettingModel discountSettingModel = new DiscountSettingModel();
        List<DiscountSettingModel> lstDiscountSettingModel = new ArrayList<>();
        try {
            discountSettingModel = discountSettingModelReq;
            lstDiscountSettingModel = this.getAllByConditions(discountSettingModel);
            if (lstDiscountSettingModel.size() == 0) {
                Core.clientMessage.get().message = MessageConstant.DISCOUNT_SETTING_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("DiscountSettingBllManager -> searchDiscountSetting got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstDiscountSettingModel;
    }
    public DiscountSettingModel deleteDiscountSetting(DiscountSettingModel discountSettingModelReq) throws Exception {
        DiscountSettingModel discountSettingModel = new DiscountSettingModel();
        try {
            discountSettingModel = discountSettingModelReq;
            discountSettingModel = this.softDelete(discountSettingModel);
            if (discountSettingModel == null) {
                Core.clientMessage.get().message = MessageConstant.DISCOUNT_SETTING_DELETE_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("DiscountSettingBllManager -> deleteDiscountSetting got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return discountSettingModel;
    }
    
}
