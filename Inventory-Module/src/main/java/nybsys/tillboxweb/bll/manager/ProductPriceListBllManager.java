/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 14/02/2018
 * Time: 10:50
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.PriceCategory;
import nybsys.tillboxweb.models.PriceCategoryModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ProductPriceListBllManager extends BaseBll<PriceCategory> {

    private static final Logger log = LoggerFactory.getLogger(ProductPriceListBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(PriceCategory.class);
        Core.runTimeModelType.set(PriceCategoryModel.class);
    }

    public PriceCategoryModel saveOrUpdatePriceCategoryWithBusinessLogic(PriceCategoryModel priceCategoryModelReq) throws Exception {
        PriceCategoryModel priceCategoryModel = new PriceCategoryModel();
        PriceCategoryModel whereCondition = new PriceCategoryModel();
        List<PriceCategoryModel> lstPriceCategoryModel = new ArrayList<>();
        try {
            priceCategoryModel = priceCategoryModelReq;

            //search first
            whereCondition.setName(priceCategoryModel.getName());
            whereCondition.setBusinessID(priceCategoryModel.getBusinessID());
            lstPriceCategoryModel = this.searchPriceCategory(whereCondition);
            Core.clientMessage.get().messageCode = null;

            //save
            if (priceCategoryModel.getPriceCategoryID() == null || priceCategoryModel.getPriceCategoryID() == 0) {
                //check duplicate save
                if (lstPriceCategoryModel.size() > 0) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.NAME_ALREADY_EXISTS;
                    Core.clientMessage.get().userMessage = MessageConstant.NAME_ALREADY_EXISTS;
                    return priceCategoryModel;
                }
                priceCategoryModel = this.save(priceCategoryModel);
                Core.clientMessage.get().messageCode = null;
                if (priceCategoryModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_PRICE_CATEGORY;
                }
            } else {//update

                //check duplicate update
                if (lstPriceCategoryModel.size() > 0) {
                    //not self reflection
                    if (lstPriceCategoryModel.get(0).getPriceCategoryID().intValue() != priceCategoryModel.getPriceCategoryID().intValue()) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.NAME_ALREADY_EXISTS;
                        Core.clientMessage.get().userMessage = MessageConstant.NAME_ALREADY_EXISTS;
                        return priceCategoryModel;
                    }
                }

                priceCategoryModel = this.update(priceCategoryModel);
                if (priceCategoryModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_UPDATE_PRICE_CATEGORY;
                }
            }

        } catch (Exception ex) {
            log.error("PriceCategoryBllManager -> saveOrUpdatePriceCategoryWithBusinessLogic got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return priceCategoryModel;
    }

    public PriceCategoryModel deletePriceCategory(PriceCategoryModel priceCategoryModelReq) throws Exception {
        PriceCategoryModel priceCategoryModel = new PriceCategoryModel();
        try {
            priceCategoryModel = priceCategoryModelReq;
            priceCategoryModel = this.softDelete(priceCategoryModel);
            if (priceCategoryModel == null) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_DELETE_PRICE_CATEGORY;
            }
        } catch (Exception ex) {
            log.error("PriceCategoryBllManager -> deletePriceCategory got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return priceCategoryModel;
    }

    public PriceCategoryModel inactivePriceCategory(PriceCategoryModel priceCategoryModelReq) throws Exception {
        PriceCategoryModel priceCategoryModel = new PriceCategoryModel();
        try {
            priceCategoryModel = priceCategoryModelReq;
            priceCategoryModel = this.inActive(priceCategoryModel);
            if (priceCategoryModel == null) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_INACTIVE_PRICE_CATEGORY;
            }
        } catch (Exception ex) {
            log.error("PriceCategoryBllManager -> inactivePriceCategory got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return priceCategoryModel;
    }

    public List<PriceCategoryModel> searchPriceCategory(PriceCategoryModel priceCategoryModelReq) throws Exception {
        PriceCategoryModel priceCategoryModel = new PriceCategoryModel();
        List<PriceCategoryModel> lstPriceCategoryModel = new ArrayList<>();
        try {
            priceCategoryModel = priceCategoryModelReq;
            lstPriceCategoryModel = this.getAllByConditions(priceCategoryModel);
            if (lstPriceCategoryModel.size() == 0) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_PRICE_CATEGORY;
            }
        } catch (Exception ex) {
            log.error("PriceCategoryBllManager -> searchPriceCategory got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstPriceCategoryModel;
    }
}
