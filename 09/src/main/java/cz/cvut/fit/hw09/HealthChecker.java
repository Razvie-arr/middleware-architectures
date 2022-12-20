package cz.cvut.fit.hw09;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Component
public class HealthChecker implements Runnable, CommandLineRunner {
    List<String> services;
    List<String> healthyServices;
    private final Logger logger = LoggerFactory.getLogger(ProxyController.class);
    @Autowired
    RestTemplate restTemplate;
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public HealthChecker() {
        this.services = createServicesList();
        this.healthyServices = new ArrayList<>();
    }

    @Override
    public void run(String... args) throws Exception {
        run();
    }

    @Override
    public void run() {
        while (true) {
            try {
                updateHealthyServices();
                Thread.sleep(10000);
            } catch (InterruptedException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateHealthyServices() throws URISyntaxException {
        healthyServices.clear();
        for (String service : services) {
            if (isServiceAlive(service)) {
                healthyServices.add(service);
            }
        }
        System.out.println(healthyServices.toString());
    }

    public List<String> createServicesList() {
        List<String> allServices = new ArrayList<>();
        allServices.add("http://147.32.233.18:8888/MI-MDW-LastMinute1/list");
        allServices.add("http://147.32.233.18:8888/MI-MDW-LastMinute2/list");
        allServices.add("http://147.32.233.18:8888/MI-MDW-LastMinute3/list");
        return allServices;
    }

    public boolean isServiceAlive(String url) {
        try {
            ResponseEntity responseEntity = restTemplate.exchange(new URI(url), HttpMethod.GET, null, String.class);
            logger.info("status code: {}", responseEntity.getStatusCode().value());
            return responseEntity.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {}
        return false;
    }

    public String getHealthyService() throws URISyntaxException {
        //double check healthy service, maybe within 10 sec service was unavailable, in that case just call updateHealthyServices and take first
        for (String service : healthyServices) {
            if (isServiceAlive(service)) {
                return service;
            }
        }
        updateHealthyServices();
        return healthyServices.get(0);
    }
}
