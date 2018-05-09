/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 05/04/2018
 * Time: 12:32
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.coreEnum;

public enum AccountClassification {
    Asset(1),
    Liability(2),
    OwnerEquities(3),
    Income(4),
    Expenses(5);

    private int accountClassification;

    AccountClassification(int accountClassification) {
        this.accountClassification = accountClassification;
    }

    public int get() {
        return this.accountClassification;
    }
}