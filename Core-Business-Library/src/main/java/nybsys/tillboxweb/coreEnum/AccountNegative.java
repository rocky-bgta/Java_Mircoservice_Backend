/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 05/04/2018
 * Time: 12:41
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.coreEnum;

import java.util.Map;
import java.util.TreeMap;

public enum AccountNegative // credit-debit
{
    Liability(2),
    OwnerEquities(3),
    Income(4);

    private int accountNegative;

    AccountNegative(int accountNegative) {
        this.accountNegative = accountNegative;
    }

    public int get() {
        return this.accountNegative;
    }

    private static final Map<String, Integer> MAP = new TreeMap<>();

    static {
        for (AccountNegative kv : AccountNegative.values()) {
            MAP.put(kv.name(), kv.get());
        }
    }

    public static Map<String, Integer> getMAP() {
        return MAP;
    }

}