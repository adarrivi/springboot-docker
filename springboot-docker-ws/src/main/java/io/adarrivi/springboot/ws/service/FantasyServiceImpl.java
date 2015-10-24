package io.adarrivi.springboot.ws.service;

import io.adarrivi.springboot.model.domain.FantasyCharacter;
import io.adarrivi.springboot.model.domain.LifeStatus;
import io.adarrivi.springboot.ws.request.FantasyCharacterResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;

@Service
class FantasyServiceImpl implements FantasyService {

    private static final Random RANDOM = new Random();
    private static final List<String> NAMES = asList("John Snow", "Dracula", "Ender", "Tanis", "Zeus");
    private static final List<LifeStatus> LIFE_STATUSES = asList(LifeStatus.values());

    FantasyServiceImpl() {
    }

    @Override
    public FantasyCharacterResponse generateRandom() {
        FantasyCharacter randomCharacter = createRandomCharacter();
        return new FantasyCharacterResponse(randomCharacter.getName(), randomCharacter.getAge(), randomCharacter.getLifeStatus().name());
    }

    private FantasyCharacter createRandomCharacter() {
        String name = randomItemOf(NAMES);
        int age = RANDOM.nextInt(60) + 1;
        LifeStatus status = randomItemOf(LIFE_STATUSES);
        return new FantasyCharacter(name, age, status);
    }

    private <T> T randomItemOf(List<T> list) {
        return list.get(RANDOM.nextInt(list.size()));
    }
}
