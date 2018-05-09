/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 23-Feb-18
 * Time: 11:51 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb;

import nybsys.tillboxweb.MessageModel.RequestMessage;

import java.util.List;

public interface BaseBllManager {
    //type     return type
    <M> M saveOrUpdate (RequestMessage requestMessage) throws Exception;
    //type     return type
    <M> List<M> search(RequestMessage requestMessage) throws Exception;
    //type     return type
    <M> List<M> getAllModels() throws Exception;

    //type     return type
    <M> Integer deleteByConditions(RequestMessage requestMessage) throws Exception;

    //type     return type
    <M> Integer inactiveByConditions(RequestMessage requestMessage) throws Exception;

    <M> M inActive(RequestMessage requestMessage) throws Exception;

    <M> M delete(RequestMessage requestMessage) throws Exception;

    //type     return type
    <M> M getByID(RequestMessage requestMessage) throws Exception;
}
