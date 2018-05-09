package nybsys.tillboxweb;

import nybsys.tillboxweb.TillBoxWebHistoryEntity.History;
import nybsys.tillboxweb.broker.client.Subscriber;
import nybsys.tillboxweb.controller.ApiRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import static nybsys.tillboxweb.constant.ControllerSubscriptionConstants.WEB_API_PURCHASE_TOPIC;
import static nybsys.tillboxweb.constant.WorkerSubscriptionConstants.WORKER_PURCHASE_TOPIC;

/**
 * PurchaseModule
 *
 */

@Component
public class PurchaseModule
{

    @Autowired
    private ApiRouter apiRouter;

    private Subscriber subscriber;

    private static final Logger log = LoggerFactory.getLogger(PurchaseModule.class);


    private void start() {
        log.info("Start PurchaseModule Back End");
        this.subscriber = new Subscriber(WORKER_PURCHASE_TOPIC,WEB_API_PURCHASE_TOPIC,apiRouter);
        try{
            this.subscriber.subscribe();

            if(Core.HistoryEntity==null)
                Core.HistoryEntity = new History();

            log.info("PurchaseModule Module Subscribe Successful at Back End");
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
        PurchaseModule appMain = applicationContext.getBean(PurchaseModule.class);
        appMain.start();
    }
}
