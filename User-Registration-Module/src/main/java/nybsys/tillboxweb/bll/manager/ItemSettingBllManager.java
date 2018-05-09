/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 18-Apr-18
 * Time: 12:06 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.MessageModel.BllResponseMessage;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.ItemSetting;
import nybsys.tillboxweb.models.ItemSettingModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ItemSettingBllManager extends BaseBll<ItemSetting> {

    private static final Logger log = LoggerFactory.getLogger(ItemSettingBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(ItemSetting.class);
        Core.runTimeModelType.set(ItemSettingModel.class);
    }

    public BllResponseMessage saveOrUpdate(RequestMessage requestMessage) throws Exception {
        BllResponseMessage bllResponseMessage = new BllResponseMessage();

        ItemSettingModel reqItemSettingModel =
                Core.getRequestObject(requestMessage, ItemSettingModel.class);

        Integer primaryKeyValue = reqItemSettingModel.getItemSettingID();
        ItemSettingModel savedItemSettingModel = null, updatedItemSettingModel;

        try {

            if (primaryKeyValue == null || primaryKeyValue == 0) {
                // Save Code
                reqItemSettingModel.setBusinessID(requestMessage.businessID);
                savedItemSettingModel = this.save(reqItemSettingModel);
                if (savedItemSettingModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "Item Setting Save Successfully";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to Save ItemSetting";
                }
            } else {
                // Update Code
                updatedItemSettingModel = this.update(reqItemSettingModel);
                if (updatedItemSettingModel != null) {
                    Core.clientMessage.get().userMessage = "Item Setting Update Successfully";
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to Update Item Setting";
                }

                savedItemSettingModel = updatedItemSettingModel;
            }

        } catch (Exception ex) {
            log.error("ItemSettingBllManager -> saveOrUpdate got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        bllResponseMessage.responseObject = savedItemSettingModel;
        bllResponseMessage.responseCode = Core.clientMessage.get().messageCode;
        bllResponseMessage.message = Core.clientMessage.get().message;

        return bllResponseMessage;
    }

    public List<ItemSettingModel> search(ItemSettingModel reqItemSettingModel) throws Exception {
        List<ItemSettingModel> findItemSettingList;
        try {
            findItemSettingList = this.getAllByConditions(reqItemSettingModel);
            if (findItemSettingList.size() > 0) {
                Core.clientMessage.get().userMessage = "Find the request Item Setting";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                Core.clientMessage.get().message = "Failed to find the requested Item Setting";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("ItemSettingBllManager -> search got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return findItemSettingList;
    }

    public Integer deleteByConditions(RequestMessage requestMessage) throws Exception {
        ItemSettingModel req_ItemSettingModel =
                Core.getRequestObject(requestMessage, ItemSettingModel.class);
        Integer numberOfDeleteRow = 0;
        try {
            numberOfDeleteRow = this.deleteByConditions(req_ItemSettingModel);
            if (numberOfDeleteRow > 0) {
                //Core.clientMessage.get().userMessage = "Successfully deleted the requested ItemSetting";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                Core.clientMessage.get().message = "Failed to deleted the requested ItemSetting";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("ItemSettingBllManager -> deleteByConditions got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return numberOfDeleteRow;
    }


    public ItemSettingModel inActive(RequestMessage requestMessage) throws Exception {
        ItemSettingModel reqItemSettingModel =
                Core.getRequestObject(requestMessage, ItemSettingModel.class);
        ItemSettingModel _ItemSettingModel = null;
        try {
            if (reqItemSettingModel != null) {
                _ItemSettingModel = this.inActive(reqItemSettingModel);
                if (_ItemSettingModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "Successfully inactive the requested ItemSetting";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to inactive the requested ItemSetting";
                }
            }

        } catch (Exception ex) {
            log.error("ItemSettingBllManager -> inActive got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return _ItemSettingModel;
    }


    public ItemSettingModel delete(ItemSettingModel reqItemSettingModel) throws Exception {
        ItemSettingModel deletedItemSettingModel = null;
        try {
            if (reqItemSettingModel != null) {
                deletedItemSettingModel = this.softDelete(reqItemSettingModel);
                if (deletedItemSettingModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    //Core.clientMessage.get().userMessage = "Successfully deleted the requested ItemSetting";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to deleted the requested ItemSetting";
                }
            }

        } catch (Exception ex) {
            log.error("ItemSettingBllManager -> delete got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return deletedItemSettingModel;
    }

    public ItemSettingModel getByReqId(ItemSettingModel reqItemSettingModel) throws Exception {
        Integer primaryKeyValue = reqItemSettingModel.getItemSettingID();
        ItemSettingModel foundItemSettingModel = null;
        try {
            if (primaryKeyValue != null) {
                foundItemSettingModel = this.getByIdActiveStatus(primaryKeyValue);
                if (foundItemSettingModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    //Core.clientMessage.get().userMessage = "Get the requested ItemSetting successfully";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to the requested Item Setting";
                }
            }

        } catch (Exception ex) {
            log.error("ItemSettingBllManager -> getByID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return foundItemSettingModel;
    }

}