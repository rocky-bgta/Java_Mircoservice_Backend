/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/14/2018
 * Time: 11:06 AM
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
import nybsys.tillboxweb.entities.ProductPicture;
import nybsys.tillboxweb.MessageModel.ClientMessage;
import nybsys.tillboxweb.models.ProductPictureModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ProductPictureBllManager extends BaseBll<ProductPicture> {
    private static final Logger log = LoggerFactory.getLogger(ProductPictureBllManager.class);
    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(ProductPicture.class);
        Core.runTimeModelType.set(ProductPictureModel.class);
    }

    public ProductPictureModel saveProductPicture(ProductPictureModel productPictureModel, ClientMessage wrapperModel) {

        try {
            productPictureModel.setStatus(TillBoxAppEnum.Status.Active.get());
            productPictureModel.setCreatedBy("");
            productPictureModel.setCreatedDate(new Date());
            productPictureModel = this.save(productPictureModel);

            if (productPictureModel == null) {
                wrapperModel.messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                wrapperModel.message = MessageConstant.FAILED_TO_SAVE_PRODUCT_ATTRIBUTE;
            }

        } catch (Exception e) {
            e.printStackTrace();
            wrapperModel.messageCode = TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE;
            wrapperModel.message = TillBoxAppConstant.INTERNAL_SERVER_ERROR;
            log.error("ProductPictureBllManager -> save product picture got exception");
        }
        return productPictureModel;
    }

}
