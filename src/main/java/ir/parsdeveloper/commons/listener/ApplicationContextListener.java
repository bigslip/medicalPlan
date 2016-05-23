package ir.parsdeveloper.commons.listener;


import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

/**
 * @author hadi tayebi
 */
@Component
public class ApplicationContextListener implements ApplicationListener<ApplicationContextEvent> {


    public void onApplicationEvent(ContextStartedEvent event) {

    /*    try {
            MetaModelInitializer.initial();
        } catch (SystemException e) {
            e.printStackTrace();
            throw new Error();//todo fix throw exception
        }*/

    }


    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("----------------------***---------------------------");
    }

    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            System.out.println("event  : " + event.toString() + " time : " + event.getTimestamp());
        }

    }
}
