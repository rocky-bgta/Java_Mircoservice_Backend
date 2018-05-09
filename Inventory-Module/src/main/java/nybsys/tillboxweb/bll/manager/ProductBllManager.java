/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/14/2018
 * Time: 10:53 AM
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
import nybsys.tillboxweb.coreEnum.ReferenceType;
import nybsys.tillboxweb.coreModels.InventoryTransactionModel;
import nybsys.tillboxweb.entities.Product;
import nybsys.tillboxweb.models.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ProductBllManager extends BaseBll<Product> {
    private static final Logger log = LoggerFactory.getLogger(ProductBllManager.class);
    @Autowired
    private ProductPictureBllManager productPictureBllManager;
    @Autowired
    private ProductPurchasePriceBllManager productPurchasePriceBllManager;
    @Autowired
    private ProductSalesPriceBllManager productSalesPriceBllManager;
    @Autowired
    private ProductDocumentBllManager productDocumentBllManager;
    @Autowired
    private ProductAttributeMapperBllManger productAttributeMapperBllManger;

    private InventoryTransactionBllManager inventoryTransactionBllManager = new InventoryTransactionBllManager();

    @Override
    protected void initEntityModel() {
        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(Product.class);
        Core.runTimeModelType.set(ProductModel.class);
    }


    public VMProduct saveProduct(VMProduct vmProduct) throws Exception {
        try {

            if (isValidProduct(vmProduct)) {

                if (vmProduct.productModel.getProductID() > 0) {

                    vmProduct.productModel = this.update(vmProduct.productModel);

                    ProductPurchasePriceModel searchProductPurchasePrice = new ProductPurchasePriceModel();
                    List<ProductPurchasePriceModel> productPurchasePriceModels = new ArrayList<>();
                    searchProductPurchasePrice.setProductID(vmProduct.productModel.getProductID());
                    productPurchasePriceModels = this.productPurchasePriceBllManager.getAllByConditions(searchProductPurchasePrice);

                    for (ProductPurchasePriceModel productPurchasePriceModel : productPurchasePriceModels) {
                        productPurchasePriceModel.setStatus(TillBoxAppEnum.Status.Deleted.get());
                        this.productPurchasePriceBllManager.update(productPurchasePriceModel);
                    }

                    ProductSalesPriceModel searchProductSalesPriceModel = new ProductSalesPriceModel();
                    searchProductSalesPriceModel.setProductID(vmProduct.productModel.getProductID());
                    List<ProductSalesPriceModel> productSalesPriceModels = new ArrayList<>();
                    productSalesPriceModels = this.productSalesPriceBllManager.getAllByConditions(searchProductSalesPriceModel);
                    for (ProductSalesPriceModel productSalesPriceModel : productSalesPriceModels) {
                        productSalesPriceModel.setStatus(TillBoxAppEnum.Status.Deleted.get());
                        this.productSalesPriceBllManager.update(productSalesPriceModel);
                    }

                    ProductAttributeMapperModel searchProductAttributeMapperModel = new ProductAttributeMapperModel();
                    searchProductAttributeMapperModel.setProductID(vmProduct.productModel.getProductID());
                    List<ProductAttributeMapperModel> productAttributeMapperModels = new ArrayList<>();
                    productAttributeMapperModels = this.productAttributeMapperBllManger.getAllByConditions(searchProductAttributeMapperModel);

                    for (ProductAttributeMapperModel productAttributeMapperModel : productAttributeMapperModels) {
                        productAttributeMapperModel.setStatus(TillBoxAppEnum.Status.Deleted.get());
                        this.productAttributeMapperBllManger.update(productAttributeMapperModel);
                    }

                    nybsys.tillboxweb.coreModels.InventoryTransactionModel searchInventoryTransaction = new InventoryTransactionModel();
                    searchInventoryTransaction.setReferenceID(vmProduct.productModel.getProductID());
                    searchInventoryTransaction.setReferenceType(ReferenceType.Product.get());
                    nybsys.tillboxweb.coreModels.InventoryTransactionModel invTransModel = new InventoryTransactionModel();
                    invTransModel = this.inventoryTransactionBllManager.getAllByConditions(searchInventoryTransaction).get(0);

                    if (invTransModel != null) {
                        invTransModel.setStatus(TillBoxAppEnum.Status.Deleted.get());
                        ;
                        this.inventoryTransactionBllManager.update(invTransModel);
                    }


//                if (vmProduct.productPictureModel.getPicture() != null) {
//                    vmProduct.productPictureModel.setProductID(vmProduct.productModel.getProductID());
//                    vmProduct.productPictureModel = this.productPictureBllManager.save(vmProduct.productPictureModel);
//                }
//                if (vmProduct.productDocumentModel.getDocument() != null) {
//                    vmProduct.productDocumentModel.setProductID(vmProduct.productModel.getProductID());
//                    vmProduct.productDocumentModel = this.productDocumentBllManager.save(vmProduct.productDocumentModel);
//                }

                    for (ProductPurchasePriceModel productPurchasePriceModel : vmProduct.lstProductPurchasePriceModel) {

                        productPurchasePriceModel.setProductID(vmProduct.productModel.getProductID());
                        productPurchasePriceModel = this.productPurchasePriceBllManager.saveProductPurchasePrice(productPurchasePriceModel, Core.clientMessage.get());
                    }

                    for (ProductSalesPriceModel productSalesPriceModel : vmProduct.lstProductSalesPriceModel) {

                        productSalesPriceModel.setProductID(vmProduct.productModel.getProductID());
                        productSalesPriceModel = this.productSalesPriceBllManager.saveProductSalesPriceModel(productSalesPriceModel, Core.clientMessage.get());
                    }
                    for (ProductAttributeMapperModel productAttributeMapperModel : vmProduct.lstProductAttributeMapperModels) {
                        productAttributeMapperModel.setProductID(vmProduct.productModel.getProductID());
                        productAttributeMapperModel = this.productAttributeMapperBllManger.saveProductAttributeMapper(productAttributeMapperModel);
                    }


                    if (vmProduct.productModel.getOpeningQuantity() != null && vmProduct.productModel.getOpeningQuantity() > 0) {
                        nybsys.tillboxweb.coreModels.InventoryTransactionModel inventoryTransactionModel = new InventoryTransactionModel();
                        inventoryTransactionModel.setBusinessID(vmProduct.productModel.getBusinessID());
                        inventoryTransactionModel.setProductID(vmProduct.productModel.getProductID());
                        inventoryTransactionModel.setInQuantity(vmProduct.productModel.getOpeningQuantity());
                        inventoryTransactionModel.setReferenceID(vmProduct.productModel.getProductID());
                        inventoryTransactionModel.setReferenceType(ReferenceType.Product.get());
                        this.inventoryTransactionBllManager.save(inventoryTransactionModel);
                    }


                } else {
                    vmProduct.productModel.setStatus(TillBoxAppEnum.Status.Active.get());
                    vmProduct.productModel.setCreatedBy("");
                    vmProduct.productModel.setCreatedDate(new Date());
                    vmProduct.productModel = this.save(vmProduct.productModel);

//                if (vmProduct.productPictureModel.getPicture() != null) {
//                    vmProduct.productPictureModel.setProductID(vmProduct.productModel.getProductID());
//                    vmProduct.productPictureModel = this.productPictureBllManager.save(vmProduct.productPictureModel);
//                }
//                if (vmProduct.productDocumentModel.getDocument() != null) {
//                    vmProduct.productDocumentModel.setProductID(vmProduct.productModel.getProductID());
//                    vmProduct.productDocumentModel = this.productDocumentBllManager.save(vmProduct.productDocumentModel);
//                }

                    for (ProductPurchasePriceModel productPurchasePriceModel : vmProduct.lstProductPurchasePriceModel) {

                        productPurchasePriceModel.setProductID(vmProduct.productModel.getProductID());
                        productPurchasePriceModel = this.productPurchasePriceBllManager.saveProductPurchasePrice(productPurchasePriceModel, Core.clientMessage.get());
                    }

                    for (ProductSalesPriceModel productSalesPriceModel : vmProduct.lstProductSalesPriceModel) {

                        productSalesPriceModel.setProductID(vmProduct.productModel.getProductID());
                        productSalesPriceModel = this.productSalesPriceBllManager.saveProductSalesPriceModel(productSalesPriceModel, Core.clientMessage.get());
                    }
                    for (ProductAttributeMapperModel productAttributeMapperModel : vmProduct.lstProductAttributeMapperModels) {
                        productAttributeMapperModel.setProductID(vmProduct.productModel.getProductID());
                        productAttributeMapperModel = this.productAttributeMapperBllManger.saveProductAttributeMapper(productAttributeMapperModel);
                    }

                    if (vmProduct.productModel.getOpeningQuantity() != null && vmProduct.productModel.getOpeningQuantity() > 0) {
                        nybsys.tillboxweb.coreModels.InventoryTransactionModel inventoryTransactionModel = new InventoryTransactionModel();
                        inventoryTransactionModel.setBusinessID(vmProduct.productModel.getBusinessID());
                        inventoryTransactionModel.setProductID(vmProduct.productModel.getProductID());
                        inventoryTransactionModel.setInQuantity(vmProduct.productModel.getOpeningQuantity());
                        inventoryTransactionModel.setReferenceID(vmProduct.productModel.getProductID());
                        inventoryTransactionModel.setReferenceType(ReferenceType.Product.get());
                        this.inventoryTransactionBllManager.save(inventoryTransactionModel);
                    }

                }
            }


//            if ( Core.clientMessage.get().messageCode == null) {
//                Core.clientMessage.get().message = MessageConstant.SUCCESSFULLY_SAVE_PRODUCT_ATTRIBUTE;
//            } else {
//
//                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
//                Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_PRODUCT_ATTRIBUTE;
//            }
            if (vmProduct.productModel == null) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.FAILED_TO_SAVE_PRODUCT_ATTRIBUTE;
            }

        } catch (Exception ex) {

            log.error("Error from save product (Service Manager) : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return vmProduct;
    }

    private Boolean isValidProduct(VMProduct vmProduct) throws Exception {
        ProductModel existingProductModel = new ProductModel();
        existingProductModel.setName(vmProduct.productModel.getName());
        List<ProductModel> lstProductModel = new ArrayList<>();

        ProductAttributeMapperModel productAttributeMapperModel = new ProductAttributeMapperModel();

        List<ProductAttributeMapperModel> lstExistingProductAttributeMapperModel = new ArrayList<>();

        lstProductModel = this.getAllByConditions(existingProductModel);

        if (vmProduct.productModel.getProductID() > 0) {
            existingProductModel = this.getById(vmProduct.productModel.getProductID(), TillBoxAppEnum.Status.Active.get());
        } else {
            lstProductModel = this.getAllByConditions(existingProductModel);
        }
        if (lstProductModel.size() > 0) {
            existingProductModel = lstProductModel.get(0);
            productAttributeMapperModel.setProductID(existingProductModel.getProductID());
            lstExistingProductAttributeMapperModel = this.productAttributeMapperBllManger.getAllByConditions(productAttributeMapperModel);

        }


//        Collections.sort(lstExistingProductAttributeMapperModel, Collections.reverseOrder(a-> a.g));


        Comparator<ProductAttributeMapperModel> nameShorted = (o1, o2) -> o1.getProductAttributeID().compareTo(o2.getProductAttributeID());
        lstExistingProductAttributeMapperModel = lstExistingProductAttributeMapperModel.stream().sorted(nameShorted).collect(Collectors.toList());

        vmProduct.lstProductAttributeMapperModels = vmProduct.lstProductAttributeMapperModels.stream().sorted(nameShorted).collect(Collectors.toList());

        if (vmProduct.lstProductAttributeMapperModels.size() > 0) {
            String newMapperCombination = "";
            String existingMapperCombination = "";

            boolean duplicate = false;
            for (ProductAttributeMapperModel pAttributeMapperModel : vmProduct.lstProductAttributeMapperModels) {
                newMapperCombination = new StringBuilder().append(newMapperCombination).append(pAttributeMapperModel.getProductAttributeID()).append(pAttributeMapperModel.getProductAttributeValueID()).toString();
            }

            for (ProductAttributeMapperModel pAttributeMapperModel : lstExistingProductAttributeMapperModel) {
                existingMapperCombination = new StringBuilder().append(existingMapperCombination).append(pAttributeMapperModel.getProductAttributeID()).append(pAttributeMapperModel.getProductAttributeValueID()).toString();
            }


            if ((existingProductModel.getProductID() != null && existingProductModel.getProductID() > 0) && existingProductModel.getProductID().intValue() != vmProduct.productModel.getProductID().intValue()) {

                if (StringUtils.equals(newMapperCombination, existingMapperCombination)) {
                    Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                    Core.clientMessage.get().userMessage = MessageConstant.PRODUCT_NAME_ALREADY_EXISTS;
                    return false;
                }
            }
        } else {
            if (existingProductModel != null || existingProductModel.getProductID() != vmProduct.productModel.getProductID()) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().userMessage = MessageConstant.PRODUCT_NAME_ALREADY_EXISTS;
                return false;
            }
        }


        return true;
    }


    public List<VMProduct> getFilteredVMProduct(ProductModel productModel) throws Exception {

        List<VMProduct> lstVMProduct = new ArrayList<>();

        List<ProductModel> lstProductModel = new ArrayList<>();
        lstProductModel = this.getAllByConditions(productModel);
        for (ProductModel pModel : lstProductModel) {
            VMProduct vmProduct = new VMProduct();
            vmProduct.productModel = pModel;
            ProductPurchasePriceModel productPurchasePriceModel = new ProductPurchasePriceModel();
            ProductSalesPriceModel productSalesPriceModel = new ProductSalesPriceModel();
            ProductPictureModel productPictureModel = new ProductPictureModel();
            ProductAttributeMapperModel productAttributeMapperModel = new ProductAttributeMapperModel();
            ProductDocumentModel productDocumentModel = new ProductDocumentModel();

            productPurchasePriceModel.setProductID(pModel.getProductID());
            productDocumentModel.setProductID(pModel.getProductID());
            productSalesPriceModel.setProductID(pModel.getProductID());
            productPictureModel.setProductID(pModel.getProductID());
            productAttributeMapperModel.setProductID(pModel.getProductID());

            vmProduct.lstProductPurchasePriceModel = this.productPurchasePriceBllManager.getAllByConditions(productPurchasePriceModel);
            vmProduct.lstProductSalesPriceModel = this.productSalesPriceBllManager.getAllByConditions(productSalesPriceModel);
            vmProduct.lstProductAttributeMapperModels = this.productAttributeMapperBllManger.getAllByConditions(productAttributeMapperModel);

//            vmProduct.productPictureModel = (this.productPictureBllManager.getAllByConditions(productPictureModel).size() > 0) ?
//                    this.productPictureBllManager.getAllByConditions(productPictureModel).get(0) : new ProductPictureModel();
//
//            vmProduct.productDocumentModel = (this.productDocumentBllManager.getAllByConditions(productDocumentModel).size() > 0) ?
//                    this.productDocumentBllManager.getAllByConditions(productDocumentModel).get((0)) : new ProductDocumentModel();

            lstVMProduct.add(vmProduct);
        }


        return lstVMProduct;
    }

    public List<VMProduct> getLikeFilteredProduct(ProductModel productModel, Integer businessID) throws Exception {

        List<VMProduct> lstVMProduct = new ArrayList<>();
        try {
            List<ProductModel> lstProductModel = new ArrayList<>();
            String hql = "FROM Product P WHERE P.status = " + TillBoxAppEnum.Status.Active.get() + " AND P.businessID = " + businessID + " AND P.name LIKE '%" + productModel.getName() + "%'";
            lstProductModel = this.executeHqlQuery(hql, ProductModel.class, TillBoxAppEnum.QueryType.Join.get());
            for (ProductModel pModel : lstProductModel) {
                VMProduct vmProduct = new VMProduct();
                vmProduct.productModel = pModel;
                ProductPurchasePriceModel productPurchasePriceModel = new ProductPurchasePriceModel();
                ProductSalesPriceModel productSalesPriceModel = new ProductSalesPriceModel();
                ProductPictureModel productPictureModel = new ProductPictureModel();
                ProductAttributeMapperModel productAttributeMapperModel = new ProductAttributeMapperModel();
                ProductDocumentModel productDocumentModel = new ProductDocumentModel();

                productPurchasePriceModel.setProductID(pModel.getProductID());
                productDocumentModel.setProductID(pModel.getProductID());
                productSalesPriceModel.setProductID(pModel.getProductID());
                productPictureModel.setProductID(pModel.getProductID());
                productAttributeMapperModel.setProductID(pModel.getProductID());

                vmProduct.lstProductPurchasePriceModel = this.productPurchasePriceBllManager.getAllByConditions(productPurchasePriceModel);
                vmProduct.lstProductSalesPriceModel = this.productSalesPriceBllManager.getAllByConditions(productSalesPriceModel);
                vmProduct.lstProductAttributeMapperModels = this.productAttributeMapperBllManger.getAllByConditions(productAttributeMapperModel);

//            vmProduct.productPictureModel = (this.productPictureBllManager.getAllByConditions(productPictureModel).size() > 0) ?
//                    this.productPictureBllManager.getAllByConditions(productPictureModel).get(0) : new ProductPictureModel();
//
//            vmProduct.productDocumentModel = (this.productDocumentBllManager.getAllByConditions(productDocumentModel).size() > 0) ?
//                    this.productDocumentBllManager.getAllByConditions(productDocumentModel).get((0)) : new ProductDocumentModel();

                lstVMProduct.add(vmProduct);
            }
            if (lstProductModel.size() == 0) {
                Core.clientMessage.get().messageCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                Core.clientMessage.get().message = MessageConstant.PRODUCT_GET_FAILED;

            }

        } catch (
                Exception ex) {
            log.error("ProductBllManager -> getLikeFilteredProduct got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstVMProduct;
    }

    public VMProduct getProductByID(ProductModel productModel) throws Exception {

        ProductModel pModel = new ProductModel();
        pModel = this.getById(productModel.getProductID());

        VMProduct vmProduct = new VMProduct();
        vmProduct.productModel = pModel;
        ProductPurchasePriceModel productPurchasePriceModel = new ProductPurchasePriceModel();
        ProductSalesPriceModel productSalesPriceModel = new ProductSalesPriceModel();
        ProductPictureModel productPictureModel = new ProductPictureModel();
        ProductAttributeMapperModel productAttributeMapperModel = new ProductAttributeMapperModel();
        ProductDocumentModel productDocumentModel = new ProductDocumentModel();

        productPurchasePriceModel.setProductID(pModel.getProductID());
        productDocumentModel.setProductID(pModel.getProductID());
        productSalesPriceModel.setProductID(pModel.getProductID());
        productPictureModel.setProductID(pModel.getProductID());
        productAttributeMapperModel.setProductID(pModel.getProductID());

        vmProduct.lstProductPurchasePriceModel = this.productPurchasePriceBllManager.getAllByConditions(productPurchasePriceModel);
        vmProduct.lstProductSalesPriceModel = this.productSalesPriceBllManager.getAllByConditions(productSalesPriceModel);
        vmProduct.lstProductAttributeMapperModels = this.productAttributeMapperBllManger.getAllByConditions(productAttributeMapperModel);

//        vmProduct.productPictureModel = (this.productPictureBllManager.getAllByConditions(productPictureModel).size() > 0) ?
//                this.productPictureBllManager.getAllByConditions(productPictureModel).get(0) : new ProductPictureModel();
//
//        vmProduct.productDocumentModel = (this.productDocumentBllManager.getAllByConditions(productDocumentModel).size() > 0) ?
//                this.productDocumentBllManager.getAllByConditions(productDocumentModel).get((0)) : new ProductDocumentModel();


        return vmProduct;
    }

    public List<VMProductWithStockAndPrice> getProductWithStockAndPrice(ProductModel productModelReq) throws Exception {
        List<VMProductWithStockAndPrice> lstVmProductWithStockAndPrice = new ArrayList<>();
        ProductModel wheareCondition;
        List<ProductModel> lstProductModel;
        try {
            wheareCondition = productModelReq;
            if (wheareCondition.getStatus() == null) {
                wheareCondition.setStatus(TillBoxAppEnum.Status.Active.get());
            }
            lstProductModel = this.getAllByConditions(wheareCondition);
            if (lstProductModel.size() > 0) {
                for (ProductModel productItem : lstProductModel) {
                    VMProductWithStockAndPrice vmProductWithStockAndPrice = new VMProductWithStockAndPrice();
                    vmProductWithStockAndPrice.productModel = productItem;

                    ProductPurchasePriceModel productPurchasePriceModel = new ProductPurchasePriceModel();
                    productPurchasePriceModel = productPurchasePriceBllManager.getById(productItem.getProductID(), TillBoxAppEnum.Status.Active.get());
                    vmProductWithStockAndPrice.productPurchasePriceModel = productPurchasePriceModel;

                    ProductSalesPriceModel productSalesPriceModel = new ProductSalesPriceModel();
                    productSalesPriceModel = productSalesPriceBllManager.getById(productItem.getProductID(), TillBoxAppEnum.Status.Active.get());
                    vmProductWithStockAndPrice.productSalesPriceModel = productSalesPriceModel;

                    Double stock = 0.0;
                    stock = inventoryTransactionBllManager.getStock(productItem.getBusinessID(), productItem.getProductID());
                    vmProductWithStockAndPrice.stock = stock;
                    lstVmProductWithStockAndPrice.add(vmProductWithStockAndPrice);
                }
            }

        } catch (
                Exception ex) {
            log.error("ProductBllManager -> getProductWithStockAndPrice got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstVmProductWithStockAndPrice;
    }
}
