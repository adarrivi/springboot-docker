package io.adarrivi.springboot.ws.controller;

import io.adarrivi.springboot.ws.request.FantasyCharacterResponse;
import io.adarrivi.springboot.ws.service.FantasyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class FantasyCharacterController {

    @Autowired
    private FantasyService fantasyService;

    @RequestMapping(value = "/character/random", method = GET)
    public FantasyCharacterResponse generateRandom() {
        return fantasyService.generateRandom();
    }
}
