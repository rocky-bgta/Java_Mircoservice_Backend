/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 18-Apr-18
 * Time: 3:12 PM
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
import nybsys.tillboxweb.models.OutStandingBalanceModel;
import nybsys.tillboxweb.bll.manager.OutStandingBalanceBllManager;

import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class OutStandingBalanceServiceManager extends BaseService implements BaseServiceManager {

    private static final Logger log = LoggerFactory.getLogger(OutStandingBalanceServiceManager.class);

    @Autowired
    private OutStandingBalanceBllManager outStandingBalanceBllManager;

    @Override
    public ResponseMessage saveOrUpdate(RequestMessage requestMessage) {
        ResponseMessage responseMessage = this.getDefaultResponseMessage();
        BllResponseMessage bllResponseMessage;
        try {
            bllResponseMessage = this.outStandingBalanceBllManager.saveOrUpdate(requestMessage);
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
            log.error("OutStandingBalanceServiceManager -> saveOrUpdate got exception");
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
        List<OutStandingBalanceModel> finedOutStandingBalanceList;
        try {
            OutStandingBalanceModel reqOutStandingBalanceModel =
                    Core.getRequestObject(requestMessage, OutStandingBalanceModel.class);
            finedOutStandingBalanceList = this.outStandingBalanceBllManager.search(reqOutStandingBalanceModel);
            if (Core.clientMessage.get().messageCode == TillBoxAppConstant.SUCCESS_CODE) {
                responseMessage.responseObj = finedOutStandingBalanceList;
                responseMessage.message = "Find the request OutStandingBalance"; //Core.wrapperModel.get().userMessage;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = Core.clientMessage.get().message;
            }
        } catch (Exception e) {
            log.error("OutStandingBalanceServiceManager -> search got exception");

            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(e);
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage delete(RequestMessage requestMessage) {
        ResponseMessage responseMessage = this.getDefaultResponseMessage();
        OutStandingBalanceModel deletedOutStandingBalanceModel;
        try {
            OutStandingBalanceModel reqOutStandingBalanceModel =
                    Core.getRequestObject(requestMessage, OutStandingBalanceModel.class);

            deletedOutStandingBalanceModel = this.outStandingBalanceBllManager.delete(reqOutStandingBalanceModel);
            if (Core.clientMessage.get().messageCode == TillBoxAppConstant.SUCCESS_CODE) {
                responseMessage.responseObj = deletedOutStandingBalanceModel;
                responseMessage.message = Core.clientMessage.get().userMessage;
                this.commit();
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = Core.clientMessage.get().message;
                this.rollBack();
            }
        } catch (Exception e) {
            log.error("OutStandingBalanceServiceManager -> delete got exception");
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
            OutStandingBalanceModel reqOutStandingBalanceModel =
                    Core.getRequestObject(requestMessage, OutStandingBalanceModel.class);
            resultObject = this.outStandingBalanceBllManager.inActive(requestMessage);
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
            log.error("OutStandingBalanceServiceManager -> delete got exception");
            this.rollBack();
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(e);
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage getById(RequestMessage requestMessage) {
        ResponseMessage responseMessage = this.getDefaultResponseMessage();
        OutStandingBalanceModel findOutStandingBalanceModel;
        try {
            OutStandingBalanceModel reqOutStandingBalanceModel =
                    Core.getRequestObject(requestMessage, OutStandingBalanceModel.class);
            findOutStandingBalanceModel = this.outStandingBalanceBllManager.getByReqId(reqOutStandingBalanceModel);
            if (Core.clientMessage.get().messageCode == TillBoxAppConstant.SUCCESS_CODE) {
                responseMessage.responseObj = findOutStandingBalanceModel;
                responseMessage.message = "Find the requested OutStandingBalance successful";
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = "Failed to find the requested OutStandingBalance";
            }
        } catch (Exception e) {
            log.error("OutStandingBalanceServiceManager -> getById got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(e);
        }
        return responseMessage;
    }
}