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
import nybsys.tillboxweb.entities.CompanyDetail;
import nybsys.tillboxweb.models.CompanyAddressModel;
import nybsys.tillboxweb.models.CompanyDetailModel;
import nybsys.tillboxweb.models.VMCompanyDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CompanyDetailBllManager extends BaseBll<CompanyDetail> {

    private static final Logger log = LoggerFactory.getLogger(CompanyDetailBllManager.class);

    private CompanyAddressBllManager companyAddressBllManager = new CompanyAddressBllManager();


    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(CompanyDetail.class);
        Core.runTimeModelType.set(CompanyDetailModel.class);
    }

    public VMCompanyDetail saveCompanyDetail(VMCompanyDetail vmCompanyDetail) throws Exception {

        CompanyDetailModel cDetailModel = new CompanyDetailModel();
        try {

            //save
            if (vmCompanyDetail.companyDetailModel.getCompanyDetailID() == null || vmCompanyDetail.companyDetailModel.getCompanyDetailID() == 0) {
                cDetailModel = this.save(vmCompanyDetail.companyDetailModel);

                if (cDetailModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CURRENCY_SAVE_FAILED;
                }
            } else {
                cDetailModel = this.update(vmCompanyDetail.companyDetailModel);
                if (cDetailModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CURRENCY_UPDATE_FAILED;
                }
            }
            CompanyAddressModel companyAddressModel = new CompanyAddressModel();
            companyAddressModel.setBusinessID(vmCompanyDetail.companyDetailModel.getBusinessID());

            List<CompanyAddressModel> companyAddressModels=new ArrayList<>();
            companyAddressModels=this.companyAddressBllManager.getAllByConditions(companyAddressModel);

            for (CompanyAddressModel addressModel : companyAddressModels) {
                this.companyAddressBllManager.delete(addressModel);
            }

            for (CompanyAddressModel addressModel : vmCompanyDetail.lstCompanyAddressModel) {
                addressModel.setBusinessID(vmCompanyDetail.companyDetailModel.getBusinessID());
                this.companyAddressBllManager.save(addressModel);
            }


        } catch (Exception ex) {
            log.error("CompanyDetailBllManager -> saveOrUpdate got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return vmCompanyDetail;
    }


    public VMCompanyDetail searchCompanyDetail(CompanyDetailModel companyDetailModel) throws Exception {

        VMCompanyDetail vmCompanyDetail=new VMCompanyDetail();
        List<CompanyDetailModel> companyDetailModels = new ArrayList<>();
        try {
            companyDetailModels = this.getAllByConditions(companyDetailModel);

            if (companyDetailModels.size()>0)
            {
                vmCompanyDetail.companyDetailModel=companyDetailModels.get(0);

                CompanyAddressModel companyAddressModel=new CompanyAddressModel();
                companyAddressModel.setBusinessID(vmCompanyDetail.companyDetailModel.getBusinessID());
                vmCompanyDetail.lstCompanyAddressModel=this.companyAddressBllManager.getAllByConditions(companyAddressModel);

            }

        } catch (Exception ex) {
            log.error("CompanyDetailBllManager -> saveOrUpdate got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return vmCompanyDetail;
    }


}
