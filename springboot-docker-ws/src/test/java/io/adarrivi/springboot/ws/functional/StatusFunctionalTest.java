package io.adarrivi.springboot.ws.functional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpStatus.OK;

public class StatusFunctionalTest {

    private static final Logger logger = LoggerFactory.getLogger(StatusFunctionalTest.class);

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void getStatus() throws InterruptedException {
        Thread.sleep(5000);
        String url = "http://localhost:8080/status";
        logger.info("Calling url {}", url);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        assertThat(responseEntity.getStatusCode(), equalTo(OK));
        logger.info("Response received {}", responseEntity.getBody());
    }
}
