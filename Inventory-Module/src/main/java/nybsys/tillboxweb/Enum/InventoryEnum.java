/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 13-Feb-18
 * Time: 12:48 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.Enum;

import java.util.Map;
import java.util.TreeMap;

public class InventoryEnum {

    public enum ProductAdjustmentReferenceType {
        Stolen(1),
        Damage(2),
        SalesReturn(3),
        PurchaseReturn(4);

        private int productAdjustmentReferenceType;

        ProductAdjustmentReferenceType(int fieldType) {
            this.productAdjustmentReferenceType = productAdjustmentReferenceType;
        }

        public int get() {
            return this.productAdjustmentReferenceType;
        }

        private static final Map<String, Integer> MAP = new TreeMap<>();

        static {
            for (ProductAdjustmentReferenceType kv : ProductAdjustmentReferenceType.values()) {
                MAP.put(kv.name(), kv.get());
            }
        }
        public static Map<String, Integer> getMAP() {
            return MAP;
        }

    }

    public enum AdjustmentType {
        In(1),
        Out(2);

        private int adjustmentType;

        AdjustmentType(int adjustmentType) {
            this.adjustmentType = adjustmentType;
        }

        public int get() {
            return this.adjustmentType;
        }

        private static final Map<String, Integer> MAP = new TreeMap<>();

        static {
            for (AdjustmentType kv : AdjustmentType.values()) {
                MAP.put(kv.name(), kv.get());
            }
        }
        public static Map<String, Integer> getMAP() {
            return MAP;
        }

    }
}
