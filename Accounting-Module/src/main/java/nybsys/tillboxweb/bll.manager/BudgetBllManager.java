package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.Budget;
import nybsys.tillboxweb.models.BudgetModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class BudgetBllManager extends BaseBll<Budget> {
    private static final Logger log = LoggerFactory.getLogger(BudgetBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(Budget.class);
        Core.runTimeModelType.set(BudgetModel.class);
    }
    public BudgetModel saveOrUpdateBudgetWithBusinessLogic(BudgetModel budgetModelReq) throws Exception {
        BudgetModel budgetModel = new BudgetModel();
        try {
            budgetModel = budgetModelReq;
            //save
            if(budgetModel.getBudgetID() == null || budgetModel.getBudgetID() == 0) {
                budgetModel = this.save(budgetModel);
                if (budgetModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_BUDGET;
                }
            }else {//update
                budgetModel = this.update(budgetModel);
                if (budgetModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_UPDATE_BUDGET;
                }
            }
        } catch (Exception ex) {
            log.error("BudgetBllManager -> updateBudget got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return budgetModel;
    }

    public List<BudgetModel> getAllBudget() throws Exception {
        List<BudgetModel> lstBudgetModel = new ArrayList<>();
        BudgetModel budgetModel = new BudgetModel();
        try {
            budgetModel.setStatus(TillBoxAppEnum.Status.Active.get());
            lstBudgetModel = this.getAllByConditions(budgetModel);
            if (lstBudgetModel.size() == 0) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_BUDGET;
            }
        } catch (Exception ex) {
            log.error("BudgetBllManager -> getAllBudget got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstBudgetModel;
    }
    public BudgetModel getBudgetByFinancialYearIDAndBusinessID(Integer financialYearID,Integer businessID) throws Exception {
        List<BudgetModel> lstBudgetModel = new ArrayList<>();
        BudgetModel budgetModel = new BudgetModel();
        try {
            budgetModel.setStatus(TillBoxAppEnum.Status.Active.get());
            budgetModel.setFinancialYearID(financialYearID);
            budgetModel.setBusinessID(businessID);
            lstBudgetModel = this.getAllByConditions(budgetModel);
            if (lstBudgetModel.size() > 0) {
                budgetModel = lstBudgetModel.get(0);
            } else {
                budgetModel = null;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_BUDGET;
            }
        } catch (Exception ex) {
            log.error("BudgetBllManager -> getAllBudget got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return budgetModel;
    }
}
