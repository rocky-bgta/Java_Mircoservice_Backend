package nybsys.tillboxweb;


import nybsys.tillboxweb.MessageModel.*;
import nybsys.tillboxweb.Utils.TillBoxUtils;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.constant.BrokerMessageTopic;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public abstract class BaseController extends Core {
    private static final Logger log = LoggerFactory.getLogger(BaseController.class);

    public abstract ResponseMessage getResponseMessage(String serviceName, RequestMessage requestMessage);

    //@Async
    protected abstract void executeServiceManager(String serviceName, RequestMessage requestMessage);



    protected ResponseMessage responseMessage;
    protected SecurityResMessage securityResMessage;
    protected String dataBaseName;
    protected String token;

    protected void setDefaultBusinessValue(RequestMessage requestMessage) {

        ClientMessage clientMessage = new ClientMessage();

        Core.requestToken.remove();
        Core.requestToken.set(requestMessage.token);

        Core.messageId.remove();
        Core.messageId.set(requestMessage.brokerMessage.messageId);

        Core.userId.remove();
        Core.userId.set(requestMessage.userID);

        Core.businessId.remove();
        Core.businessId.set(requestMessage.businessID);

        Core.clientMessage.remove();
        Core.clientMessage.set(clientMessage);

        Core.baseCurrencyID.remove();
        Core.baseCurrencyID.set(requestMessage.baseCurrencyID);

        Core.entryCurrencyID.remove();
        Core.entryCurrencyID.set(requestMessage.entryCurrencyID);

        Core.exchangeRate.remove();
        Core.exchangeRate.set(requestMessage.exchangeRate);

    }


    public SecurityReqMessage getDefaultSecurityMessage() {
        SecurityReqMessage securityReqMessage = new SecurityReqMessage();
        securityReqMessage.messageId = TillBoxUtils.getUUID();
        return securityReqMessage;
    }

    protected SecurityResMessage checkSecurity(RequestMessage requestMessage) {
        //Boolean isPermitted = false;
        String securityRequestTopic = BrokerMessageTopic.SECURITY_REQUEST_TOPIC;
        //String securityResponseTopic = BrokerMessageTopic.SECURITY_RESPONSE_TOPIC;
        String securityResponseTopic = requestMessage.brokerMessage.messageId + "security";

        System.out.println("securityResponseTopic: " +securityResponseTopic);

        SecurityResMessage securityResMessage = null;
        SecurityReqMessage securityReqMessage = this.getDefaultSecurityMessage();
        securityReqMessage.token = requestMessage.token;
        //Core.securityMessageId.set(securityReqMessage.messageId);

        /*Core.messageIdListForSecqurity.add(securityReqMessage.messageId);
        log.info(""+Core.messageIdListForSecqurity.size());*/

        securityReqMessage.serviceUrl = requestMessage.brokerMessage.serviceName;
        securityReqMessage.messageId = requestMessage.brokerMessage.messageId;

        //securityReqMessage. = requestMessage.businessID;

        Object lockObject = new Object();



        return securityResMessage;
    }

    protected void setSecurityForApi(RequestMessage requestMessage, SecurityResMessage securityResMessage) {
        this.dataBaseName = securityResMessage.businessDBName;
        Core.userDataBase.set(this.dataBaseName);

        Core.userDataBase.set(this.dataBaseName);
        this.token = securityResMessage.token;

        Core.businessId.set(securityResMessage.businessID);
        Core.userId.set(requestMessage.userID);

        if (securityResMessage.isDefaultDB) {
            this.setDefaultDateBase();
        } else {
            this.selectDataBase(this.dataBaseName);
        }
    }

    protected void checkSecurityAndExecuteService(String serviceName, RequestMessage requestMessage) {
        this.securityResMessage = this.checkSecurity(requestMessage);
        this.responseMessage = this.buildDefaultResponseMessage();

        try {
            if (this.securityResMessage!=null && this.securityResMessage.isPermitted) {

                this.setSecurityForApi(requestMessage, this.securityResMessage);

                this.setDefaultBusinessValue(requestMessage);



                //Update request message after security pass
                requestMessage.businessID = this.securityResMessage.businessID;
                requestMessage.userID = this.securityResMessage.userID;
                requestMessage.entryCurrencyID = this.securityResMessage.currentCurrencyID;
                //==========================================

                this.responseMessage.token = this.token;
                this.executeServiceManager(serviceName, requestMessage);
                //this.closeSession();


                this.responseMessage.businessID = Core.businessId.get();

                //give error message on development mode
                this.responseMessage.errorMessage = Core.clientMessage.get().message;
                //give only user message to message field for inter module communication
                if (requestMessage.brokerMessage.requestFrom == TillBoxAppEnum.BrokerRequestType.WORKER.get()) {
                    this.responseMessage.message = Core.clientMessage.get().userMessage;
                }
            } else {
                this.responseMessage.responseCode = TillBoxAppConstant.UNAUTHORIZED_CODE;
                this.responseMessage.message = TillBoxAppConstant.UNAUTHORIZED_USER;
                this.responseMessage.responseObj = null;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
    protected void closeSession(){
        SessionFactory sessionFactory;
        sessionFactory = Core.sessionFactoryThreadLocal.get();
        if (sessionFactory != null) {
            sessionFactory.close();
            log.warn("session Closed");
        }
    }
}
