/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 19-Feb-18
 * Time: 4:39 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.service.manager;

import nybsys.tillboxweb.BaseService;
import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import nybsys.tillboxweb.appenum.TillBoxAppEnum;
import nybsys.tillboxweb.bll.manager.InventoryTransactionBllManager;
import nybsys.tillboxweb.bll.manager.ProductAdjustmentBllManager;
import nybsys.tillboxweb.bll.manager.ProductAdjustmentDetailBllManager;
import nybsys.tillboxweb.bll.manager.ProductBllManager;
import nybsys.tillboxweb.constant.MessageConstant;
import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.coreConstant.CurrencyConstant;
import nybsys.tillboxweb.coreEnum.Adjustment;
import nybsys.tillboxweb.coreEnum.ReferenceType;
import nybsys.tillboxweb.coreModels.CurrencyModel;
import nybsys.tillboxweb.models.*;
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
public class ProductAdjustmentServiceManager extends BaseService {

    private static final Logger log = LoggerFactory.getLogger(ProductAdjustmentServiceManager.class);

    @Autowired
    private ProductAdjustmentDetailBllManager productAdjustmentDetailBllManager;

    @Autowired
    private ProductAdjustmentBllManager productAdjustmentBllManager;

    @Autowired
    private InventoryTransactionBllManager inventoryTransactionBllManager;

    @Autowired
    private ProductBllManager productBllManager;

    private ProductServiceManager productServiceManager = new ProductServiceManager();

