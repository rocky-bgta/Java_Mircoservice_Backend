/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 19-Feb-18
 * Time: 4:29 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.coreBllManager.CoreJournalBllManager;
import nybsys.tillboxweb.entities.ProductAdjustmentDetail;
import nybsys.tillboxweb.models.ProductAdjustmentDetailModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ProductAdjustmentDetailBllManager extends BaseBll<ProductAdjustmentDetail> {
    private static final Logger log = LoggerFactory.getLogger(ProductAdjustmentDetailBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(ProductAdjustmentDetail.class);
        Core.runTimeModelType.set(ProductAdjustmentDetailModel.class);
    }

    @Autowired
    CoreJournalBllManager coreJournalBllManager;

    public ProductAdjustmentDetailModel saveOrUpdateProductAdjustmentDetail(ProductAdjustmentDetailModel productAdjustmentDetailModelReq) throws Exception {
        Integer primaryKeyValue = productAdjustmentDetailModelReq.getProductAdjustmentDetailID();
        ProductAdjustmentDetailModel processedProductAdjustmentDetailModel = null;
        try {


            if (primaryKeyValue == null || primaryKeyValue == 0) {
                // Save Code
                processedProductAdjustmentDetailModel = this.save(productAdjustmentDetailModelReq);
                if (processedProductAdjustmentDetailModel == null) {
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_PRODUCT_ADJUSTMENT_DETAIL;
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                }
            } else {
                // Update Code

                // First delete the previous record
                this.softDelete(productAdjustmentDetailModelReq);
                // Then insert the new detail record
                processedProductAdjustmentDetailModel = this.save(productAdjustmentDetailModelReq);
                if (processedProductAdjustmentDetailModel != null) {
                    Core.clientMessage.get().message = MessageConstant.UPDATE_FAILED_PRODUCT_ADJUSTMENT_DETAIL;
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                }
            }

        } catch (Exception ex) {
            log.error("ProductAdjustmentDetailBllManager -> saveOrUpdateProductAdjustmentDetail got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return processedProductAdjustmentDetailModel;
    }
}
