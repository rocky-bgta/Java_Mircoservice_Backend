package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.Utils.TillBoxUtils;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.bll.manager.AccessRightBllManager;
import nybsys.tillboxweb.bll.manager.BusinessBllManager;
import nybsys.tillboxweb.bll.manager.SessionBllManager;
import nybsys.tillboxweb.bll.manager.UserBusinessRightMapperBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.coreEnum.AccessRight;
import nybsys.tillboxweb.models.BusinessModel;
import nybsys.tillboxweb.models.UserBusinessRightMapperModel;
import nybsys.tillboxweb.models.VMBusinessAccessRightModel;
import nybsys.tillboxweb.models.VMUserBusinessModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class BusinessServiceManager extends BaseService {

    private static final Logger log = LoggerFactory.getLogger(Core.class);
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @Autowired
    private BusinessBllManager businessBllManager;

    @Autowired
    private UserBusinessRightMapperBllManager userBusinessRightMapperBll;

    private SessionBllManager sessionBllManager = new SessionBllManager();

    private UserBusinessRightMapperBllManager userBusinessRightMapperBllManager;
    private AccessRightBllManager accessRightBllManager = new AccessRightBllManager();

    public ResponseMessage save(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        BusinessModel businessModel = new BusinessModel();
        try {
            if (requestMessage.userID == null || requestMessage.userID == "") {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.LOG_IN;
                Core.clientMessage.get().userMessage = MessageConstant.LOG_IN;
                return responseMessage;
            }

            businessModel = Core.getRequestObject(requestMessage, BusinessModel.class);

            // Set<ConstraintViolation<BusinessModel>> violations = this.validator.validate(businessModel);
            // for (ConstraintViolation<BusinessModel> violation : violations) {
            //     log.error(violation.getMessage());
            //  }

            //select default dbConfig
            //this.setDefaultDateBase();

            //(1)
            if (!IsBusinessExist(businessModel)) {

                //(2)create business DB
                int max = 9999;
                int min = 1000;
                String dbName;
                dbName = TillBoxUtils.getDbName(requestMessage.userID);
                do {
                    Random random = new Random();
                    int randomValue = random.nextInt((max - min) + 1) + min;
                    dbName += String.valueOf(randomValue);
                } while (TillBoxUtils.isDatabaseExists(dbName));

                boolean creationFlag = TillBoxUtils.createDatabase(dbName);
                if (!creationFlag) {
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    responseMessage.message = MessageConstant.BUSINESS_SAVE_FAILED;
                    this.rollBack();
                    return responseMessage;
                }


                //(3)
                businessModel.setBusinessDBName(dbName);
                businessModel.setOwner(requestMessage.userID);
                businessModel = this.businessBllManager.saveOrUpdateBusiness(businessModel);

                //(4)
                //Save user right mapping
                UserBusinessRightMapperModel userBusinessRightMapperModel = new UserBusinessRightMapperModel();
                userBusinessRightMapperModel.setBusinessID(businessModel.getBusinessID());
                userBusinessRightMapperModel.setAccessRightID(AccessRight.Administrator.get());
                userBusinessRightMapperModel.setUserID(businessModel.getOwner());
                userBusinessRightMapperModel.setBusinessStatus(TillBoxAppEnum.Status.Active.get());
                this.userBusinessRightMapperBll.saveOrUpdate(userBusinessRightMapperModel);
                if (Core.clientMessage.get().messageCode == null) {
                    responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                    responseMessage.message = MessageConstant.BUSINESS_SAVE_SUCCESSFULLY;
                    this.commit();
                } else {
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    if (Core.clientMessage.get().userMessage != null) {
                        responseMessage.message = Core.clientMessage.get().userMessage;
                    } else {
                        responseMessage.message = MessageConstant.BUSINESS_SAVE_FAILED;
                    }
                    this.rollBack();
                }
            } else {
                responseMessage.message = MessageConstant.DUPLICATE_BUSINESS;
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                this.rollBack();
            }


            responseMessage.responseObj = businessModel;
        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            this.rollBack();
            log.error("BusinessServiceManager -> save got exception");
        }
        return responseMessage;
    }


    public boolean IsBusinessExist(BusinessModel businessModel) throws Exception {

        BusinessModel searBusinessModel = new BusinessModel();
        searBusinessModel.setBusinessName(businessModel.getBusinessName());

        List<BusinessModel> businessList = new ArrayList<BusinessModel>();
        try {
            businessList = this.businessBllManager.getAllByConditions(searBusinessModel);
        } catch (Exception ex) {
            this.WriteExceptionLog(ex);
            log.error("BusinessServiceManager -> IsBusinessExist got exception");
            throw ex;
        }

        if (businessList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public ResponseMessage getByID(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        BusinessModel businessModel;// = new BusinessModel();
        try {
            businessModel = Core.getRequestObject(requestMessage, BusinessModel.class);

            //select default dbConfig
            //this.setDefaultDateBase();

            businessModel = this.businessBllManager.getById(businessModel.getBusinessID());

            //change dbConfig
            //this.selectDataBase(Core.userDataBase.get());

            responseMessage.responseObj = businessModel;
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            log.error("BusinessServiceManager -> getByID got exception");
        }
        return responseMessage;
    }

    public ResponseMessage search(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        BusinessModel businessModel = new BusinessModel();
        List<BusinessModel> lstBusinessModel;
        try {
            businessModel = Core.getRequestObject(requestMessage, BusinessModel.class);

            //select default dbConfig
            //this.setDefaultDateBase();

            lstBusinessModel = this.businessBllManager.getAllByConditions(businessModel);

            //change dbConfig
            //this.selectDataBase(Core.userDataBase.get());

            responseMessage.responseObj = lstBusinessModel;
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            log.error("BusinessServiceManager -> search got exception");
        }
        return responseMessage;
    }

    public ResponseMessage getBusinessByUserID(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        VMBusinessAccessRightModel vmBusinessAccessRightModel = new VMBusinessAccessRightModel();
        List<BusinessModel> lstBusinessModel;
        try {
            if (requestMessage.userID == null || requestMessage.userID == "") {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SELECT_A_BUSINESS;
                Core.clientMessage.get().userMessage = MessageConstant.SELECT_A_BUSINESS;
                return responseMessage;
            }

            //select default dbConfig
            //this.setDefaultDateBase();

            lstBusinessModel = this.businessBllManager.getBusinessListByUser(requestMessage.userID);
            vmBusinessAccessRightModel.lstBusinessModel = lstBusinessModel;
          //  change dbConfig
            this.selectDataBase(Core.userDataBase.get());
            vmBusinessAccessRightModel.lstAccessRight = this.accessRightBllManager.getAll();


            responseMessage.responseObj = vmBusinessAccessRightModel;
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            log.error("BusinessServiceManager -> getBusinessByUserID got exception");
        }
        return responseMessage;
    }

    public ResponseMessage selectBusiness(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        BusinessModel businessModel;
        UserBusinessRightMapperModel userBusinessRightMapperModel;
        try {
            if (requestMessage.userID == null || requestMessage.userID == "") {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SELECT_A_BUSINESS;
                Core.clientMessage.get().userMessage = MessageConstant.SELECT_A_BUSINESS;
                return responseMessage;
            }

            businessModel = Core.getRequestObject(requestMessage, BusinessModel.class);


            //select default dbConfig
            //this.setDefaultDateBase();

            //check user has access or not
            userBusinessRightMapperModel = this.userBusinessRightMapperBll.getUserBusinessRightMapperByUserIDAndBusinessID(requestMessage.userID, businessModel.getBusinessID());

            if (userBusinessRightMapperModel != null) {

                businessModel = this.businessBllManager.getById(businessModel.getBusinessID(), TillBoxAppEnum.Status.Active.get());

                if (businessModel != null) {
                    this.sessionBllManager.changeBusinessAndDb(requestMessage.userID, businessModel.getBusinessID(), businessModel.getBusinessDBName());
                    responseMessage.responseObj = businessModel;
                    if (Core.clientMessage.get().messageCode != null) {
                        responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        responseMessage.message = MessageConstant.BUSINESS_CHANGE_FAILED;
                        Core.clientMessage.get().message = MessageConstant.BUSINESS_GET_FAILED;
                        this.rollBack();
                        return responseMessage;
                    }
                } else {
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    responseMessage.message = MessageConstant.BUSINESS_CHANGE_FAILED;
                    this.rollBack();
                    return responseMessage;
                }
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.BUSINESS_CHANGE_FAILED;
                this.rollBack();
                return responseMessage;
            }

            //change dbConfig
            //this.selectDataBase(Core.userDataBase.get());

            this.commit();
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
            responseMessage.message = MessageConstant.BUSINESS_CHANGE_SUCCESSFULLY;

        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            log.error("BusinessServiceManager -> selectBusiness got exception");
            this.rollBack();
        }
        return responseMessage;
    }

}
