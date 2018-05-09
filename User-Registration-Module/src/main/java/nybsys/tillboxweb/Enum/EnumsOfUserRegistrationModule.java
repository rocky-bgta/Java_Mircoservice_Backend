package nybsys.tillboxweb.Enum;

import java.util.*;

public class EnumsOfUserRegistrationModule {


    public enum AccountingBasis {
        Cash(1), Accruals(2);

        private int accountingBasis;

        AccountingBasis(int accountingBasis) {
            this.accountingBasis = accountingBasis;
        }

        public int get() {
            return this.accountingBasis;
        }

        private static final Map<String, Integer> MAP = new TreeMap<>();
        static {
            for (AccountingBasis kv : AccountingBasis.values()) {
                MAP.put(kv.name(), kv.get());
            }
        }
        public static Map<String, Integer> getMAP() {
            return MAP;
        }
    }

    public enum ReportingFrequency {
        Monthly(1), Quarterly(2), Annually(3);

        private int reportingFrequency;
        ReportingFrequency(int reportingFrequency) {
            this.reportingFrequency = reportingFrequency;
        }

        public int get() {
            return this.reportingFrequency;
        }

        private static final Map<String, Integer> MAP = new TreeMap<>();

        static {
            for (ReportingFrequency kv : ReportingFrequency.values()) {
                MAP.put(kv.name(), kv.get());
            }
        }
        public static Map<String, Integer> getMAP() {
            return MAP;
        }
    }

    public enum GstOption {
        Calculated_Gst_And_Report(1),
        Calculate_Gst_And_Report_Quarterly(2),
        Pay_Gst_Installment_Amount_Quarterly(3);

        private int gstOption;

        GstOption(int gstOption) {
            this.gstOption = gstOption;
        }

        public int get() {
            return this.gstOption;
        }

        private static final Map<String, Integer> MAP = new TreeMap<>();
        static {
            for (GstOption kv : GstOption.values()) {
                MAP.put(kv.name().toString().replaceAll("_", " "), kv.get());
            }
        }
        public static Map<String, Integer> getMAP() {
            return MAP;
        }
    }

    public enum BaseLodgementOption {
        Lodge_By_Paper(1),
        Lodge_By_Business_Portal_Or_ECI(2),
        Tax_Pactitioner_Lodges_A_Paper_Copy_Or_Via_ECI(3),
        Tax_Pactitioner_Lodges_Electronically(4);

        private int baseLodgementOption;

        BaseLodgementOption(int baseLodgementOption) {
            this.baseLodgementOption = baseLodgementOption;
        }

        public int get() {
            return this.baseLodgementOption;
        }

        private static final Map<String, Integer> MAP = new TreeMap<>();
        static {
            for (BaseLodgementOption kv : BaseLodgementOption.values()) {
                MAP.put(kv.name().toString().replaceAll("_", " "), kv.get());
            }
        }
        public static Map<String, Integer> getMAP() {
            return MAP;
        }
    }

    public enum TaxType
    {
        ImportDuty(10),
        Consolidated(2),
        SalesTax(3),
        GoodsAndServicesTax(4),
        InputTaxed(5),
        LuxuryCarsTax(6);

        private int taxType;
        // constructor
        TaxType(int taxType) {
            this.taxType = taxType;
        }

        public int get() {
            return this.taxType;
        }

        private static final Map<String, Integer> MAP = new TreeMap<>();

        static {
            for (TaxType kv : TaxType.values()) {
                MAP.put(kv.name(), kv.get());
            }
        }
        public static Map<String, Integer> getMAP() {
            return MAP;
        }

    }
}
