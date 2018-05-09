/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 12-Jan-18
 * Time: 4:42 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */
package nybsys.tillboxweb.broker.client;


import nybsys.tillboxweb.BaseController;
import nybsys.tillboxweb.WorkerThread;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientCallBack implements MqttCallback {
    private static final Logger log = LoggerFactory.getLogger(ClientCallBack.class);
    private String publishedTopic;
    private BaseController baseController;

    public ClientCallBack(String publishedTopic, BaseController baseController){
        this.publishedTopic = publishedTopic;
        this.baseController = baseController;
    }

    public void connectionLost(Throwable throwable) {
        log.info("Connection to MQTT broker lost!");
    }

    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        Thread thread;
        WorkerThread workerThread = new WorkerThread(this.publishedTopic, this.baseController);
        String incomingMessage;
        log.info("===================");
        log.info("Message received at Java End");

        incomingMessage = new String(mqttMessage.getPayload());
        log.info(incomingMessage);


        if (incomingMessage == null) {
            throw new Exception();
        }
        workerThread.setIncomingBrokerMessage(incomingMessage);
        thread = new Thread(workerThread);
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.setName(this.publishedTopic+ " Worker: " + thread.getId());
        thread.start();

    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
    }
}
