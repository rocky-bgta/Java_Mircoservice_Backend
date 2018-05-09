/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 15/02/2018
 * Time: 10:19
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.UnitMeasurementConversion;
import nybsys.tillboxweb.models.UnitMeasurementConversionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UnitMeasurementConversionBllManager extends BaseBll<UnitMeasurementConversion>{

    private static final Logger log = LoggerFactory.getLogger(UnitMeasurementConversionBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(UnitMeasurementConversion.class);
        Core.runTimeModelType.set(UnitMeasurementConversionModel.class);
    }

    public UnitMeasurementConversionModel saveOrUpdateWithBusinessLogic(UnitMeasurementConversionModel unitMeasurementConversionModelReq) throws Exception {
        UnitMeasurementConversionModel unitMeasurementConversionModel = new UnitMeasurementConversionModel();
        UnitMeasurementConversionModel whereCondition = new UnitMeasurementConversionModel();
        List<UnitMeasurementConversionModel> lstUnitMeasurementConversionModel = new ArrayList<>();
        try {
            unitMeasurementConversionModel = unitMeasurementConversionModelReq;

            //search first
            whereCondition.setName(unitMeasurementConversionModel.getName());
            whereCondition.setBusinessID(unitMeasurementConversionModel.getBusinessID());
            lstUnitMeasurementConversionModel = this.searchUnitMeasurementConversion(whereCondition);
            Core.clientMessage.get().messageCode = null;
            //save
            if (unitMeasurementConversionModel.getUnitMeasurementID() == null || unitMeasurementConversionModel.getUnitMeasurementID() == 0)
            {
                //check duplicate save
                if(lstUnitMeasurementConversionModel.size() > 0 ){
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.NAME_ALREADY_EXISTS;
                    Core.clientMessage.get().userMessage = MessageConstant.NAME_ALREADY_EXISTS;
                    return unitMeasurementConversionModel;
                }
                
                unitMeasurementConversionModel = this.save(unitMeasurementConversionModel);
                if (unitMeasurementConversionModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_UNIT_OF_MEASURE_CONVERSION;
                }
            } else {//update

                //check duplicate update
                if(lstUnitMeasurementConversionModel.size() > 0 ){
                    //not self reflection
                    if(lstUnitMeasurementConversionModel.get(0).getUnitMeasurementID().intValue() != unitMeasurementConversionModel.getUnitMeasurementID().intValue()) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.NAME_ALREADY_EXISTS;
                        Core.clientMessage.get().userMessage = MessageConstant.NAME_ALREADY_EXISTS;
                        return unitMeasurementConversionModel;
                    }
                }
                
                unitMeasurementConversionModel = this.update(unitMeasurementConversionModel);
                if (unitMeasurementConversionModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_UPDATE_UNIT_OF_MEASURE_CONVERSION;
                }
            }

        } catch (Exception ex) {
            log.error("UnitMeasurementConversionBllManager -> saveOrUpdateUnitMeasurementConversionWithBusinessLogic got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return unitMeasurementConversionModel;
    }

    public UnitMeasurementConversionModel inactiveUnitMeasurementConversion(UnitMeasurementConversionModel unitMeasurementConversionModelReq) throws Exception {
        UnitMeasurementConversionModel unitMeasurementConversionModel = new UnitMeasurementConversionModel();
        try {
            unitMeasurementConversionModel = unitMeasurementConversionModelReq;
            unitMeasurementConversionModel = this.inActive(unitMeasurementConversionModel);
            if (unitMeasurementConversionModel == null) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_INACTIVE_UNIT_OF_MEASURE_CONVERSION;
            }
        } catch (Exception ex) {
            log.error("UnitMeasurementConversionBllManager -> inactiveUnitMeasurementConversion got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return unitMeasurementConversionModel;
    }
    public List<UnitMeasurementConversionModel> searchUnitMeasurementConversion(UnitMeasurementConversionModel unitMeasurementConversionModelReq) throws Exception {
        UnitMeasurementConversionModel unitMeasurementConversionModel = new UnitMeasurementConversionModel();
        List<UnitMeasurementConversionModel> lstUnitMeasurementConversionModel = new ArrayList<>();
        try {
            unitMeasurementConversionModel = unitMeasurementConversionModelReq;
            lstUnitMeasurementConversionModel = this.getAllByConditions(unitMeasurementConversionModel);
            if (lstUnitMeasurementConversionModel.size() == 0) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_UNIT_OF_MEASURE_CONVERSION;
            }
        } catch (Exception ex) {
            log.error("UnitMeasurementConversionBllManager -> searchUnitMeasurementConversion got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstUnitMeasurementConversionModel;
    }
}
