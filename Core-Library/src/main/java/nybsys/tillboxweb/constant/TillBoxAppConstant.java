package nybsys.tillboxweb.constant;

public final class TillBoxAppConstant {

    //Base Url
    public static final String FRONT_END_BASE_URL = "http://localhost:4200/";

    //Currency exchange rate live api
    public static final String CURRENCY_EXCHANGE_RATE_LIVE_API = "http://data.fixer.io/api/latest?access_key=0fd00e05cfe7c57404d3d797dfe30e0c";

    // Regex for acceptable login
    public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String ANONYMOUS_USER = "anonymoususer";
    public static final String DEFAULT_LANGUAGE = "en";

    public static final String INVALID_REQUEST = "Invalid Request";
    public static final String NOT_A_VALID_USER= "Not a Valid User";

    public static final String DUPLICATE_USER= "Duplicate User";

    public static final String INVALID_DATA= "Invalid Data";
    public static final String ACCESS_DENY= "Access Permission Required";

    public static final String NO_CONTENT= "No Content";
    public static final int NO_CONTENT_CODE = 204;

    public static final String PARTIAL_CONTENT= "Partial Content";
    public static final int PARTIAL_CONTENT_CODE= 206;

    public static final String SUCCESS= "Success";
    public static final int SUCCESS_CODE= 200;

    public static final String REDIRECT= "Redirect Page";
    public static final int REDIRECT_CODE= 302;

    public static final String FAILED= "Not Found";
    public static final int FAILED_ERROR_CODE= 404;

    public static final String BAD_REQUEST= "Bad Request";
    public static final int BAD_REQUEST_CODE= 400;

    public static final String PAYMENT_REQUIRED= "Payment Required";
    public static final int  PAYMENT_REQUIRED_CODE= 402;

    public static final String FORBIDDEN= "Forbidden";
    public static final int FORBIDDEN_CODE= 403;

    public static final String REQUEST_TIMEOUT= "Request Timeout";
    public static final int REQUEST_TIMEOUT_CODE= 408;

    public static final String CONFLICT= "Conflict";
    public static final int CONFLICT_CODE= 409;

    public static final String LOCKED= "Locked";
    public static final int LOCKED_CODE= 423;


    public static final int UN_PROCESSABLE_REQUEST= 422;

    public static final String TOO_MANY_REQUESTS= "Too Many Request";
    public static final int TOO_MANY_REQUESTS_CODE= 429;

    public static final String INTERNAL_SERVER_ERROR= "Internal Server Error";
    public static final int INTERNAL_SERVER_ERROR_CODE= 500;

    public static final String NOT_IMPLEMENTED_ERROR= "Not Implemented Error";
    public static final int NOT_IMPLEMENTED_ERROR_CODE= 501;

    public static final String SERVICE_UNAVAILABLE_ERROR= "Service Unavailable Error";
    public static final int SERVICE_UNAVAILABLE_ERROR_CODE= 503;

    public static final String UNAUTHORIZED_USER= "Unauthorized";
    public static final int UNAUTHORIZED_CODE= 401;

    public static final String SUSPENDED= "Suspended";
    public static final String WRONG_USER_NAME_PASSWORD= "User Name Or Password Does not Matched";

    public static final String EMAIL_NOT_FOUND= "Email Not Found";

    public static final String INVALID_TOKEN= "Invalid Token";
    public static final int INVALID_TOKEN_CODE= 400;

    public static final String INACTIVE= "Inactive";

    public static final String USER_NOT_FOUND= "User Not Found";

    public static final String OPERATION_FAILED= "Operation Failed";

    public static final String MAIL_SEND_SUCCESSFULLY= "Mail Send Successfully";

    public static final String NO_CHANGE_FOUND_IN_UPDATE= "No change found in update";



}
