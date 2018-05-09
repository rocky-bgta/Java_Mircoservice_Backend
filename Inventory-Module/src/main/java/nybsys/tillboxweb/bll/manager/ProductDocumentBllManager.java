/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/14/2018
 * Time: 11:03 AM
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
import nybsys.tillboxweb.entities.ProductDocument;
import nybsys.tillboxweb.MessageModel.ClientMessage;
import nybsys.tillboxweb.models.ProductDocumentModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ProductDocumentBllManager extends BaseBll<ProductDocument> {

    private static final Logger log = LoggerFactory.getLogger(ProductDocumentBllManager.class);

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(ProductDocument.class);
        Core.runTimeModelType.set(ProductDocumentModel.class);
    }

    public ProductDocumentModel saveProductDocument(ProductDocumentModel productDocumentModel,ClientMessage wrapperModel) {

        try {
            productDocumentModel.setStatus(TillBoxAppEnum.Status.Active.get());
            productDocumentModel.setCreatedBy("");
            productDocumentModel.setCreatedDate(new Date());
            productDocumentModel = this.save(productDocumentModel);

            if (productDocumentModel == null) {
                wrapperModel.messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                wrapperModel.message = MessageConstant.FAILED_TO_SAVE_PRODUCT_ATTRIBUTE;
            }

        } catch (Exception e) {
            e.printStackTrace();
            wrapperModel.messageCode = TillBoxAppConstant.INTERNAL_SERVER_ERROR_CODE;
            wrapperModel.message = TillBoxAppConstant.INTERNAL_SERVER_ERROR;
            log.error("ProductDocumentBllManager -> save product document got exception");
        }
        return productDocumentModel;
    }

}
