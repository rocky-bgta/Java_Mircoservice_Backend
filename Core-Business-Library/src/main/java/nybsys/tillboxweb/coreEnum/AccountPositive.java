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

public enum AccountPositive //debit-credit
{
    Asset(1),
    Expenses(5);

    private int accountPositive;

    AccountPositive(int accountPositive) {
        this.accountPositive = accountPositive;
    }

    public int get() {
        return this.accountPositive;
    }

    private static final Map<String, Integer> MAP = new TreeMap<>();

    static {
        for (AccountPositive kv : AccountPositive.values()) {
            MAP.put(kv.name(), kv.get());
        }
    }

    public static Map<String, Integer> getMAP() {
        return MAP;
    }

}