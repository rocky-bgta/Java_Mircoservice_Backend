package nybsys.tillboxweb.bll.manager;


import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.TillBoxWebEntities.UserInvitation;
import nybsys.tillboxweb.TillBoxWebModels.UserInvitationModel;
import nybsys.tillboxweb.Utils.TillBoxUtils;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static nybsys.tillboxweb.constant.TillBoxAppConstant.FRONT_END_BASE_URL;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserInvitationBllManager extends BaseBll<UserInvitation> {
    private static final Logger log = LoggerFactory.getLogger(UserInvitationModel.class);

    @Autowired
    UserBusinessRightMapperBllManager userBusinessRightMapperBllManager;

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(UserInvitation.class);
        Core.runTimeModelType.set(UserInvitationModel.class);
    }

    public UserInvitationModel invite(UserInvitationModel userInvitationModelReq, Integer businessID) throws Exception {
        List<UserInvitationModel> lstUserInvitationModel;
        UserInvitationModel userInvitationModel = new UserInvitationModel();
        try {
            userInvitationModel = userInvitationModelReq;
            //check user exists or not
            this.setDefaultDateBase();
            if (this.userBusinessRightMapperBllManager.getUserBusinessRightMapperByUserIDAndBusinessID(userInvitationModel.getUserID(), businessID) != null) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.USER_ALREADY_EXISTS;
                Core.clientMessage.get().userMessage = MessageConstant.USER_ALREADY_EXISTS;
                return userInvitationModel;

            }
            Core.clientMessage.get().messageCode = null;

            //(1) check user already invited or not in this business
            UserInvitationModel whereCondition = new UserInvitationModel();
            whereCondition.setBusinessID(businessID);
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());
            whereCondition.setDone(false);
            whereCondition.setUserID(userInvitationModel.getUserID());

            lstUserInvitationModel = this.getAllByConditions(whereCondition);
            if (lstUserInvitationModel.size() > 0) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.USER_ALREADY_INVITED;
                Core.clientMessage.get().userMessage = MessageConstant.USER_ALREADY_INVITED;
                return userInvitationModel;

            } else {
                //save invitation
                userInvitationModel.setUserInvitationEntityID(null);
                userInvitationModel.setDone(false);
                userInvitationModel.setStatus(TillBoxAppEnum.Status.Active.get());
                userInvitationModel.setCreatedBy(userInvitationModel.getUserID());
                userInvitationModel.setToken(TillBoxUtils.getUUID());
                userInvitationModel.setCreatedDate(new Date());
                Date currentDate = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(currentDate);
                cal.add(Calendar.DATE, 3);
                userInvitationModel.setExpireDate(cal.getTime());
                userInvitationModel = this.save(userInvitationModel);
                if (userInvitationModel != null) {
                    //send invitation
                    TillBoxUtils.sendEmail(userInvitationModel.getUserID(), "send invitation", FRONT_END_BASE_URL+"welcome/"+userInvitationModel.getToken());
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_SEND_USER_INVITATION;
                }
            }


        } catch (Exception ex) {
            log.error("UserInvitationBllManager -> invite: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return userInvitationModel;
    }

    public UserInvitationModel reInvite(UserInvitationModel userInvitationModelReq, Integer businessID) throws Exception {

        List<UserInvitationModel> lstUserInvitationModel;
        UserInvitationModel userInvitationModel = new UserInvitationModel();
        try {
            userInvitationModel = userInvitationModelReq;

            userInvitationModel.setUserID(userInvitationModel.getUserID());
            userInvitationModel.setBusinessID(businessID);
            userInvitationModel.setDone(false);
            userInvitationModel.setStatus(TillBoxAppEnum.Status.Active.get());
            lstUserInvitationModel = this.getAllByConditions(userInvitationModel);
            if (lstUserInvitationModel.size() > 0) {
                userInvitationModel = lstUserInvitationModel.get(0);
                userInvitationModel.setCreatedDate(new Date());
                Date currentDate = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(currentDate);
                cal.add(Calendar.DATE, 1);
                userInvitationModel.setExpireDate(cal.getTime());
                userInvitationModel.setUpdatedBy(userInvitationModel.getUserID());
                userInvitationModel.setUpdatedDate(new Date());
                userInvitationModel = this.update(userInvitationModel);
                if (userInvitationModel != null) {
                    //send invitation
                    TillBoxUtils.sendEmail(userInvitationModel.getUserID(), "send re-invitation", FRONT_END_BASE_URL+"welcome/"+ userInvitationModel.getToken());
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_SEND_USER_INVITATION;
                }
            } else {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.NO_INVITED_USER_FOUND;
                Core.clientMessage.get().userMessage = MessageConstant.NO_INVITED_USER_FOUND;

            }

        } catch (Exception ex) {
            log.error("UserInvitationBllManager -> reInvite: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return userInvitationModel;
    }

    public UserInvitationModel removeInvitation(UserInvitationModel userInvitationModelReq, Integer businessID) throws Exception {
        List<UserInvitationModel> lstUserInvitationModel;
        UserInvitationModel userInvitationModel = new UserInvitationModel();
        try {
            userInvitationModel = userInvitationModelReq;

            userInvitationModel.setUserID(userInvitationModel.getUserID());
            userInvitationModel.setBusinessID(businessID);
            userInvitationModel.setStatus(TillBoxAppEnum.Status.Active.get());
            lstUserInvitationModel = this.getAllByConditions(userInvitationModel);
            if (lstUserInvitationModel.size() > 0) {
                userInvitationModel = lstUserInvitationModel.get(0);
                userInvitationModel.setStatus(TillBoxAppEnum.Status.Deleted.get());
                userInvitationModel = this.update(userInvitationModel);
                if (userInvitationModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_REMOVE_USER_INVITATION;
                }
            } else {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.NO_INVITED_USER_FOUND;
                Core.clientMessage.get().userMessage = MessageConstant.NO_INVITED_USER_FOUND;
            }

        } catch (Exception ex) {
            log.error("UserInvitationBllManager -> removeInvitation: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return userInvitationModel;
    }

    public List<UserInvitationModel> getAllInvitedUserByBusinessID(Integer businessID) throws Exception {
        List<UserInvitationModel> lstUserInvitationModel = new ArrayList<>();
        UserInvitationModel whereCondition = new UserInvitationModel();

        try {
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());
            whereCondition.setBusinessID(businessID);
            whereCondition.setDone(false);
            lstUserInvitationModel = this.getAllByConditions(whereCondition);
            if (lstUserInvitationModel.size() == 0) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_INVITED_USER;
            }
        } catch (Exception ex) {
            log.error("UserInvitationBllManager -> getAllInvitedUserByBusinessID got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstUserInvitationModel;
    }

    public UserInvitationModel getInvitedUserByToken(String token) throws Exception {
        List<UserInvitationModel> lstUserInvitationModel = new ArrayList<>();
        UserInvitationModel whereCondition = new UserInvitationModel();
        UserInvitationModel userInvitationModel = new UserInvitationModel();

        try {
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());
            whereCondition.setToken(token);
            whereCondition.setDone(false);
            lstUserInvitationModel = this.getAllByConditions(whereCondition);
            if (lstUserInvitationModel.size() == 0) {
                userInvitationModel = null;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_INVITED_USER;
            }else{
                userInvitationModel = lstUserInvitationModel.get(0);
            }
        } catch (Exception ex) {
            log.error("UserInvitationBllManager -> getAllInvitedUserByBusinessID got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return userInvitationModel;
    }

}
