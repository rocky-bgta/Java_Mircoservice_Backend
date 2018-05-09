/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 26/02/2018
 * Time: 05:18
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
import nybsys.tillboxweb.entities.ExpenseAdjustmentDetail;
import nybsys.tillboxweb.models.ExpenseAdjustmentDetailModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ExpenseAdjustmentDetailBllManager extends BaseBll<ExpenseAdjustmentDetail> {
    private static final Logger log = LoggerFactory.getLogger(ExpenseAdjustmentDetailBllManager.class);

    @Autowired
    private ExpenseAdjustmentBllManager expenseAdjustmentBllManager = new ExpenseAdjustmentBllManager();

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(ExpenseAdjustmentDetail.class);
        Core.runTimeModelType.set(ExpenseAdjustmentDetailModel.class);
    }

    public List<ExpenseAdjustmentDetailModel> saveOrUpdateList(List<ExpenseAdjustmentDetailModel> lstExpenseAdjustmentDetailModelReq, Integer expenseAdjustmentID) throws Exception {
        List<ExpenseAdjustmentDetailModel> lstExpenseAdjustmentDetailModel = new ArrayList<>();
        try {
            lstExpenseAdjustmentDetailModel = lstExpenseAdjustmentDetailModelReq;

            Core.clientMessage.get().messageCode = null;
            for (ExpenseAdjustmentDetailModel expenseAdjustmentDetailModel : lstExpenseAdjustmentDetailModel) {

                expenseAdjustmentDetailModel.setExpenseAdjustmentID(expenseAdjustmentID.intValue());
                expenseAdjustmentDetailModel.setExpenseDetailID(null);

                //save "because detail delete first by adjustment id in the service"
                expenseAdjustmentDetailModel = this.save(expenseAdjustmentDetailModel);
                if (expenseAdjustmentDetailModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.EXPENSE_ADJUSTMENT_DETAIL_SAVE_FAILED;
                }

            }

        } catch (Exception ex) {
            log.error("ExpenseAdjustmentDetailBllManager -> saveOrUpdate got exception :" + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstExpenseAdjustmentDetailModel;
    }

    public List<ExpenseAdjustmentDetailModel> searchExpenseAdjustmentDetailByAdjustmentID(int expenseAdjustmentID) throws Exception {
        ExpenseAdjustmentDetailModel expenseAdjustmentDetailModel = new ExpenseAdjustmentDetailModel();
        List<ExpenseAdjustmentDetailModel> lstExpenseAdjustmentDetailModel = new ArrayList<>();
        try {
            expenseAdjustmentDetailModel.setExpenseAdjustmentID(expenseAdjustmentID);
            expenseAdjustmentDetailModel.setStatus(TillBoxAppEnum.Status.Active.get());
            lstExpenseAdjustmentDetailModel = this.getAllByConditions(expenseAdjustmentDetailModel);

            if (lstExpenseAdjustmentDetailModel.size() == 0) {
                Core.clientMessage.get().message = MessageConstant.EXPENSE_ADJUSTMENT_DETAIL_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("ExpenseAdjustmentDetailBllManager -> searchExpenseByID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstExpenseAdjustmentDetailModel;
    }

    public Integer deleteExpenseAdjustDetailByExpenseAdjustmentID(Integer expenseAdjustmentID) throws Exception {
        ExpenseAdjustmentDetailModel whereCondition = new ExpenseAdjustmentDetailModel();
        List<ExpenseAdjustmentDetailModel> lstExpenseAdjustmentDetailModel;
        Integer deleteCounter = 0;
        try {
            whereCondition.setExpenseAdjustmentID(expenseAdjustmentID);
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());

            lstExpenseAdjustmentDetailModel = this.getAllByConditions(whereCondition);
            if (lstExpenseAdjustmentDetailModel.size() > 0) {
                for (ExpenseAdjustmentDetailModel expenseAdjustmentDetailModel : lstExpenseAdjustmentDetailModel) {

                    //delete expense adjustment detail;
                    expenseAdjustmentDetailModel = this.softDelete(expenseAdjustmentDetailModel);
                    if (expenseAdjustmentDetailModel == null) {
                        Core.clientMessage.get().message = MessageConstant.EXPENSE_ADJUSTMENT_DETAIL_DELETE_FAILED;
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        return deleteCounter;
                    }
                    deleteCounter++;
                }
            }

        } catch (Exception ex) {
            log.error("ExpenseAdjustmentDetailBllManager -> deleteExpenseAdjustDetailByExpenseAdjustmentID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return deleteCounter;
    }
}
