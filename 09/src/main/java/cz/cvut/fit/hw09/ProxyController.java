package cz.cvut.fit.hw09;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/loadBalancer")
public class ProxyController {
    private final Logger logger = LoggerFactory.getLogger(ProxyController.class);

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    HealthChecker healthChecker;

    @GetMapping(value = "getInfo")
    public ResponseEntity getInfo(HttpServletRequest request) throws Exception {
        // copy headers
        HttpHeaders headers = new HttpHeaders();
        Collections.list(request.getHeaderNames()).forEach(head -> headers.add(head, request.getHeader(head)));
        // create request entity
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        // HTTP
        String uri = healthChecker.getHealthyService();
        ResponseEntity responseEntity = restTemplate.exchange(new URI(uri), HttpMethod.GET, requestEntity, String.class);
        logger.info("Proxy controller status code: {}", responseEntity.getStatusCode().value());
        logger.info("Used url: " + uri);
        return responseEntity;
    }
}
