/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 05/04/2018
 * Time: 12:39
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.coreEnum;

public enum ProductType {
    Accounting(1);

    private int productType;

    ProductType(int productType) {
        this.productType = productType;
    }

    public int get() {
        return this.productType;
    }
}