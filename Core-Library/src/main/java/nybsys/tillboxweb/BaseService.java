package nybsys.tillboxweb;


import nybsys.tillboxweb.constant.TillBoxAppConstant;
import nybsys.tillboxweb.MessageModel.ResponseMessage;

public abstract class BaseService extends Core {
    public BaseService(){
        super();
    }


    protected ResponseMessage getDefaultResponseMessage(Object requestObject, String message, int code, String token){
        ResponseMessage responseMessage = getDefaultResponseMessage(requestObject,message,code);
        if(token!=null)
            responseMessage.token=token;
        return responseMessage;
    }

    public ResponseMessage getDefaultResponseMessage() {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.responseCode = TillBoxAppConstant.SUCCESS_CODE;
        responseMessage.message = TillBoxAppConstant.SUCCESS;
        return responseMessage;
    }

    protected ResponseMessage getDefaultResponseMessage(Object requestObject, String message, int code){
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.responseObj = requestObject;
        responseMessage.message = message;
        responseMessage.responseCode = code;
        return responseMessage;
    }

    protected boolean  WriteExceptionLog(Exception ex){

        return true;
    }
}
