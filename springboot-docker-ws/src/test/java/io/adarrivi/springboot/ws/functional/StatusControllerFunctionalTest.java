package io.adarrivi.springboot.ws.functional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpStatus.OK;

public class StatusControllerFunctionalTest {

    private static final Logger logger = LoggerFactory.getLogger(StatusControllerFunctionalTest.class);

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void getStatus() {
        String url = "http://"+System.getProperty("example.app.ip")+":8080/status";
        logger.info("Calling url {}", url);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        assertThat(responseEntity.getStatusCode(), equalTo(OK));
        logger.info("Response received {}", responseEntity.getBody());
    }


}
