/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/14/2018
 * Time: 10:59 AM
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
import nybsys.tillboxweb.entities.ProductPurchasePrice;
import nybsys.tillboxweb.MessageModel.ClientMessage;
import nybsys.tillboxweb.models.ProductPurchasePriceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ProductPurchasePriceBllManager extends BaseBll<ProductPurchasePrice> {

    private static final Logger log = LoggerFactory.getLogger(ProductPurchasePriceBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(ProductPurchasePrice.class);
        Core.runTimeModelType.set(ProductPurchasePriceModel.class);
    }


    public ProductPurchasePriceModel saveProductPurchasePrice(ProductPurchasePriceModel productPurchasePriceModel, ClientMessage wrapperModel) {

        ProductPurchasePriceModel existingProductPriceModel=new ProductPurchasePriceModel();
        existingProductPriceModel.setProductID(productPurchasePriceModel.getProductID());
       // existingProductPriceModel=this.ge(productPurchasePriceModel.getProductID())
        try {
            if (productPurchasePriceModel.getProductPurchasePriceID() > 0) {
                productPurchasePriceModel.setUpdatedDate(new Date());
                productPurchasePriceModel = this.update(productPurchasePriceModel);
            } else {
                productPurchasePriceModel.setStatus(TillBoxAppEnum.Status.Active.get());
                productPurchasePriceModel.setCreatedDate(new Date());
                productPurchasePriceModel = this.save(productPurchasePriceModel);
            }

            if (productPurchasePriceModel == null) {
                wrapperModel.messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                wrapperModel.message = MessageConstant.FAILED_TO_SAVE_PRODUCT_ATTRIBUTE;
            }

        } catch (Exception e) {
            e.printStackTrace();
            wrapperModel.messageCode = TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE;
            wrapperModel.message = TillBoxAppConstant.INTERNAL_SERVER_ERROR;
            log.error("ProductPurchasePriceBllManager -> save product purchase price got exception");
        }
        return productPurchasePriceModel;
    }


}
