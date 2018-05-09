/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 23-Feb-18
 * Time: 3:41 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.Enum;

import java.util.Map;
import java.util.TreeMap;

public class CommonEnum {

    public enum ReferenceType {
        Product(1),
        PurchaseOrder(2),
        Invoice(3);

        private int referenceType;

        ReferenceType(int referenceType) {
            this.referenceType = referenceType;
        }

        public int get() {
            return this.referenceType;
        }

        private static final Map<String, Integer> MAP = new TreeMap<>();

        static {
            for (ReferenceType kv : ReferenceType.values()) {
                MAP.put(kv.name(), kv.get());
            }
        }
        public static Map<String, Integer> getMAP() {
            return MAP;
        }

    }
}
