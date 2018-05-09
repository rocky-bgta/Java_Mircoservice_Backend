/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 16-Apr-18
 * Time: 5:51 PM
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
import nybsys.tillboxweb.entities.RegionalSetting;
import nybsys.tillboxweb.models.RegionalSettingModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class RegionalSettingBllManager extends BaseBll<RegionalSetting> {

    private static final Logger log = LoggerFactory.getLogger(RegionalSettingBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(RegionalSetting.class);
        Core.runTimeModelType.set(RegionalSettingModel.class);
    }

    public BllResponseMessage saveOrUpdate(RequestMessage requestMessage) throws Exception {
        BllResponseMessage bllResponseMessage = new BllResponseMessage();

        RegionalSettingModel reqRegionalSettingModel =
                Core.getRequestObject(requestMessage, RegionalSettingModel.class);

        Integer primaryKeyValue = reqRegionalSettingModel.getRegionalSettingID();
        RegionalSettingModel savedRegionalSettingModel = null, updatedRegionalSettingModel;

        try {

            if (primaryKeyValue == null || primaryKeyValue == 0) {
                // Save Code
                reqRegionalSettingModel.setBusinessID(requestMessage.businessID);
                savedRegionalSettingModel = this.save(reqRegionalSettingModel);
                if (savedRegionalSettingModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "Regional Setting Save Successfully";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to Save Regional Setting";
                }
            } else {
                // Update Code
                updatedRegionalSettingModel = this.update(reqRegionalSettingModel);
                if (updatedRegionalSettingModel != null) {
                    Core.clientMessage.get().userMessage = "Regional Setting Update Successfully";
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to Update Regional Setting";
                }

                savedRegionalSettingModel = updatedRegionalSettingModel;
            }

        } catch (Exception ex) {
            log.error("RegionalSettingBllManager -> saveOrUpdate got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;

        }
        bllResponseMessage.responseObject = savedRegionalSettingModel;
        bllResponseMessage.responseCode = Core.clientMessage.get().messageCode;
        bllResponseMessage.message = Core.clientMessage.get().message;

        return bllResponseMessage;
    }

    public List<RegionalSettingModel> search(RegionalSettingModel reqRegionalSettingModel) throws Exception {
        List<RegionalSettingModel> findRegionalSettingList;
        try {
            findRegionalSettingList = this.getAllByConditions(reqRegionalSettingModel);
            if (findRegionalSettingList.size() > 0) {
                Core.clientMessage.get().userMessage = "Find the request Regional Setting";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                Core.clientMessage.get().message = "Failed to find the requested Regional Setting";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("RegionalSettingBllManager -> search got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return findRegionalSettingList;
    }

    public Integer deleteByConditions(RequestMessage requestMessage) throws Exception {
        RegionalSettingModel req_RegionalSettingModel =
                Core.getRequestObject(requestMessage, RegionalSettingModel.class);
        Integer numberOfDeleteRow = 0;
        try {
            numberOfDeleteRow = this.deleteByConditions(req_RegionalSettingModel);
            if (numberOfDeleteRow > 0) {
                //Core.clientMessage.get().userMessage = "Successfully deleted the requested RegionalSetting";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                Core.clientMessage.get().message = "Failed to deleted the requested Regional Setting";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("RegionalSettingBllManager -> deleteByConditions got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return numberOfDeleteRow;
    }


    public RegionalSettingModel inActive(RequestMessage requestMessage) throws Exception {
        RegionalSettingModel reqRegionalSettingModel =
                Core.getRequestObject(requestMessage, RegionalSettingModel.class);
        RegionalSettingModel _RegionalSettingModel = null;
        try {
            if (reqRegionalSettingModel != null) {
                _RegionalSettingModel = this.inActive(reqRegionalSettingModel);
                if (_RegionalSettingModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "Successfully inactive the requested RegionalSetting";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to inactive the requested RegionalSetting";
                }
            }

        } catch (Exception ex) {
            log.error("RegionalSettingBllManager -> inActive got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return _RegionalSettingModel;
    }


    public RegionalSettingModel delete(RegionalSettingModel reqRegionalSettingModel) throws Exception {
        RegionalSettingModel deletedRegionalSettingModel = null;
        try {
            if (reqRegionalSettingModel != null) {
                deletedRegionalSettingModel = this.softDelete(reqRegionalSettingModel);
                if (deletedRegionalSettingModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    //Core.clientMessage.get().userMessage = "Successfully deleted the requested RegionalSetting";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to deleted the requested RegionalSetting";
                }
            }

        } catch (Exception ex) {
            log.error("RegionalSettingBllManager -> delete got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return deletedRegionalSettingModel;
    }

    public RegionalSettingModel getByReqId(RegionalSettingModel reqRegionalSettingModel) throws Exception {
        Integer primaryKeyValue = reqRegionalSettingModel.getRegionalSettingID();
        RegionalSettingModel foundRegionalSettingModel = null;
        try {
            if (primaryKeyValue != null) {
                foundRegionalSettingModel = this.getByIdActiveStatus(primaryKeyValue);
                if (foundRegionalSettingModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    //Core.clientMessage.get().userMessage = "Get the requested RegionalSetting successfully";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to the requested Regional Setting";
                }
            }

        } catch (Exception ex) {
            log.error("RegionalSettingBllManager -> getByID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return foundRegionalSettingModel;
    }

}