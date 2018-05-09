/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 14/02/2018
 * Time: 10:49
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.entities.ProductAttributeValue;
import nybsys.tillboxweb.models.ProductAttributeValueModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ProductAttributeValueBllManager extends BaseBll<ProductAttributeValue> {

    private static final Logger log = LoggerFactory.getLogger(ProductAttributeValueBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(ProductAttributeValue.class);
        Core.runTimeModelType.set(ProductAttributeValueModel.class);
    }

    public ProductAttributeValueModel saveOrUpdateProductAttributeValueWithBusinessLogic(ProductAttributeValueModel productAttributeValueModelReq) throws Exception {
        ProductAttributeValueModel productAttributeValueModel = new ProductAttributeValueModel();
        ProductAttributeValueModel whereCondition = new ProductAttributeValueModel();
        List<ProductAttributeValueModel> lstProductAttributeValueModel = new ArrayList<>();
        try {
            productAttributeValueModel = productAttributeValueModelReq;

            //search first
            whereCondition.setValue(productAttributeValueModel.getValue());
            whereCondition.setBusinessID(productAttributeValueModel.getBusinessID());
            lstProductAttributeValueModel = this.searchProductAttributeValue(whereCondition);
            Core.clientMessage.get().messageCode = null;

            if (productAttributeValueModel.getProductAttributeValueID() == null || productAttributeValueModel.getProductAttributeValueID() == 0) {

                //check duplicate save
                if(lstProductAttributeValueModel.size() > 0){
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.VALUE_ALREADY_EXISTS;
                    Core.clientMessage.get().userMessage = MessageConstant.VALUE_ALREADY_EXISTS;
                    return productAttributeValueModel;
                }
                
                productAttributeValueModel = this.save(productAttributeValueModel);
                if (productAttributeValueModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_PRODUCT_ATTRIBUTE_VALUE;
                }
            } else {//update

                //check duplicate update
                if(lstProductAttributeValueModel.size() > 0){
                    //not self reflection
                    if(lstProductAttributeValueModel.get(0).getProductAttributeID().intValue() != productAttributeValueModel.getProductAttributeID().intValue()) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.VALUE_ALREADY_EXISTS;
                        Core.clientMessage.get().userMessage = MessageConstant.VALUE_ALREADY_EXISTS;
                        return productAttributeValueModel;
                    }
                }
                
                productAttributeValueModel = this.update(productAttributeValueModel);
                if (productAttributeValueModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_UPDATE_PRODUCT_ATTRIBUTE_VALUE;
                }
            }

        } catch (Exception ex) {
            log.error("ProductAttributeValueBllManager -> saveOrUpdateProductAttributeValueWithBusinessLogic got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return productAttributeValueModel;
    }

    public ProductAttributeValueModel deleteProductAttributeValue(ProductAttributeValueModel productAttributeValueModelReq) throws Exception {
        ProductAttributeValueModel productAttributeValueModel = new ProductAttributeValueModel();
        try {
            productAttributeValueModel = productAttributeValueModelReq;
            productAttributeValueModel = this.softDelete(productAttributeValueModel);
            if (productAttributeValueModel == null) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_DELETE_PRODUCT_ATTRIBUTE_VALUE;
            }
        } catch (Exception ex) {
            log.error("ProductAttributeValueBllManager -> deleteProductAttributeValue got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return productAttributeValueModel;
    }

    public ProductAttributeValueModel inactiveProductAttributeValue(ProductAttributeValueModel productAttributeValueModelReq) throws Exception {
        ProductAttributeValueModel productAttributeValueModel = new ProductAttributeValueModel();
        try {
            productAttributeValueModel = productAttributeValueModelReq;
            productAttributeValueModel = this.inActive(productAttributeValueModel);
            if (productAttributeValueModel == null) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_INACTIVE_PRODUCT_ATTRIBUTE_VALUE;
            }
        } catch (Exception ex) {
            log.error("ProductAttributeValueBllManager -> inactiveProductAttributeValue got exception:"+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return productAttributeValueModel;
    }

    public List<ProductAttributeValueModel> searchProductAttributeValue(ProductAttributeValueModel productAttributeValueModelReq) throws Exception {
        ProductAttributeValueModel productAttributeValueModel = new ProductAttributeValueModel();
        List<ProductAttributeValueModel> lstProductAttributeValueModel = new ArrayList<>();
        try {
            productAttributeValueModel = productAttributeValueModelReq;
            lstProductAttributeValueModel = this.getAllByConditions(productAttributeValueModel);
            if (lstProductAttributeValueModel.size() == 0) {
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_PRODUCT_ATTRIBUTE_VALUE;
            }
        } catch (Exception ex) {
            log.error("ProductAttributeValueBllManager -> searchProductAttributeValue got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstProductAttributeValueModel;
    }
}
