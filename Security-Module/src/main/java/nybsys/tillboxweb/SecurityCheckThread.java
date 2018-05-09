/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 09-Feb-18
 * Time: 11:22 AM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb;

import nybsys.tillboxweb.MessageModel.SecurityReqMessage;
import nybsys.tillboxweb.MessageModel.SecurityResMessage;
import nybsys.tillboxweb.broker.client.BrokerClient;
import nybsys.tillboxweb.broker.client.MqttUtils;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class SecurityCheckThread extends Core implements Runnable {

    private final Logger log = LoggerFactory.getLogger(SecurityCheckThread.class);

    private SecurityReqMessage securityReqMessage;

    @Autowired
    private Authorization authorization;

    @Override
    public void run() {
        SecurityResMessage securityResMessage;
        MqttClient mqttClient = BrokerClient.mqttClient;
        MqttMessage mqttMessage = MqttUtils.getMqttDefaultMessage();;
        String jsonString;
        String publishedTopic = securityReqMessage.messageId+"security";
        try {
            securityResMessage = this.authorization.getUsersPermission(this.securityReqMessage);
            jsonString = Core.jsonMapper.writeValueAsString(securityResMessage);
            mqttMessage.setPayload(jsonString.getBytes());
            mqttClient.publish(publishedTopic, mqttMessage);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("SecurityCheckThread: "+ e.getMessage());
        }
    }


    public void setSecurityReqMessage(SecurityReqMessage securityReqMessage) {
        this.securityReqMessage = securityReqMessage;
    }
}
