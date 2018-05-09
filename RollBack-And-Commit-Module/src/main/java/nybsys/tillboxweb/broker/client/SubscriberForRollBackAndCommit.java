package nybsys.tillboxweb.broker.client;


import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubscriberForRollBackAndCommit {

    private static final Logger log = LoggerFactory.getLogger(SubscriberForRollBackAndCommit.class);
    private MqttCallback mqttCallback;
    private String subscribeTopic;

    public SubscriberForRollBackAndCommit(){
        super();
    }

    public SubscriberForRollBackAndCommit(String subscribeTopic, MqttCallback mqttCallback) {
        this.subscribeTopic = subscribeTopic;
        this.mqttCallback = mqttCallback;
    }

    public void subscribe() throws MqttException {
        int QoS = 1;
        try {
            //this.callBack = new CallBackForRollBack();
            String connectionUrl = MqttRollBackUtils.getConnectionUrl();
            MqttConnectOptions mqttConnectOptions = MqttRollBackUtils.getMqttConnectOptions();
            MqttClient mqttClient = MqttRollBackUtils.getMqttClient(connectionUrl, mqttConnectOptions);
            mqttClient.setCallback(this.mqttCallback);
            mqttClient.subscribe(this.subscribeTopic, QoS);
            log.info("SUBSCRIBED TO BROKER FROM ROLLBACK AND COMMIT WORKER ON TOPIC: " +this.subscribeTopic);
        } catch (MqttException ex) {
            log.info("Exception from BROKER FROM ROLLBACK AND COMMIT WORKER");
            ex.printStackTrace();
            throw ex;
        }
    }
}
