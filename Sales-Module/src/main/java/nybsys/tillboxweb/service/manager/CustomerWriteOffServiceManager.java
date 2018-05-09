/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 26/02/2018
 * Time: 05:45
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
import nybsys.tillboxweb.bll.manager.CustomerWriteOffBllManager;
import nybsys.tillboxweb.bll.manager.CustomerWriteOffDetailBllManager;
import nybsys.tillboxweb.broker.client.CallBack;
import nybsys.tillboxweb.broker.client.PublisherForWorker;
import nybsys.tillboxweb.broker.client.SubscriberForWorker;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.constant.WorkerSubscriptionConstants;
import nybsys.tillboxweb.coreConstant.CurrencyConstant;
import nybsys.tillboxweb.coreEnum.DebitCreditIndicator;
import nybsys.tillboxweb.coreEnum.DefaultCOA;
import nybsys.tillboxweb.coreEnum.PartyType;
import nybsys.tillboxweb.coreEnum.ReferenceType;
import nybsys.tillboxweb.coreModels.CurrencyModel;
import nybsys.tillboxweb.coreModels.JournalModel;
import nybsys.tillboxweb.coreModels.VMJournalListModel;
import nybsys.tillboxweb.models.CustomerWriteOffDetailModel;
import nybsys.tillboxweb.models.CustomerWriteOffModel;
import nybsys.tillboxweb.models.VMCustomerWriteOffModel;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CustomerWriteOffServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(CustomerWriteOffServiceManager.class);
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Autowired
    CustomerWriteOffBllManager customerWriteOffBllManager;

    @Autowired
    CustomerWriteOffDetailBllManager customerWriteOffDetailBllManager;

    private CustomerAdjustmentServiceManager customerAdjustmentServiceManager = new CustomerAdjustmentServiceManager();

    public ResponseMessage saveCustomerWriteOffVM(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        ResponseMessage responseFromInterModule;
        CustomerWriteOffModel customerWriteOffModel;
        VMCustomerWriteOffModel vmCustomerWriteOffModel;
        List<CustomerWriteOffDetailModel> lstCustomerWriteOffDetailModel;
        CurrencyModel currencyModel;
        try {
            //get base currency and exchange rate
            currencyModel = this.customerAdjustmentServiceManager.getBaseCurrency();
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

            vmCustomerWriteOffModel = Core.getRequestObject(requestMessage, VMCustomerWriteOffModel.class);
            customerWriteOffModel = vmCustomerWriteOffModel.customerWriteOffModel;
            lstCustomerWriteOffDetailModel = vmCustomerWriteOffModel.lstCustomerWriteOffDetailModel;


            /*Set<ConstraintViolation<CustomerWriteOffModel>> violations = this.validator.validate(customerWriteOffModel);
            if (violations.size() > 0) {
                responseMessage = this.buildResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }
            for(CustomerWriteOffDetailModel customerWriteOffDetailModel : lstCustomerWriteOffDetailModel)
            {
                Set<ConstraintViolation<CustomerWriteOffDetailModel>> violations = this.validator.validate(customerWriteOffDetailModel);
                if (violations.size() > 0) {
                    responseMessage = this.buildResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                    return responseMessage;
                }
            }*/

            if (customerWriteOffModel.getCustomerWriteOffID() != null && customerWriteOffModel.getCustomerWriteOffID() > 0) {

                //(1)delete journal
                JournalModel deleteJournalModel = new JournalModel();
                deleteJournalModel.setReferenceType(DefaultCOA.AccountPayable.get());
                deleteJournalModel.setReferenceID(customerWriteOffModel.getCustomerWriteOffID());

                String deleteJournalServiceName = "api/journal/delete";
                responseFromInterModule = this.customerAdjustmentServiceManager.accountingInterModuleCommunication(deleteJournalServiceName,deleteJournalModel);
                if (responseFromInterModule.responseCode != 200) {
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    if (responseFromInterModule.message == null) {
                        responseMessage.message = MessageConstant.CUSTOMER_WRITE_OFF_SAVE_FAILED;
                    } else {
                        responseMessage.message = responseFromInterModule.message;
                    }
                    this.rollBack();
                    return responseMessage;
                }

                //(2)delete detail
                this.customerWriteOffDetailBllManager.deleteCustomerAdjustDetailByCustomerWriteOffID(customerWriteOffModel.getCustomerWriteOffID());
                if (Core.clientMessage.get().messageCode != null) {
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    if (Core.clientMessage.get().userMessage == null) {
                        responseMessage.message = MessageConstant.CUSTOMER_WRITE_OFF_SAVE_FAILED;
                    } else {
                        responseMessage.message = Core.clientMessage.get().userMessage;
                    }
                    this.rollBack();
                    return responseMessage;
                }
            }

            //(3)
            //add currency
            if (customerWriteOffModel.getCustomerWriteOffID() == null || customerWriteOffModel.getCustomerWriteOffID() == 0) {
                customerWriteOffModel.setBaseCurrencyID(currencyModel.getCurrencyID());
                customerWriteOffModel.setEntryCurrencyID(requestMessage.entryCurrencyID);
                customerWriteOffModel.setBaseCurrencyAmount(customerWriteOffModel.getTotalAmount() * customerWriteOffModel.getExchangeRate());
            }

            customerWriteOffModel = this.customerWriteOffBllManager.saveOrUpdate(customerWriteOffModel);
            if (Core.clientMessage.get().messageCode != null) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.CUSTOMER_WRITE_OFF_SAVE_FAILED;
                } else {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
                return responseMessage;
            }

            //(4)save journal
            VMJournalListModel vmJournalListModel = new VMJournalListModel();
            JournalModel drJournalModel = new JournalModel();
            JournalModel crJournalModel = new JournalModel();

            //dr
            drJournalModel.setDrCrIndicator(DebitCreditIndicator.Debit.get());
            drJournalModel.setBusinessID(customerWriteOffModel.getBusinessID());
            drJournalModel.setAmount(customerWriteOffModel.getTotalAmount());
            drJournalModel.setAccountID(DefaultCOA.BadDebts.get());
            drJournalModel.setReferenceType(ReferenceType.CustomerWriteOff.get());
            drJournalModel.setReferenceID(customerWriteOffModel.getCustomerWriteOffID());

            drJournalModel.setBaseCurrencyID(currencyModel.getCurrencyID());
            drJournalModel.setEntryCurrencyID(requestMessage.entryCurrencyID);
            drJournalModel.setExchangeRate(customerWriteOffModel.getExchangeRate());
            drJournalModel.setBaseCurrencyAmount(customerWriteOffModel.getTotalAmount() * customerWriteOffModel.getExchangeRate());
            //cr
            crJournalModel.setDrCrIndicator(DebitCreditIndicator.Credit.get());
            crJournalModel.setBusinessID(customerWriteOffModel.getBusinessID());
            crJournalModel.setAmount(customerWriteOffModel.getTotalAmount());
            crJournalModel.setAccountID(DefaultCOA.TradeDebtors.get());
            crJournalModel.setReferenceType(ReferenceType.CustomerWriteOff.get());
            crJournalModel.setReferenceID(customerWriteOffModel.getCustomerWriteOffID());
            crJournalModel.setPartyID(customerWriteOffModel.getCustomerID());
            crJournalModel.setPartyType(PartyType.Customer.get());

            crJournalModel.setBaseCurrencyID(currencyModel.getCurrencyID());
            crJournalModel.setEntryCurrencyID(requestMessage.entryCurrencyID);
            crJournalModel.setExchangeRate(customerWriteOffModel.getExchangeRate());
            crJournalModel.setBaseCurrencyAmount(customerWriteOffModel.getTotalAmount() * customerWriteOffModel.getExchangeRate());

            //make vm
            vmJournalListModel.lstJournalModel.add(drJournalModel);
            vmJournalListModel.lstJournalModel.add(crJournalModel);

            String saveJournalServiceName = "api/journal/save";
            responseFromInterModule = this.customerAdjustmentServiceManager.accountingInterModuleCommunication(saveJournalServiceName,vmJournalListModel);
            if (responseFromInterModule.responseCode != 200) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (responseFromInterModule.message == null) {
                    responseMessage.message = MessageConstant.CUSTOMER_WRITE_OFF_SAVE_FAILED;
                } else {
                    responseMessage.message = responseFromInterModule.message;
                }
                this.rollBack();
                return responseMessage;
            }

            //(5)save or update customer adjustment detail+ business validation
            this.customerWriteOffDetailBllManager.saveOrUpdateList(lstCustomerWriteOffDetailModel, customerWriteOffModel.getCustomerWriteOffID());
            if (Core.clientMessage.get().messageCode != null) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.CUSTOMER_WRITE_OFF_SAVE_FAILED;
                } else {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
                return responseMessage;
            }

            this.commit();
            responseMessage.message = MessageConstant.CUSTOMER_WRITE_OFF_SAVE_SUCCESSFULLY;
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

        } catch (Exception ex) {
            log.error("CustomerWriteOffServiceManager -> saveCustomerWriteOffVM got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }

    public ResponseMessage searchCustomerWriteOffVM(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        CustomerWriteOffModel customerWriteOffModel;
        List<CustomerWriteOffModel> lstCustomerWriteOffModel;
        List<VMCustomerWriteOffModel> lstVmCustomerWriteOffModel = new ArrayList<>();
        try {
            customerWriteOffModel = Core.getRequestObject(requestMessage, CustomerWriteOffModel.class);

            lstCustomerWriteOffModel = this.customerWriteOffBllManager.searchCustomerWriteOff(customerWriteOffModel);
            for (CustomerWriteOffModel customerWriteOffModelObject : lstCustomerWriteOffModel) {
                VMCustomerWriteOffModel vmCustomerWriteOffModel = new VMCustomerWriteOffModel();
                vmCustomerWriteOffModel.customerWriteOffModel = this.customerWriteOffBllManager.searchCustomerWriteOffByID(customerWriteOffModelObject.getCustomerID(), customerWriteOffModelObject.getBusinessID());
                if (vmCustomerWriteOffModel.customerWriteOffModel != null && vmCustomerWriteOffModel.customerWriteOffModel.getCustomerWriteOffID() != null) {
                    vmCustomerWriteOffModel.lstCustomerWriteOffDetailModel = this.customerWriteOffDetailBllManager.searchCustomerWriteOffDetailByWriteOffID(vmCustomerWriteOffModel.customerWriteOffModel.getCustomerWriteOffID());
                }
                if (vmCustomerWriteOffModel.customerWriteOffModel != null || vmCustomerWriteOffModel.lstCustomerWriteOffDetailModel.size() > 0) {
                    lstVmCustomerWriteOffModel.add(vmCustomerWriteOffModel);
                }
            }

            responseMessage.responseObj = lstVmCustomerWriteOffModel;
            if (lstVmCustomerWriteOffModel.size() == 0) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.CUSTOMER_WRITE_OFF_GET_FAILED;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.CUSTOMER_WRITE_OFF_GET_SUCCESSFULLY;
            }

        } catch (Exception ex) {
            log.error("CustomerWriteOffServiceManager -> searchCustomerWriteOffVM got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }

    public ResponseMessage getCustomerWriteOffVMByID(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        CustomerWriteOffModel customerWriteOffModel;
        VMCustomerWriteOffModel vmCustomerWriteOffModel = new VMCustomerWriteOffModel();
        try {
            customerWriteOffModel = Core.getRequestObject(requestMessage, CustomerWriteOffModel.class);
            Integer businessID = requestMessage.businessID;
            vmCustomerWriteOffModel.customerWriteOffModel = this.customerWriteOffBllManager.searchCustomerWriteOffByID(customerWriteOffModel.getCustomerWriteOffID(), businessID);
            if (vmCustomerWriteOffModel.customerWriteOffModel != null && vmCustomerWriteOffModel.customerWriteOffModel.getCustomerWriteOffID() != null) {
                vmCustomerWriteOffModel.lstCustomerWriteOffDetailModel = this.customerWriteOffDetailBllManager.searchCustomerWriteOffDetailByWriteOffID(vmCustomerWriteOffModel.customerWriteOffModel.getCustomerWriteOffID());
            }
            responseMessage.responseObj = vmCustomerWriteOffModel;
            if (vmCustomerWriteOffModel.lstCustomerWriteOffDetailModel.size() == 0 && vmCustomerWriteOffModel.customerWriteOffModel == null) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.CUSTOMER_WRITE_OFF_GET_FAILED;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.CUSTOMER_WRITE_OFF_GET_SUCCESSFULLY;
            }

        } catch (Exception ex) {
            log.error("CustomerWriteOffServiceManager -> getCustomerWriteOffVMByID got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }

    public ResponseMessage deleteCustomerWriteOffAndDetail(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        CustomerWriteOffModel customerWriteOffModel;
        try {
            customerWriteOffModel = Core.getRequestObject(requestMessage, CustomerWriteOffModel.class);
            if (customerWriteOffModel.getCustomerWriteOffID() != null && customerWriteOffModel.getCustomerWriteOffID() > 0) {

                //(1)
                this.customerWriteOffBllManager.deleteCustomerWriteOffByCustomerWriteOffID(customerWriteOffModel.getCustomerWriteOffID());
                if (Core.clientMessage.get().messageCode != null) {
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    if (Core.clientMessage.get().userMessage == null) {
                        responseMessage.message = MessageConstant.CUSTOMER_WRITE_OFF_DELETE_FAILED;
                    } else {
                        responseMessage.message = Core.clientMessage.get().userMessage;
                    }
                    this.rollBack();
                    return responseMessage;
                }
                //(2)delete journal
                JournalModel deleteJournalModel = new JournalModel();
                deleteJournalModel.setReferenceType(DefaultCOA.AccountPayable.get());
                deleteJournalModel.setReferenceID(customerWriteOffModel.getCustomerWriteOffID());

                String deleteJournalServiceName = "api/journal/delete";
                ResponseMessage responseFromInterModule = this.customerAdjustmentServiceManager.accountingInterModuleCommunication(deleteJournalServiceName,deleteJournalModel);
                if (responseFromInterModule.responseCode != 200) {
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = responseFromInterModule.message;
                    if (responseFromInterModule.message == null) {
                        responseMessage.message = MessageConstant.CUSTOMER_WRITE_OFF_DELETE_FAILED;
                    } else {
                        responseMessage.message = responseFromInterModule.message;
                    }
                    this.rollBack();
                    return responseMessage;
                }

                //(3)
                this.customerWriteOffDetailBllManager.deleteCustomerAdjustDetailByCustomerWriteOffID(customerWriteOffModel.getCustomerWriteOffID());
                if (Core.clientMessage.get().messageCode != null) {
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    if (Core.clientMessage.get().userMessage == null) {
                        responseMessage.message = MessageConstant.CUSTOMER_WRITE_OFF_DELETE_FAILED;
                    } else {
                        responseMessage.message = Core.clientMessage.get().userMessage;
                    }
                    this.rollBack();
                    return responseMessage;
                }
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.CUSTOMER_WRITE_OFF_DELETE_FAILED;
                this.rollBack();
                return responseMessage;
            }

            this.commit();
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
            responseMessage.message = MessageConstant.CUSTOMER_WRITE_OFF_DELETE_SUCCESSFULLY;

        } catch (Exception ex) {
            log.error("CustomerWriteOffServiceManager -> deleteCustomerWriteOff got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }

}
