package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.TillBoxWebEntities.Session;
import nybsys.tillboxweb.TillBoxWebModels.SessionModel;
import nybsys.tillboxweb.Utils.TillBoxUtils;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SessionBllManager extends BaseBll<Session> {

    private static final Logger log = LoggerFactory.getLogger(SessionBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(Session.class);
        Core.runTimeModelType.set(SessionModel.class);
    }

    public String saveSession(String userID, Integer businessID, String businessDbName) throws Exception {
        String sessionToken = TillBoxUtils.getUUID();

        SessionModel whereCondition = new SessionModel();
        whereCondition.setUserID(userID);

        List<SessionModel> sessionListOfThisUser = new ArrayList<SessionModel>();
        try {
            sessionListOfThisUser = this.getAllByConditions(whereCondition);
            // session is already exists for this user
            if(sessionListOfThisUser.size() > 0 )
            {
                SessionModel updateModel = new SessionModel();
                updateModel = sessionListOfThisUser.get(0);
                updateModel.setBusinessDBName(businessDbName);
                updateModel.setBusinessID(businessID);
                updateModel.setStart(new Date());
                updateModel.setEnd(new Date());
                updateModel.setDuration(1200);
                updateModel.setLoginStatus(1);
                updateModel.setUpdatedBy(userID);
                updateModel.setUpdatedDate(new Date());
                updateModel.setToken(sessionToken);
                updateModel.setRefreshToken(sessionToken);
                updateModel = this.update(updateModel);
                if(updateModel != null)
                {
                    sessionToken = updateModel.getToken();
                }

            }else
            {
                SessionModel sessionModel = new SessionModel();
                sessionModel.setUserID(userID);
                sessionModel.setBusinessDBName(businessDbName);
                sessionModel.setBusinessID(businessID);
                sessionModel.setStart(new Date());
                sessionModel.setEnd(new Date());
                sessionModel.setDuration(1200);
                sessionModel.setLoginStatus(1);
                sessionModel.setCreatedBy(userID);
                sessionModel.setCreatedDate(new Date());
                sessionModel.setToken(sessionToken);
                sessionModel.setRefreshToken(sessionToken);
                sessionModel = this.save(sessionModel);
                if(sessionModel != null)
                {
                    sessionToken = sessionModel.getToken();
                }
            }

        } catch (Exception ex) {
            log.error("SessionBllManager -> saveSession got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return sessionToken;
    }

    public  SessionModel saveOrUpdate(SessionModel sessionModelReq) throws Exception {
        SessionModel sessionModel = new SessionModel();
        try {
           sessionModel = sessionModelReq;
           
           //save
           if(sessionModel.getSessionID() == null || sessionModel.getSessionID() == 0) {
               sessionModel = this.save(sessionModel);
               if (sessionModel == null) {
                   Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                   Core.clientMessage.get().message = MessageConstant.SESSION_SAVE_FAILED;
               }


           }else {
               sessionModel = this.update(sessionModel);
               if (sessionModel == null) {
                   Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                   Core.clientMessage.get().message = MessageConstant.SESSION_UPDATE_FAILED;
               }

           }
        }catch (Exception ex) {
            log.error("SessionBllManager -> saveOrUpdate got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return sessionModel;
    }

    public  SessionModel changeBusinessAndDb(String userID, Integer businessID,String businessDbName) throws Exception {
        SessionModel sessionModel = new SessionModel();
        List<SessionModel>  lstSessionModel = new ArrayList<>();
        try {
            sessionModel.setUserID(userID);
            lstSessionModel = this.getAllByConditions(sessionModel);
            if(lstSessionModel.size() > 0)
            {
                sessionModel = lstSessionModel.get(0);
                sessionModel.setBusinessID(businessID);
                sessionModel.setBusinessDBName(businessDbName);

                sessionModel = this.update(sessionModel);
                if (sessionModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.SESSION_UPDATE_FAILED;
                }
            }else{
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.SESSION_GET_FAILED;
            }

        }catch (Exception ex) {
            log.error("SessionBllManager -> saveOrUpdate got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return sessionModel;
    }

    public  boolean changeCurrentCurrency(String userID, Integer businessID,Integer currencyID) throws Exception {
        SessionModel sessionModel = new SessionModel();
        List<SessionModel>  lstSessionModel = new ArrayList<>();
        try {
            sessionModel.setUserID(userID);
            sessionModel.setBusinessID(businessID);

            lstSessionModel = this.getAllByConditions(sessionModel);
            if(lstSessionModel.size() > 0)
            {
                sessionModel = lstSessionModel.get(0);
                sessionModel.setCurrentCurrencyID(currencyID);

                sessionModel = this.update(sessionModel);
                if (sessionModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.SESSION_UPDATE_FAILED;
                }else {
                    return true;
                }
            }else{
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.SESSION_GET_FAILED;
            }

        }catch (Exception ex) {
            log.error("SessionBllManager -> saveOrUpdate got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return false;
    }
}
