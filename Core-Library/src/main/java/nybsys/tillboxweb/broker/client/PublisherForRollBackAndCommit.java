/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 06-Feb-18
 * Time: 11:51 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.broker.client;

import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.constant.WorkerPublishedConstants;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PublisherForRollBackAndCommit extends Core {
    private static final Logger log = LoggerFactory.getLogger(PublisherForRollBackAndCommit.class);

    private static MqttClient mqttClient;
    private final static String mqttServerURI;
    private final static MqttConnectOptions mqttConnectOptions;
    private final static MqttMessage mqttMessage;
    private final static String mqttServerHost;
    private final static int mqttServerPort;

    static {
        mqttServerHost = "localhost";
        mqttServerPort = 1883;
        mqttServerURI = String.format("tcp://%s:%d", mqttServerHost, mqttServerPort);
    }

    static {
        mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
        mqttConnectOptions.setKeepAliveInterval(0);
        mqttConnectOptions.setCleanSession(false);
    }

    static {
        mqttMessage = new MqttMessage();
        mqttMessage.setQos(1);
        //mqttMessage.setRetained(true);
    }

    static {
        try {
            if (mqttClient == null) {
                mqttClient = new MqttClient(mqttServerURI, "RollBackCommit " + MqttClient.generateClientId(), null);
                mqttClient.connect(mqttConnectOptions);
            }

        } catch (Exception e) {
            log.error("Exception from RollBack and Commit Publisher");
            e.printStackTrace();
        }
    }

    public void publishedMessageForRollBack(String messageId) {
        String jsonString;
        try {
            if (mqttClient.isConnected()) {
                jsonString = Core.jsonMapper.writeValueAsString(messageId);
                mqttMessage.setPayload(jsonString.getBytes());
                mqttClient.publish(WorkerPublishedConstants.ROLLBACK_TOPIC, mqttMessage);
            } else
                throw new Exception("Connected to Broker Failed from publishedMessageForRollBack");
        } catch (Exception e) {
            log.error("Exception from RollBack Publisher");
            e.printStackTrace();
        }

    }

    public void publishedMessageForCommit(String messageId) {
        String jsonString;
        try {
            if (mqttClient.isConnected()) {
                jsonString = Core.jsonMapper.writeValueAsString(messageId);
                mqttMessage.setPayload(jsonString.getBytes());
                mqttClient.publish(WorkerPublishedConstants.COMMIT_TOPIC, mqttMessage);
            } else
                throw new Exception("Connected to Broker Failed from publishedMessageForCommit");
        } catch (Exception e) {
            log.error("Exception from Commit Publisher");
            e.printStackTrace();
        }
    }
}
