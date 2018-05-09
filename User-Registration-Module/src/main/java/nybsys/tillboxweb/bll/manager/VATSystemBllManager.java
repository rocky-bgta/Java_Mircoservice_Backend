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
import nybsys.tillboxweb.coreEntities.VATSystem;
import nybsys.tillboxweb.coreModels.VATRateModel;
import nybsys.tillboxweb.coreModels.VATSystemModel;
import nybsys.tillboxweb.models.VMVATSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class VATSystemBllManager extends BaseBll<VATSystem> {

    private static final Logger log = LoggerFactory.getLogger(VATSystemBllManager.class);
    private VATRateBllManager vatRateBllManager = new VATRateBllManager();

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(VATSystem.class);
        Core.runTimeModelType.set(VATSystemModel.class);
    }

    public VATSystemModel saveVATSystem(VMVATSystem vmvatSystem, Integer businessID) throws Exception {

        VATSystemModel vatSystemModel = new VATSystemModel();
        try {
            if (vmvatSystem.vatSystem.getvATSystemID() == null || vmvatSystem.vatSystem.getvATSystemID() == 0) {
                vmvatSystem.vatSystem.setBusinessID(businessID);
                vatSystemModel = this.save(vmvatSystem.vatSystem);
                if (vatSystemModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CURRENCY_SAVE_FAILED;
                }
            } else {
                vatSystemModel = this.update(vmvatSystem.vatSystem);
                if (vatSystemModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CURRENCY_UPDATE_FAILED;
                }
            }

            VATRateModel vatRateModel = new VATRateModel();
            List<VATRateModel> lstExisting = new ArrayList<>();
            vatRateModel.setBusinessID(businessID);
            lstExisting = this.vatRateBllManager.getAllByConditions(vatRateModel);

            for (VATRateModel vatRate : lstExisting) {
                this.vatRateBllManager.delete(vatRate);
            }

            for (VATRateModel vatRate : vmvatSystem.lstVATRate) {
                vatRate.setBusinessID(businessID);
                this.vatRateBllManager.save(vatRate);
            }


        } catch (Exception ex) {
            log.error("VATSystemBllManager -> saveOrUpdate got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return vatSystemModel;
    }


    public List<VATSystemModel> searchVATSystem(VATSystemModel VATSystemModel) throws Exception {

        List<VATSystemModel> VATSystemModels = new ArrayList<>();
        try {
            VATSystemModels = this.getAllByConditions(VATSystemModel);
        } catch (Exception ex) {
            log.error("VATSystemBllManager -> saveOrUpdate got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return VATSystemModels;
    }


}
