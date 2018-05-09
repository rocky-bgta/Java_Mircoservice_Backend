/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/8/2018
 * Time: 10:42 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.bll.manager.CombineAccountBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.coreConstant.CurrencyConstant;
import nybsys.tillboxweb.coreModels.CurrencyModel;
import nybsys.tillboxweb.models.CombineAccountModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CombineAccountServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(AccountServiceManager.class);
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Autowired
    private CombineAccountBllManager combineAccountBllManager;

    private OpeningBalanceServiceManager openingBalanceServiceManager = new OpeningBalanceServiceManager();

    public ResponseMessage saveCombineAccount(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        CombineAccountModel combineAccountModel = new CombineAccountModel();
        CurrencyModel currencyModel;
        try {

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

            combineAccountModel = Core.getRequestObject(requestMessage, CombineAccountModel.class);
            combineAccountModel.setBusinessID(requestMessage.businessID);

            /*Set<ConstraintViolation<AccountModel>> violations = this.validator.validate(this.accountModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            this.combineAccountBllManager.saveCombineAccount(combineAccountModel, currencyModel, requestMessage.entryCurrencyID);

            if (Core.clientMessage.get().messageCode != null) {
                if (Core.clientMessage.get().userMessage == "") {
                    responseMessage.message = MessageConstant.FAILED_TO_COMBINE_ACCOUNT;
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                } else {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                    responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                }
                this.rollBack();

            } else {
                responseMessage.message = MessageConstant.SUCCESSFULLY_COMBINE_ACCOUNT;
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                this.commit();
            }

        } catch (Exception ex) {
            log.error("CombineAccountBllManager -> save combine account got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }
        return responseMessage;
    }


    public ResponseMessage getByID(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        CombineAccountModel combineAccountModel = new CombineAccountModel();
        try {
            combineAccountModel = Core.getRequestObject(requestMessage, CombineAccountModel.class);

            combineAccountModel = this.combineAccountBllManager.getById(combineAccountModel.getCombineAccountID());

            responseMessage.responseObj = combineAccountModel;
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

        } catch (Exception ex) {
            log.error("CombineAccountBllManager -> getByID got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }
        return responseMessage;
    }

    public ResponseMessage search(RequestMessage requestMessage) {
        List<CombineAccountModel> lstCombineAccountModel;
        ResponseMessage responseMessage = new ResponseMessage();
        CombineAccountModel combineAccountModel = new CombineAccountModel();
        try {
            combineAccountModel = Core.getRequestObject(requestMessage, CombineAccountModel.class);
            lstCombineAccountModel = this.combineAccountBllManager.getAllByConditions(combineAccountModel);
            if (lstCombineAccountModel.size() > 0) {
                responseMessage.responseObj = lstCombineAccountModel;
            }
            responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
        } catch (Exception ex) {
            log.error("CombineAccountBllManager -> search got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }
        return responseMessage;
    }

    public ResponseMessage getByBusinessID(RequestMessage requestMessage) {
        List<CombineAccountModel> lstCombineAccountModel;
        ResponseMessage responseMessage = new ResponseMessage();
        CombineAccountModel combineAccountModel = new CombineAccountModel();
        try {
            combineAccountModel.setBusinessID(requestMessage.businessID);
            combineAccountModel.setStatus(TillBoxAppEnum.Status.Active.get());

            lstCombineAccountModel = this.combineAccountBllManager.getAllByConditions(combineAccountModel);

            responseMessage.responseObj = lstCombineAccountModel;
            if (lstCombineAccountModel.size() > 0) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

            } else {
                responseMessage.message =  MessageConstant.FAILED_TO_GET_COMBINE_ACCOUNT;
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("CombineAccountBllManager -> search got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }
        return responseMessage;
    }

}
