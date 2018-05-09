/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 18-Apr-18
 * Time: 12:08 PM
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
import nybsys.tillboxweb.models.ItemSettingModel;
import nybsys.tillboxweb.bll.manager.ItemSettingBllManager;

import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ItemSettingServiceManager extends BaseService implements BaseServiceManager {

    private static final Logger log = LoggerFactory.getLogger(ItemSettingServiceManager.class);

    @Autowired
    private ItemSettingBllManager itemSettingBllManager;

    @Override
    public ResponseMessage saveOrUpdate(RequestMessage requestMessage) {
        ResponseMessage responseMessage = this.getDefaultResponseMessage();
        BllResponseMessage bllResponseMessage;
        try {
            bllResponseMessage = this.itemSettingBllManager.saveOrUpdate(requestMessage);
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
            log.error("ItemSettingServiceManager -> saveOrUpdate got exception");
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
        List<ItemSettingModel> finedItemSettingList;
        try {
            ItemSettingModel reqItemSettingModel =
                    Core.getRequestObject(requestMessage, ItemSettingModel.class);
            finedItemSettingList = this.itemSettingBllManager.search(reqItemSettingModel);
            if (Core.clientMessage.get().messageCode == TillBoxAppConstant.SUCCESS_CODE) {
                responseMessage.responseObj = finedItemSettingList;
                responseMessage.message = "Find the request ItemSetting"; //Core.wrapperModel.get().userMessage;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = Core.clientMessage.get().message;
            }
        } catch (Exception e) {
            log.error("ItemSettingServiceManager -> search got exception");

            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(e);
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage delete(RequestMessage requestMessage) {
        ResponseMessage responseMessage = this.getDefaultResponseMessage();
        ItemSettingModel deletedItemSettingModel;
        try {
            ItemSettingModel reqItemSettingModel =
                    Core.getRequestObject(requestMessage, ItemSettingModel.class);

            deletedItemSettingModel = this.itemSettingBllManager.delete(reqItemSettingModel);
            if (Core.clientMessage.get().messageCode == TillBoxAppConstant.SUCCESS_CODE) {
                responseMessage.responseObj = deletedItemSettingModel;
                responseMessage.message = Core.clientMessage.get().userMessage;
                this.commit();
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = Core.clientMessage.get().message;
                this.rollBack();
            }
        } catch (Exception e) {
            log.error("ItemSettingServiceManager -> delete got exception");
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
            ItemSettingModel reqItemSettingModel =
                    Core.getRequestObject(requestMessage, ItemSettingModel.class);
            resultObject = this.itemSettingBllManager.inActive(requestMessage);
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
            log.error("ItemSettingServiceManager -> delete got exception");
            this.rollBack();
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(e);
        }
        return responseMessage;
    }

    @Override
    public ResponseMessage getById(RequestMessage requestMessage) {
        ResponseMessage responseMessage = this.getDefaultResponseMessage();
        ItemSettingModel findItemSettingModel;
        try {
            ItemSettingModel reqItemSettingModel =
                    Core.getRequestObject(requestMessage, ItemSettingModel.class);
            findItemSettingModel = this.itemSettingBllManager.getByReqId(reqItemSettingModel);
            if (Core.clientMessage.get().messageCode == TillBoxAppConstant.SUCCESS_CODE) {
                responseMessage.responseObj = findItemSettingModel;
                responseMessage.message = "Find the requested ItemSetting successful";
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = "Failed to find the requested ItemSetting";
            }
        } catch (Exception e) {
            log.error("ItemSettingServiceManager -> getById got exception");
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(e);
        }
        return responseMessage;
    }
}