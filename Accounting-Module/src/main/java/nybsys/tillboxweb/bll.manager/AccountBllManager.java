package nybsys.tillboxweb.bll.manager;


import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.Account;
import nybsys.tillboxweb.models.AccountModel;
import nybsys.tillboxweb.models.VMOpeningBalance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AccountBllManager extends BaseBll<Account> {
    private static final Logger log = LoggerFactory.getLogger(AccountBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(Account.class);
        Core.runTimeModelType.set(AccountModel.class);
    }

    public AccountModel saveAccountWithBusinessLogic(AccountModel accountModel) throws Exception {
        //AccountModel accountModel = new AccountModel();
        try {
            // accountModel = vmOpeningBalance.accountModel;

//            //check is this a default account
//            if (accountModel.getDefault() != null && accountModel.getDefault()) {
//                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
//                Core.clientMessage.get().message = MessageConstant.CAN_NOT_MODIFY_DEFAULT_ACCOUNT;
//                Core.clientMessage.get().userMessage = MessageConstant.CAN_NOT_MODIFY_DEFAULT_ACCOUNT;
//                return accountModel;
//            }

            // build account code
//            Integer accountCode = this.buildAccountCode(accountModel);
//            accountModel.setAccountCode(accountCode);

            //get account by account code to check uniqueness
            List<AccountModel> lstAccountModel;
            // lstAccountModel = this.getAccountByAccountCode(accountCode);
            lstAccountModel = this.getAccountByAccountCode(accountModel.getAccountCode());
            Core.clientMessage.get().messageCode = null;

            //save
            if (accountModel.getAccountID() == null || accountModel.getAccountID() == 0) {
                accountModel.setDefault(false);
                //check account code is unique or not
                if (lstAccountModel.size() > 0 && lstAccountModel.get(0).getAccountID() != accountModel.getAccountID()) {
                    // not unique
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.DUPLICATE_ACCOUNT_CODE;
                    Core.clientMessage.get().userMessage = MessageConstant.DUPLICATE_ACCOUNT_CODE;
                } else {
                    accountModel = this.save(accountModel);

                    if (accountModel == null) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_ACCOUNT;
                    }

                }

            } else //update
            {
                if (lstAccountModel.size() > 0) {
                    // check is account code conflict with itself
                    if (accountModel.getAccountID().intValue() == lstAccountModel.get(0).getAccountID().intValue()) {
                        accountModel = this.update(accountModel);
                        if (accountModel == null) {
                            Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                            Core.clientMessage.get().message = MessageConstant.FAILED_TO_UPDATE_ACCOUNT;
                        }

                    } else {
                        // account code is conflict with another account
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.ACCOUNT_IS_NOT_UNIQUE;
                        Core.clientMessage.get().userMessage = MessageConstant.ACCOUNT_IS_NOT_UNIQUE;
                    }
                } else {
                    // no account with this account code. you can update
                    accountModel = this.update(accountModel);
                    if (accountModel == null) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.FAILED_TO_UPDATE_ACCOUNT;
                    }
                }
            }
        } catch (Exception ex) {
            log.error("AccountBllManager -> saveAccountWithBusinessLogic got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return accountModel;
    }

    public AccountModel updateCombineAccount(AccountModel accountModelReq) throws Exception {
        AccountModel accountModel = new AccountModel();
        try {
            accountModel = accountModelReq;
            accountModel = this.update(accountModel);
            if (accountModel == null) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_UPDATE_ACCOUNT;
            }

        } catch (Exception ex) {
            log.error("AccountBllManager -> updateCombineAccount got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return accountModel;
    }

    public AccountModel getAccountByAccountID(Integer accountID) throws Exception {
        AccountModel accountModel = new AccountModel();
        try {
            accountModel = this.getById(accountID);
            if (accountModel == null) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.ACCOUNT_NOT_FOUND;
            }
        } catch (Exception ex) {
            log.error("AccountBllManager -> getAccountByAccountID got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return accountModel;
    }

    public List<AccountModel> getAccountByAccountTypeIDAndAccountClassificationIDAndBusinessID(Integer accountTypeID, Integer accountClassificationID, Integer businessID) throws Exception {
        List<AccountModel> lstAccountModel = new ArrayList<>();
        try {
            AccountModel whereCondition = new AccountModel();
            whereCondition.setBusinessID(businessID);
            whereCondition.setAccountClassificationID(accountClassificationID);
            whereCondition.setAccountTypeID(accountTypeID);
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());

            lstAccountModel = this.getAllByConditions(whereCondition);
            if (lstAccountModel.size() == 0) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.ACCOUNT_NOT_FOUND;
            }
        } catch (Exception ex) {
            log.error("AccountBllManager -> getAccountByAccountID got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstAccountModel;
    }

    public List<AccountModel> getAllAccount(Integer businessID) throws Exception {
        List<AccountModel> lstAccountModel = new ArrayList<>();
        AccountModel whereCondition = new AccountModel();

        try {
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());
            whereCondition.setBusinessID(businessID);
            lstAccountModel = this.getAllByConditions(whereCondition);
            if (lstAccountModel.size() == 0) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_ACCOUNT;
            }
        } catch (Exception ex) {
            log.error("AccountBllManager -> getAllAccount got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstAccountModel;
    }

    public List<AccountModel> getAllDeActiveAccount(Integer businessID) throws Exception {
        List<AccountModel> lstAccountModel = new ArrayList<>();
        AccountModel whereCondition = new AccountModel();

        try {
            whereCondition.setStatus(TillBoxAppEnum.Status.Inactive.get());
            whereCondition.setBusinessID(businessID);
            lstAccountModel = this.getAllByConditions(whereCondition);
            if (lstAccountModel.size() == 0) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_ACCOUNT;
            }
        } catch (Exception ex) {
            log.error("AccountBllManager -> getAllDeActiveAccount got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstAccountModel;
    }

    public List<AccountModel> getAccountByAccountCode(Integer accountCode) throws Exception {
        List<AccountModel> lstAccountModel = new ArrayList<>();
        AccountModel whereCondition = new AccountModel();
        try {
            whereCondition.setStatus(TillBoxAppEnum.Status.Active.get());
            whereCondition.setAccountCode(accountCode);
            lstAccountModel = this.getAllByConditions(whereCondition);
            if (lstAccountModel.size() == 0) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.ACCOUNT_NOT_FOUND;
            }
        } catch (Exception ex) {
            log.error("AccountBllManager -> getAccountByAccountCode got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstAccountModel;
    }

    public Integer buildAccountCode(AccountModel accountModel) {
        StringBuilder stringBuilder = new StringBuilder("");
        Integer accountCode = 0;
        try {
            stringBuilder.append(accountModel.getAccountClassificationID());
            stringBuilder.append(accountModel.getAccountTypeID());
            stringBuilder.append(accountModel.getAccountCode());
            accountCode = Integer.parseInt(stringBuilder.toString());

        } catch (Exception ex) {
            log.error("AccountBllManager -> buildAccountCode got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return accountCode;
    }

    public AccountModel getRootAccount(AccountModel accountModelReq) throws Exception {
        AccountModel accountModel = new AccountModel();
        try {
            accountModel = accountModelReq;
            while (accountModel.getParentAccountID() > 0) {
                accountModel = this.getAccountByAccountID(accountModel.getParentAccountID());
            }

        } catch (Exception ex) {
            log.error("AccountBllManager -> getRootAccount got exception");for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return accountModel;
    }
}
