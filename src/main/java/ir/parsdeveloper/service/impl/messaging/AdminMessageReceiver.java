package ir.parsdeveloper.service.impl.messaging;

import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * @author hadi tayebi
 */

public class AdminMessageReceiver implements MessageListener {

    @Override
    public void onMessage(Message message) {
        System.out.println("receved message : ");
        try {
            System.out.println(((ActiveMQTextMessage) message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
