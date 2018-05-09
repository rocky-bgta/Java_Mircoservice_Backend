/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 22/02/2018
 * Time: 03:03
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.AddressType;
import nybsys.tillboxweb.models.AddressTypeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AddressTypeBllManager extends BaseBll<AddressType> {
    private static final Logger log = LoggerFactory.getLogger(AddressTypeBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(AddressType.class);
        Core.runTimeModelType.set(AddressTypeModel.class);
    }
    public AddressTypeModel saveOrUpdate(AddressTypeModel addressTypeModelReq) throws Exception {
        AddressTypeModel addressTypeModel = new AddressTypeModel();
        AddressTypeModel whereCondition = new AddressTypeModel();
        List<AddressTypeModel> lstAddressTypeModel = new ArrayList<>();
        try {
            addressTypeModel = addressTypeModelReq;

            //search first
            whereCondition.setName(addressTypeModel.getName());
            whereCondition.setReferenceType(addressTypeModel.getReferenceType());
            lstAddressTypeModel = this.searchAddressType(whereCondition);
            Core.clientMessage.get().messageCode = null;
            //save
            if (addressTypeModel.getAddressTypeID() == null || addressTypeModel.getAddressTypeID() == 0)
            {
                //check duplicate save
                if(lstAddressTypeModel.size() > 0){
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.NAME_ALREADY_EXISTS;
                    Core.clientMessage.get().userMessage = MessageConstant.NAME_ALREADY_EXISTS;
                    return addressTypeModel;
                }
                addressTypeModel = this.save(addressTypeModel);
                if (addressTypeModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.ADDRESS_TYPE_SAVE_FAILED;
                }
            } else { //update

                //check duplicate update
                if(lstAddressTypeModel.size() > 0){
                    //not self reflection
                    if(lstAddressTypeModel.get(0).getAddressTypeID().intValue() != addressTypeModel.getAddressTypeID().intValue()) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.NAME_ALREADY_EXISTS;
                        Core.clientMessage.get().userMessage = MessageConstant.NAME_ALREADY_EXISTS;
                        return addressTypeModel;
                    }
                }
                addressTypeModel = this.update(addressTypeModel);
                if (addressTypeModel == null) {
                    addressTypeModel = null;
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.ADDRESS_TYPE_UPDATE_FAILED;
                }
            }

        } catch (Exception ex) {
            log.error("AddressTypeBllManager -> saveOrUpdate got exception :"+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return addressTypeModel;
    }

    public List<AddressTypeModel> searchAddressType(AddressTypeModel addressTypeModelReq) throws Exception {
        AddressTypeModel addressTypeModel = new AddressTypeModel();
        List<AddressTypeModel> lstAddressTypeModel = new ArrayList<>();
        try {
            addressTypeModel = addressTypeModelReq;
            lstAddressTypeModel = this.getAllByConditions(addressTypeModel);
            if (lstAddressTypeModel.size() == 0) {
                Core.clientMessage.get().message = MessageConstant.ADDRESS_TYPE_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("AddressTypeBllManager -> searchAddressType got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstAddressTypeModel;
    }
    public AddressTypeModel getAddressTypeByID(Integer categoryID) throws Exception {
        AddressTypeModel addressTypeModel = new AddressTypeModel();
        try {
            addressTypeModel = this.getById(categoryID);
            if (addressTypeModel == null) {
                addressTypeModel = null;
                Core.clientMessage.get().message = MessageConstant.ADDRESS_TYPE_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("AddressTypeBllManager -> getAddressTypeByID got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return addressTypeModel;
    }
}
