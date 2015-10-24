package io.adarrivi.springboot.ws.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class StatusController {

    @RequestMapping(value = "/status", method = GET)
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("Everything looks OK");

    }
}
