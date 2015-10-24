package io.adarrivi.springboot.ws.integration.controller;

import io.adarrivi.springboot.ws.WebServiceApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpStatus.OK;

@SpringApplicationConfiguration(classes = {WebServiceApplication.class})
@WebIntegrationTest(randomPort = true)
public class StatusControllerIntegrationTest extends AbstractTestNGSpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(StatusControllerIntegrationTest.class);

    @Value("${local.server.port}")
    private int port;

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void getStatus() {
        String url = "http://localhost:" + port + "/status";
        logger.info("Calling url {}", url);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        assertThat(responseEntity.getStatusCode(), equalTo(OK));
        logger.info("Response received {}", responseEntity.getBody());
    }


}
