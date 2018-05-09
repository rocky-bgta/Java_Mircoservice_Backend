/**
 * Created By: Md. Abdul Hannan
 * Created Date: 4/16/2018
 * Time: 10:41 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.coreEntities.VATRate;
import nybsys.tillboxweb.coreModels.VATRateModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class VATRateBllManager extends BaseBll<VATRate> {

    private static final Logger log = LoggerFactory.getLogger(VATRateBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(VATRate.class);
        Core.runTimeModelType.set(VATRateModel.class);
    }

    public VATRateModel saveVATRate(VATRateModel VATRateModel) throws Exception {

        VATRateModel cDetailModel = new VATRateModel();
        try {

            //save
            if (VATRateModel.getvATRateID() == null || VATRateModel.getvATRateID() == 0) {
                cDetailModel = this.save(VATRateModel);
                if (cDetailModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CURRENCY_SAVE_FAILED;
                }
            } else {
                cDetailModel = this.update(VATRateModel);
                if (cDetailModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CURRENCY_UPDATE_FAILED;
                }
            }
        } catch (Exception ex) {
            log.error("VATRateBllManager -> saveOrUpdate got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return cDetailModel;
    }


    public List<VATRateModel> searchVATRate(VATRateModel VATRateModel) throws Exception {

        List<VATRateModel> VATRateModels = new ArrayList<>();
        try {
            VATRateModels = this.getAllByConditions(VATRateModel);
        } catch (Exception ex) {
            log.error("VATRateBllManager -> saveOrUpdate got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return VATRateModels;
    }


}
