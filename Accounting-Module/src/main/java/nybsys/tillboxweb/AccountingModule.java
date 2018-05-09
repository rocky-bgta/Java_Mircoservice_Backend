package nybsys.tillboxweb;

import nybsys.tillboxweb.TillBoxWebHistoryEntity.History;
import nybsys.tillboxweb.broker.client.Subscriber;
import nybsys.tillboxweb.controller.ApiRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import static nybsys.tillboxweb.constant.ControllerSubscriptionConstants.WEB_API_ACCOUNTING_TOPIC;
import static nybsys.tillboxweb.constant.WorkerSubscriptionConstants.WORKER_ACCOUNTING_MODULE_TOPIC;


@Component
public class AccountingModule
{
    @Autowired
    private ApiRouter apiRouter;

    private Subscriber subscriber;

    private static final Logger log = LoggerFactory.getLogger(AccountingModule.class);
    private void start() {
        log.info("Start TillBoxWeb Account Module Back End");
        this.subscriber = new Subscriber(WORKER_ACCOUNTING_MODULE_TOPIC,WEB_API_ACCOUNTING_TOPIC,apiRouter);

        //Subscriber subscriber = new Subscriber();
        try{
            this.subscriber.subscribe();

            if(Core.HistoryEntity==null)
                Core.HistoryEntity = new History();

            log.info("Account Module Subscribe Successful at Back End");
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
        AccountingModule appMain = applicationContext.getBean(AccountingModule.class);
        appMain.start();
    }
}
