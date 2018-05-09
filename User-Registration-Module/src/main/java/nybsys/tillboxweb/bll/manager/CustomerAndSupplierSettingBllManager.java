/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 19-Apr-18
 * Time: 12:57 PM
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
import nybsys.tillboxweb.entities.CustomerAndSupplierSetting;
import nybsys.tillboxweb.models.CustomerAndSupplierSettingModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CustomerAndSupplierSettingBllManager extends BaseBll<CustomerAndSupplierSetting> {

    private static final Logger log = LoggerFactory.getLogger(CustomerAndSupplierSettingBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(CustomerAndSupplierSetting.class);
        Core.runTimeModelType.set(CustomerAndSupplierSettingModel.class);
    }

    public BllResponseMessage saveOrUpdate(RequestMessage requestMessage) throws Exception {
        BllResponseMessage bllResponseMessage = new BllResponseMessage();

        CustomerAndSupplierSettingModel reqCustomerAndSupplierSettingModel =
                Core.getRequestObject(requestMessage, CustomerAndSupplierSettingModel.class);

        Integer primaryKeyValue = reqCustomerAndSupplierSettingModel.getCustomerAndSupplierSettingID();
        CustomerAndSupplierSettingModel savedCustomerAndSupplierSettingModel = null, updatedCustomerAndSupplierSettingModel;


        try {

            if (primaryKeyValue == null || primaryKeyValue == 0) {
                // Save Code
                reqCustomerAndSupplierSettingModel.setBusinessID(requestMessage.businessID);
                savedCustomerAndSupplierSettingModel = this.save(reqCustomerAndSupplierSettingModel);
                if (savedCustomerAndSupplierSettingModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "CustomerAndSupplierSetting Save Successfully";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to Save CustomerAndSupplierSetting";
                }
            } else {
                // Update Code
                updatedCustomerAndSupplierSettingModel = this.update(reqCustomerAndSupplierSettingModel);
                if (updatedCustomerAndSupplierSettingModel != null) {
                    Core.clientMessage.get().userMessage = "CustomerAndSupplierSetting Update Successfully";
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to Update CustomerAndSupplierSetting";
                }

                savedCustomerAndSupplierSettingModel = updatedCustomerAndSupplierSettingModel;
            }

        } catch (Exception ex) {
            log.error("CustomerAndSupplierSettingBllManager -> saveOrUpdate got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        bllResponseMessage.responseObject = savedCustomerAndSupplierSettingModel;
        bllResponseMessage.responseCode = Core.clientMessage.get().messageCode;
        bllResponseMessage.message = Core.clientMessage.get().message;

        return bllResponseMessage;
    }

    public List<CustomerAndSupplierSettingModel> search(CustomerAndSupplierSettingModel reqCustomerAndSupplierSettingModel) throws Exception {
        List<CustomerAndSupplierSettingModel> findCustomerAndSupplierSettingList;
        try {
            findCustomerAndSupplierSettingList = this.getAllByConditions(reqCustomerAndSupplierSettingModel);
            if (findCustomerAndSupplierSettingList.size() > 0) {
                Core.clientMessage.get().userMessage = "Find the request CustomerAndSupplierSetting";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                Core.clientMessage.get().message = "Failed to find the requested CustomerAndSupplierSetting";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("CustomerAndSupplierSettingBllManager -> search got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return findCustomerAndSupplierSettingList;
    }

    public Integer deleteByConditions(RequestMessage requestMessage) throws Exception {
        CustomerAndSupplierSettingModel req_CustomerAndSupplierSettingModel =
                Core.getRequestObject(requestMessage, CustomerAndSupplierSettingModel.class);
        Integer numberOfDeleteRow = 0;
        try {
            numberOfDeleteRow = this.deleteByConditions(req_CustomerAndSupplierSettingModel);
            if (numberOfDeleteRow > 0) {
                //Core.clientMessage.get().userMessage = "Successfully deleted the requested CustomerAndSupplierSetting";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
            } else {
                Core.clientMessage.get().message = "Failed to deleted the requested CustomerAndSupplierSetting";
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("CustomerAndSupplierSettingBllManager -> deleteByConditions got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return numberOfDeleteRow;
    }


    public CustomerAndSupplierSettingModel inActive(RequestMessage requestMessage) throws Exception {
        CustomerAndSupplierSettingModel reqCustomerAndSupplierSettingModel =
                Core.getRequestObject(requestMessage, CustomerAndSupplierSettingModel.class);
        CustomerAndSupplierSettingModel _CustomerAndSupplierSettingModel = null;
        try {
            if (reqCustomerAndSupplierSettingModel != null) {
                _CustomerAndSupplierSettingModel = this.inActive(reqCustomerAndSupplierSettingModel);
                if (_CustomerAndSupplierSettingModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    Core.clientMessage.get().userMessage = "Successfully inactive the requested CustomerAndSupplierSetting";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to inactive the requested CustomerAndSupplierSetting";
                }
            }

        } catch (Exception ex) {
            log.error("CustomerAndSupplierSettingBllManager -> inActive got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return _CustomerAndSupplierSettingModel;
    }


    public CustomerAndSupplierSettingModel delete(CustomerAndSupplierSettingModel reqCustomerAndSupplierSettingModel) throws Exception {
        CustomerAndSupplierSettingModel deletedCustomerAndSupplierSettingModel = null;
        try {
            if (reqCustomerAndSupplierSettingModel != null) {
                deletedCustomerAndSupplierSettingModel = this.softDelete(reqCustomerAndSupplierSettingModel);
                if (deletedCustomerAndSupplierSettingModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    //Core.clientMessage.get().userMessage = "Successfully deleted the requested CustomerAndSupplierSetting";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to deleted the requested CustomerAndSupplierSetting";
                }
            }

        } catch (Exception ex) {
            log.error("CustomerAndSupplierSettingBllManager -> delete got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return deletedCustomerAndSupplierSettingModel;
    }

    public CustomerAndSupplierSettingModel getByReqId(CustomerAndSupplierSettingModel reqCustomerAndSupplierSettingModel) throws Exception {
        Integer primaryKeyValue = reqCustomerAndSupplierSettingModel.getCustomerAndSupplierSettingID();
        CustomerAndSupplierSettingModel foundCustomerAndSupplierSettingModel = null;
        try {
            if (primaryKeyValue != null) {
                foundCustomerAndSupplierSettingModel = this.getByIdActiveStatus(primaryKeyValue);
                if (foundCustomerAndSupplierSettingModel != null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.SUCCESS_CODE;
                    //Core.clientMessage.get().userMessage = "Get the requested CustomerAndSupplierSetting successfully";
                } else {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = "Failed to the requested CustomerAndSupplierSetting";
                }
            }

        } catch (Exception ex) {
            log.error("CustomerAndSupplierSettingBllManager -> getByID got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return foundCustomerAndSupplierSettingModel;
    }
}