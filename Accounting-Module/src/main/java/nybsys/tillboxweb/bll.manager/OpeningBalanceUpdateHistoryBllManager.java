/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 06/03/2018
 * Time: 02:47
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
import nybsys.tillboxweb.entities.OpeningBalanceUpdateHistory;
import nybsys.tillboxweb.models.OpeningBalanceUpdateHistoryModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class OpeningBalanceUpdateHistoryBllManager extends BaseBll<OpeningBalanceUpdateHistory> {
    private static final Logger log = LoggerFactory.getLogger(OpeningBalanceBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(OpeningBalanceUpdateHistory.class);
        Core.runTimeModelType.set(OpeningBalanceUpdateHistoryModel.class);
    }

    public OpeningBalanceUpdateHistoryModel saveOpeningBalanceUpdateHistory(OpeningBalanceUpdateHistoryModel openingBalanceUpdateHistoryModelReq) throws Exception {
        OpeningBalanceUpdateHistoryModel openingBalanceUpdateHistoryModel = new OpeningBalanceUpdateHistoryModel();
        try {
            openingBalanceUpdateHistoryModel = openingBalanceUpdateHistoryModelReq;

            openingBalanceUpdateHistoryModel = this.save(openingBalanceUpdateHistoryModel);
            if (openingBalanceUpdateHistoryModel == null) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.OPENING_BALANCE_HISTORY_SAVE_FAILED;
            }

        } catch (Exception ex) {
            log.error("OpeningBalanceUpdateHistoryBllManager -> saveOpeningBalanceUpdateHistory got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return openingBalanceUpdateHistoryModel;
    }

    public boolean deleteOpeningBalanceHistory(Integer businessID,Integer referenceID, Integer referenceType) throws Exception {
        OpeningBalanceUpdateHistoryModel whereConditionOpeningBalanceHistory = new OpeningBalanceUpdateHistoryModel();
        OpeningBalanceUpdateHistoryModel openingBalanceUpdateHistoryModel = new OpeningBalanceUpdateHistoryModel();
        Integer numberOfRowDeleted = 0;
        try {
            whereConditionOpeningBalanceHistory.setBusinessID(businessID);
            whereConditionOpeningBalanceHistory.setReferenceID(referenceID);
            whereConditionOpeningBalanceHistory.setReferenceType(referenceType);

            openingBalanceUpdateHistoryModel.setStatus(TillBoxAppEnum.Status.Deleted.get());

            numberOfRowDeleted = this.updateByConditions(whereConditionOpeningBalanceHistory, openingBalanceUpdateHistoryModel);
            if (numberOfRowDeleted == 0) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.OPENING_BALANCE_HISTORY_DELETE_FAILED;
                return false;
            }

        } catch (Exception ex) {
            log.error("OpeningBalanceUpdateHistoryBllManager -> deleteOpeningBalanceHistory got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return true;
    }
}
