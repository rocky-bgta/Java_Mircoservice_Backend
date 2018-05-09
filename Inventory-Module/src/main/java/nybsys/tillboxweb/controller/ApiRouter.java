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
    private ProductCategoryServiceManager productCategoryServiceManager;

    @Autowired
    private ProductTypeServiceManager productTypeServiceManager;

    @Autowired
    private ProductAttributeServiceManager productAttributeServiceManager;

    @Autowired
    private ProductAttributeValueServiceManager productAttributeValueServiceManager;

    @Autowired
    private ProductPriceListServiceManager productPriceListServiceManager;

    @Autowired
    private UOMMethodServiceManager uomMethodServiceManager;

    @Autowired
    private UnitOfMeasureServiceManager unitOfMeasureServiceManager;

    @Autowired
    private UnitMeasurementConversionServiceManager unitMeasurementConversionServiceManager;

    @Autowired
    private ProductServiceManager productServiceManager;

    @Autowired
    private ProductAdjustmentServiceManager productAdjustmentServiceManager;

    @Autowired
    private InventoryTransactionServiceManager inventoryTransactionServiceManager;

    @Autowired
    private AdjustmentReferenceTypeServiceManager adjustmentReferenceTypeServiceManager;

    @Override
    public ResponseMessage getResponseMessage(String serviceName, RequestMessage requestMessage) {
        this.checkSecurityAndExecuteService(serviceName,requestMessage);
        //close session factory
        //this.closeSession();
        return this.responseMessage;
    }


    protected void executeServiceManager(String serviceName, RequestMessage requestMessage) {
        switch (serviceName) {
            case "api/inventory/adjustmentReferenceType/save":
                this.responseMessage = this.adjustmentReferenceTypeServiceManager.saveOrUpdate(requestMessage);
                log.info("Inventory module -> api/inventory/adjustmentReferenceType/save executed");
                break;

            case "api/inventory/adjustmentReferenceType/search":
                this.responseMessage = this.adjustmentReferenceTypeServiceManager.search(requestMessage);
                log.info("Inventory module -> api/inventory/adjustmentReferenceType/search executed");
                break;

            case "api/inventory/adjustmentReferenceType/delete":
                this.responseMessage = this.adjustmentReferenceTypeServiceManager.delete(requestMessage);
                log.info("Inventory module -> api/inventory/adjustmentReferenceType/delete executed");
                break;

            case "api/inventory/adjustmentReferenceType/inactive":
                this.responseMessage = this.adjustmentReferenceTypeServiceManager.inActive(requestMessage);
                log.info("Inventory module -> api/inventory/adjustmentReferenceType/inactive executed");
                break;


            case "api/inventory/productAdjustment/save":
                this.responseMessage = this.productAdjustmentServiceManager.saveOrUpdateProductAdjustment(requestMessage);
                log.info("Inventory module -> api/productAdjustment/save executed");
                break;

            case "api/inventory/productAdjustment/search":
                this.responseMessage = this.productAdjustmentServiceManager.searchProductAdjustment(requestMessage);
                log.info("Inventory module -> api/productAdjustment/search executed");
                break;

            case "api/inventory/productAdjustment/delete":
                this.responseMessage = this.productAdjustmentServiceManager.deleteProductAdjustment(requestMessage);
                log.info("Inventory module -> api/productAdjustment/delete executed");
                break;

            case "api/productType/save":
                this.responseMessage = this.productTypeServiceManager.saveProductType(requestMessage);
                log.info("Inventory module -> api/productType/save executed");
                break;
            case "api/productType/delete":
                this.responseMessage = this.productTypeServiceManager.deleteProductType(requestMessage);
                log.info("Inventory module -> api/productType/delete executed");
                break;
            case "api/productType/search":
                this.responseMessage = this.productTypeServiceManager.searchProductType(requestMessage);
                log.info("Inventory module -> api/productType/search executed");
                break;
            case "api/productType/inactive":
                this.responseMessage = this.productTypeServiceManager.inactiveProductType(requestMessage);
                log.info("Inventory module -> api/productType/inactive executed");
                break;
            case "api/productCategory/save":
                this.responseMessage = this.productCategoryServiceManager.saveProductCategory(requestMessage);
                log.info("Inventory module -> api/productCategory/save executed");
                break;
            case "api/productCategory/delete":
                this.responseMessage = this.productCategoryServiceManager.deleteProductCategory(requestMessage);
                log.info("Inventory module -> api/productCategory/delete executed");
                break;
            case "api/productCategory/search":
                this.responseMessage = this.productCategoryServiceManager.searchProductCategory(requestMessage);
                log.info("Inventory module -> api/productCategory/search executed");
                break;
            case "api/productCategory/inactive":
                this.responseMessage = this.productCategoryServiceManager.inactiveProductCategory(requestMessage);
                log.info("Inventory module -> api/productCategory/inactive executed");
                break;
            case "api/productAttribute/save":
                this.responseMessage = this.productAttributeServiceManager.saveProductAttribute(requestMessage);
                log.info("Inventory module -> api/productCategory/save executed");
                break;
            case "api/productAttribute/delete":
                this.responseMessage = this.productAttributeServiceManager.deleteProductAttribute(requestMessage);
                log.info("Inventory module -> api/productAttribute/delete executed");
                break;
            case "api/productAttribute/search":
                this.responseMessage = this.productAttributeServiceManager.searchProductAttribute(requestMessage);
                log.info("Inventory module -> api/productAttribute/search executed");
                break;
            case "api/productAttribute/inactive":
                this.responseMessage = this.productAttributeServiceManager.inactiveProductAttribute(requestMessage);
                log.info("Inventory module -> api/productAttribute/inactive executed");
                break;
            case "api/productAttributeValue/save":
                this.responseMessage = this.productAttributeValueServiceManager.saveProductAttributeValue(requestMessage);
                log.info("Inventory module -> api/productCategory/save executed");
                break;
            case "api/productAttributeValue/delete":
                this.responseMessage = this.productAttributeValueServiceManager.deleteProductAttributeValue(requestMessage);
                log.info("Inventory module -> api/productAttributeValue/delete executed");
                break;
            case "api/productAttributeValue/search":
                this.responseMessage = this.productAttributeValueServiceManager.searchProductAttributeValue(requestMessage);
                log.info("Inventory module -> api/productAttributeValue/search executed");
                break;
            case "api/productAttributeValue/inactive":
                this.responseMessage = this.productAttributeValueServiceManager.inactiveProductAttributeValue(requestMessage);
                log.info("Inventory module -> api/productAttributeValue/inactive executed");
                break;
            case "api/productPriceList/save":
                this.responseMessage = this.productPriceListServiceManager.savePriceCategory(requestMessage);
                log.info("Inventory module -> api/productCategory/save executed");
                break;
            case "api/productPriceList/delete":
                this.responseMessage = this.productPriceListServiceManager.deletePriceCategory(requestMessage);
                log.info("Inventory module -> api/productPriceList/delete executed");
                break;
            case "api/productPriceList/search":
                this.responseMessage = this.productPriceListServiceManager.searchPriceCategory(requestMessage);
                log.info("Inventory module -> api/productPriceList/search executed");
                break;
            case "api/productPriceList/inactive":
                this.responseMessage = this.productPriceListServiceManager.inactivePriceCategory(requestMessage);
                log.info("Inventory module -> api/productPriceList/inactive executed");
                break;
            case "api/uomMethod/save":
                this.responseMessage = this.uomMethodServiceManager.saveUOMMethod(requestMessage);
                log.info("Inventory module -> api/uomMethod/save executed");
                break;
            case "api/uomMethod/search":
                this.responseMessage = this.uomMethodServiceManager.searchUOMMethod(requestMessage);
                log.info("Inventory module -> api/uomMethod/search executed");
                break;
            case "api/uomMethod/inactive":
                this.responseMessage = this.uomMethodServiceManager.inactiveUOMMethod(requestMessage);
                log.info("Inventory module -> api/uomMethod/inactive executed");
                break;
            case "api/unitOfMeasure/save":
                this.responseMessage = this.unitOfMeasureServiceManager.saveUnitOfMeasure(requestMessage);
                log.info("Inventory module -> api/unitOfMeasure/save executed");
                break;
            case "api/unitOfMeasure/search":
                this.responseMessage = this.unitOfMeasureServiceManager.searchUnitOfMeasure(requestMessage);
                log.info("Inventory module -> api/unitOfMeasure/search executed");
                break;
            case "api/unitOfMeasure/inactive":
                this.responseMessage = this.unitOfMeasureServiceManager.inactiveUnitOfMeasure(requestMessage);
                log.info("Inventory module -> api/unitOfMeasure/inactive executed");
                break;
            case "api/unitMeasurementConversion/save":
                this.responseMessage = this.unitMeasurementConversionServiceManager.saveUnitMeasurementConversion(requestMessage);
                log.info("Inventory module -> api/unitMeasurementConversion/save executed");
                break;
            case "api/unitMeasurementConversion/search":
                this.responseMessage = this.unitMeasurementConversionServiceManager.searchUnitMeasurementConversion(requestMessage);
                log.info("Inventory module -> api/unitMeasurementConversion/search executed");
                break;
            case "api/unitMeasurementConversion/inactive":
                this.responseMessage = this.unitMeasurementConversionServiceManager.inactiveUnitMeasurementConversion(requestMessage);
                log.info("Inventory module -> api/unitMeasurementConversion/inactive executed");
                break;
            case "api/product/save":
                this.responseMessage = this.productServiceManager.saveProduct(requestMessage);
                log.info("Inventory module -> api/product/save executed");
                break;
            case "api/product/search":
                this.responseMessage = this.productServiceManager.search(requestMessage);
                log.info("Inventory module -> api/product/search executed");
                break;
            case "api/product/likeSearch":
                this.responseMessage = this.productServiceManager.likeSearch(requestMessage);
                log.info("Inventory module -> api/product/likeSearch executed");
                break;
            case "api/product/searchWithStockAndPrice":
                this.responseMessage = this.productServiceManager.getProductWithStockAndPrice(requestMessage);
                log.info("Inventory module -> api/product/searchWithStockAndPrice executed");
                break;
            case "api/product/getByID":
                this.responseMessage = this.productServiceManager.getByID(requestMessage);
                log.info("Inventory module -> api/product/getByID executed");
                break;
            case "api/product/getVMProduct":
                this.responseMessage = this.productServiceManager.getVMProduct(requestMessage);
                log.info("Inventory module -> api/product/getVMProduct executed");
            case "api/inventory/inventoryTransaction/Save":
                this.responseMessage = this.inventoryTransactionServiceManager.saveInventoryTransaction(requestMessage);
                log.info("Inventory module -> api/Inventory/inventoryTransaction/save executed");
                break;
            case "api/inventory/inventoryTransaction/search":
                this.responseMessage = this.inventoryTransactionServiceManager.searchInventoryTransaction(requestMessage);
                log.info("Inventory module -> api/Inventory/inventoryTransaction/search executed");
                break;
            case "api/inventory/inventoryTransaction/delete":
                this.responseMessage = this.inventoryTransactionServiceManager.delete(requestMessage);
                log.info("Inventory module -> api/Inventory/inventoryTransaction/delete executed");
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
