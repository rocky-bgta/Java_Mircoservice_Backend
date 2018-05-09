package nybsys.tillboxweb;

import nybsys.tillboxweb.broker.client.MqttCallBack;
import nybsys.tillboxweb.broker.client.MqttUtils;
import nybsys.tillboxweb.broker.client.TestMqttCallBack;
import org.eclipse.paho.client.mqttv3.MqttClient;

/**
 * UserRegistrationModule
 */


public class UserRegistrationModule extends Core {

    final static String countrySubscriptionTopic = "countryPublishedTopic";
    final static String countryPublishedTopic="countrySubscriptionTopic";

    final static String vatSubscriptionTopic = "vatPublishedTopic";
    final static String vatPublishedTopic="vatSubscriptionTopic";





   /* static {
        try {
            mqttClient = MqttUtils.getMqttClient();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
*/

    static final Integer QoS = 0;

    public static void main(String[] args) {
        MqttClient mqttClient;
        MqttCallBack mqttCallBack;
        TestMqttCallBack testMqttCallBack;
        try {

            //=============== Test ================
            mqttClient = MqttUtils.getMqttClient();
            testMqttCallBack = new TestMqttCallBack();
            mqttClient.setCallback(testMqttCallBack);
            mqttClient.subscribe(countrySubscriptionTopic, QoS);
            //=============== Test ================

            //=============== County ================
            mqttClient = MqttUtils.getMqttClient();
            mqttCallBack = new MqttCallBack();
            mqttCallBack.setPublishedTopic(countryPublishedTopic);
            mqttClient.setCallback(mqttCallBack);
            mqttClient.subscribe(countrySubscriptionTopic, QoS);
            //=============== County ================


            //=============== Vat Rat ================
            mqttClient = MqttUtils.getMqttClient();
            mqttCallBack = new MqttCallBack();
            mqttCallBack.setPublishedTopic(vatPublishedTopic);
            mqttClient.setCallback(mqttCallBack);
            mqttClient.subscribe(vatSubscriptionTopic, QoS);
            //=============== Vat Rat ================


            System.out.println("Back end stared");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
