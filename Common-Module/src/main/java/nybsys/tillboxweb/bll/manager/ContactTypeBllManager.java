/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 22/02/2018
 * Time: 02:57
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.ContactType;
import nybsys.tillboxweb.models.ContactTypeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ContactTypeBllManager extends BaseBll<ContactType> {

    private static final Logger log = LoggerFactory.getLogger(ContactTypeBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(ContactType.class);
        Core.runTimeModelType.set(ContactTypeModel.class);
    }
    public ContactTypeModel saveOrUpdate(ContactTypeModel contactTypeModelReq) throws Exception {
        ContactTypeModel contactTypeModel = new ContactTypeModel();
        ContactTypeModel whereCondition = new ContactTypeModel();
        List<ContactTypeModel> lstContactTypeModel = new ArrayList<>();
        try {
            contactTypeModel = contactTypeModelReq;

            //search first
            whereCondition.setName(contactTypeModel.getName());
            whereCondition.setReferenceType(contactTypeModel.getReferenceType());
            lstContactTypeModel = this.searchContactType(whereCondition);
            Core.clientMessage.get().messageCode = null;
            //save
            if (contactTypeModel.getContactTypeID() == null || contactTypeModel.getContactTypeID() == 0)
            {
                //check duplicate save
                if(lstContactTypeModel.size() > 0){
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.NAME_ALREADY_EXISTS;
                    Core.clientMessage.get().userMessage = MessageConstant.NAME_ALREADY_EXISTS;
                    return contactTypeModel;
                }
                contactTypeModel = this.save(contactTypeModel);
                if (contactTypeModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CONTACT_TYPE_SAVE_FAILED;
                }
            } else { //update

                //check duplicate update
                if(lstContactTypeModel.size() > 0){
                    //not self reflection
                    if(lstContactTypeModel.get(0).getContactTypeID().intValue() != contactTypeModel.getContactTypeID().intValue()) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.NAME_ALREADY_EXISTS;
                        Core.clientMessage.get().userMessage = MessageConstant.NAME_ALREADY_EXISTS;
                        return contactTypeModel;
                    }
                }
                contactTypeModel = this.update(contactTypeModel);
                if (contactTypeModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.CONTACT_TYPE_UPDATE_FAILED;
                }
            }

        } catch (Exception ex) {
            log.error("ContactTypeBllManager -> saveOrUpdate got exception :"+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return contactTypeModel;
    }

    public List<ContactTypeModel> searchContactType(ContactTypeModel contactTypeModelReq) throws Exception {
        ContactTypeModel contactTypeModel = new ContactTypeModel();
        List<ContactTypeModel> lstContactTypeModel = new ArrayList<>();
        try {
            contactTypeModel = contactTypeModelReq;
            lstContactTypeModel = this.getAllByConditions(contactTypeModel);
            if (lstContactTypeModel.size() == 0) {
                Core.clientMessage.get().message = MessageConstant.CONTACT_TYPE_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("ContactTypeBllManager -> searchContactType got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstContactTypeModel;
    }
    public ContactTypeModel getContactTypeByID(Integer categoryID) throws Exception {
        ContactTypeModel contactTypeModel = new ContactTypeModel();
        try {
            contactTypeModel = this.getById(categoryID);
            if (contactTypeModel == null) {
                Core.clientMessage.get().message = MessageConstant.CONTACT_TYPE_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("ContactTypeBllManager -> getContactTypeByID got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return contactTypeModel;
    }
}
