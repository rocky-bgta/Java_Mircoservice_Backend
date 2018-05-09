/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 14/02/2018
 * Time: 10:54
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.UOMMethod;
import nybsys.tillboxweb.models.UOMMethodModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UOMMethodBllManager extends BaseBll<UOMMethod>{

    private static final Logger log = LoggerFactory.getLogger(UOMMethodBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(UOMMethod.class);
        Core.runTimeModelType.set(UOMMethodModel.class);
    }

    public UOMMethodModel saveOrUpdateUOMMethodWithBusinessLogic(UOMMethodModel uOMMethodModelReq) throws Exception {
        UOMMethodModel uOMMethodModel = new UOMMethodModel();
        UOMMethodModel whereCondition = new UOMMethodModel();
        List<UOMMethodModel> lstUomMethodModel = new ArrayList<>();

        try {
            uOMMethodModel = uOMMethodModelReq;
            //search first
            whereCondition.setName(uOMMethodModel.getName());
            whereCondition.setBusinessID(uOMMethodModel.getBusinessID());
            lstUomMethodModel = this.searchUOMMethod(whereCondition);
            Core.clientMessage.get().messageCode = null;
            //save
            if (uOMMethodModel.getuOMMethodID() == null || uOMMethodModel.getuOMMethodID() == 0)
            {
                //check duplicate save
                if(lstUomMethodModel.size() > 0 ){
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.NAME_ALREADY_EXISTS;
                    Core.clientMessage.get().userMessage = MessageConstant.NAME_ALREADY_EXISTS;
                    return uOMMethodModel;
                }
                
                uOMMethodModel.setCreatedDate(new Date());
                uOMMethodModel = this.save(uOMMethodModel);
                if (uOMMethodModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_UOM_METHOD;
                }
            } else {//update
                //check duplicate update
                if(lstUomMethodModel.size() > 0 ){
                    if(lstUomMethodModel.get(0).getuOMMethodID().intValue() != uOMMethodModel.getuOMMethodID().intValue()) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.NAME_ALREADY_EXISTS;
                        Core.clientMessage.get().userMessage = MessageConstant.NAME_ALREADY_EXISTS;
                        return uOMMethodModel;
                    }
                }
                
                uOMMethodModel = this.update(uOMMethodModel);
                if (uOMMethodModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_UPDATE_UOM_METHOD;
                }
            }

        } catch (Exception ex) {
            log.error("UOMMethodBllManager -> saveOrUpdateUOMMethodWithBusinessLogic got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;

        }
        return uOMMethodModel;
    }


    public UOMMethodModel inactiveUOMMethod(UOMMethodModel uOMMethodModelReq) throws Exception {
        UOMMethodModel uOMMethodModel = new UOMMethodModel();
        try {
            uOMMethodModel = uOMMethodModelReq;
            uOMMethodModel = this.inActive(uOMMethodModel);
            if (uOMMethodModel == null) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_INACTIVE_UOM_METHOD;
            }
        } catch (Exception ex) {
            log.error("UOMMethodBllManager -> inactiveUOMMethod got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return uOMMethodModel;
    }
    public List<UOMMethodModel> searchUOMMethod(UOMMethodModel uOMMethodModelReq) throws Exception {
        UOMMethodModel uOMMethodModel = new UOMMethodModel();
        List<UOMMethodModel> lstUOMMethodModel = new ArrayList<>();
        try {
            uOMMethodModel = uOMMethodModelReq;
            lstUOMMethodModel = this.getAllByConditions(uOMMethodModel);
            if (lstUOMMethodModel.size() == 0) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_UOM_METHOD;
            }
        } catch (Exception ex) {
            log.error("UOMMethodBllManager -> searchUOMMethod got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstUOMMethodModel;
    }

}
