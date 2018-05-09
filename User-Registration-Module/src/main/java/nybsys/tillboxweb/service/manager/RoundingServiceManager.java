/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 19-Apr-18
 * Time: 12:40 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.BaseServiceManager;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.MessageModel.BllResponseMessage;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import nybsys.tillboxweb.models.RoundingModel;
import nybsys.tillboxweb.bll.manager.RoundingBllManager;

import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class RoundingServiceManager extends BaseService implements BaseServiceManager {

    private static final Logger log = LoggerFactory.getLogger(RoundingServiceManager.class);

    @Autowired
    private RoundingBllManager roundingBllManager;

    @Override
    public ResponseMessage saveOrUpdate(RequestMessage requestMessage) {
        ResponseMessage responseMessage = this.getDefaultResponseMessage();
        BllResponseMessage bllResponseMessage;
        try {
            bllResponseMessage = this.roundingBllManager.saveOrUpdate(requestMessage);
            if (bllResponseMessage.responseCode == TillBoxAppConstant.SUCCESS_CODE) {
                responseMessage.responseObj = bllResponseMessage.responseObject;
                responseMessage.responseCode = Core.clientMessage.get().messageCode;
                responseMessage.message = Core.clientMessage.get().userMessage;
                this.commit();
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = Core.clientMessage.get().message;
                this.rollBack();
            }
        } catch (Exception e) {
            log.error("RoundingServiceManager -> saveOrUpdate got exception");
            this.rollBack();
            if (Core.clientMessage.get().userMessage != null)
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, Core.clientMessage.get().userMessage, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            else
                responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(e);
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage search(RequestMessage requestMessage) {
        ResponseMessage responseMessage = this.getDefaultResponseMessage();
        List<RoundingModel> finedRoundingList;
        try {
            RoundingModel reqRoundingModel =
                    Core.getRequestObject(requestMessage, RoundingModel.class);
            finedRoundingList = this.roundingBllManager.search(reqRoundingModel);
            if (Core.clientMessage.get().messageCode == TillBoxAppConstant.SUCCESS_CODE) {
                responseMessage.responseObj = finedRoundingList;
                responseMessage.message = "Find the request Rounding"; //Core.wrapperModel.get().userMessage;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = Core.clientMessage.get().message;
            }
        } catch (Exception e) {
            log.error("RoundingServiceManager -> search got exception");

            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(e);
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage delete(RequestMessage requestMessage) {
        ResponseMessage responseMessage = this.getDefaultResponseMessage();
        RoundingModel deletedRoundingModel;
        try {
            RoundingModel reqRoundingModel =
                    Core.getRequestObject(requestMessage, RoundingModel.class);

            deletedRoundingModel = this.roundingBllManager.delete(reqRoundingModel);
            if (Core.clientMessage.get().messageCode == TillBoxAppConstant.SUCCESS_CODE) {
                responseMessage.responseObj = deletedRoundingModel;
                responseMessage.message = Core.clientMessage.get().userMessage;
                this.commit();
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = Core.clientMessage.get().message;
                this.rollBack();
            }
        } catch (Exception e) {
            log.error("RoundingServiceManager -> delete got exception");
            this.rollBack();
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(e);
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage inActive(RequestMessage requestMessage) {
        ResponseMessage responseMessage = this.getDefaultResponseMessage();
        Object resultObject;
        try {
            RoundingModel reqRoundingModel =
                    Core.getRequestObject(requestMessage, RoundingModel.class);
            resultObject = this.roundingBllManager.inActive(requestMessage);
            if (Core.clientMessage.get().messageCode == TillBoxAppConstant.SUCCESS_CODE) {
                responseMessage.responseObj = resultObject;
                responseMessage.message = Core.clientMessage.get().userMessage;
                this.commit();
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = Core.clientMessage.get().message;
                this.rollBack();
            }
        } catch (Exception e) {
            log.error("RoundingServiceManager -> delete got exception");
            this.rollBack();
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(e);
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage getById(RequestMessage requestMessage) {
        ResponseMessage responseMessage = this.getDefaultResponseMessage();
        RoundingModel findRoundingModel;
        try {
            RoundingModel reqRoundingModel =
                    Core.getRequestObject(requestMessage, RoundingModel.class);
            findRoundingModel = this.roundingBllManager.getByReqId(reqRoundingModel);
            if (Core.clientMessage.get().messageCode == TillBoxAppConstant.SUCCESS_CODE) {
                responseMessage.responseObj = findRoundingModel;
                responseMessage.message = "Find the requested Rounding successful";
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = "Failed to find the requested Rounding";
            }
        } catch (Exception e) {
            log.error("RoundingServiceManager -> getById got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(e);
        }
        return responseMessage;
    }
}