/**
 * Created By: Md. Nazmus Salahin
 * Created Date: 08-Feb-18
 * Time: 1:00 PM
 * Modified By:
 * Modified date:
 * (C) CopyRight Nybsys ltd.
 */

package nybsys.tillboxweb.broker.client;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrokerClient {
    private static final Logger log = LoggerFactory.getLogger(BrokerClient.class);

    public static MqttClient mqttClient;

    static {
        try {
            mqttClient = MqttUtils.getMqttClient();
        } catch (Exception e) {
            log.error("Exception from Api Controller Publisher");
            e.printStackTrace();
        }
    }
}
