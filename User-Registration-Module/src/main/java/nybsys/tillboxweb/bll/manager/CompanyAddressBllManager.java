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
import nybsys.tillboxweb.entities.CompanyAddress;
import nybsys.tillboxweb.models.CompanyAddressModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CompanyAddressBllManager extends BaseBll<CompanyAddress> {

    private static final Logger log = LoggerFactory.getLogger(CompanyAddressBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(CompanyAddress.class);
        Core.runTimeModelType.set(CompanyAddressModel.class);
    }


    public CompanyAddressModel saveCompanyAddress(CompanyAddressModel CompanyAddressModel) throws Exception {

        CompanyAddressModel CompanyAddressModel1 = new CompanyAddressModel();
        try {

            //save
            if (CompanyAddressModel.getCompanyAddressID() == null || CompanyAddressModel.getCompanyAddressID() == 0) {
                CompanyAddressModel1 = this.save(CompanyAddressModel);
                if (CompanyAddressModel1 == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CURRENCY_SAVE_FAILED;
                }
            } else {
                CompanyAddressModel1 = this.update(CompanyAddressModel);
                if (CompanyAddressModel1 == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CURRENCY_UPDATE_FAILED;
                }
            }
        } catch (Exception ex) {
            log.error("CompanyAddressBllManager -> saveOrUpdate got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return CompanyAddressModel1;
    }


    public List<CompanyAddressModel> searchCompanyAddress(CompanyAddressModel CompanyAddressModel) throws Exception {

        List<CompanyAddressModel> CompanyAddressModels = new ArrayList<>();
        try {
            CompanyAddressModels = this.getAllByConditions(CompanyAddressModel);
        } catch (Exception ex) {
            log.error("CompanyAddressBllManager -> saveOrUpdate got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return CompanyAddressModels;
    }

}
