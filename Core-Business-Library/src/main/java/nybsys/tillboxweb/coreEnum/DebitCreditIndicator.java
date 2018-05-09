package nybsys.tillboxweb.coreEnum;

public enum DebitCreditIndicator {
        Debit(1),
        Credit(2);

        private int debitCreditIndicator;

        DebitCreditIndicator(int debitCreditIndicator) {
            this.debitCreditIndicator = debitCreditIndicator;
        }

        public int get() {
            return this.debitCreditIndicator;
        }

    }