package cz.cvut.fit.bookingsprocessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;

@SpringBootApplication
@EnableJms
public class BookingsProcessorApplication {
    private final Logger logger = LoggerFactory.getLogger(BookingsProcessorApplication.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @JmsListener(destination = "bookingQueue")
    public void readMessage(String message) {
        logger.info("Received message: {}", message);
    }


    public static void main(String[] args) {
        SpringApplication.run(BookingsProcessorApplication.class, args);
    }

}
