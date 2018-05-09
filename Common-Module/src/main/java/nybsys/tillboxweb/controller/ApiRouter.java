/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 22-Dec-17
 * Time: 10:50 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */
package nybsys.tillboxweb.controller;


import nybsys.tillboxweb.BaseController;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
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

    @Autowired
    private UserDefineSettingServiceManager userDefineSettingServiceManager;

    @Autowired
    private UserDefineSettingDetailServiceManager userDefineSettingDetailServiceManager;

    @Autowired
    private RememberNoteServiceManager rememberNoteServiceManager;

    @Autowired
    private CommonMessageServiceManager commonMessageServiceManager;

    @Autowired
    private AttachmentServiceManager attachmentServiceManager;
    
    @Autowired
    private AddressTypeServiceManager addressTypeServiceManager;
    
    @Autowired
    private  ContactTypeServiceManager contactTypeServiceManager;

    @Override
    public ResponseMessage getResponseMessage(String serviceName, RequestMessage requestMessage) {
        this.checkSecurityAndExecuteService(serviceName,requestMessage);
        //close session factory
        //this.closeSession();
        return this.responseMessage;
    }



    protected void executeServiceManager(String serviceName, RequestMessage requestMessage) {
        switch (serviceName) {

            case "api/commonModule/attachment/save":
                this.responseMessage = this.attachmentServiceManager.saveOrUpdate(requestMessage);
                log.info("CommonModule attachment->api/commonModule/attachment/save executed");
                break;

            case "api/commonModule/attachment/search":
                this.responseMessage = this.attachmentServiceManager.search(requestMessage);
                log.info("CommonModule ->api/commonModule/attachment/search executed");
                break;

            case "api/commonModule/commonMessage/save":
                this.responseMessage = this.commonMessageServiceManager.saveOrUpdate(requestMessage);
                log.info("CommonModule ->api/commonModule/commonMessage/save executed");
                break;

            case "api/commonModule/commonMessage/search":
                this.responseMessage = this.commonMessageServiceManager.search(requestMessage);
                log.info("CommonModule ->api/commonModule/commonMessage/search executed");
                break;

            case "api/commonModule/commonMessage/delete":
                this.responseMessage = this.commonMessageServiceManager.delete(requestMessage);
                log.info("CommonModule ->api/commonModule/commonMessage/delete executed");
                break;

            case "api/commonModule/commonMessage/inactive":
                this.responseMessage = this.commonMessageServiceManager.inActive(requestMessage);
                log.info("CommonModule ->api/commonModule/commonMessage/inactive executed");
                break;

            case "api/commonModule/userDefineSetting/save":
                this.responseMessage = this.userDefineSettingServiceManager.saveOrUpdateUserDefineSetting(requestMessage);
                log.info("CommonModule ->api/commonModule/userDefineSetting/save executed");
                break;

            case "api/commonModule/userDefineSetting/getAll":
                this.responseMessage = this.userDefineSettingServiceManager.getAllUserDefineSetting(requestMessage);
                log.info("api/commonModule/userDefineSetting/getAll executed");
                break;

            case "api/commonModule/userDefineSetting/search":
                this.responseMessage = this.userDefineSettingServiceManager.searchUserDefineSetting(requestMessage);
                log.info("UserDefineSetting module ->api/commonModule/userDefineSetting/search executed");
                break;

            case "api/commonModule/userDefineSetting/getByID":
                this.responseMessage = this.userDefineSettingServiceManager.getUserDefineSettingById(requestMessage);
                log.info("CommonModule ->api/commonModule/userDefineSetting/getByID executed");
                break;

            case "api/commonModule/userDefineSetting/delete":
                this.responseMessage = this.userDefineSettingServiceManager.deleteUserDefineSetting(requestMessage);
                log.info("CommonModule ->api/commonModule/delete executed");
                break;

            case "api/commonModule/userDefineSettingDetail/save":
                this.responseMessage = this.userDefineSettingDetailServiceManager.saveUserDefineSettingDetail(requestMessage);
                log.info("CommonModule ->api/commonModule/save executed");
                break;
            case "api/commonModule/userDefineSettingDetail/search":
                this.responseMessage = this.userDefineSettingDetailServiceManager.searchUserDefineSettingDetail(requestMessage);
                log.info("CommonModule ->api/commonModule/search executed");
                break;
            case "api/commonModule/userDefineSettingDetail/delete":
                this.responseMessage = this.userDefineSettingDetailServiceManager.deleteUserDefineSettingDetail(requestMessage);
                log.info("CommonModule ->api/commonModule/search executed");
                break;
            case "api/commonModule/rememberNote/save":
                this.responseMessage = this.rememberNoteServiceManager.save(requestMessage);
                log.info("CommonModule ->api/commonModule/search executed");
                break;
            case "api/commonModule/rememberNote/search":
                this.responseMessage = this.rememberNoteServiceManager.search(requestMessage);
                log.info("CommonModule ->api/commonModule/search executed");
                break;

            case "api/commonModule/rememberNote/delete":
                this.responseMessage = this.rememberNoteServiceManager.search(requestMessage);
                log.info("CommonModule ->api/commonModule/search executed");
                break;
                
            case "api/commonModule/addressType/save":
                this.responseMessage = this.addressTypeServiceManager.saveAddressType(requestMessage);
                log.info("Common Module -> api/commonModule/addressType/save executed");
                break;

            case "api/commonModule/addressType/search":
                this.responseMessage = this.addressTypeServiceManager.searchAddressType(requestMessage);
                log.info("Common Module -> api/commonModule/addressType/search executed");
                break;

            case "api/commonModule/addressType/getByID":
                this.responseMessage = this.addressTypeServiceManager.getAddressTypeByID(requestMessage);
                log.info("Common Module -> api/commonModule/addressType/getByID executed");
                break;
                
            case "api/commonModule/contactType/save":
                this.responseMessage = this.contactTypeServiceManager.saveContactType(requestMessage);
                log.info("Common Module -> api/commonModule/contactType/save executed");
                break;

            case "api/commonModule/contactType/search":
                this.responseMessage = this.contactTypeServiceManager.searchContactType(requestMessage);
                log.info("Common Module -> api/commonModule/contactType/search executed");
                break;

            case "api/commonModule/contactType/getByID":
                this.responseMessage = this.contactTypeServiceManager.getContactTypeByID(requestMessage);
                log.info("Common Module -> api/commonModule/contactType/getByID executed");
                break;

            default:
                log.warn("INVALID REQUEST");
        }
    }
}
