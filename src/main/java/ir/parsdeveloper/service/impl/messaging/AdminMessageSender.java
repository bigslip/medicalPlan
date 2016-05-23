package ir.parsdeveloper.service.impl.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author hadi tayebi
 */
public class AdminMessageSender {
    JmsTemplate jmsTemplate;

    public void senMessage(String message) {
        jmsTemplate.convertAndSend(message);
    }

    public String receiveMessage() {
        return (String) jmsTemplate.receiveAndConvert();
    }

    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
}
