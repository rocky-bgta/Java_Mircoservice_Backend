package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.coreEntities.AccountType;
import nybsys.tillboxweb.coreModels.AccountTypeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AccountTypeBllManager extends BaseBll<AccountType> {
    private static final Logger log = LoggerFactory.getLogger(AccountTypeBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(AccountType.class);
        Core.runTimeModelType.set(AccountTypeModel.class);
    }

    public AccountTypeModel saveAccountType(AccountTypeModel accountTypeModelReq, String userID) throws Exception {
        AccountTypeModel accountTypeModel = new AccountTypeModel();
        accountTypeModel = accountTypeModelReq;
        try {
            accountTypeModel = this.save(accountTypeModel);
            if (accountTypeModel == null) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_ACCOUNT_TYPE;
            }
        } catch (Exception ex) {
            log.error("AccountTypeBllManager -> saveAccountType got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return accountTypeModel;
    }

    private AccountTypeModel updateAccountType(AccountTypeModel accountTypeModelReq, String userID) throws Exception {
        AccountTypeModel accountTypeModel = new AccountTypeModel();
        accountTypeModel = accountTypeModelReq;
        try {
            accountTypeModel.setUpdatedBy(userID);
            accountTypeModel.setUpdatedDate(new Date());
            accountTypeModel = this.update(accountTypeModel);
            if (accountTypeModel == null) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_UPDATE_ACCOUNT_TYPE;
            }
        } catch (Exception ex) {
            log.error("AccountTypeBllManager -> updateAccountType got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return accountTypeModel;
    }

    public List<AccountTypeModel> getAllAccountType() throws Exception {
        List<AccountTypeModel> lstAccountTypeModel = new ArrayList<>();
        AccountTypeModel whereCondition = new AccountTypeModel();
        try {
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());
            lstAccountTypeModel = this.getAllByConditions(whereCondition);
            if (lstAccountTypeModel.size() == 0) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_ACCOUNT_TYPE;
            }
        } catch (Exception ex) {
            log.error("AccountTypeBllManager -> getAllAccountType got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstAccountTypeModel;
    }
    public AccountTypeModel getAccountTypeByID(Integer accountTypeID) {
        AccountTypeModel accountTypeModel = new AccountTypeModel();
        try {
            accountTypeModel = this.getById(accountTypeID,TillBoxAppEnum.Status.Active.get());
            if (accountTypeModel == null) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_ACCOUNT_TYPE;
            }
        } catch (Exception ex) {
            log.error("AccountTypeBllManager -> getAccountTypeByID got exception: "+ex.getMessage());
        }
        return accountTypeModel;
    }
}