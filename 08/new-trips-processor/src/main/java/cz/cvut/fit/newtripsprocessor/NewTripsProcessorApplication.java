package cz.cvut.fit.newtripsprocessor;

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
public class NewTripsProcessorApplication {
    private final Logger logger = LoggerFactory.getLogger(NewTripsProcessorApplication.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @JmsListener(destination = "tripQueue")
    public void readMessage(String message) {
        logger.info("Received message: {}", message);
    }



    public static void main(String[] args) {
        SpringApplication.run(NewTripsProcessorApplication.class, args);
    }

}
