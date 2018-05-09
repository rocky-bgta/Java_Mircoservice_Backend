/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 22-Dec-17
 * Time: 11:14 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */
package nybsys.tillboxweb;


import nybsys.tillboxweb.appenum.TillBoxAppEnum;

import nybsys.tillboxweb.broker.client.Publisher;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;

public class WorkerThread extends Core implements Runnable {

    private String publishedTopic;
    private String incomingBrokerMessage;
    private RequestMessage requestMessage;
    private ResponseMessage responseMessage;

    private BaseController baseController;
    private String serviceName;
    private String messageId;

    public WorkerThread(String publishedTopic,BaseController baseController){
        this.publishedTopic = publishedTopic;
        this.baseController = baseController;
    }


    @Override
    public void run() {
        try {
            this.getRequestMessage();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    // this is final method
    public ResponseMessage getRequestMessage() {
        try{
            Publisher publisher = new Publisher(publishedTopic);
            this.requestMessage = Core.jsonMapper.readValue(incomingBrokerMessage,RequestMessage.class);

            this.serviceName = this.requestMessage.brokerMessage.serviceName;
            this.messageId = this.requestMessage.brokerMessage.messageId;
            if(this.baseController!=null) {
                this.responseMessage = this.baseController.getResponseMessage(this.serviceName, this.requestMessage);
                this.responseMessage.brokerMessageId = this.messageId;

                if(this.requestMessage.brokerMessage.requestFrom ==
                        TillBoxAppEnum.BrokerRequestType.API_CONTROLLER.get()) {
                    publisher.publishedMessageToBroker(this.responseMessage);
                }else if(this.requestMessage.brokerMessage.requestFrom ==
                        TillBoxAppEnum.BrokerRequestType.WORKER.get()){
                    publisher.publishedMessageToBroker(this.responseMessage,this.messageId);
                }

            }else {
                throw new Exception("Router Implementation not provided");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            this.baseController=null;
        }
        return this.responseMessage;
    }

    public void setIncomingBrokerMessage(String incomingBrokerMessage) {
        this.incomingBrokerMessage = incomingBrokerMessage;
    }
}
