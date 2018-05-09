package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.coreEntities.AccountClassification;
import nybsys.tillboxweb.coreModels.AccountClassificationModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AccountClassificationBllManager extends BaseBll<AccountClassification> {
    private static final Logger log = LoggerFactory.getLogger(AccountClassificationBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(AccountClassification.class);
        Core.runTimeModelType.set(AccountClassificationModel.class);
    }

    public AccountClassificationModel saveAccountClassification(AccountClassificationModel accountClassificationModelReq, String userID) throws Exception {
        AccountClassificationModel accountClassificationModel = new AccountClassificationModel();
        accountClassificationModel = accountClassificationModelReq;
        try {
            accountClassificationModel = this.save(accountClassificationModel);
            if (accountClassificationModel == null) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_ACCOUNT_CLASSIFICATION;
            }
        } catch (Exception ex) {
            log.error("AccountClassificationBllManager -> saveAccountClassification got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return accountClassificationModel;
    }

    private AccountClassificationModel updateAccountClassification(AccountClassificationModel accountClassificationModelReq, String userID) throws Exception {
        AccountClassificationModel accountClassificationModel = new AccountClassificationModel();
        accountClassificationModel = accountClassificationModelReq;
        try {
            accountClassificationModel = this.update(accountClassificationModel);
            if (accountClassificationModel == null) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_UPDATE_ACCOUNT_CLASSIFICATION;
            }
        } catch (Exception ex) {
            log.error("AccountClassificationBllManager -> updateAccountClassification got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return accountClassificationModel;
    }

    public List<AccountClassificationModel> getAllAccountClassification() throws Exception {
        List<AccountClassificationModel> lstAccountClassificationModel = new ArrayList<>();
        AccountClassificationModel whereCondition = new AccountClassificationModel();
        try {
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());
            lstAccountClassificationModel = this.getAllByConditions(whereCondition);
            if (lstAccountClassificationModel.size() == 0) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_ACCOUNT_CLASSIFICATION;
            }
        } catch (Exception ex) {
            log.error("AccountClassificationBllManager -> getAllAccountClassification got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstAccountClassificationModel;
    }
}
