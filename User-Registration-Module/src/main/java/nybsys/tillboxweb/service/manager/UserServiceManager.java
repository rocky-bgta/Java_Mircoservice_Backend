package nybsys.tillboxweb.service.manager;


import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.TillBoxWebBllManager.*;
import nybsys.tillboxweb.TillBoxWebModels.*;
import nybsys.tillboxweb.Utils.RandomUtil;
import nybsys.tillboxweb.Utils.TillBoxUtils;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.bll.manager.*;
import nybsys.tillboxweb.broker.client.CallBack;
import nybsys.tillboxweb.broker.client.PublisherForWorker;
import nybsys.tillboxweb.broker.client.SubscriberForWorker;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.constant.WorkerSubscriptionConstants;
import nybsys.tillboxweb.coreBllManager.*;
import nybsys.tillboxweb.coreModels.FinancialYearModel;
import nybsys.tillboxweb.models.*;
import nybsys.tillboxweb.models.BusinessModel;
import nybsys.tillboxweb.models.UserBusinessRightMapperModel;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.*;

import static nybsys.tillboxweb.constant.TillBoxAppConstant.NO_CONTENT_CODE;
import static nybsys.tillboxweb.constant.TillBoxAppConstant.SUCCESS_CODE;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserServiceManager extends BaseService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceManager.class);

    @Autowired
    UserBllManager userBllManager;

    @Autowired
    BusinessBllManager businessBllManager;

    @Autowired
    UserBusinessRightMapperBllManager userBusinessRightMapperBll;

    @Autowired
    GstSettingServiceManager gstSettingServiceManager;

    @Autowired
    UserInvitationBllManager userInvitationBllManager;

    @Autowired
    AccessRightBllManager accessRightBllManager;

    @Autowired
    private DefaultCOABllManager defaultCOABllManager;

    @Autowired
    private CoreFinancialYearBllManager coreFinancialYearBllManager;

    @Autowired
    private CoreAccountBllManager coreAccountBllManager;

    @Autowired
    private SessionBllManager sessionBllManager;

    @Autowired
    private RegistrationInvitationBllManager registrationInvitationBllManager;

    private List<DefaultCOAModel> defaultCOAModelList;

    ///============== bll for fixed table data =====================
    @Autowired
    private CoreAccountClassificationBllManager coreAccountClassificationBllManager;

    @Autowired
    private CoreAccountTypeBllManager coreAccountTypeBllManager;

    @Autowired
    private CoreCountryBllManager coreCountryBllManager;

    @Autowired
    private CoreCurrencyBllManager coreCurrencyBllManager;

    @Autowired
    private CoreCurrencyExchangeRateBllManager coreCurrencyExchangeRateBllManager;

    @Autowired
    private CoreCustomerCategoryBllManager coreCustomerCategoryBllManager;

    @Autowired
    private CoreCustomerTypeBllManager coreCustomerTypeBllManager;

    @Autowired
    private CoreProductCategoryBllManager coreProductCategoryBllManager;

    @Autowired
    private CoreProductTypeBllManager coreProductTypeBllManager;

    @Autowired
    private CoreSupplierCategoryBllManager coreSupplierCategoryBllManager;

    @Autowired
    private CoreSupplierTypeBllManager coreSupplierTypeBllManager;

    @Autowired
    private CoreVATRateBllManager coreVATRateBllManager;

    @Autowired
    private CoreVATSystemBllManager coreVATSystemBllManager;

    @Autowired
    private CoreDocumentNumberBllManager coreDocumentNumberBllManager;

    @Autowired
    private CoreCashFlowBllManager coreCashFlowBllManager;


    @Autowired
    private DefaultAccountClassificationBllManager defaultAccountClassificationBllManager;

    @Autowired
    private DefaultAccountTypeBllManager defaultAccountTypeBllManager;

    @Autowired
    private DefaultCountryBllManager defaultCountryBllManager;

    @Autowired
    private DefaultCurrencyBllManager defaultCurrencyBllManager;

    @Autowired
    private DefaultCurrencyExchangeRateBllManager defaultCurrencyExchangeRateBllManager;

    @Autowired
    private DefaultCustomerCategoryBllManager defaultCustomerCategoryBllManager;

    @Autowired
    private DefaultCustomerTypeBllManager defaultCustomerTypeBllManager;

    @Autowired
    private DefaultProductCategoryBllManager defaultProductCategoryBllManager;

    @Autowired
    private DefaultProductTypeBllManager defaultProductTypeBllManager;

    @Autowired
    private DefaultSupplierCategoryBllManager defaultSupplierCategoryBllManager;

    @Autowired
    private DefaultSupplierTypeBllManager defaultSupplierTypeBllManager;

    @Autowired
    private DefaultVATRateBllManager defaultVATRateBllManager;

    @Autowired
    private DefaultVATSystemBllManager defaultVATSystemBllManager;

    @Autowired
    private DefaultDocumentNumberBllManager defaultDocumentNumberBllManager;

    @Autowired
    private DefaultCashFlowBllManager defaultCashFlowBllManager;



    ///============== bll for fixed table data =====================






    public ResponseMessage signUpInvitation(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        RegistrationInvitationModel registrationInvitationModel;
        try {
            registrationInvitationModel = Core.getRequestObject(requestMessage, RegistrationInvitationModel.class);

            /*Set<ConstraintViolation<RegistrationInvitationModel>> violations = this.validator.validate(registrationInvitationModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            registrationInvitationModel = this.registrationInvitationBllManager.saveRegistrationInvitation(registrationInvitationModel);
            if (Core.clientMessage.get().messageCode != null) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (Core.clientMessage.get().userMessage != null) {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                } else {
                    responseMessage.message = MessageConstant.FAILED_TO_SEND_MAIL;
                }
                this.rollBack();
                return responseMessage;
            }


            this.commit();
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
            responseMessage.responseObj = registrationInvitationModel;
            responseMessage.message = MessageConstant.SUCCESSFULLY_SEND_MAIL;

        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.SIGN_UP_FAILED, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            log.error("UserServiceManager -> signUpUser got exception");
            this.rollBack();
        }
        return responseMessage;
    }

    public ResponseMessage signUpUser(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        BusinessModel businessModel;
        RegistrationInvitationModel registrationInvitationModel;
        UserModel userModel = new UserModel();
        VMTokenModel vmTokenModel;
        String dbName;
        FinancialYearModel defaultFinancialYearModel;
        try {
            vmTokenModel = Core.getRequestObject(requestMessage, VMTokenModel.class);

            /*Set<ConstraintViolation<VMTokenModel>> violations = this.validator.validate(vmTokenModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            //(1)get registration invitation
            registrationInvitationModel = this.registrationInvitationBllManager.getRegistrationInvitationByToken(vmTokenModel.getToken());
            if (Core.clientMessage.get().messageCode != null) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (Core.clientMessage.get().userMessage != null) {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                } else {
                    responseMessage.message = MessageConstant.SIGN_UP_FAILED;
                }
                return responseMessage;
            }

            //check logical validation
            // check token period is expired or not
            if (registrationInvitationModel.getExpireDate().before(new Date())) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_INVITED_USER;
                Core.clientMessage.get().userMessage = MessageConstant.FAILED_TO_GET_INVITED_USER;
                return responseMessage;
            }

            //create user model
            userModel.setUserID(registrationInvitationModel.getUserID());
            userModel.setPassword(registrationInvitationModel.getPassword());
            userModel.setCellPhone(registrationInvitationModel.getCellPhone());
            userModel.setSurname(registrationInvitationModel.getName());
            userModel.setName(registrationInvitationModel.getSurName());

            //(2) save user
            userModel = this.userBllManager.saveUser(userModel);
            if (Core.clientMessage.get().messageCode != null) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (Core.clientMessage.get().userMessage != null) {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                } else {
                    responseMessage.message = MessageConstant.SIGN_UP_FAILED;
                }
                this.rollBack();
                return responseMessage;
            }

            //user not exists
            // (3)create business DB
            dbName = TillBoxUtils.getDbName(userModel.getUserID());
            // if database is not exists
            if (!TillBoxUtils.isDatabaseExists(dbName)) {
                boolean creationFlag = TillBoxUtils.createDatabase(dbName);
                if (!creationFlag) {
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    responseMessage.message = MessageConstant.SIGN_UP_FAILED;
                    this.rollBack();
                    return responseMessage;
                }
            }

            //(4)save business
            businessModel = this.businessBllManager.saveBusinessByRegistrationInvitationModelAndDbName(registrationInvitationModel, dbName);
            if (Core.clientMessage.get().messageCode != null) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (Core.clientMessage.get().userMessage != null) {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                } else {
                    responseMessage.message = MessageConstant.SIGN_UP_FAILED;
                }
                this.rollBack();
                return responseMessage;
            }

            //(5)save user business right mapper
            userBusinessRightMapperBll.saveBusinessByUserModelAndDbName(userModel, businessModel.getBusinessID());
            if (Core.clientMessage.get().messageCode != null) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (Core.clientMessage.get().userMessage != null) {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                } else {
                    responseMessage.message = MessageConstant.SIGN_UP_FAILED;
                }
                this.rollBack();
                return responseMessage;
            }


            this.setDataIntoFixedTable(businessModel.getBusinessID(),dbName);
            // (6)save chart of account
            //===================================================
            // Then get the default COA
            // First switch dbConfig
            // Then insert DefaultCOA to respective database and

            // For the time being create default financial year




            //(7)remove registration invitation

            //select default dbConfig
            this.setDefaultDateBase();



            this.registrationInvitationBllManager.removeRegistrationInvitation(registrationInvitationModel);
            if (Core.clientMessage.get().messageCode != null) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (Core.clientMessage.get().userMessage != null) {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                } else {
                    responseMessage.message = MessageConstant.SIGN_UP_FAILED;
                }
                this.rollBack();
                return responseMessage;
            }

            this.commit();
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
            responseMessage.responseObj = vmTokenModel;
            responseMessage.token = requestMessage.token;
            responseMessage.message = MessageConstant.SIGN_UP_SUCCESSFULLY;

        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.SIGN_UP_FAILED, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            log.error("UserServiceManager -> signUpUser got exception");
            this.rollBack();
        }
        return responseMessage;
    }

    public ResponseMessage loginUser(RequestMessage requestMessage) {
        String sessionToken = "";
        UserModel userModel;
        VMLoginResponseModel vmLoginResponseModel = new VMLoginResponseModel();


        ResponseMessage responseMessage = Core.buildDefaultResponseMessage();
        try {
            userModel = Core.getRequestObject(requestMessage, UserModel.class);

            Set<ConstraintViolation<UserModel>> violations = this.validator.validate(userModel);

            // Server side validation
            boolean loginViolations = false;
//            for (ConstraintViolation<UserModel> item : violations)
//            {
//                String parameter = item.getPropertyPath().toString();
//                String userID = "userID",password="password";
//                boolean test = StringUtils.equals(parameter,userID);
//                boolean test1 = StringUtils.equals(parameter,password);
//                boolean e = test || test1;
//                if(e == true);
//                {
//                    loginViolations  = true;
//                }
//            }
           /* if (loginViolations) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.MODEL_VIOLATION, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            //select default dbConfig
            this.setDefaultDateBase();

            // check user exists
            if (this.userBllManager.isUserExist(userModel)) {
                List<BusinessModel> lstBusiness = new ArrayList<BusinessModel>();


                lstBusiness = this.businessBllManager.getBusinessListByUser(userModel.getUserID());
                if (lstBusiness.size() == 1) // single business
                {
                    //save session
                    sessionToken = this.sessionBllManager.saveSession(userModel.getUserID(), lstBusiness.get(0).getBusinessID(), lstBusiness.get(0).getBusinessDBName());
                    if (sessionToken != "") {

                        vmLoginResponseModel.userModel = userModel;
                        vmLoginResponseModel.lstBusinessModel = lstBusiness;
                        responseMessage.token = sessionToken;
                        responseMessage.businessID = lstBusiness.get(0).getBusinessID();

                        responseMessage.responseObj = vmLoginResponseModel;
                        responseMessage.responseCode = SUCCESS_CODE;
                        responseMessage.message = MessageConstant.LOGIN_SUCCESSFULLY;
                    }
                } else if (lstBusiness.size() > 1)// multiple business
                {
                    //save session - with business id =  0
                    sessionToken = this.sessionBllManager.saveSession(userModel.getUserID(), 0, "");
                    if (sessionToken != "") {

                        vmLoginResponseModel.userModel = userModel;
                        vmLoginResponseModel.lstBusinessModel = lstBusiness;
                        responseMessage.token = sessionToken;

                        responseMessage.responseObj = vmLoginResponseModel;
                        responseMessage.responseCode = SUCCESS_CODE;
                        responseMessage.message = MessageConstant.LOGIN_SUCCESSFULLY;
                    }
                } else {
                    responseMessage.responseCode = NO_CONTENT_CODE;
                    responseMessage.message = MessageConstant.BUSINESS_NOT_FOUND;
                }


            } else {
                responseMessage.responseCode = NO_CONTENT_CODE;
                responseMessage.message = MessageConstant.USER_OR_PASSWORD_NOT_MATCH;
            }

            //change dbConfig
            //this.selectDataBase(Core.userDataBase.get());

        } catch (Exception ex) {
            this.WriteExceptionLog(ex);
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.LOGIN_FAILED, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            ex.printStackTrace();
        }
        return responseMessage;
    }


    public ResponseMessage getAllUserByBusinessID(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<UserModel> lstUserModel = new ArrayList<>();
        try {

            if (requestMessage.businessID == null || requestMessage.businessID == 0) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SELECT_A_BUSINESS;
                Core.clientMessage.get().userMessage = MessageConstant.SELECT_A_BUSINESS;
                return responseMessage;
            }

            lstUserModel = this.userBllManager.getAllUserByBusinessID(requestMessage.businessID);

            responseMessage.responseObj = lstUserModel;
            if (Core.clientMessage.get().messageCode != null) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.FAILED_TO_GET_USER;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_GET_USER;
            }

        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            log.error("UserServiceManager -> getAllUserByBusinessID got exception");
        }
        return responseMessage;
    }

    public ResponseMessage getUserListActiveAndInactiveAndInvited(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<VMUserModel> lstVmUserModel = new ArrayList<>();
        UserModel userModel = new UserModel();
        VMUserBusinessModel vmUserBusinessModel = new VMUserBusinessModel();
        try {

            if (requestMessage.businessID == null || requestMessage.businessID == 0) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SELECT_A_BUSINESS;
                Core.clientMessage.get().userMessage = MessageConstant.SELECT_A_BUSINESS;
                return responseMessage;
            }

            //(1) get all active and inactive user
            List<UserBusinessRightMapperModel> lstUserBusinessRightMapperModel;
            lstUserBusinessRightMapperModel = this.userBusinessRightMapperBll.getAllActiveAndInactiveUserBusinessRightMapperByBusinessID(requestMessage.businessID);
            if (Core.clientMessage.get().messageCode != null) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.FAILED_TO_GET_USER;
                return responseMessage;
            }

            for (int index = 0; index < lstUserBusinessRightMapperModel.size(); index++) {
                UserBusinessRightMapperModel userBusinessRightMapperModel = new UserBusinessRightMapperModel();
                userBusinessRightMapperModel = lstUserBusinessRightMapperModel.get(index);

                //get user by userID
                userModel = this.userBllManager.getById(userBusinessRightMapperModel.getUserID());


                VMUserModel vmUserModel = new VMUserModel();
                vmUserModel.setAccessRightID(userBusinessRightMapperModel.getAccessRightID());
                vmUserModel.setFirstName(userBusinessRightMapperModel.getFirstName());
                vmUserModel.setLastName(userBusinessRightMapperModel.getLastName());
                vmUserModel.setUserModel(userModel);
                vmUserModel.setStatus(userBusinessRightMapperModel.getStatus());
                lstVmUserModel.add(vmUserModel);
            }

            //(2)get all invited user
            List<UserInvitationModel> lstUserInvitationModel;
            lstUserInvitationModel = userInvitationBllManager.getAllInvitedUserByBusinessID(requestMessage.businessID);
            Core.clientMessage.get().messageCode = null;


            for (int index = 0; index < lstUserInvitationModel.size(); index++) {
                UserInvitationModel userInvitationModel = new UserInvitationModel();
                userInvitationModel = lstUserInvitationModel.get(index);

                //get user by userID
                userModel = this.userBllManager.getById(userInvitationModel.getUserID());

                if (userModel == null) {
                    userModel = new UserModel();
                    userModel.setUserID(userInvitationModel.getUserID());
                    userModel.setName("");
                    userModel.setSurname("");
                    userModel.setCellPhone("");
                }
                VMUserModel vmUserModel = new VMUserModel();
                vmUserModel.setAccessRightID(userInvitationModel.getAccessRightID());
                vmUserModel.setFirstName(userInvitationModel.getFirstName());
                vmUserModel.setLastName(userInvitationModel.getLastName());
                vmUserModel.setUserModel(userModel);
                vmUserModel.setStatus(TillBoxAppEnum.UserStatus.Invited.get());
                lstVmUserModel.add(vmUserModel);
            }


            //(3)get business by businessID
            Core.clientMessage.get().messageCode = null;
            BusinessModel businessModel;
            businessModel = this.businessBllManager.getBusinessByBusinessID(requestMessage.businessID);
            if (Core.clientMessage.get().messageCode != null) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.FAILED_TO_GET_USER;
                return responseMessage;
            }

            //(4)get all access rights
            List<AccessRightModel> lstAccessRightModel = new ArrayList<>();

            //change db Config
            this.selectDataBase(Core.userDataBase.get());

            lstAccessRightModel = this.accessRightBllManager.getAllAccessRight();
            Core.clientMessage.get().messageCode = null;

            //(5)make isOwner is true
            for (int index = 0; index < lstVmUserModel.size(); index++) {
                if (StringUtils.equals(lstVmUserModel.get(index).getUserModel().getUserID().toString(), businessModel.getOwner().toString())) {
                    lstVmUserModel.get(index).setBusinessOwner(true);
                }
            }

            //(6)final object
            vmUserBusinessModel.lstVmUserModel = lstVmUserModel;
            vmUserBusinessModel.lstAccessRight = lstAccessRightModel;

            responseMessage.responseObj = vmUserBusinessModel;
            responseMessage.message = MessageConstant.SUCCESSFULLY_GET_USER;
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;


        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            log.error("UserServiceManager -> getUserListActiveAndInactiveAndInvited got exception");
        }
        return responseMessage;
    }

    public ResponseMessage editUser(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        UserBusinessRightMapperModel userBusinessRightMapperModel;
        try {

            if (requestMessage.businessID == null || requestMessage.businessID == 0) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SELECT_A_BUSINESS;
                Core.clientMessage.get().userMessage = MessageConstant.SELECT_A_BUSINESS;
                return responseMessage;
            }

            userBusinessRightMapperModel = Core.getRequestObject(requestMessage, UserBusinessRightMapperModel.class);
            userBusinessRightMapperModel.setBusinessID(requestMessage.businessID);

            /*Set<ConstraintViolation<UserBusinessRightMapperModel>> violations = this.validator.validate(userBusinessRightMapperModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            this.setDefaultDateBase();
            userBusinessRightMapperModel = this.userBllManager.updateUserBusinessRightMapper(userBusinessRightMapperModel);

            responseMessage.responseObj = userBusinessRightMapperModel;
            if (Core.clientMessage.get().messageCode != null) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.FAILED_TO_UPDATE_USER;
                this.rollBack();
            } else {
                this.commit();
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_UPDATE_USER;
            }

            //change dbConfig
            this.selectDataBase(Core.userDataBase.get());

        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            this.rollBack();
            log.error("UserServiceManager -> editUser got exception");
        }
        return responseMessage;
    }

    public ResponseMessage activateUser(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        UserModel userModel = new UserModel();
        try {

            if (requestMessage.businessID == null || requestMessage.businessID == 0) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SELECT_A_BUSINESS;
                Core.clientMessage.get().userMessage = MessageConstant.SELECT_A_BUSINESS;
                return responseMessage;
            }

            userModel = Core.getRequestObject(requestMessage, UserModel.class);

            /*Set<ConstraintViolation<UserModel>> violations = this.validator.validate(userModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            this.setDefaultDateBase();
            userModel = this.userBllManager.activeUser(userModel, requestMessage.businessID);

            responseMessage.responseObj = userModel;
            if (Core.clientMessage.get().messageCode != null) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.FAILED_TO_ACTIVE_USER;
                this.rollBack();
            } else {
                this.commit();
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_ACTIVE_USER;
            }

            //change dbConfig
            this.selectDataBase(Core.userDataBase.get());

        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            this.rollBack();
            log.error("UserServiceManager -> activateUser got exception");
        }
        return responseMessage;
    }

    public ResponseMessage inActivateUser(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        UserModel userModel = new UserModel();
        try {

            if (requestMessage.businessID == null || requestMessage.businessID == 0) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SELECT_A_BUSINESS;
                Core.clientMessage.get().userMessage = MessageConstant.SELECT_A_BUSINESS;
                return responseMessage;
            }

            userModel = Core.getRequestObject(requestMessage, UserModel.class);

             /*Set<ConstraintViolation<UserModel>> violations = this.validator.validate(userModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            this.setDefaultDateBase();
            userModel = this.userBllManager.inActiveUser(userModel, requestMessage.businessID);

            responseMessage.responseObj = userModel;
            if (Core.clientMessage.get().messageCode != null) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.FAILED_TO_IN_ACTIVE_USER;
                this.rollBack();
            } else {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_IN_ACTIVE_USER;
                this.commit();
            }

            //change dbConfig
            this.selectDataBase(Core.userDataBase.get());

        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            this.rollBack();
            log.error("UserServiceManager -> inActivateUser got exception");
        }
        return responseMessage;
    }

    public ResponseMessage checkInterCom(RequestMessage requestMessage) {
        MqttClient mqttClientGetUserList, mqttClientSaveUser, mqttClient2;
        CallBack callBackGetUserList, callBackSaveUser, callBack2;
        ResponseMessage responseMessage;// = new ResponseMessage();
        ResponseMessage responseMessageGetList, responseMessageSaveUser, responseMessage2;


        RequestMessage reqMessGetUserList, reqMessSaveUser, reqMessForWorker2;
        boolean workCompleteWithInAllowTime;
        try {

            Object lockObject = new Object();
            responseMessageGetList = Core.buildDefaultResponseMessage();
            responseMessageSaveUser = Core.buildDefaultResponseMessage();
            responseMessage2 = Core.buildDefaultResponseMessage();


            String pubTopic = WorkerSubscriptionConstants.WORKER_SECURITY_MODULE_TOPIC;

            this.barrier = TillBoxUtils.getBarrier(2, lockObject); //new CyclicBarrier(2, new WorkersStatus(lockObject));


            //Core.numberOfRequest.set(2);
            //Core.lockWorker.set(lockObject);

            //======================= Start of one ===========================
            // Get User List
            reqMessGetUserList = Core.getDefaultWorkerRequestMessage();
            reqMessGetUserList.brokerMessage.serviceName = "api/auth/interCom";
            SubscriberForWorker subForWorker = new SubscriberForWorker(reqMessGetUserList.brokerMessage.messageId, this.barrier);
            mqttClientGetUserList = subForWorker.subscribe();
            callBackGetUserList = subForWorker.getCallBack();
            PublisherForWorker pubForWorkerGetUserList = new PublisherForWorker(pubTopic, mqttClientGetUserList);
            pubForWorkerGetUserList.publishedMessageToWorker(reqMessGetUserList);
            //======================= End of one ===========================

            //======================= Start of two ===========================
            // Save user
            reqMessSaveUser = Core.getDefaultWorkerRequestMessage();
            reqMessSaveUser.brokerMessage.serviceName = "api/auth/interCom1";
            SubscriberForWorker subForWorker1 = new SubscriberForWorker(reqMessSaveUser.brokerMessage.messageId, this.barrier);
            log.info("MessageID2: " + reqMessSaveUser.brokerMessage.messageId);
            mqttClientSaveUser = subForWorker1.subscribe();
            callBackSaveUser = subForWorker1.getCallBack();
            PublisherForWorker pubForWorkerSaveUser = new PublisherForWorker(pubTopic, mqttClientSaveUser);
            pubForWorkerSaveUser.publishedMessageToWorker(reqMessSaveUser);
            //======================= End of two ===========================

            /*

            //======================= Start of three ===========================
            reqMessForWorker2 = Core.getDefaultWorkerRequestMessage();
            reqMessForWorker2.brokerMessage.serviceName = "api/auth/interCom2";
            SubscriberForWorker subForWorker2 = new SubscriberForWorker(reqMessSaveUser.brokerMessage.messageId);
            mqttClient2 = subForWorker2.subscribe();
            callBack2 = subForWorker2.getCallBack();
            PublisherForWorker pubForWorker2 = new PublisherForWorker(pubTopic,mqttClient2);
            pubForWorker2.publishedMessageToWorker(reqMessForWorker2);
            //======================= End of three ===========================

            */


            synchronized (lockObject) {
                responseMessage = Core.buildDefaultResponseMessage();
                long startTime = System.nanoTime();
                lockObject.wait(this.allowedTime);
                workCompleteWithInAllowTime = this.isResponseWithInAllowedTime(startTime);

                if (workCompleteWithInAllowTime) {

                    responseMessageGetList = callBackGetUserList.getResponseMessage();
                    responseMessageSaveUser = callBackSaveUser.getResponseMessage();
                    //log.info("List: " + responseMessageGetList.responseObj);
                    //log.info("Save object: "+ responseMessageSaveUser.responseObj);

                    responseMessage.message = "Inter module communication successful";
                    responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                    //responseMessage2 = callBack2.getResponseMessage();
                } else {
                    //timeout
                    log.info("Response time out");

                    reqMessSaveUser.brokerMessage.serviceName = "api/auth/interCom1RollBack";
                    pubForWorkerSaveUser.publishedMessageToWorker(reqMessSaveUser);

                    log.info("RollBack checkInterCom Operation");

                    responseMessage.message = "Inter module communication Failed";
                    responseMessage.responseCode = TillBoxAppConstant.UN_PROCESSABLE_REQUEST;
                }
            }


            this.closeBrokerClient(mqttClientGetUserList, reqMessGetUserList.brokerMessage.messageId);
            this.closeBrokerClient(mqttClientSaveUser, reqMessSaveUser.brokerMessage.messageId);
            //this.closeBrokerClient(mqttClient2,reqMessForWorker2.brokerMessage.messageId);


        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Exception from checkInterCom Module communication UserServiceManager");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
        }
        return responseMessage;
    }

    public ResponseMessage historyTest(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);

        String jsonString, entityClassPath, entityType;

        String ran = RandomUtil.generateActivationKey();

        UserModel userModel = new UserModel();
        userModel.setUserID("history" + ran + "@gmail.com");
        userModel.setName("Md Salahin");
        userModel.setSurname("Rocky");
        userModel.setCellPhone("34343344");
        userModel.setPassword("123456");
        //userModel.setStatus(1);


       /* UserRegistrationHistoryModel userRegistrationHistoryModel = new UserRegistrationHistoryModel();
        UserRegistrationHistoryModel getUserRegistrationHistoryModel;*/

        UserModel updateUserModel = new UserModel();
        updateUserModel.setUserID("history378@gmail.com");
        updateUserModel.setStatus(1);
        updateUserModel.setCellPhone("3423243432");
        updateUserModel.setName("Jabed");
        updateUserModel.setPassword("2eee333455");
        updateUserModel.setSurname("Hannan");

        UserModel whereCondition = new UserModel();
        whereCondition.setSurname("Rocky");

        UserModel modelToBeUpdate = new UserModel();
        modelToBeUpdate.setStatus(1);

        try {

            this.userBllManager.save(userModel);
            this.commit();


        } catch (Exception e) {
            log.error(e.getMessage());
            for (Throwable throwable : e.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
        }


        return responseMessage;
    }

    private void setDataIntoFixedTable(Integer businessID, String targetDB){
        List<DefaultAccountClassificationModel> defaultAccountClassificationModelList;
        List<DefaultAccountTypeModel> defaultAccountTypeModelList;
        List<DefaultCountryModel> defaultCountryModelList;
        List<DefaultCurrencyExchangeRateModel> defaultCurrencyExchangeRateModelList;
        List<DefaultCurrencyModel> defaultCurrencyModelList;
        List<DefaultCustomerCategoryModel> defaultCustomerCategoryModelList;
        List<DefaultCustomerTypeModel> defaultCustomerTypeModelList;
        List<DefaultProductCategoryModel> defaultProductCategoryModelList;
        List<DefaultProductTypeModel> defaultProductTypeModelList;
        List<DefaultSupplierCategoryModel> defaultSupplierCategoryModelList;
        List<DefaultSupplierTypeModel> defaultSupplierTypeModelList;
        List<DefaultVATRateModel> defaultVATRateModelList;
        List<DefaultVATSystemModel> defaultVATSystemModelList;
        List<DefaultDocumentNumberModel> defaultDocumentNumberModelList;
        List<DefaultCashFlowModel> defaultCashFlowModelList;


        FinancialYearModel defaultFinancialYearModel;
        List<DefaultCOAModel> defaultCOAModelList;

        try {
            defaultAccountClassificationModelList = this.defaultAccountClassificationBllManager.getAll();
            defaultAccountTypeModelList = this.defaultAccountTypeBllManager.getAll();
            defaultCountryModelList = this.defaultCountryBllManager.getAll();
            defaultCurrencyExchangeRateModelList = this.defaultCurrencyExchangeRateBllManager.getAll();
            defaultCurrencyModelList = this.defaultCurrencyBllManager.getAll();
            defaultCustomerCategoryModelList = this.defaultCustomerCategoryBllManager.getAll();
            defaultProductCategoryModelList = this.defaultProductCategoryBllManager.getAll();
            defaultProductTypeModelList = this.defaultProductTypeBllManager.getAll();
            defaultSupplierCategoryModelList = this.defaultSupplierCategoryBllManager.getAll();
            defaultSupplierTypeModelList = this.defaultSupplierTypeBllManager.getAll();
            defaultVATRateModelList = this.defaultVATRateBllManager.getAll();
            defaultVATSystemModelList = this.defaultVATSystemBllManager.getAll();
            defaultCOAModelList = this.defaultCOABllManager.getAll();
            defaultCustomerTypeModelList = this.defaultCustomerTypeBllManager.getAll();
            defaultDocumentNumberModelList = this.defaultDocumentNumberBllManager.getAll();
            defaultCashFlowModelList = this.defaultCashFlowBllManager.getAll();

            //set target database
            this.selectDataBase(targetDB);

            for (DefaultAccountClassificationModel defaultAccountClassificationModel : defaultAccountClassificationModelList ){
                this.coreAccountClassificationBllManager.save(defaultAccountClassificationModel);
            }

            for(DefaultAccountTypeModel defaultAccountTypeModel : defaultAccountTypeModelList){
                this.coreAccountTypeBllManager.save(defaultAccountTypeModel);
            }

            for(DefaultCountryModel defaultCountryModel : defaultCountryModelList){
                this.coreCountryBllManager.save(defaultCountryModel);
            }

            for(DefaultCurrencyExchangeRateModel defaultCurrencyExchangeRateModel : defaultCurrencyExchangeRateModelList){
                this.coreCurrencyExchangeRateBllManager.save(defaultCurrencyExchangeRateModel);
            }

            for(DefaultCurrencyModel defaultCurrencyModel : defaultCurrencyModelList){
                defaultCurrencyModel.setBusinessID(businessID);
                this.coreCurrencyBllManager.save(defaultCurrencyModel);
            }

            for(DefaultCustomerCategoryModel defaultCustomerCategoryModel : defaultCustomerCategoryModelList){
                defaultCustomerCategoryModel.setBusinessID(businessID);
                this.coreCustomerCategoryBllManager.save(defaultCustomerCategoryModel);
            }

            for(DefaultCustomerTypeModel defaultCustomerTypeModel : defaultCustomerTypeModelList){
                defaultCustomerTypeModel.setBusinessID(businessID);
                this.coreCustomerTypeBllManager.save(defaultCustomerTypeModel);
            }

            for(DefaultProductCategoryModel defaultProductCategoryModel : defaultProductCategoryModelList){
                defaultProductCategoryModel.setBusinessID(businessID);
                this.coreProductCategoryBllManager.save(defaultProductCategoryModel);
            }

            for(DefaultProductTypeModel defaultProductTypeModel : defaultProductTypeModelList){
                defaultProductTypeModel.setBusinessID(businessID);
                this.coreProductTypeBllManager.save(defaultProductTypeModel);
            }

            for(DefaultSupplierCategoryModel defaultSupplierCategoryModel : defaultSupplierCategoryModelList){
                defaultSupplierCategoryModel.setBusinessID(businessID);
                this.coreSupplierCategoryBllManager.save(defaultSupplierCategoryModel);
            }

            for(DefaultSupplierTypeModel defaultSupplierTypeModel : defaultSupplierTypeModelList){
                defaultSupplierTypeModel.setBusinessID(businessID);
                this.coreSupplierTypeBllManager.save(defaultSupplierTypeModel);
            }

            for(DefaultVATRateModel defaultVATRateModel : defaultVATRateModelList){
                defaultVATRateModel.setBusinessID(businessID);
                this.coreVATRateBllManager.save(defaultVATRateModel);
            }

            for(DefaultVATSystemModel defaultVATSystemModel : defaultVATSystemModelList){
                defaultVATSystemModel.setBusinessID(businessID);
                this.coreVATSystemBllManager.save(defaultVATSystemModel);
            }

            for(DefaultDocumentNumberModel defaultDocumentNumberModel : defaultDocumentNumberModelList){
                defaultDocumentNumberModel.setBusinessID(businessID);
                this.coreDocumentNumberBllManager.save(defaultDocumentNumberModel);
            }

            for(DefaultCashFlowModel defaultCashFlowModel : defaultCashFlowModelList){
                this.coreCashFlowBllManager.save(defaultCashFlowModel);
            }
            for (DefaultCOAModel defaultCOAModel : defaultCOAModelList) {
                defaultCOAModel.setBusinessID(businessID);
                this.coreAccountBllManager.save(defaultCOAModel);
            }

            // For the time being create default financial year
            defaultFinancialYearModel = new FinancialYearModel();
            defaultFinancialYearModel.setBusinessID(businessID);
            defaultFinancialYearModel.setFinancialYearID(1);
            defaultFinancialYearModel.setCreatedBy("NybSys");
            defaultFinancialYearModel.setStatus(1);
            defaultFinancialYearModel.setCreatedDate(new Date());
            defaultFinancialYearModel.setUpdatedBy("NybSys");
            defaultFinancialYearModel.setStartDate(new Date());
            Date currentDate = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentDate);
            cal.add(Calendar.DATE, 120);
            defaultFinancialYearModel.setEndDate(cal.getTime());
            defaultFinancialYearModel.setFinancialYearName("Golden 2018");
            defaultFinancialYearModel.setIsCurrentFinancialYear(true);

            this.coreFinancialYearBllManager.save(defaultFinancialYearModel);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
