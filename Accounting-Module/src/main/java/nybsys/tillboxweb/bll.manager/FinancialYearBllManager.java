
package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.Utils.TillBoxUtils;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.coreEntities.FinancialYear;
import nybsys.tillboxweb.coreModels.FinancialYearModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class FinancialYearBllManager extends BaseBll<FinancialYear> {
    private static final Logger log = LoggerFactory.getLogger(FinancialYearBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(FinancialYear.class);
        Core.runTimeModelType.set(FinancialYearModel.class);
    }

    public FinancialYearModel saveOrUpdateFinancialYearWithBusinessLogic(FinancialYearModel financialYearModelReq, Integer businessID) throws Exception {
        FinancialYearModel financialYearModel ;
        FinancialYearModel whereCondition = new FinancialYearModel();
        try {
            financialYearModel = financialYearModelReq;
            financialYearModel.setBusinessID(businessID);
            SimpleDateFormat month = new SimpleDateFormat("MMMM");
            Calendar cal = Calendar.getInstance();
            cal.setTime(financialYearModel.getStartDate());
            int startYear = cal.get(Calendar.YEAR);
            cal.setTime(financialYearModel.getEndDate());
            int endYear = cal.get(Calendar.YEAR);

            //start year,month - end year,month
            financialYearModel.setFinancialYearName(""+startYear +" "+month.format(financialYearModel.getStartDate()) + " - "+endYear+" " +month.format(financialYearModel.getEndDate()));

            whereCondition.setFinancialYearName(financialYearModel.getFinancialYearName());
            whereCondition.setBusinessID(businessID);
            List<FinancialYearModel> lstFinancialYearModel = this.getAllByConditions(whereCondition);

            //not self conflict
            if (lstFinancialYearModel.size() > 0 && financialYearModel.getFinancialYearID() != null && lstFinancialYearModel.get(0).getFinancialYearID() != financialYearModel.getFinancialYearID()) {
                Core.clientMessage.get().message = MessageConstant.DUPLICATE_FINANCIAL_YEAR_NAME;
                Core.clientMessage.get().userMessage = MessageConstant.DUPLICATE_FINANCIAL_YEAR_NAME;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            } else if (financialYearModel.getStartDate().compareTo(financialYearModel.getEndDate()) > 0) {
                Core.clientMessage.get().message = MessageConstant.START_DATE_IS_GREATER_THEN_END_DATE;
                Core.clientMessage.get().userMessage = MessageConstant.START_DATE_IS_GREATER_THEN_END_DATE;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            } else if (financialYearModel.getLockMyDataAt() != null && ((financialYearModel.getLockMyDataAt().compareTo(financialYearModel.getEndDate()) > 0) || (financialYearModel.getLockMyDataAt().compareTo(financialYearModel.getStartDate()) < 0))) {
                Core.clientMessage.get().message = MessageConstant.LOCK_DATE_IS_OUT_OF_RANGE;
                Core.clientMessage.get().userMessage = MessageConstant.LOCK_DATE_IS_OUT_OF_RANGE;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            } else {
                //save
                if (financialYearModel.getFinancialYearID() == null || financialYearModel.getFinancialYearID() == 0) {
                    financialYearModel = this.save(financialYearModel);
                    if (financialYearModel== null) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_FINANCIAL_YEAR;
                    }

                }
                //update
                else {
                    financialYearModel = this.update(financialYearModel);
                    if (financialYearModel == null) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.FAILED_TO_UPDATE_FINANCIAL_YEAR;
                    }
                }
            }
        } catch (Exception ex) {
            log.error("FinancialYearBllManager -> saveFinancialYearWithBusinessLogic got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return financialYearModel;
    }

    public List<FinancialYearModel> getAllFinancialYear(Integer businessID) throws Exception {
        List<FinancialYearModel> lstFinancialYearModel = new ArrayList<>();
        FinancialYearModel financialYearModel = new FinancialYearModel();

        try {
            financialYearModel.setStatus(TillBoxAppEnum.Status.Active.get());
            financialYearModel.setBusinessID(businessID);
            lstFinancialYearModel = this.getAllByConditions(financialYearModel);
            if (lstFinancialYearModel.size() > 0) {
                Core.clientMessage.get().message = MessageConstant.FINANCIAL_YEAR_GET_SUCCESSFULLY;
            } else {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_FINANCIAL_YEAR;
            }
        } catch (Exception ex) {
            log.error("FinancialYearBllManager -> getAllFinancialYear got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstFinancialYearModel;
    }

    public FinancialYearModel getCurrentFinancialYear(Integer businessID) throws Exception {
        List<FinancialYearModel> lstFinancialYearModel = new ArrayList<>();
        FinancialYearModel financialYearModel = new FinancialYearModel();

        try {
            financialYearModel.setStatus(TillBoxAppEnum.Status.Active.get());
            financialYearModel.setBusinessID(businessID);
            financialYearModel.setIsCurrentFinancialYear(true);
            lstFinancialYearModel = this.getAllByConditions(financialYearModel);
            if (lstFinancialYearModel.size() == 0) {
                financialYearModel = null;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_FINANCIAL_YEAR;
            } else {
                financialYearModel = lstFinancialYearModel.get(0);
            }
        } catch (Exception ex) {
            log.error("FinancialYearBllManager -> getCurrentFinancialYear got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return financialYearModel;
    }

    public List<FinancialYearModel> search(FinancialYearModel financialYearModelReq) throws Exception {
        List<FinancialYearModel> lstFinancialYearModel = new ArrayList<>();
        FinancialYearModel financialYearModel = new FinancialYearModel();

        try {
            financialYearModel = financialYearModelReq;
            lstFinancialYearModel = this.getAllByConditions(financialYearModel);
            if (lstFinancialYearModel.size() == 0) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_FINANCIAL_YEAR;
            }
        } catch (Exception ex) {
            log.error("FinancialYearBllManager -> search got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstFinancialYearModel;
    }

}
