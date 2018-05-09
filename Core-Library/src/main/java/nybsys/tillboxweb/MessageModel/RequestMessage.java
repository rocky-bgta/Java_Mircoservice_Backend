/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 22-Dec-17
 * Time: 11:56 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */
package nybsys.tillboxweb.MessageModel;

public class RequestMessage {

    public RequestMessage(){
        super();
    }

    public String token;
    public Object requestObj;
    public Integer businessID;
    public String userID;
    public Integer pageIndex;
    public Integer pageSize;
    public BrokerMessage brokerMessage;


    public Integer baseCurrencyID;
    public Integer entryCurrencyID;
    public Double exchangeRate;

}
