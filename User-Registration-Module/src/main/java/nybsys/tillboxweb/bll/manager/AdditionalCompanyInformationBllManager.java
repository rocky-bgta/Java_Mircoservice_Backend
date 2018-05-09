/**
 * Created By: Md. Abdul Hannan
 * Created Date: 4/16/2018
 * Time: 11:24 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.AdditionalCompanyInformation;
import nybsys.tillboxweb.models.AdditionalCompanyInformationModel;
import nybsys.tillboxweb.models.CompanyDetailModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AdditionalCompanyInformationBllManager extends BaseBll<AdditionalCompanyInformation> {

    private static final Logger log = LoggerFactory.getLogger(AdditionalCompanyInformationBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(AdditionalCompanyInformation.class);
        Core.runTimeModelType.set(AdditionalCompanyInformationModel.class);
    }


    public AdditionalCompanyInformationModel saveAdditionalCompanyInformation(AdditionalCompanyInformationModel additionalCompanyInformationModel) throws Exception {

        AdditionalCompanyInformationModel additionalCompanyInformationModel1 = new AdditionalCompanyInformationModel();
        try {

            //save
            if (additionalCompanyInformationModel.getAdditionalCompanyInformationID() == null || additionalCompanyInformationModel.getAdditionalCompanyInformationID() == 0) {
                additionalCompanyInformationModel1 = this.save(additionalCompanyInformationModel);
                if (additionalCompanyInformationModel1 == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CURRENCY_SAVE_FAILED;
                }
            } else {
                additionalCompanyInformationModel1 = this.update(additionalCompanyInformationModel);
                if (additionalCompanyInformationModel1 == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CURRENCY_UPDATE_FAILED;
                }
            }
        } catch (Exception ex) {
            log.error("AdditionalCompanyInformationBllManager -> saveOrUpdate got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return additionalCompanyInformationModel1;
    }


    public List<AdditionalCompanyInformationModel> searchAdditionalCompanyInformation(AdditionalCompanyInformationModel additionalCompanyInformationModel) throws Exception {

        List<AdditionalCompanyInformationModel> additionalCompanyInformationModels = new ArrayList<>();
        try {
            additionalCompanyInformationModels = this.getAllByConditions(additionalCompanyInformationModel);
        } catch (Exception ex) {
            log.error("AdditionalCompanyInformationBllManager -> saveOrUpdate got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return additionalCompanyInformationModels;
    }

}
