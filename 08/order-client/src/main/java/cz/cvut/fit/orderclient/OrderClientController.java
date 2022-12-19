package cz.cvut.fit.orderclient;

import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Queue;

@RestController
@RequestMapping(value = "/orderClient")
@EnableJms
public class OrderClientController {
    private final Logger logger = LoggerFactory.getLogger(OrderClientApplication.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Queue queue;

    @Bean
    public Queue queue() {
        return new ActiveMQQueue("allOrdersQueue");
    }

    @PostMapping("booking")
    public ResponseEntity<String> publishNewBooking() {
        String message = "new-booking";
        jmsTemplate.convertAndSend(queue, message);
        logger.info("sent new booking to allOrdersQueue");
        return new ResponseEntity(message, HttpStatus.OK);
    }

    @PostMapping("trip")
    public ResponseEntity<String> publishNewTrip() {
        String message = "new-trip";
        jmsTemplate.convertAndSend(queue, message);
        logger.info("sent new trip to allOrdersQueue");
        return new ResponseEntity(message, HttpStatus.OK);
    }
}
