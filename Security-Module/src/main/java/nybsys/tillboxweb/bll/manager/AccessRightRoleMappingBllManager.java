/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 09-Apr-18
 * Time: 12:40 PM
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
import nybsys.tillboxweb.entities.AccessRightRoleMapping;
import nybsys.tillboxweb.models.AccessRightRoleMappingModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AccessRightRoleMappingBllManager extends BaseBll<AccessRightRoleMapping> {

    private static final Logger log = LoggerFactory.getLogger(AccessRightRoleMappingBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(AccessRightRoleMapping.class);
        Core.runTimeModelType.set(AccessRightRoleMappingModel.class);
    }

    public BllResponseMessage saveOrUpdate(RequestMessage requestMessage) throws Exception {
        BllResponseMessage bllResponseMessage = new BllResponseMessage();

        AccessRightRoleMappingModel reqAccessRightRoleMappingModel =
                Core.getRequestObject(requestMessage, AccessRightRoleMappingModel.class);

        Integer primaryKeyValue = reqAccessRightRoleMappingModel.getAccessRightRoleMappingID();
        AccessRightRoleMappingModel savedAccessRightRoleMappingModel = null, updatedAccessRightRoleMappingModel;

        try {

            if (primaryKeyValue == null || primaryKeyValue == 0) {
                // Save Code
                savedAccessRightRoleMappingModel = this.save(reqAccessRightRoleMappingModel);
                if (savedAccessRightRoleMappingModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "AccessRightRoleMapping Save Successfully";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to Save AccessRightRoleMapping";
                }
            } else {
                // Update Code
                updatedAccessRightRoleMappingModel = this.update(reqAccessRightRoleMappingModel);
                if (updatedAccessRightRoleMappingModel != null) {
                    Core.clientMessage.get().userMessage = "AccessRightRoleMapping Update Successfully";
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to Update AccessRightRoleMapping";
                }

                savedAccessRightRoleMappingModel = updatedAccessRightRoleMappingModel;
            }

        } catch (Exception ex) {
            log.error("AccessRightRoleMappingBllManager -> saveOrUpdate got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        bllResponseMessage.responseObject = savedAccessRightRoleMappingModel;
        bllResponseMessage.responseCode = Core.clientMessage.get().messageCode;
        bllResponseMessage.message = Core.clientMessage.get().message;

        return bllResponseMessage;
    }

    public List<AccessRightRoleMappingModel> search(AccessRightRoleMappingModel reqAccessRightRoleMappingModel) throws Exception {
        List<AccessRightRoleMappingModel> findAccessRightRoleMappingList;
        try {
            findAccessRightRoleMappingList = this.getAllByConditionWithActive(reqAccessRightRoleMappingModel);
            if (findAccessRightRoleMappingList!=null && findAccessRightRoleMappingList.size() > 0) {
                Core.clientMessage.get().userMessage = "Find the request AccessRightRoleMapping successful";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                Core.clientMessage.get().message = "Failed to find the requested AccessRightRoleMapping";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("AccessRightRoleMappingBllManager -> search got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return findAccessRightRoleMappingList;
    }

    public Integer deleteByConditions(RequestMessage requestMessage) throws Exception {
        AccessRightRoleMappingModel req_AccessRightRoleMappingModel =
                Core.getRequestObject(requestMessage, AccessRightRoleMappingModel.class);
        Integer numberOfDeleteRow = 0;
        try {
            numberOfDeleteRow = this.deleteByConditions(req_AccessRightRoleMappingModel);
            if (numberOfDeleteRow > 0) {
                //Core.clientMessage.get().userMessage = "Successfully deleted the requested AccessRightRoleMapping";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                Core.clientMessage.get().message = "Failed to deleted the requested AccessRightRoleMapping";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("AccessRightRoleMappingBllManager -> deleteByConditions got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return numberOfDeleteRow;
    }


    public AccessRightRoleMappingModel inActive(RequestMessage requestMessage) throws Exception {
        AccessRightRoleMappingModel reqAccessRightRoleMappingModel =
                Core.getRequestObject(requestMessage, AccessRightRoleMappingModel.class);
        AccessRightRoleMappingModel _AccessRightRoleMappingModel = null;
        try {
            if (reqAccessRightRoleMappingModel != null) {
                _AccessRightRoleMappingModel = this.inActive(reqAccessRightRoleMappingModel);
                if (_AccessRightRoleMappingModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "Successfully inactive the requested AccessRightRoleMapping";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to inactive the requested AccessRightRoleMapping";
                }
            }

        } catch (Exception ex) {
            log.error("AccessRightRoleMappingBllManager -> inActive got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return _AccessRightRoleMappingModel;
    }


    public AccessRightRoleMappingModel delete(AccessRightRoleMappingModel reqAccessRightRoleMappingModel) throws Exception {
        AccessRightRoleMappingModel deletedAccessRightRoleMappingModel = null;
        try {
            if (reqAccessRightRoleMappingModel != null) {
                deletedAccessRightRoleMappingModel = this.softDelete(reqAccessRightRoleMappingModel);
                if (deletedAccessRightRoleMappingModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    //Core.clientMessage.get().userMessage = "Successfully deleted the requested AccessRightRoleMapping";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to deleted the requested AccessRightRoleMapping";
                }
            }

        } catch (Exception ex) {
            log.error("AccessRightRoleMappingBllManager -> delete got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return deletedAccessRightRoleMappingModel;
    }

    public AccessRightRoleMappingModel getByReqId(AccessRightRoleMappingModel reqAccessRightRoleMappingModel) throws Exception {
        Integer primaryKeyValue = reqAccessRightRoleMappingModel.getAccessRightRoleMappingID();
        AccessRightRoleMappingModel foundAccessRightRoleMappingModel = null;
        try {
            if (primaryKeyValue != null) {
                foundAccessRightRoleMappingModel = this.getById(primaryKeyValue);
                if (foundAccessRightRoleMappingModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    //Core.clientMessage.get().userMessage = "Get the requested AccessRightRoleMapping successfully";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to the requested AccessRightRoleMapping";
                }
            }

        } catch (Exception ex) {
            log.error("AccessRightRoleMappingBllManager -> getByID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return foundAccessRightRoleMappingModel;
    }
}