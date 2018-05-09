package nybsys.tillboxweb;

import nybsys.tillboxweb.broker.client.CallBackForCommit;
import nybsys.tillboxweb.broker.client.CallBackForRollBack;
import nybsys.tillboxweb.broker.client.SubscriberForRollBackAndCommit;
import nybsys.tillboxweb.constant.MessageConstant;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;


@Component
public class RollBackAndCommitModule
{

    @Autowired
    private ApplicationContext applicationContext;

    private static final Logger log = LoggerFactory.getLogger(RollBackAndCommitModule.class);

    private void start() {
        log.info("Start TillBoxWeb RollBack and Commit Module Back End");
        SubscriberForRollBackAndCommit subscriberCommit = new SubscriberForRollBackAndCommit(
                MessageConstant.COMMIT_TOPIC, new CallBackForCommit());
        SubscriberForRollBackAndCommit subscriberRollBack = new SubscriberForRollBackAndCommit(
                MessageConstant.ROLLBACK_TOPIC, new CallBackForRollBack());
        try{
            subscriberCommit.subscribe();
            subscriberRollBack.subscribe();

            SessionFactory sessionFactory;
            sessionFactory = (SessionFactory) this.applicationContext.getBean("defaultSessionFactory");
            RollBackAndCommit.sessionFactory = sessionFactory;

            log.info("RollBack and Commit Module Subscribe Successful");
        }catch (Exception ex){
            log.info("Subscribe Failed from RollBack and Commit Module");
            ex.printStackTrace();
        }
    }

    public static void main( String[] args )
    {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        applicationContext.scan("nybsys.tillboxweb");
        applicationContext.refresh();
        RollBackAndCommitModule appMain = applicationContext.getBean(RollBackAndCommitModule.class);
        appMain.start();
    }
}
