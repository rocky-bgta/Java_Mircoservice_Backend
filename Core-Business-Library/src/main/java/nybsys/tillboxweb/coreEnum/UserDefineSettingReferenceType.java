/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 05/04/2018
 * Time: 12:44
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.coreEnum;

import java.util.Map;
import java.util.TreeMap;

public enum UserDefineSettingReferenceType {
    Product(1),
    Customer(2);

    private int userDefineSettingReferenceType;

    UserDefineSettingReferenceType(int userDefineSettingReferenceType) {
        this.userDefineSettingReferenceType = userDefineSettingReferenceType;
    }

    public int get() {
        return this.userDefineSettingReferenceType;
    }

    private static final Map<String, Integer> MAP = new TreeMap<>();

    static {
        for (UserDefineSettingReferenceType kv : UserDefineSettingReferenceType.values()) {
            MAP.put(kv.name(), kv.get());
        }
    }

    public static Map<String, Integer> getMAP() {
        return MAP;
    }
}