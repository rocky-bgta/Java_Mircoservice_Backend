package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.entities.BusinessAddress;
import nybsys.tillboxweb.entities.BusinessDetail;
import nybsys.tillboxweb.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class BusinessDetailBllManager extends BaseBll<BusinessDetail> {
    private static final Logger log = LoggerFactory.getLogger(GstSettingBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(BusinessDetail.class);
        Core.runTimeModelType.set(BusinessDetailsModel.class);
    }

    @Autowired
    private BusinessAddressBllManager businessAddressBllManager;
    @Autowired
    private BusinessContactBllManager businessContactBllManager;
    @Autowired
    private CountryBllManager countryBllManager;

    private  BusinessBllManager businessBllManager=new BusinessBllManager();

    public BusinessDetailsModel saveBusinessDetailsModel(BusinessDetailsModel businessDetailsModelReq) throws Exception {
        BusinessDetailsModel businessDetailsModel;
        try {
            businessDetailsModel = businessDetailsModelReq;

            if (businessDetailsModel.getBusinessDetailsID() == null || businessDetailsModel.getBusinessDetailsID() == 0) {
                businessDetailsModel = this.save(businessDetailsModel);
            } else {
                businessDetailsModel = this.update(businessDetailsModel);
            }
        } catch (Exception ex) {
            log.error("BusinessDetailBllManager -> saveBusinessDetailsModel got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return businessDetailsModel;
    }


    public VMBusinessDetail saveVMBusinessDetail(VMBusinessDetail vmBusinessDetail, Integer securityBusinessID) throws Exception {

        try {
            //(1)
            if (vmBusinessDetail.businessDetailsModel.getBusinessDetailsID() == null || vmBusinessDetail.businessDetailsModel.getBusinessDetailsID() == 0) {

                vmBusinessDetail.businessDetailsModel.setBusinessID(securityBusinessID);
                this.save(vmBusinessDetail.businessDetailsModel);

            } else {
                vmBusinessDetail.businessDetailsModel = this.update(vmBusinessDetail.businessDetailsModel);
            }

            //(2)
            //save business address information
            for (BusinessAddressModel addressModel : vmBusinessDetail.lstBusinessAddressModel) {
                if (addressModel.getAddressID() == null || addressModel.getAddressID() == 0) {
                    addressModel.setBusinessID(securityBusinessID);
                    this.businessAddressBllManager.save(addressModel);
                } else {
                    addressModel.setBusinessID(securityBusinessID);
                    this.businessAddressBllManager.update(addressModel);
                }
            }

            //(3)
            //save business contact information
            vmBusinessDetail.businessContact.setBusinessID(securityBusinessID);
            this.businessContactBllManager.save(vmBusinessDetail.businessContact);
//            for (BusinessContactModel businessContactModel : vmBusinessDetail.lstBusinessContactModel) {
//                if (businessContactModel.getBusinessContactID() == null || businessContactModel.getBusinessContactID() == 0) {
//                    businessContactModel.setBusinessID(securityBusinessID);
//
//                } else {
//                    this.businessContactBllManager.update(businessContactModel);
//                }
//            }


        } catch (Exception ex) {
            log.error("BusinessDetailBllManager -> saveVMBusinessDetail got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return vmBusinessDetail;

    }

    public VMBusinessDetail searchVMBusinessDetail(BusinessDetailsModel businessDetailsModel) throws Exception {
        List list;
        VMBusinessDetail vMBusinessDetail = new VMBusinessDetail();
        try {
            list = this.getAllByConditions(businessDetailsModel);
            if (list.size() > 0) {
                List<BusinessDetailsModel> lstBusinessDetailsModel = new ArrayList<BusinessDetailsModel>();
                lstBusinessDetailsModel = (List<BusinessDetailsModel>) (Object) list;
                vMBusinessDetail.businessDetailsModel = lstBusinessDetailsModel.get(0);
                BusinessAddressModel businessAddressModel = new BusinessAddressModel();
                businessAddressModel.setBusinessID(businessDetailsModel.getBusinessID());
                BusinessContactModel businessContactModel = new BusinessContactModel();
                businessContactModel.setBusinessID(businessDetailsModel.getBusinessID());

                vMBusinessDetail.lstBusinessAddressModel = this.businessAddressBllManager.getAllByConditions(businessAddressModel);

                if (this.businessContactBllManager.getAllByConditions(businessContactModel).size() > 0) {
                    vMBusinessDetail.businessContact = this.businessContactBllManager.getAllByConditions(businessContactModel).get(0);
                }
                vMBusinessDetail.lstCountryModel = this.countryBllManager.getAll();

                BusinessModel businessModel=new BusinessModel();
                businessModel.setBusinessID(businessDetailsModel.getBusinessID());
                //select default dbConfig
                this.setDefaultDateBase();
                vMBusinessDetail.business=this.businessBllManager.getAllByConditions(businessModel).get(0);

            } else {
                vMBusinessDetail = new VMBusinessDetail();
                vMBusinessDetail.lstCountryModel = this.countryBllManager.getAll();
                //select default dbConfig
                this.setDefaultDateBase();
                BusinessModel businessModel=new BusinessModel();
                businessModel.setBusinessID(businessDetailsModel.getBusinessID());
                vMBusinessDetail.business=this.businessBllManager.getAllByConditions(businessModel).get(0);
            }

        } catch (Exception ex) {
            log.error("BusinessDetailBllManager -> searchVMBusinessDetail got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return vMBusinessDetail;


    }
}


