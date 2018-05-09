/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 09-Jan-18
 * Time: 2:44 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */
package nybsys.tillboxweb.bll.manager;


import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.TillBoxWebEntities.UserBusinessRightMapper;
import nybsys.tillboxweb.TillBoxWebModels.UserModel;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.coreEnum.AccessRight;
import nybsys.tillboxweb.models.UserBusinessRightMapperModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
//@Lazy
public class UserBusinessRightMapperBllManager extends BaseBll<UserBusinessRightMapper> {
    private static final Logger log = LoggerFactory.getLogger(UserBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(UserBusinessRightMapper.class);
        Core.runTimeModelType.set(UserBusinessRightMapperModel.class);
    }

    public UserBusinessRightMapperModel saveOrUpdate(UserBusinessRightMapperModel userBusinessRightMapperModelReq) throws Exception {
        UserBusinessRightMapperModel userBusinessRightMapperModel;
        try {
            userBusinessRightMapperModel = userBusinessRightMapperModelReq;
            //save
            if (userBusinessRightMapperModel.getUserBusinessRightMapperID() == null || userBusinessRightMapperModel.getUserBusinessRightMapperID() == 0) {
                userBusinessRightMapperModel = this.save(userBusinessRightMapperModel);
                if (userBusinessRightMapperModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.USER_BUSINESS_RIGHT_MAPPER_SAVE_FAILED;
                }
            } else {
                userBusinessRightMapperModel = this.update(userBusinessRightMapperModel);
                if (userBusinessRightMapperModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.USER_BUSINESS_RIGHT_MAPPER_UPDATE_FAILED;
                }
            }
        } catch (Exception ex) {
            log.error("UserBusinessRightMapperBllManager -> saveOrUpdate got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return userBusinessRightMapperModel;
    }

    public UserBusinessRightMapperModel saveBusinessByUserModelAndDbName(UserModel userModelReq, Integer businessID) throws Exception {
        UserBusinessRightMapperModel userBusinessRightMapperModel = new UserBusinessRightMapperModel();
        List<UserBusinessRightMapperModel> lstUserBusinessRightMapperModel = new ArrayList<>();
        UserBusinessRightMapperModel whereCondition = new UserBusinessRightMapperModel();
        try {
            //check user business right mapper already exists or not
            whereCondition.setUserID(userModelReq.getUserID());
            whereCondition.setBusinessID(businessID);
            lstUserBusinessRightMapperModel = this.getAllByConditionWithActive(whereCondition);
            if (lstUserBusinessRightMapperModel.size() > 0) {
                userBusinessRightMapperModel = lstUserBusinessRightMapperModel.get(0);
            } else {
                userBusinessRightMapperModel.setAccessRightID(AccessRight.Administrator.get());
                userBusinessRightMapperModel.setUserID(userModelReq.getUserID());
                userBusinessRightMapperModel.setBusinessID(businessID);
                userBusinessRightMapperModel.setBusinessStatus(TillBoxAppEnum.Status.Active.get());
                //save
                userBusinessRightMapperModel = this.save(userBusinessRightMapperModel);
                if (userBusinessRightMapperModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_BUSINESS;
                }
            }

        } catch (Exception ex) {
            log.error("UserBusinessRightMapperBllManager -> saveBusinessByUserModelAndBusinessDb got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return userBusinessRightMapperModel;
    }

    public List<UserBusinessRightMapperModel> getAllActiveAndInactiveUserBusinessRightMapperByBusinessID(Integer businessID) throws Exception {
        List<UserBusinessRightMapperModel> lstUserBusinessRightMapperModel = new ArrayList<>();
        UserBusinessRightMapperModel whereCondition = new UserBusinessRightMapperModel();

        try {
            String hql = "FROM UserBusinessRightMapper UB WHERE UB.businessID = " + businessID + " AND UB.status IN (" + TillBoxAppEnum.Status.Active.get() + "," + TillBoxAppEnum.Status.Inactive.get() + ")";

            lstUserBusinessRightMapperModel = this.executeHqlQuery(hql, UserBusinessRightMapperModel.class, TillBoxAppEnum.QueryType.Select.get());
            if (lstUserBusinessRightMapperModel.size() == 0) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_INVITED_USER;
            }
        } catch (Exception ex) {
            log.error("UserBusinessRightMapperBllManager -> getAllActiveAndInactiveUserByBusinessID got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstUserBusinessRightMapperModel;
    }


    public UserBusinessRightMapperModel getUserBusinessRightMapperByUserIDAndBusinessID(String userID, Integer businessID) throws Exception {
        List<UserBusinessRightMapperModel> lstUserBusinessRightMapperModel = new ArrayList<>();
        UserBusinessRightMapperModel userBusinessRightMapperModel = new UserBusinessRightMapperModel();

        try {
            userBusinessRightMapperModel.setStatus(TillBoxAppEnum.Status.Active.get());
            userBusinessRightMapperModel.setUserID(userID);
            userBusinessRightMapperModel.setBusinessID(businessID);

            lstUserBusinessRightMapperModel = this.getAllByConditions(userBusinessRightMapperModel);
            if (lstUserBusinessRightMapperModel.size() > 0) {
                userBusinessRightMapperModel = lstUserBusinessRightMapperModel.get(0);
            } else {
                userBusinessRightMapperModel = null;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_USER_BUSINESS_RIGHT_MAPPER;
            }
        } catch (Exception ex) {
            log.error("UserBusinessRightMapperBllManager -> getUserBusinessRightMapperByUserIDAndBusinessID: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return userBusinessRightMapperModel;
    }
}

