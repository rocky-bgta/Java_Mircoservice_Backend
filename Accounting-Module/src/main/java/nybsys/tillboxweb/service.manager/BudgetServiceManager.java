package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.bll.manager.*;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.coreModels.AccountTypeModel;
import nybsys.tillboxweb.coreModels.FinancialYearModel;
import nybsys.tillboxweb.models.*;
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
public class BudgetServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(BudgetServiceManager.class);
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Autowired
    private AccountTypeBllManager accountTypeBllManager;
    @Autowired
    private AccountBllManager accountBllManager;
    @Autowired
    private BudgetBllManager budgetBllManager;
    @Autowired
    private BudgetDetailBllManager budgetDetailBllManager;
    @Autowired
    private FinancialYearBllManager financialYearBllManager;

    private class VMBudgetDropDown {
        public List<AccountTypeModel> lstAccountTypeModel = new ArrayList<>();
        public List<FinancialYearModel> lstFinancialYearModel = new ArrayList<>();
    }

    public static class VMBudgetReq {
        public Integer accountTypeID ;
        public Integer financialYearID ;
    }

    public static class VMBudgetDetail {

        public BudgetModel budgetModel = new BudgetModel();
        public List<VMAccountBudgetDetailModel> lstVmAccountBudgetDetailModel = new ArrayList<>();
    }

    public ResponseMessage saveBudgetDetail(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        VMBudgetDetail vmBudgetDetail ;

        try {
            if (requestMessage.businessID == null || requestMessage.businessID == 0) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SELECT_A_BUSINESS;
                Core.clientMessage.get().userMessage = MessageConstant.SELECT_A_BUSINESS;
                return responseMessage;
            }

            vmBudgetDetail = Core.getRequestObject(requestMessage, VMBudgetDetail.class);
            vmBudgetDetail.budgetModel.setBusinessID(requestMessage.businessID);

            //save budget
            vmBudgetDetail.budgetModel = this.budgetBllManager.saveOrUpdateBudgetWithBusinessLogic(vmBudgetDetail.budgetModel);
            if(Core.clientMessage.get().messageCode != null)
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.FAILED_TO_SAVE_BUDGET;
                this.rollBack();
                return responseMessage;
            }

            // loop through list of accounting budget detail vm and call list of save or update operation
            List<BudgetDetailModel> lstBudgetDetailModel = new ArrayList<>();
            for (VMAccountBudgetDetailModel vmAccountBudgetDetailModelItem : vmBudgetDetail.lstVmAccountBudgetDetailModel) {
                for (BudgetDetailModel budgetDetailModelItem : vmAccountBudgetDetailModelItem.lstBudgetDetailModel) {
                    budgetDetailModelItem.setBudgetID(vmBudgetDetail.budgetModel.getBudgetID());
                    this.budgetDetailBllManager.saveOrUpdateBudgetDetailWithBusinessLogic(budgetDetailModelItem);
                    if(Core.clientMessage.get().messageCode != null) {
                        responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        responseMessage.message = MessageConstant.FAILED_TO_SAVE_BUDGET;
                        this.rollBack();
                        return responseMessage;
                    }
                }
            }

            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
            responseMessage.message = MessageConstant.SUCCESSFULLY_SAVE_BUDGET;
            this.commit();

        } catch (Exception ex) {
            log.error("BudgetServiceManager -> saveBudgetDetail got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }
        return responseMessage;
    }

    public ResponseMessage getBudgetDetail(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        BudgetModel budgetModel = new BudgetModel();
        VMBudgetReq vmBudgetReq = new VMBudgetReq();
        VMBudgetDetail vmBudgetDetail = new VMBudgetDetail();
        AccountTypeModel accountTypeModel = new AccountTypeModel();
        List<AccountModel> lstAccountModel;
        try {
            if (requestMessage.businessID == null || requestMessage.businessID == 0) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SELECT_A_BUSINESS;
                Core.clientMessage.get().userMessage = MessageConstant.SELECT_A_BUSINESS;
                return responseMessage;
            }

            vmBudgetReq = Core.getRequestObject(requestMessage, VMBudgetReq.class);

            //(1)
            accountTypeModel = this.accountTypeBllManager.getAccountTypeByID(vmBudgetReq.accountTypeID);
            if (Core.clientMessage.get().messageCode != null) {
                responseMessage.message = MessageConstant.FAILED_TO_GET_BUDGET_DETAIL;
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                return responseMessage;
            }
            //(2)get account list
            lstAccountModel = this.accountBllManager.getAccountByAccountTypeIDAndAccountClassificationIDAndBusinessID(accountTypeModel.getAccountTypeID(), accountTypeModel.getAccountClassificationID(), requestMessage.businessID);
            if (Core.clientMessage.get().messageCode != null) {
                responseMessage.message = MessageConstant.FAILED_TO_GET_BUDGET_DETAIL;
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                return responseMessage;
            }
            //(3)get budget
            budgetModel = this.budgetBllManager.getBudgetByFinancialYearIDAndBusinessID(vmBudgetReq.financialYearID, requestMessage.businessID);
//            if (Core.clientMessage.get().messageCode != null) {
//                responseMessage.message = MessageConstant.FAILED_TO_GET_BUDGET_DETAIL;
//                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
//                return responseMessage;
//            }

            //push account budget detail vm for every account to a list of account budget detail vm
            List<VMAccountBudgetDetailModel> lstVmAccountBudgetDetailModel = new ArrayList<>();
            for (AccountModel accountModelIItem : lstAccountModel) {
                VMAccountBudgetDetailModel vmAccountBudgetDetailModel = new VMAccountBudgetDetailModel();

                List<BudgetDetailModel> lstBudgetDetailModel = new ArrayList<>();
                if(budgetModel != null && budgetModel.getBudgetID() != null) {
                    lstBudgetDetailModel = this.budgetDetailBllManager.getAllBudgetDetailByAccountIDAndBudgetID(accountModelIItem.getAccountID(), budgetModel.getBudgetID());
//                    if (Core.clientMessage.get().messageCode != null) {
//                        responseMessage.message = MessageConstant.FAILED_TO_GET_BUDGET_DETAIL;
//                        responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
//                        return responseMessage;
//                    }
                }

                vmAccountBudgetDetailModel.accountModel = accountModelIItem;
                vmAccountBudgetDetailModel.lstBudgetDetailModel = lstBudgetDetailModel;

                lstVmAccountBudgetDetailModel.add(vmAccountBudgetDetailModel);
            }

            vmBudgetDetail.budgetModel = budgetModel;
            vmBudgetDetail.lstVmAccountBudgetDetailModel = lstVmAccountBudgetDetailModel;

                responseMessage.message = MessageConstant.SUCCESSFULLY_GET_BUDGET_DETAIL;
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.responseObj = vmBudgetDetail;

        } catch (Exception ex) {
            log.error("BudgetServiceManager -> getBudgetDetail got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }
        return responseMessage;
    }

    public ResponseMessage getBudgetDropDown(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        VMBudgetDropDown vmBudgetDropDown = new VMBudgetDropDown();
        List<AccountTypeModel> lstAccountTypeModel = new ArrayList<>();
        List<FinancialYearModel> lstFinancialYearModel = new ArrayList<>();

        try {
            Integer businessID = requestMessage.businessID;//to do

            lstAccountTypeModel = this.accountTypeBllManager.getAllAccountType();
            if (Core.clientMessage.get().messageCode != null) {
                responseMessage.message = MessageConstant.FAILED_TO_GET_BUDGET_DROP_DOWN;
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                return responseMessage;
            }

            lstFinancialYearModel = this.financialYearBllManager.getAllFinancialYear(businessID);
            if (Core.clientMessage.get().messageCode != null) {
                responseMessage.message = MessageConstant.FAILED_TO_GET_BUDGET_DROP_DOWN;
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                return responseMessage;
            }

            //make final obj
            vmBudgetDropDown.lstAccountTypeModel = lstAccountTypeModel;
            vmBudgetDropDown.lstFinancialYearModel = lstFinancialYearModel;

            responseMessage.responseObj = vmBudgetDropDown;
            responseMessage.message = MessageConstant.SUCCESSFULLY_GET_BUDGET_DROP_DOWN;
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

        } catch (Exception ex) {
            log.error("BudgetServiceManager -> getBudgetDropDown got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }
        return responseMessage;
    }

}
