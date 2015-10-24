package io.adarrivi.springboot.ws.service;

import io.adarrivi.springboot.ws.request.FantasyCharacterResponse;

public interface FantasyService {

    FantasyCharacterResponse generateRandom();
}
