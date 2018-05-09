package nybsys.tillboxweb;

import nybsys.tillboxweb.broker.client.MqttCallBack;
import nybsys.tillboxweb.broker.client.MqttUtils;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

/**
 * UserRegistrationModule
 *
 */

//@Component
public class UserRegistrationModule extends Core
{

    final static String countrySubscriptionTopic="countryPublishedTopic";
    private static MqttClient mqttClient;

    static {
        try {
            mqttClient = MqttUtils.getMqttClient();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    static final Integer QoS=0;
    public static void main( String[] args )
    {



        try {
            MqttCallBack mqttCallBack = new MqttCallBack();
            String connectionUrl = MqttUtils.getConnectionUrl();
            MqttConnectOptions mqttConnectOptions = MqttUtils.getMqttConnectOptions();
            MqttClient mqttClient = MqttUtils.getMqttClient(connectionUrl, mqttConnectOptions);
            mqttClient.setCallback(mqttCallBack);

            mqttClient.setCallback(mqttCallBack);
            mqttClient.subscribe(countrySubscriptionTopic,QoS);
            System.out.println("Back end stared");

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
