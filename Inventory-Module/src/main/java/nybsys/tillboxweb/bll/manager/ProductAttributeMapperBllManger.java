/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/14/2018
 * Time: 10:57 AM
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
import nybsys.tillboxweb.entities.ProductAttributeMapper;
import nybsys.tillboxweb.models.ProductAttributeMapperModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ProductAttributeMapperBllManger extends BaseBll<ProductAttributeMapper> {
    private static final Logger log = LoggerFactory.getLogger(ProductSalesPriceBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(ProductAttributeMapper.class);
        Core.runTimeModelType.set(ProductAttributeMapperModel.class);
    }

    public ProductAttributeMapperModel saveProductAttributeMapper(ProductAttributeMapperModel productAttributeMapperModel) throws Exception {

        try {

            if (productAttributeMapperModel.getProductAttributeMapperID() > 0) {

                productAttributeMapperModel.setUpdatedDate(new Date());
                productAttributeMapperModel = this.update(productAttributeMapperModel);
            } else {
                productAttributeMapperModel.setStatus(TillBoxAppEnum.Status.Active.get());
                productAttributeMapperModel.setCreatedBy("");
                productAttributeMapperModel.setCreatedDate(new Date());
                productAttributeMapperModel = this.save(productAttributeMapperModel);
            }
            if (productAttributeMapperModel == null) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_PRODUCT_ATTRIBUTE;
            }

        } catch (Exception ex) {
            log.error("ProductAttributeMapperBllManger -> saveProductAttributeMapper got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return productAttributeMapperModel;
    }

}
