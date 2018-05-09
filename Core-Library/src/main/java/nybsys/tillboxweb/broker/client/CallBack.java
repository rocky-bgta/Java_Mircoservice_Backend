/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 16-Jan-18
 * Time: 10:13 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */
package nybsys.tillboxweb.broker.client;

import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CallBack extends Core implements MqttCallback {
    private static final Logger log = LoggerFactory.getLogger(CallBack.class);
    private ResponseMessage responseMessage;

    private Object lockObject;

    public CallBack() {
    }

    public CallBack(Object lockObject) {
        this.lockObject = lockObject;
    }

    @Override
    public void connectionLost(Throwable cause) {
        log.info("Connection to MQTT broker lost from Worker!");
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        String incomingMessage;
        log.info("===================");
        log.info("Message received at Java Worker End");

        incomingMessage = new String(mqttMessage.getPayload());

        this.responseMessage = Core.jsonMapper.readValue(incomingMessage, ResponseMessage.class);

        try {

            synchronized (this.lockObject) {
                //System.out.println(item);
                log.info("======== In worker lock ===========");
                this.lockObject.notifyAll();
                log.info("======== Lock release for worker ======");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            log.info("Exception from Worker Client Call Back");
            throw ex;
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    public ResponseMessage getResponseMessage() {
        return responseMessage;
    }
}
