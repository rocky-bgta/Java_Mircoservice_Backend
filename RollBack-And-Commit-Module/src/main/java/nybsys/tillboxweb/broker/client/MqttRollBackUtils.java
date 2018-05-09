package nybsys.tillboxweb.broker.client;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

@SuppressWarnings("Duplicates")
public class MqttRollBackUtils {
    public static final int BROKER_CONNECTION_KEEP_ALIVE_TIME=0;

    static public MqttConnectOptions getMqttConnectOptions() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
        //mqttConnectOptions.setKeepAliveInterval(MqttConnectOptions.KEEP_ALIVE_INTERVAL_DEFAULT);
        mqttConnectOptions.setKeepAliveInterval(BROKER_CONNECTION_KEEP_ALIVE_TIME);
        //mqttConnectOptions.setCleanSession(MqttConnectOptions.CLEAN_SESSION_DEFAULT);
        mqttConnectOptions.setCleanSession(false);
        return mqttConnectOptions;
    }

    static public String getConnectionUrl() {
        final String mqttServerHost = "localhost";
        final int mqttServerPort = 1883;
        final String mqttServerURI = String.format("tcp://%s:%d",mqttServerHost,mqttServerPort);
        return mqttServerURI;
    }

    static public MqttClient getMqttClient(String connectionUrl, MqttConnectOptions mqttConnectOptions) {
        MqttClient mqttClient = null;
         try {
            mqttClient = new MqttClient(connectionUrl, "RollBackEnd " + MqttClient.generateClientId(), null);
            mqttClient.connect(mqttConnectOptions);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        if(mqttClient.isConnected())
            return mqttClient;
         else
             return null;
    }

    static public MqttClient getMqttClientForRollBack() throws Exception {
        String connectionUrl = getConnectionUrl();
        MqttConnectOptions mqttConnectOptions = getMqttConnectOptions();
        MqttClient mqttClient = null;
        try {
            mqttClient = new MqttClient(connectionUrl, "RollBackEnd " + MqttClient.generateClientId(), null);
            mqttClient.connect(mqttConnectOptions);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        if(mqttClient.isConnected())
            return mqttClient;
        else
            throw new Exception("Broker not connected for RollBack");
    }


    static public MqttMessage getMqttDefaultMessage() {
        MqttMessage mqttMessage;
        mqttMessage = new MqttMessage();
        mqttMessage.setQos(1);
        //mqttMessage.setRetained(true);
        return mqttMessage;
    }
}
