
package nybsys.tillboxweb.coreBllManager;


import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.coreEntities.FinancialYear;
import nybsys.tillboxweb.coreModels.FinancialYearModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CoreFinancialYearBllManager extends BaseBll<FinancialYear> {
    private static final Logger log = LoggerFactory.getLogger(CoreFinancialYearBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(FinancialYear.class);
        Core.runTimeModelType.set(FinancialYearModel.class);
    }

    public FinancialYearModel getCurrentFinancialYear(Integer businessID) throws Exception {
        List<FinancialYearModel> financialYearModelArrayList;
        FinancialYearModel financialYearModel = new FinancialYearModel();

        try {
            financialYearModel.setStatus(TillBoxAppEnum.Status.Active.get());
            financialYearModel.setBusinessID(businessID);
            financialYearModel.setIsCurrentFinancialYear(true);
            financialYearModelArrayList = this.getAllByConditions(financialYearModel);
            if (financialYearModelArrayList.size() == 0) {
                financialYearModel = null;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = "Failed to get Financial year";
            }
            else {
                financialYearModel=financialYearModelArrayList.get(0);
            }
        } catch (Exception ex) {
            log.error("FinancialYearBllManager -> getCurrentFinancialYear got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return financialYearModel;
    }

}
