/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 26/02/2018
 * Time: 04:41
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
import nybsys.tillboxweb.coreUtil.CoreUtils;
import nybsys.tillboxweb.entities.ExpenseAdjustment;
import nybsys.tillboxweb.models.ExpenseAdjustmentModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ExpenseAdjustmentBllManager extends BaseBll<ExpenseAdjustment>{
    private static final Logger log = LoggerFactory.getLogger(ExpenseAdjustmentBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(ExpenseAdjustment.class);
        Core.runTimeModelType.set(ExpenseAdjustmentModel.class);
    }

    public ExpenseAdjustmentModel saveOrUpdate(ExpenseAdjustmentModel expenseAdjustmentModelReq) throws Exception {
        ExpenseAdjustmentModel expenseAdjustmentModel = new ExpenseAdjustmentModel();
        List<ExpenseAdjustmentModel> lstExpenseAdjustmentModel = new ArrayList<>();
        try {
            expenseAdjustmentModel = expenseAdjustmentModelReq;
            //save
            if (expenseAdjustmentModel.getExpenseAdjustmentID() == null || expenseAdjustmentModel.getExpenseAdjustmentID() == 0)
            {
                // ============================= Create CAD0000001 =============================
                String currentDBSequence = null , buildDbSequence, hsql;
                hsql = "SELECT ea FROM ExpenseAdjustment ea ORDER BY ea.expenseAdjustmentID DESC";
                lstExpenseAdjustmentModel = this.executeHqlQuery(hsql, ExpenseAdjustmentModel.class, TillBoxAppEnum.QueryType.GetOne.get());
                if (lstExpenseAdjustmentModel.size() > 0) {
                    currentDBSequence = lstExpenseAdjustmentModel.get(0).getAdjustmentNumber();
                }
                buildDbSequence = CoreUtils.getSequence(currentDBSequence,"EAD");
                // ==========================End Create CAD0000001 =============================

                expenseAdjustmentModel.setAdjustmentNumber(buildDbSequence);
                expenseAdjustmentModel = this.save(expenseAdjustmentModel);
                if (expenseAdjustmentModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.EXPENSE_ADJUSTMENT_SAVE_FAILED;
                }
            } else { //update

                expenseAdjustmentModel = this.update(expenseAdjustmentModel);
                if (expenseAdjustmentModel == null) {
                    expenseAdjustmentModel = null;
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.EXPENSE_ADJUSTMENT_UPDATE_FAILED;
                }
            }

        } catch (Exception ex) {
            log.error("ExpenseAdjustmentBllManager -> saveOrUpdate got exception :"+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return expenseAdjustmentModel;
    }
    public ExpenseAdjustmentModel searchExpenseAdjustmentByID(int expenseAdjustmentID) throws Exception {
        ExpenseAdjustmentModel expenseAdjustmentModel = new ExpenseAdjustmentModel();
        List<ExpenseAdjustmentModel> lstExpenseAdjustmentModel = new ArrayList<>();
        try {
            expenseAdjustmentModel.setExpenseAdjustmentID(expenseAdjustmentID);
            expenseAdjustmentModel.setStatus(TillBoxAppEnum.Status.Active.get());
            lstExpenseAdjustmentModel = this.getAllByConditions(expenseAdjustmentModel);
            if (lstExpenseAdjustmentModel.size() > 0) {
                expenseAdjustmentModel = lstExpenseAdjustmentModel.get(0);
            } else {
                expenseAdjustmentModel= null;
                Core.clientMessage.get().message = MessageConstant.EXPENSE_ADJUSTMENT_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("ExpenseAdjustmentBllManager -> searchExpenseAdjustmentByID got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return expenseAdjustmentModel;
    }
    public List<ExpenseAdjustmentModel> searchExpenseAdjustment(ExpenseAdjustmentModel expenseAdjustmentModelReq) throws Exception {
        ExpenseAdjustmentModel expenseAdjustmentModel = new ExpenseAdjustmentModel();
        List<ExpenseAdjustmentModel> lstExpenseAdjustmentModel = new ArrayList<>();
        try {
            expenseAdjustmentModel = expenseAdjustmentModelReq;

            expenseAdjustmentModel.setStatus(TillBoxAppEnum.Status.Active.get());
            lstExpenseAdjustmentModel = this.getAllByConditions(expenseAdjustmentModel);
            if (lstExpenseAdjustmentModel.size() == 0) {
                Core.clientMessage.get().message = MessageConstant.EXPENSE_ADJUSTMENT_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("ExpenseAdjustmentBllManager -> searchExpenseAdjustmentByID got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstExpenseAdjustmentModel;
    }
    public ExpenseAdjustmentModel deleteExpenseAdjustmentByExpenseAdjustmentID(Integer expenseAdjustmentID) throws Exception {
        ExpenseAdjustmentModel expenseAdjustmentModel = new ExpenseAdjustmentModel();
        try {
            expenseAdjustmentModel.setExpenseAdjustmentID(expenseAdjustmentID);
            expenseAdjustmentModel = this.softDelete(expenseAdjustmentModel);
            if (expenseAdjustmentModel == null) {
                Core.clientMessage.get().message = MessageConstant.EXPENSE_ADJUSTMENT_DELETE_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("ExpenseAdjustmentBllManager -> deleteExpenseAdjustmentByExpenseAdjustmentID got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return expenseAdjustmentModel;
    }

}
