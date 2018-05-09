/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 23-Feb-18
 * Time: 11:35 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.BaseBllManager;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.CommonMessage;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.models.CommonMessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CommonMessageBllManager extends BaseBll<CommonMessage> implements BaseBllManager {

    //private static final Logger log = LoggerFactory.getLogger(CommonMessageBllManager.class);
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());


    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(CommonMessage.class);
        Core.runTimeModelType.set(CommonMessageModel.class);
    }


    @Override
    public <M> M saveOrUpdate(RequestMessage requestMessage) throws Exception {
        CommonMessageModel castRequestModel =
                Core.getRequestObject(requestMessage, CommonMessageModel.class);
        Integer primaryKeyValue = castRequestModel.getCommonMessageID();
        CommonMessageModel processedModel = null;
        try {

            if (primaryKeyValue == null || primaryKeyValue == 0) {

                //============== Business Logic specific to this method only ==============
                CommonMessageModel whereCondition;
                List<CommonMessageModel> commonMessageModelList;
                whereCondition = new CommonMessageModel();
                Integer referenceType = castRequestModel.getReferenceType();
                whereCondition.setReferenceType(referenceType);
                commonMessageModelList = this.getAllByConditions(whereCondition);
                if(commonMessageModelList.size()>0){
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.PLEASE_CHANGE_COMMON_MESSAGE_DEFAULT_VALUE;
                    return (M) processedModel;
                }
                //============== Business Logic specific to this method only End ==============


                // Save Code
                processedModel = this.save(castRequestModel);
                if (castRequestModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = MessageConstant.SAVE_COMMON_MESSAGE_SUCCESSFUL;
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().userMessage = MessageConstant.FAILED_TO_SAVE_COMMON_MESSAGE;
                }
            } else {
                // Update Code
                processedModel = this.update(castRequestModel);
                if (castRequestModel != null) {
                    Core.clientMessage.get().userMessage = MessageConstant.UPDATE_COMMON_MESSAGE_SUCCESSFUL;
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().userMessage = MessageConstant.FAILED_TO_UPDATE_COMMON_MESSAGE;
                }
            }

        } catch (Exception ex) {
            log.error("CommonMessageBllManager -> saveOrUpdate got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return (M) processedModel;
    }

    @Override
    public <M> List<M> search(RequestMessage requestMessage) throws Exception {
        CommonMessageModel castRequestModel =
                Core.getRequestObject(requestMessage, CommonMessageModel.class);

        List<CommonMessageModel> resultingList;
        try {
            resultingList = this.getAllByConditions(castRequestModel);
            if (resultingList.size() > 0) {
                Core.clientMessage.get().userMessage = MessageConstant.FINED_THE_REQUESTED_COMMON_MESSAGE_SUCCESSFUL;
            } else {
                Core.clientMessage.get().userMessage = MessageConstant.FAILED_TO_THE_FINED_REQUESTED_COMMON_MESSAGE;
            }
        } catch (Exception ex) {
            log.error("CommonMessageBllManager -> search got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return (List<M>) resultingList;
    }

    @Override
    public <M> List<M> getAllModels() throws Exception {
        List<CommonMessageModel> resultingList;
        try {
            resultingList = this.getAll();
            if (resultingList.size() > 0) {
                Core.clientMessage.get().userMessage = MessageConstant.GET_ALL_COMMON_MESSAGE_SUCCESSFUL;
            } else {
                Core.clientMessage.get().userMessage = MessageConstant.FAILED_TO_GET_ALL_COMMON_MESSAGE;
            }
        } catch (Exception ex) {
            log.error("CommonMessageBllManager -> getAllModels got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return (List<M>) resultingList;
    }

    @Override
    public <M> Integer deleteByConditions(RequestMessage requestMessage) throws Exception {
        CommonMessageModel castRequestModel =
                Core.getRequestObject(requestMessage, CommonMessageModel.class);
        Integer numberOfDeleteRow = 0;
        try {
            numberOfDeleteRow = this.deleteByConditions(castRequestModel);
            if (numberOfDeleteRow > 0) {
                Core.clientMessage.get().userMessage = MessageConstant.INACTIVE_COMMON_MESSAGE;
            } else {
                Core.clientMessage.get().userMessage = MessageConstant.FAILED_TO_INACTIVE_COMMON_MESSAGE;
            }
        } catch (Exception ex) {
            log.error("CommonMessageBllManager -> deleteByConditions got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return numberOfDeleteRow;
    }

    @Override
    public <M> Integer inactiveByConditions(RequestMessage requestMessage) throws Exception {
        return null;
    }

    @Override
    public <M> M inActive(RequestMessage requestMessage) throws Exception {
        CommonMessageModel castRequestModel =
                Core.getRequestObject(requestMessage, CommonMessageModel.class);
        CommonMessageModel processedModel = null;
        try {
            if (castRequestModel != null) {
                processedModel = this.inActive(castRequestModel);
                if (castRequestModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = MessageConstant.INACTIVE_COMMON_MESSAGE;
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().userMessage = MessageConstant.FAILED_TO_INACTIVE_COMMON_MESSAGE;
                }
            }

        } catch (Exception ex) {
            log.error("CommonMessageBllManager -> inActive got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return (M) processedModel;
    }

    @Override
    public <M> M delete(RequestMessage requestMessage) throws Exception {
        CommonMessageModel castRequestModel =
                Core.getRequestObject(requestMessage, CommonMessageModel.class);
        CommonMessageModel processedModel = null;
        try {
            if (castRequestModel != null) {
                processedModel = this.softDelete(castRequestModel);
                if (processedModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = MessageConstant.DELETE_COMMON_MESSAGE_SUCCESSFUL;
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_DELETE_COMMON_MESSAGE;
                }
            }

        } catch (Exception ex) {
            log.error("CommonMessageBllManager -> delete got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return (M) processedModel;
    }

    @Override
    public <M> M getByID(RequestMessage requestMessage) throws Exception {
        CommonMessageModel castRequestModel =
                Core.getRequestObject(requestMessage, CommonMessageModel.class);
        Integer primaryKeyValue = castRequestModel.getCommonMessageID();
        CommonMessageModel processedModel = null;
        try {
            if (primaryKeyValue != null) {
                processedModel = this.getById(primaryKeyValue);
                if (processedModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = MessageConstant.GET_REQUESTED_COMMON_MESSAGE_SUCCESSFUL;
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().userMessage = MessageConstant.FAILED_TO_GET_REQUESTED_COMMON_MESSAGE;
                }
            }

        } catch (Exception ex) {
            log.error("CommonMessageBllManager -> getByID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return (M) processedModel;
    }

}
