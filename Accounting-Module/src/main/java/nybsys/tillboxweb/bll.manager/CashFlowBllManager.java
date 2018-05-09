package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.CashFlow;
import nybsys.tillboxweb.models.CashFlowModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CashFlowBllManager extends BaseBll<CashFlow> {
    private static final Logger log = LoggerFactory.getLogger(CashFlowBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(CashFlow.class);
        Core.runTimeModelType.set(CashFlowModel.class);
    }

    public CashFlowModel saveCashFlow(CashFlowModel cashFlowModelReq, String userID) throws Exception {
        CashFlowModel cashFlowModel = new CashFlowModel();
        cashFlowModel = cashFlowModelReq;
        try {
            cashFlowModel = this.save(cashFlowModel);
            if (cashFlowModel == null) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_CASH_FLOW;
            }
        } catch (Exception ex) {
            log.error("CashFlowBllManager -> getAllCashFlow got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return cashFlowModel;
    }

    private CashFlowModel updateCashFlow(CashFlowModel cashFlowModelReq, String userID) throws Exception {
        CashFlowModel cashFlowModel = new CashFlowModel();
        cashFlowModel = cashFlowModelReq;
        try {
            cashFlowModel = this.update(cashFlowModel);
            if (cashFlowModel == null) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_UPDATE_CASH_FLOW;
            }
        } catch (Exception ex) {
            log.error("CashFlowBllManager -> updateCashFlow got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return cashFlowModel;
    }

    public List<CashFlowModel> getAllCashFlow() throws Exception {
        List<CashFlowModel> lstCashFlowModel = new ArrayList<>();
        CashFlowModel whereCondition = new CashFlowModel();
        try {
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());
            lstCashFlowModel = this.getAllByConditions(whereCondition);
            if (lstCashFlowModel.size() == 0) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_CASH_FLOW;
            }
        } catch (Exception ex) {
            log.error("CashFlowBllManager -> getAllCashFlow got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstCashFlowModel;
    }
}