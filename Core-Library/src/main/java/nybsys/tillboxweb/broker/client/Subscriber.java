package nybsys.tillboxweb.broker.client;


import nybsys.tillboxweb.BaseController;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Subscriber {

    private static final Logger log = LoggerFactory.getLogger(Subscriber.class);
    private ClientCallBack clientCallBack;
    private String subscribeTopic;
    private String publishedTopic;
    private BaseController baseController;


    public Subscriber(String subscribeTopic, String publishedTopic, BaseController controller) {
        this.subscribeTopic = subscribeTopic;
        this.publishedTopic = publishedTopic;
        this.baseController = controller;
    }

    public void subscribe() throws MqttException {
        int QoS = 0;
        try {
            this.clientCallBack = new ClientCallBack(this.publishedTopic,this.baseController);
            String connectionUrl = MqttUtils.getConnectionUrl();
            MqttConnectOptions mqttConnectOptions = MqttUtils.getMqttConnectOptions();
            MqttClient mqttClient = MqttUtils.getMqttClient(connectionUrl, mqttConnectOptions);
            mqttClient.setCallback(this.clientCallBack);
            mqttClient.subscribe(this.subscribeTopic, QoS);
            log.info("== SUBSCRIBED TO BROKER ==");
        } catch (MqttException ex) {
            log.info("Exception from Subscriber");
            ex.printStackTrace();
            throw ex;
        }
    }
}
