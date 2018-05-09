/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 09-Apr-18
 * Time: 12:41 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.MessageModel.BllResponseMessage;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.PrivilegeMapping;
import nybsys.tillboxweb.models.PrivilegeMappingModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PrivilegeMappingBllManager extends BaseBll<PrivilegeMapping> {

    private static final Logger log = LoggerFactory.getLogger(PrivilegeMappingBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(PrivilegeMapping.class);
        Core.runTimeModelType.set(PrivilegeMappingModel.class);
    }

    public BllResponseMessage saveOrUpdate(RequestMessage requestMessage) throws Exception {
        BllResponseMessage bllResponseMessage = new BllResponseMessage();

        PrivilegeMappingModel reqPrivilegeMappingModel =
                Core.getRequestObject(requestMessage, PrivilegeMappingModel.class);

        Integer primaryKeyValue = reqPrivilegeMappingModel.getPrivilegeMappingID();
        PrivilegeMappingModel savedPrivilegeMappingModel = null, updatedPrivilegeMappingModel;
        try {

            if (primaryKeyValue == null || primaryKeyValue == 0) {
                // Save Code
                savedPrivilegeMappingModel = this.save(reqPrivilegeMappingModel);
                if (savedPrivilegeMappingModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "PrivilegeMapping Save Successfully";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to Save PrivilegeMapping";
                }
            } else {
                // Update Code
                updatedPrivilegeMappingModel = this.update(reqPrivilegeMappingModel);
                if (updatedPrivilegeMappingModel != null) {
                    Core.clientMessage.get().userMessage = "PrivilegeMapping Update Successfully";
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to Update PrivilegeMapping";
                }

                savedPrivilegeMappingModel = updatedPrivilegeMappingModel;
            }

        } catch (Exception ex) {
            log.error("PrivilegeMappingBllManager -> saveOrUpdate got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        bllResponseMessage.responseObject = savedPrivilegeMappingModel;
        bllResponseMessage.responseCode = Core.clientMessage.get().messageCode;
        bllResponseMessage.message = Core.clientMessage.get().message;

        return bllResponseMessage;
    }

    public List<PrivilegeMappingModel> search(PrivilegeMappingModel reqPrivilegeMappingModel) throws Exception {
        List<PrivilegeMappingModel> findPrivilegeMappingList;
        try {
            findPrivilegeMappingList = this.getAllByConditionWithActive(reqPrivilegeMappingModel);
            if (findPrivilegeMappingList.size() > 0) {
                Core.clientMessage.get().userMessage = "Find the request PrivilegeMapping";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                Core.clientMessage.get().message = "Failed to find the requested PrivilegeMapping";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("PrivilegeMappingBllManager -> search got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return findPrivilegeMappingList;
    }

    public Integer deleteByConditions(RequestMessage requestMessage) throws Exception {
        PrivilegeMappingModel req_PrivilegeMappingModel =
                Core.getRequestObject(requestMessage, PrivilegeMappingModel.class);
        Integer numberOfDeleteRow = 0;
        try {
            numberOfDeleteRow = this.deleteByConditions(req_PrivilegeMappingModel);
            if (numberOfDeleteRow > 0) {
                //Core.clientMessage.get().userMessage = "Successfully deleted the requested PrivilegeMapping";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                Core.clientMessage.get().message = "Failed to deleted the requested PrivilegeMapping";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("PrivilegeMappingBllManager -> deleteByConditions got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return numberOfDeleteRow;
    }


    public PrivilegeMappingModel inActive(RequestMessage requestMessage) throws Exception {
        PrivilegeMappingModel reqPrivilegeMappingModel =
                Core.getRequestObject(requestMessage, PrivilegeMappingModel.class);
        PrivilegeMappingModel _PrivilegeMappingModel = null;
        try {
            if (reqPrivilegeMappingModel != null) {
                _PrivilegeMappingModel = this.inActive(reqPrivilegeMappingModel);
                if (_PrivilegeMappingModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "Successfully inactive the requested PrivilegeMapping";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to inactive the requested PrivilegeMapping";
                }
            }

        } catch (Exception ex) {
            log.error("PrivilegeMappingBllManager -> inActive got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return _PrivilegeMappingModel;
    }


    public PrivilegeMappingModel delete(PrivilegeMappingModel reqPrivilegeMappingModel) throws Exception {
        PrivilegeMappingModel deletedPrivilegeMappingModel = null;
        try {
            if (reqPrivilegeMappingModel != null) {
                deletedPrivilegeMappingModel = this.softDelete(reqPrivilegeMappingModel);
                if (deletedPrivilegeMappingModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    //Core.clientMessage.get().userMessage = "Successfully deleted the requested PrivilegeMapping";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to deleted the requested PrivilegeMapping";
                }
            }

        } catch (Exception ex) {
            log.error("PrivilegeMappingBllManager -> delete got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return deletedPrivilegeMappingModel;
    }

    public PrivilegeMappingModel getByReqId(PrivilegeMappingModel reqPrivilegeMappingModel) throws Exception {
        Integer primaryKeyValue = reqPrivilegeMappingModel.getPrivilegeMappingID();
        PrivilegeMappingModel foundPrivilegeMappingModel = null;
        try {
            if (primaryKeyValue != null) {
                foundPrivilegeMappingModel = this.getById(primaryKeyValue);
                if (foundPrivilegeMappingModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    //Core.clientMessage.get().userMessage = "Get the requested PrivilegeMapping successfully";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to the requested PrivilegeMapping";
                }
            }

        } catch (Exception ex) {
            log.error("PrivilegeMappingBllManager -> getByID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return foundPrivilegeMappingModel;
    }
}