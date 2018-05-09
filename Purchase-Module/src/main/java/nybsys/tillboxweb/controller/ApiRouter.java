/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 22-Dec-17
 * Time: 10:50 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */
package nybsys.tillboxweb.controller;


import nybsys.tillboxweb.BaseController;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.service.manager.*;
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
    private SupplierCategoryServiceManager supplierCategoryServiceManager;

    @Autowired
    private SupplierAddressTypeServiceManager supplierAddressTypeServiceManager;

    @Autowired
    private SupplierContactTypeServiceManager supplierContactTypeServiceManager;

    @Autowired
    private PurchaseOrderServiceManager purchaseOrderServiceManager;

    @Autowired
    private SupplierInvoiceServiceManager supplierInvoiceServiceManager;

    @Autowired
    private SupplierReturnServiceManager supplierReturnServiceManager;

    @Autowired
    private SupplierServiceManager supplierServiceManager;

    @Autowired
    private SupplierPaymentServiceManager supplierPaymentServiceManager;

    @Autowired
    private SupplierAdjustmentServiceManager supplierAdjustmentServiceManager;

    @Autowired
    private AllocatePaymentServiceManager allocatePaymentServiceManager;

    @Autowired
    private SupplierAdditionalCostServiceManager supplierAdditionalCostServiceManager;

    @Autowired
    private SupplierAdditionalCostSettingServiceManager supplierAdditionalCostSettingServiceManager;
    
    @Autowired
    private SupplierTypeServiceManager supplierTypeServiceManager;

    @Override
    public ResponseMessage getResponseMessage(String serviceName, RequestMessage requestMessage) {
        this.checkSecurityAndExecuteService(serviceName,requestMessage);
        //close session factory
        //this.closeSession();
        return this.responseMessage;
    }


    protected void executeServiceManager(String serviceName, RequestMessage requestMessage) {
        switch (serviceName) {

            case "api/purchase/allocatePayment/save":
                this.responseMessage = this.allocatePaymentServiceManager.saveOrUpdate(requestMessage);
                log.info("Purchase module -> api/purchase/allocatePayment/save executed");
                break;

            case "api/purchase/allocatePayment/search":
                this.responseMessage = this.allocatePaymentServiceManager.search(requestMessage);
                log.info("Purchase module -> api/purchase/allocatePayment/search executed");
                break;

            case "api/purchase/allocatePayment/getById":
                this.responseMessage = this.allocatePaymentServiceManager.getById(requestMessage);
                log.info("Purchase module -> api/purchase/allocatePayment/getById executed");
                break;

            case "api/purchase/allocatePayment/delete":
                this.responseMessage = this.allocatePaymentServiceManager.delete(requestMessage);
                log.info("Purchase module -> api/purchase/allocatePayment/delete executed");
                break;

            case "api/purchase/supplierPayment/save":
                this.responseMessage = this.supplierPaymentServiceManager.saveOrUpdate(requestMessage);
                log.info("Purchase module -> api/purchase/supplierPayment/save executed");
                break;

            case "api/purchase/supplierPayment/search":
                this.responseMessage = this.supplierPaymentServiceManager.search(requestMessage);
                log.info("Purchase module -> api/purchase/supplierPayment/search executed");
                break;

            case "api/purchase/supplierPayment/getById":
                this.responseMessage = this.supplierPaymentServiceManager.getById(requestMessage);
                log.info("Purchase module -> api/purchase/supplierPayment/getByID executed");
                break;

            case "api/purchase/supplierPayment/delete":
                this.responseMessage = this.supplierPaymentServiceManager.delete(requestMessage);
                log.info("Purchase module -> api/purchase/supplierPayment/delete executed");
                break;

            case "api/supplierCategory/save":
                this.responseMessage = this.supplierCategoryServiceManager.saveSupplierCategory(requestMessage);
                log.info("Purchase module -> api/SupplierCategory/save executed");
                break;

            case "api/supplierCategory/search":
                this.responseMessage = this.supplierCategoryServiceManager.searchSupplierCategory(requestMessage);
                log.info("Purchase module -> api/SupplierCategory/search executed");
                break;

            case "api/supplierCategory/getByID":
                this.responseMessage = this.supplierCategoryServiceManager.getSupplierCategoryByID(requestMessage);
                log.info("Purchase module -> api/SupplierCategory/getByID executed");
                break;

            case "api/purchaseModule/supplierAddressType/save":
                this.responseMessage = this.supplierAddressTypeServiceManager.saveAddressType(requestMessage);
                log.info("Purchase module -> api/purchaseModule/supplierAddressType/save executed");
                break;

            case "api/purchaseModule/supplierAddressType/search":
                this.responseMessage = this.supplierAddressTypeServiceManager.searchAddressType(requestMessage);
                log.info("Purchase module -> api/purchaseModule/supplierAddressType/search executed");
                break;

            case "api/purchaseModule/supplierAddressType/getByID":
                this.responseMessage = this.supplierAddressTypeServiceManager.getAddressTypeByID(requestMessage);
                log.info("Purchase module -> api/purchaseModule/supplierAddressType/getByID executed");
                break;

            case "api/purchaseModule/supplierContactType/save":
                this.responseMessage = this.supplierContactTypeServiceManager.saveContactType(requestMessage);
                log.info("Purchase module -> api/purchaseModule/supplierContactType/save executed");
                break;

            case "api/purchaseModule/supplierContactType/search":
                this.responseMessage = this.supplierContactTypeServiceManager.searchContactType(requestMessage);
                log.info("Purchase module -> api/purchaseModule/supplierContactType/search executed");
                break;

            case "api/purchaseModule/supplierContactType/getByID":
                this.responseMessage = this.supplierContactTypeServiceManager.getContactTypeByID(requestMessage);
                log.info("Purchase module -> api/purchaseModule/supplierContactType/getByID executed");
                break;

            case "api/supplier/save":
                this.responseMessage = this.supplierServiceManager.saveSupplierVM(requestMessage);
                log.info("Purchase module -> api/supplier/save executed");
                break;

            case "api/supplier/search":
                this.responseMessage = this.supplierServiceManager.searchSupplier(requestMessage);
                log.info("Purchase module -> api/supplier/search executed");
                break;

            case "api/supplier/searchVM":
                this.responseMessage = this.supplierServiceManager.searchSupplierVM(requestMessage);
                log.info("Purchase module -> api/supplier/searchVM executed");
                break;

            case "api/supplier/delete":
                this.responseMessage = this.supplierServiceManager.deleteSupplierVM(requestMessage);
                log.info("Purchase module -> api/supplier/delete executed");
                break;

            case "api/purchaseOrder/save":
                this.responseMessage = this.purchaseOrderServiceManager.save(requestMessage);
                log.info("Purchase module -> api/purchaseOrder/save executed");
                break;

            case "api/purchaseOrder/search":
                this.responseMessage = this.purchaseOrderServiceManager.search(requestMessage);
                log.info("Purchase module -> api/purchaseOrder/search executed");
                break;

            case "api/purchaseOrder/getByID":
                this.responseMessage = this.purchaseOrderServiceManager.getByID(requestMessage);
                log.info("Purchase module -> api/purchaseOrder/getByID executed");
                break;

            case "api/purchaseOrder/delete":
                this.responseMessage = this.purchaseOrderServiceManager.delete(requestMessage);
                log.info("Purchase module -> api/purchaseOrder/delete executed");
                break;

            case "api/supplierReturn/save":
                this.responseMessage = this.supplierReturnServiceManager.save(requestMessage);
                log.info("Purchase module -> api/purchaseOrder/save executed");
                break;

            case "api/supplierReturn/search":
                this.responseMessage = this.supplierReturnServiceManager.search(requestMessage);
                log.info("Purchase module -> api/supplierReturn/search executed");
                break;

            case "api/supplierReturn/getByID":
                this.responseMessage = this.supplierReturnServiceManager.getByID(requestMessage);
                log.info("Purchase module -> api/supplierReturn/getByID executed");
                break;

            case "api/supplierReturn/delete":
                this.responseMessage = this.supplierReturnServiceManager.delete(requestMessage);
                log.info("Purchase module -> api/supplierReturn/delete executed");
                break;

            case "api/supplierInvoice/save":
                this.responseMessage = this.supplierInvoiceServiceManager.save(requestMessage);
                log.info("Purchase module -> api/supplierInvoice/save executed");
                break;

            case "api/supplierInvoice/getStatement":
                this.responseMessage = this.supplierInvoiceServiceManager.supplierStatement(requestMessage);
                log.info("Purchase module -> api/supplierInvoice/save executed");
                break;

            case "api/supplierInvoice/getSupplierOutstanding":
                this.responseMessage = this.supplierInvoiceServiceManager.getSupplierOutstanding(requestMessage);
                log.info("Purchase module -> api/supplierInvoice/getSupplierOutstanding executed");
                break;

            case "api/supplierInvoice/search":
                this.responseMessage = this.supplierInvoiceServiceManager.search(requestMessage);
                log.info("Purchase module -> api/purchaseOrder/search executed");
                break;

            case "api/supplierInvoice/getByID":
                this.responseMessage = this.supplierInvoiceServiceManager.getByID(requestMessage);
                log.info("Purchase module -> api/supplierInvoice/getByID executed");
                break;

            case "api/supplierInvoice/delete":
                this.responseMessage = this.supplierInvoiceServiceManager.delete(requestMessage);
                log.info("Purchase module -> api/supplierInvoice/getByID executed");
                break;

            case "api/purchase/supplierAdjustment/save":
                this.responseMessage = this.supplierAdjustmentServiceManager.saveSupplierAdjustmentVM(requestMessage);
                log.info("Purchase module -> api/purchase/supplierAdjustment/save executed");
                break;

            case "api/purchase/supplierAdjustment/search":
                this.responseMessage = this.supplierAdjustmentServiceManager.searchSupplierAdjustmentVM(requestMessage);
                log.info("Purchase module -> api/purchase/supplierAdjustment/search executed");
                break;

            case "api/purchase/supplierAdjustment/getByID":
                this.responseMessage = this.supplierAdjustmentServiceManager.getSupplierAdjustmentVMByID(requestMessage);
                log.info("Purchase module -> api/purchase/supplierAdjustment/getByID executed");
                break;

            case "api/purchase/supplierAdjustment/delete":
                this.responseMessage = this.supplierAdjustmentServiceManager.deleteSupplierAdjustmentAndDetail(requestMessage);
                log.info("Purchase module -> api/purchase/supplierAdjustment/delete executed");
                break;

            case "api/purchase/supplierAdjustment/adjustOpeningBalance":
                this.responseMessage = this.supplierAdjustmentServiceManager.adjustOpeningBalance(requestMessage);
                log.info("Purchase module -> api/purchase/supplierAdjustment/adjustOpeningBalance executed");
                break;

            case "api/purchase/supplierAdjustment/getSupplierDueByInvoiceID":
                this.responseMessage = this.supplierAdjustmentServiceManager.getSupplierDueByInvoiceID(requestMessage);
                log.info("Purchase module -> api/purchase/supplierAdjustment/getSupplierDueByInvoiceID executed");
                break;

            case "api/purchase/supplierAdditionalCostSetting/save":
                this.responseMessage = this.supplierAdditionalCostSettingServiceManager.saveSupplierAdditionalCostSetting(requestMessage);
                log.info("Purchase module -> api/purchase/supplierAdditionalCostSetting/save executed");
                break;

            case "api/purchase/supplierAdditionalCostSetting/search":
                this.responseMessage = this.supplierAdditionalCostSettingServiceManager.searchSupplierAdditionalCostSetting(requestMessage);
                log.info("Purchase module -> api/purchase/supplierAdditionalCostSetting/search executed");
                break;

            case "api/purchase/supplierAdditionalCostSetting/delete":
                this.responseMessage = this.supplierAdditionalCostSettingServiceManager.deleteSupplierAdditionalCostSetting(requestMessage);
                log.info("Purchase module -> api/purchase/supplierAdditionalCostSetting/delete executed");
                break;

            case "api/purchase/supplierAdditionalCost/save":
                this.responseMessage = this.supplierAdditionalCostServiceManager.saveSupplierAdditionalCost(requestMessage);
                log.info("Purchase module -> api/purchase/supplierAdditionalCost/save executed");
                break;

            case "api/purchase/supplierAdditionalCost/search":
                this.responseMessage = this.supplierAdditionalCostServiceManager.searchSupplierAdditionalCost(requestMessage);
                log.info("Purchase module -> api/purchase/supplierAdditionalCost/search executed");
                break;

            case "api/purchase/supplierAdditionalCost/delete":
                this.responseMessage = this.supplierAdditionalCostServiceManager.deleteSupplierAdditionalCost(requestMessage);
                log.info("Purchase module -> api/purchase/supplierAdditionalCost/delete executed");
                break;

            case "api/purchase/supplierType/save":
                this.responseMessage = this.supplierTypeServiceManager.saveSupplierType(requestMessage);
                log.info("Purchase module -> api/purchase/supplierType/save executed");
                break;

            case "api/purchase/supplierType/search":
                this.responseMessage = this.supplierTypeServiceManager.searchSupplierType(requestMessage);
                log.info("Purchase module -> api/purchase/supplierType/search executed");
                break;

            case "api/purchase/supplierType/getByID":
                this.responseMessage = this.supplierTypeServiceManager.getSupplierTypeByID(requestMessage);
                log.info("Purchase module -> api/purchase/supplierType/getByID executed");
                break;

            case "api/purchase/supplierType/delete":
                this.responseMessage = this.supplierTypeServiceManager.deleteSupplierType(requestMessage);
                log.info("Purchase module -> api/purchase/supplierType/delete executed");
                break;

            default:
                log.warn("INVALID REQUEST");
        }
        //return this.responseMessage;
    }

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
