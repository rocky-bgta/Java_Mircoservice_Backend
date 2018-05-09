/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 4:53
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.Expense;
import nybsys.tillboxweb.models.ExpenseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ExpenseBllManager extends BaseBll<Expense> {
    private static final Logger log = LoggerFactory.getLogger(ExpenseBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(Expense.class);
        Core.runTimeModelType.set(ExpenseModel.class);
    }

    public ExpenseModel saveOrUpdate(ExpenseModel expenseModelReq) throws Exception {
        ExpenseModel expenseModel = new ExpenseModel();
        List<ExpenseModel> lstExpenseModel = new ArrayList<>();
        try {
            expenseModel = expenseModelReq;
            //save
            if (expenseModel.getExpenseID() == null || expenseModel.getExpenseID() == 0)
            {
                expenseModel = this.save(expenseModel);
                if (expenseModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.EXPENSE_TYPE_SAVE_FAILED;
                }
            } else { //update

                expenseModel = this.update(expenseModel);
                if (expenseModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.EXPENSE_TYPE_UPDATE_FAILED;
                }
            }

        } catch (Exception ex) {
            log.error("ExpenseBllManager -> saveOrUpdate got exception :"+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return expenseModel;
    }

    public List<ExpenseModel> searchExpense(ExpenseModel expenseModelReq) throws Exception {
        ExpenseModel expenseModel = new ExpenseModel();
        List<ExpenseModel> lstExpenseModel = new ArrayList<>();
        try {
            expenseModel = expenseModelReq;
            lstExpenseModel = this.getAllByConditions(expenseModel);
            if (lstExpenseModel.size() == 0) {
                Core.clientMessage.get().message = MessageConstant.EXPENSE_TYPE_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("ExpenseBllManager -> searchExpense got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstExpenseModel;
    }
    public ExpenseModel deleteExpenseByID(Integer expenseID) throws Exception {
        ExpenseModel expenseModel = new ExpenseModel();
        try {
            expenseModel = this.getById(expenseID);
            if (expenseModel == null) {
                Core.clientMessage.get().message = MessageConstant.EXPENSE_TYPE_DELETE_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                return expenseModel;
            }
            expenseModel = this.softDelete(expenseModel);
            if (expenseModel == null) {
                Core.clientMessage.get().message = MessageConstant.EXPENSE_TYPE_DELETE_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("ExpenseBllManager -> deleteExpense got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return expenseModel;
    }
    
}
