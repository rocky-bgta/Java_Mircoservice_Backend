package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.BudgetDetail;
import nybsys.tillboxweb.models.BudgetDetailModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class BudgetDetailBllManager extends BaseBll<BudgetDetail> {
    private static final Logger log = LoggerFactory.getLogger(BudgetDetailBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(BudgetDetail.class);
        Core.runTimeModelType.set(BudgetDetailModel.class);
    }
    public BudgetDetailModel saveOrUpdateBudgetDetailWithBusinessLogic(BudgetDetailModel budgetDetailModelReq) throws Exception {
        BudgetDetailModel budgetDetailModel = new BudgetDetailModel();
        budgetDetailModel = budgetDetailModelReq;
        try {
            if (budgetDetailModel.getBudgetDetailID() == null || budgetDetailModel.getBudgetDetailID() == 0 ) {
                budgetDetailModel = this.save(budgetDetailModel);
                if (budgetDetailModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_BUDGET_DETAIL;
                }
            }else
            {
                budgetDetailModel = this.update(budgetDetailModel);
                if (budgetDetailModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_UPDATE_BUDGET_DETAIL;
                }
            }
        } catch (Exception ex) {
            log.error("BudgetDetailBllManager -> saveOrUpdateBudgetDetailWithBusinessLogic got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return budgetDetailModel;
    }

    public List<BudgetDetailModel> getAllBudgetDetail() throws Exception {
        List<BudgetDetailModel> lstBudgetDetailModel = new ArrayList<>();
        BudgetDetailModel whereCondition = new BudgetDetailModel();
        try {
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());
            lstBudgetDetailModel = this.getAllByConditions(whereCondition);
            if (lstBudgetDetailModel.size() == 0) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_BUDGET_DETAIL;
            }
        } catch (Exception ex) {
            log.error("BudgetDetailBllManager -> getAllBudgetDetail got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstBudgetDetailModel;
    }

    public List<BudgetDetailModel> getAllBudgetDetailByAccountIDAndBudgetID(Integer accountID, Integer budgetID) throws Exception {
        List<BudgetDetailModel> lstBudgetDetailModel = new ArrayList<>();
        BudgetDetailModel budgetDetailModel = new BudgetDetailModel();
        try {
            budgetDetailModel.setStatus(TillBoxAppEnum.Status.Active.get());
            budgetDetailModel.setAccountID(accountID);
            budgetDetailModel.setBudgetID(budgetID);
            lstBudgetDetailModel = this.getAllByConditions(budgetDetailModel);
            if (lstBudgetDetailModel.size() == 0) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_BUDGET_DETAIL;
            }
        } catch (Exception ex) {
            log.error("BudgetDetailBllManager -> getAllBudgetDetailByAccountIDAndBudgetID got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstBudgetDetailModel;
    }
}
