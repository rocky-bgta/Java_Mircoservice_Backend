/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 08-Feb-18
 * Time: 12:56 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.broker.client;

import nybsys.tillboxweb.Core;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PublisherForSecurity extends Core {
    private static final Logger log = LoggerFactory.getLogger(PublisherForSecurity.class);

    //private String publishedTopic;
    //private MqttClient mqttClient;
    private MqttMessage mqttMessage;


    public void publishedMessage(String publishedTopic, Object requestMessage) throws MqttException {
        String jsonString;
        MqttClient mqttClient = BrokerClient.mqttClient;
        try {
            this.mqttMessage = MqttUtils.getMqttDefaultMessage();

            //if (this.mqttClient.isConnected()) {
            if (mqttClient.isConnected()) {
                jsonString = Core.jsonMapper.writeValueAsString(requestMessage);
                this.mqttMessage.setPayload(jsonString.getBytes());
                //this.mqttClient.publish(publishedTopic, this.mqttMessage);
                mqttClient.publish(publishedTopic, this.mqttMessage);
                //this.mqttClient.unsubscribe(publishedTopic);
            } else
                throw new Exception("Connected to Broker Failed from");

            log.info("Message published from PublisherForSecurity Worker End");

        } catch (Exception e) {
            log.error("Exception from PublisherForSecurity Worker Publisher");
            e.printStackTrace();
        }finally {
            this.mqttMessage = null;
        }
    }
}