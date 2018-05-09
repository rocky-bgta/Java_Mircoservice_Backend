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
import nybsys.tillboxweb.entities.ExpenseType;
import nybsys.tillboxweb.models.ExpenseTypeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ExpenseTypeBllManager extends BaseBll<ExpenseType> {
    private static final Logger log = LoggerFactory.getLogger(ExpenseTypeBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(ExpenseType.class);
        Core.runTimeModelType.set(ExpenseTypeModel.class);
    }

    public ExpenseTypeModel saveOrUpdate(ExpenseTypeModel expenseTypeModelReq) throws Exception {
        ExpenseTypeModel expenseTypeModel = new ExpenseTypeModel();
        ExpenseTypeModel whereCondition = new ExpenseTypeModel();
        List<ExpenseTypeModel> lstExpenseTypeModel = new ArrayList<>();
        try {
            expenseTypeModel = expenseTypeModelReq;

            //search first
            whereCondition.setName(expenseTypeModel.getName());
            whereCondition.setBusinessID(expenseTypeModel.getBusinessID());
            lstExpenseTypeModel = this.searchExpenseType(whereCondition);
            Core.clientMessage.get().messageCode = null;
            //save
            if (expenseTypeModel.getExpenseTypeID() == null || expenseTypeModel.getExpenseTypeID() == 0)
            {
                //check duplicate save
                if(lstExpenseTypeModel.size() > 0){
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.NAME_ALREADY_EXISTS;
                    Core.clientMessage.get().userMessage = MessageConstant.NAME_ALREADY_EXISTS;
                    return expenseTypeModel;
                }
                expenseTypeModel = this.save(expenseTypeModel);
                if (expenseTypeModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.EXPENSE_TYPE_SAVE_FAILED;
                }
            } else { //update

                //check duplicate update
                if(lstExpenseTypeModel.size() > 0){
                    //not self reflection
                    if(lstExpenseTypeModel.get(0).getExpenseTypeID().intValue() != expenseTypeModel.getExpenseTypeID().intValue()) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.NAME_ALREADY_EXISTS;
                        Core.clientMessage.get().userMessage = MessageConstant.NAME_ALREADY_EXISTS;
                        return expenseTypeModel;
                    }
                }
                expenseTypeModel = this.update(expenseTypeModel);
                if (expenseTypeModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.EXPENSE_TYPE_UPDATE_FAILED;
                }
            }
        } catch (Exception ex) {
            log.error("ExpenseTypeBllManager -> saveOrUpdate got exception :"+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return expenseTypeModel;
    }

    public List<ExpenseTypeModel> searchExpenseType(ExpenseTypeModel expenseTypeModelReq) throws Exception {
        ExpenseTypeModel expenseTypeModel = new ExpenseTypeModel();
        List<ExpenseTypeModel> lstExpenseTypeModel = new ArrayList<>();
        try {
            expenseTypeModel = expenseTypeModelReq;
            lstExpenseTypeModel = this.getAllByConditions(expenseTypeModel);
            if (lstExpenseTypeModel.size() == 0) {
                Core.clientMessage.get().message = MessageConstant.EXPENSE_TYPE_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("ExpenseTypeBllManager -> searchExpenseType got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstExpenseTypeModel;
    }
    public ExpenseTypeModel deleteExpenseType(ExpenseTypeModel expenseTypeModelReq) throws Exception {
        ExpenseTypeModel expenseTypeModel = new ExpenseTypeModel();
        try {
            expenseTypeModel = expenseTypeModelReq;
            expenseTypeModel = this.softDelete(expenseTypeModel);
            if (expenseTypeModel == null) {
                Core.clientMessage.get().message = MessageConstant.EXPENSE_TYPE_DELETE_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("ExpenseTypeBllManager -> deleteExpenseType got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return expenseTypeModel;
    }
    
}
