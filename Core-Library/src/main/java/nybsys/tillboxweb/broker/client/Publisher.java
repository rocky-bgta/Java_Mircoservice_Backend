package nybsys.tillboxweb.broker.client;


import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("ALL")
public class Publisher extends Core {
    private static final Logger log = LoggerFactory.getLogger(Publisher.class);

    private String publishedTopic;
    private static MqttClient mqttClient;

    static {
        try {
            mqttClient = MqttUtils.getMqttClient();
        } catch (Exception e) {
            log.error("Exception from Core Publisher");
            e.printStackTrace();
        }
    }

    private static MqttMessage mqttMessage;

    public Publisher(String publishedTopic) {
        this.publishedTopic = publishedTopic;
    }

    public void publishedMessageToBroker(ResponseMessage responseMessage, String messageId) {
        String jsonString;
        try {

            /*if (Publisher.mqttClient == null) {
                Publisher.mqttClient = MqttUtils.getMqttClient();
                Publisher.mqttMessage = MqttUtils.getMqttDefaultMessage();
            }*/

            if (Publisher.mqttClient.isConnected()) {
                Publisher.mqttMessage = MqttUtils.getMqttDefaultMessage();
                jsonString = Core.jsonMapper.writeValueAsString(responseMessage);
                Publisher.mqttMessage.setPayload(jsonString.getBytes());
                Publisher.mqttClient.publish(messageId, Publisher.mqttMessage);
                //Publisher.mqttClient.unsubscribe(publishedTopic);
            } else
                throw new Exception("Connected to Broker Failed");
            log.info("Message published from Java End");

        } catch (Exception e) {
            log.error("Exception from Core Publisher");
            e.printStackTrace();
        }

    }

    public void publishedMessageToBroker(ResponseMessage responseMessage) {
        String jsonString;
        try {

           /* if (Publisher.mqttClient == null) {
                Publisher.mqttClient = MqttUtils.getMqttClient();
                Publisher.mqttMessage = MqttUtils.getMqttDefaultMessage();
            }*/

            if (Publisher.mqttClient.isConnected()) {
                Publisher.mqttMessage = MqttUtils.getMqttDefaultMessage();
                jsonString = Core.jsonMapper.writeValueAsString(responseMessage);
                Publisher.mqttMessage.setPayload(jsonString.getBytes());
                Publisher.mqttClient.publish(publishedTopic, Publisher.mqttMessage);
                System.out.println("published Topic from Java end:: "+publishedTopic);
                System.out.println("Message published from Java end: "+jsonString);
                //Publisher.mqttClient.unsubscribe(publishedTopic);
            } else
                throw new Exception("Connected to Broker Failed");
            log.info("Message published from Core Java End");

        } catch (Exception e) {
            log.error("Exception from Core Publisher");
            e.printStackTrace();
        }
    }
}
