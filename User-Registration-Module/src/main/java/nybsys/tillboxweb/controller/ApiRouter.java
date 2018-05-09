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
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ApiRouter extends BaseController {

    private final Logger log = LoggerFactory.getLogger(ApiRouter.class);

    //private String loginToken;

    @Autowired
    private UserServiceManager userServiceManager;

    @Autowired
    private BusinessServiceManager businessServiceManager;

    @Autowired
    private BusinessDetailsServiceManager businessDetailsServiceManager;

    @Autowired
    private CountryServiceManager countryServiceManager;

    @Autowired
    private GstSettingServiceManager gstSettingServiceManager;

    @Autowired
    private TaxCodeServiceManager taxCodeServiceManager;

    @Autowired
    private UserInvitationServiceManager userInvitationServiceManager;

    @Autowired
    private PasswordServiceManager passwordServiceManager;

    @Autowired
    private CurrencyServiceManager currencyServiceManager;

    @Autowired
    private CurrencyExchangeRateServiceManager currencyExchangeRateServiceManager;

    @Autowired
    private ItemSettingServiceManager itemSettingServiceManager;

    @Autowired
    private OutStandingBalanceServiceManager outStandingBalanceServiceManager;

    @Autowired
    private RoundingServiceManager roundingServiceManager;

    @Autowired
    private CustomerAndSupplierSettingServiceManager customerAndSupplierSettingServiceManager;

    @Autowired
    private BrandSettingServiceManager brandSettingServiceManager;

    @Autowired
    private CurrencySettingServiceManager currencySettingServiceManager;

    @Autowired
    private RegionalSettingServiceManager regionalSettingServiceManager;

    private AdditionalCompanyInformationModelServiceManager additionalCompanyInformationModelServiceManager = new AdditionalCompanyInformationModelServiceManager();
    private CompanyDetailServiceManager companyDetailServiceManager = new CompanyDetailServiceManager();
    private DocumentDescriptionServiceManager documentDescriptionServiceManager = new DocumentDescriptionServiceManager();
    private DocumentNumberServiceManager documentNumberServiceManager = new DocumentNumberServiceManager();
    private DocumentMessageServiceManager documentMessageServiceManager = new DocumentMessageServiceManager();
    private VATRateServiceManager vatRateServiceManager = new VATRateServiceManager();
    private VATSystemServiceManager vatSystemServiceManager = new VATSystemServiceManager();
    private CompanyAddressServiceManager companyAddressServiceManager = new CompanyAddressServiceManager();
    private InvoiceStatementLayoutTypeServiceManager invoiceStatementLayoutTypeServiceManager = new InvoiceStatementLayoutTypeServiceManager();
    private StatementMessageServiceManager statementMessageServiceManager = new StatementMessageServiceManager();
    private UserDefineFieldServiceManager userDefineFieldServiceManager = new UserDefineFieldServiceManager();
    private EmailSignatureServiceManager emailSignatureServiceManager = new EmailSignatureServiceManager();


    @Override
    public ResponseMessage getResponseMessage(String serviceName, RequestMessage requestMessage) {

        this.checkSecurityAndExecuteService(serviceName, requestMessage);
        return this.responseMessage;
    }

    protected void executeServiceManager(String serviceName, RequestMessage requestMessage) {
        switch (serviceName) {


            case "api/userRegistration/customerAndSupplierSetting/save":
                this.responseMessage = this.customerAndSupplierSettingServiceManager.saveOrUpdate(requestMessage);
                break;

            case "api/userRegistration/customerAndSupplierSetting/search":
                this.responseMessage = this.customerAndSupplierSettingServiceManager.search(requestMessage);
                break;

            case "api/userRegistration/rounding/save":
                this.responseMessage = this.roundingServiceManager.saveOrUpdate(requestMessage);
                break;

            case "api/userRegistration/rounding/search":
                this.responseMessage = this.roundingServiceManager.search(requestMessage);
                break;

            case "api/userRegistration/outStandingBalance/save":
                this.responseMessage = this.outStandingBalanceServiceManager.saveOrUpdate(requestMessage);
                break;

            case "api/userRegistration/outStandingBalance/search":
                this.responseMessage = this.outStandingBalanceServiceManager.search(requestMessage);
                break;


            case "api/userRegistration/itemSetting/save":
                this.responseMessage = this.itemSettingServiceManager.saveOrUpdate(requestMessage);
                break;

            case "api/userRegistration/itemSetting/search":
                this.responseMessage = this.itemSettingServiceManager.search(requestMessage);
                break;

         /*   case "api/userRegistration/itemSetting/getById":
                this.responseMessage = this.itemSettingServiceManager.getById(requestMessage);
                break;*/

            case "api/userRegistration/regionalSetting/save":
                this.responseMessage = this.regionalSettingServiceManager.saveOrUpdate(requestMessage);
                break;

            case "api/userRegistration/regionalSetting/search":
                this.responseMessage = this.regionalSettingServiceManager.search(requestMessage);
                break;

/*

            case "api/userRegistration/regionalSetting/getById":
                this.responseMessage = this.regionalSettingServiceManager.getById(requestMessage);
                break;
*/

            case "api/user/signUp":
                this.responseMessage = this.userServiceManager.signUpInvitation(requestMessage);
                log.info("User Registration Module -> api/user/signUp executed");
                break;

            case "api/user/signUpConfirmation":
                this.responseMessage = this.userServiceManager.signUpUser(requestMessage);
                log.info("User Registration Module -> api/user/signUpConfirmation executed");
                break;

            case "api/user/login":
                this.responseMessage = this.userServiceManager.loginUser(requestMessage);
                //this.loginToken = this.responseMessage.token;
                log.info("User Registration Module -> api/user/login executed");
                break;

            case "api/user/getUserListByBusinessID":
                this.responseMessage = this.userServiceManager.getAllUserByBusinessID(requestMessage);
                log.info("User Registration Module -> api/user/getUserListByBusinessID executed");
                break;

            case "api/user/getUserListActiveAndInactiveAndInvited":
                this.responseMessage = this.userServiceManager.getUserListActiveAndInactiveAndInvited(requestMessage);
                log.info("User Registration Module -> api/user/getUserListActiveAndInactiveAndInvited executed");
                break;

            case "api/user/editUser":
                this.responseMessage = this.userServiceManager.editUser(requestMessage);
                log.info("User Registration Module -> api/user/editUser executed");
                break;

            case "api/user/activateUser":
                this.responseMessage = this.userServiceManager.activateUser(requestMessage);
                log.info("User Registration Module -> api/user/inActivateUser executed");
                break;

            case "api/user/inActivateUser":
                this.responseMessage = this.userServiceManager.inActivateUser(requestMessage);
                log.info("User Registration Module -> api/user/inActivateUser executed");
                break;

            case "api/user/checkInterCom":
                this.responseMessage = this.userServiceManager.checkInterCom(requestMessage);
                log.info("Inter module communication executed");
                break;

            case "api/user/historyTest":
                this.responseMessage = this.userServiceManager.historyTest(requestMessage);
                log.info("api/user/historyTest executed");
                break;

            case "api/business/save":
                this.responseMessage = this.businessServiceManager.save(requestMessage);
                log.info("User Registration Module -> api/business/save executed");
                break;

            case "api/business/getByUserID":
                this.responseMessage = this.businessServiceManager.getBusinessByUserID(requestMessage);
                log.info("User Registration Module -> api/business/getByUserID executed");
                break;

            case "api/business/getByID":
                this.responseMessage = this.businessServiceManager.getByID(requestMessage);
                log.info("User Registration Module -> api/business/getByID executed");
                break;

            case "api/business/select":
                this.responseMessage = this.businessServiceManager.selectBusiness(requestMessage);
                log.info("User Registration Module -> api/business/select executed");
                break;

            case "api/businessDetails/save":
                this.responseMessage = this.businessDetailsServiceManager.save(requestMessage);
                log.info("User Registration Module -> api/user/deleteByCondition executed");
                break;

            case "api/businessDetails/search":
                this.responseMessage = this.businessDetailsServiceManager.search(requestMessage);
                log.info("User Registration Module -> api/business/getByUserID executed");
                break;

            case "api/businessDetails/getByID":
                this.responseMessage = this.businessDetailsServiceManager.getByID(requestMessage);
                log.info("User Registration Module -> api/business/getByID executed");
                break;

            case "api/country/save":
                this.responseMessage = this.countryServiceManager.save(requestMessage);
                log.info("User Registration Module -> api/country/save executed");
                break;

            case "api/country/getByID":
                this.responseMessage = this.countryServiceManager.getByID(requestMessage);
                log.info("User Registration Module -> api/country/getByID executed");
                break;

            case "api/country/getAll":
                this.responseMessage = this.countryServiceManager.getAll(requestMessage);
                log.info("User Registration Module -> api/country/getAll executed");
                break;

            case "api/gstSettings/save":
                this.responseMessage = this.gstSettingServiceManager.save(requestMessage);
                log.info("User Registration Module -> api/gstSettings/sav executed");
                break;

            case "api/gstSettings/getByID":
                this.responseMessage = this.gstSettingServiceManager.getByID(requestMessage);
                log.info("User Registration Module -> api/gstSettings/getByID executed");
                break;

            case "api/gstSettings/getByBusinessID":
                this.responseMessage = this.gstSettingServiceManager.getByBusinessID(requestMessage);
                log.info("User Registration Module -> api/gstSettings/getByBusinessID executed");
                break;

            case "api/taxCode/save":
                this.responseMessage = this.taxCodeServiceManager.saveTaxCode(requestMessage);
                log.info("User Registration Module -> api/taxCode/save executed");
                break;

            case "api/taxCode/taxType/get":
                this.responseMessage = this.taxCodeServiceManager.getTaxCodeByTaxType(requestMessage);
                log.info("User Registration Module -> api/taxCode/taxType/get executed");
                break;

            case "api/taxCode/get":
                this.responseMessage = this.taxCodeServiceManager.getTaxCode(requestMessage);
                log.info("User Registration Module -> api/taxCode/get executed");
                break;

            case "api/userInvite/invite":
                this.responseMessage = this.userInvitationServiceManager.invite(requestMessage);
                log.info("User Registration Module -> api/userInvite/invite executed");
                break;

            case "api/userInvite/reInvite":
                this.responseMessage = this.userInvitationServiceManager.reInvite(requestMessage);
                log.info("User Registration Module -> api/userInvite/reInvite executed");
                break;

            case "api/userInvite/removeInvitation":
                this.responseMessage = this.userInvitationServiceManager.removeInvitation(requestMessage);
                log.info("User Registration Module -> api/userInvite/removeInvitation executed");
                break;

            case "api/userInvite/createUserWithBusinessId":
                this.responseMessage = this.userInvitationServiceManager.createUserWithBusinessId(requestMessage);
                log.info("User Registration Module -> api/userInvite/removeInvitation executed");
                break;

            case "api/userInvite/search":
                this.responseMessage = this.userInvitationServiceManager.getUserByToken(requestMessage);
                log.info("User Registration Module -> api/userInvite/search executed");
                break;

            case "api/forgetPassword/userID/get":
                this.responseMessage = this.passwordServiceManager.getByUserID(requestMessage);
                log.info("User Registration Module -> api/forgetPassword/userID/get executed");
                break;

            case "api/forgetPassword/update":
                this.responseMessage = this.passwordServiceManager.forgetPasswordUpdate(requestMessage);
                log.info("User Registration Module -> api/forgetPassword/update executed");
                break;

            case "api/changePassword/update":
                this.responseMessage = this.passwordServiceManager.changePasswordUpdate(requestMessage);
                log.info("User Registration Module -> api/changePassword/update executed");
                break;

            case "api/currency/save":
                this.responseMessage = this.currencyServiceManager.save(requestMessage);
                log.info("User Registration Module -> api/currency/save executed");
                break;

            case "api/currency/search":
                this.responseMessage = this.currencyServiceManager.search(requestMessage);
                log.info("User Registration Module -> api/currency/search executed");
                break;

            case "api/currency/getBaseCurrency":
                this.responseMessage = this.currencyServiceManager.getBaseCurrency(requestMessage);
                log.info("User Registration Module -> api/currency/getBaseCurrentCurrency executed");
                break;

            case "api/currency/select":
                this.responseMessage = this.currencyServiceManager.changeCurrentCurrency(requestMessage);
                log.info("User Registration Module -> api/currency/select executed");
                break;

            case "api/currency/getExchangeRateByCurrency":
                this.responseMessage = this.currencyServiceManager.search(requestMessage);
                log.info("User Registration Module -> api/currency/getExchangeRateByCurrency executed");
                break;

            case "api/currency/getCurrencyExchangeRate":
                this.responseMessage = this.currencyServiceManager.getCurrencyExchangeRate(requestMessage);
                log.info("User Registration Module -> api/currency/getCurrencyExchangeRate executed");
                break;

            case "api/currencySetting/save":
                this.responseMessage = this.currencySettingServiceManager.save(requestMessage);
                log.info("User Registration Module -> api/currencySetting/save executed");
                break;

            case "api/currencySetting/getByBusinessID":
                this.responseMessage = this.currencySettingServiceManager.getByBusinessID(requestMessage);
                log.info("User Registration Module -> api/currencySetting/getByBusinessID executed");
                break;

            case "api/currencyExchangeRate/save":
                this.responseMessage = this.currencyExchangeRateServiceManager.save(requestMessage);
                log.info("User Registration Module -> api/currencyExchangeRate/save executed");
                break;
            case "api/additionalCompanyInformation/save":
                this.responseMessage = this.additionalCompanyInformationModelServiceManager.save(requestMessage);
                log.info("User Registration Module -> api/additionalCompanyInformationModelServiceManager/save executed");
                break;
            case "api/additionalCompanyInformation/search":
                this.responseMessage = this.additionalCompanyInformationModelServiceManager.search(requestMessage);
                log.info("User Registration Module -> api/additionalCompanyInformationModelServiceManager/search executed");
                break;
            case "api/companyDetail/save":
                this.responseMessage = this.companyDetailServiceManager.save(requestMessage);
                log.info("User Registration Module -> api/companyDetailServiceManager/save executed");
                break;
            case "api/companyDetail/search":
                this.responseMessage = this.companyDetailServiceManager.search(requestMessage);
                log.info("User Registration Module -> api/companyDetailServiceManager/search executed");
                break;

            case "api/customerAndSupplierSetting/search":
                this.responseMessage = this.customerAndSupplierSettingServiceManager.search(requestMessage);
                log.info("User Registration Module -> api/customerAndSupplierSettingServiceManager/search executed");
                break;

            case "api/documentDescription/save":
                this.responseMessage = this.documentDescriptionServiceManager.save(requestMessage);
                log.info("User Registration Module -> api/documentDescriptionServiceManager/save executed");
                break;
            case "api/documentDescription/search":
                this.responseMessage = this.documentDescriptionServiceManager.search(requestMessage);
                log.info("User Registration Module -> api/documentDescriptionServiceManager/search executed");
                break;
            case "api/documentNumber/save":
                this.responseMessage = this.documentNumberServiceManager.save(requestMessage);
                log.info("User Registration Module -> api/documentNumberServiceManager/save executed");
                break;
            case "api/documentNumber/search":
                this.responseMessage = this.documentNumberServiceManager.search(requestMessage);
                log.info("User Registration Module -> api/documentNumberServiceManager/search executed");
                break;
            case "api/statementMessage/save":
                this.responseMessage = this.statementMessageServiceManager.save(requestMessage);
                log.info("User Registration Module -> api/statementMessageServiceManager/save executed");
                break;
            case "api/statementMessage/search":
                this.responseMessage = this.statementMessageServiceManager.search(requestMessage);
                log.info("User Registration Module -> api/statementMessageServiceManager/search executed");
                break;
            case "api/vATRate/save":
                this.responseMessage = this.vatRateServiceManager.save(requestMessage);
                log.info("User Registration Module -> api/vatRateServiceManager/save executed");
                break;
            case "api/vATRate/search":
                this.responseMessage = this.vatRateServiceManager.search(requestMessage);
                log.info("User Registration Module -> api/vatRateServiceManager/search executed");
                break;
            case "api/vATSystem/save":
                this.responseMessage = this.vatSystemServiceManager.save(requestMessage);
                log.info("User Registration Module -> api/vatSystemServiceManager/save executed");
                break;
            case "api/vATSystem/search":
                this.responseMessage = this.vatSystemServiceManager.search(requestMessage);
                log.info("User Registration Module -> api/vatSystemServiceManager/search executed");
                break;
            case "api/companyAddress/save":
                this.responseMessage = this.companyAddressServiceManager.save(requestMessage);
                log.info("User Registration Module -> api/companyAddressServiceManager/save executed");
                break;
            case "api/companyAddress/search":
                this.responseMessage = this.companyAddressServiceManager.search(requestMessage);
                log.info("User Registration Module -> api/companyAddressServiceManager/search executed");
                break;
            case "api/invoiceStatementLayout/save":
                this.responseMessage = this.invoiceStatementLayoutTypeServiceManager.save(requestMessage);
                log.info("User Registration Module -> api/companyAddressServiceManager/save executed");
                break;
            case "api/invoiceStatementLayout/search":
                this.responseMessage = this.invoiceStatementLayoutTypeServiceManager.search(requestMessage);
                log.info("User Registration Module -> api/companyAddressServiceManager/search executed");
                break;

            case "api/userRegistration/brandingSetting/save":
                this.responseMessage = this.brandSettingServiceManager.save(requestMessage);
                log.info("User Registration Module -> api/userRegistration/brandingSetting/save executed");
                break;

            case "api/userRegistration/brandingSetting/search":
                this.responseMessage = this.brandSettingServiceManager.getByBusinessID(requestMessage);
                log.info("User Registration Module -> api/userRegistration/brandingSetting/search executed");
                break;

            case "api/documentMessage/save":
                this.responseMessage = this.documentMessageServiceManager.save(requestMessage);
                log.info("User Registration Module -> api/userRegistration/brandingSetting/save executed");
                break;

            case "api/documentMessage/search":
                this.responseMessage = this.documentMessageServiceManager.search(requestMessage);
                log.info("User Registration Module -> api/userRegistration/brandingSetting/search executed");
                break;
            case "api/userDefineField/save":
                this.responseMessage = this.userDefineFieldServiceManager.save(requestMessage);
                log.info("User Registration Module -> api/userRegistration/brandingSetting/save executed");
                break;

            case "api/userDefineField/search":
                this.responseMessage = this.userDefineFieldServiceManager.search(requestMessage);
                log.info("User Registration Module -> api/userRegistration/brandingSetting/search executed");
                break;
            case "api/emailSignature/save":
                this.responseMessage = this.emailSignatureServiceManager.save(requestMessage);
                log.info("User Registration Module -> api/emailSignature/search executed");
                break;
            case "api/emailSignature/search":
                this.responseMessage = this.emailSignatureServiceManager.search(requestMessage);
                log.info("User Registration Module -> api/emailSignature/search executed");
                break;
            default:
                log.warn("INVALID REQUEST");
        }
    }
}
