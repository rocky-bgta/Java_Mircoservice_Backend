package nybsys.tillboxweb.expense_enum;

import java.util.Map;
import java.util.TreeMap;

public enum ExpenseCategory {
    Expense(1),
    AdvanceExpense(2),
    DueExpense(3);

    private int expenseCategory;

    ExpenseCategory(int expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public int get() {
        return this.expenseCategory;
    }

    private static final Map<String, Integer> MAP = new TreeMap<>();

    static {
        for (ExpenseCategory kv : ExpenseCategory.values()) {
            MAP.put(kv.name(), kv.get());
        }
    }

    public static Map<String, Integer> getMAP() {
        return MAP;
    }
}