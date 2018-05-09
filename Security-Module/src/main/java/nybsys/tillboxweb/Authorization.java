/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 07-Feb-18
 * Time: 4:26 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb;


import nybsys.tillboxweb.MessageModel.SecurityReqMessage;
import nybsys.tillboxweb.MessageModel.SecurityResMessage;
import nybsys.tillboxweb.TillBoxWebModels.SessionModel;
import nybsys.tillboxweb.bll.manager.SessionBllManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

import static nybsys.tillboxweb.PublicApiList.PUBLIC_API_LIST;
import static nybsys.tillboxweb.CommonApiList.COMMON_API_LIST;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Authorization extends Core{

    private final Logger log = LoggerFactory.getLogger(Authorization.class);

    @Autowired
    private SessionBllManager sessionBllManager;

    public SecurityResMessage getUsersPermission(SecurityReqMessage securityReqMessage) {

        Boolean isPublicApi,isCommonApi;

        SecurityResMessage securityResMessage = null;
        //isCommonApi = this.isCommonApi(securityReqMessage.serviceUrl);
        //isPublicApi = this.isPublicApi(securityReqMessage.serviceUrl);

        isCommonApi = this.findApiInList(securityReqMessage.serviceUrl, COMMON_API_LIST);
        isPublicApi = this.findApiInList(securityReqMessage.serviceUrl, PUBLIC_API_LIST);

        try {
            if(isPublicApi){
                // do not consider about token
                securityResMessage = new SecurityResMessage();
                securityResMessage.messageId = securityReqMessage.messageId;
                securityResMessage.isPermitted = true;
                //securityResMessage.businessDBName = TillBoxDbConstant.DEFAULT_DATABASE;
                securityResMessage.isDefaultDB=true;
            }else {
                //private url or common url
                securityResMessage = this.getSecurityResponseMessage(securityReqMessage);
                if(isCommonApi){
                    securityResMessage.isDefaultDB=true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("SecurityCheckThread: "+ e.getMessage());
            //throw e;
        }
        return securityResMessage;
    }


    private SecurityResMessage getSecurityResponseMessage(SecurityReqMessage securityReqMessage) throws Exception {
        //Boolean isPermitted = false;
        SessionModel whereCondition, sessionModel;// = new SessionModel();
        SecurityResMessage securityResMessage = new SecurityResMessage();
        List<SessionModel> sessionModelList;

        securityResMessage.isPermitted = false;
        securityResMessage.messageId = securityReqMessage.messageId;

        if (securityReqMessage.token != null) {
            whereCondition = new SessionModel();
            whereCondition.setToken(securityReqMessage.token);

            try {
                this.setDefaultDateBase();
                sessionModelList = this.sessionBllManager.getAllByConditions(whereCondition);
                if (sessionModelList.size() > 0) {
                    sessionModel = sessionModelList.get(0);
                    securityResMessage.token = sessionModel.getToken();
                    securityResMessage.businessID = sessionModel.getBusinessID();
                    securityResMessage.businessDBName = sessionModel.getBusinessDBName();
                    securityResMessage.isDefaultDB=false;
                    securityResMessage.userID = sessionModel.getUserID();
                    securityResMessage.isPermitted = true;
                    securityResMessage.currentCurrencyID = sessionModel.getCurrentCurrencyID();
                }

            } catch (Exception e) {
                e.printStackTrace();
                log.error("SecurityCheckThread: "+ e.getMessage());
            }
        }//else {
            //throw new Exception("Please provide valid token");
        //}
        return securityResMessage;
    }
/*
    private Boolean isCommonApi(String serviceUrl) {
        Boolean result = false;
        for (String api : COMMON_API_LIST) {
            if (StringUtils.equals(api, serviceUrl)) {
                result = true;
                break;
            }
        }
        return result;
    }

    private Boolean isPublicApi(String serviceUrl) {
        Boolean result = false;
        for (String api : PUBLIC_API_LIST) {
            if (StringUtils.equals(api, serviceUrl)) {
                result = true;
                break;
            }
        }
        return result;
    }*/

    private Boolean findApiInList(String serviceUrl, List<String> apiList){
        Boolean result = false;
        for (String api : apiList) {
            if (StringUtils.equals(api, serviceUrl)) {
                result = true;
                break;
            }
        }
        return result;
    }
}

