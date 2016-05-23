package ir.parsdeveloper.config;

import ir.parsdeveloper.service.impl.messaging.AdminMessageReceiver;
import ir.parsdeveloper.service.impl.messaging.AdminMessageSender;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.util.backoff.FixedBackOff;

import javax.jms.ConnectionFactory;
import javax.jms.MessageListener;
import javax.jms.Queue;

/**
 * @author hadi tayebi
 */
//@Configuration
public class JmsConfig {

    @Value("${jms.queue.name}")
    public String queueName;

    @Bean
    public CachingConnectionFactory connectionFactory() {
        //CachingConnectionFactory Definition, sessionCacheSize property is the number of sessions to cache
        ActiveMQConnectionFactory amqConnectionFactory = new ActiveMQConnectionFactory("failover:(tcp://localhost:61616)?timeout=30000");
        amqConnectionFactory.setWarnAboutUnstartedConnectionTimeout(1);
        amqConnectionFactory.setCloseTimeout(1);
        amqConnectionFactory.setSendTimeout(1);
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(amqConnectionFactory);
        cachingConnectionFactory.setSessionCacheSize(100);

        return cachingConnectionFactory;
    }

    @Bean
    public Queue queueDestination() {
        ActiveMQQueue queue = new ActiveMQQueue(queueName);
        return queue;
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory, Queue queueDestination) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory);
        jmsTemplate.setDefaultDestination(queueDestination);
        jmsTemplate.setReceiveTimeout(10);
        jmsTemplate.setTimeToLive(600000);
        return jmsTemplate;
    }

    @Bean
    public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory, Queue queueDestination, MessageListener adminMessageReceiver) {
        DefaultMessageListenerContainer messageListenerContainer = new DefaultMessageListenerContainer();
        //messageListenerContainer.set
        messageListenerContainer.setCacheLevel(0);
        messageListenerContainer.setConnectionFactory(connectionFactory);
        messageListenerContainer.setDestination(queueDestination);
        messageListenerContainer.setMessageListener(adminMessageReceiver);
        messageListenerContainer.setReceiveTimeout(100);
        messageListenerContainer.setTransactionTimeout(100);
        messageListenerContainer.setBackOff(new FixedBackOff(1, 2));
        return messageListenerContainer;
    }/*
    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory, Queue queueDestination, MessageListener adminMessageReceiver) {
        SimpleMessageListenerContainer messageListenerContainer = new SimpleMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(connectionFactory);
        messageListenerContainer.setDestination(queueDestination);
        messageListenerContainer.setMessageListener(adminMessageReceiver);
        return messageListenerContainer;
    }*/

    @Bean
    public AdminMessageSender adminMessageSender(JmsTemplate jmsTemplate) {
        AdminMessageSender adminMessageSender = new AdminMessageSender();
        adminMessageSender.setJmsTemplate(jmsTemplate);
        return adminMessageSender;
    }

    @Bean
    public AdminMessageReceiver adminMessageReceiver() {
        AdminMessageReceiver adminMessageReceiver = new AdminMessageReceiver();
        return adminMessageReceiver;
    }


}
