/**
 * Created By: Md. Abdul Hannan
 * Created Date: 2/14/2018
 * Time: 11:09 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */
package nybsys.tillboxweb.models;

import nybsys.tillboxweb.BaseModel;
import nybsys.tillboxweb.models.*;

import java.util.ArrayList;
import java.util.List;

public class VMProduct extends BaseModel {
    public ProductModel productModel;
    public List<ProductAttributeMapperModel> lstProductAttributeMapperModels;
//    public ProductPictureModel productPictureModel;
//    public ProductDocumentModel productDocumentModel;
    public List<ProductPurchasePriceModel> lstProductPurchasePriceModel;
    public List<ProductSalesPriceModel> lstProductSalesPriceModel;
    public List<UserDefineSettingModel> lstUserDefineSettingModels;
    public List<UserDefineSettingDetailModel> lstUserDefineSettingDetailModels;
    public List<RememberNoteModel> lstRememberNoteModels;


    public VMProduct() {
        productModel = new ProductModel();
        lstProductAttributeMapperModels = new ArrayList<>();
//        productPictureModel = new ProductPictureModel();
//        productDocumentModel = new ProductDocumentModel();
        lstProductPurchasePriceModel = new ArrayList<>();
        lstProductSalesPriceModel = new ArrayList<>();
        lstRememberNoteModels = new ArrayList<>();
        lstUserDefineSettingModels = new ArrayList<>();
        lstUserDefineSettingDetailModels = new ArrayList<>();
    }




}
