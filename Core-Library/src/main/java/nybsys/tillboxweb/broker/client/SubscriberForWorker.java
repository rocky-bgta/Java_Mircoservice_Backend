/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 16-Jan-18
 * Time: 12:04 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */
package nybsys.tillboxweb.broker.client;

import nybsys.tillboxweb.Core;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CyclicBarrier;

public class SubscriberForWorker extends Core {

    private static final Logger log = LoggerFactory.getLogger(SubscriberForWorker.class);


    private CallBack clientCallBackForWorker;
    private String brokerMessageId;
    private Object lockObject;

    private MqttClient mqttClient;

    private CyclicBarrier barrier;


    public SubscriberForWorker(String brokerMessageId, CyclicBarrier barrier) {
        this.brokerMessageId = brokerMessageId;
        this.barrier = barrier;
    }

    public SubscriberForWorker(String brokerMessageId) {
        this.brokerMessageId = brokerMessageId;
    }

    public MqttClient subscribe() throws MqttException {
        int QoS = 0;
        try {

            String connectionUrl = MqttUtils.getConnectionUrl();
            MqttConnectOptions mqttConnectOptions = MqttUtils.getMqttConnectOptions();
            this.mqttClient = MqttUtils.getMqttClient(connectionUrl, mqttConnectOptions);

            Object lock = new Object();

            new InterModuleWorkerCommunicationThread(barrier,lock);

            this.clientCallBackForWorker = new CallBack(lock);

            this.mqttClient.setCallback(this.clientCallBackForWorker);

            this.mqttClient.subscribe(this.brokerMessageId, QoS);
            log.info("== subscribed to broker for worker ==");
        } catch (MqttException ex) {
            log.info("Exception from worker Subscriber");
            ex.printStackTrace();
            throw ex;
        }
        return this.mqttClient;
    }

    public CallBack getCallBack() {
        return clientCallBackForWorker;
    }
}
