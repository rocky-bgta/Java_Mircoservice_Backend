/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 09-March-17
 * Time: 10:50 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */
package nybsys.tillboxweb.controller;


import nybsys.tillboxweb.BaseController;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.service.manager.ExpenseAdjustmentServiceManager;
import nybsys.tillboxweb.service.manager.ExpenseServiceManager;
import nybsys.tillboxweb.service.manager.ExpenseTypeServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ApiRouter extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(ApiRouter.class);

    @Autowired
    private ExpenseTypeServiceManager expenseTypeServiceManager;

    @Autowired
    private ExpenseServiceManager expenseServiceManager;

    @Autowired
    private ExpenseAdjustmentServiceManager expenseAdjustmentServiceManager;

    @Override
    public ResponseMessage getResponseMessage(String serviceName, RequestMessage requestMessage) {
        this.checkSecurityAndExecuteService(serviceName,requestMessage);
        //close session factory
        //this.closeSession();
        return this.responseMessage;
    }

    protected void executeServiceManager(String serviceName, RequestMessage requestMessage) {
        switch (serviceName) {
            
            case "api/expense/expenseType/save":
                this.responseMessage = this.expenseTypeServiceManager.save(requestMessage);
                log.info("Expense module -> api/expense/expenseType/save executed");
                break;
                
            case "api/expense/expenseType/search":
                this.responseMessage = this.expenseTypeServiceManager.search(requestMessage);
                log.info("Expense module -> api/expense/expenseType/search executed");
                break;
                
            case "api/expense/expenseType/delete":
                this.responseMessage = this.expenseTypeServiceManager.delete(requestMessage);
                log.info("Expense module -> api/expense/expenseType/delete executed");
                break;

            case "api/expense/expense/save":
                this.responseMessage = this.expenseServiceManager.save(requestMessage);
                log.info("Expense module -> api/expense/expense/save executed");
                break;

            case "api/expense/expense/search":
                this.responseMessage = this.expenseServiceManager.search(requestMessage);
                log.info("Expense module -> api/expense/expense/search executed");
                break;

            case "api/expense/expense/delete":
                this.responseMessage = this.expenseServiceManager.delete(requestMessage);
                log.info("Expense module -> api/expense/expense/delete executed");
                break;

            case "api/expense/expenseCategories/get":
                this.responseMessage = this.expenseServiceManager.expenseCategories(requestMessage);
                log.info("Expense module -> api/expense/expenseCategories executed");
                break;

            case "api/expense/expenseAdjustment/save":
                this.responseMessage = this.expenseAdjustmentServiceManager.saveExpenseAdjustmentVM(requestMessage);
                log.info("Expense module -> api/expense/expenseAdjustment/save executed");
                break;

            case "api/expense/expenseAdjustment/search":
                this.responseMessage = this.expenseAdjustmentServiceManager.searchExpenseAdjustmentVM(requestMessage);
                log.info("Expense module -> api/expense/expenseAdjustment/search executed");
                break;

            case "api/expense/expenseAdjustment/getByID":
                this.responseMessage = this.expenseAdjustmentServiceManager.getExpenseAdjustmentVMByID(requestMessage);
                log.info("Expense module -> api/expense/expenseAdjustment/getByID executed");
                break;

            case "api/expense/expenseAdjustment/delete":
                this.responseMessage = this.expenseAdjustmentServiceManager.deleteExpenseAdjustmentAndDetail(requestMessage);
                log.info("Expense module -> api/expense/expenseAdjustment/delete executed");
                break;

            default:
                log.warn("INVALID REQUEST");
        }
        //return this.responseMessage;
    }

    //TODO: implement security check

/*

    private SecurityResMessage checkSecurity(RequestMessage requestMessage) {
        //Boolean isPermitted = false;
        String topic = BrokerMessageTopic.SECURITY_TOPIC;
        SecurityResMessage securityResMessage=null;
        SecurityReqMessage securityReqMessage = this.getDefaultSecurityMessage();
        securityReqMessage.token = requestMessage.token;
        Core.securityMessageId.set(securityReqMessage.messageId);
        securityReqMessage.serviceUrl = requestMessage.brokerMessage.serviceName;
        //securityReqMessage. = requestMessage.businessID;

        Object lockObject = new Object();
        MqttClient mqttClient = BrokerClient.mqttClient;
        CallBackForSecurity callBackForSecurity = new CallBackForSecurity(lockObject);
        PublisherForSecurity publisherForSecurity;
        mqttClient.setCallback(callBackForSecurity);

        if(mqttClient.isConnected()){
            try {
                // Subscription
                mqttClient.subscribe(topic, BrokerConstant.oneQoS);

                publisherForSecurity = new PublisherForSecurity();
                synchronized (lockObject){
                    publisherForSecurity.publishedMessage(topic,securityReqMessage);
                    //lockObject.wait();
                    securityResMessage = callBackForSecurity.getSecurityResMessage();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                log.error("Error while security check: " + ex.getMessage());
            }
        }
        return securityResMessage;
    }
*/

}
