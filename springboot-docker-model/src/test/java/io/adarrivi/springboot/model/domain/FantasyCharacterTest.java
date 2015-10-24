package io.adarrivi.springboot.model.domain;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.adarrivi.springboot.model.domain.LifeStatus.ALIVE;
import static io.adarrivi.springboot.model.domain.LifeStatus.DEAD;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FantasyCharacterTest {

    @DataProvider
    public Object[][] equalAndDifferent() {
        return new Object[][]{
                {characterOf("name", 3, ALIVE), characterOf("name", 3, ALIVE), true},
                {characterOf("other", 3, ALIVE), characterOf("name", 3, ALIVE), false},
                {characterOf("name", 2, ALIVE), characterOf("name", 3, ALIVE), false},
                {characterOf("name", 3, DEAD), characterOf("name", 3, ALIVE), false}
        };
    }

    private FantasyCharacter characterOf(String name, int age, LifeStatus status) {
        return new FantasyCharacter(name, age, status);
    }

    @Test(dataProvider = "equalAndDifferent")
    public void equalsAndHashCode_AreSymmetrical(FantasyCharacter character1, FantasyCharacter character2, boolean expectedResult) {
        assertThat(character1.equals(character2), is(expectedResult));
        assertThat(character2.equals(character1), is(expectedResult));
        assertThat(character1.hashCode() == character2.hashCode(), is(expectedResult));
    }

}
