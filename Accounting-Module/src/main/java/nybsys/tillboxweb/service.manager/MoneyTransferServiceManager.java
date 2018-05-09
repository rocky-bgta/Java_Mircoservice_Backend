package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.bll.manager.AccountBllManager;
import nybsys.tillboxweb.bll.manager.MoneyTransferBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.coreConstant.CurrencyConstant;
import nybsys.tillboxweb.coreModels.CurrencyModel;
import nybsys.tillboxweb.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MoneyTransferServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(MoneyTransferServiceManager.class);
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Autowired
    private MoneyTransferBllManager moneyTransferBllManager;

    @Autowired
    private OpeningBalanceServiceManager openingBalanceServiceManager;


    public ResponseMessage saveMoneyTransfer(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        MoneyTransferModel moneyTransferModel;
        CurrencyModel currencyModel;
        try {
            //check business is selected
            if (requestMessage.businessID == null || requestMessage.businessID == 0) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.SELECT_A_BUSINESS;
                Core.clientMessage.get().userMessage = MessageConstant.SELECT_A_BUSINESS;
                return responseMessage;
            }

            //get base currency and exchange rate
            currencyModel = this.openingBalanceServiceManager.getBaseCurrency();
            if (currencyModel == null) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = CurrencyConstant.BASE_CURRENT_NOT_FOUND;
                return responseMessage;
            }

            //check entry currency is present if not base currency will be entry currency
            if (requestMessage.entryCurrencyID == null || requestMessage.entryCurrencyID == 0) {
                requestMessage.entryCurrencyID = currencyModel.getCurrencyID();
            }

            moneyTransferModel = Core.getRequestObject(requestMessage, MoneyTransferModel.class);

             /*Set<ConstraintViolation<MoneyTransferModel>> violations = this.validator.validate(this.moneyTransferModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            moneyTransferModel = this.moneyTransferBllManager.saveOrUpdateWithBusinessLogic(moneyTransferModel,requestMessage.businessID,currencyModel,requestMessage.entryCurrencyID);

            responseMessage.responseObj = moneyTransferModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SUCCESSFULLY_SAVE_MONEY_TRANSFER;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.FAILED_TO_SAVE_MONEY_TRANSFER;
                }else
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("MoneyTransferServiceManager -> saveMoneyTransfer got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.commit();
        }
        return responseMessage;
    }

    public ResponseMessage getAllMoneyTransfer(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();

        try {
            Integer businessID = requestMessage.businessID;

            responseMessage.responseObj = this.moneyTransferBllManager.getAllMoneyTransfer(businessID);

            if(Core.clientMessage.get().messageCode != null)
            {
                responseMessage.message = MessageConstant.FAILED_TO_GET_MONEY_TRANSFER;
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }else
            {
                responseMessage.message = MessageConstant.SUCCESSFULLY_GET_MONEY_TRANSFER;
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
            }

        } catch (Exception ex) {
            log.error("MoneyTransferServiceManager -> getMoneyTransfer got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }
        return responseMessage;
    }


}
