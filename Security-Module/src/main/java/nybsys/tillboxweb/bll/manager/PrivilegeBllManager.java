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
import nybsys.tillboxweb.entities.Privilege;
import nybsys.tillboxweb.models.PrivilegeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PrivilegeBllManager extends BaseBll<Privilege> {

    private static final Logger log = LoggerFactory.getLogger(PrivilegeBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(Privilege.class);
        Core.runTimeModelType.set(PrivilegeModel.class);
    }

    public BllResponseMessage saveOrUpdate(RequestMessage requestMessage) throws Exception {
        BllResponseMessage bllResponseMessage = new BllResponseMessage();

        PrivilegeModel reqPrivilegeModel =
                Core.getRequestObject(requestMessage, PrivilegeModel.class);

        Integer primaryKeyValue = reqPrivilegeModel.getPrivilegeID();
        PrivilegeModel savedPrivilegeModel = null, updatedPrivilegeModel;
        try {

            if (primaryKeyValue == null || primaryKeyValue == 0) {
                // Save Code
                savedPrivilegeModel = this.save(reqPrivilegeModel);
                if (savedPrivilegeModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "Privilege Save Successfully";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to Save Privilege";
                }
            } else {
                // Update Code
                updatedPrivilegeModel = this.update(reqPrivilegeModel);
                if (updatedPrivilegeModel != null) {
                    Core.clientMessage.get().userMessage = "Privilege Update Successfully";
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to Update Privilege";
                }

                savedPrivilegeModel = updatedPrivilegeModel;
            }

        } catch (Exception ex) {
            log.error("PrivilegeBllManager -> saveOrUpdate got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        bllResponseMessage.responseObject = savedPrivilegeModel;
        bllResponseMessage.responseCode = Core.clientMessage.get().messageCode;
        bllResponseMessage.message = Core.clientMessage.get().message;

        return bllResponseMessage;
    }

    public List<PrivilegeModel> search(PrivilegeModel reqPrivilegeModel) throws Exception {
        List<PrivilegeModel> findPrivilegeList;
        try {
            findPrivilegeList = this.getAllByConditionWithActive(reqPrivilegeModel);
            if (findPrivilegeList.size() > 0) {
                Core.clientMessage.get().userMessage = "Find the request Privilege";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                Core.clientMessage.get().message = "Failed to find the requested Privilege";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("PrivilegeBllManager -> search got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return findPrivilegeList;
    }

    public Integer deleteByConditions(RequestMessage requestMessage) throws Exception {
        PrivilegeModel req_PrivilegeModel =
                Core.getRequestObject(requestMessage, PrivilegeModel.class);
        Integer numberOfDeleteRow = 0;
        try {
            numberOfDeleteRow = this.deleteByConditions(req_PrivilegeModel);
            if (numberOfDeleteRow > 0) {
                //Core.clientMessage.get().userMessage = "Successfully deleted the requested Privilege";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                Core.clientMessage.get().message = "Failed to deleted the requested Privilege";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("PrivilegeBllManager -> deleteByConditions got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return numberOfDeleteRow;
    }


    public PrivilegeModel inActive(RequestMessage requestMessage) throws Exception {
        PrivilegeModel reqPrivilegeModel =
                Core.getRequestObject(requestMessage, PrivilegeModel.class);
        PrivilegeModel _PrivilegeModel = null;
        try {
            if (reqPrivilegeModel != null) {
                _PrivilegeModel = this.inActive(reqPrivilegeModel);
                if (_PrivilegeModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "Successfully inactive the requested Privilege";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to inactive the requested Privilege";
                }
            }

        } catch (Exception ex) {
            log.error("PrivilegeBllManager -> inActive got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return _PrivilegeModel;
    }


    public PrivilegeModel delete(PrivilegeModel reqPrivilegeModel) throws Exception {
        PrivilegeModel deletedPrivilegeModel = null;
        try {
            if (reqPrivilegeModel != null) {
                deletedPrivilegeModel = this.softDelete(reqPrivilegeModel);
                if (deletedPrivilegeModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    //Core.clientMessage.get().userMessage = "Successfully deleted the requested Privilege";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to deleted the requested Privilege";
                }
            }

        } catch (Exception ex) {
            log.error("PrivilegeBllManager -> delete got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return deletedPrivilegeModel;
    }

    public PrivilegeModel getByReqId(PrivilegeModel reqPrivilegeModel) throws Exception {
        Integer primaryKeyValue = reqPrivilegeModel.getPrivilegeID();
        PrivilegeModel foundPrivilegeModel = null;
        try {
            if (primaryKeyValue != null) {
                foundPrivilegeModel = this.getById(primaryKeyValue);
                if (foundPrivilegeModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    //Core.clientMessage.get().userMessage = "Get the requested Privilege successfully";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to the requested Privilege";
                }
            }

        } catch (Exception ex) {
            log.error("PrivilegeBllManager -> getByID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return foundPrivilegeModel;
    }
}