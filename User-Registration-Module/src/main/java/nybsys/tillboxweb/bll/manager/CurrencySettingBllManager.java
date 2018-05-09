/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/9/2018
 * Time: 10:06 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.CurrencySetting;
import nybsys.tillboxweb.models.CurrencySettingModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CurrencySettingBllManager extends BaseBll<CurrencySetting> {

    private static final Logger log = LoggerFactory.getLogger(CurrencySettingBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(CurrencySetting.class);
        Core.runTimeModelType.set(CurrencySettingModel.class);
    }

    public CurrencySettingModel saveOrUpdate(CurrencySettingModel currencySettingModelReq) throws Exception {
        CurrencySettingModel currencySettingModel;
        try {
            currencySettingModel = currencySettingModelReq;
            //save
            if (currencySettingModel.getCurrencySettingID() == null || currencySettingModel.getCurrencySettingID() == 0) {
                currencySettingModel = this.save(currencySettingModel);
                if (currencySettingModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CURRENCY_SETTING_SAVE_FAILED;
                }
            } else {
                currencySettingModel = this.update(currencySettingModel);
                if (currencySettingModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CURRENCY_SETTING_UPDATE_FAILED;
                }
            }
        } catch (Exception ex) {
            log.error("CurrencySettingBllManager -> saveOrUpdate got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return currencySettingModel;
    }

    public CurrencySettingModel getCurrencySetting(Integer businessID) throws Exception {
        List<CurrencySettingModel> lstCurrencySettingModel = new ArrayList<>();
        CurrencySettingModel currencySettingModel = new CurrencySettingModel();
        try {
            currencySettingModel.setBusinessID(businessID);

            lstCurrencySettingModel = this.getAllByConditionWithActive(currencySettingModel);
            if (lstCurrencySettingModel.size() == 0) {
                currencySettingModel = null;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.CURRENCY_SETTING_GET_FAILED;
            } else {
                currencySettingModel = lstCurrencySettingModel.get(0);
            }

        } catch (Exception ex) {
            log.error("CurrencySettingBllManager -> getCurrencySetting got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return currencySettingModel;
    }
}
