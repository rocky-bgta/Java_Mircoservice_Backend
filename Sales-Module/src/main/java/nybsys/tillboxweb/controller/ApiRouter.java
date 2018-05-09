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
    private CustomerAddressTypeServiceManager customerAddressTypeServiceManager;

    @Autowired
    private CustomerContactTypeServiceManager customerContactTypeServiceManager;

    @Autowired
    private CustomerCategoryServiceManager customerCategoryServiceManager;

    @Autowired
    private SalesRepresentativeServiceManager salesRepresentativeServiceManager;

    @Autowired
    private CustomerServiceManager customerServiceManager;

    @Autowired
    private CustomerReceiveServiceManager customerReceiveServiceManager;

    @Autowired
    private CustomerWriteOffServiceManager customerWriteOffServiceManager;

    @Autowired
    private CustomerAdjustmentServiceManager customerAdjustmentServiceManager;

    @Autowired
    private DiscountGiveMoneyServiceManager discountGiveMoneyServiceManager;

    @Autowired
    private DiscountGiveProductServiceManager discountGiveProductServiceManager;

    @Autowired
    private CustomerDiscountFlatServiceManager customerDiscountFlatServiceManager;

    @Autowired
    private CustomerDiscountRangeServiceManager customerDiscountRangeServiceManager;

    @Autowired
    private DiscountSettingServiceManager discountSettingServiceManager;

    @Autowired
    private CustomerQuotationServiceManager customerQuotationServiceManager;

    @Autowired
    private CustomerInvoiceServiceManager customerInvoiceServiceManager;

    @Autowired
    private  CustomerTypeServiceManager customerTypeServiceManager;

    @Autowired
    private AllocateReceiveServiceManager allocateReceiveServiceManager;
    @Autowired
    private CustomerReturnServiceManager customerReturnServiceManager;

    @Override
    public ResponseMessage getResponseMessage(String serviceName, RequestMessage requestMessage) {
        this.checkSecurityAndExecuteService(serviceName,requestMessage);
        //close session factory
        //this.closeSession();
        return this.responseMessage;
    }


    protected void executeServiceManager(String serviceName, RequestMessage requestMessage) {
        switch (serviceName) {

            case "api/sales/allocateReceive/save":
                this.responseMessage = this.allocateReceiveServiceManager.saveOrUpdate(requestMessage);
                log.info("Sales Module -> api/salesModule/allocateReceive/save executed");
                break;

            case "api/sales/allocateReceive/search":
                this.responseMessage = this.allocateReceiveServiceManager.search(requestMessage);
                log.info("Sales Module -> api/salesModule/allocateReceive/search executed");
                break;

            case "api/sales/allocateReceive/getById":
                this.responseMessage = this.allocateReceiveServiceManager.getById(requestMessage);
                log.info("Sales Module -> api/salesModule/allocateReceive/getById executed");
                break;

            case "api/sales/allocateReceive/delete":
                this.responseMessage = this.allocateReceiveServiceManager.delete(requestMessage);
                log.info("Sales Module -> api/salesModule/allocateReceive/delete executed");
                break;

            case "api/sales/customerReceive/save":
                this.responseMessage = this.customerReceiveServiceManager.saveOrUpdate(requestMessage);
                log.info("Sales Module -> api/salesModule/customerReceive/save executed");
                break;

            case "api/sales/customerReceive/search":
                this.responseMessage = this.customerReceiveServiceManager.search(requestMessage);
                log.info("Sales Module -> api/salesModule/customerReceive/search executed");
                break;

            case "api/sales/customerReceive/getById":
                this.responseMessage = this.customerReceiveServiceManager.getById(requestMessage);
                log.info("Sales Module -> api/salesModule/customerReceive/getById executed");
                break;

            case "api/sales/customerReceive/delete":
                this.responseMessage = this.customerReceiveServiceManager.delete(requestMessage);
                log.info("Sales Module -> api/salesModule/customerReceive/delete executed");
                break;

            case "api/sales/customerAddressType/save":
                this.responseMessage = this.customerAddressTypeServiceManager.saveAddressType(requestMessage);
                log.info("Sales Module -> api/sales/customerAddressType/save executed");
                break;

            case "api/sales/customerAddressType/search":
                this.responseMessage = this.customerAddressTypeServiceManager.searchAddressType(requestMessage);
                log.info("Sales Module -> api/sales/customerAddressType/search executed");
                break;

            case "api/sales/customerAddressType/getByID":
                this.responseMessage = this.customerAddressTypeServiceManager.getAddressTypeByID(requestMessage);
                log.info("Sales Module -> api/sales/customerAddressType/getByID executed");
                break;
            case "api/sales/customerContactType/save":
                this.responseMessage = this.customerContactTypeServiceManager.saveContactType(requestMessage);
                log.info("Sales Module -> api/sales/customerContactType/save executed");
                break;

            case "api/sales/customerContactType/search":
                this.responseMessage = this.customerContactTypeServiceManager.searchContactType(requestMessage);
                log.info("Sales Module -> api/sales/customerContactType/search executed");
                break;

            case "api/sales/customerContactType/getByID":
                this.responseMessage = this.customerContactTypeServiceManager.getContactTypeByID(requestMessage);
                log.info("Sales Module -> api/sales/customerContactType/getByID executed");
                break;

            case "api/sales/customerCategory/save":
                this.responseMessage = this.customerCategoryServiceManager.saveCustomerCategory(requestMessage);
                log.info("Sales Module -> api/customerCategory/save executed");
                break;

            case "api/sales/customerCategory/search":
                this.responseMessage = this.customerCategoryServiceManager.searchCustomerCategory(requestMessage);
                log.info("Sales  -> api/customerCategory/search executed");
                break;

            case "api/sales/customerCategory/getByID":
                this.responseMessage = this.customerCategoryServiceManager.getCustomerCategoryByID(requestMessage);
                log.info("Sales Module -> api/sales/customerCategory/getByID executed");
                break;

            case "api/sales/salesRepresentative/save":
                this.responseMessage = this.salesRepresentativeServiceManager.saveSalesRepresentative(requestMessage);
                log.info("Sales Module -> api/sales/Representative/save executed");
                break;

            case "api/sales/salesRepresentative/search":
                this.responseMessage = this.salesRepresentativeServiceManager.searchSalesRepresentative(requestMessage);
                log.info("Sales Module -> api/sales/Representative/search executed");
                break;

            case "api/sales/salesRepresentative/getByID":
                this.responseMessage = this.salesRepresentativeServiceManager.getSalesRepresentativeByID(requestMessage);
                log.info("Sales Module -> api/sales/Representative/getByID executed");
                break;

            case "api/sales/customer/save":
                this.responseMessage = this.customerServiceManager.saveCustomerVM(requestMessage);
                log.info("Sales Module -> api/sales/customer/save executed");
                break;

            case "api/sales/customer/search":
                this.responseMessage = this.customerServiceManager.searchCustomer(requestMessage);
                log.info("Sales Module -> api/sales/customer/search executed");
                break;

            case "api/sales/customer/searchVM":
                this.responseMessage = this.customerServiceManager.searchCustomerVM(requestMessage);
                log.info("Sales Module -> api/sales/customer/searchVM executed");
                break;
            case "api/sales/customer/delete":
                this.responseMessage = this.customerServiceManager.deleteCustomerVM(requestMessage);
                log.info("Sales Module -> api/sales/customer/delete executed");
                break;

            case "api/sales/customerAdjustment/save":
                this.responseMessage = this.customerAdjustmentServiceManager.saveCustomerAdjustmentVM(requestMessage);
                log.info("Sales module -> api/sales/customerAdjustment/save executed");
                break;

            case "api/sales/customerAdjustment/search":
                this.responseMessage = this.customerAdjustmentServiceManager.searchCustomerAdjustmentVM(requestMessage);
                log.info("Sales module -> api/sales/customerAdjustment/search executed");
                break;

            case "api/sales/customerAdjustment/getByID":
                this.responseMessage = this.customerAdjustmentServiceManager.getCustomerAdjustmentVMByID(requestMessage);
                log.info("Sales module -> api/sales/customerAdjustment/getByID executed");
                break;

            case "api/sales/customerAdjustment/delete":
                this.responseMessage = this.customerAdjustmentServiceManager.deleteCustomerAdjustmentAndDetail(requestMessage);
                log.info("Sales module -> api/sales/customerAdjustment/delete executed");
                break;

            case "api/sales/customerAdjustment/adjustOpeningBalance":
                this.responseMessage = this.customerAdjustmentServiceManager.adjustOpeningBalance(requestMessage);
                log.info("Sales module -> api/sales/customerAdjustment/adjustOpeningBalance executed");
                break;

            case "api/sales/customerAdjustment/getCustomerDueByInvoiceID":
                this.responseMessage = this.customerAdjustmentServiceManager.getCustomerDueByInvoiceID(requestMessage);
                log.info("Sales module -> api/sales/customerAdjustment/getCustomerDueByInvoiceID executed");
                break;

            case "api/sales/customerWriteOff/save":
                this.responseMessage = this.customerWriteOffServiceManager.saveCustomerWriteOffVM(requestMessage);
                log.info("Sales module -> api/sales/customerWriteOff/save executed");
                break;

            case "api/sales/customerWriteOff/search":
                this.responseMessage = this.customerWriteOffServiceManager.searchCustomerWriteOffVM(requestMessage);
                log.info("Sales module -> api/sales/customerWriteOff/search executed");
                break;

            case "api/sales/customerWriteOff/getByID":
                this.responseMessage = this.customerWriteOffServiceManager.getCustomerWriteOffVMByID(requestMessage);
                log.info("Sales module -> api/sales/customerWriteOff/getByID executed");
                break;

            case "api/sales/customerWriteOff/delete":
                this.responseMessage = this.customerWriteOffServiceManager.deleteCustomerWriteOffAndDetail(requestMessage);
                log.info("Sales module -> api/sales/customerWriteOff/delete executed");
                break;

            case "api/sales/discountGiveProduct/save":
                this.responseMessage = this.discountGiveProductServiceManager.saveDiscountGiveProduct(requestMessage);
                log.info("Sales module -> api/sales/discountGiveProduct/save executed");
                break;

            case "api/sales/discountGiveProduct/search":
                this.responseMessage = this.discountGiveProductServiceManager.searchDiscountGiveProduct(requestMessage);
                log.info("Sales module -> api/sales/discountGiveProduct/search executed");

            case "api/sales/discountGiveProduct/delete":
                this.responseMessage = this.discountGiveProductServiceManager.deleteDiscountGiveProduct(requestMessage);
                log.info("Sales module -> api/sales/discountGiveProduct/delete executed");
                break;

            case "api/sales/discountGiveMoney/save":
                this.responseMessage = this.discountGiveMoneyServiceManager.saveDiscountGiveMoney(requestMessage);
                log.info("Sales module -> api/sales/discountGiveMoney/save executed");
                break;

            case "api/sales/discountGiveMoney/search":
                this.responseMessage = this.discountGiveMoneyServiceManager.searchDiscountGiveMoney(requestMessage);
                log.info("Sales module -> api/sales/discountGiveMoney/search executed");

            case "api/sales/discountGiveMoney/delete":
                this.responseMessage = this.discountGiveMoneyServiceManager.deleteDiscountGiveMoney(requestMessage);
                log.info("Sales module -> api/sales/discountGiveMoney/delete executed");
                break;

            case "api/sales/discountSetting/save":
                this.responseMessage = this.discountSettingServiceManager.saveDiscountSetting(requestMessage);
                log.info("Sales module -> api/sales/discountSetting/save executed");
                break;

            case "api/sales/discountSetting/search":
                this.responseMessage = this.discountSettingServiceManager.searchDiscountSetting(requestMessage);
                log.info("Sales module -> api/sales/discountSetting/search executed");
                break;

            case "api/sales/discountSetting/delete":
                this.responseMessage = this.discountSettingServiceManager.deleteDiscountSetting(requestMessage);
                log.info("Sales module -> api/sales/discountSetting/delete executed");
                break;

            case "api/sales/customerDiscountFlat/save":
                this.responseMessage = this.customerDiscountFlatServiceManager.saveCustomerDiscountFlat(requestMessage);
                log.info("Sales module -> api/sales/customerDiscountFlat/save executed");
                break;

            case "api/sales/customerDiscountFlat/search":
                this.responseMessage = this.customerDiscountFlatServiceManager.searchCustomerDiscountFlat(requestMessage);
                log.info("Sales module -> api/sales/customerDiscountFlat/search executed");
                break;

            case "api/sales/customerDiscountFlat/delete":
                this.responseMessage = this.customerDiscountFlatServiceManager.deleteCustomerDiscountFlat(requestMessage);
                log.info("Sales module -> api/sales/customerDiscountFlat/delete executed");
                break;

            case "api/sales/customerDiscountRange/save":
                this.responseMessage = this.customerDiscountRangeServiceManager.saveCustomerDiscountRange(requestMessage);
                log.info("Sales module -> api/sales/customerDiscountRange/save executed");
                break;

            case "api/sales/customerDiscountRange/search":
                this.responseMessage = this.customerDiscountRangeServiceManager.searchCustomerDiscountRange(requestMessage);
                log.info("Sales module -> api/sales/customerDiscountRange/search executed");
                break;

            case "api/sales/customerDiscountRange/delete":
                this.responseMessage = this.customerDiscountRangeServiceManager.deleteCustomerDiscountRange(requestMessage);
                log.info("Sales module -> api/sales/customerDiscountRange/delete executed");
                break;

            case "api/sales/customerQuotation/save":
                this.responseMessage = this.customerQuotationServiceManager.save(requestMessage);
                log.info("Sales module -> api/sales/customerQuotation/save executed");
                break;

            case "api/sales/customerQuotation/search":
                this.responseMessage = this.customerQuotationServiceManager.search(requestMessage);
                log.info("Sales module -> api/sales/customerQuotation/search executed");
                break;

            case "api/sales/customerQuotation/getByID":
                this.responseMessage = this.customerQuotationServiceManager.getByID(requestMessage);
                log.info("Sales module -> api/sales/customerQuotation/getByID executed");
                break;
                
            case "api/sales/customerQuotation/delete":
                this.responseMessage = this.customerQuotationServiceManager.delete(requestMessage);
                log.info("Sales module -> api/sales/customerQuotation/delete executed");
                break;

            case "api/sales/customerType/save":
                this.responseMessage = this.customerTypeServiceManager.saveCustomerType(requestMessage);
                log.info("Sales module -> api/sales/customerType/save executed");
                break;

            case "api/sales/customerType/search":
                this.responseMessage = this.customerTypeServiceManager.searchCustomerType(requestMessage);
                log.info("Sales module -> api/sales/customerType/search executed");
                break;

            case "api/sales/customerType/getByID":
                this.responseMessage = this.customerTypeServiceManager.getCustomerTypeByID(requestMessage);
                log.info("Sales module -> api/sales/customerType/getByID executed");
                break;

            case "api/sales/customerType/delete":
                this.responseMessage = this.customerTypeServiceManager.deleteCustomerType(requestMessage);
                log.info("Sales module -> api/sales/customerType/delete executed");
                break;
            case "api/customerInvoice/save":
                this.responseMessage = this.customerInvoiceServiceManager.save(requestMessage);
                log.info("Sales module -> api/sales/customerInvoice/delete executed");
                break;
            case "api/customerInvoice/search":
                this.responseMessage = this.customerInvoiceServiceManager.search(requestMessage);
                log.info("Sales module -> api/sales/customerInvoice/delete executed");
                break;
            case "api/customerInvoice/getByID":
                this.responseMessage = this.customerInvoiceServiceManager.search(requestMessage);
                log.info("Sales module -> api/sales/customerInvoice/delete executed");
                break;
            case "api/customerInvoice/delete":
                this.responseMessage = this.customerInvoiceServiceManager.delete(requestMessage);
                log.info("Sales module -> api/sales/customerInvoice/delete executed");
                break;

            case "api/customerReturn/save":
                this.responseMessage = this.customerReturnServiceManager.save(requestMessage);
                log.info("Sales module -> api/sales/customerReturn/delete executed");
                break;
            case "api/customerReturn/search":
                this.responseMessage = this.customerReturnServiceManager.search(requestMessage);
                log.info("Sales module -> api/sales/customerReturn/delete executed");
                break;
            case "api/customerReturn/getByID":
                this.responseMessage = this.customerReturnServiceManager.search(requestMessage);
                log.info("Sales module -> api/sales/customerReturn/delete executed");
                break;
            case "api/customerReturn/delete":
                this.responseMessage = this.customerReturnServiceManager.delete(requestMessage);
                log.info("Sales module -> api/sales/customerReturn/delete executed");
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
