package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.TillBoxWebEntities.ForgetPasswordToken;
import nybsys.tillboxweb.TillBoxWebModels.ForgetPasswordTokenModel;
import nybsys.tillboxweb.TillBoxWebModels.UserModel;
import nybsys.tillboxweb.Utils.TillBoxUtils;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.models.VMChangePasswordModel;
import nybsys.tillboxweb.models.VMForgetPasswordModel;
import org.apache.commons.lang3.StringUtils;
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

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PasswordBllManager extends BaseBll<ForgetPasswordToken> {
    private static final Logger log = LoggerFactory.getLogger(PasswordBllManager.class);

    @Autowired
    UserBllManager userBllManager;

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(ForgetPasswordToken.class);
        Core.runTimeModelType.set(ForgetPasswordTokenModel.class);
    }

    public VMChangePasswordModel changePassword(VMChangePasswordModel changePasswordModelReq) throws Exception {
        UserModel userModel = new UserModel();
        List<UserModel> lstUserModel = new ArrayList<>();
        try {
            userModel.setPassword(changePasswordModelReq.getPassword());
            userModel.setUserID(changePasswordModelReq.getUserID());
            userModel.setStatus(TillBoxAppEnum.Status.Active.get());
            //check old and new password are same or not
            if (!StringUtils.equals(changePasswordModelReq.getPassword(), changePasswordModelReq.getNewPassword())) {

                lstUserModel = this.userBllManager.getAllByConditions(userModel);
                if (lstUserModel.size() > 0) {
                    userModel = lstUserModel.get(0);
                    userModel.setPassword(changePasswordModelReq.getNewPassword());
                    userModel = this.userBllManager.update(userModel);
                    if (userModel == null) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.FAILED_TO_CHANGE_PASSWORD;
                    }
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_USER;
                }
            } else {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.OLD_AND_NEW_PASSWORD_ARE_SAME;
                Core.clientMessage.get().userMessage = MessageConstant.OLD_AND_NEW_PASSWORD_ARE_SAME;
            }
        } catch (Exception ex) {
            log.error("PasswordBllManager -> changePassword got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return changePasswordModelReq;
    }

    public VMForgetPasswordModel forgetPassword(VMForgetPasswordModel forgetPasswordModelReq) throws Exception {
        VMForgetPasswordModel forgetPasswordModel;
        List<ForgetPasswordTokenModel> lstForgetPasswordTokenModel;
        ForgetPasswordTokenModel forgetPasswordTokenModel;
        try {
            forgetPasswordModel = forgetPasswordModelReq;

            ForgetPasswordTokenModel whereCondition = new ForgetPasswordTokenModel();
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());
            whereCondition.setToken(forgetPasswordModel.getToken());
            lstForgetPasswordTokenModel = this.getAllByConditions(whereCondition);

            //check valid token
            if (lstForgetPasswordTokenModel.size() > 0) {
                forgetPasswordTokenModel = lstForgetPasswordTokenModel.get(0);
                //check expiration
                if (forgetPasswordTokenModel.getValidation().before(new Date())) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.TOKEN_EXPIRED;
                    Core.clientMessage.get().userMessage = MessageConstant.TOKEN_EXPIRED;
                } else {
                    List<UserModel> lstUserModel;
                    UserModel userModel = new UserModel();
                    userModel.setUserID(forgetPasswordTokenModel.getUserID());
                    userModel.setStatus(TillBoxAppEnum.Status.Active.get());

                    lstUserModel = this.userBllManager.getAllByConditions(userModel);
                    //is user exists
                    if (lstUserModel.size() > 0) {
                        userModel = lstUserModel.get(0);
                        //update user password
                        userModel.setPassword(forgetPasswordModel.getPassword());
                        userModel = this.userBllManager.update(userModel);
                        if (userModel == null) {
                            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                            Core.clientMessage.get().message = MessageConstant.FAILED_TO_CHANGE_PASSWORD;
                            return forgetPasswordModel;
                        }

                        //update forget password token table status done
                        forgetPasswordTokenModel = this.softDelete(forgetPasswordTokenModel);
                        if (forgetPasswordTokenModel == null) {
                            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                            Core.clientMessage.get().message = MessageConstant.FAILED_TO_CHANGE_PASSWORD;
                            return forgetPasswordModel;
                        }

                    } else {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_USER;
                        Core.clientMessage.get().userMessage = MessageConstant.FAILED_TO_GET_USER;
                    }
                }
            } else {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_TOKEN;
                Core.clientMessage.get().userMessage = MessageConstant.FAILED_TO_GET_TOKEN;
            }


        } catch (Exception ex) {
            log.error("PasswordBllManager -> forgetPassword got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return forgetPasswordModel;
    }

    public ForgetPasswordTokenModel getPassword(ForgetPasswordTokenModel forgetPasswordTokenModelReq) throws Exception {
        ForgetPasswordTokenModel forgetPasswordTokenModel;
        try {
            forgetPasswordTokenModel = forgetPasswordTokenModelReq;
            //check is this a valid user
            UserModel userModel = new UserModel();
            userModel.setUserID(forgetPasswordTokenModel.getUserID());
            userModel.setStatus(TillBoxAppEnum.Status.Active.get());

            if (this.userBllManager.getAllByConditions(userModel).size() > 0) {
                String forgetPasswordToken = TillBoxUtils.getUUID();
                Date currentDate = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(currentDate);
                cal.add(Calendar.DATE, 1);
                forgetPasswordTokenModel.setValidation(cal.getTime());
                forgetPasswordTokenModel.setToken(forgetPasswordToken);

                forgetPasswordTokenModel = this.save(forgetPasswordTokenModel);
                if (forgetPasswordTokenModel != null) {
                    // send mail to user
                    TillBoxUtils.sendEmail(forgetPasswordTokenModel.getUserID(), "forget password", TillBoxAppConstant.FRONT_END_BASE_URL + "resetPassword/" + forgetPasswordToken);
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_CHANGE_PASSWORD;
                }

            } else {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_USER;
            }
        } catch (Exception ex) {
            log.error("PasswordBllManager -> getPassword got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return forgetPasswordTokenModel;
    }
}

