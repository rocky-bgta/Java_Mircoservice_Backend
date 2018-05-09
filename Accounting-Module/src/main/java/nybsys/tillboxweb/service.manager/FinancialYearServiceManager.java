package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.bll.manager.FinancialYearBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.coreModels.FinancialYearModel;
import nybsys.tillboxweb.models.VMFinancialYearModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class FinancialYearServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(AccountServiceManager.class);
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Autowired
    private FinancialYearBllManager financialYearBllManager;

    public ResponseMessage save(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        VMFinancialYearModel vmFinancialYearModel = new VMFinancialYearModel();
        try {
            if (requestMessage.businessID == null || requestMessage.businessID == 0) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SELECT_A_BUSINESS;
                Core.clientMessage.get().userMessage = MessageConstant.SELECT_A_BUSINESS;
                return responseMessage;
            }

            vmFinancialYearModel = Core.getRequestObject(requestMessage, VMFinancialYearModel.class);

           /* Set<ConstraintViolation<FinancialYearModel>> violations = this.validator.validate(this.financialYearModel);
            for (ConstraintViolation<FinancialYearModel> violation : violations) {
                log.error(violation.getMessage());
            }*/

            for (FinancialYearModel financialYearModel : vmFinancialYearModel.lstFinancialYearModel) {

                this.financialYearBllManager.saveOrUpdateFinancialYearWithBusinessLogic(financialYearModel, requestMessage.businessID);
                if (Core.clientMessage.get().messageCode != null) {
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    if(Core.clientMessage.get().message == null) {
                        responseMessage.message = MessageConstant.FAILED_TO_SAVE_FINANCIAL_YEAR;
                    }else{
                        responseMessage.message = Core.clientMessage.get().message;
                    }
                    this.rollBack();
                    return responseMessage;
                }
            }

            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
            responseMessage.message = MessageConstant.SAVE_FINANCIAL_YEAR_SUCCESSFULLY;
            this.commit();

        } catch (Exception ex) {
            log.error("FinancialYearServiceManager -> save got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }
        return responseMessage;
    }

    public ResponseMessage getAllFinancialYear(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<FinancialYearModel> lstFinancialYearModel = new ArrayList<>();
        try {
            Integer businessID = requestMessage.businessID;

            lstFinancialYearModel = this.financialYearBllManager.getAllFinancialYear(businessID);

            if (Core.clientMessage.get().messageCode == null) {
                responseMessage.responseObj = lstFinancialYearModel;
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.FINANCIAL_YEAR_GET_SUCCESSFULLY;
            } else {
                responseMessage.responseObj = null;
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;//for front end message issue
                responseMessage.message = MessageConstant.FAILED_TO_GET_FINANCIAL_YEAR;
            }

        } catch (Exception ex) {
            log.error("FinancialYearServiceManager -> getAllFinancialYear got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }
        return responseMessage;
    }

    public ResponseMessage search(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<FinancialYearModel> lstFinancialYearModel = new ArrayList<>();
        FinancialYearModel financialYearModel = new FinancialYearModel();
        try {
            financialYearModel = Core.getRequestObject(requestMessage, FinancialYearModel.class);
            financialYearModel.setBusinessID(requestMessage.businessID);
            lstFinancialYearModel = this.financialYearBllManager.search(financialYearModel);

            responseMessage.responseObj = lstFinancialYearModel;
            if (Core.clientMessage.get().messageCode == null) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.FINANCIAL_YEAR_GET_SUCCESSFULLY;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.FAILED_TO_GET_FINANCIAL_YEAR;
            }
        } catch (Exception ex) {
            log.error("FinancialYearServiceManager -> search got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }
        return responseMessage;
    }

    public ResponseMessage getCurrentFinancialYearByBusinessID(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        FinancialYearModel financialYearModel = new FinancialYearModel();
        try {
            Integer businessID = requestMessage.businessID;

            financialYearModel = this.financialYearBllManager.getCurrentFinancialYear(businessID);

            responseMessage.responseObj = financialYearModel;
            if (Core.clientMessage.get().messageCode == null) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.FINANCIAL_YEAR_GET_SUCCESSFULLY;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.FAILED_TO_GET_FINANCIAL_YEAR;
            }

        } catch (Exception ex) {
            log.error("FinancialYearServiceManager -> getCurrentFinancialYearByBusinessID got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }
        return responseMessage;
    }


}
