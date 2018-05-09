/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 12-Mar-18
 * Time: 6:11 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.BaseServiceManager;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.MessageModel.BllResponseMessage;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.bll.manager.CustomerReceiveBllManager;
import nybsys.tillboxweb.bll.manager.CustomerReceiveDetailBllManager;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.coreBllManager.CoreJournalBllManager;
import nybsys.tillboxweb.coreEnum.ReferenceType;
import nybsys.tillboxweb.coreModels.JournalModel;
import nybsys.tillboxweb.models.CustomerReceiveDetailModel;
import nybsys.tillboxweb.models.CustomerReceiveModel;
import nybsys.tillboxweb.models.VMCustomerReceive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CustomerReceiveServiceManager extends BaseService implements BaseServiceManager {

    private static final Logger log = LoggerFactory.getLogger(CustomerReceiveServiceManager.class);

    @Autowired
    private CustomerReceiveBllManager customerReceiveBllManager;

    @Autowired
    private CustomerReceiveDetailBllManager customerReceiveDetailBllManager;

    @Autowired
    private CoreJournalBllManager coreJournalBllManager;

    @Override
    public ResponseMessage saveOrUpdate(RequestMessage requestMessage) {
        ResponseMessage responseMessage = this.getDefaultResponseMessage();
        BllResponseMessage bllResponseMessage;
        try {
            bllResponseMessage = this.customerReceiveBllManager.saveOrUpdate(requestMessage);
            if (bllResponseMessage.responseCode == TillBoxAppConstant.SUCCESS_CODE) {
                responseMessage.responseObj = bllResponseMessage.responseObject;
                responseMessage.responseCode = Core.clientMessage.get().messageCode;
                responseMessage.message = Core.clientMessage.get().userMessage;
                this.commit();
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = Core.clientMessage.get().message;
                this.rollBack();
            }
        } catch (Exception e) {
            log.error("CustomerReceiveServiceManager -> saveOrUpdate got exception");
            this.rollBack();
            if(Core.clientMessage.get().userMessage!=null)
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj,Core.clientMessage.get().userMessage, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            else
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(e);
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage search(RequestMessage requestMessage) {
        ResponseMessage responseMessage = this.getDefaultResponseMessage();
        List<CustomerReceiveModel> finedCustomerReceiveList;

        VMCustomerReceive vmCustomerReceive;
        List<VMCustomerReceive> vmCustomerReceiveList = new ArrayList<>();

        CustomerReceiveDetailModel whereCondition = new CustomerReceiveDetailModel();
        List<CustomerReceiveDetailModel> customerReceiveDetailModelList;

        try {
            CustomerReceiveModel reqCustomerReceiveModel =
                    Core.getRequestObject(requestMessage, CustomerReceiveModel.class);
            finedCustomerReceiveList = this.customerReceiveBllManager.search(reqCustomerReceiveModel);

            for(CustomerReceiveModel customerReceiveModel: finedCustomerReceiveList){
                whereCondition.setCustomerReceiveID(customerReceiveModel.getCustomerReceiveID());
                customerReceiveDetailModelList = this.customerReceiveDetailBllManager.getAllByConditionWithActive(whereCondition);
                vmCustomerReceive = new VMCustomerReceive();
                vmCustomerReceive.customerReceiveModel = customerReceiveModel;
                vmCustomerReceive.customerReceiveDetailModelList = customerReceiveDetailModelList;
                vmCustomerReceiveList.add(vmCustomerReceive);
            }

            if (Core.clientMessage.get().messageCode == TillBoxAppConstant.SUCCESS_CODE) {
                responseMessage.responseObj = vmCustomerReceiveList;
                responseMessage.message = "Find the request CustomerReceive"; //Core.wrapperModel.get().userMessage;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = Core.clientMessage.get().message;
            }
        } catch (Exception e) {
            log.error("CustomerReceiveServiceManager -> search got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(e);
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage delete(RequestMessage requestMessage) {
        ResponseMessage responseMessage;

        CustomerReceiveModel customerWhereCondition;
        CustomerReceiveDetailModel cusDetailWhereCondition;
        List<CustomerReceiveModel> customerReceiveModelList;
        List<CustomerReceiveDetailModel> customerReceiveDetailModelList;

        customerWhereCondition = Core.getRequestObject(requestMessage, CustomerReceiveModel.class);

        JournalModel journalModelForDelete = new JournalModel();
        List<JournalModel> journalModelList;

        Integer id,referenceID,referenceType;

        try {

            responseMessage = this.getDefaultResponseMessage();

            customerReceiveModelList = this.customerReceiveBllManager.getAllByConditions(customerWhereCondition);

            if (customerReceiveModelList.size() > 0 && customerReceiveModelList != null) {

                for (CustomerReceiveModel customerReceiveModel : customerReceiveModelList) {
                    id = customerReceiveModel.getCustomerReceiveID();
                    cusDetailWhereCondition = new CustomerReceiveDetailModel();
                    cusDetailWhereCondition.setCustomerReceiveID(id);
                    customerReceiveDetailModelList = this.customerReceiveDetailBllManager.getAllByConditions(cusDetailWhereCondition);
                    for (CustomerReceiveDetailModel customerReceiveDetailModel : customerReceiveDetailModelList) {
                        this.customerReceiveDetailBllManager.softDelete(customerReceiveDetailModel);
                    }
                    this.customerReceiveBllManager.softDelete(customerReceiveModel);

                    // Delete Journal
                    referenceID = customerReceiveModel.getCustomerReceiveID();
                    referenceType = ReferenceType.CustomerReceipt.get();
                    journalModelForDelete.setReferenceID(referenceID);
                    journalModelForDelete.setStatus(TillBoxAppEnum.Status.Active.get());
                    journalModelForDelete.setReferenceType(referenceType);
                    journalModelList = this.coreJournalBllManager.getAllByConditions(journalModelForDelete);
                    for (JournalModel journalModel : journalModelList) {
                        this.coreJournalBllManager.softDelete(journalModel);
                    }
                }


                responseMessage.responseObj = requestMessage.requestObj;
                responseMessage.message = "Delete the Requested Customer Receive successful";
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                this.commit();

            }else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = Core.clientMessage.get().message;
                this.rollBack();
            }

        } catch (Exception e) {
            log.error("CustomerReceiveServiceManager -> delete got exception");
            this.rollBack();
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(e);
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage inActive(RequestMessage requestMessage) {
        ResponseMessage responseMessage = this.getDefaultResponseMessage();
        Object resultObject;
        try {
            CustomerReceiveModel reqCustomerReceiveModel =
                    Core.getRequestObject(requestMessage, CustomerReceiveModel.class);
            resultObject = this.customerReceiveBllManager.inActive(requestMessage);
            if (Core.clientMessage.get().messageCode == TillBoxAppConstant.SUCCESS_CODE) {
                responseMessage.responseObj = resultObject;
                responseMessage.message = Core.clientMessage.get().userMessage;
                this.commit();
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = Core.clientMessage.get().message;
                this.rollBack();
            }
        } catch (Exception e) {
            log.error("CustomerReceiveServiceManager -> delete got exception");
            this.rollBack();
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(e);
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage getById(RequestMessage requestMessage) {
        ResponseMessage responseMessage = this.getDefaultResponseMessage();

        CustomerReceiveModel reqCustomerReceiveModel = Core.getRequestObject(requestMessage, CustomerReceiveModel.class);
        CustomerReceiveModel resultCustomerReceiveModel;
        List<CustomerReceiveDetailModel> customerReceiveDetailModelList = null;
        CustomerReceiveDetailModel cusRecDetailModelWhereCondition;
        VMCustomerReceive vmCustomerReceive;
        try {

            if (reqCustomerReceiveModel != null) {
                resultCustomerReceiveModel = this.customerReceiveBllManager.getById(reqCustomerReceiveModel.getCustomerReceiveID(), TillBoxAppEnum.Status.Active.get());
                if (resultCustomerReceiveModel != null) {
                    cusRecDetailModelWhereCondition = new CustomerReceiveDetailModel();
                    cusRecDetailModelWhereCondition.setCustomerReceiveID(resultCustomerReceiveModel.getCustomerReceiveID());
                    customerReceiveDetailModelList = this.customerReceiveDetailBllManager.getAllByConditions(cusRecDetailModelWhereCondition);
                }
                vmCustomerReceive = new VMCustomerReceive();
                vmCustomerReceive.customerReceiveModel = resultCustomerReceiveModel;
                vmCustomerReceive.customerReceiveDetailModelList = customerReceiveDetailModelList;

                responseMessage.responseObj = vmCustomerReceive;
                responseMessage.message = "Find the requested Customer Receive successfully";
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

            } else {
                responseMessage.responseObj = requestMessage.requestObj;
                responseMessage.message = "Failed to find the requested Customer Receive";
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception e) {
            log.error("CustomerReceiveServiceManager -> getByID got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(e);
        }
        return responseMessage;
    }
}