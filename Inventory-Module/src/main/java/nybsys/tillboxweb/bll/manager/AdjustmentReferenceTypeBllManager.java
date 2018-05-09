/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 01-Mar-18
 * Time: 2:43 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.BaseBllManager;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.AdjustmentReferenceType;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.models.AdjustmentReferenceTypeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AdjustmentReferenceTypeBllManager extends BaseBll<AdjustmentReferenceType> implements BaseBllManager {

    private static final Logger log = LoggerFactory.getLogger(AdjustmentReferenceTypeBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(AdjustmentReferenceType.class);
        Core.runTimeModelType.set(AdjustmentReferenceTypeModel.class);
    }


    @Override
    public <M> M saveOrUpdate(RequestMessage requestMessage) throws Exception {
        AdjustmentReferenceTypeModel castRequestModel =
                Core.getRequestObject(requestMessage, AdjustmentReferenceTypeModel.class);
        Integer primaryKeyValue = castRequestModel.getAdjustmentReferenceTypeID();
        AdjustmentReferenceTypeModel processedModel = null;
        try {

            if (primaryKeyValue == null || primaryKeyValue == 0) {
                // Save Code
                processedModel = this.save(castRequestModel);
                if (processedModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "AdjustmentReferenceType Save Successfully";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to Save AdjustmentReferenceType";
                }
            } else {
                // Update Code
                processedModel = this.update(castRequestModel);
                if (processedModel != null) {
                    Core.clientMessage.get().userMessage = "AdjustmentReferenceType Update Successfully";
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to Update AdjustmentReferenceType";
                }
            }

        } catch (Exception ex) {
            log.error("AdjustmentReferenceTypeBllManager -> saveOrUpdate got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return (M) processedModel;
    }

    @Override
    public <M> List<M> search(RequestMessage requestMessage) throws Exception {
        AdjustmentReferenceTypeModel castRequestModel =
                Core.getRequestObject(requestMessage, AdjustmentReferenceTypeModel.class);

        List<AdjustmentReferenceTypeModel> resultingList;
        try {
            resultingList = this.getAllByConditions(castRequestModel);
            if (resultingList.size() > 0) {
                Core.clientMessage.get().userMessage = "Find the request AdjustmentReferenceType";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                Core.clientMessage.get().message = "Failed to find the requested AdjustmentReferenceType";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("AdjustmentReferenceTypeBllManager -> search got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return (List<M>) resultingList;
    }

    @Override
    public <M> List<M> getAllModels() throws Exception {
        List<AdjustmentReferenceTypeModel> resultingList;
        try {
            resultingList = this.getAll();
            if (resultingList.size() > 0) {
                Core.clientMessage.get().userMessage = "Get all AdjustmentReferenceType successfully";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                Core.clientMessage.get().message = "Failed to get AdjustmentReferenceType";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("AdjustmentReferenceTypeBllManager -> getAllModels got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return (List<M>) resultingList;
    }

    @Override
    public <M> Integer deleteByConditions(RequestMessage requestMessage) throws Exception {
        AdjustmentReferenceTypeModel castRequestModel =
                Core.getRequestObject(requestMessage, AdjustmentReferenceTypeModel.class);
        Integer numberOfDeleteRow = 0;
        try {
            numberOfDeleteRow = this.deleteByConditions(castRequestModel);
            if (numberOfDeleteRow > 0) {
                Core.clientMessage.get().userMessage = "Successfully deleted the requested AdjustmentReferenceType";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                Core.clientMessage.get().message = "Failed to deleted the requested AdjustmentReferenceType";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("AdjustmentReferenceTypeBllManager -> deleteByConditions got exception : " + ex.getMessage());
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
        AdjustmentReferenceTypeModel castRequestModel =
                Core.getRequestObject(requestMessage, AdjustmentReferenceTypeModel.class);
        AdjustmentReferenceTypeModel processedModel = null;
        try {
            if (castRequestModel != null) {
                processedModel = this.inActive(castRequestModel);
                if (processedModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "Successfully inactive the requested AdjustmentReferenceType";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to inactive the requested AdjustmentReferenceType";
                }
            }

        } catch (Exception ex) {
            log.error("AdjustmentReferenceTypeBllManager -> inActive got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return (M) processedModel;
    }

    @Override
    public <M> M delete(RequestMessage requestMessage) throws Exception {
        AdjustmentReferenceTypeModel castRequestModel =
                Core.getRequestObject(requestMessage, AdjustmentReferenceTypeModel.class);
        AdjustmentReferenceTypeModel processedModel = null;
        try {
            if (castRequestModel != null) {
                processedModel = this.softDelete(castRequestModel);
                if (processedModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "Successfully deleted the requested AdjustmentReferenceType";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to deleted the requested AdjustmentReferenceType";
                }
            }

        } catch (Exception ex) {
            log.error("AdjustmentReferenceTypeBllManager -> delete got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return (M) processedModel;
    }

    @Override
    public <M> M getByID(RequestMessage requestMessage) throws Exception {
        AdjustmentReferenceTypeModel castRequestModel =
                Core.getRequestObject(requestMessage, AdjustmentReferenceTypeModel.class);
        Integer primaryKeyValue = castRequestModel.getAdjustmentReferenceTypeID();
        AdjustmentReferenceTypeModel processedModel = null;
        try {
            if (primaryKeyValue != null) {
                processedModel = this.getById(primaryKeyValue);
                if (processedModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "Get the requested AdjustmentReferenceType successfully";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to the requested AdjustmentReferenceType";
                }
            }

        } catch (Exception ex) {
            log.error("AdjustmentReferenceTypeBllManager -> getByID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return (M) processedModel;
    }
}