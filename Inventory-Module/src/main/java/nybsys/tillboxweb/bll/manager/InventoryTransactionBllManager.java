/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/28/2018
 * Time: 3:42 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.bll.manager;

import nybsys.tillboxweb.BaseBll;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.entities.InventoryTransaction;

import nybsys.tillboxweb.coreModels.InventoryTransactionModel;
import nybsys.tillboxweb.models.ProductModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class InventoryTransactionBllManager extends BaseBll<InventoryTransaction> {

    private static final Logger log = LoggerFactory.getLogger(InventoryTransactionBllManager.class);

    @Autowired
    private ProductBllManager productBllManager;

    @Override
    protected void initEntityModel() {

        Core.runTimeModelType.remove();
        Core.runTimeEntityType.remove();
        Core.runTimeEntityType.set(InventoryTransaction.class);
        Core.runTimeModelType.set(InventoryTransactionModel.class);
    }

    public static class StockSum {
        public Double inQuantitySum;
        public Double outQuantitySum;
    }


    public List<InventoryTransactionModel> saveInventoryTransaction(List<InventoryTransactionModel> lstInventoryTransactionModel) throws Exception {

        try {

            for (InventoryTransactionModel inventoryTransactionModel : lstInventoryTransactionModel) {
                ProductModel productModel = new ProductModel();
                productModel = this.productBllManager.getById(inventoryTransactionModel.getProductID(),TillBoxAppEnum.Status.Active.get());
                if (inventoryTransactionModel.getInventoryTransactionID() != null && inventoryTransactionModel.getInventoryTransactionID() > 0) {
                    if (productModel != null) {
                        inventoryTransactionModel.setProductCategoryID(productModel.getProductCategoryID());
                        inventoryTransactionModel.setProductTypeID(productModel.getProductTypeID());
                        inventoryTransactionModel.setProductCategoryID(productModel.getProductCategoryID());
                    }
                    this.update(inventoryTransactionModel);
                } else {
                    if (productModel != null) {
                        inventoryTransactionModel.setProductCategoryID(productModel.getProductCategoryID());
                        inventoryTransactionModel.setProductTypeID(productModel.getProductTypeID());
                        inventoryTransactionModel.setProductCategoryID(productModel.getProductCategoryID());
                    }
                    this.save(inventoryTransactionModel);
                }
            }

        } catch (Exception ex) {
            log.error("inventoryTransactionBllManager -> saveOrUpdateProductAdjustment got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return lstInventoryTransactionModel;
    }

    public Double getStock(Integer businessID, Integer productID) throws Exception {
        List<StockSum> lstStockSum;
        Double stock = 0.0;

        try {

            String hql = "SELECT sum(I.inQuantity) as  inQuantitySum, sum(I.outQuantity) as  outQuantitySum FROM InventoryTransaction I WHERE I.status = " + TillBoxAppEnum.Status.Active.get() + " AND I.businessID = " + businessID + " AND I.productID = " + productID;
            lstStockSum = this.executeHqlQuery(hql, StockSum.class, TillBoxAppEnum.QueryType.Join.get());
            for (StockSum stockSum : lstStockSum) {
                stock += stockSum.inQuantitySum;
                stock -= stockSum.outQuantitySum;
            }
        } catch (Exception ex) {
            log.error("InventoryTransactionBllManager -> getStock got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }
        return stock;
    }


    public List<InventoryTransactionModel> deleteInventoryTransaction(InventoryTransactionModel inventoryTransactionModel) throws Exception {

        List<InventoryTransactionModel> inventoryTransactionModels = new ArrayList<>();
        try {

            inventoryTransactionModels = this.getAllByConditions(inventoryTransactionModel);
            for (InventoryTransactionModel invmodel : inventoryTransactionModels) {
                invmodel.setStatus(TillBoxAppEnum.Status.Deleted.get());
                this.update(invmodel);
            }
        } catch (Exception ex) {
            log.error("InventoryTransactionBllManager -> inventory transaction delete got exception: " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            throw ex;
        }


        return inventoryTransactionModels;
    }

}
