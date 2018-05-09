/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 12/03/2018
 * Time: 11:12
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.SalesRepresentative;
import nybsys.tillboxweb.models.SalesRepresentativeModel;
import nybsys.tillboxweb.models.SalesRepresentativeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SalesRepresentativeBllManager extends BaseBll<SalesRepresentative> {
    private static final Logger log = LoggerFactory.getLogger(SalesRepresentativeBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(SalesRepresentative.class);
        Core.runTimeModelType.set(SalesRepresentativeModel.class);
    }
    public SalesRepresentativeModel saveOrUpdate(SalesRepresentativeModel salesRepresentativeModelReq) throws Exception {
        SalesRepresentativeModel salesRepresentativeModel = new SalesRepresentativeModel();
        SalesRepresentativeModel whereCondition = new SalesRepresentativeModel();
        List<SalesRepresentativeModel> lstSalesRepresentativeModel = new ArrayList<>();
        try {
            salesRepresentativeModel = salesRepresentativeModelReq;
            //save
            if (salesRepresentativeModel.getSalesRepresentativeID() == null || salesRepresentativeModel.getSalesRepresentativeID() == 0)
            {
                salesRepresentativeModel = this.save(salesRepresentativeModel);
                if (salesRepresentativeModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.SALES_REPRESENTATIVE_SAVE_FAILED;
                }
            } else { //update
                
                salesRepresentativeModel = this.update(salesRepresentativeModel);
                if (salesRepresentativeModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.SALES_REPRESENTATIVE_UPDATE_FAILED;
                }
            }

        } catch (Exception ex) {
            log.error("SalesRepresentativeBllManager -> saveOrUpdate got exception :"+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return salesRepresentativeModel;
    }

    public List<SalesRepresentativeModel> searchSalesRepresentative(SalesRepresentativeModel salesRepresentativeModelReq) throws Exception {
        SalesRepresentativeModel salesRepresentativeModel = new SalesRepresentativeModel();
        List<SalesRepresentativeModel> lstSalesRepresentativeModel = new ArrayList<>();
        try {
            salesRepresentativeModel = salesRepresentativeModelReq;
            lstSalesRepresentativeModel = this.getAllByConditions(salesRepresentativeModel);
            if (lstSalesRepresentativeModel.size() == 0) {
                Core.clientMessage.get().message = MessageConstant.SALES_REPRESENTATIVE_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("SalesRepresentativeBllManager -> searchSalesRepresentative got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstSalesRepresentativeModel;
    }
    public SalesRepresentativeModel getSalesRepresentativeByID(Integer suplierCategoryID) throws Exception {
        SalesRepresentativeModel salesRepresentativeModel = new SalesRepresentativeModel();
        try {
            salesRepresentativeModel = this.getById(suplierCategoryID);
            if (salesRepresentativeModel == null) {
                Core.clientMessage.get().message = MessageConstant.SALES_REPRESENTATIVE_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("SalesRepresentativeBllManager -> getSalesRepresentativeByID got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return salesRepresentativeModel;
    }
}
