package nybsys.tillboxweb.appenum;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TillBoxAppEnum {

    public enum Status {
        Active(1),
        Inactive(2),
        Deleted(3);

        private int status;

        Status(int status) {
            this.status = status;
        }

        public int get() {
            return this.status;
        }
    }

    public enum UserStatus {
        Invited(4);

        private int userStatus;

        UserStatus(int userStatus) {
            this.userStatus = userStatus;
        }

        public int get() {
            return this.userStatus;
        }
    }


    public enum QueryType {
        Select(1),
        Insert(2),
        Update(3),
        Delete(4),
        Join(5),
        Raw(6),
        UpdateByConditions(7),
        CountRow(8),
        GetOne(9);

        private int queryType;

        QueryType(int queryType) {
            this.queryType = queryType;
        }

        public int get() {
            return this.queryType;
        }

        private static final Map<String, Integer> MAP = new HashMap<>();

        static {
            for (QueryType s : QueryType.values()) {
                MAP.put(s.name(), s.ordinal());
            }
        }

        public static Map<String, Integer> getMAP() {
            return MAP;
        }
    }

    public enum BrokerRequestType {
        API_CONTROLLER(0),
        WORKER(1);

        private int requestFrom;

        BrokerRequestType(int requestFrom) {
            this.requestFrom = requestFrom;
        }

        public int get() {
            return this.requestFrom;
        }
    }

}
