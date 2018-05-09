package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.bll.manager.CurrencyBllManager;
import nybsys.tillboxweb.bll.manager.CurrencyExchangeRateBllManager;
import nybsys.tillboxweb.bll.manager.CurrencySettingBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.coreModels.CurrencyExchangeRateModel;
import nybsys.tillboxweb.coreModels.CurrencyModel;
import nybsys.tillboxweb.models.VMCurrencySettingModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Date;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CurrencySettingServiceManager extends BaseService {

    private static final Logger log = LoggerFactory.getLogger(Core.class);
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @Autowired
    private CurrencySettingBllManager currencySettingBllManager;

    @Autowired
    private CurrencyBllManager currencyBllManager;

    @Autowired
    private CurrencyExchangeRateBllManager currencyExchangeRateBllManager;


    public ResponseMessage save(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        VMCurrencySettingModel vmCurrencySettingModel;
        try {

            if (requestMessage.businessID == null || requestMessage.businessID == 0) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SELECT_A_BUSINESS;
                Core.clientMessage.get().userMessage = MessageConstant.SELECT_A_BUSINESS;
                return responseMessage;
            }

            vmCurrencySettingModel = Core.getRequestObject(requestMessage, VMCurrencySettingModel.class);

            /*Set<ConstraintViolation<CurrencySettingModel>> violations = this.validator.validate(currencySettingModel);
            for (ConstraintViolation<CurrencySettingModel> violation : violations) {
                log.error(violation.getMessage());
            }*/

            //(1)save currency setting
            vmCurrencySettingModel.currencySettingModel.setBusinessID(requestMessage.businessID);
            this.currencySettingBllManager.saveOrUpdate(vmCurrencySettingModel.currencySettingModel);
            if (Core.clientMessage.get().messageCode != null) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.CURRENCY_SETTING_SAVE_FAILED;
                this.rollBack();
                return responseMessage;
            }

            //(2)save currency
            for (CurrencyModel currencyModel : vmCurrencySettingModel.lstCurrencyModel) {
                currencyModel.setBusinessID(requestMessage.businessID);
                currencyModel = this.currencyBllManager.saveOrUpdate(currencyModel);
                if (Core.clientMessage.get().messageCode != null) {
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    responseMessage.message = MessageConstant.CURRENCY_SETTING_SAVE_FAILED;
                    this.rollBack();
                    return responseMessage;
                }

                //(3)save currency exchange rate
                for (CurrencyExchangeRateModel currencyExchangeRateModel : vmCurrencySettingModel.lstCurrencyExchangeRateModel) {
                    currencyExchangeRateModel.setCurrencyID(currencyModel.getCurrencyID());
                    this.currencyExchangeRateBllManager.saveOrUpdate(currencyExchangeRateModel);
                    if (Core.clientMessage.get().messageCode != null) {
                        responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        responseMessage.message = MessageConstant.CURRENCY_SETTING_SAVE_FAILED;
                        this.rollBack();
                        return responseMessage;
                    }
                }
            }

            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
            responseMessage.message = MessageConstant.CURRENCY_SETTING_SAVE_SUCCESSFULLY;
            this.commit();

        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            this.rollBack();
            log.error("CurrencySettingServiceManager -> save got exception");
        }
        return responseMessage;
    }


    public ResponseMessage getByBusinessID(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        VMCurrencySettingModel vmCurrencySettingModel = new VMCurrencySettingModel();
        try {
            //(1)get currency setting
            vmCurrencySettingModel.currencySettingModel = this.currencySettingBllManager.getCurrencySetting(requestMessage.businessID);

            //(2)get currency
            vmCurrencySettingModel.lstCurrencyModel = this.currencyBllManager.getAllCurrencyByBusinessID(requestMessage.businessID);

            //(3)get currency exchange rate
            for (CurrencyModel currencyModel : vmCurrencySettingModel.lstCurrencyModel) {
                CurrencyExchangeRateModel currencyExchangeRateModel;
                currencyExchangeRateModel = this.currencyExchangeRateBllManager.getExchangeRateByCurrencyID(currencyModel.getCurrencyID(),new Date());
                vmCurrencySettingModel.lstCurrencyExchangeRateModel.add(currencyExchangeRateModel);
            }

            responseMessage.responseObj = vmCurrencySettingModel;
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

        } catch (Exception ex) {
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.WriteExceptionLog(ex);
            log.error("CurrencySettingServiceManager -> getByBusinessID got exception");
        }
        return responseMessage;
    }


}
