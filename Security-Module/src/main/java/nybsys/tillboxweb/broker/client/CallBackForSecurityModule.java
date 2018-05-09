/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 07-Feb-18
 * Time: 4:03 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.broker.client;

import nybsys.tillboxweb.Core;
import nybsys.tillboxweb.SecurityCheckThread;
import nybsys.tillboxweb.MessageModel.SecurityReqMessage;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CallBackForSecurityModule extends Core implements MqttCallback {

    private static final Logger log = LoggerFactory.getLogger(CallBackForSecurityModule.class);
    private SecurityReqMessage securityReqMessage;
    private Thread thread;

    @Autowired
    private SecurityCheckThread securityCheckThread;

    @Override
    public void connectionLost(Throwable cause) {
        log.info("Connection to MQTT broker lost from CallBackForSecurityModule CallBack! " +cause);
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        String incomingMessage;

        incomingMessage = new String(mqttMessage.getPayload());


        this.securityReqMessage = Core.jsonMapper.readValue(incomingMessage, SecurityReqMessage.class);
        //this.securityCheckThread = new SecurityCheckThread(this.securityReqMessage);
        this.securityCheckThread.setSecurityReqMessage(this.securityReqMessage);
        this.thread = new Thread(this.securityCheckThread);
        this.thread.setName("Security Check Thread No: " + this.thread.getId());
        this.thread.start();

        // Lambda Runnable

        /*
        Runnable runnable = () -> {
            this.rollBackAndCommit = new RollBackAndCommit();
            this.rollBackAndCommit.commit(this.messageId);
        };

        // start the thread
        this.thread = new Thread(runnable);
        this.thread.setName("Commit Thread No: " + this.thread.getId());
        this.thread.start();
        */
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
