/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 05/04/2018
 * Time: 12:39
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.coreEnum;

public enum BusinessType {
    StandardBusiness(1);

    private int businessType;

    BusinessType(int businessType) {
        this.businessType = businessType;
    }

    public int get() {
        return this.businessType;
    }
}