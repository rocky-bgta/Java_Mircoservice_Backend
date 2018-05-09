/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 14/02/2018
 * Time: 05:15
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.UnitOfMeasure;
import nybsys.tillboxweb.models.UnitOfMeasureModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UnitOfMeasureBllManager extends BaseBll<UnitOfMeasure> {
    private static final Logger log = LoggerFactory.getLogger(UnitOfMeasureBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(UnitOfMeasure.class);
        Core.runTimeModelType.set(UnitOfMeasureModel.class);
    }

    public UnitOfMeasureModel saveOrUpdateWithBusinessLogic(UnitOfMeasureModel unitOfMeasureModelReq) throws Exception {
        UnitOfMeasureModel unitOfMeasureModel = new UnitOfMeasureModel();
        UnitOfMeasureModel whereCondition = new UnitOfMeasureModel();
        List<UnitOfMeasureModel> lstUnitOfMeasureModel = new ArrayList<>();
        try {
            unitOfMeasureModel = unitOfMeasureModelReq;

            //search first
            whereCondition.setName(unitOfMeasureModel.getName());
            whereCondition.setBusinessID(unitOfMeasureModel.getBusinessID());
            lstUnitOfMeasureModel = this.searchUnitOfMeasure(whereCondition);
            Core.clientMessage.get().messageCode = null;
            //save
            if (unitOfMeasureModel.getUnitOfMeasureID() == null || unitOfMeasureModel.getUnitOfMeasureID() == 0) {
                //check duplicate save
                if (lstUnitOfMeasureModel.size() > 0) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.NAME_ALREADY_EXISTS;
                    Core.clientMessage.get().userMessage = MessageConstant.NAME_ALREADY_EXISTS;
                    return unitOfMeasureModel;
                }
                
                unitOfMeasureModel = this.save(unitOfMeasureModel);
                if (unitOfMeasureModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_UNIT_OF_MEASURE;
                }
            } else {//update
                //check duplicate update
                if (lstUnitOfMeasureModel.size() > 0) {
                    //not self reflection
                    if(lstUnitOfMeasureModel.get(0).getUnitOfMeasureID().intValue() != unitOfMeasureModel.getUnitOfMeasureID().intValue()) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.NAME_ALREADY_EXISTS;
                        Core.clientMessage.get().userMessage = MessageConstant.NAME_ALREADY_EXISTS;
                        return unitOfMeasureModel;
                    }
                }
                unitOfMeasureModel = this.update(unitOfMeasureModel);
                if (unitOfMeasureModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_UPDATE_UNIT_OF_MEASURE;
                }
            }

        } catch (Exception ex) {
            log.error("UnitOfMeasureBllManager -> saveOrUpdateUnitOfMeasureWithBusinessLogic got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return unitOfMeasureModel;
    }

    public UnitOfMeasureModel inactiveUnitOfMeasure(UnitOfMeasureModel unitOfMeasureModelReq) throws Exception {
        UnitOfMeasureModel unitOfMeasureModel = new UnitOfMeasureModel();
        try {
            unitOfMeasureModel = unitOfMeasureModelReq;
            unitOfMeasureModel = this.inActive(unitOfMeasureModel);
            if (unitOfMeasureModel == null) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_INACTIVE_UNIT_OF_MEASURE;
            }
        } catch (Exception ex) {
            log.error("UnitOfMeasureBllManager -> inactiveUnitOfMeasure got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return unitOfMeasureModel;
    }

    public List<UnitOfMeasureModel> searchUnitOfMeasure(UnitOfMeasureModel unitOfMeasureModelReq) throws Exception {
        UnitOfMeasureModel unitOfMeasureModel = new UnitOfMeasureModel();
        List<UnitOfMeasureModel> lstUnitOfMeasureModel = new ArrayList<>();
        try {
            unitOfMeasureModel = unitOfMeasureModelReq;
            lstUnitOfMeasureModel = this.getAllByConditions(unitOfMeasureModel);
            if (lstUnitOfMeasureModel.size() == 0) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_UNIT_OF_MEASURE;
            }
        } catch (Exception ex) {
            log.error("UnitOfMeasureBllManager -> searchUnitOfMeasure got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstUnitOfMeasureModel;
    }
}