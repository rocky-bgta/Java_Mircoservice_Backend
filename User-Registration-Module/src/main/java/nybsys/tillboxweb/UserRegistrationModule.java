package nybsys.tillboxweb;

import nybsys.tillboxweb.broker.client.MqttCallBack;
import nybsys.tillboxweb.broker.client.MqttUtils;
import org.eclipse.paho.client.mqttv3.MqttClient;

/**
 * UserRegistrationModule
 */


public class UserRegistrationModule extends Core {

    final static String countrySubscriptionTopic = "countryPublishedTopic";
    final static String countryPublishedTopic="countrySubscriptionTopic";

    final static String vatSubscriptionTopic = "vatPublishedTopic";
    final static String vatPublishedTopic="vatSubscriptionTopic";



    private static MqttClient mqttClient;

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

        try {

            //=============== County ================
            MqttClient mqttClientForCountry = MqttUtils.getMqttClient();

            MqttCallBack mqttCallBackForCountry = new MqttCallBack();
            mqttCallBackForCountry.setPublishedTopic(countryPublishedTopic);
            mqttClientForCountry.setCallback(mqttCallBackForCountry);
            mqttClientForCountry.subscribe(countrySubscriptionTopic, QoS);
            //=============== County ================


            //=============== Vat Rat ================
            MqttClient mqttClientForVat = MqttUtils.getMqttClient();

            mqttClientForVat = MqttUtils.getMqttClient();
            MqttCallBack mqttCallBackForVat = new MqttCallBack();
            mqttCallBackForVat.setPublishedTopic(vatPublishedTopic);
            mqttClientForVat.setCallback(mqttCallBackForVat);
            mqttClientForVat.subscribe(vatSubscriptionTopic, QoS);


            //=============== Vat Rat ================


            System.out.println("Back end stared");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
