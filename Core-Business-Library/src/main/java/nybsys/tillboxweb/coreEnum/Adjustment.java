/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 02-Mar-18
 * Time: 10:45 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.coreEnum;

import java.util.Map;
import java.util.TreeMap;

public enum Adjustment {
    Decrease(1),
    Increase(2);

    private int adjustment;

    Adjustment(int adjustment) {
        this.adjustment = adjustment;
    }

    public int get() {
        return this.adjustment;
    }

    private static final Map<String, Integer> MAP = new TreeMap<>();

    static {
        for (Adjustment kv : Adjustment.values()) {
            MAP.put(kv.name(), kv.get());
        }
    }

    public static Map<String, Integer> getMAP() {
        return MAP;
    }

}
