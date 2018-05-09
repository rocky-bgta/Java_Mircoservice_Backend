/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 05/04/2018
 * Time: 12:42
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.coreEnum;

public enum BankReferenceType {
    Customer(1),
    Supplier(2);

    private int bankReferenceType;

    BankReferenceType(int bankReferenceType) {
        this.bankReferenceType = bankReferenceType;
    }

    public int get() {
        return this.bankReferenceType;
    }
}