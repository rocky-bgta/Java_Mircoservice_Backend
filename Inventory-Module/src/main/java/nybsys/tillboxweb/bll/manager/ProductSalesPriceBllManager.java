/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/14/2018
 * Time: 11:01 AM
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
import nybsys.tillboxweb.entities.ProductSalesPrice;
import nybsys.tillboxweb.MessageModel.ClientMessage;
import nybsys.tillboxweb.models.ProductSalesPriceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ProductSalesPriceBllManager extends BaseBll<ProductSalesPrice> {

    private static final Logger log = LoggerFactory.getLogger(ProductSalesPriceBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(ProductSalesPrice.class);
        Core.runTimeModelType.set(ProductSalesPriceModel.class);
    }


    public ProductSalesPriceModel saveProductSalesPriceModel(ProductSalesPriceModel productSalesPriceModel, ClientMessage wrapperModel) {

        try {

            if (productSalesPriceModel.getProductSalesPriceID()>0)
            {
                productSalesPriceModel.setUpdatedDate(new Date());
                productSalesPriceModel = this.update(productSalesPriceModel);
            }
            else
            {
                productSalesPriceModel.setStatus(TillBoxAppEnum.Status.Active.get());
                productSalesPriceModel.setCreatedBy("");
                productSalesPriceModel.setCreatedDate(new Date());
                productSalesPriceModel = this.save(productSalesPriceModel);
            }

            if (productSalesPriceModel == null) {
                wrapperModel.messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                wrapperModel.message = MessageConstant.FAILED_TO_SAVE_PRODUCT_ATTRIBUTE;
            }

        } catch (Exception e) {
            e.printStackTrace();
            wrapperModel.messageCode = TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE;
            wrapperModel.message = TillBoxAppConstant.INTERNAL_SERVER_ERROR;
            log.error("ProductSalesPriceBllManager -> save product sale price got exception");
        }
        return productSalesPriceModel;
    }
}
