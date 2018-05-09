package nybsys.tillboxweb;

import nybsys.tillboxweb.TillBoxWebHistoryEntity.History;
import nybsys.tillboxweb.broker.client.Subscriber;
import nybsys.tillboxweb.controller.ApiRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import static nybsys.tillboxweb.constant.ControllerSubscriptionConstants.WEB_API_SALES_TOPIC;
import static nybsys.tillboxweb.constant.WorkerSubscriptionConstants.WORKER_SALES_MODULE_TOPIC;


/**
 * SalesModule
 *
 */

@Component
public class SalesModule
{

    @Autowired
    private ApiRouter apiRouter;

    private Subscriber subscriber;

    private static final Logger log = LoggerFactory.getLogger(SalesModule.class);


    private void start() {
        log.info("Start TillBoxWeb Back End");
        this.subscriber = new Subscriber(WORKER_SALES_MODULE_TOPIC,WEB_API_SALES_TOPIC,apiRouter);
        try{
            this.subscriber.subscribe();

            if(Core.HistoryEntity==null)
                Core.HistoryEntity = new History();

            log.info("Sales Module Subscribe Successful at Back End");
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
        SalesModule appMain = applicationContext.getBean(SalesModule.class);
        appMain.start();
    }
}
