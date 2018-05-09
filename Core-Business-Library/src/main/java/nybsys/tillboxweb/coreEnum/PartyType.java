package nybsys.tillboxweb.coreEnum;

public enum PartyType {

        Customer(1),
        Supplier(2),
        Bank(3),
        ItemAdjustment(4);

        private int partyType;

        PartyType(int partyType) {
            this.partyType = partyType;
        }

        public int get() {
            return this.partyType;
        }

    }