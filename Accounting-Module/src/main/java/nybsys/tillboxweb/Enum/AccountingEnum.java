/**
 * Created By: Md. Rashed Khan Menon
 * Created Date: 12/02/2018
 * Time: 11:13
 * Modified By:
 * Modified date:
 * (C) CopyRight NybSys ltd.
 */

package nybsys.tillboxweb.Enum;

import java.util.Map;
import java.util.TreeMap;

public class AccountingEnum {
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

    public enum PartyPositive //debit-credit
    {
        Asset(1),
        Expenses(5);

        private int partyPositive;

        PartyPositive(int partyPositive) {
            this.partyPositive = partyPositive;
        }

        public int get() {
            return this.partyPositive;
        }

        private static final Map<String, Integer> MAP = new TreeMap<>();

        static {
            for (PartyPositive kv : PartyPositive.values()) {
                MAP.put(kv.name(), kv.get());
            }
        }
        public static Map<String, Integer> getMAP() {
            return MAP;
        }

    }
    public enum PartyNegative // credit-debit
    {
        Liability(2),
        OwnerEquities(3),
        Income(4);

        private int partyNegative;

        PartyNegative(int partyNegative) {
            this.partyNegative = partyNegative;
        }

        public int get() {
            return this.partyNegative;
        }

        private static final Map<String, Integer> MAP = new TreeMap<>();

        static {
            for (PartyNegative kv : PartyNegative.values()) {
                MAP.put(kv.name(), kv.get());
            }
        }
        public static Map<String, Integer> getMAP() {
            return MAP;
        }

    }
}
