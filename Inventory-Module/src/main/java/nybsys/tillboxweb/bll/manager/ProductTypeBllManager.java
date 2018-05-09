/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 13/02/2018
 * Time: 03:51
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.coreEntities.ProductType;
import nybsys.tillboxweb.coreModels.ProductTypeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public ProductTypeModel saveOrUpdateProductTypeWithBusinessLogic(ProductTypeModel productTypeModelReq) throws Exception {
        ProductTypeModel productTypeModel = new ProductTypeModel();
        ProductTypeModel whereCondition = new ProductTypeModel();
        List<ProductTypeModel> lstProductTypeModel = new ArrayList<>();
        try {
            productTypeModel = productTypeModelReq;

            //search first
            whereCondition.setName(productTypeModel.getName());
            whereCondition.setBusinessID(productTypeModel.getBusinessID());
            lstProductTypeModel = this.searchProductType(whereCondition);
            Core.clientMessage.get().messageCode = null;
            //save
            if (productTypeModel.getProductTypeID() == null || productTypeModel.getProductTypeID() == 0) {
                //check duplicate save
                if (lstProductTypeModel.size() > 0) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.NAME_ALREADY_EXISTS;
                    Core.clientMessage.get().userMessage = MessageConstant.NAME_ALREADY_EXISTS;
                    return productTypeModel;
                }
                
                productTypeModel = this.save(productTypeModel);
                if (productTypeModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_PRODUCT_TYPE;
                }
            } else {//update
                //check duplicate update
                if (lstProductTypeModel.size() > 0) {
                    //not self reflection
                    if(lstProductTypeModel.get(0).getProductTypeID().intValue() != productTypeModel.getProductTypeID().intValue()) {
                        Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                        Core.clientMessage.get().message = MessageConstant.NAME_ALREADY_EXISTS;
                        Core.clientMessage.get().userMessage = MessageConstant.NAME_ALREADY_EXISTS;
                        return productTypeModel;
                    }
                }
                
                productTypeModel = this.update(productTypeModel);
                if (productTypeModel == null) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().message = MessageConstant.FAILED_TO_UPDATE_PRODUCT_TYPE;
                }
            }

        } catch (Exception ex) {
            log.error("ProductTypeBllManager -> saveOrUpdateProductTypeWithBusinessLogic got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return productTypeModel;
    }

    public ProductTypeModel deleteProductType(ProductTypeModel productTypeModelReq) throws Exception {
        ProductTypeModel productTypeModel = new ProductTypeModel();
        try {
            productTypeModel = productTypeModelReq;
            productTypeModel = this.softDelete(productTypeModel);
            if (productTypeModel == null) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_DELETE_PRODUCT_TYPE;
            }
        } catch (Exception ex) {
            log.error("ProductTypeBllManager -> deleteProductType got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return productTypeModel;
    }

    public ProductTypeModel inactiveProductType(ProductTypeModel productTypeModelReq) throws Exception {
        ProductTypeModel productTypeModel = new ProductTypeModel();
        try {
            productTypeModel = productTypeModelReq;
            productTypeModel = this.inActive(productTypeModel);
            if (productTypeModel == null) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_INACTIVE_PRODUCT_TYPE;
            }
        } catch (Exception ex) {
            log.error("ProductTypeBllManager -> inactiveProductType got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return productTypeModel;
    }

    public List<ProductTypeModel> searchProductType(ProductTypeModel productTypeModelReq) throws Exception {
        ProductTypeModel productTypeModel = new ProductTypeModel();
        List<ProductTypeModel> lstProductTypeModel = new ArrayList<>();
        try {
            productTypeModel = productTypeModelReq;
            lstProductTypeModel = this.getAllByConditions(productTypeModel);
            if (lstProductTypeModel.size() == 0) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_GET_PRODUCT_TYPE;
            }
        } catch (Exception ex) {
            log.error("ProductTypeBllManager -> searchProductType got exception: "+ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }

        return lstProductTypeModel;
    }
}
