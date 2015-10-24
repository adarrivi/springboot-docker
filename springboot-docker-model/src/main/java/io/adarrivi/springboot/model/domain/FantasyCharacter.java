package io.adarrivi.springboot.model.domain;

import java.util.Objects;

public class FantasyCharacter {

    private String name;
    private int age;
    private LifeStatus lifeStatus;

    public FantasyCharacter(String name, int age, LifeStatus lifeStatus) {
        this.name = name;
        this.age = age;
        this.lifeStatus = lifeStatus;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public LifeStatus getLifeStatus() {
        return lifeStatus;
    }

    @Override
    public String toString() {
        return "FantasyCharacter{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", lifeStatus=" + lifeStatus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FantasyCharacter that = (FantasyCharacter) o;
        return Objects.equals(age, that.age) &&
                Objects.equals(name, that.name) &&
                Objects.equals(lifeStatus, that.lifeStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, lifeStatus);
    }
}
