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
import nybsys.tillboxweb.entities.ProductAttribute;
import nybsys.tillboxweb.models.ProductAttributeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ProductAttributeBllManager extends BaseBll<ProductAttribute>{

    private static final Logger log = LoggerFactory.getLogger(ProductAttributeBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(ProductAttribute.class);
        Core.runTimeModelType.set(ProductAttributeModel.class);
    }

    public ProductAttributeModel saveOrUpdateProductAttributeWithBusinessLogic(ProductAttributeModel productAttributeModelReq) throws Exception {
        ProductAttributeModel productAttributeModel = new ProductAttributeModel();
        ProductAttributeModel whereCondition = new ProductAttributeModel();
        List<ProductAttributeModel> lstProductAttributeModel = new ArrayList<>();
        try {
            productAttributeModel = productAttributeModelReq;

            //search first
            whereCondition.setName(productAttributeModel.getName());
            whereCondition.setBusinessID(productAttributeModel.getBusinessID());
            lstProductAttributeModel = this.searchProductAttribute(whereCondition);
            Core.clientMessage.get().messageCode = null;
            //save
            if (productAttributeModel.getProductAttributeID() == null || productAttributeModel.getProductAttributeID() == 0)
            {
                //check duplicate save
                if(lstProductAttributeModel.size() > 0){
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.NAME_ALREADY_EXISTS;
                    Core.clientMessage.get().userMessage = MessageConstant.NAME_ALREADY_EXISTS;
                    return productAttributeModel;
                }
                productAttributeModel = this.save(productAttributeModel);
                if (productAttributeModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_PRODUCT_ATTRIBUTE;
                }
            } else { //update

                //check duplicate update
                if(lstProductAttributeModel.size() > 0){
                    //not self reflection
                    if(lstProductAttributeModel.get(0).getProductAttributeID().intValue() != productAttributeModel.getProductAttributeID().intValue()) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.NAME_ALREADY_EXISTS;
                        Core.clientMessage.get().userMessage = MessageConstant.NAME_ALREADY_EXISTS;
                        return productAttributeModel;
                    }
                }
                productAttributeModel = this.update(productAttributeModel);
                if (productAttributeModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_UPDATE_PRODUCT_ATTRIBUTE;
                }
            }

        } catch (Exception ex) {
            log.error("ProductAttributeBllManager -> saveOrUpdateProductAttributeWithBusinessLogic got exception :"+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return productAttributeModel;
    }

    public ProductAttributeModel deleteProductAttribute(ProductAttributeModel productAttributeModelReq) throws Exception {
        ProductAttributeModel productAttributeModel = new ProductAttributeModel();
        try {
            productAttributeModel = productAttributeModelReq;
            productAttributeModel = this.softDelete(productAttributeModel);
            if (productAttributeModel == null) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_DELETE_PRODUCT_ATTRIBUTE;
            }
        } catch (Exception ex) {
            log.error("ProductAttributeBllManager -> deleteProductAttribute got exception :"+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return productAttributeModel;
    }

    public ProductAttributeModel inactiveProductAttribute(ProductAttributeModel productAttributeModelReq) throws Exception {
        ProductAttributeModel productAttributeModel = new ProductAttributeModel();
        try {
            productAttributeModel = productAttributeModelReq;
            productAttributeModel = this.inActive(productAttributeModel);
            if (productAttributeModel == null) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_INACTIVE_PRODUCT_ATTRIBUTE;
            }
        } catch (Exception ex) {
            log.error("ProductAttributeBllManager -> inactiveProductAttribute got exception :"+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return productAttributeModel;
    }
    public List<ProductAttributeModel> searchProductAttribute(ProductAttributeModel productAttributeModelReq) throws Exception {
        ProductAttributeModel productAttributeModel = new ProductAttributeModel();
        List<ProductAttributeModel> lstProductAttributeModel = new ArrayList<>();
        try {
            productAttributeModel = productAttributeModelReq;
            lstProductAttributeModel = this.getAllByConditions(productAttributeModel);
            if (lstProductAttributeModel.size() == 0) {
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_PRODUCT_ATTRIBUTE;
            }
        } catch (Exception ex) {
            log.error("ProductAttributeBllManager -> searchProductAttribute got exception : "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstProductAttributeModel;
    }
}
