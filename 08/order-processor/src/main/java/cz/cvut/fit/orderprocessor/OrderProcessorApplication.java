package cz.cvut.fit.orderprocessor;

import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Queue;

@SpringBootApplication
@EnableJms
public class OrderProcessorApplication {
    private final Logger logger = LoggerFactory.getLogger(OrderProcessorApplication.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Queue bookingQueue;

    @Autowired
    private Queue tripQueue;

    @Bean
    public Queue bookingQueue() {
        return new ActiveMQQueue("bookingQueue");
    }

    @Bean
    public Queue tripQueue() {
        return new ActiveMQQueue("tripQueue");
    }

    @JmsListener(destination = "allOrdersQueue")
    public void readMessageAndSentToConsumer(String message) throws InterruptedException {
        logger.info("Received message: {}", message);
        if (message.equals("new-booking")) {
            jmsTemplate.convertAndSend(bookingQueue, message);
            logger.info("Sent message: " + message + " to booking queue");
        } else {
            jmsTemplate.convertAndSend(tripQueue, message);
            logger.info("Sent message: " + message + " to trip queue");
        }
    }


    public static void main(String[] args) {
        SpringApplication.run(OrderProcessorApplication.class, args);
    }

}
