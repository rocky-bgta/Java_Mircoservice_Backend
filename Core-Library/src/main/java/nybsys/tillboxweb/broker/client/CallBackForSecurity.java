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
import nybsys.tillboxweb.MessageModel.SecurityResMessage;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CallBackForSecurity extends Core implements MqttCallback {

    private static final Logger log = LoggerFactory.getLogger(CallBackForSecurity.class);
    private SecurityResMessage securityResMessage;
    private Object lockObject;

    public CallBackForSecurity() {
    }

   /* public CallBackForSecurity(Object lockObject) {
        this.lockObject = lockObject;
    }
    */


    @Override
    public void connectionLost(Throwable cause) {
        log.error("Connection to MQTT broker lost from Security CallBack!!!!" + cause);
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        String incomingMessage;

        incomingMessage = new String(mqttMessage.getPayload());

        try {

            this.securityResMessage = Core.jsonMapper.readValue(incomingMessage, SecurityResMessage.class);
            synchronized (this.lockObject) {
                log.info("======== In security check lock  ===========");
                //Thread.sleep(100);
                this.lockObject.notifyAll();
                log.info("======== Release security check lock======");
            }
        } catch (Exception ex) {
            log.error("Exception from Security CallBack " + ex);
            ex.printStackTrace();
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }

    public SecurityResMessage getSecurityResMessage() {
        return securityResMessage;
    }

    public void setLockObject(Object lockObject) {
        this.lockObject = lockObject;
    }
}
