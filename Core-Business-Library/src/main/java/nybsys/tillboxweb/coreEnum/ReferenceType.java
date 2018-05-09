package nybsys.tillboxweb.coreEnum;

public enum ReferenceType {
        Demo(0),
        OpeningBalance(1),
        MoneyTransfer(2),
        CombineAccount(3),
        SupplierInvoice(4),
        SupplierReturn(5),
        SupplierOpeningBalance(6),
        CustomerOpeningBalance(7),
        SupplierAdjustment(8),
        CustomerAdjustment(9),
        CustomerWriteOff(10),
        CustomerInvoice(11),
        CustomerReturn(12),
        CustomerReceipt(13),
        Product(14),
        ProductAdjustment(15),
        SupplierPayment(16),
        Expense(17),
        ExpenseAdjustment(18);


        private int referenceType;

        ReferenceType(int referenceType) {
            this.referenceType = referenceType;
        }

        public int get() {
            return this.referenceType;
        }
    }