package io.adarrivi.springboot.model.json;

import io.adarrivi.springboot.model.domain.FantasyCharacter;
import io.adarrivi.springboot.model.test.builder.ModelTestBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.fail;

public class JsonMapperTest {

    private static final Logger logger = LoggerFactory.getLogger(JsonMapperTest.class);
    private static final String JSON_FILE = "/json/FantasyCharacter.json";

    @Test
    public void toJsonString_FantasyCharacter_DoesNotReturnNull() {
        FantasyCharacter character = ModelTestBuilder.createRandomCharacter();
        String jsonString = JsonMapper.toJsonString(character);
        assertThat(jsonString, is(notNullValue()));
        logger.info("{} parsed as {}", character, jsonString);
    }

    @Test
    public void toObject_FantasyCharacter_DoesNotReturnNull() {
        String jsonString = getFileAsString(JSON_FILE);
        FantasyCharacter character = JsonMapper.toObject(jsonString, FantasyCharacter.class);
        assertThat(character, is(notNullValue()));
        logger.info("{} parsed as {}", jsonString, character);
    }

    private String getFileAsString(String fileName) {
        try {
            URL resource = getClass().getResource(fileName);
            return new String(Files.readAllBytes(Paths.get(resource.toURI())));
        } catch (IOException | URISyntaxException e) {
            fail(e.getMessage());
        }
        return null;
    }

    @Test
    public void toJsonString_toObject_FantasyCharacter_AreEquals() {
        FantasyCharacter character = ModelTestBuilder.createRandomCharacter();
        String jsonString = JsonMapper.toJsonString(character);
        FantasyCharacter newCharacter = JsonMapper.toObject(jsonString, FantasyCharacter.class);
        assertThat(character, equalTo(newCharacter));
        logger.info("{} parsed as {}", character, jsonString);
    }


}
