/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 09-Apr-18
 * Time: 12:55 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.BaseServiceManager;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.MessageModel.BllResponseMessage;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import nybsys.tillboxweb.models.RolePrivilegeMappingModel;
import nybsys.tillboxweb.bll.manager.RolePrivilegeMappingBllManager;

import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class RolePrivilegeMappingServiceManager extends BaseService implements BaseServiceManager {

    private static final Logger log = LoggerFactory.getLogger(RolePrivilegeMappingServiceManager.class);

    @Autowired
    private RolePrivilegeMappingBllManager rolePrivilegeMappingBllManager;

    @Override
    public ResponseMessage saveOrUpdate(RequestMessage requestMessage) {
        ResponseMessage responseMessage = this.getDefaultResponseMessage();
        BllResponseMessage bllResponseMessage;
        try {
            bllResponseMessage = this.rolePrivilegeMappingBllManager.saveOrUpdate(requestMessage);
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
            log.error("RolePrivilegeMappingServiceManager -> saveOrUpdate got exception");
            this.rollBack();
            if (Core.clientMessage.get().userMessage != null)
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, Core.clientMessage.get().userMessage, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            else
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(e);
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage search(RequestMessage requestMessage) {
        ResponseMessage responseMessage = this.getDefaultResponseMessage();
        List<RolePrivilegeMappingModel> finedRolePrivilegeMappingList;
        try {
           /* RolePrivilegeMappingModel reqRolePrivilegeMappingModel =
                    Core.getRequestObject(requestMessage, RolePrivilegeMappingModel.class);*/
            finedRolePrivilegeMappingList = this.rolePrivilegeMappingBllManager.getAll();
            if (finedRolePrivilegeMappingList!=null && finedRolePrivilegeMappingList.size()>0) {
                responseMessage.responseObj = finedRolePrivilegeMappingList;
                responseMessage.message = "Find the all RolePrivilegeMapping successful";
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = Core.clientMessage.get().message;
            }
        } catch (Exception e) {
            log.error("RolePrivilegeMappingServiceManager -> search got exception");

            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(e);
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage delete(RequestMessage requestMessage) {
        ResponseMessage responseMessage = this.getDefaultResponseMessage();
        RolePrivilegeMappingModel deletedRolePrivilegeMappingModel;
        try {
            RolePrivilegeMappingModel reqRolePrivilegeMappingModel =
                    Core.getRequestObject(requestMessage, RolePrivilegeMappingModel.class);

            deletedRolePrivilegeMappingModel = this.rolePrivilegeMappingBllManager.delete(reqRolePrivilegeMappingModel);
            if (Core.clientMessage.get().messageCode == TillBoxAppConstant.SUCCESS_CODE) {
                responseMessage.responseObj = deletedRolePrivilegeMappingModel;
                responseMessage.message = Core.clientMessage.get().userMessage;
                this.commit();
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = Core.clientMessage.get().message;
                this.rollBack();
            }
        } catch (Exception e) {
            log.error("RolePrivilegeMappingServiceManager -> delete got exception");
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
            RolePrivilegeMappingModel reqRolePrivilegeMappingModel =
                    Core.getRequestObject(requestMessage, RolePrivilegeMappingModel.class);
            resultObject = this.rolePrivilegeMappingBllManager.inActive(requestMessage);
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
            log.error("RolePrivilegeMappingServiceManager -> delete got exception");
            this.rollBack();
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(e);
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage getById(RequestMessage requestMessage) {
        ResponseMessage responseMessage = this.getDefaultResponseMessage();
        RolePrivilegeMappingModel findRolePrivilegeMappingModel;
        try {
            RolePrivilegeMappingModel reqRolePrivilegeMappingModel =
                    Core.getRequestObject(requestMessage, RolePrivilegeMappingModel.class);
            findRolePrivilegeMappingModel = this.rolePrivilegeMappingBllManager.getByReqId(reqRolePrivilegeMappingModel);
            if (Core.clientMessage.get().messageCode == TillBoxAppConstant.SUCCESS_CODE) {
                responseMessage.responseObj = findRolePrivilegeMappingModel;
                responseMessage.message = "Find the requested RolePrivilegeMapping successful";
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = "Faild to find the requested RolePrivilegeMapping";
            }
        } catch (Exception e) {
            log.error("RolePrivilegeMappingServiceManager -> getById got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(e);
        }
        return responseMessage;
    }
}