    public ResponseMessage saveOrUpdateProductAdjustment(RequestMessage requestMessage) {
        ResponseMessage responseMessage = new ResponseMessage();
        VMProductAdjustment reqVMProductAdjustment;
        ProductAdjustmentModel productAdjustmentModel;
        List<ProductAdjustmentDetailModel> productAdjustmentDetailModelList;

        InventoryTransactionModel inventoryTransactionModel = new InventoryTransactionModel();
        ProductModel productModelWhereCondition = new ProductModel();
        List<ProductModel> productModelList;
        InventoryTransactionModel whereConditionInventoryTran, modelToUpdateInventoryTran;
        CurrencyModel currencyModel;
        try {

            //get base currency and exchange rate
            currencyModel = this.productServiceManager.getBaseCurrency();
            if (currencyModel == null) {
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;
                responseMessage.message = CurrencyConstant.BASE_CURRENT_NOT_FOUND;
                return responseMessage;
            }

            //check entry currency is present if not base currency will be entry currency
            if(requestMessage.entryCurrencyID == null || requestMessage.entryCurrencyID == 0)
            {
                requestMessage.entryCurrencyID = currencyModel.getCurrencyID();
            }

            reqVMProductAdjustment = Core.getRequestObject(requestMessage, VMProductAdjustment.class);
            productAdjustmentModel = reqVMProductAdjustment.productAdjustmentModel;
            productAdjustmentDetailModelList = reqVMProductAdjustment.productAdjustmentDetailModelList;

            //set currency
            if (productAdjustmentModel.getProductAdjustmentID() == null || productAdjustmentModel.getProductAdjustmentID() == 0) {
                productAdjustmentModel.setBaseCurrencyID(currencyModel.getCurrencyID());
                productAdjustmentModel.setEntryCurrencyID(requestMessage.entryCurrencyID);
                productAdjustmentModel.setBaseCurrencyAmount(productAdjustmentModel.getTotalPrice() * productAdjustmentModel.getExchangeRate());
            }

            productAdjustmentModel = this.productAdjustmentBllManager.saveOrUpdateProductAdjustment(productAdjustmentModel, currencyModel, requestMessage.entryCurrencyID);

            // For update first delete previous inventoryTransactionTable data
            if (productAdjustmentModel.getProductAdjustmentID() != 0) {
                whereConditionInventoryTran = new InventoryTransactionModel();
                //whereConditionInventoryTran.setBusinessID(requestMessage.businessID);
                whereConditionInventoryTran.setReferenceType(productAdjustmentModel.getProductAdjustmentID());
                whereConditionInventoryTran.setReferenceType(ReferenceType.ProductAdjustment.get());
                whereConditionInventoryTran.setStatus(TillBoxAppEnum.Status.Active.get());

                modelToUpdateInventoryTran = new InventoryTransactionModel();
                modelToUpdateInventoryTran.setStatus(TillBoxAppEnum.Status.Deleted.get());

                this.inventoryTransactionBllManager.updateByConditions(whereConditionInventoryTran, modelToUpdateInventoryTran);
            }

            for (ProductAdjustmentDetailModel productAdjustmentDetailModel : productAdjustmentDetailModelList) {

                productAdjustmentDetailModel.setProductAdjustmentID(productAdjustmentModel.getProductAdjustmentID());
                this.productAdjustmentDetailBllManager.saveOrUpdateProductAdjustmentDetail(productAdjustmentDetailModel);

                // Insert data into Inventory Transaction table
                inventoryTransactionModel.setBusinessID(requestMessage.businessID);
                inventoryTransactionModel.setReferenceID(productAdjustmentModel.getProductAdjustmentID());
                inventoryTransactionModel.setReferenceType(ReferenceType.ProductAdjustment.get());
                inventoryTransactionModel.setProductID(productAdjustmentDetailModel.getProductID());


                productModelWhereCondition.setProductID(productAdjustmentDetailModel.getProductID());
                productModelWhereCondition.setStatus(TillBoxAppEnum.Status.Active.get());

                productModelList = this.productBllManager.getAllByConditions(productModelWhereCondition);
                if (productModelList != null && productModelList.size() > 0) {
                    inventoryTransactionModel.setProductCategoryID(productModelList.get(0).getProductCategoryID());
                    inventoryTransactionModel.setProductTypeID(productModelList.get(0).getProductTypeID());
                }

                if (productAdjustmentModel.getAdjustmentType() == Adjustment.Decrease.get())
                    inventoryTransactionModel.setOutQuantity(productAdjustmentDetailModel.getQty());
                if (productAdjustmentModel.getAdjustmentType() == Adjustment.Increase.get())
                    inventoryTransactionModel.setInQuantity(productAdjustmentDetailModel.getQty());


                this.inventoryTransactionBllManager.save(inventoryTransactionModel);
                // Insert data into Inventory Transaction table

            }


            //productAdjustmentDetailModel = productAdjustmentDetailBllManager.saveOrUpdateProductAdjustmentDetail(productAdjustmentDetailModel);

            if (productAdjustmentModel != null) {

                VMProductAdjustment resultVmProductAdjustment = new VMProductAdjustment();
                resultVmProductAdjustment.productAdjustmentModel = productAdjustmentModel;
                resultVmProductAdjustment.productAdjustmentDetailModelList = productAdjustmentDetailModelList;

                responseMessage.responseObj = resultVmProductAdjustment;
                responseMessage.message = MessageConstant.SAVE_PRODUCT_ADJUSTMENT;
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

                this.commit();

            } else {
                responseMessage.responseObj = reqVMProductAdjustment;
                responseMessage.message = MessageConstant.FAILED_TO_SAVE_PRODUCT_ADJUSTMENT;
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;

                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("ProductAdjustmentServiceManager -> saveOrUpdateProductAdjustment got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            this.WriteExceptionLog(ex);
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.rollBack();
        }
        return responseMessage;
    }

    public ResponseMessage searchProductAdjustment(RequestMessage requestMessage) {
        ResponseMessage responseMessage;
        VMProductAdjustment reqVMProductAdjustment;
        ProductAdjustmentModel productAdjustmentModelWhereCondition;
        ProductAdjustmentDetailModel proAdjustDetailModelWhereCondition = new ProductAdjustmentDetailModel();

        List<ProductAdjustmentModel> productAdjustmentModelList = null;
        List<ProductAdjustmentDetailModel> productAdjustmentDetailModelList;

        List<VMProductAdjustment> vmProductAdjustmentList = new ArrayList<>();
        VMProductAdjustment vmProductAdjustment;


        try {
            responseMessage = Core.buildDefaultResponseMessage();
            reqVMProductAdjustment = Core.getRequestObject(requestMessage, VMProductAdjustment.class);

            productAdjustmentModelWhereCondition = reqVMProductAdjustment.productAdjustmentModel;


            //TODO: Model validation
            productAdjustmentModelList = productAdjustmentBllManager.getAllByConditions(productAdjustmentModelWhereCondition);

            if (productAdjustmentModelList.size() > 0 && productAdjustmentModelList != null) {
                for (ProductAdjustmentModel productAdjustmentModel : productAdjustmentModelList) {

                    vmProductAdjustment = new VMProductAdjustment();
                    vmProductAdjustment.productAdjustmentModel = productAdjustmentModel;

                    proAdjustDetailModelWhereCondition.setProductAdjustmentID(productAdjustmentModel.getProductAdjustmentID());

                    productAdjustmentDetailModelList = this.productAdjustmentDetailBllManager.getAllByConditions(proAdjustDetailModelWhereCondition);

                    if (productAdjustmentDetailModelList != null) {
                        vmProductAdjustment.productAdjustmentDetailModelList = productAdjustmentDetailModelList;
                    }
                    vmProductAdjustmentList.add(vmProductAdjustment);

                }
                responseMessage.responseObj = vmProductAdjustmentList;
                responseMessage.message = MessageConstant.SEARCH_PRODUCT_ADJUSTMENT;
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

            } else {
                responseMessage.responseObj = reqVMProductAdjustment;
                responseMessage.message = MessageConstant.SEARCH_PRODUCT_ADJUSTMENT;
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
            }

        } catch (Exception ex) {
            log.error("ProductAdjustmentServiceManager -> searchProductAdjustment got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            this.WriteExceptionLog(ex);
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
        }
        return responseMessage;
    }

    public ResponseMessage deleteProductAdjustment(RequestMessage requestMessage) {
        ResponseMessage responseMessage;
        VMProductAdjustment reqVMProductAdjustment;
        ProductAdjustmentDetailModel productAdjustmentDetailModel;
        ProductAdjustmentModel productAdjustmentModelWhereCondition;
        List<ProductAdjustmentModel> productAdjustmentModelList = null;

        Integer id;

        try {
            responseMessage = Core.buildDefaultResponseMessage();
            reqVMProductAdjustment = Core.getRequestObject(requestMessage, VMProductAdjustment.class);

            productAdjustmentModelWhereCondition = reqVMProductAdjustment.productAdjustmentModel;


            //TODO: Model validation
            productAdjustmentModelList = this.productAdjustmentBllManager.getAllByConditions(productAdjustmentModelWhereCondition);

            if (productAdjustmentModelList.size() > 0 && productAdjustmentModelList != null) {
                for (ProductAdjustmentModel productAdjustmentModel : productAdjustmentModelList) {
                    id = productAdjustmentModel.getProductAdjustmentID();
                    productAdjustmentDetailModel = this.productAdjustmentDetailBllManager.getById(id, TillBoxAppEnum.Status.Active.get());

                    if (productAdjustmentDetailModel != null) {
                        this.productAdjustmentDetailBllManager.softDelete(productAdjustmentDetailModel);
                    }
                    this.productAdjustmentBllManager.softDelete(productAdjustmentModel);
                }

                responseMessage.responseObj = reqVMProductAdjustment;
                responseMessage.message = MessageConstant.SEARCH_PRODUCT_ADJUSTMENT;
                responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;

                this.commit();

            } else {
                responseMessage.responseObj = reqVMProductAdjustment;
                responseMessage.message = MessageConstant.SEARCH_FAILED_PRODUCT_ADJUSTMENT;
                responseMessage.responseCode = TillBoxAppConstant.FAILED_ERROR_CODE;

                this.rollBack();
            }

        } catch (Exception ex) {
            log.error("ProductAdjustmentServiceManager -> deleteProductAdjustment got exception : " + ex.getMessage());
            for (Throwable throwable : ex.getSuppressed()) {
                log.error("suppressed: " + throwable);
            }
            this.WriteExceptionLog(ex);
            responseMessage = this.getDefaultResponseMessage(requestMessage.requestObj, TillBoxAppConstant.INTERNAL_SERVER_ERROR, TillBoxAppConstant.UN_PROCESSABLE_REQUEST);
            this.rollBack();
        }
        return responseMessage;
    }
}
