/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/28/2018
 * Time: 3:49 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.bll.manager.InventoryTransactionBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.coreModels.VMInventoryTransaction;
import nybsys.tillboxweb.coreModels.InventoryTransactionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class InventoryTransactionServiceManager extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(InventoryTransactionServiceManager.class);

    @Autowired
    private InventoryTransactionBllManager inventoryTransactionBllManager;


    public ResponseMessage saveInventoryTransaction(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        VMInventoryTransaction vmInventoryTransaction = new VMInventoryTransaction();
        try {
            vmInventoryTransaction = Core.getRequestObject(requestMessage, VMInventoryTransaction.class);

            this.inventoryTransactionBllManager.saveInventoryTransaction(vmInventoryTransaction.lstInventoryTransactionModel);
            if (Core.clientMessage.get().messageCode == null) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.SAVE_PRODUCT;
                this.commit();
            } else {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                if (Core.clientMessage.get().userMessage == null) {
                    responseMessage.message = MessageConstant.FAILED_TO_SAVE_PRODUCT;
                } else {
                    responseMessage.message = Core.clientMessage.get().userMessage;
                }
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("InventoryTransactionServiceManager -> saveInventoryTransaction got exception");
            responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            responseMessage.errorMessage = ex.getMessage();
//            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE);
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }


    public ResponseMessage searchInventoryTransaction(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        InventoryTransactionModel inventoryTransactionModel = new InventoryTransactionModel();
        List<InventoryTransactionModel> lstInventoryTransactionModel = new ArrayList<>();
        try {
            inventoryTransactionModel = Core.getRequestObject(requestMessage, InventoryTransactionModel.class);
            lstInventoryTransactionModel = this.inventoryTransactionBllManager.getAllByConditions(inventoryTransactionModel);

            responseMessage.responseObj = lstInventoryTransactionModel;
            if (lstInventoryTransactionModel.size() > 0) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.NO_DATA_FOUND;
            }

        } catch (Exception ex) {
            log.error("ProductServiceManager -> Product Service Manager got exception");
            responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            responseMessage.errorMessage = ex.getMessage();
            this.WriteExceptionLog(ex);
        }

        return responseMessage;
    }


    public ResponseMessage delete(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        InventoryTransactionModel inventoryTransactionModel = new InventoryTransactionModel();
        List<InventoryTransactionModel> lstInventoryTransactionModel = new ArrayList<>();
        try {
            inventoryTransactionModel = Core.getRequestObject(requestMessage, InventoryTransactionModel.class);
            lstInventoryTransactionModel = this.inventoryTransactionBllManager.deleteInventoryTransaction(inventoryTransactionModel);

            responseMessage.responseObj = lstInventoryTransactionModel;
            if (lstInventoryTransactionModel.size() > 0) {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                this.commit();
            } else {
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
                responseMessage.message = MessageConstant.NO_DATA_FOUND;
                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("ProductServiceManager -> Product Service Manager got exception");
            responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            responseMessage.errorMessage = ex.getMessage();
            this.WriteExceptionLog(ex);
            this.rollBack();
        }

        return responseMessage;
    }

}
