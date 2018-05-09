/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 01-Mar-18
 * Time: 4:35 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.coreEnum;

import java.util.Map;
import java.util.TreeMap;


public enum DefaultCOA
{

    HistoricalBalance(133),
    AccountReceivable(14),
    AccountPayable(59),
    PurchaseVAT(114),
    SalesVAT(114),
    DiscountEarn(141),
    DiscountGiven(185),
    Inventory(15),
    AdjustmentIncome(143),
    StockAdjustmentIncome(142),
    StockAdjustmentLoss(226),
    AdjustmentLoss(172),
    TradeCreditors(59),
    BankAccount(3),
    TradeDebtors(14),
    CashAccount(6),
    BadDebts(170),
    Sales(135),
    CostOfGoodsSold(148);

    private int coa;

    DefaultCOA(int coa) {
        this.coa = coa;
    }

    public int get() {
        return this.coa;
    }

    private static final Map<String, Integer> MAP = new TreeMap<>();

    static {
        for (DefaultCOA kv : DefaultCOA.values()) {
            MAP.put(kv.name(), kv.get());
        }
    }

    public static Map<String, Integer> getMAP() {
        return MAP;
    }

}
