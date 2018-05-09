/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 09-Apr-18
 * Time: 12:43 PM
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
import nybsys.tillboxweb.entities.Role;
import nybsys.tillboxweb.models.RoleModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class RoleBllManager extends BaseBll<Role> {

    private static final Logger log = LoggerFactory.getLogger(RoleBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(Role.class);
        Core.runTimeModelType.set(RoleModel.class);
    }

    public BllResponseMessage saveOrUpdate(RequestMessage requestMessage) throws Exception {
        BllResponseMessage bllResponseMessage = new BllResponseMessage();

        RoleModel reqRoleModel =
                Core.getRequestObject(requestMessage, RoleModel.class);

        Integer primaryKeyValue = reqRoleModel.getRoleID();
        RoleModel savedRoleModel = null, updatedRoleModel;
        try {

            if (primaryKeyValue == null || primaryKeyValue == 0) {
                // Save Code
                savedRoleModel = this.save(reqRoleModel);
                if (savedRoleModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "Role Save Successfully";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to Save Role";
                }
            } else {
                // Update Code
                updatedRoleModel = this.update(reqRoleModel);
                if (updatedRoleModel != null) {
                    Core.clientMessage.get().userMessage = "Role Update Successfully";
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to Update Role";
                }

                savedRoleModel = updatedRoleModel;
            }

        } catch (Exception ex) {
            log.error("RoleBllManager -> saveOrUpdate got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        bllResponseMessage.responseObject = savedRoleModel;
        bllResponseMessage.responseCode = Core.clientMessage.get().messageCode;
        bllResponseMessage.message = Core.clientMessage.get().message;

        return bllResponseMessage;
    }

    public List<RoleModel> search(RoleModel reqRoleModel) throws Exception {
        List<RoleModel> findRoleList;
        try {
            findRoleList = this.getAllByConditionWithActive(reqRoleModel);
            if (findRoleList.size() > 0) {
                Core.clientMessage.get().userMessage = "Find the request Role";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                Core.clientMessage.get().message = "Failed to find the requested Role";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("RoleBllManager -> search got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return findRoleList;
    }

    public Integer deleteByConditions(RequestMessage requestMessage) throws Exception {
        RoleModel req_RoleModel =
                Core.getRequestObject(requestMessage, RoleModel.class);
        Integer numberOfDeleteRow = 0;
        try {
            numberOfDeleteRow = this.deleteByConditions(req_RoleModel);
            if (numberOfDeleteRow > 0) {
                //Core.clientMessage.get().userMessage = "Successfully deleted the requested Role";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                Core.clientMessage.get().message = "Failed to deleted the requested Role";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("RoleBllManager -> deleteByConditions got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return numberOfDeleteRow;
    }


    public RoleModel inActive(RequestMessage requestMessage) throws Exception {
        RoleModel reqRoleModel =
                Core.getRequestObject(requestMessage, RoleModel.class);
        RoleModel _RoleModel = null;
        try {
            if (reqRoleModel != null) {
                _RoleModel = this.inActive(reqRoleModel);
                if (_RoleModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "Successfully inactive the requested Role";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to inactive the requested Role";
                }
            }

        } catch (Exception ex) {
            log.error("RoleBllManager -> inActive got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return _RoleModel;
    }


    public RoleModel delete(RoleModel reqRoleModel) throws Exception {
        RoleModel deletedRoleModel = null;
        try {
            if (reqRoleModel != null) {
                deletedRoleModel = this.softDelete(reqRoleModel);
                if (deletedRoleModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    //Core.clientMessage.get().userMessage = "Successfully deleted the requested Role";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to deleted the requested Role";
                }
            }

        } catch (Exception ex) {
            log.error("RoleBllManager -> delete got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return deletedRoleModel;
    }

    public RoleModel getByReqId(RoleModel reqRoleModel) throws Exception {
        Integer primaryKeyValue = reqRoleModel.getRoleID();
        RoleModel foundRoleModel = null;
        try {
            if (primaryKeyValue != null) {
                foundRoleModel = this.getById(primaryKeyValue);
                if (foundRoleModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    //Core.clientMessage.get().userMessage = "Get the requested Role successfully";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to the requested Role";
                }
            }

        } catch (Exception ex) {
            log.error("RoleBllManager -> getByID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return foundRoleModel;
    }
}