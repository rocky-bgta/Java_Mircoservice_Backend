/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 5:09
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.bll.manager.ExpenseTypeBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.models.ExpenseTypeModel;
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
public class ExpenseTypeServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(ExpenseTypeServiceManager.class);
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Autowired
    private ExpenseTypeBllManager expenseTypeBllManager;

    public ResponseMessage save(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        ExpenseTypeModel expenseTypeModel = new ExpenseTypeModel();
        try {
            expenseTypeModel = Core.getRequestObject(requestMessage, ExpenseTypeModel.class);

            /*Set<ConstraintViolation<ExpenseTypeModel>> violations = this.validator.validate(expenseTypeModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            expenseTypeModel = this.expenseTypeBllManager.saveOrUpdate(expenseTypeModel);

            responseMessage.responseObj = expenseTypeModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.EXPENSE_TYPE_SAVE_SUCCESSFULLY;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.EXPENSE_TYPE_SAVE_FAILED;
                }else
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("ExpenseTypeServiceManager -> saveExpenseType got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }
    public ResponseMessage search(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<ExpenseTypeModel> lstExpenseTypeModel = new ArrayList<>();
        ExpenseTypeModel expenseTypeModel = new ExpenseTypeModel();
        try {
            expenseTypeModel = Core.getRequestObject(requestMessage, ExpenseTypeModel.class);

            lstExpenseTypeModel = this.expenseTypeBllManager.searchExpenseType(expenseTypeModel);

            responseMessage.responseObj = lstExpenseTypeModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.EXPENSE_TYPE_GET_SUCCESSFULLY;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.EXPENSE_TYPE_GET_FAILED;
            }

        } catch (Exception ex) {
            log.error("ExpenseTypeServiceManager -> searchExpenseType got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }
    public ResponseMessage delete(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        ExpenseTypeModel expenseTypeModel = new ExpenseTypeModel();
        try {
            expenseTypeModel = Core.getRequestObject(requestMessage, ExpenseTypeModel.class);

            expenseTypeModel = this.expenseTypeBllManager.deleteExpenseType(expenseTypeModel);

            responseMessage.responseObj = expenseTypeModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.EXPENSE_TYPE_DELETE_SUCCESSFULLY;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.EXPENSE_TYPE_DELETE_FAILED;
            }

        } catch (Exception ex) {
            log.error("ExpenseTypeServiceManager -> deleteExpenseType got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }
}
