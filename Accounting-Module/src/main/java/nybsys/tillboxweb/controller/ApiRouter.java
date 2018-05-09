/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 22-Dec-17
 * Time: 10:50 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */
package nybsys.tillboxweb.controller;


import nybsys.tillboxweb.BaseController;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.service.manager.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ApiRouter extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(ApiRouter.class);

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private AccountServiceManager accountServiceManager;

    @Autowired
    private BudgetServiceManager budgetServiceManager;

    @Autowired
    private MoneyTransferServiceManager moneyTransferServiceManager;

    @Autowired
    private CombineAccountServiceManager combineAccountServiceManager;

    @Autowired
    private OpeningBalanceServiceManager openingBalanceServiceManager;

    @Autowired
    private FinancialYearServiceManager financialYearServiceManager;

    @Autowired
    private JournalServiceManager journalServiceManager;


    @Override
    public ResponseMessage getResponseMessage(String serviceName, RequestMessage requestMessage) {
        this.checkSecurityAndExecuteService(serviceName,requestMessage);
        //close session factory
        //this.closeSession();
        return this.responseMessage;
    }

    protected void executeServiceManager(String serviceName, RequestMessage requestMessage) {
        switch (serviceName) {

            case "api/financialYear/save":
                this.responseMessage = this.financialYearServiceManager.save(requestMessage);
                log.info("account module -> api/financialYear/save executed");
                break;

            case "api/financialYear/get":
                this.responseMessage = this.financialYearServiceManager.getAllFinancialYear(requestMessage);
                log.info("account module -> api/financialYear/get executed");
                break;

            case "api/financialYear/current/get":
                this.responseMessage = this.financialYearServiceManager.getCurrentFinancialYearByBusinessID(requestMessage);
                log.info("account module -> api/financialYear/Current/get executed");
                break;

            case "api/financialYear/search":
                this.responseMessage = this.financialYearServiceManager.search(requestMessage);
                log.info("account module -> api/financialYear/search executed");
                break;

            case "api/journal/save":
                this.responseMessage = this.journalServiceManager.saveJournal(requestMessage);
                log.info("account module -> api/journal/save executed");
                break;

            case "api/journal/bypassEntryJournal/save":
                this.responseMessage = this.journalServiceManager.saveBypassEntryJournal(requestMessage);
                log.info("account module -> api/journal/bypassEntryJournal/save executed");
                break;

            case "api/journal/availableBalanceByAccount/get":
                this.responseMessage = this.journalServiceManager.getAvailableBalanceByAccount(requestMessage);
                log.info("account module -> api/journal/availableBalanceByAccount/get executed");
                break;

            case "api/journal/availableBalanceByPartyID/get":
                this.responseMessage = this.journalServiceManager.getAvailableBalancePartyID(requestMessage);
                log.info("account module -> api/journal/availableBalanceByPartyID/get executed");
                break;

            case "api/journal/supplierCurrentDue/get":
                this.responseMessage = this.journalServiceManager.getSupplierCurrentDue(requestMessage);
                log.info("account module -> api/journal/supplierCurrentDue/get executed");
                break;

            case "api/journal/customerCurrentDue/get":
                this.responseMessage = this.journalServiceManager.getCustomerCurrentDue(requestMessage);
                log.info("account module -> api/journal/customerCurrentDue/get executed");
                break;

            case "api/journal/search":
                this.responseMessage = this.journalServiceManager.search(requestMessage);
                log.info("account module -> api/journal/search executed");
                break;

            case "api/journal/delete":
                this.responseMessage = this.journalServiceManager.delete(requestMessage);
                log.info("account module -> api/journal/delete executed");
                break;

            case "api/journal/dataExistsExcludeOpeningBalance":
                this.responseMessage = this.journalServiceManager.dataExistsExcludeOpeningBalance(requestMessage);
                log.info("account module -> api/journal/dataExists executed");
                break;

            case "api/account/dropDownList":
                this.responseMessage = this.accountServiceManager.getAccountDropDownList(requestMessage);
                log.info("account module -> api/account/dropDownList executed");
                break;

            case "api/account/accountClassification/get":
                this.responseMessage = this.accountServiceManager.getAccountClassificationList(requestMessage);
                log.info("account module -> api/account/accountClassification/get executed");
                break;

            case "api/account/save":
                this.responseMessage = this.accountServiceManager.saveAccount(requestMessage);
                log.info("account module -> api/account/save executed");
                break;

            case "api/account/root/get":
                this.responseMessage = this.accountServiceManager.getRootAccount(requestMessage);
                log.info("account module -> api/account/root/get executed");
                break;

            case "api/account/get":
                this.responseMessage = this.accountServiceManager.getAccount(requestMessage);
                log.info("account module -> api/account/get executed");
                break;

            case "api/account/deActive/get":
                this.responseMessage = this.accountServiceManager.getDeActiveAccount(requestMessage);
                log.info("account module -> api/account/deActive/get executed");
                break;

            case "api/account/withOpeningBalance/get":
                this.responseMessage = this.accountServiceManager.getAllAccountWithOpeningBalance(requestMessage);
                log.info("account module -> api/account/withOpeningBalance/get executed");
                break;

            case "api/moneyTransfer/save":
                this.responseMessage = this.moneyTransferServiceManager.saveMoneyTransfer(requestMessage);
                log.info("account module -> api/moneyTransfer/save executed");
                break;

            case "api/moneyTransfer/get":
                this.responseMessage = this.moneyTransferServiceManager.getAllMoneyTransfer(requestMessage);
                log.info("account module -> api/moneyTransfer/get executed");
                break;

            case "api/budget/dropDownList/get":
                this.responseMessage = this.budgetServiceManager.getBudgetDropDown(requestMessage);
                log.info("account module -> api/budget/dropDownList/get executed");
                break;

            case "api/budget/detail/get":
                this.responseMessage = this.budgetServiceManager.getBudgetDetail(requestMessage);
                log.info("account module -> api/budget/detail/get executed");
                break;

            case "api/budget/detail/save":
                this.responseMessage = this.budgetServiceManager.saveBudgetDetail(requestMessage);
                log.info("account module -> api/budget/detail/save executed");
                break;

            case "api/combineAccount/save":
                this.responseMessage = this.combineAccountServiceManager.saveCombineAccount(requestMessage);
                log.info("account module -> api/combineAccount/save executed");
                break;

            case "api/combineAccount/search":
                this.responseMessage = this.combineAccountServiceManager.search(requestMessage);
                log.info("account module -> api/combineAccount/save executed");
                break;

            case "api/combineAccount/getByID":
                this.responseMessage = this.combineAccountServiceManager.getByBusinessID(requestMessage);
                log.info("account module -> api/combineAccount/getByID executed");
                break;

            case "api/openingBalance/save":
                this.responseMessage = this.openingBalanceServiceManager.save(requestMessage);
                log.info("account module -> api/openingBalance/save executed");
                break;

            case "api/openingBalance/getByAccountID":
                this.responseMessage = this.openingBalanceServiceManager.getOpeningBalanceByAccountID(requestMessage);
                log.info("account module -> api/openingBalance/getByAccountID executed");
                break;

            case "api/openingBalance/getByBusinessID":
                this.responseMessage = this.openingBalanceServiceManager.getOpeningBalanceByBusinessID(requestMessage);
                log.info("account module -> api/openingBalance/getByBusinessID executed");
                break;

            case "api/openingBalance/delete":
                this.responseMessage = this.openingBalanceServiceManager.delete(requestMessage);
                log.info("account module -> api/openingBalance/delete executed");
                break;

            default:
                System.out.println("account module -> INVALID REQUEST");
        }
    }

    //TODO: implement security check
    private boolean checkSecurity() {
        return true;
    }

}
