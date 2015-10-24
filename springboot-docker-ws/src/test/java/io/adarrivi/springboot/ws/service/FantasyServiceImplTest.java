package io.adarrivi.springboot.ws.service;

import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class FantasyServiceImplTest {

    private FantasyServiceImpl victim = new FantasyServiceImpl();

    @Test
      public void createRandomCharacter_DoesNotReturnNull() {
        assertThat(victim.generateRandom(), is(notNullValue()));
    }

    @Test
      public void createRandomCharacter_PopulatesCreatedAt() {
        assertThat(victim.generateRandom().getCreatedAt(), is(notNullValue()));
    }

}