package nybsys.tillboxweb.bll.manager;


import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.ProductType;
import nybsys.tillboxweb.models.ProductTypeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ProductTypeBllManager extends BaseBll<ProductType> {

    private static final Logger log = LoggerFactory.getLogger(ProductTypeBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(ProductType.class);
        Core.runTimeModelType.set(ProductTypeModel.class);
    }
    public ProductTypeModel saveOrUpdate(ProductTypeModel productTypeModelReq) throws Exception {
        ProductTypeModel productTypeModel = new ProductTypeModel();
        try {
            productTypeModel = productTypeModelReq;

            //save
            if(productTypeModel.getProductTypeID() == null || productTypeModel.getProductTypeID() == 0) {
                productTypeModel = this.save(productTypeModel);
                if (productTypeModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.PRODUCT_TYPE_SAVE_FAILED;
                }

            }else {
                productTypeModel = this.update(productTypeModel);
                if (productTypeModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.PRODUCT_TYPE_UPDATE_FAILED;
                }
            }
        }catch (Exception ex) {
            log.error("ProductTypeBllManager -> saveOrUpdate got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return productTypeModel;
    }
}
