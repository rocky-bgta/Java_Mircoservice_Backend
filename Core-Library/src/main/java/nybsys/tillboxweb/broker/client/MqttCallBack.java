/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 12-Jan-18
 * Time: 4:42 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */
package nybsys.tillboxweb.broker.client;


import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.MessageModel.RequestMessage;
import nybsys.tillboxweb.MessageModel.ResponseMessage;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MqttCallBack implements MqttCallback {


    private static final Logger log = LoggerFactory.getLogger(MqttCallBack.class);
    private RequestMessage requestMessage;

    final static String countryPublishedTopic="countrySubscriptionTopic";
    private static MqttClient mqttClient;

    static {
        try {
            mqttClient = MqttUtils.getMqttClient();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void connectionLost(Throwable throwable) {
        log.info("Connection to MQTT broker lost!");
    }

    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {

        String incomingMessage,pubJsonString;
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.responseObj = "Country from Backend";



        incomingMessage = new String(mqttMessage.getPayload());
        this.requestMessage = Core.jsonMapper.readValue(incomingMessage, RequestMessage.class);
        responseMessage.token = this.requestMessage.token;



        mqttMessage = MqttUtils.getMqttDefaultMessage();

        pubJsonString = Core.jsonMapper.writeValueAsString(responseMessage);
        mqttMessage.setPayload(pubJsonString.getBytes());
        mqttClient.publish(countryPublishedTopic,mqttMessage);



        if (incomingMessage == null) {
            throw new Exception();
        }
    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
    }
}
