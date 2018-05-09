/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 23-Feb-18
 * Time: 11:44 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;

public interface BaseServiceManager {

    ResponseMessage saveOrUpdate(RequestMessage requestMessage);
    ResponseMessage search(RequestMessage requestMessage);
    ResponseMessage delete(RequestMessage requestMessage);
    ResponseMessage inActive(RequestMessage requestMessage);
    ResponseMessage getById(RequestMessage requestMessage);

}
