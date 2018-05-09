/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 18/04/2018
 * Time: 10:54
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.TillBoxWebEntities.RegistrationInvitation;
import nybsys.tillboxweb.TillBoxWebModels.RegistrationInvitationModel;
import nybsys.tillboxweb.Utils.TillBoxUtils;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static nybsys.tillboxweb.constant.TillBoxAppConstant.FRONT_END_BASE_URL;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class RegistrationInvitationBllManager extends BaseBll<RegistrationInvitation> {

    private static final Logger log = LoggerFactory.getLogger(RegistrationInvitationBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(RegistrationInvitation.class);
        Core.runTimeModelType.set(RegistrationInvitationModel.class);
    }

    public RegistrationInvitationModel saveRegistrationInvitation(RegistrationInvitationModel registrationInvitationModelReq) throws Exception {
        RegistrationInvitationModel registrationInvitationModel;
        try {
            registrationInvitationModel = this.getRegistrationInvitationByUser(registrationInvitationModelReq.getUserID());
            Core.clientMessage.get().messageCode = null;

            // invitation already exists for this user
            if (registrationInvitationModel != null) {
                registrationInvitationModel = this.softDelete(registrationInvitationModel);
                if (registrationInvitationModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.REGISTRATION_INVITATION_DELETE_FAILED;
                    return registrationInvitationModel;
                }
            }

            //save
            Date currentDate = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentDate);
            cal.add(Calendar.DATE, 3);
            String registerToken = TillBoxUtils.getUUID();
            registrationInvitationModelReq.setExpireDate(cal.getTime());
            registrationInvitationModelReq.setDone(false);
            registrationInvitationModelReq.setToken(registerToken);
            registrationInvitationModel = this.save(registrationInvitationModelReq);
            if (registrationInvitationModel == null) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.REGISTRATION_INVITATION_SAVE_FAILED;
            } else {
                TillBoxUtils.sendEmail(registrationInvitationModelReq.getUserID(), "send registration invitation", FRONT_END_BASE_URL + "registration-welcome/" + registerToken);
            }


        } catch (Exception ex) {
            log.error("RegistrationInvitationBllManager -> saveRegistrationInvitation got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return registrationInvitationModel;
    }

    public RegistrationInvitationModel getRegistrationInvitationByUser(String userID) throws Exception {
        RegistrationInvitationModel registrationInvitationModel = new RegistrationInvitationModel();
        List<RegistrationInvitationModel> lstRegistrationInvitationModel;
        try {
            registrationInvitationModel.setUserID(userID);
            registrationInvitationModel.setStatus(TillBoxAppEnum.Status.Active.get());

            lstRegistrationInvitationModel = this.getAllByConditions(registrationInvitationModel);
            if (lstRegistrationInvitationModel.size() > 0) {
                registrationInvitationModel = lstRegistrationInvitationModel.get(0);
            } else {
                registrationInvitationModel = null;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.REGISTRATION_INVITATION_GET_FAILED;
            }

        } catch (Exception ex) {
            log.error("RegistrationInvitationBllManager -> getRegistrationInvitationByUser got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return registrationInvitationModel;
    }

    public RegistrationInvitationModel getRegistrationInvitationByToken(String token) throws Exception {
        RegistrationInvitationModel registrationInvitationModel = new RegistrationInvitationModel();
        List<RegistrationInvitationModel> lstRegistrationInvitationModel;
        try {
            registrationInvitationModel.setToken(token);
            registrationInvitationModel.setStatus(TillBoxAppEnum.Status.Active.get());

            lstRegistrationInvitationModel = this.getAllByConditions(registrationInvitationModel);
            if (lstRegistrationInvitationModel.size() > 0) {
                registrationInvitationModel = lstRegistrationInvitationModel.get(0);
            } else {
                registrationInvitationModel = null;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.REGISTRATION_INVITATION_GET_FAILED;
            }

        } catch (Exception ex) {
            log.error("RegistrationInvitationBllManager -> getRegistrationInvitationByToken got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return registrationInvitationModel;
    }

    public RegistrationInvitationModel removeRegistrationInvitation(RegistrationInvitationModel registrationInvitationModelReq) throws Exception {
        RegistrationInvitationModel registrationInvitationModel;
        try {
            registrationInvitationModel = registrationInvitationModelReq;
            registrationInvitationModel.setDone(true);

            registrationInvitationModel = this.update(registrationInvitationModel);
            if (registrationInvitationModel == null) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.REGISTRATION_INVITATION_DELETE_FAILED;
            }else{
                Core.clientMessage.get().messageCode = null;
            }

        } catch (Exception ex) {
            log.error("RegistrationInvitationBllManager -> removeRegistrationInvitation got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return registrationInvitationModel;
    }

}
