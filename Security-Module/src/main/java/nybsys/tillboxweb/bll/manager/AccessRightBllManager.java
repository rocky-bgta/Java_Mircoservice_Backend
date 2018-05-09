/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 09-Apr-18
 * Time: 12:34 PM
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
import nybsys.tillboxweb.entities.AccessRight;
import nybsys.tillboxweb.models.AccessRightModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AccessRightBllManager extends BaseBll<AccessRight> {

    private static final Logger log = LoggerFactory.getLogger(AccessRightBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(AccessRight.class);
        Core.runTimeModelType.set(AccessRightModel.class);
    }

    public BllResponseMessage saveOrUpdate(RequestMessage requestMessage) throws Exception {
        BllResponseMessage bllResponseMessage = new BllResponseMessage();

        AccessRightModel reqAccessRightModel =
                Core.getRequestObject(requestMessage, AccessRightModel.class);

        Integer primaryKeyValue = reqAccessRightModel.getAccessRightID();
        AccessRightModel savedAccessRightModel = null, updatedAccessRightModel;

        try {

            if (primaryKeyValue == null || primaryKeyValue == 0) {
                // Save Code
                savedAccessRightModel = this.save(reqAccessRightModel);
                if (savedAccessRightModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "AccessRight Save Successfully";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to Save AccessRight";
                }
            } else {
                // Update Code
                updatedAccessRightModel = this.update(reqAccessRightModel);
                if (updatedAccessRightModel != null) {
                    Core.clientMessage.get().userMessage = "AccessRight Update Successfully";
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to Update AccessRight";
                }

                savedAccessRightModel = updatedAccessRightModel;
            }

        } catch (Exception ex) {
            log.error("AccessRightBllManager -> saveOrUpdate got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        bllResponseMessage.responseObject = savedAccessRightModel;
        bllResponseMessage.responseCode = Core.clientMessage.get().messageCode;
        bllResponseMessage.message = Core.clientMessage.get().message;

        return bllResponseMessage;
    }

    public List<AccessRightModel> search(AccessRightModel reqAccessRightModel) throws Exception {
        List<AccessRightModel> findAccessRightList;
        try {
            findAccessRightList = this.getAllByConditionWithActive(reqAccessRightModel);
            if (findAccessRightList.size() > 0) {
                Core.clientMessage.get().userMessage = "Find the request AccessRight";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                Core.clientMessage.get().message = "Failed to find the requested AccessRight";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("AccessRightBllManager -> search got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return findAccessRightList;
    }

    public Integer deleteByConditions(RequestMessage requestMessage) throws Exception {
        AccessRightModel req_AccessRightModel =
                Core.getRequestObject(requestMessage, AccessRightModel.class);
        Integer numberOfDeleteRow = 0;
        try {
            numberOfDeleteRow = this.deleteByConditions(req_AccessRightModel);
            if (numberOfDeleteRow > 0) {
                //Core.clientMessage.get().userMessage = "Successfully deleted the requested AccessRight";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                Core.clientMessage.get().message = "Failed to deleted the requested AccessRight";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("AccessRightBllManager -> deleteByConditions got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return numberOfDeleteRow;
    }


    public AccessRightModel inActive(RequestMessage requestMessage) throws Exception {
        AccessRightModel reqAccessRightModel =
                Core.getRequestObject(requestMessage, AccessRightModel.class);
        AccessRightModel _AccessRightModel = null;
        try {
            if (reqAccessRightModel != null) {
                _AccessRightModel = this.inActive(reqAccessRightModel);
                if (_AccessRightModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "Successfully inactive the requested AccessRight";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to inactive the requested AccessRight";
                }
            }

        } catch (Exception ex) {
            log.error("AccessRightBllManager -> inActive got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return _AccessRightModel;
    }


    public AccessRightModel delete(AccessRightModel reqAccessRightModel) throws Exception {
        AccessRightModel deletedAccessRightModel = null;
        try {
            if (reqAccessRightModel != null) {
                deletedAccessRightModel = this.softDelete(reqAccessRightModel);
                if (deletedAccessRightModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    //Core.clientMessage.get().userMessage = "Successfully deleted the requested AccessRight";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to deleted the requested AccessRight";
                }
            }

        } catch (Exception ex) {
            log.error("AccessRightBllManager -> delete got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return deletedAccessRightModel;
    }

    public AccessRightModel getByReqId(AccessRightModel reqAccessRightModel) throws Exception {
        Integer primaryKeyValue = reqAccessRightModel.getAccessRightID();
        AccessRightModel foundAccessRightModel = null;
        try {
            if (primaryKeyValue != null) {
                foundAccessRightModel = this.getById(primaryKeyValue);
                if (foundAccessRightModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    //Core.clientMessage.get().userMessage = "Get the requested AccessRight successfully";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to the requested AccessRight";
                }
            }

        } catch (Exception ex) {
            log.error("AccessRightBllManager -> getByID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return foundAccessRightModel;
    }
}