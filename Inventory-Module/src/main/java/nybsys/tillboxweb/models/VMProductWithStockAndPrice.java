/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 05/03/2018
 * Time: 04:09
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.models;


public class VMProductWithStockAndPrice {
    public ProductModel productModel = new ProductModel();
    public ProductPurchasePriceModel productPurchasePriceModel = new ProductPurchasePriceModel();
    public ProductSalesPriceModel productSalesPriceModel = new ProductSalesPriceModel();
    public Double stock;
}
