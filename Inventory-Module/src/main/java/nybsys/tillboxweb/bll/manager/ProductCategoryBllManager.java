/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 13/02/2018
 * Time: 03:52
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.coreEntities.ProductCategory;
import nybsys.tillboxweb.coreModels.ProductCategoryModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ProductCategoryBllManager extends BaseBll<ProductCategory>{

    private static final Logger log = LoggerFactory.getLogger(ProductCategoryBllManager.class);

    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(ProductCategory.class);
        Core.runTimeModelType.set(ProductCategoryModel.class);
    }

    public ProductCategoryModel saveOrUpdateProductCategoryWithBusinessLogic(ProductCategoryModel productCategoryModelReq) throws Exception {
        ProductCategoryModel productCategoryModel = new ProductCategoryModel();
        ProductCategoryModel whereCondition = new ProductCategoryModel();
        List<ProductCategoryModel> lstProductCategoryModel = new ArrayList<>();
        try {
            productCategoryModel = productCategoryModelReq;
            //search first
            whereCondition.setName(productCategoryModel.getName());
            whereCondition.setBusinessID(productCategoryModel.getBusinessID());
            lstProductCategoryModel = this.searchProductCategory(whereCondition);
            Core.clientMessage.get().messageCode = null;

            //save
            if (productCategoryModel.getProductCategoryID() == null || productCategoryModel.getProductCategoryID() == 0)
            {
                //check duplicate save
                if(lstProductCategoryModel.size() > 0 ){
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.NAME_ALREADY_EXISTS;
                    Core.clientMessage.get().userMessage = MessageConstant.NAME_ALREADY_EXISTS;
                    return productCategoryModel;
                }
                
                productCategoryModel = this.save(productCategoryModel);
                if (productCategoryModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_PRODUCT_CATEGORY;
                }
            } else {//update

                //check duplicate update
                if(lstProductCategoryModel.size() > 0 ){
                    //not self reflection
                    if(lstProductCategoryModel.get(0).getProductCategoryID().intValue() != productCategoryModel.getProductCategoryID().intValue()) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.NAME_ALREADY_EXISTS;
                        Core.clientMessage.get().userMessage = MessageConstant.NAME_ALREADY_EXISTS;
                        return productCategoryModel;
                    }
                }
                
                productCategoryModel = this.update(productCategoryModel);
                if (productCategoryModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_UPDATE_PRODUCT_CATEGORY;
                }
            }

        } catch (Exception ex) {
            log.error("ProductCategoryBllManager -> saveOrUpdateProductCategoryWithBusinessLogic got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return productCategoryModel;
    }

    public ProductCategoryModel deleteProductCategory(ProductCategoryModel productCategoryModelReq) throws Exception {
        ProductCategoryModel productCategoryModel = new ProductCategoryModel();
        try {
            productCategoryModel = productCategoryModelReq;
            productCategoryModel = this.softDelete(productCategoryModel);
            if (productCategoryModel == null) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_DELETE_PRODUCT_CATEGORY;
            }
        } catch (Exception ex) {
            log.error("ProductCategoryBllManager -> deleteProductCategory got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return productCategoryModel;
    }

    public ProductCategoryModel inactiveProductCategory(ProductCategoryModel productCategoryModelReq) throws Exception {
        ProductCategoryModel productCategoryModel = new ProductCategoryModel();
        try {
            productCategoryModel = productCategoryModelReq;
            productCategoryModel = this.inActive(productCategoryModel);
            if (productCategoryModel == null) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_INACTIVE_PRODUCT_CATEGORY;
            }
        } catch (Exception ex) {
            log.error("ProductCategoryBllManager -> inactiveProductCategory got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;

        }
        return productCategoryModel;
    }
    public List<ProductCategoryModel> searchProductCategory(ProductCategoryModel productCategoryModelReq) throws Exception {
        ProductCategoryModel productCategoryModel = new ProductCategoryModel();
        List<ProductCategoryModel> lstProductCategoryModel = new ArrayList<>();
        try {
            productCategoryModel = productCategoryModelReq;
            lstProductCategoryModel = this.getAllByConditions(productCategoryModel);
            if (lstProductCategoryModel.size() == 0) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_PRODUCT_CATEGORY;
            }
        } catch (Exception ex) {
            log.error("ProductCategoryBllManager -> searchProductCategory got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        
        return lstProductCategoryModel;
    }

}
