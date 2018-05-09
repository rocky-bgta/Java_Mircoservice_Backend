/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 22-Dec-17
 * Time: 11:44 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */
package nybsys.tillboxweb.MessageModel;

public class ResponseMessage {

    public ResponseMessage() {
        super();
    }

    public String token;
    public Object responseObj;
    public Integer responseCode;
    public String message;
    public String errorMessage;
    public Integer businessID;
    //public BrokerMessage brokerMessage;
    public String brokerMessageId;

    // Pagination
    public Integer pageIndex;
    public Integer recordCount;
    public Integer entryCurrencyID;

}
