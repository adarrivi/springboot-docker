package io.adarrivi.springboot.model.test.builder;


import io.adarrivi.springboot.model.domain.FantasyCharacter;
import io.adarrivi.springboot.model.domain.LifeStatus;

import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;

public class ModelTestBuilder {

    private static final Random RANDOM = new Random();
    private static final List<String> NAMES = asList("John Snow", "Dracula", "Ender", "Tanis", "Zeus");
    private static final List<LifeStatus> LIFE_STATUSES = asList(LifeStatus.values());

    private ModelTestBuilder() {
    }

    public static FantasyCharacter createRandomCharacter() {
        String name = randomItemOf(NAMES);
        int age = RANDOM.nextInt(60) + 1;
        LifeStatus status = randomItemOf(LIFE_STATUSES);
        return new FantasyCharacter(name, age, status);
    }

    private static final <T> T randomItemOf(List<T> list) {
        return list.get(RANDOM.nextInt(list.size()));
    }
}
