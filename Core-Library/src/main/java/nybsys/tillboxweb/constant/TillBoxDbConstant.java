/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 21-Dec-17
 * Time: 10:13 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */
package nybsys.tillboxweb.constant;

public final class TillBoxDbConstant {
    public static final String DB_DRIVER_CLASS = "org.postgresql.Driver";
    public static final String DB_DIALECT = "org.hibernate.dialect.PostgreSQL9Dialect";
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/";
    //public static final String DB_URL = "jdbc:postgresql://10.200.10.151:5432/";
    public static final String DB_USER_NAME = "postgres";
    public static final String DB_USER_PASSWORD = "abcd123!";
    //public static final String DEFAULT_DB_ENTITY_PATH = "nybsys.tillboxweb.entities";
    public static final String BUSINESS_DB_ENTITY_PATH = "nybsys.tillboxweb.entities";
    public static final String DEFAULT_DB_ENTITY_PATH = "nybsys.tillboxweb.TillBoxWebEntities";
    public static final String CORE_DB_ENTITY_PATH = "nybsys.tillboxweb.coreEntities";
    public static final String HISTORY_ENTITY_PATH = "nybsys.tillboxweb.TillBoxWebHistoryEntity";
    public static final String DEFAULT_DATABASE = "TillBoxWeb";
    public static final String CREATE_TABLE = "create";
    public static final String UPDATE_TABLE = "update";
}
