/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 09-Jan-18
 * Time: 1:29 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */
package nybsys.tillboxweb.bll.manager;


import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.TillBoxWebEntities.Business;
import nybsys.tillboxweb.TillBoxWebModels.RegistrationInvitationModel;
import nybsys.tillboxweb.TillBoxWebModels.UserModel;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.coreEnum.BusinessType;
import nybsys.tillboxweb.coreEnum.ProductType;
import nybsys.tillboxweb.models.BusinessModel;
import nybsys.tillboxweb.models.UserBusinessRightMapperModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
//@Lazy
public class BusinessBllManager extends BaseBll<Business> {
    private static final Logger log = LoggerFactory.getLogger(BusinessBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(Business.class);
        Core.runTimeModelType.set(BusinessModel.class);
    }

    @Autowired
    GstSettingBllManager gstSettingBllManager;

    @Autowired
    UserBusinessRightMapperBllManager userBusinessRightMapperBllManager;

    public List<BusinessModel> getBusinessListByBusinessID(List<UserBusinessRightMapperModel> lstUserBusinessRightMapperModels) throws Exception {
        List<BusinessModel> lstBusiness = new ArrayList<BusinessModel>();
        StringBuilder businessIDs = new StringBuilder("");
        int size = lstUserBusinessRightMapperModels.size();
        for (int index = 0; index < size; index++) {
            String businessID = lstUserBusinessRightMapperModels.get(index).getBusinessID().toString();
            businessIDs.append(businessID);
            if (index + 1 < size) {
                businessIDs.append(",");
            }
        }
        String hql = "FROM Business E WHERE E.status =" + TillBoxAppEnum.Status.Active.get() + " AND E.businessID IN (" + businessIDs + ")";
        try {
            lstBusiness = this.executeHqlQuery(hql, BusinessModel.class, TillBoxAppEnum.QueryType.Select.get());
        } catch (Exception ex) {
            log.error("BusinessBllManager -> getBusinessListByBusinessID got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstBusiness;
    }

    public BusinessModel saveOrUpdateBusiness(BusinessModel businessModelReq) throws Exception {
        BusinessModel businessModel = new BusinessModel();
        try {
            businessModel = businessModelReq;

            Calendar now = Calendar.getInstance();
            int year = now.get(Calendar.YEAR);
            int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
            int day = now.get(Calendar.DAY_OF_MONTH);
            int max = 9999;
            int min = 1000;
            BigInteger serialNumber ;
            do {
                Random random = new Random();
                int randomValue = random.nextInt((max - min) + 1) + min;
                serialNumber = new BigInteger(String.valueOf(year) + String.valueOf(month) + String.valueOf(day) + String.valueOf(randomValue));
            }while (IsBusinessExistsBySerialNumber(serialNumber));

            businessModel.setSerialNo(serialNumber);

            //save
            if (businessModel.getBusinessID() == null || businessModel.getBusinessID() == 0) {
                businessModel = this.save(businessModel);
                if (businessModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_USER;
                }
            } else {//update
                businessModel = this.update(businessModel);
                if (businessModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_UPDATE_USER;
                }
            }
        } catch (Exception ex) {
            log.error("BusinessBllManager -> saveOrUpdateBusiness got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return businessModel;
    }

    public BusinessModel saveBusinessByRegistrationInvitationModelAndDbName(RegistrationInvitationModel registrationInvitationModelReq, String dbName) throws Exception {
        BusinessModel businessModel = new BusinessModel();
        List< BusinessModel> lstBusinessModel = new ArrayList<>();
        BusinessModel whereCondition = new BusinessModel();
        try {

            //check business already exists or not
            whereCondition.setBusinessName(registrationInvitationModelReq.getUserID());
            whereCondition.setBusinessDBName(dbName);
            lstBusinessModel = this.getAllByConditionWithActive(whereCondition);
            if( lstBusinessModel.size()>0) {
                businessModel = lstBusinessModel.get(0);
            }else {

                businessModel.setOwner(registrationInvitationModelReq.getUserID());
                businessModel.setBusinessDBName(dbName);
                businessModel.setBusinessName(registrationInvitationModelReq.getUserID());
                businessModel.setBusinessTypeID(registrationInvitationModelReq.getBusinessTypeID());
                businessModel.setEmail(registrationInvitationModelReq.getUserID());
                businessModel.setPhone(registrationInvitationModelReq.getCellPhone());
                businessModel.setProductTypeID(registrationInvitationModelReq.getProductTypeID());
                Calendar now = Calendar.getInstance();
                int year = now.get(Calendar.YEAR);
                int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
                int day = now.get(Calendar.DAY_OF_MONTH);
                int max = 9999;
                int min = 1000;
                BigInteger serialNumber;
                do {
                    Random random = new Random();
                    int randomValue = random.nextInt((max - min) + 1) + min;
                    serialNumber = new BigInteger(String.valueOf(year) + String.valueOf(month) + String.valueOf(day) + String.valueOf(randomValue));
                } while (IsBusinessExistsBySerialNumber(serialNumber));
                businessModel.setSerialNo(serialNumber);
                //save
                if (businessModel.getBusinessID() == null || businessModel.getBusinessID() == 0) {
                    businessModel = this.save(businessModel);
                    if (businessModel == null) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_BUSINESS;
                    }
                }
            }
        } catch (Exception ex) {
            log.error("BusinessBllManager -> saveBusinessByUserModelAndBusinessDb got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return businessModel;
    }

    public BusinessModel getBusinessByBusinessID(int businessID) throws Exception {
        BusinessModel businessModel = new BusinessModel();
        List<BusinessModel> lstBusinessModel = new ArrayList<>();
        try {
            businessModel.setBusinessID(businessID);
            lstBusinessModel = this.getAllByConditions(businessModel);
            if (lstBusinessModel.size() > 0) {
                businessModel = lstBusinessModel.get(0);
            } else {
                businessModel = null;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_BUSINESS;
            }

        } catch (Exception ex) {
            log.error("BusinessBllManager -> getBusinessByBusinessID got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return businessModel;
    }

    public List<BusinessModel> getBusinessListByUser(String userID) throws Exception {
        List<BusinessModel> lstBusiness = new ArrayList<BusinessModel>();
        try {
            // get business id from user business right mapper by user;
            UserBusinessRightMapperModel whereCondition = new UserBusinessRightMapperModel();
            whereCondition.setUserID(userID);
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());
            List<UserBusinessRightMapperModel> lstUserBusinessRightMapper = new ArrayList<UserBusinessRightMapperModel>();
            lstUserBusinessRightMapper = this.userBusinessRightMapperBllManager.getAllByConditions(whereCondition);

            //get list of business by user's businessID's
            if (lstUserBusinessRightMapper.size() > 0) {
                lstBusiness = this.getBusinessListByBusinessID(lstUserBusinessRightMapper);
            }else
            {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_BUSINESS;
            }
        } catch (Exception ex) {
            log.error("BusinessBllManager -> getBusinessListByUser got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return  lstBusiness;
    }
    public boolean IsBusinessExistsBySerialNumber(BigInteger serialNumber) throws Exception {
        BusinessModel businessModel = new BusinessModel();
        List<BusinessModel> lstBusinessModel = new ArrayList<>();
        try {
            businessModel.setSerialNo(serialNumber);
            lstBusinessModel = this.getAllByConditions(businessModel);
            if (lstBusinessModel.size() > 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception ex) {
            log.error("BusinessBllManager -> IsBusinessExistsBySerialNumber got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
    }
}
