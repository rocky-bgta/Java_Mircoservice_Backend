/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 22-Dec-17
 * Time: 10:50 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */
package nybsys.tillboxweb.controller;


import nybsys.tillboxweb.Authorization;
import nybsys.tillboxweb.BaseController;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.MessageModel.SecurityReqMessage;
import nybsys.tillboxweb.MessageModel.SecurityResMessage;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.service.manager.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ApiRouter extends BaseController {


    private static final Logger log = LoggerFactory.getLogger(ApiRouter.class);
    private ResponseMessage responseMessage;
    private String dbName;

    @Autowired
    private Authorization authorization;

    @Autowired
    private AccessRightServiceManager accessRightServiceManager;

    @Autowired
    private AccessRightRoleMappingServiceManager accessRightRoleMappingServiceManager;

    @Autowired
    private RoleServiceManager roleServiceManager;

    @Autowired
    private PrivilegeServiceManager privilegeServiceManager;

    @Autowired
    private RolePrivilegeMappingServiceManager rolePrivilegeMappingServiceManager;

    @Override
    public ResponseMessage getResponseMessage(String serviceName, RequestMessage requestMessage) {

        SecurityResMessage securityResMessage;

        Core.messageId.set(requestMessage.brokerMessage.messageId);
        this.responseMessage = this.buildDefaultResponseMessage();

        SecurityReqMessage securityReqMessage = new SecurityReqMessage();
        securityReqMessage.messageId = requestMessage.brokerMessage.messageId;
        securityReqMessage.serviceUrl = requestMessage.brokerMessage.serviceName;
        securityReqMessage.token = requestMessage.token;

        securityResMessage = this.authorization.getUsersPermission(securityReqMessage);

        if(securityResMessage!=null && securityResMessage.isPermitted) {
            this.dbName = securityResMessage.businessDBName;
            this.selectDataBase(this.dbName);
            this.setDefaultBusinessValue(requestMessage);
            this.executeServiceManager(serviceName, requestMessage);
        }else {
            this.responseMessage.responseCode = TillBoxAppConstant.BAD_REQUEST_CODE;
            this.responseMessage.message = "Please provide valid token";
            this.responseMessage.errorMessage="Please provide valid token";
        }

        //close session factory
        this.closeSession();
        return this.responseMessage;
    }


    protected void executeServiceManager(String serviceName, RequestMessage requestMessage) {
        switch (serviceName) {

            case "api/AccessRight/save":
                this.responseMessage = this.accessRightServiceManager.saveOrUpdate(requestMessage);
                log.info("api/AccessRight/save executed");
                break;

            case "api/AccessRight/getAll":
                this.responseMessage = this.accessRightServiceManager.search(requestMessage);
                log.info("api/AccessRight/getAll executed");
                break;

            case "api/AccessRight/getByID":
                this.responseMessage = this.accessRightServiceManager.getById(requestMessage);
                log.info("api/AccessRight/getByID executed");
                break;

            case "api/accessRightRoleMapping/getAll":
                this.responseMessage = this.accessRightRoleMappingServiceManager.search(requestMessage);
                log.info("api/accessRightRoleMapping/getAll executed");
                break;

            case "api/accessRightRoleMapping/getByID":
                this.responseMessage = this.accessRightRoleMappingServiceManager.getById(requestMessage);
                log.info("api/accessRightRoleMapping/getByID executed");
                break;

            case "api/accessRightRoleMapping/save":
                this.responseMessage = this.accessRightRoleMappingServiceManager.saveOrUpdate(requestMessage);
                log.info("api/accessRightRoleMapping/save executed");
                break;

            case "api/role/getAll":
                this.responseMessage = this.roleServiceManager.search(requestMessage);
                log.info("api/role/getAll executed");
                break;

            case "api/role/getByID":
                this.responseMessage = this.roleServiceManager.getById(requestMessage);
                log.info("api/role/getByID executed");
                break;

            case "api/role/save":
                this.responseMessage = this.roleServiceManager.saveOrUpdate(requestMessage);
                log.info("api/role/save executed");
                break;

            case "api/privilege/getAll":
                this.responseMessage = this.privilegeServiceManager.search(requestMessage);
                log.info("api/privilege/getAll executed");
                break;

            case "api/privilege/getByID":
                this.responseMessage = this.privilegeServiceManager.getById(requestMessage);
                log.info("api/privilege/getByID executed");
                break;

            case "api/privilege/save":
                this.responseMessage = this.privilegeServiceManager.saveOrUpdate(requestMessage);
                log.info("api/privilege/save executed");
                break;

            case "api/rolePrivilegeMapping/getAll":
                this.responseMessage = this.rolePrivilegeMappingServiceManager.search(requestMessage);
                log.info("api/privilege/getAll executed");
                break;

            case "api/rolePrivilegeMapping/getByRoleID":
                this.responseMessage = this.rolePrivilegeMappingServiceManager.getById(requestMessage);
                log.info("api/privilege/getByID executed");
                break;

            case "api/rolePrivilegeMapping/save":
                this.responseMessage = this.rolePrivilegeMappingServiceManager.saveOrUpdate(requestMessage);
                log.info("api/privilege/save executed");
                break;

            default:
                log.error("INVALID REQUEST");
        }
    }
}
