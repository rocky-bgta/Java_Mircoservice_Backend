/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 12/03/2018
 * Time: 2:47
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.DiscountGiveProduct;
import nybsys.tillboxweb.models.DiscountGiveProductModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class DiscountGiveProductBllManager extends BaseBll<DiscountGiveProduct> {
    private static final Logger log = LoggerFactory.getLogger(DiscountGiveProductBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(DiscountGiveProduct.class);
        Core.runTimeModelType.set(DiscountGiveProductModel.class);
    }

    public DiscountGiveProductModel saveOrUpdate(DiscountGiveProductModel discountGiveProductModelReq) throws Exception {
        DiscountGiveProductModel discountGiveProductModel = new DiscountGiveProductModel();
        List<DiscountGiveProductModel> lstDiscountGiveProductModel = new ArrayList<>();
        try {
            discountGiveProductModel = discountGiveProductModelReq;
            //save
            if (discountGiveProductModel.getDiscountGiveProductID() == null || discountGiveProductModel.getDiscountGiveProductID() == 0)
            {
                discountGiveProductModel = this.save(discountGiveProductModel);
                if (discountGiveProductModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.DISCOUNT_GIVE_PRODUCT_SAVE_FAILED;
                }
            } else { //update

                discountGiveProductModel = this.update(discountGiveProductModel);
                if (discountGiveProductModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.DISCOUNT_GIVE_PRODUCT_UPDATE_FAILED;
                }
            }

        } catch (Exception ex) {
            log.error("DiscountGiveProductBllManager -> saveOrUpdate got exception :"+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return discountGiveProductModel;
    }

    public List<DiscountGiveProductModel> searchDiscountGiveProduct(DiscountGiveProductModel discountGiveProductModelReq) throws Exception {
        DiscountGiveProductModel discountGiveProductModel = new DiscountGiveProductModel();
        List<DiscountGiveProductModel> lstDiscountGiveProductModel = new ArrayList<>();
        try {
            discountGiveProductModel = discountGiveProductModelReq;
            lstDiscountGiveProductModel = this.getAllByConditions(discountGiveProductModel);
            if (lstDiscountGiveProductModel.size() == 0) {
                Core.clientMessage.get().message = MessageConstant.DISCOUNT_GIVE_PRODUCT_GET_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("DiscountGiveProductBllManager -> searchDiscountGiveProduct got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstDiscountGiveProductModel;
    }
    public DiscountGiveProductModel deleteDiscountGiveProduct(DiscountGiveProductModel discountGiveProductModelReq) throws Exception {
        DiscountGiveProductModel discountGiveProductModel = new DiscountGiveProductModel();
        try {
            discountGiveProductModel = discountGiveProductModelReq;
            discountGiveProductModel = this.softDelete(discountGiveProductModel);
            if (discountGiveProductModel == null) {
                Core.clientMessage.get().message = MessageConstant.DISCOUNT_GIVE_PRODUCT_DELETE_FAILED;
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
            }
        } catch (Exception ex) {
            log.error("DiscountGiveProductBllManager -> deleteDiscountGiveProduct got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return discountGiveProductModel;
    }
    
}
