/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/03/2018
 * Time: 5:09
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.bll.manager.DiscountGiveMoneyBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.models.DiscountGiveMoneyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DiscountGiveMoneyServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(DiscountGiveMoneyServiceManager.class);
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    @Autowired
    private DiscountGiveMoneyBllManager discountGiveMoneyBllManager;

    public ResponseMessage saveDiscountGiveMoney(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        DiscountGiveMoneyModel discountGiveMoneyModel = new DiscountGiveMoneyModel();
        try {
            discountGiveMoneyModel = Core.getRequestObject(requestMessage, DiscountGiveMoneyModel.class);

            /*Set<ConstraintViolation<DiscountGiveMoneyModel>> violations = this.validator.validate(discountGiveMoneyModel);
            if (violations.size() > 0) {
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, MessageConstant.modelViolation, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
                return responseMessage;
            }*/

            discountGiveMoneyModel = this.discountGiveMoneyBllManager.saveOrUpdate(discountGiveMoneyModel);

            responseMessage.responseObj = discountGiveMoneyModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.DISCOUNT_GIVE_MONEY_SAVE_SUCCESSFULLY;
                this.commit();
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if(Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.DISCOUNT_GIVE_MONEY_SAVE_FAILED;
                }else
                {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("DiscountGiveMoneyServiceManager -> saveDiscountGiveMoney got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }
    public ResponseMessage searchDiscountGiveMoney(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<DiscountGiveMoneyModel> lstDiscountGiveMoneyModel = new ArrayList<>();
        DiscountGiveMoneyModel discountGiveMoneyModel = new DiscountGiveMoneyModel();
        try {
            discountGiveMoneyModel = Core.getRequestObject(requestMessage, DiscountGiveMoneyModel.class);

            lstDiscountGiveMoneyModel = this.discountGiveMoneyBllManager.searchDiscountGiveMoney(discountGiveMoneyModel);

            responseMessage.responseObj = lstDiscountGiveMoneyModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.DISCOUNT_GIVE_MONEY_GET_SUCCESSFULLY;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.DISCOUNT_GIVE_MONEY_GET_FAILED;
            }

        } catch (Exception ex) {
            log.error("DiscountGiveMoneyServiceManager -> searchDiscountGiveMoney got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }
    public ResponseMessage deleteDiscountGiveMoney(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        DiscountGiveMoneyModel discountGiveMoneyModel = new DiscountGiveMoneyModel();
        try {
            discountGiveMoneyModel = Core.getRequestObject(requestMessage, DiscountGiveMoneyModel.class);

            discountGiveMoneyModel = this.discountGiveMoneyBllManager.deleteDiscountGiveMoney(discountGiveMoneyModel);

            responseMessage.responseObj = discountGiveMoneyModel;
            if(Core.clientMessage.get().messageCode == null)
            {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.DISCOUNT_GIVE_MONEY_DELETE_SUCCESSFULLY;
            }else
            {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = MessageConstant.DISCOUNT_GIVE_MONEY_DELETE_FAILED;
            }

        } catch (Exception ex) {
            log.error("DiscountGiveMoneyServiceManager -> deleteDiscountGiveMoney got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }
}
