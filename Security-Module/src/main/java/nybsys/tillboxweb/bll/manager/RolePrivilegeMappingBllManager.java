/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 09-Apr-18
 * Time: 12:44 PM
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
import nybsys.tillboxweb.entities.RolePrivilegeMapping;
import nybsys.tillboxweb.models.RolePrivilegeMappingModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class RolePrivilegeMappingBllManager extends BaseBll<RolePrivilegeMapping> {

    private static final Logger log = LoggerFactory.getLogger(RolePrivilegeMappingBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(RolePrivilegeMapping.class);
        Core.runTimeModelType.set(RolePrivilegeMappingModel.class);
    }

    public BllResponseMessage saveOrUpdate(RequestMessage requestMessage) throws Exception {
        BllResponseMessage bllResponseMessage = new BllResponseMessage();

        RolePrivilegeMappingModel reqRolePrivilegeMappingModel =
                Core.getRequestObject(requestMessage, RolePrivilegeMappingModel.class);

        Integer primaryKeyValue = reqRolePrivilegeMappingModel.getRolePrivilegeMappingID();
        RolePrivilegeMappingModel savedRolePrivilegeMappingModel = null, updatedRolePrivilegeMappingModel;

        try {

            if (primaryKeyValue == null || primaryKeyValue == 0) {
                // Save Code
                savedRolePrivilegeMappingModel = this.save(reqRolePrivilegeMappingModel);
                if (savedRolePrivilegeMappingModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "RolePrivilegeMapping Save Successfully";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to Save RolePrivilegeMapping";
                }
            } else {
                // Update Code
                updatedRolePrivilegeMappingModel = this.update(reqRolePrivilegeMappingModel);
                if (updatedRolePrivilegeMappingModel != null) {
                    Core.clientMessage.get().userMessage = "RolePrivilegeMapping Update Successfully";
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to Update RolePrivilegeMapping";
                }

                savedRolePrivilegeMappingModel = updatedRolePrivilegeMappingModel;
            }

        } catch (Exception ex) {
            log.error("RolePrivilegeMappingBllManager -> saveOrUpdate got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        bllResponseMessage.responseObject = savedRolePrivilegeMappingModel;
        bllResponseMessage.responseCode = Core.clientMessage.get().messageCode;
        bllResponseMessage.message = Core.clientMessage.get().message;

        return bllResponseMessage;
    }

    public List<RolePrivilegeMappingModel> search(RolePrivilegeMappingModel reqRolePrivilegeMappingModel) throws Exception {
        List<RolePrivilegeMappingModel> findRolePrivilegeMappingList;
        try {
            findRolePrivilegeMappingList = this.getAllByConditionWithActive(reqRolePrivilegeMappingModel);
            if (findRolePrivilegeMappingList.size() > 0) {
                Core.clientMessage.get().userMessage = "Find the request RolePrivilegeMapping";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                Core.clientMessage.get().message = "Failed to find the requested RolePrivilegeMapping";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("RolePrivilegeMappingBllManager -> search got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return findRolePrivilegeMappingList;
    }

    public Integer deleteByConditions(RequestMessage requestMessage) throws Exception {
        RolePrivilegeMappingModel req_RolePrivilegeMappingModel =
                Core.getRequestObject(requestMessage, RolePrivilegeMappingModel.class);
        Integer numberOfDeleteRow = 0;
        try {
            numberOfDeleteRow = this.deleteByConditions(req_RolePrivilegeMappingModel);
            if (numberOfDeleteRow > 0) {
                //Core.clientMessage.get().userMessage = "Successfully deleted the requested RolePrivilegeMapping";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                Core.clientMessage.get().message = "Failed to deleted the requested RolePrivilegeMapping";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("RolePrivilegeMappingBllManager -> deleteByConditions got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return numberOfDeleteRow;
    }


    public RolePrivilegeMappingModel inActive(RequestMessage requestMessage) throws Exception {
        RolePrivilegeMappingModel reqRolePrivilegeMappingModel =
                Core.getRequestObject(requestMessage, RolePrivilegeMappingModel.class);
        RolePrivilegeMappingModel _RolePrivilegeMappingModel = null;
        try {
            if (reqRolePrivilegeMappingModel != null) {
                _RolePrivilegeMappingModel = this.inActive(reqRolePrivilegeMappingModel);
                if (_RolePrivilegeMappingModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "Successfully inactive the requested RolePrivilegeMapping";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to inactive the requested RolePrivilegeMapping";
                }
            }

        } catch (Exception ex) {
            log.error("RolePrivilegeMappingBllManager -> inActive got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return _RolePrivilegeMappingModel;
    }


    public RolePrivilegeMappingModel delete(RolePrivilegeMappingModel reqRolePrivilegeMappingModel) throws Exception {
        RolePrivilegeMappingModel deletedRolePrivilegeMappingModel = null;
        try {
            if (reqRolePrivilegeMappingModel != null) {
                deletedRolePrivilegeMappingModel = this.softDelete(reqRolePrivilegeMappingModel);
                if (deletedRolePrivilegeMappingModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    //Core.clientMessage.get().userMessage = "Successfully deleted the requested RolePrivilegeMapping";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to deleted the requested RolePrivilegeMapping";
                }
            }

        } catch (Exception ex) {
            log.error("RolePrivilegeMappingBllManager -> delete got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return deletedRolePrivilegeMappingModel;
    }

    public RolePrivilegeMappingModel getByReqId(RolePrivilegeMappingModel reqRolePrivilegeMappingModel) throws Exception {
        Integer primaryKeyValue = reqRolePrivilegeMappingModel.getRolePrivilegeMappingID();
        RolePrivilegeMappingModel foundRolePrivilegeMappingModel = null;
        try {
            if (primaryKeyValue != null) {
                foundRolePrivilegeMappingModel = this.getById(primaryKeyValue);
                if (foundRolePrivilegeMappingModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    //Core.clientMessage.get().userMessage = "Get the requested RolePrivilegeMapping successfully";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to the requested RolePrivilegeMapping";
                }
            }

        } catch (Exception ex) {
            log.error("RolePrivilegeMappingBllManager -> getByID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return foundRolePrivilegeMappingModel;
    }
}