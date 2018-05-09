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
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.ExpenseDetail;
import nybsys.tillboxweb.models.ExpenseDetailModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ExpenseDetailBllManager extends BaseBll<ExpenseDetail> {
    private static final Logger log = LoggerFactory.getLogger(ExpenseDetailBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(ExpenseDetail.class);
        Core.runTimeModelType.set(ExpenseDetailModel.class);
    }

    public ExpenseDetailModel saveOrUpdate(ExpenseDetailModel expenseDetailModelReq) throws Exception {
        ExpenseDetailModel expenseDetailModel = new ExpenseDetailModel();
        List<ExpenseDetailModel> lstExpenseDetailModel = new ArrayList<>();
        try {
            expenseDetailModel = expenseDetailModelReq;
            //save
            if (expenseDetailModel.getExpenseDetailID() == null || expenseDetailModel.getExpenseDetailID() == 0)
            {
                expenseDetailModel = this.save(expenseDetailModel);
                if (expenseDetailModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.EXPENSE_DETAIL_SAVE_FAILED;
                }
            } else { //update

                expenseDetailModel = this.update(expenseDetailModel);
                if (expenseDetailModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.EXPENSE_DETAIL_UPDATE_FAILED;
                }
            }

        } catch (Exception ex) {
            log.error("ExpenseDetailBllManager -> saveOrUpdate got exception :"+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return expenseDetailModel;
    }

    public List<ExpenseDetailModel> searchExpenseDetail(ExpenseDetailModel expenseDetailModelReq) throws Exception {
        ExpenseDetailModel expenseDetailModel = new ExpenseDetailModel();
        List<ExpenseDetailModel> lstExpenseDetailModel = new ArrayList<>();
        try {
            expenseDetailModel = expenseDetailModelReq;
            lstExpenseDetailModel = this.getAllByConditions(expenseDetailModel);
            if (lstExpenseDetailModel.size() == 0) {
                Core.clientMessage.get().message = MessageConstant.EXPENSE_DETAIL_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("ExpenseDetailBllManager -> searchExpenseDetail got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstExpenseDetailModel;
    }
    
    public Integer deleteExpenseDetailByExpenseID(Integer expenseID) throws Exception {
        ExpenseDetailModel whereCondition = new ExpenseDetailModel();
        ExpenseDetailModel modelUpdateCondition = new ExpenseDetailModel();
        Integer deleteCounter = 0;
        try {
            whereCondition.setExpenseID(expenseID);
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());
            
            modelUpdateCondition.setStatus(TillBoxAppEnum.Status.Deleted.get());

            deleteCounter = this.updateByConditions(whereCondition,modelUpdateCondition);
            if (deleteCounter == 0) {
                        Core.clientMessage.get().message = MessageConstant.EXPENSE_DETAIL_DELETE_FAILED;
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }

        } catch (Exception ex) {
            log.error("ExpenseDetailBllManager -> deleteExpenseDetailByExpenseID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return deleteCounter;
    }
    
}
