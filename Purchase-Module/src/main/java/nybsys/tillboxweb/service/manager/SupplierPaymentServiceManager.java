/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 27-Feb-18
 * Time: 5:30 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.BaseServiceManager;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.MessageModel.BllResponseMessage;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.bll.manager.SupplierPaymentBllManager;
import nybsys.tillboxweb.bll.manager.SupplierPaymentDetailBllManager;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.coreBllManager.CoreJournalBllManager;
import nybsys.tillboxweb.coreEnum.ReferenceType;
import nybsys.tillboxweb.coreModels.JournalModel;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.models.SupplierPaymentDetailModel;
import nybsys.tillboxweb.models.SupplierPaymentModel;
import nybsys.tillboxweb.models.VMSupplierPaymentModel;
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
public class SupplierPaymentServiceManager extends BaseService implements BaseServiceManager {

    private static final Logger log = LoggerFactory.getLogger(SupplierPaymentServiceManager.class);

    @Autowired
    private SupplierPaymentBllManager supplierPaymentBllManager;

    @Autowired
    private SupplierPaymentDetailBllManager supplierPaymentDetailBllManager;

    @Autowired
    private CoreJournalBllManager coreJournalBllManager;


    @Override
    public ResponseMessage saveOrUpdate(RequestMessage requestMessage) {
        ResponseMessage responseMessage = this.getDefaultResponseMessage();
        BllResponseMessage bllResponseMessage;
        try {
            bllResponseMessage = this.supplierPaymentBllManager.saveOrUpdate(requestMessage);
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
            log.error("SupplierPaymentServiceManager -> saveOrUpdate got exception");
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

        ResponseMessage responseMessage;
        SupplierPaymentModel supplierPaymentWhereConditionModel;
        List<SupplierPaymentModel> supplierPaymentModelList;
        List<SupplierPaymentDetailModel> supplierPaymentDetailModelList;
        SupplierPaymentDetailModel supplierPaymentDetailWhereConditionModel;


        supplierPaymentWhereConditionModel = Core.getRequestObject(requestMessage, SupplierPaymentModel.class);


        List<VMSupplierPaymentModel> vmSupplierPaymentModelList = new ArrayList<>();
        VMSupplierPaymentModel vmSupplierPaymentModel = null;


        Integer id;
        try {
            responseMessage = Core.buildDefaultResponseMessage();

            supplierPaymentModelList = this.supplierPaymentBllManager.getAllByConditions(supplierPaymentWhereConditionModel);


            if (supplierPaymentModelList.size() > 0 && supplierPaymentModelList != null) {

                for (SupplierPaymentModel item : supplierPaymentModelList) {
                    id = item.getSupplierPaymentID();
                    supplierPaymentDetailWhereConditionModel = new SupplierPaymentDetailModel();
                    supplierPaymentDetailWhereConditionModel.setSupplierPaymentID(id);
                    supplierPaymentDetailWhereConditionModel.setStatus(TillBoxAppEnum.Status.Active.get());
                    supplierPaymentDetailModelList = this.supplierPaymentDetailBllManager.getAllByConditions(supplierPaymentDetailWhereConditionModel);

                    if (supplierPaymentDetailModelList != null) {
                        vmSupplierPaymentModel = new VMSupplierPaymentModel();
                        vmSupplierPaymentModel.supplierPaymentModel = item;
                        vmSupplierPaymentModel.supplierPaymentDetailModelList = supplierPaymentDetailModelList;
                    }
                    vmSupplierPaymentModelList.add(vmSupplierPaymentModel);
                }

                responseMessage.responseObj = vmSupplierPaymentModelList;
                responseMessage.message = "search requested Supplier Payment successful";
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

            } else {
                responseMessage.responseObj = requestMessage.requestObj;
                responseMessage.errorMessage = "Failed to find the Requested Supplier Payment";
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }

        } catch (Exception ex) {
            log.error("ProductAdjustmentServiceManager -> searchProductAdjustment got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            this.WriteExceptionLog(ex);
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage delete(RequestMessage requestMessage) {
        ResponseMessage responseMessage;
        SupplierPaymentModel supplierPaymentWhereConditionModel;
        List<SupplierPaymentModel> supplierPaymentModelList;
        SupplierPaymentDetailModel supPayDetailModelWhereCondition;
        List<SupplierPaymentDetailModel> supplierPaymentDetailModelList;

        supplierPaymentWhereConditionModel = Core.getRequestObject(requestMessage, SupplierPaymentModel.class);
        List<VMSupplierPaymentModel> vmSupplierPaymentModelList = new ArrayList<>();


        // TODO: update journal table and  payment status change of correspoinding table
        JournalModel journalModelForDelete = new JournalModel();
        List<JournalModel> journalModelList;

        Integer id,referenceID,referenceType;
        try {
            responseMessage = Core.buildDefaultResponseMessage();

            supplierPaymentModelList = this.supplierPaymentBllManager.getAllByConditions(supplierPaymentWhereConditionModel);

            if (supplierPaymentModelList.size() > 0 && supplierPaymentModelList != null) {

                for (SupplierPaymentModel supplierPaymentModel : supplierPaymentModelList) {
                    id = supplierPaymentModel.getSupplierPaymentID();
                    supPayDetailModelWhereCondition = new SupplierPaymentDetailModel();
                    supPayDetailModelWhereCondition.setSupplierPaymentID(id);
                    supplierPaymentDetailModelList = this.supplierPaymentDetailBllManager.getAllByConditions(supPayDetailModelWhereCondition);
                    for(SupplierPaymentDetailModel supPayDetailModel: supplierPaymentDetailModelList){
                        this.supplierPaymentDetailBllManager.softDelete(supPayDetailModel);
                    }
                    this.supplierPaymentBllManager.softDelete(supplierPaymentModel);

                    // Delete Journal
                    referenceID = supplierPaymentModel.getSupplierPaymentID();
                    referenceType = ReferenceType.SupplierPayment.get();
                    journalModelForDelete.setReferenceID(referenceID);
                    journalModelForDelete.setStatus(TillBoxAppEnum.Status.Active.get());
                    journalModelForDelete.setReferenceType(referenceType);
                    journalModelList = this.coreJournalBllManager.getAllByConditions(journalModelForDelete);
                    for(JournalModel journalModel: journalModelList){
                        this.coreJournalBllManager.softDelete(journalModel);
                    }
                }

                responseMessage.responseObj = vmSupplierPaymentModelList;
                responseMessage.message = "Delete the Requested Supplier Payment successful";
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

            } else {
                responseMessage.responseObj = requestMessage.requestObj;
                responseMessage.errorMessage = "Failed to delete the Requested Supplier Payment";
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }

        } catch (Exception ex) {
            log.error("ProductAdjustmentServiceManager -> searchProductAdjustment got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            this.WriteExceptionLog(ex);
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage inActive(RequestMessage requestMessage) {
       /* ResponseMessage responseMessage = this.getDefaultResponseMessage();
        Object resultObject;
        try {
            resultObject = this.supplierPaymentBllManager.inActive(requestMessage);
            if (Core.clientMessage.get().messageCode == TillBoxAppConstant.SUCCESS_CODE) {
                responseMessage.responseObj = resultObject;
                responseMessage.message = Core.clientMessage.get().userMessage;
                this.commit();
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                //responseMessage.message = MessageConstant.FAILED_TO_DELETE_COMMON_MESSAGE;
                this.rollBack();
            }
        } catch (Exception e) {
            log.error("SupplierPaymentServiceManager -> delete got exception");
            this.rollBack();
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(e);
        }

        return responseMessage;*/
        return null;
    }

    @Override
    public ResponseMessage getById(RequestMessage requestMessage) {
        ResponseMessage responseMessage = this.getDefaultResponseMessage();
        SupplierPaymentModel reqSupplierPaymentModel = Core.getRequestObject(requestMessage, SupplierPaymentModel.class);
        SupplierPaymentModel resultSupplierPaymentModel;
        List<SupplierPaymentDetailModel> supplierPaymentDetailModelList = null;
        SupplierPaymentDetailModel supplierPaymentDetailWhereConditionModel;
        VMSupplierPaymentModel vmSupplierPaymentModel;
        try {

            if (reqSupplierPaymentModel != null) {
                resultSupplierPaymentModel = this.supplierPaymentBllManager.getById(reqSupplierPaymentModel.getSupplierPaymentID(), TillBoxAppEnum.Status.Active.get());
                if (resultSupplierPaymentModel != null) {
                    supplierPaymentDetailWhereConditionModel = new SupplierPaymentDetailModel();
                    supplierPaymentDetailWhereConditionModel.setSupplierPaymentID(resultSupplierPaymentModel.getSupplierPaymentID());
                    supplierPaymentDetailModelList = this.supplierPaymentDetailBllManager.getAllByConditions(supplierPaymentDetailWhereConditionModel);
                }
                vmSupplierPaymentModel = new VMSupplierPaymentModel();
                vmSupplierPaymentModel.supplierPaymentModel = resultSupplierPaymentModel;
                vmSupplierPaymentModel.supplierPaymentDetailModelList = supplierPaymentDetailModelList;

                responseMessage.responseObj = vmSupplierPaymentModel;
                responseMessage.message = "Find the requested Supplier Payment successfully";
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

            } else {
                responseMessage.responseObj = requestMessage.requestObj;
                responseMessage.message = "Failed to find the requested Supplier Payment";
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception e) {
            log.error("SupplierPaymentServiceManager -> getByID got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(e);
        }
        return responseMessage;
    }
}