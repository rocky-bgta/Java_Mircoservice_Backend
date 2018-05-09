/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 31-Jan-18
 * Time: 4:11 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */
package nybsys.tillboxweb.broker.client;

import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.RollBackAndCommit;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CallBackForRollBack extends Core implements MqttCallback {

    private static final Logger log = LoggerFactory.getLogger(CallBackForRollBack.class);
    private String messageId;
    private RollBackAndCommit rollBackAndCommit;
    private Thread thread;


    @Override
    public void connectionLost(Throwable cause) {
        log.info("Connection to MQTT broker lost from RollBack CallBack!");
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        String incomingMessage;

        // log.info("====================================================================");
        log.info("Message received at Java RollBack End");

        incomingMessage = new String(mqttMessage.getPayload());


        this.messageId = Core.jsonMapper.readValue(incomingMessage, String.class);

        // Lambda Runnable
        Runnable runnable = () -> {
            this.rollBackAndCommit = new RollBackAndCommit();
            this.rollBackAndCommit.rollBack(this.messageId);
        };

        // start the thread
        this.thread = new Thread(runnable);
        this.thread.setName("RollBack Thread No: " + this.thread.getId());
        this.thread.start();

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
