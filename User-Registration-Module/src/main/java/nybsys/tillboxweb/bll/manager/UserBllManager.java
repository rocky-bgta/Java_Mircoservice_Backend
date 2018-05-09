package nybsys.tillboxweb.bll.manager;


import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.TillBoxWebEntities.User;
import nybsys.tillboxweb.TillBoxWebModels.UserInvitationModel;
import nybsys.tillboxweb.TillBoxWebModels.UserModel;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.models.UserBusinessRightMapperModel;
import nybsys.tillboxweb.models.VMTokenModel;
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
//@Lazy
public class UserBllManager extends BaseBll<User> {
    private static final Logger log = LoggerFactory.getLogger(UserBllManager.class);
    @Autowired
    UserBusinessRightMapperBllManager userBusinessRightMapperBllManager;

    @Autowired
    UserInvitationBllManager userInvitationBllManager;

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(User.class);
        Core.runTimeModelType.set(UserModel.class);
    }

    public UserModel saveUser(UserModel userModelReq) throws Exception {
        UserModel userModel = new UserModel();
        List<UserModel> lstUserModel = new ArrayList<>();
        try {
            userModel = userModelReq;
            //first check user exists or not
            UserModel whereCondition = new UserModel();
            whereCondition.setUserID(userModel.getUserID());
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());
            lstUserModel = this.getAllByConditions(whereCondition);
            Core.clientMessage.get().messageCode = null;
            if (lstUserModel.size() > 0) {
                Core.clientMessage.get().message = MessageConstant.USER_ALREADY_EXISTS;
                Core.clientMessage.get().userMessage = MessageConstant.USER_ALREADY_EXISTS;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                return userModel;
            }
            //save
            userModel = this.save(userModel);
            if (userModel == null) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_USER;
            }

        } catch (Exception ex) {
            log.error("UserBllManager -> saveOrUpdateUser got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return userModel;
    }

    public UserModel updateUser(UserModel userModelReq) throws Exception {
        UserModel userModel = new UserModel();
        List<UserModel> lstUserModel = new ArrayList<>();
        try {
            userModel = userModelReq;
            userModel = this.update(userModel);
            if (userModel == null) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_UPDATE_USER;
            }

        } catch (Exception ex) {
            log.error("UserBllManager -> updateUser got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return userModel;
    }

    public boolean isUserExist(UserModel userModel) throws Exception {

        UserModel whereCondition = new UserModel();
        whereCondition.setUserID(userModel.getUserID());
        whereCondition.setPassword(userModel.getPassword());

        List<UserModel> userList= new ArrayList<UserModel>();
        try {
            userList = this.getAllByConditions(whereCondition);
        } catch (Exception ex) {
            throw  ex;
        }

        if(userList.size() > 0)
        {
            return true;
        }else {
            return false;
        }

    }
    public UserBusinessRightMapperModel updateUserBusinessRightMapper(UserBusinessRightMapperModel userBusinessRightMapperModelReq) throws Exception {
        UserBusinessRightMapperModel userBusinessRightMapperModel = new UserBusinessRightMapperModel();
        List<UserBusinessRightMapperModel> lstUserBusinessRightMapperModel = new ArrayList<>();
        UserBusinessRightMapperModel whereCondition = new UserBusinessRightMapperModel();
        try {
            whereCondition.setBusinessID(userBusinessRightMapperModelReq.getBusinessID());
            whereCondition.setUserID(userBusinessRightMapperModelReq.getUserID());

            lstUserBusinessRightMapperModel = this.userBusinessRightMapperBllManager.getAllByConditionWithActive(whereCondition);
            if (lstUserBusinessRightMapperModel.size() == 0) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_USER_BUSINESS_RIGHT_MAPPER;
                return userBusinessRightMapperModel;
            }else {
                userBusinessRightMapperModel = lstUserBusinessRightMapperModel.get(0);
                userBusinessRightMapperModel.setAccessRightID(userBusinessRightMapperModelReq.getAccessRightID());
                userBusinessRightMapperModel.setFirstName(userBusinessRightMapperModelReq.getFirstName());
                userBusinessRightMapperModel.setLastName(userBusinessRightMapperModelReq.getLastName());
            }

            userBusinessRightMapperModel = this.userBusinessRightMapperBllManager.update(userBusinessRightMapperModel);
            if (userBusinessRightMapperModel == null) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_UPDATE_USER;
            }


        } catch (Exception ex) {
            log.error("UserBllManager -> updateUser got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return userBusinessRightMapperModel;
    }

    public List<UserModel> getAllUserByBusinessID(Integer businessID) throws Exception {
        List<UserModel> lstUserModel = new ArrayList<>();
        try {

            // first get user business right mapper
            List<UserBusinessRightMapperModel> lstUserBusinessRightMapperModel = new ArrayList<UserBusinessRightMapperModel>();
            UserBusinessRightMapperModel whereCondition = new UserBusinessRightMapperModel();
            whereCondition.setBusinessID(businessID);
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());
            lstUserBusinessRightMapperModel = this.userBusinessRightMapperBllManager.getAllByConditions(whereCondition);

            StringBuilder userIDs = new StringBuilder("");
            int size = lstUserBusinessRightMapperModel.size();
            for (int index = 0; index < size; index++) {
                String userID = lstUserBusinessRightMapperModel.get(index).getUserID().toString();
                userIDs.append("'" + userID + "'");
                if (index + 1 < size) {
                    userIDs.append(",");
                }
            }

            //then get users
            String hql = "FROM User U WHERE U.status = " + TillBoxAppEnum.Status.Active.get() + " AND U.userID IN (" + userIDs + ")";

            lstUserModel = this.executeHqlQuery(hql, UserModel.class, TillBoxAppEnum.QueryType.Select.get());
            if (lstUserModel.size() == 0) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_USER;
            }
        } catch (Exception ex) {
            log.error("UserBllManager -> getAllUserByBusinessID got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstUserModel;
    }

    public UserModel activeUser(UserModel userModelReq, Integer businessID) throws Exception {
        UserModel userModel = new UserModel();
        List<UserBusinessRightMapperModel> lstUserBusinessRightMapperModel;
        UserBusinessRightMapperModel userBusinessRightMapperModel = new UserBusinessRightMapperModel();
        List<UserBusinessRightMapperModel> lstUserBusinessRightMapperModels = new ArrayList<>();
        try {
            userModel = userModelReq;

            userBusinessRightMapperModel.setBusinessID(businessID);
            userBusinessRightMapperModel.setUserID(userModel.getUserID());
            userBusinessRightMapperModel.setStatus(TillBoxAppEnum.Status.Inactive.get());
            lstUserBusinessRightMapperModel = this.userBusinessRightMapperBllManager.getAllByConditions(userBusinessRightMapperModel);
            if (lstUserBusinessRightMapperModel.size() > 0) {
                userBusinessRightMapperModel = lstUserBusinessRightMapperModel.get(0);
                userBusinessRightMapperModel.setStatus(TillBoxAppEnum.Status.Active.get());

                userBusinessRightMapperModel = this.userBusinessRightMapperBllManager.update(userBusinessRightMapperModel);

                if (userBusinessRightMapperModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_ACTIVE_USER;
                }
            } else {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_ACTIVE_USER;
            }

        } catch (Exception ex) {
            log.error("UserBllManager -> activeUser got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return userModel;
    }

    public UserModel inActiveUser(UserModel userModelReq, Integer businessID) throws Exception {
        UserModel userModel = new UserModel();
        List<UserBusinessRightMapperModel> lstUserBusinessRightMapperModel;
        UserBusinessRightMapperModel userBusinessRightMapperModel = new UserBusinessRightMapperModel();
        List<UserBusinessRightMapperModel> lstUserBusinessRightMapperModels = new ArrayList<>();
        try {
            userModel = userModelReq;

            userBusinessRightMapperModel.setBusinessID(businessID);
            userBusinessRightMapperModel.setUserID(userModel.getUserID());
            userBusinessRightMapperModel.setStatus(TillBoxAppEnum.Status.Active.get());
            lstUserBusinessRightMapperModel = this.userBusinessRightMapperBllManager.getAllByConditions(userBusinessRightMapperModel);
            if (lstUserBusinessRightMapperModel.size() > 0) {
                userBusinessRightMapperModel = lstUserBusinessRightMapperModel.get(0);
                userBusinessRightMapperModel.setStatus(TillBoxAppEnum.Status.Inactive.get());

                userBusinessRightMapperModel = this.userBusinessRightMapperBllManager.update(userBusinessRightMapperModel);

                if (userBusinessRightMapperModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_IN_ACTIVE_USER;
                }
            } else {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_IN_ACTIVE_USER;
            }

        } catch (Exception ex) {
            log.error("UserBllManager -> inActiveUser got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return userModel;
    }

    public void createUserWithBusinessId(VMTokenModel tokenModelReq) throws Exception {
        VMTokenModel tokenModel = new VMTokenModel();
        List<UserInvitationModel> lstUserInvitationModel;
        UserInvitationModel userInvitationModel = new UserInvitationModel();
        try {
            tokenModel = tokenModelReq;

            userInvitationModel.setToken(tokenModel.getToken());
            userInvitationModel.setDone(false);
            userInvitationModel.setStatus(TillBoxAppEnum.Status.Active.get());

            //select default dbConfig
            this.setDefaultDateBase();
            //invited token's user
            lstUserInvitationModel = this.userInvitationBllManager.getAllByConditions(userInvitationModel);


            if (lstUserInvitationModel.size() > 0) {
                userInvitationModel = lstUserInvitationModel.get(0);

                // check token period is expired or not
                if (userInvitationModel.getExpireDate().after(new Date())) {


                    //check is user already exists in this business
                    UserBusinessRightMapperModel userBusinessRightMapperModel ;
                    userBusinessRightMapperModel = this.userBusinessRightMapperBllManager.getUserBusinessRightMapperByUserIDAndBusinessID(lstUserInvitationModel.get(0).getUserID(), userInvitationModel.getBusinessID());
                    Core.clientMessage.get().messageCode = null;

                    //user is already exists in this business
                    if (userBusinessRightMapperModel != null) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.USER_ALREADY_EXISTS;
                        Core.clientMessage.get().userMessage = MessageConstant.USER_ALREADY_EXISTS;
                    } else {

                        UserModel userModel;
                        //check user already sign up or not
                        userModel = this.getById(lstUserInvitationModel.get(0).getUserID());
                        // not exists in user table
                        if (userModel == null) {

                            userModel = new UserModel();
                            userModel.setUserID(lstUserInvitationModel.get(0).getUserID());
                            userModel.setPassword(tokenModel.getPassword());
                            userModel.setName("");
                            userModel.setSurname("");
                            userModel.setCellPhone(tokenModel.getCellPhone());
                            userModel = this.save(userModel);
                            if (userModel == null){
                                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                                Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_USER;
                                return;
                            }
                        }

                        userBusinessRightMapperModel = new UserBusinessRightMapperModel();
                        userBusinessRightMapperModel.setBusinessStatus(TillBoxAppEnum.Status.Active.get());
                        userBusinessRightMapperModel.setAccessRightID(userInvitationModel.getAccessRightID());
                        userBusinessRightMapperModel.setUserID(userInvitationModel.getUserID());
                        userBusinessRightMapperModel.setBusinessID(userInvitationModel.getBusinessID());
                        userBusinessRightMapperModel.setFirstName("");
                        userBusinessRightMapperModel.setLastName("");
                        userBusinessRightMapperModel = this.userBusinessRightMapperBllManager.save(userBusinessRightMapperModel);
                        if (userBusinessRightMapperModel == null){
                            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                            Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_USER_BUSINESS_RIGHT_MAPPER;
                            return;
                        }

                        // set token's user is done
                        userInvitationModel.setDone(true);

                        userInvitationModel = this.userInvitationBllManager.update(userInvitationModel);

                        if (userInvitationModel == null){
                            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                            Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_USER_INVITATION;
                        }
                    }
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.TOKEN_EXPIRED;
                    Core.clientMessage.get().userMessage = MessageConstant.TOKEN_EXPIRED;
                }
            } else {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_INVITED_USER;
                Core.clientMessage.get().userMessage = MessageConstant.FAILED_TO_GET_INVITED_USER;

            }

        } catch (Exception ex) {
            log.error("UserBllManager -> createUserWithBusinessId got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
    }

    public void createSystemUserRelationWithInvitedBusiness(UserInvitationModel userInvitationModel, UserModel userModel) throws Exception {
        try {
                // check token period is expired or not
                if (userInvitationModel.getExpireDate().after(new Date())) {

                    //check is user already exists in this business
                    UserBusinessRightMapperModel userBusinessRightMapperModel ;
                    userBusinessRightMapperModel = this.userBusinessRightMapperBllManager.getUserBusinessRightMapperByUserIDAndBusinessID(userModel.getUserID(), userInvitationModel.getBusinessID());
                    Core.clientMessage.get().messageCode = null;

                    //user is already exists in this business
                    if (userBusinessRightMapperModel != null) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.USER_ALREADY_EXISTS;
                        Core.clientMessage.get().userMessage = MessageConstant.USER_ALREADY_EXISTS;
                    } else {

                        userBusinessRightMapperModel = new UserBusinessRightMapperModel();
                        userBusinessRightMapperModel.setBusinessStatus(TillBoxAppEnum.Status.Active.get());
                        userBusinessRightMapperModel.setAccessRightID(userInvitationModel.getAccessRightID());
                        userBusinessRightMapperModel.setUserID(userInvitationModel.getUserID());
                        userBusinessRightMapperModel.setBusinessID(userInvitationModel.getBusinessID());
                        userBusinessRightMapperModel.setFirstName(userInvitationModel.getFirstName());
                        userBusinessRightMapperModel.setLastName(userInvitationModel.getLastName());
                        userBusinessRightMapperModel = this.userBusinessRightMapperBllManager.save(userBusinessRightMapperModel);

                        if (userBusinessRightMapperModel == null) {
                            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                            Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_USER_BUSINESS_RIGHT_MAPPER;
                            return;
                        }
                        // set token's user is done
                        userInvitationModel.setDone(true);

                        userInvitationModel = this.userInvitationBllManager.update(userInvitationModel);

                        if (userInvitationModel == null) {
                            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                            Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_USER_INVITATION;
                        }
                    }
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.TOKEN_EXPIRED;
                    Core.clientMessage.get().userMessage = MessageConstant.TOKEN_EXPIRED;
                }


        } catch (Exception ex) {
            log.error("UserBllManager -> createUserWithBusinessId got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
    }
}
