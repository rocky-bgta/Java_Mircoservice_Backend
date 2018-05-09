/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 05/04/2018
 * Time: 12:27
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.coreEnum;

public enum AccountType {
    CurrentAsset(1),
    FixedAsset(2),
    IntangibleAsset(3),
    ShortTermLiabilities(4),
    LongTermLiabilities(5),
    OperatingIncome(6),
    NonOperatingIncome(7),
    OperatingExpense(8),
    NonOperatingExpense(9);

    private int accountType;

    AccountType(int accountType) {
        this.accountType = accountType;
    }

    public int get() {
        return this.accountType;
    }

}
