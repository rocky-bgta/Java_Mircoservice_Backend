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

public enum FieldType {
    Text(1),
    CheckBox(2);

    private int fieldType;

    FieldType(int fieldType) {
        this.fieldType = fieldType;
    }

    public int get() {
        return this.fieldType;
    }

    private static final Map<String, Integer> MAP = new TreeMap<>();

    static {
        for (FieldType kv : FieldType.values()) {
            MAP.put(kv.name(), kv.get());
        }
    }

    public static Map<String, Integer> getMAP() {
        return MAP;
    }

}