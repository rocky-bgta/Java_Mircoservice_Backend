/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/14/2018
 * Time: 10:50 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.Utils.TillBoxUtils;
import nybsys.tillboxweb.bll.manager.ProductBllManager;
import nybsys.tillboxweb.broker.client.CallBack;
import nybsys.tillboxweb.broker.client.PublisherForWorker;
import nybsys.tillboxweb.broker.client.SubscriberForWorker;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.constant.WorkerSubscriptionConstants;
import nybsys.tillboxweb.coreConstant.CurrencyConstant;
import nybsys.tillboxweb.coreEnum.DebitCreditIndicator;
import nybsys.tillboxweb.coreEnum.DefaultCOA;
import nybsys.tillboxweb.coreEnum.ReferenceType;
import nybsys.tillboxweb.coreEnum.UserDefineSettingReferenceType;
import nybsys.tillboxweb.coreModels.CurrencyModel;
import nybsys.tillboxweb.coreModels.JournalModel;
import nybsys.tillboxweb.coreModels.VMJournalListModel;
import nybsys.tillboxweb.models.ProductModel;
import nybsys.tillboxweb.models.UserDefineSettingModel;
import nybsys.tillboxweb.models.VMProduct;
import nybsys.tillboxweb.models.VMProductWithStockAndPrice;
import org.eclipse.paho.client.mqttv3.MqttClient;
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
public class ProductServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(ProductServiceManager.class);

    @Autowired
    private ProductBllManager productBllManager;
    private ProductModel productModel;


    public ResponseMessage saveProduct(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        boolean isUpdateRequest = false;
        CurrencyModel currencyModel;
        VMProduct vmProduct;
        try {

            //get base currency and exchange rate
            currencyModel = getBaseCurrency();
            if (currencyModel == null) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = CurrencyConstant.BASE_CURRENT_NOT_FOUND;
                return responseMessage;
            }

            //check entry currency is present if not base currency will be entry currency
            if(requestMessage.entryCurrencyID == null || requestMessage.entryCurrencyID == 0)
            {
                requestMessage.entryCurrencyID = currencyModel.getCurrencyID();
            }

            vmProduct = Core.getRequestObject(requestMessage, VMProduct.class);

            /*Set<ConstraintViolation<ProductAttributeModel>> violations = this.validator.validate(productAttributeModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/
            if (vmProduct.productModel.getProductID() == null || vmProduct.productModel.getProductID() > 0) {
                isUpdateRequest = true;
            }

            //set currency
            if (vmProduct.productModel.getProductID() == null || vmProduct.productModel.getProductID() == 0) {
                vmProduct.productModel.setBaseCurrencyID(currencyModel.getCurrencyID());
                vmProduct.productModel.setEntryCurrencyID(requestMessage.entryCurrencyID);
                vmProduct.productModel.setBaseCurrencyAmount(vmProduct.productModel.getOpeningCost() * vmProduct.productModel.getExchangeRate());
            }

            vmProduct = this.productBllManager.saveProduct(vmProduct);
//            if (vmProduct.lstUserDefineSettingDetailModels.size() > 0) {
//                requestMessage.requestObj = vmProduct.lstUserDefineSettingDetailModels;
//                responseMessage = this.callInterModuleFunction("api/commonModule/userDefineSettingDetail/save", WorkerSubscriptionConstants.WORKER_COMMON_TOPIC, requestMessage);
//            }
//
//            if (vmProduct.lstRememberNoteModels.size() > 0) {
//                requestMessage.requestObj = vmProduct.lstRememberNoteModels;
//                responseMessage = this.callInterModuleFunction("api/commonModule/rememberNote/save", WorkerSubscriptionConstants.WORKER_COMMON_TOPIC, requestMessage);
//            }


            responseMessage.responseObj = vmProduct;

            if (Core.clientMessage.get().messageCode == null) {
                if (isUpdateRequest) {
                    if (vmProduct.productModel.getOpeningQuantity() != null && vmProduct.productModel.getOpeningQuantity() > 0) {
                        responseMessage = this.checkInterComForUpdate(requestMessage, vmProduct, currencyModel, requestMessage.entryCurrencyID);
                    }
                } else {
                    if (vmProduct.productModel.getOpeningQuantity() != null && vmProduct.productModel.getOpeningQuantity() > 0) {
                        responseMessage = this.checkInterCom(requestMessage, vmProduct, currencyModel, requestMessage.entryCurrencyID);
                    }

                }

                if (responseMessage.responseCode != TillBoxAppConstant.SUCCESS_CODE) {
                    this.rollBack();
                    if (responseMessage.message != null && responseMessage.message != "") {
                        responseMessage.message = MessageConstant.FAILED_TO_SAVE_PRODUCT;
                    }
                } else {
                    responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                    responseMessage.message = MessageConstant.SAVE_PRODUCT;
                    this.commit();
                }
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.FAILED_TO_SAVE_PRODUCT;
                } else {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("ProductServiceManager -> Product Service Manager got exception");
            responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            responseMessage.errorMessage = ex.getMessage();
//            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }

    private List<JournalModel> getJournalModelFromInvoice(VMProduct vmProduct, CurrencyModel currencyModel, Integer entryCurrencyID) {
        List<JournalModel> lstJournalModel = new ArrayList<>();

        double totalAmount = (vmProduct.productModel.getOpeningQuantity() * vmProduct.productModel.getOpeningCost());

        //Debit journal
        JournalModel journalModelDebit = new JournalModel();
        journalModelDebit.setBusinessID(vmProduct.productModel.getBusinessID());
        journalModelDebit.setAmount(totalAmount);
        journalModelDebit.setAccountID(DefaultCOA.Inventory.get());
        journalModelDebit.setReferenceID(vmProduct.productModel.getProductID());
        journalModelDebit.setReferenceType(ReferenceType.Product.get());
        journalModelDebit.setDrCrIndicator(DebitCreditIndicator.Debit.get());
        journalModelDebit.setDate(new Date());

        journalModelDebit.setBaseCurrencyID(currencyModel.getCurrencyID());
        journalModelDebit.setEntryCurrencyID(entryCurrencyID);
        journalModelDebit.setExchangeRate(vmProduct.productModel.getExchangeRate());
        journalModelDebit.setBaseCurrencyAmount(totalAmount * vmProduct.productModel.getExchangeRate());

        //Credit journal
        JournalModel journalModelCredit = new JournalModel();
        journalModelCredit.setBusinessID(vmProduct.productModel.getBusinessID());
        journalModelCredit.setAmount(totalAmount);
        journalModelCredit.setAccountID(DefaultCOA.HistoricalBalance.get());
        journalModelCredit.setReferenceID(vmProduct.productModel.getProductID());
        journalModelCredit.setReferenceType(ReferenceType.Product.get());
        journalModelCredit.setDrCrIndicator(DebitCreditIndicator.Credit.get());
        journalModelCredit.setDate(new Date());

        journalModelCredit.setBaseCurrencyID(currencyModel.getCurrencyID());
        journalModelCredit.setEntryCurrencyID(entryCurrencyID);
        journalModelCredit.setExchangeRate(vmProduct.productModel.getExchangeRate());
        journalModelCredit.setBaseCurrencyAmount(totalAmount * vmProduct.productModel.getExchangeRate());

        lstJournalModel.add(journalModelDebit);
        lstJournalModel.add(journalModelCredit);


        return lstJournalModel;
    }


    public ResponseMessage checkInterCom(RequestMessage requestMessage, VMProduct vmProduct, CurrencyModel currencyModel, Integer entryCurrencyID) {

        MqttClient mqttClientSaveJournal;
        CallBack callBackSaveJournal;

        //CallBack callBackDeleteJournal = null;


        ResponseMessage responseMessage;// = new ResponseMessage();
        ResponseMessage responseMessageSaveJournal;


        RequestMessage reqJournalSaveMessage;

        boolean workCompleteWithInAllowTime;
        try {

            Object lockObject = new Object();

            reqJournalSaveMessage = Core.getDefaultWorkerRequestMessage();
            reqJournalSaveMessage.token = requestMessage.token;

            String accountingTopic = WorkerSubscriptionConstants.WORKER_ACCOUNTING_MODULE_TOPIC;
            // String inventoryTopic = WorkerSubscriptionConstants.WORKER_INVENTORY_TOPIC;

            this.barrier = TillBoxUtils.getBarrier(1, lockObject);

            VMJournalListModel vmJournalListModel = new VMJournalListModel();
            vmJournalListModel.lstJournalModel = this.getJournalModelFromInvoice(vmProduct, currencyModel, entryCurrencyID);
            reqJournalSaveMessage.requestObj = vmJournalListModel;
            reqJournalSaveMessage.token = Core.requestToken.get();
            reqJournalSaveMessage.brokerMessage.serviceName = "api/journal/save";

            SubscriberForWorker subForWorker = new SubscriberForWorker(reqJournalSaveMessage.brokerMessage.messageId, this.barrier);
            mqttClientSaveJournal = subForWorker.subscribe();
            callBackSaveJournal = subForWorker.getCallBack();
            PublisherForWorker pubForWorkerGetJournalList = new PublisherForWorker(accountingTopic, mqttClientSaveJournal);
            pubForWorkerGetJournalList.publishedMessageToWorker(reqJournalSaveMessage);


            synchronized (lockObject) {
                responseMessage = Core.buildDefaultResponseMessage();
                long startTime = System.nanoTime();
                lockObject.wait(this.allowedTime);
                workCompleteWithInAllowTime = this.isResponseWithInAllowedTime(startTime);

                if (workCompleteWithInAllowTime) {

                    responseMessageSaveJournal = callBackSaveJournal.getResponseMessage();
                    responseMessage.message = "Inter module communication successful";
                    responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

                } else {
                    //timeout
                    log.info("Response time out");
                    log.info("RollBack checkInterCom Operation");

                    responseMessage.message = "Inter module communication Failed";
                    responseMessage.responseCode = TillBoxAppConstant.UN_PROCESSABLE_REQUEST;
                }
            }


            this.closeBrokerClient(mqttClientSaveJournal, reqJournalSaveMessage.brokerMessage.messageId);
            // this.closeBrokerClient(mqttClientSaveInventoryTransaction, reqObjInventoryTransactionMessage.brokerMessage.messageId);


        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Exception from checkInterCom Module communication UserServiceManager");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
        }
        return responseMessage;
    }


    public ResponseMessage checkInterComForUpdate(RequestMessage requestMessage, VMProduct vmProduct, CurrencyModel currencyModel, Integer entryCurrencyID) {

        MqttClient mqttClientSaveJournal;
        MqttClient mqttClientDeleteJournal = null;


        CallBack callBackSaveJournal;
        CallBack callBackDeleteJournal = null;

        //CallBack callBackDeleteJournal = null;


        ResponseMessage responseMessage;// = new ResponseMessage();
        ResponseMessage responseMessageSaveJournal;


        RequestMessage reqJournalSaveMessage, journalDeletedRequestMessage;
        boolean workCompleteWithInAllowTime;
        try {

            Object lockObject = new Object();

            reqJournalSaveMessage = Core.getDefaultWorkerRequestMessage();
            journalDeletedRequestMessage = Core.getDefaultWorkerRequestMessage();

            reqJournalSaveMessage.token = requestMessage.token;
            journalDeletedRequestMessage.token = requestMessage.token;

            String accountingTopic = WorkerSubscriptionConstants.WORKER_ACCOUNTING_MODULE_TOPIC;


            this.barrier = TillBoxUtils.getBarrier(2, lockObject);


            VMJournalListModel vmJournalListModel = new VMJournalListModel();
            vmJournalListModel.lstJournalModel = this.getJournalModelFromInvoice(vmProduct, currencyModel, entryCurrencyID);
            reqJournalSaveMessage.requestObj = vmJournalListModel;

            JournalModel searchJournalModel = new JournalModel();
            searchJournalModel.setReferenceType(ReferenceType.Product.get());
            searchJournalModel.setReferenceID(vmProduct.productModel.getProductID());

            journalDeletedRequestMessage.requestObj = searchJournalModel;
            journalDeletedRequestMessage.brokerMessage.serviceName = "api/journal/delete";
            SubscriberForWorker subForWorkerJournalDelete = new SubscriberForWorker(journalDeletedRequestMessage.brokerMessage.messageId, this.barrier);
            mqttClientDeleteJournal = subForWorkerJournalDelete.subscribe();
            callBackDeleteJournal = subForWorkerJournalDelete.getCallBack();
            PublisherForWorker pubForWorkerForJournalDelete = new PublisherForWorker(accountingTopic, mqttClientDeleteJournal);
            pubForWorkerForJournalDelete.publishedMessageToWorker(journalDeletedRequestMessage);
            //======================= End of one ===========================


            reqJournalSaveMessage.brokerMessage.serviceName = "api/journal/save";
            SubscriberForWorker subForWorker = new SubscriberForWorker(reqJournalSaveMessage.brokerMessage.messageId, this.barrier);
            mqttClientSaveJournal = subForWorker.subscribe();
            callBackSaveJournal = subForWorker.getCallBack();
            PublisherForWorker pubForWorkerGetJournalList = new PublisherForWorker(accountingTopic, mqttClientSaveJournal);
            pubForWorkerGetJournalList.publishedMessageToWorker(reqJournalSaveMessage);


            synchronized (lockObject) {
                responseMessage = Core.buildDefaultResponseMessage();
                long startTime = System.nanoTime();
                lockObject.wait(this.allowedTime);
                workCompleteWithInAllowTime = this.isResponseWithInAllowedTime(startTime);

                if (workCompleteWithInAllowTime) {
                    responseMessage = callBackDeleteJournal.getResponseMessage();
                    responseMessageSaveJournal = callBackSaveJournal.getResponseMessage();
                    responseMessage.message = "Inter module communication successful";
                    responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

                } else {
                    //timeout
                    log.info("Response time out");
                    log.info("RollBack checkInterCom Operation");

                    responseMessage.message = "Inter module communication Failed";
                    responseMessage.responseCode = TillBoxAppConstant.UN_PROCESSABLE_REQUEST;
                }
            }


            this.closeBrokerClient(mqttClientSaveJournal, reqJournalSaveMessage.brokerMessage.messageId);
            this.closeBrokerClient(mqttClientDeleteJournal, requestMessage.brokerMessage.messageId);


        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Exception from checkInterCom Module communication UserServiceManager");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
        }
        return responseMessage;
    }


    public ResponseMessage search(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<ProductModel> lstProductModel = new ArrayList<>();
        ProductModel productModel = new ProductModel();
        try {
            productModel = Core.getRequestObject(requestMessage, ProductModel.class);

            responseMessage.responseObj = this.productBllManager.getFilteredVMProduct(productModel);


        } catch (Exception ex) {
            log.error("ProductServiceManager -> searchProduct got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }

    public ResponseMessage likeSearch(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<VMProduct> lstVmProduct = new ArrayList<>();
        ProductModel productModel = new ProductModel();
        try {
            productModel = Core.getRequestObject(requestMessage, ProductModel.class);
            Integer businessID = requestMessage.businessID;
            lstVmProduct = this.productBllManager.getLikeFilteredProduct(productModel, businessID);

            responseMessage.responseObj = lstVmProduct;
            if (Core.clientMessage.get().messageCode == null) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.PRODUCT_GET_SUCCESSFULLY;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.PRODUCT_GET_FAILED;
            }

        } catch (Exception ex) {
            log.error("ProductServiceManager -> searchProduct got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }

    public ResponseMessage getByID(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        ProductModel productModel = new ProductModel();
        try {
            productModel = Core.getRequestObject(requestMessage, ProductModel.class);

            /*Set<ConstraintViolation<ProductTypeModel>> violations = this.validator.validate(productTypeModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            productModel = this.productBllManager.getById(productModel.getProductID());

            responseMessage.responseObj = productModel;
            if (Core.clientMessage.get().messageCode == null) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_INACTIVE_PRODUCT_TYPE;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.FAILED_TO_INACTIVE_PRODUCT_TYPE;
                } else {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
            }

        } catch (Exception ex) {
            log.error("ProductServiceManager -> filterProduct got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }


    public ResponseMessage getVMProduct(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        VMProduct vmProduct = new VMProduct();

        UserDefineSettingModel userDefineSettingModel = new UserDefineSettingModel();
        userDefineSettingModel.setReferenceID(UserDefineSettingReferenceType.Product.get());

        requestMessage.requestObj = userDefineSettingModel;
        responseMessage = this.callInterModuleFunction("api/commonModule/userDefineSetting/getByConditions", WorkerSubscriptionConstants.WORKER_COMMON_TOPIC, requestMessage);
        List<UserDefineSettingModel> lstUserDefineSettingModel = new ArrayList<>();
        List<UserDefineSettingModel> lstFinalUserDefineSettingModel = new ArrayList<>();
        if (responseMessage.responseObj != null) {
            lstUserDefineSettingModel = (List) responseMessage.responseObj;
            if (lstUserDefineSettingModel.size() > 0) {
                for (Object model : lstUserDefineSettingModel) {
                    model = Core.modelMapper.map(model, UserDefineSettingModel.class);
                    lstFinalUserDefineSettingModel.add((UserDefineSettingModel) model);
                }
            }
        }

        vmProduct.lstUserDefineSettingModels = lstFinalUserDefineSettingModel;
        responseMessage.responseObj = vmProduct;
        return responseMessage;
    }

    public ResponseMessage getProductWithStockAndPrice(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        ProductModel productModel = new ProductModel();
        List<VMProductWithStockAndPrice> lstVmProductWithStockAndPrice = new ArrayList<>();
        try {
            productModel = Core.getRequestObject(requestMessage, ProductModel.class);

            lstVmProductWithStockAndPrice = this.productBllManager.getProductWithStockAndPrice(productModel);

            responseMessage.responseObj = lstVmProductWithStockAndPrice;
            if (Core.clientMessage.get().messageCode == null) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.PRODUCT_GET_SUCCESSFULLY;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.PRODUCT_GET_FAILED;
                } else {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
            }

        } catch (Exception ex) {
            log.error("ProductServiceManager -> getProductWithStockAndPrice got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }

    public ResponseMessage callInterModuleFunction(String apiurl, String topicName, RequestMessage reqMessForWorker) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {

            MqttClient mqttClient;
            responseMessage = Core.buildDefaultResponseMessage();

            boolean workCompleteWithInAllowTime;
            Object lockObject = new Object();
            this.barrier = TillBoxUtils.getBarrier(1, lockObject);

            CallBack callBack;
            String pubTopic = topicName;
            reqMessForWorker.brokerMessage.serviceName = apiurl;
            reqMessForWorker.token = Core.requestToken.get();
           /* UserDefineSettingModel userDefineSettingModel = new UserDefineSettingModel();
            userDefineSettingModel.setReferenceID(TillBoxAppEnum.UserDefineSettingReferenceType.Product.get());

            reqMessForWorker.requestObj = userDefineSettingModel;*/
            SubscriberForWorker subForWorker = new SubscriberForWorker(reqMessForWorker.brokerMessage.messageId, this.barrier);
            mqttClient = subForWorker.subscribe();
            callBack = subForWorker.getCallBack();
            PublisherForWorker pubForWorker = new PublisherForWorker(pubTopic, mqttClient);
            pubForWorker.publishedMessageToWorker(reqMessForWorker);

            synchronized (lockObject) {
                long startTime = System.nanoTime();
                lockObject.wait(allowedTime);
                workCompleteWithInAllowTime = this.isResponseWithInAllowedTime(startTime);

                if (workCompleteWithInAllowTime) {
                    responseMessage = callBack.getResponseMessage();


                } else {
                    //timeout
                    //TODO Implement role back logic
                }
            }
            this.closeBrokerClient(mqttClient, reqMessForWorker.brokerMessage.messageId);

        } catch (Exception ex) {
            log.error("ProductServiceManager -> interModuelfunction got exception");
            this.WriteExceptionLog(ex);
        }
        return responseMessage;
    }

    public CurrencyModel getBaseCurrency() {
        MqttClient mqttClient;
        ResponseMessage responseMessage = new ResponseMessage();
        CurrencyModel currencyModel = new CurrencyModel();
        RequestMessage reqMessForWorker;
        boolean workCompleteWithInAllowTime;
        try {

            Object lockObject = new Object();
            this.barrier = TillBoxUtils.getBarrier(1, lockObject);

            CallBack callBack;
            reqMessForWorker = Core.getDefaultWorkerRequestMessage();

            String pubTopic = WorkerSubscriptionConstants.WORKER_USER_REGISTRATION_MODULE_TOPIC;
            reqMessForWorker.brokerMessage.serviceName = "api/currency/getBaseCurrency";
            reqMessForWorker.token = Core.requestToken.get();

            SubscriberForWorker subForWorker = new SubscriberForWorker(reqMessForWorker.brokerMessage.messageId, this.barrier);
            mqttClient = subForWorker.subscribe();
            callBack = subForWorker.getCallBack();
            PublisherForWorker pubForWorker = new PublisherForWorker(pubTopic, mqttClient);
            pubForWorker.publishedMessageToWorker(reqMessForWorker);

            synchronized (lockObject) {
                long startTime = System.nanoTime();
                lockObject.wait(allowedTime);
                workCompleteWithInAllowTime = this.isResponseWithInAllowedTime(startTime);

                if (workCompleteWithInAllowTime) {
                    responseMessage = callBack.getResponseMessage();
                    if (responseMessage.responseObj != null) {
                        currencyModel = Core.jsonMapper.convertValue(responseMessage.responseObj, CurrencyModel.class);
                    } else {
                        currencyModel = null;
                    }
                } else {
                    //timeout
                    //TODO Implement role back logic
                    currencyModel = null;
                }
            }
            this.closeBrokerClient(mqttClient, reqMessForWorker.brokerMessage.messageId);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("ProductServiceManager -> inter module communication getBaseCurrencyAndExchangeRate got exception");
        }
        return currencyModel;
    }
}
