package nybsys.tillboxweb;


import nybsys.tillboxweb.TillBoxWebHistoryEntity.History;
import nybsys.tillboxweb.broker.client.BrokerClient;
import nybsys.tillboxweb.broker.client.CallBackForSecurityModule;
import nybsys.tillboxweb.broker.client.Subscriber;
import nybsys.tillboxweb.constant.BrokerConstant;
import nybsys.tillboxweb.constant.BrokerMessageTopic;
import nybsys.tillboxweb.controller.ApiRouter;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import static nybsys.tillboxweb.constant.ControllerSubscriptionConstants.WEB_API_SECURITY_TOPIC;
import static nybsys.tillboxweb.constant.WorkerSubscriptionConstants.WORKER_SECURITY_MODULE_TOPIC;

/**
 * SecurityModule
 *
 */

@Component
public class SecurityModule
{

    private static final Logger log = LoggerFactory.getLogger(SecurityModule.class);
    private Subscriber subscriber;
    private MqttClient mqttClient;

    @Autowired
    private ApiRouter apiRouter;

    @Autowired
    private CallBackForSecurityModule callBackForSecurityModule;


    private void start() {
        log.info("Start TillBoxWeb Security module Back End");
        this.subscriber = new Subscriber(WORKER_SECURITY_MODULE_TOPIC,WEB_API_SECURITY_TOPIC,apiRouter);

        //Subscriber subscriber = new Subscriber();
        try{
            this.subscriber.subscribe();


            this.mqttClient = BrokerClient.mqttClient;
            this.mqttClient.setCallback(this.callBackForSecurityModule);
            this.mqttClient.subscribe(BrokerMessageTopic.SECURITY_REQUEST_TOPIC, BrokerConstant.oneQoS);


            if(Core.HistoryEntity==null)
                Core.HistoryEntity = new History();

            log.info("Security Module Subscribe Successful at Back End");
        }catch (Exception ex){
            log.info("Subscribe Failed");
            ex.printStackTrace();
        }
    }

    public static void main( String[] args )
    {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        applicationContext.scan("nybsys.tillboxweb");
        applicationContext.refresh();
        SecurityModule appMain = applicationContext.getBean(SecurityModule.class);
        appMain.start();
    }
}
